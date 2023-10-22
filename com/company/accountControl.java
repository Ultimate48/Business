package com.company;

import java.util.*;
import static com.company.Interaction.*;

/**
 *
 * <p><strong>This class is used to make accounts with different attributes and behaviours.</strong></p>
 *
 * <p><strong>This class contains everything one needs for his account.</strong>It has final fields
 * like the name, account number and the password. It also has fields like money,
 * which holds the balance, the user currently has int his account, creditScore and totalDebt,
 * which contains the total debt which the user holds based on different credit cards and normal loans.</p>
 *
 * <p>It also has to two lists, one array and one linked list. The arraylist is contains
 * the information of all the assets the user currently holds and the linked list
 * contains the last 20 bank statements of the user</p>
 *
 * <p>A account, i.e., the instance of this class is created by a constructor which takes
 * in the name, account number(which is a static class variable of class accountControl, the
 * class which controls the different accounts, the variable is an int type which starts from
 * zero and increments everytime a account is created by the addAccounts method of the
 * Interaction class) and the money, the user wants to deposit. After receiving the required data,
 * a new instance of this class is created, which randomly generates a four digit password,
 * which is printed after the account is created</p>
 *
 * <p>All the accounts that are created are stored in a ArrayList named accounts, which is
 * present in the accountControl class. This forms the basis of all the work that is
 * done related to the accounts. <strong>The index of an account in the ArrayList accounts
 * is as same as the account number of the account</strong></p>
 *
 * @author Adeesh Garg
 * @version 1.0
 * @since 2021
 * @see accountControl
 * @see Interaction
 */

class Accounts extends Object_Head{

    private final String name;

    public void setMoney(int money) {
        this.money = money;
    }

    private int money;

    public int getNumber() {
        return accountNumber;
    }

    private final int accountNumber;

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public int getPassword() {
        return password;
    }

    final private int password;

    public LinkedList<int[]> getStatement() {
        return statement;
    }

    LinkedList<int[]> statement = new LinkedList<>();

    public ArrayList<Object[]> getAssets() {
        return assets;
    }

    ArrayList<Object[]> assets = new ArrayList<>();

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    private int creditScore = 0;

    public int getTotalDebt() {
        return totalDebt;
    }

    public void setTotalDebt(int totalDebt) {
        this.totalDebt = totalDebt;
    }

    private int totalDebt = 0;

    public Accounts(String name, int money, int accountNumber) {
        this.name = name;
        this.money = money;
        this.accountNumber = accountNumber;

        int min = 1000;
        int max = 9999;
        this.password = (int)(Math.random() * (max - min + 1) + min);
    }
 }

/**
 * <p>This class is used to control the different accounts that are created by the user.
 * It has a static variable which is the index of the last account created. This is used
 * to generate the account number of the account. It also has a static ArrayList which
 * contains all the accounts that are created by the user. This is used to access the
 * account number of the account and the account itself</p>
 * <p>The class also has a method which is used to add the accounts to the ArrayList
 * and also to generate the account number of the account</p>
 * <p>The class also has a method which is used to access the account number of the
 * account and the account itself</p>
 *
 * @author Adeesh Garg
 * @version 1.0
 * @since 2021
 * @see Interaction
 * @see accountControl
 * @see Accounts
 */
public class accountControl
{
    static ArrayList<Accounts> accounts = new ArrayList<>();
    static int nextAccNumber = 0;

    /**
     * <P><Strong>This method takes in account name or number and returns
     * account number</Strong></P>
     *
     * <p>This method is a very special method. It accepts a string which can
     * be the name or the account number of the account which is being trying to
     * be accessed. It first checks that if the string is the account number by Integer
     * parsing it. If the string is found to be an integer, it returns the string in int
     * form. If there is an error while Integer parsing it, it searches for all the
     * created accounts for the name given in the string. If a match is found,
     * it returns the account number of the account number with name given in the accName variable</p>
     *
     * <p><strong>If the entered string is found to a integer, the method also
     * checks if a account number with that variable exists. Only than, the integer is returned</strong></p>
     *
     * <p>If neither the string is actually an integer on parsing, nor the name provided
     * is present in the name variable of the accounts, the following line is printed:</p>
     *
     * <p><strong><i>No such account</i></strong></p>
     *
     * <p>And the method than calls the <i>Operation</i> method of the <strong>Interaction</strong> class</p>
     *
     * @see Accounts
     * @see accountControl
     * @param accName This is the name or the account number of the account
     * @return It returns the account number of the account
     */
    @Deprecated
    static int getAccNumber(String accName)
    {
        int accNum = -1;
        try {
            accNum = Integer.parseInt(accName);
        }catch (Exception e)
        {
            for (int i = 0; i <= (accounts.size() - 1); i++)
            {
                String accNames = accounts.get(i).getName();
                if (accNames.equals(accName))
                {
                    accNum = accounts.get(i).getNumber();
                }
            }
        }
        if (accNum < 0 || accNum >= accounts.size()) {
            System.out.println("No such account");
            Operations();
        }
        return accNum;
    }

    static void moneyAvailable(int accNumber, int amount)
    {
        int creditScore = accounts.get(accNumber).getCreditScore();

        if (creditScore == 0){
            creditScore = 600;
        }
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        boolean y = accounts.get(accNumber).getMoney() >= amount;
        if (!y) {
            System.out.println("Not enough money");
            if (stackTraceElements[2].getMethodName().equals("payForProperty") ||
                    stackTraceElements[2].getMethodName().equals("payMoney")){
                creditScore -= (creditScore * 0.1);
                if (creditScore < 300){
                    creditScore = 300;
                }
            }
            accounts.get(accNumber).setCreditScore(creditScore);
            Operations();
        }

        if (stackTraceElements[2].getMethodName().equals("payForProperty") ||
                     stackTraceElements[2].getMethodName().equals("payMoney")){
            creditScore += (creditScore * 0.1);
            if (creditScore > 900){
                creditScore = 900;
            }
        }
        accounts.get(accNumber).setCreditScore(creditScore);
    }

    static void deductMoney(int accNumber, int amount)
    {
        accounts.get(accNumber).setMoney(accounts.get(accNumber).getMoney() - amount);
        accounts.get(accNumber).statement.addFirst(new int[]{0, amount});
        if(accounts.get(accNumber).statement.size() > 20){
            accounts.get(accNumber).statement.removeLast();
        }
    }

    static void addMoney(int accNumber, int amount)
    {
        accounts.get(accNumber).setMoney(accounts.get(accNumber).getMoney() + amount);
        accounts.get(accNumber).statement.addFirst(new int[]{1, amount});
        if(accounts.get(accNumber).statement.size() > 20){
            accounts.get(accNumber).statement.removeLast();
        }
    }

    static void transMoney(int accNum, int accNum2, int amount)
    {
        moneyAvailable(accNum, amount);
        deductMoney(accNum, amount);
        addMoney(accNum2, amount);
    }
}