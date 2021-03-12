package com.stockmarket.models;

import com.stockmarket.data.CustomerNotification;
import com.stockmarket.data.BuyOrSell;

public interface StockTriggers {
    void setTrigger(BuyOrSell trigger);

    BuyOrSell getTriggerType();

    void setTriggerThreshold(float threshold);

    float getTriggerThreshold();

    void setCommunication(CustomerNotification comm);

    CustomerNotification getCommunication();

    Person getPerson();
}
