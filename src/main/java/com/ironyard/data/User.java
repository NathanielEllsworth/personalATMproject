package com.ironyard.data;

import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.security.MessageDigest;

/**
 * Created by nathanielellsworth on 10/25/16.
 */
public class User {

    // user first name
    private String firstName;

    // user last name
    private String lastName;

    // universal unique identifier
    private String uuid;

    //store pin number of the user using hash MD5
    private byte pinHash[];

    // each user will have a list of accounts. Accounts will be encapsulated
    private ArrayList<Account> accounts;

    /**
     * Create a new user
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param pin the user's account pin number
     * @param theBank the Bank object that the user is a customer of
     */

    public User (String firstName, String lastName, String pin, Bank theBank){


        // set user's name
        this.firstName = firstName;
        this.lastName = lastName;

        // store pin's MD5 hash, rather than the original value for security reasons

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            //getting the memory, (the bytes of the pin object) then taking those bytes and digesting them through the MD5 algorithm
            //which then returns a different array of bytes that will then be stored in pin hash
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        //get a new, universal unique ID for the user
        this.uuid = theBank.getNewUserUUID();

        // create empty list of accounts
        this.accounts = new ArrayList<Account>();

        //print out log message
        System.out.printf("New user %s, %s with ID %s created.\n", lastName, firstName, this.uuid);

    }

    /**
     * add an account for the user
     * @param anAcct the account to add
     */
    public void addAccount(Account anAcct){
        this.accounts.add(anAcct);
    }

    /**
     * return the user's uuid
     * @return the uuid
     */
    public String getUUID(){
        return this.uuid;
    }

    /**
     * check whether a given pin matches the true User pin
     * @param aPin the pin to check
     * @return whether the pin is valid or not
     */

    public boolean validatePin(String aPin){

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            //same thing as above, getting the memory, (the bytes of the pin object) then taking those bytes and digesting them through the MD5 algorithm
            //which then returns a different array of bytes that will then be stored in pin hash
            return MessageDigest.isEqual(md.digest(aPin.getBytes()),
                    this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);;
        }

        return false;

    }

    /**
     * return the user's first name.
     * @return the first name
     */

    public String getFirstName(){
        return this.firstName;
    }


    /**
     * print summaries for the accounts of this user.
     */
    //going to print the transactions for all of the accounts and their balances
    public void printAccountsSummary(){

        //%d is int %s is string

        System.out.printf("\n\n%s's accounts summary\n", this.firstName);
        for(int a = 0; a < this.accounts.size(); a++){
            // asking each account class to generate it's own account line
            System.out.printf("  %d) %s\n", a+1, //most people are use to counting starting from one
                    this.accounts.get(a).getSummaryLine());

        }
        System.out.println();

    }

    /**
     * get the number of accounts of the user
     * @return the number of accounts
     */
    public int numAccounts(){
        return this.accounts.size();
    }


    /**
     * print transaction history for a particular account
     * @param acctIdx acctIdx the index of the account to use
     */
    public void printAcctTransHistory(int acctIdx){ //getting the account index to print
        this.accounts.get(acctIdx).printTransHistory();


    }

    /**
     * get the balance of a particular account
     * @param acctIdx the index of the account to use
     * @return the balance of the account
     */
    public double getAcctBalance(int acctIdx){
        return this.accounts.get(acctIdx).getBalance();

    }

    /**
     * get the UUID of a particular account
     * @param acctIdx the index of the account to use
     * @return the UUID of the account
     */
    public String getAcctUUID(int acctIdx){
        return this.accounts.get(acctIdx).getUUID();
    }


    /**
     * add a transaction to a particular account
     * @param acctIdx the index of the account
     * @param amount the amount of the transaction
     * @param memo the memo of the transaction
     */
    public void addAcctTransaction(int acctIdx, double amount, String memo){
        //getting a particular account here then adding a transaction
        this.accounts.get(acctIdx).addTransaction(amount, memo);
    }


}
