package com.sash.quarkus.coffeeshop.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Beverage {

    public String beverage;
    public String customer;
    public String preparedBy;
    public String orderId;
    public State preparationState;
    public String orderMethod;

    public Beverage() {

    }

    public static Beverage queued(Order order) {
        return new Beverage(order, null, State.IN_QUEUE);
    }

    public enum State {
        IN_QUEUE,
        BEING_PREPARED,
        READY,
        FAILED;
    }

    public Beverage(Order order, String baristaName, State state) {
        this.beverage = order.getProduct();
        this.orderMethod = order.getOrderMethod();
        this.customer = order.getName();
        this.orderId = order.getOrderId();
        this.preparedBy = baristaName;
        this.preparationState = state;
    }
}
