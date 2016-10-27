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
            System.out.printf("Welcome %s, what would you like to do?\n",
                    theUser.getFirstName());

            System.out.println("  1) Show account transaction history");
            System.out.println("  2) Withdraw");
            System.out.println("  3) Deposit");
            System.out.println("  4) Transfer"); // which is just a withdraw and a deposit bundled together
            System.out.println("  5) Quit");
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
                ATM.withdrawFunds(theUser, sc);
                break;
            case 3:
                ATM.depositFunds(theUser, sc);
                break;
            case 4:
                ATM.transferFunds(theUser, sc);
                break;
            case 5:
                // gobble up the rest of the previous input line
                sc.nextLine();
                break;

        }

        // redisplay this menu unless the user wants to quit
        if (choice != 5){
            //I could have also looped the entire thing above and skipped this part, another way of doing it.
            //make recursive call (calling 'print user menu' inside of 'print user menu' which is called recursion)
            ATM.printUserMenu(theUser, sc);
        }

    }


    /**
     * show the transaction history for an account
     * @param theUser the logged-in User object
     * @param sc the Scanner object used for user input
     */
    public static void showTransHistory(User theUser, Scanner sc){

        int theAcct;

        //get account whos transaction history to look at
        do{
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "whose transactions you want to see: ",
                    theUser.numAccounts());
            theAcct = sc.nextInt()-1; //we want the value to be whatever is minus 1 since we start with zero base indexing
            if (theAcct < 0 || theAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");

            }

        }while (theAcct < 0 || theAcct >= theUser.numAccounts());

        //once we have the account we're going to print the transaction history
        theUser.printAcctTransHistory(theAcct);

    }


    /**
     * process transferring funds from one account to another
     * @param theUser the logged-in User object
     * @param sc the Scanner object used for user input
     */
    public static void transferFunds(User theUser, Scanner sc){
    //need to get which funds are being transferred from, which funds are being transferred to, and the amount

        // inits
        int fromAcct;
        int toAcct;
        double amount;
        // storing account balance because you can't withdraw too much from one account
        double acctBal;


        //get the account to transfer from
        do{
            System.out.printf("Enter the number (1-%d) of the account\n" +
            "to transfer from: ", theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        //want to keep track of the maximum amount you can withdraw
        acctBal = theUser.getAcctBalance(fromAcct);


        //now want to get the account to transfer to.

        do{
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to transfer to: ", theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());


        //now want to get the amount to transfer

        do{ // reminder that the max amount transfer is the balance of the account
            System.out.printf("Enter the amount to transfer (max $%.02f): $",
                    acctBal);
            amount = sc.nextDouble();
            if (amount < 0){
                System.out.println("Amount must be greater than zero.");
            } else if (amount > acctBal){
                System.out.printf("Amount must not be greater than\n" +
                        "balance of $%.02f.\n", acctBal);
            }
        } while (amount < 0 || amount > acctBal);


        // finally, do the transfer

        //subtracting money from the account
        theUser.addAcctTransaction(fromAcct, -1*amount, String.format(
                "Transfer to account %s", theUser.getAcctUUID(toAcct)));
        //two transfers
        //adding money to the other account
        theUser.addAcctTransaction(toAcct, amount, String.format(
                "Transfer to account %s", theUser.getAcctUUID(fromAcct)));

    }


    /**
     * process a fund withdraw from an account
     * @param theUser the logged-in User object
     * @param sc the Scanner object user for user input
     */
    public static void withdrawFunds(User theUser, Scanner sc){

        // inits
        int fromAcct;
        double amount;
        double acctBal;
        String memo;


        //get the account to transfer from
        do{
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to withdraw from: ", theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        //want to keep track of the maximum amount you can withdraw
        acctBal = theUser.getAcctBalance(fromAcct);


        //now want to get the amount to transfer

        do{ // reminder that the max amount transfer is the balance of the account
            System.out.printf("Enter the amount to withdraw (max $%.02f): $",
                    acctBal);
            amount = sc.nextDouble();
            if (amount < 0){
                System.out.println("Amount must be greater than zero.");
            } else if (amount > acctBal){
                System.out.printf("Amount must not be greater than\n" +
                        "balance of $%.02f.\n", acctBal);
            }
        } while (amount < 0 || amount > acctBal);


        // gobble up the rest of the previous input line
        sc.nextLine();



        //get a memo
        System.out.print("Enter a memo: ");
        memo = sc.nextLine();



        //do the withdraw
        theUser.addAcctTransaction(fromAcct, -1*amount, memo);

    }


    /**
     * process a fund deposit to an account
     * @param theUser the logged-in User object
     * @param sc the Scanner object used for user input
     */
    public static void depositFunds(User theUser, Scanner sc){


        // inits
        int toAcct;
        double amount;
        double acctBal;
        String memo;


        //get the account to transfer from
        do{
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to deposit in: ", theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());
        //want to keep track of the maximum amount you can withdraw
        acctBal = theUser.getAcctBalance(toAcct);


        //now want to get the amount to transfer

        do{ // reminder that the max amount transfer is the balance of the account
            System.out.printf("Enter the amount to transfer (max $%.02f): $",
                    acctBal);
            amount = sc.nextDouble();
            if (amount < 0){
                System.out.println("Amount must be greater than zero.");
            }
        } while (amount < 0);


        // gobble up the rest of the previous input line
        sc.nextLine();



        //get a memo
        System.out.print("Enter a memo: ");
        memo = sc.nextLine();



        //do the withdraw
        theUser.addAcctTransaction(toAcct, amount, memo);


    }




}
