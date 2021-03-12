package com.stockmarket.models;

import com.stockmarket.exceptions.UnknownValueException;

public interface Person {
    String getName();

    String getCustId();

    String getEmail() throws UnknownValueException;

    void setEmail(String email);

    long getPhoneNumber() throws UnknownValueException;

    void setPhoneNumber(long phoneNumber);
}
