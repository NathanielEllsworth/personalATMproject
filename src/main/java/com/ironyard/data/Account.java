package com.ironyard.data;

import java.util.*;

/**
 * Created by nathanielellsworth on 10/25/16.
 */
public class Account {

    // name of account: checking, saving, etc
    private String name;

    //in the future I want to add the account Balance

    // account id number
    private String uuid;

    // the user account owner
    private User holder;

    // list of transactions for this account
    private ArrayList<Transaction> transactions;

    /**
     * Create a new Account
     * @param name the name of the Account
     * @param holder the User object that holds the account
     * @param theBank the bank that issues the account
     */
    public Account(String name, User holder, Bank theBank){


        //set the account name and holder
        this.name = name;
        this.holder = holder;

        // get new account UUID
        this.uuid = theBank.getNewAccountUUID();


        // init transactions
        this.transactions = new ArrayList<Transaction>();



    }


    /**
     * get the account ID
     * @return the uuid
     */
    public String getUUID(){
        return this.uuid;
    }


    /**
     * get summary line for the account
     * @return the string summary
     */
    public String getSummaryLine(){

        //get the account's balance
        double balance = this.getBalance();

        //banks don't like to use negative numbers so they use red or enclose negative numbers in parentheses
        //so formatting the summary line depending on whether the balance is
        //negative (overdrawn)
        if(balance >= 0){
            //f = a floating point number like a double with two digits of precision after the decimal point
            // so in order I'm going to print the account ID, the account balance, and then the account name
            // also I know I'm hardcoding in dollars but accepting other currencies is a whole other project
            return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);

        }else{ //if it's a negative number same thing but the balance is in parentheses
            return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.name);

        }

    }


    /**
     * get the balance of this account by adding the amounts of the transactions
     * @return the balance value
     */
    public double getBalance(){

        //this is basically looping through all of the transactions and adding up the amounts
        double balance = 0;
        for (Transaction t : this.transactions){
            balance += t.getAmount();
        }
        return balance;



    }

}
