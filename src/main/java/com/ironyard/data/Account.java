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

}
