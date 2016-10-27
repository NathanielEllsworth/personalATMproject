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

    /**
     * get the amount of the transaction
     * @return the amount
     */
    public double getAmount(){
        return this.amount;
    }


    /**
     * get a string summarizing the transaction
     * @return the summary string
     */
    public String getSummaryLine(){
            // in order: returning the timestamp of the transaction, dollar amount, memo of the transaction
        if(this.amount >= 0){
            // again, two digits of precision past the decimal point
            // need to fix timestamp to just print out mm/dd/yyyy
            return String.format("%s : $%.02f : %s", this.timestamp.toString(),
                    this.amount, this.memo);

        }else{ //if it's a withdraw..
            return String.format("%s : $(%.02f) : %s", this.timestamp.toString(),
                    -this.amount, this.memo);

        }
    }

}
