package Business.com.company;

import java.util.ArrayList;
import java.util.LinkedList;

import static Business.com.company.accountControl.*;
import static Business.com.company.assetShare.*;
import static Business.com.company.Interaction.*;
import static Business.com.company.Methods.*;

class Loan {
    final String account;
    int time;
    final double interest;
    final int principle;
    final int loanNum;
    String Mortgage = null;
    int mortgagePercent;
    int EMI;
    int actualAmount;

    public Loan(String account, int time, int principle, int loanNum, int EMI,
                String mortgage, int mortgagePercent, double interest) {
        this.account = account;
        this.time = time;
        this.principle = principle;
        this.loanNum = loanNum;
        this.Mortgage = mortgage;
        this.mortgagePercent = mortgagePercent;
        this.interest = interest;
        this.EMI = EMI;
        this.actualAmount = principle;
    }

    public Loan(String account, int time, int principle, int loanNum, int EMI, double interest) {
        this.account = account;
        this.time = time;
        this.principle = principle;
        this.loanNum = loanNum;
        this.interest = interest;
        this.EMI = EMI;
        this.actualAmount = principle;
    }
}

public class loanControl {
    static ArrayList<Loan> loans = new ArrayList<>();
    static int loanNum = 0;

    static double interestCalculate(String account, int amount, int mortgageAmount)
    {
        double actualInterest = 7.0;

        LinkedList<int[]> statement = accounts.get(getNumberFromArrayList(accounts, account)).getStatement();

        if (statement.size() == 0){
            System.out.println("Sorry you have no past bank statements" +
                    ". Therefore, you can't get a loan");
            Operations();
        }

        //Changes interest rate according to credit score
        int creditScore = arrangeCreditScore(account);
        int x = creditScore - 600;
        if (x > 0){
            actualInterest -= ((x/10.0)/100) * actualInterest;
        }else if (x < 0){
            actualInterest += ((Math.abs(x)/5.0)/100) * actualInterest;
        }

        //Changes interest rate according to provided mortgage
        double leastMortgage = 0.45 * amount;
        int a = (int) ((mortgageAmount * 100.0) / leastMortgage);
        if (mortgageAmount <= leastMortgage){
            actualInterest += (((100.0 - a)/2)/100) * actualInterest;
        }else {
            actualInterest -= (((a - 100.0)/2)/100) * actualInterest;
        }

        int totalAssetsValues = accounts.get(getNumberFromArrayList(accounts, account)).getMoney()/5;

        if(accounts.get(getNumberFromArrayList(accounts, account)).assets.size() != 0){
        for (int i = 0; i <= accounts.get(getNumberFromArrayList(accounts, account)).assets.size() - 1; i++){
            totalAssetsValues += currentSharePrize(accounts.get(getNumberFromArrayList(accounts, account)).assets.get(i)[0].toString(),
                    (Integer) accounts.get(getNumberFromArrayList(accounts, account)).assets.get(i)[1]);
        }
    }

        if (totalAssetsValues >= amount){
            int z = totalAssetsValues - amount;
            int y = ((z/amount)*100)/2;
            actualInterest -= (y/100.0) * actualInterest;
        }else {
            if(totalAssetsValues == 0){
                totalAssetsValues = 1;
            }
            int z = amount - totalAssetsValues;
            int y = ((z/totalAssetsValues)*100);
            actualInterest += (y/100.0)*actualInterest;
        }

        actualInterest = round(actualInterest);

        if (actualInterest < 3.0){
            actualInterest = 3.0;
        }

        return actualInterest;
    }

    static int calculateEMI(int principle, double interestRate, double timeInMonths)
    {
        double EMI;
        double amount = principle*Math.pow(((1 + (interestRate / (100 * 12) ))), (12 * timeInMonths / 12));
        EMI = amount/timeInMonths;
        return (int)EMI;
    }

    static int arrangeCreditScore(String account)
    {
        int creditScore = accounts.get(getNumberFromArrayList(accounts, account)).getCreditScore();

        if (creditScore > 0) {
            return creditScore;
        }

        creditScore = 600;

        int paid = 0, paidN = 0;
        int received = 0, receivedN = 0;

        for(int i = 0; i <= 9; i++){
            if (accounts.get(getNumberFromArrayList(accounts, account)).statement.get(i)[0] == 1){
                receivedN++;
                received += accounts.get(getNumberFromArrayList(accounts, account)).statement.get(i)[1];
            }else {
                paidN++;
                paid += accounts.get(getNumberFromArrayList(accounts, account)).statement.get(i)[1];
            }
        }

        if (paid >= received){
            int a = paid - received;
            int b;
            if(received == 0){
                received = 1;
            }
            if (paidN >= receivedN){
                b = ((a/received)*100)*2;
            }else{
                b = (a/received)*100;
            }
            creditScore -= (b/100.0)*creditScore;
        }else{
            if(paid == 0){
                paid = 1;
            }
            int a = received - paid;
            int b;
            if (receivedN >= paidN){
                b = ((a/paid)*100)*2;
            }else{
                b = (a/paid)*100;
            }
            creditScore += (b/100.0)*creditScore;
        }

        if(creditScore > 900){
            creditScore = 900;
        }else if (creditScore < 300){
            creditScore = 300;
        }

        accounts.get(getNumberFromArrayList(accounts, account)).setCreditScore(creditScore);

        return creditScore;
    }

    static void loanReCalculation(){
        ArrayList<Integer> closedLoans = new ArrayList<>();
        for(int i = 0; i <= loans.size() - 1; i++){
            int compound = (int)(loans.get(i).actualAmount * (loans.get(i).interest/100*12));
            loans.get(i).actualAmount += compound;
            var account = loans.get(i).account;
            if (loans.get(i).actualAmount >= loans.get(i).EMI) {
                deductMoney(getNumberFromArrayList(accounts, account), loans.get(i).EMI);
                loans.get(i).actualAmount -= loans.get(i).EMI;
                System.out.println("Loan " + loans.get(i).loanNum + " EMI paid");
            }else{
                deductMoney(getNumberFromArrayList(accounts, account), loans.get(i).actualAmount);
                loans.get(i).actualAmount = 0;
            }
            loans.get(i).time--;
            if(loans.get(i).actualAmount == 0){
                closedLoans.add(loans.get(i).loanNum);
            }
        }
        for(int i = 0; i <= closedLoans.size() - 1; i++){
            if(loans.get(closedLoans.get(i)).Mortgage != null) {
                var mortgage = loans.get(closedLoans.get(i)).Mortgage;
                var m_percent = loans.get(closedLoans.get(i)).mortgagePercent;
                addShare(mortgage, loans.get(closedLoans.get(i)).account, m_percent);
            }
            System.out.println("Loan numbered " + loans.get(closedLoans.get(i)).loanNum + "closed");
            //noinspection SuspiciousMethodCalls
            loans.remove(closedLoans.get(i));
        }
    }
}