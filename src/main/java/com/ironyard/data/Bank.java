package com.ironyard.data;

import java.util.*;

/**
 * Created by nathanielellsworth on 10/25/16.
 */
public class Bank {

    //Bank name
    private String name;

    //list of customers
    private ArrayList<User> users;

    //list of accounts the bank has
    private ArrayList<Account> accounts;

    //will be random strings
    public String getNewUserUUID(){

        // inits
        String uuid;
        Random rng = new Random();
        int len = 6;
        boolean nonUnique;

        // continue looping until we get a unique ID
        do{

            //generate the number
            uuid = "";
            for(int c = 0; c < len; c++){
                //casting the primitive int to the class Integer to call methods
                uuid += ((Integer)rng.nextInt(10)).toString();
            }

            // check to make sure it's unique
            // iterate through all the users
            nonUnique = false;
            for (User u : this.users){
                if(uuid.compareTo(u.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }

        }while (nonUnique);



        return uuid;

    }


    /**
     * generate a new universally unique ID for an account
     * @return the uuid
     */
    //will be random strings
    public String getNewAccountUUID();

    {

        // inits
        String uuid;
        Random rng = new Random();
        int len = 10;
        boolean nonUnique;

        // continue looping until we get a unique ID
        do {

            //generate the number
            uuid = "";
            for (int c = 0; c < len; c++) {
                //casting the primitive int to the class Integer to call methods
                uuid += ((Integer) rng.nextInt(10)).toString();
            }

            // check to make sure it's unique
            // iterate through all the users
            nonUnique = false;
            for (Account a : this.accounts) {
                if (uuid.compareTo(a.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }

        } while (nonUnique);

        return uuid;

    }


    /**
     * add an account
     * @param anAcct the account to add
     */
    public void addAccount(Account anAcct){
        this.accounts.add(anAcct);
    }


    /**
     * create a new user of the bank
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param pin the user's pin
     * @return the new User object
     */

    public User addUser(String firstName, String lastName, String pin){

        //create a new User object and add it to our list
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        // create a savings account for the user and add to User and Bank accounts lists
        Account newAccount = new Account("Savings", newUser, this);
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;

    }

    



}
