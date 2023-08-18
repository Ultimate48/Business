package com.company;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import static com.company.assetShare.*;
import static com.company.accountControl.*;
import static com.company.loanControl.*;
import static com.company.Methods.*;

public class Interaction
{
    static Scanner sc = new Scanner(System.in);
    static int Salary;
    static int time = 0;

    public static void main(String[] args)
    {
        System.out.println("Welcome to business, best software to play the game of business");
        System.out.println("What would be the Salary of the players");
        Salary = sc.nextInt();
        while(Salary <= 0){
            System.out.println("Salary cannot be equal to or less than 0");
            System.out.println("Enter again");
            Salary = sc.nextInt();
        }
        Operations();
    }

    static void Operations()
    {
        System.out.println("Enter :");
        System.out.println("-1 to exit");
        System.out.println("0 to increase time");
        System.out.println("1 to add a account");
        System.out.println("2 to check balance");
        System.out.println("3 to add Salary");
        System.out.println("4 to transfer money");
        System.out.println("5 to Pay money");
        System.out.println("6 to check your bank statements");
        System.out.println("7 to do assets and share related works");
        System.out.println("8 to do loan related works");

        int operation = sc.nextInt();

        switch (operation) {
            case -1 -> {
                System.out.println("Thank you for playing");
                System.out.println("Bye");
                System.exit(0);
            }
            case 0 -> timeIncrease();
            case 1 -> addAccounts();
            case 2 -> checkBalance();
            case 3 -> addSalary();
            case 4 -> transferMoney();
            case 5 -> payMoney();
            case 6 -> checkBankStatements();
            case 7 -> shareWork();
            case 8 -> loanWork();
            default -> {
                System.out.println("Option wasn't int the scope");
                System.out.println("Try again");
                Operations();
            }
        }
    }

    static void shareWork()
    {
        System.out.println("Enter:");
        System.out.println("-1 to go back");
        System.out.println("1 to List an asset");
        System.out.println("2 to check valuation of an asset");
        System.out.println("3 to buy a share");
        System.out.println("4 to sell a share");
        System.out.println("5 to Pay for a property");
        System.out.println("6 to check your shares in Account");
        System.out.println("7 to check earnings of a particular share");
        System.out.println("8 to check owners of a particular asset");

        int operation = sc.nextInt();

        switch (operation) {
            case -1 -> Operations();
            case 1 -> createAsset();
            case 2 -> valuationOfAnAsset();
            case 3 -> buyShares();
            case 4 -> sellShares();
            case 5 -> payForProperty();
            case 6 -> sharesInAccount();
            case 7 -> checkEarning();
            case 8 -> owners();
            default -> {
                System.out.println("Option wasn't int the scope");
                System.out.println("Try again");
                shareWork();
            }
        }
    }

    static void loanWork()
    {
        System.out.println("Enter:");
        System.out.println("-1 to go back");
        System.out.println("1 to initiate a loan");

        int operation = sc.nextInt();

        switch (operation) {
            case -1 -> Operations();
            case 1 -> initiateLoan();
            default -> {
                System.out.println("Option wasn't int the scope");
                System.out.println("Try again");
                shareWork();
            }
        }
    }

    static void timeIncrease()
    {
        loanReCalculation();
        time++;
        Operations();
    }

    static void addAccounts()
    {
        System.out.println("Enter your name");
        String name = null;
        int money = 0;
        try {
            name = sc.next();
        }catch (Exception e)
        {
            System.out.println("Not a valid name");
            Operations();
        }
        int accNum = -1;
        for (int i = 0; i <= (accounts.size() - 1); i++)
        {
            String accNames = accounts.get(i).getName();
            if (accNames.equals(name))
            {
                accNum = accounts.get(i).getNumber();
            }
        }
        if (accNum != -1){
            System.out.println("""
                    An account with this name exits
                    Two account with same name cannot exist
                    Create account again""");
            Operations();
        }
        System.out.println("Enter the amount of money you want to deposit");
        try {
            money = sc.nextInt();
        }catch (Exception e)
        {
            System.out.println("Not a valid amount");
            Operations();
        }
        accounts.add(new Accounts(name, money, nextAccNumber));
        System.out.println("New Account created");
        System.out.println("Account number : " + nextAccNumber);
        System.out.println("Password : " + accounts.get(nextAccNumber).getPassword());
        nextAccNumber++;
        Operations();
    }

    static int accessAccount(String accName)
    {
        int accNum = Methods.getNumberFromArrayList(accounts, (accName));
        System.out.print("Enter your account password : ");
        int pass = 0;
        try {
            pass = sc.nextInt();
        }catch (Exception ignored)
        {
            System.out.println("Not a valid password");
            Operations();
        }
        if (pass != accounts.get(accNum).getPassword())
        {
            System.out.println("Wrong Password");
            Operations();
        }
        else {
            System.out.println("Account signed in");
        }
        return accNum;
    }

    static void checkBalance()
    {
        String accName = null;
        System.out.println("Enter your account name or number");
        try {
            accName = sc.next();
        }catch (Exception ignored)
        {
            System.out.println("Not a valid name o number");
            Operations();
        }
        int accNum = accessAccount(accName);
        System.out.println("Balance is "+ accounts.get(accNum).getMoney());
        Operations();
    }

    static void checkBankStatements()
    {
        System.out.println("Enter your account number for which you want to check bank statements");
        String acc = sc.next();
        int accountNum = accessAccount(acc);
        LinkedList<int[]> statement = accounts.get(accountNum).getStatement();
        System.out.println("Here are your statements:");
        for (int i = 0; i <= statement.size() - 1; i++){
            if(statement.get(i)[0] == 0){
                System.out.print("Paid : ");
            }else {
                System.out.print("Received : ");
            }
            System.out.println(statement.get(i)[1]);
        }
        Operations();
    }

    static void addSalary()
    {
        String accName = null;
        System.out.println("Enter name or number of account you want to Salary to");
        try {
            accName  = sc.next();
        }catch (Exception ignored)
        {
            System.out.println("Not a valid name or number");
            Operations();
        }
        addMoney(getNumberFromArrayList(accounts, accName), Salary);
        System.out.println("Salary successfully added");
        Operations();
    }

    static void transferMoney()
    {
        String accName = null;
        String accName2 = null;
        int amount = 0;
        System.out.println("From which account you want to transfer money");
        try {
            accName = sc.next();
        }catch (Exception ignored)
        {
            System.out.println("Not a valid name or number");
            Operations();
        }
        int accNum = accessAccount(accName);
        System.out.println("To which account you want to transfer money");
        try {
            accName2 = sc.next();
        }catch (Exception ignored)
        {
            System.out.println("Not a valid name or number");
            Operations();
        }
        int accNum2 = getNumberFromArrayList(accounts, accName2);
        System.out.println("How much amount you want to transfer");
        try{
            amount = sc.nextInt();
        }catch (Exception ignored) {
            System.out.println("Not a valid amount");
            Operations();
        }
        transMoney(accNum, accNum2, amount);
        System.out.println("Money successfully transferred");
        Operations();
    }

    static void payMoney()
    {
        String accName = null;
        int amount = 0;
        System.out.println("From which account you want to pay");
        try {
            accName = sc.next();
        }catch (Exception ignored){
            System.out.println("Not a valid account or number");
            Operations();
        }
        int accNum = accessAccount(accName);
        System.out.println("How much you have to pay");
        try {
            amount = sc.nextInt();
        }catch (Exception ignored){
            System.out.println("Not a valid amount");
            Operations();
        }
        moneyAvailable(accNum, amount);
        deductMoney(accNum, amount);
        System.out.println("Money successfully deducted");
        Operations();
    }

    static void sharesInAccount()
    {
        System.out.println("Enter your account name or number");
        String name = sc.next();
        accessAccount(name);
        System.out.println("Here are the list of your shares:");
        assetCount(name);

        Operations();
    }

    static void valuationOfAnAsset()
    {
        if (regisNumber == 0){
            System.out.println("No assets listed");
        }
        for (int i = 0; i <= regisNumber - 1; i++){
            System.out.println(assets.get(i).getName() + ":");
            System.out.println("Valuation - " + currentSharePrize(Integer.toString(i), 100));
        }
        Operations();
    }

    static void checkEarning()
    {
        System.out.print("Enter which you want to check earning of : ");
        String share = sc.next();
        System.out.println("Earning till now : " + assets.get(getNumberFromArrayList(assets, share)).getEarnings());
        Operations();
    }

    static void owners()
    {
        System.out.println("Of which share you want to check owners of");
        String share = sc.next();
        String[] owners = assets.get(getNumberFromArrayList(assets, share)).getOwners();
        System.out.println("Owners of " + share + " are:");
        for (String owner:
             owners) {
            if (owner != null) {
                System.out.println(owner + " : " + shareHolding(share, owner));
            }
        }
        Operations();
    }

    static void createAsset()
    {
        String name = null;
        int value = 0;
        int owners = 0;
        String [] owner = new String[100];
        int[] ownerShares = new int[100];


        System.out.println("Enter the name of the asset");
        try {
            name = sc.next();
        }catch (Exception ignored)
        {
            System.out.println("Not a valid name");
            Operations();
        }

        int position = -1;
        for (int i = 0; i <= (assets.size() - 1); i++)
        {
            String accNames = assets.get(i).getName();
            if (accNames.equals(name))
            {
                position = assets.get(i).getNumber();
            }
        }
        if (position != -1){
            System.out.println("""
                    An asset with this name exits
                    Two assets with same name cannot exist
                    Create asset again""");
            Operations();
        }

        System.out.println("Enter the value of the asset");
        try{
            value = sc.nextInt();
        }catch (Exception ignored) {
            System.out.println("Not a valid amount");
            Operations();
        }
        System.out.println("How many owners are their going to be of this asset");
        try{
            owners = sc.nextInt();
        }catch (Exception ignored){
            System.out.println("Not a valid number of owners");
            Operations();
        }
        String o = null;
        System.out.println("Enter their names :");
        for (int i = 0; i <= (owners - 1); i++)
        {
            try {
                o = sc.next();
            }catch (Exception ignored){
                System.out.println("Not a valid name");
                Operations();
            }
            getNumberFromArrayList(accounts, o);
            owner[i] = o;
        }
        System.out.println("Enter their shares accordingly : ");
        int total = 0;
        int share = 0;
        for (int i = 0; i <= (owners - 1); i++)
        {
            System.out.print(owner[i] + " : ");
            try {
                share = sc.nextInt();
            }catch (Exception ignored){
                System.out.println("Not a valid amount");
                Operations();
            }
            total += share;
            ownerShares[i] = share;
        }
        if (total != 100)
        {
            System.out.println("Total share percentage is not 100");
            System.out.println("Re-list the asset");
            Operations();
        }
        for (int i = 0; i <= (owners - 1); i++)
        {
            int amount = (ownerShares[i])/100 * value;
            moneyAvailable(getNumberFromArrayList(accounts, owner[i]), amount);
        }
        System.out.println("Access your account(s) : ");
        for (int i = 0; i <= (owners - 1); i++)
        {
            System.out.println(owner[i] + " with account number : " + getNumberFromArrayList(accounts, owner[i]));
            accessAccount(owner[i]);
        }
        for (int i = 0; i <= (owners - 1); i++)
        {
            int amount = ((ownerShares[i]) * value) / 100;
            deductMoney(getNumberFromArrayList(accounts, owner[i]), amount);
        }
        assets.add(new Assets(name, value, owner, ownerShares, regisNumber));
        for (int i = 0; i <= owners - 1; i++){
            String ow = assets.get(regisNumber).owners[i];
            accounts.get(getNumberFromArrayList(accounts, ow)).assets.add(new Object[]{name, shareHolding(name, ow)});
        }
        regisNumber++;
        System.out.println("Asset successfully listed");
        Operations();
    }

    static void buyShares()
    {
        String name = null;
        String share = null;
        String shareOwner = null;
        int shares = 0;
        int amount = 0;
        int pass = 0;

        System.out.println("Enter your account name or number");
        try {
            name = sc.next();
        }catch (Exception ignored)
        {
            System.out.println("Not a valid account number or name");
            Operations();
        }
        getNumberFromArrayList(accounts, name);
        System.out.println("Enter the Share Registration Number or name which you want to buy");
        try {
            share = sc.next();
        }catch (Exception ignored){
            System.out.println("Not a valid number or name");
            Operations();
        }
        getNumberFromArrayList(assets, share);
        System.out.println("These are the owners and their shares of this asset :");
        for (int i = 0; i <= ((assets.get(getNumberFromArrayList(assets, share)).owners.length) - 1); i++)
        {
            if (assets.get(getNumberFromArrayList(assets, share)).owners[i] == null) {
                continue;
            }
            System.out.print(assets.get(getNumberFromArrayList(assets, share)).owners[i] + " : ");
            System.out.println(assets.get(getNumberFromArrayList(assets, share)).ownerShares[i]);
        }
        System.out.println("From whom would you like to buy shares?");
        try {
            shareOwner = sc.next();
        }catch (Exception ignored){
            System.out.println("Not a valid name");
            Operations();
        }
        getShareOwnerPosition(shareOwner, share);
        System.out.println("How much percentage of asset you want to buy");
        try {
            shares = sc.nextInt();
        }catch (Exception ignored){
            System.out.println("Not a valid number");
            Operations();
        }
        if (shareHolding(share, shareOwner) < shares)
        {
            System.out.println("Selected owner doesn't have enough shares");
            Operations();
        }
        System.out.println("For how much you want to buy");
        System.out.println("The current prize for " + shares + "% shares is " + currentSharePrize(share, shares));
        try {
            amount = sc.nextInt();
        }catch (Exception ignored){
            System.out.println("Not a valid amount");
            Operations();
        }
        System.out.println("Hey " + shareOwner + " please enter your correct password " +
                "if you would like to sell " + shares + "% of your " + share + " shares to " + name + " for " + amount);
        try {
            pass = sc.nextInt();
        }catch (Exception ignored)
        {
            System.out.println("Not a valid number");
            System.out.println("Deal Cancelled");
            Operations();
        }
        if (pass != accounts.get(getNumberFromArrayList(accounts, shareOwner)).getPassword())
        {
            System.out.println("Password incorrect");
            System.out.println("Deal cancelled");
            Operations();
        }

        System.out.println("To confirm " + name + " please");
        try {
            transMoney(accessAccount(name), getNumberFromArrayList(accounts, shareOwner), amount);
        }catch (Exception ignored){}
        deductShare(share, shareOwner, shares);
        addShare(share, name, shares);

        int value = ((assets.get(getNumberFromArrayList(assets, share)).getValue() / 100) * (100 - shares)) + amount;
        assets.get(getNumberFromArrayList(assets, share)).setValue(value);

        System.out.println("Deal successful");
        Operations();
    }

    static void sellShares()
    {
        String name = null;
        String share;
        String buyer;
        int shares;
        int amount;

        System.out.println("Enter your account name or number");
        try {
            name = sc.next();
        }catch (Exception ignored)
        {
            System.out.println("Not a valid account number or name");
            Operations();
        }
        accessAccount(name);
        getNumberFromArrayList(accounts, name);
        System.out.println("You have the following shares:");
        ArrayList<Object[]> a = assetCount(name);
        System.out.println("Which one would you like to sell");
        share = sc.next();
        int c = 0;
        for (int i = 0; i <= a.size() - 1; i++) {
            if (a.get(i)[0].toString().equals(share)){
                c = 1;
                break;
            }
        }
        if (c == 0){
            System.out.println("No such share");
            System.out.println("Start again");
            Operations();
        }
        System.out.println("How much would you like to sell. You have " + shareHolding(share, name) + "% of " + share);
        shares = sc.nextInt();
        if(shares == 0 || shares > shareHolding(share, name)){
            System.out.println("Invalid amount");
            System.out.println("Start again");
            Operations();
        }
        System.out.println("For what prize would you like to sell these shares");
        System.out.println("The current prize of these shares are " + currentSharePrize(share, shares));
        amount = sc.nextInt();
        System.out.println("Hey would anyone would like to purchase "+ shares + "% share of " + share + " from " + name + " for " + amount);
        System.out.println("Please enter your name or account number, anyone who would like to purchase");
        buyer = sc.next();
        getNumberFromArrayList(accounts, buyer);
        System.out.println("Please " + buyer + ":");
        accessAccount(buyer);
        try {
            transMoney(getNumberFromArrayList(accounts, buyer), getNumberFromArrayList(accounts, name), amount);
        }catch (Exception ignored){}
        deductShare(share, name, shares);
        addShare(share, buyer, shares);

        int value = ((assets.get(getNumberFromArrayList(assets, share)).getValue() / 100) * (100 - shares)) + amount;
        assets.get(getNumberFromArrayList(assets, share)).setValue(value);

        System.out.println("Deal successful");
        Operations();
    }

    static void payForProperty()
    {
        String acc = null;
        String asset = null;
        int amount = 0;
        System.out.println("Enter your account number or name");
        try {
            acc = sc.next();
        }catch (Exception e){
            System.out.println("Invalid account name or number");
            Operations();
        }
        getNumberFromArrayList(accounts, acc);
        accessAccount(acc);
        System.out.println("Enter to which asset you want to pay");
        try {
            asset = sc.next();
        }catch (Exception e){
            System.out.println("Invalid asset name");
            Operations();
        }
        getNumberFromArrayList(assets, asset);
        System.out.println("Enter the amount");
        try {
            amount = sc.nextInt();
            if (amount <= 0){
                try {
                    throw new ArithmeticException("Amount cannot be less than or equal to zero");
                }catch (ArithmeticException e){
                    System.out.println("No negative integers allowed");
                    Operations();
                }
            }
        }catch (Exception e){
            System.out.println("Invalid amount");
            Operations();
        }
        moneyAvailable(getNumberFromArrayList(accounts, acc), amount);
        deductMoney(getNumberFromArrayList(accounts, acc), amount);
        assets.get(getNumberFromArrayList(assets, asset)).setEarnings(assets.get(getNumberFromArrayList(assets, asset)).getEarnings() + amount);
        for (int i = 0; i < assets.get(getNumberFromArrayList(assets, asset)).owners.length; i++) {
            if (assets.get(getNumberFromArrayList(assets, asset)).owners[i] == null){
                continue;
            }
            int account = getNumberFromArrayList(accounts, assets.get(getNumberFromArrayList(assets, asset)).owners[i]);
            int a = ((int) (((assets.get(getNumberFromArrayList(assets, asset)).ownerShares[i]) / 100.0) * amount));
            addMoney(account, a);
        }
        System.out.println("Money paid");
        Operations();
    }

    static void initiateLoan()
    {
        System.out.println("Please enter you account name or number");
        String account = sc.next();
        accessAccount(account);
        System.out.println("How much loan would you like to have");
        int amount = sc.nextInt();
        System.out.println("For how much time in months");
        int time = sc.nextInt();
        boolean isAvailable;
        System.out.println("Do you have a mortgage to give");
        System.out.println("Enter 1 if you have a mortgage to give");
        System.out.println("Or any other number if you don't");
        int available = sc.nextInt();
        isAvailable = available == 1;
        String asset = null;
        int percentage = 0;
        if (isAvailable) {
            System.out.println("You have the following assets :");
            ArrayList<Object[]> a = assetCount(account);
            System.out.println("Which one would you like to give as a collateral");
            asset = sc.next();
            getNumberFromArrayList(assets, asset);
            int c = 0;
            for (int i = 0; i <= a.size() - 1; i++) {
                if (a.get(i)[0].toString().equals(asset)) {
                    c = 1;
                    break;
                }
            }
            if (c == 0) {
                System.out.println("No such asset in your account");
                System.out.println("Start again");
                Operations();
            }
            System.out.println("You have " + shareHolding(asset, account) + "% of " + asset + " shares");
            System.out.println("How much would you like to put for collateral");
            percentage = sc.nextInt();
            System.out.println("That would amount to " + currentSharePrize(asset, percentage));
        }
        double interestRate;
        if (!isAvailable){
            interestRate = interestCalculate(account, amount, 0);
        }else{
            interestRate = interestCalculate(account, amount, currentSharePrize(asset, percentage));
        }
        if (interestRate > 55){
            System.out.println("Sorry this loan cannot be provided for it is too risky");
            Operations();
        }
        System.out.println("With all that you can have a loan with a interest rate of " + interestRate);
        System.out.println("The monthly EMI would be " + calculateEMI(amount, interestRate, time) + "for " + time + " months");
        System.out.println("The total amount would be " + (calculateEMI(amount, interestRate, time) * time));
        System.out.println("Press 1 to finalize the loan or any other number to leave");
        int choice = sc.nextInt();
        if (choice != 1){
            System.out.println("Loan initiation cancelled");
            Operations();
        }
        if (isAvailable){
            loans.add(new Loan(account, time, amount, loanNum, calculateEMI(amount, interestRate, time),  asset, percentage, interestRate));
        }else{
            loans.add(new Loan(account, time, amount, loanNum, calculateEMI(amount, interestRate, time), interestRate));
        }

        accounts.get(getNumberFromArrayList(accounts, account)).setMoney(accounts.get(getNumberFromArrayList(accounts, account)).getMoney() + amount);
        accounts.get(getNumberFromArrayList(accounts, account)).setTotalDebt(accounts.get(getNumberFromArrayList(accounts, account)).getTotalDebt() + amount);
        if(isAvailable) {
            deductShare(asset, account, percentage);
        }
        System.out.println("The loan has been initiated");
        loanNum++;
        Operations();
    }


}