package com.stockmarket.data;

import com.stockmarket.exceptions.UnknownValueException;
import com.stockmarket.models.Person;

public class User implements Person {
    final String userName;
    final String userID;
    String emailID;
    long custPhoneNumber;

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userId='" + userID + '\'' +
                ", emailId='" + (emailID == null ? "NA" : emailID) + '\'' +
                ", userPhoneNumber=" + ((custPhoneNumber == -1) ? "NA" : custPhoneNumber) +
                '}';
    }


    public User(String name, String id, String email) {
        userName = name;
        userID = id;
        emailID = email;
        custPhoneNumber = -1;
    }

    public User(String name, String id, long phone) {
        userName = name;
        userID = id;
        emailID = null;
        custPhoneNumber = phone;
    }

    public User(String name, String id, String email, long phone) {
        userName = name;
        userID = id;
        emailID = email;
        custPhoneNumber = phone;
    }

    @Override
    public String getName() {
        return userName;
    }

    @Override
    public String getCustId() {
        return userID;
    }

    @Override
    public String getEmail() throws UnknownValueException {
        if (emailID == null)
            throw new UnknownValueException("Email ID was not provided by the customer");
        return emailID;
    }

    @Override
    public void setEmail(String email) {
        emailID = email;
    }

    @Override
    public long getPhoneNumber() throws UnknownValueException {
        if (custPhoneNumber < 0)
            throw new UnknownValueException("No Number provided");
        return custPhoneNumber;
    }

    @Override
    public void setPhoneNumber(long phoneNumber) {
        custPhoneNumber = phoneNumber;
    }
}
