package com.stockmarket.models;

import com.stockmarket.exceptions.UnknownValueException;

public interface StockAlerts {
    boolean sendNotification(Person p, FinancialInstrument i, StockTriggers t) throws UnknownValueException;
}
