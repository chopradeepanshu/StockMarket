package com.stockmarket.data;

import com.stockmarket.models.Person;
import com.stockmarket.models.StockTriggers;

public class TriggerAlerts implements StockTriggers {

    final Person customer;
    BuyOrSell triggerType;
    float priceThreshold;
    CustomerNotification customerNotification;


    public TriggerAlerts(BuyOrSell trigger, Person person, CustomerNotification comm, float threshold) {
        triggerType = trigger;
        priceThreshold = threshold;
        customerNotification = comm;
        customer = person;
    }

    @Override
    public void setTrigger(BuyOrSell trigger) {
        triggerType = trigger;
    }

    @Override
    public BuyOrSell getTriggerType() {
        return triggerType;
    }

    @Override
    public void setTriggerThreshold(float threshold) {
        priceThreshold = threshold;
    }

    @Override
    public float getTriggerThreshold() {
        return priceThreshold;
    }

    public void setCommunication(CustomerNotification comm) {
        customerNotification = comm;
    }

    @Override
    public CustomerNotification getCommunication() {
        return customerNotification;
    }

    @Override
    public Person getPerson() {
        return customer;
    }
}
