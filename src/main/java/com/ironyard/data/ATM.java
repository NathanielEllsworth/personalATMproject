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

        //Login prompt
        User curUser;
        while (true){


            //stay in the login prompt until successful login
            curUser = ATM.mainMenuPrompt(theBank, sc);

            //stay in main menu until user quits
            ATM.printUserMenu(curUser, sc);

        }


    }

    /**
     * print the ATM's login menu
     * @param theBank the Bank object whose accounts to use
     * @param sc the Scanner object to use for user input
     * @return the authenticated User object
     */
    public static User mainMenuPrompt(Bank theBank, Scanner sc){

        //inits
        String userID;
        String pin;
        // authenticate user
        User authUser;

        // prompt the user for user ID/pin combo until a correct one is reached
        do{

            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            //you can see the pin for the the user stays the same but the user ID is always random
            System.out.print("Enter user ID: ");
            userID = sc.nextLine();
            System.out.print("Enter pin: ");
            pin = sc.nextLine();

            // try to get the user object corresponding to the ID and pin combination
            //returns user if it is a correct user, returns null otherwise
            authUser = theBank.userLogin(userID, pin);
            if (authUser == null){
                //now remember, you don't want to say "your user ID is wrong but your pin is correct" because if it's not the user
                //now they know which one to get. that's why you normally say "The combination is incorrect"
                System.out.println("Incorrect user ID/pin combination." +
                            "please try again.");
            }


        }while(authUser == null); // continue looping until successful login. (in the future implement a 'time out' session after so many attempts)

        return authUser;


    }

    public static void printUserMenu(User theUser, Scanner sc){

        //print a summary of the user's accounts (from my personal experience)
        theUser.printAccountsSummary();

        // init
        int choice;

        //user menu
        do{
            System.out.printf("Welcome %s, what would you like to do?",
                    theUser.getFirstName());

            System.out.println(" 1) Show account transaction history");
            System.out.println(" 2) Withdrawal");
            System.out.println(" 3) Deposit");
            System.out.println(" 4) Transfer"); // which is just a withdrawal and a deposit bundled together
            System.out.println(" 5) Quit");
            System.out.println();
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            // since this will be on a number pad or touch screen so this will not send an error if they enter a letter B,b, etc
            // will need to be implemented later if need be
            if (choice < 1 || choice > 5){
                System.out.println("Invalid choice. Please select options 1 through 5");

            }


        } while (choice < 1 || choice > 5);

        // process the choice
        switch (choice) {

            case 1:
                ATM.showTransHistory(theUser, sc);
                break;
            case 2:
                ATM.withdrawalFunds(theUser, sc);
                break;
            case 3:
                ATM.depositFunds(theUser, sc);
                break;
            case 4:
                ATM.transferFunds(theUser, sc);
                break;

        }

        // redisplay this menu unless the user wants to quit
        if (choice != 5){
            //I could have also looped the entire thing above and skipped this part, another way of doing it.
            //make recursive call (calling 'print user menu' inside of 'print user menu' which is called recursion)
            ATM.printUserMenu(theUser, sc);
        }


    }




}
