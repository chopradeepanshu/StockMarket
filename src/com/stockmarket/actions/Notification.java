package com.stockmarket.actions;

import com.stockmarket.data.CustomerNotification;
import com.stockmarket.data.BuyOrSell;
import com.stockmarket.exceptions.UnknownValueException;
import com.stockmarket.models.StockAlerts;
import com.stockmarket.models.FinancialInstrument;
import com.stockmarket.models.Person;
import com.stockmarket.models.StockTriggers;

public class Notification implements StockAlerts {

    @Override
    public boolean sendNotification(Person p, FinancialInstrument i, StockTriggers t) {
        if (t.getCommunication() == CustomerNotification.Email) {
            try {
                System.out.printf("Sending notification to %s", p.getEmail());
            } catch (UnknownValueException ex) {
                System.out.println(ex.getMessage());
                return false;
            }
        } else {
            try {
                System.out.printf("Sending notification to %s", p.getPhoneNumber());
            } catch (UnknownValueException ex) {
                System.out.println(ex.getMessage());
                return false;
            }
        }
        System.out.printf("Hi %s, Value of Stock %s has gone %s %n", p.getName(), i.getName(),
                t.getTriggerType() == BuyOrSell.BuyTrigger ? "down!" : "up");
        return true;
    }
}
