package com.food.ordering.system.payment.service.domain;

import com.food.ordering.system.order.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.system.domain.valueobject.Money;
import com.food.ordering.system.order.system.domain.valueobject.PaymentStatus;
import com.food.ordering.system.payment.service.domain.entity.CreditEntry;
import com.food.ordering.system.payment.service.domain.entity.CreditHistory;
import com.food.ordering.system.payment.service.domain.entity.Payment;
import com.food.ordering.system.payment.service.domain.event.PaymentCancelledEvent;
import com.food.ordering.system.payment.service.domain.event.PaymentCompletedEvent;
import com.food.ordering.system.payment.service.domain.event.PaymentEvent;
import com.food.ordering.system.payment.service.domain.event.PaymentFailedEvent;
import com.food.ordering.system.payment.service.domain.valueobject.CreditHistoryId;
import com.food.ordering.system.payment.service.domain.valueobject.TransactionalType;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static com.food.ordering.system.order.system.domain.DomainConstants.UTC;

@Slf4j
public class PaymentDomainServiceIml implements PaymentDomainService {

    @Override
    public PaymentEvent validateAndInitiatePayment(Payment payment,
                                                   CreditEntry creditEntry,
                                                   List<CreditHistory> creditHistories,
                                                   List<String> failureMessages,
                                                   DomainEventPublisher<PaymentCompletedEvent>
                                                               paymentCompletedDomainEventPublisher,
                                                   DomainEventPublisher<PaymentFailedEvent>
                                                               paymentFailedEventDomainEventPublisher) {
        payment.validatePayment(failureMessages);
        payment.initializePayment();
        validateCreditEntry(payment, creditEntry, failureMessages);
        subtractCreditEntry(payment, creditEntry);
        updateCreditHistory(payment, creditHistories, TransactionalType.DEBIT);
        validateCreditHistory(creditEntry, creditHistories, failureMessages);
        if (failureMessages.isEmpty()) {
            log.info("Payment is initiated for oder id = {}", payment.getPaymentId().getValue());
            payment.updateStatus(PaymentStatus.COMPLETED);
            return new PaymentCompletedEvent(payment, ZonedDateTime.now(ZoneId.of(UTC)), paymentCompletedDomainEventPublisher);
        } else {
            log.info("Payment initiate is failed for order id = {}", payment.getPaymentId().getValue());
            payment.updateStatus(PaymentStatus.FAILED);
            return new PaymentFailedEvent(payment, ZonedDateTime.now(ZoneId.of(UTC)), paymentFailedEventDomainEventPublisher);
        }
    }


    @Override
    public PaymentEvent validateAndCancelPayment(Payment payment,
                                                 CreditEntry creditEntry,
                                                 List<CreditHistory> creditHistories,
                                                 List<String> failureMessages,
                                                 DomainEventPublisher<PaymentCancelledEvent>
                                                             paymentCancelledEventDomainEventPublisher,
                                                 DomainEventPublisher<PaymentFailedEvent> paymentFailedEventDomainEventPublisher) {
        payment.validatePayment(failureMessages);
        addCreditEntry(payment, creditEntry);
        updateCreditHistory(payment, creditHistories, TransactionalType.CREDIT);
        if(failureMessages.isEmpty()){
            log.info("Payment is cancelled for order id = ", payment.getPaymentId().getValue());
            payment.updateStatus(PaymentStatus.CANCELED);
            return new PaymentCancelledEvent(payment, ZonedDateTime.now(ZoneId.of(UTC)),paymentCancelledEventDomainEventPublisher);
        }
        else {
            log.info("Payment is failed for order id = {}", payment.getPaymentId().getValue());
            payment.setPaymentStatus(PaymentStatus.FAILED);
            return new PaymentFailedEvent(payment, ZonedDateTime.now(ZoneId.of(UTC)), paymentFailedEventDomainEventPublisher);
        }
    }

    private void addCreditEntry(Payment payment, CreditEntry creditEntry) {
        creditEntry.addCreditAmount(payment.getPrice());
    }

    private void validateCreditEntry(Payment payment,
                                     CreditEntry creditEntry,
                                     List<String> failureMessages) {
        if (payment.getPrice().isGreaterThan(creditEntry.getTotalCreditAmount())) {
            log.error("Customer with id: {} does not have enough for payment!",
                    payment.getCustomerId().getValue());
            failureMessages.add("Customer with id = " + payment.getCustomerId().getValue()
                    + " does not have enough credit for payment!");
        }
    }

    private void subtractCreditEntry(Payment payment, CreditEntry creditEntry) {
        creditEntry.subtractCreditAmount(payment.getPrice());
    }

    private void updateCreditHistory(Payment payment, List<CreditHistory> creditHistories, TransactionalType transactionalType) {
        creditHistories.add(CreditHistory.builder()
                .creditHistoryId(new CreditHistoryId(UUID.randomUUID()))
                .customerId(payment.getCustomerId())
                .amount(payment.getPrice())
                .transactionalType(transactionalType)
                .build());
    }

    private void validateCreditHistory(CreditEntry creditEntry,
                                       List<CreditHistory> creditHistories,
                                       List<String> failureMessages) {
        Money totalCreditHistoryAmount = getTotalHistoryAmount(creditHistories, TransactionalType.CREDIT);

        Money totalDebitHistoryAmount = getTotalHistoryAmount(creditHistories, TransactionalType.DEBIT);

        if (totalDebitHistoryAmount.isGreaterThan(totalCreditHistoryAmount)) {
            log.error("Customer with id: {} does not have enough credit according to credit history",
                    creditEntry.getCustomerId().getValue());
            failureMessages.add("Customer with id= " + creditEntry.getCustomerId().getValue() +
                    " does not have enough credit according to credit history");
        }

        if (!creditEntry.getTotalCreditAmount().equals(totalCreditHistoryAmount.subtractMoney(totalDebitHistoryAmount))) {
            log.error("Total credit history is not equal to current current credit for customer id = {}",
                    creditEntry.getCustomerId().getValue() + " !");
            failureMessages.add("Credit history total is not equal to current credit for customer id = {}" +
                    creditEntry.getCustomerId().getValue());
        }

    }

    private Money getTotalHistoryAmount(List<CreditHistory> creditHistories, TransactionalType transactionalType) {
        return creditHistories.stream()
                .filter(creditHistory -> transactionalType == creditHistory.getTransactionalType())
                .map(CreditHistory::getAmount)
                .reduce(Money.ZERO, Money::addMoney);
    }
}
