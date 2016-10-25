package com.ironyard.data;

import java.util.*;

/**
 * Created by nathanielellsworth on 10/25/16.
 */
public class Transaction {

    //transaction ammount
    private double amount;

    //time transaction takes place
    private Date timestamp;

    //deposit/withdraw statement
    private String memo;

    // account where the transaction was performed
    private Account inAccount;


    /**
     * create a new transaction
     * @param amount the amount transacted
     * @param inAccount the account the transaction belongs to
     */
    public Transaction(double amount, Account inAccount) {

        this.amount = amount;
        this.inAccount = inAccount;
        //set timestamp to date object so stamp is whatever the current date it was created
        this.timestamp = new Date();
        this.memo = "";

    }

    /**
     * create a new transaction
     * @param amount the amount transacted
     * @param memo the memo for the transaction
     * @param inAccount the account the transaction belongs to
     */
    // overloading constructors
    public Transaction(double amount, String memo, Account inAccount){

        // call the two-arg constructor first
        this(amount, inAccount);

        //set the memo
        this.memo = memo;


    }


}
