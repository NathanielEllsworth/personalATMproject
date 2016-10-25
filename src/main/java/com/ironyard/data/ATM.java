package com.ironyard.data;

import java.util.*;

/**
 * Created by nathanielellsworth on 10/25/16.
 */
public class ATM {

    public static void main(String[] args){

        // init Scanner
        Scanner sc = new Scanner(System.in);

        // init Bank
        Bank theBank = new Bank("Bank of Nate");

        //add a user, which also creates a savings account
        User aUser = theBank.addUser("John", "Doe", "1234");

        //add a checking account for the user
        Account newAccount = new Account("Checking", aUser, theBank);

        //add account to the user
        aUser.addAccount(newAccount);

        //also add it to the bank
        theBank.addAccount(newAccount);


    }

}
