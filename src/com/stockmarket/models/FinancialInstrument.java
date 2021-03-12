package com.stockmarket.models;

public interface FinancialInstrument {
    float getPrice();

    void setPrice(float price);

    String getName();

    String getStockId();
}
