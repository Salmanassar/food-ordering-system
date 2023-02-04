package com.food.ordering.system.order.service.application.exceptionhandler;

public class ObRef extends Base {
    public static void main(String argv[]) {
        ObRef ob = new ObRef();
        Base b = new Base();
        Object o1 = new Object();
        IFace o2 = new CFace();
        System.out.println(o1=b);
        System.out.println(b=ob);


    }
}