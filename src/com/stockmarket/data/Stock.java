package com.stockmarket.data;

import com.stockmarket.models.FinancialInstrument;

public class Stock implements FinancialInstrument {
    final String stockName;
    float stockPrice;
    final String stockID;

    @Override
    public String toString() {
        return "Stock{" +
                "stockName='" + stockName + '\'' +
                ", stockPrice=" + stockPrice +
                ", stockId='" + stockID + '\'' +
                '}';
    }

    public Stock(String name, String id, float price) {
        stockPrice = price;
        stockName = name;
        stockID = id;
    }

    @Override
    public float getPrice() {
        return stockPrice;
    }

    @Override
    public void setPrice(float price) {
        stockPrice = price;
    }

    @Override
    public String getName() {
        return stockName;
    }

    @Override
    public String getStockId() {
        return stockID;
    }
}
