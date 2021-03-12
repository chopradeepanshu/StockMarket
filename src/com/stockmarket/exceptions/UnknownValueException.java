package com.stockmarket.exceptions;

public class UnknownValueException extends Exception {
    public UnknownValueException(String errorMessage) {
        super(errorMessage);
    }
}
