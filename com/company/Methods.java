package Business.com.company;

import java.util.ArrayList;
import static Business.com.company.Interaction.Operations;
import static Business.com.company.accountControl.accounts;

public class Methods {
    public static double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    public static <T extends ArrayList<? extends Object_Head>> int getNumberFromArrayList(T t, String Name){
        int Number = -1;
        try {
            Number = Integer.parseInt(Name);
        }catch (Exception e)
        {
            for (int i = 0; i <= (t.size() - 1); i++)
            {
                String Names = t.get(i).getName();
                if (Names.equals(Name))
                {
                    Number = t.get(i).getNumber();
                }
            }
        }
        if (Number < 0 || Number >= t.size()) {
            System.out.println("Wrong input");
            Operations();
        }
        return Number;
    }

    public static ArrayList<Object[]> assetCount(String account) {
        ArrayList<Object[]> a = accounts.get(getNumberFromArrayList(accounts, account)).getAssets();
        for (int i = 0; i <= a.size() - 1; i++) {
            System.out.println(a.get(i)[0] + ":  " + a.get(i)[1] + "%");
        }
        return a;
    }
}
