package Business.com.company;

import java.util.ArrayList;

import static Business.com.company.accountControl.*;
import static Business.com.company.Interaction.*;
import static Business.com.company.Methods.*;

class Assets extends Object_Head
{
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getNumber() {
        return regisNumber;
    }

    String name;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    int value;

    public String[] getOwners() {
        return owners;
    }

    String [] owners;
    int [] ownerShares;
    int regisNumber;

    public void setEarnings(int earnings) {
        this.earnings = earnings;
    }

    public int getEarnings() {
        return earnings;
    }

    int earnings;

    public Assets(String name, int value, String[] owners, int[] ownerShares, int regisNumber) {
        this.name = name;
        this.value = value;
        this.owners = owners;
        this.ownerShares = ownerShares;
        this.regisNumber = regisNumber;
        this.earnings = 0;
    }
}

public class assetShare
{
    static ArrayList <Assets> assets = new ArrayList<>();
    static int regisNumber = 0;

    static int currentSharePrize(String share, int percentage)
    {
        int shareNumber = getNumberFromArrayList(assets, share);
        return (int)(assets.get(shareNumber).getValue() * (percentage/100.0));
    }

    @Deprecated
    static int getShareNumber(String shareName)
    {
        int shareNum = -1;
        try {
            shareNum = Integer.parseInt(shareName);
        }catch (Exception e)
        {
            for (int i = 0; i <= (assets.size() - 1); i++)
            {
                String accNames = assets.get(i).getName();
                if (accNames.equals(shareName))
                {
                    shareNum = assets.get(i).getNumber();
                }
            }
        }
        if (shareNum < 0 || shareNum > (assets.size() - 1)) {
            System.out.println("No such asset");
            Operations();
        }
        return shareNum;
    }

    static int getShareOwnerPosition(String ownerName, String share)
    {
        for (int i = 0; i <= assets.get(getNumberFromArrayList(assets, share)).owners.length - 1; i++)
        {
            if (assets.get(getNumberFromArrayList(assets, share)).owners[i].equals(ownerName) && shareHolding(share, ownerName) != 0)
            {
                return i;
            }
        }
        System.out.println("No shareholding found");
        Operations();
        return 0;
    }

    static void addShare(String share, String ShareOwner, int shares)
    {
        int shareNum = getNumberFromArrayList(assets, share);

        for (int i = 0; i <= assets.get(shareNum).owners.length - 1; i++)
        {
            if (assets.get(shareNum).owners[i] == null)
            {
                continue;
            }
            if (assets.get(shareNum).owners[i].equals(ShareOwner))
            {
                assets.get(shareNum).ownerShares[i] = assets.get(shareNum).ownerShares[i] + shares;
                int j;
                for (j = 0; j <= accounts.get(getNumberFromArrayList(accounts, ShareOwner)).assets.size() - 1; j++){
                    if (accounts.get(getNumberFromArrayList(accounts, ShareOwner)).assets.get(j)[0].equals(share)){
                        break;
                    }
                }
                int a = (Integer) accounts.get(getNumberFromArrayList(accounts, ShareOwner)).assets.get(j)[1];
                a += shares;
                accounts.get(getNumberFromArrayList(accounts, ShareOwner)).assets.get(j)[1] = a;
                return;
            }
        }
        int owners = 0;
        for (int i = 0; i <= assets.get(shareNum).owners.length - 1; i++)
        {
            owners = i;
            if (assets.get(shareNum).owners[i] == null)
            {
                break;
            }
        }
        assets.get(shareNum).owners[owners] = ShareOwner;
        assets.get(shareNum).ownerShares[owners] = shares;
        accounts.get(getNumberFromArrayList(accounts, ShareOwner)).assets.add(new Object[]{share, shares});
    }

    static void deductShare(String share, String ShareOwner, int shares)
    {
        int shareNum = getNumberFromArrayList(assets, share);
        int sharePosition = getShareOwnerPosition(ShareOwner, share);
        assets.get(shareNum).ownerShares[sharePosition] =
                assets.get(shareNum).ownerShares[sharePosition] - shares;
        int j;
        for (j = 0; j <= accounts.get(getNumberFromArrayList(accounts, ShareOwner)).assets.size() - 1; j++){
            if (accounts.get(getNumberFromArrayList(accounts, ShareOwner)).assets.get(j)[0].equals(share)){
                break;
            }
        }
        int a = (Integer) accounts.get(getNumberFromArrayList(accounts, ShareOwner)).assets.get(j)[1];
        a -= shares;
        accounts.get(getNumberFromArrayList(accounts, ShareOwner)).assets.get(j)[1] = a;

        if (assets.get(shareNum).ownerShares[sharePosition] == 0)
        {
            assets.get(shareNum).owners[sharePosition] = null;
            accounts.get(getNumberFromArrayList(accounts, ShareOwner)).assets.remove(j);
        }
    }

    static int shareHolding(String share, String shareOwner)
    {
        for (int i = 0; i <= assets.get(getNumberFromArrayList(assets, share)).owners.length - 1; i++)
        {
            if ((assets.get(getNumberFromArrayList(assets, share)).owners[i] != null) && assets.get(getNumberFromArrayList(assets, share)).owners[i].equals(shareOwner))
            {
                return assets.get(getNumberFromArrayList(assets, share)).ownerShares[i];
            }
        }
        return 0;
    }

    /**
     * <p><strong>This method is used to know the assets in a particular account</strong></p>
     *
     * <p>This method takes in a string value which can be the number or name of an account,
     * and than returns all the assets in that particular account.<Strong> This method only returns
     * an ArrayList which contains the name of the assets in the particular account but not
     * the percentage owned by the account.</Strong></p>
     *
     * <p>This method firsts takes in the string value and uses the <strong>getAccNumber</strong>
     * method of the account control of the <strong>accountControl</strong> class to get the account number
     * of the entered user. Than it searches for all the assets created which are present in the <strong>assets</strong>
     * ArrayList of the <strong>assetShare</strong> class. It iterates through the <strong>owners</strong> list of all the
     * assets present and tries to match it with the given account name. If a match is found, it is added in an ArrayList named
     * <strong>shares</strong> and than this ArrayList is returned</p>
     *
     * <p>This method was introduced assuming that one could get the list of the assets in an account
     * and than the percentage could be obtained using the <strong>shareHolding</strong> method
     * but when realised that this method was just not suitable and time consuming, it was deprecated and
     * shares were directly stored in an attribute, i.e., an ArrayList in the account class, which was convenient and
     * time saving for no <strong>iteration was required there but just the pre-made ArrayList was returned.</strong></p>
     *
     * @param shareOwner Accepts the account number or name
     * @return It returns a ArrayList which contains the names of the assets owned by the account user
     * @see accountControl
     * @see Assets
     * @see assetShare
     * @see Accounts
     * @deprecated Use the assets ArrayList, an attribute of the class Accounts which store the name and percentage of the assets owned the user
     */


    @Deprecated
    static ArrayList<String> sharesInAccount(String shareOwner)
    {
        ArrayList<String> shares = new ArrayList<>();
        int accNum = getNumberFromArrayList(accounts, shareOwner);
        String name = accounts.get(accNum).getName();
        for (int i = 0; i <= regisNumber - 1; i++){
            for (int j = 0; j <= assets.get(i).owners.length - 1; j++){
                if (assets.get(i).owners[j].equals(name)){
                    shares.add(assets.get(i).getName());
                }
            }
        }
        if (shares.size() == 0){
            System.out.println("No shares in account");
            Operations();
        }
        return shares;
    }
}