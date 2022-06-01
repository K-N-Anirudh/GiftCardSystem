package Samplecodes;

import java.util.ArrayList;

class Customer
{
    private static int no=0;// To give value of of customer on order in which typed
    private int custId;// its no is based on the nth account created. that is first person will have custid 1 and account no 101
    private long accountNo;
    private String Name;//Alphabets and . are allowed
    private double balance;
    private String encryptedPassword;
    private boolean transcationtype;
    private ArrayList<GiftCard> g=new ArrayList<>();
    Customer(String Name, double balance, String encryptedPassword)
    {
        this.Name=Name;
        this.balance=balance;
        setEncryptedPassword(encryptedPassword);
        no++;// Give id no and accountNo to the person
        this.custId=no;
        String samp =Integer.toString(this.custId);
        samp=samp+"0";
        samp+=samp;
        this.accountNo=Long.parseLong(samp);//Generates id and account based on the logic first come first serve

    }
    long getAccountNo()
    {return this.accountNo;}

    double getBalance()
    {return this.balance;}

    void setEncryptedPassword(String Password)//To encrypt password
    {

        StringBuffer result= new StringBuffer();

        for (int i=0; i<Password.length(); i++)
        {
            char ch;
            if (Character.isUpperCase(Password.charAt(i)))
            {
                ch = (char)(((int)Password.charAt(i) +
                        1 - 65) % 26 + 65);
                result.append(ch);
            }

            else  if(Character.isLowerCase((Password.charAt(i))))
            {
                ch = (char)(((int)Password.charAt(i) +
                        1 - 97) % 26 + 97);
                result.append(ch);
            }
            else{
                ch = (char)(((int)Password.charAt(i) +
                        1 - 48) % 10 + 48);
                result.append(ch);

            }

        }
        this.encryptedPassword=new String(result);

    }

    String getPassword()
    {

        return this.encryptedPassword;

    }
    ArrayList<GiftCard> getCard()
    {
        return this.g;
    }

    void addBalance(double amount)// When blocking a gift card we add back the amoun in the crd to balance
    {
        this.balance+=amount;
    }

    void removeBalance(double amount)// Removing amount from balance and adding to gifCard
    {
        this.balance-=amount;
    }


    void Display()
    {
        System.out.println("\nCustomer Id    :"+this.custId+"\nAccount No     :"+this.accountNo+"\nName           :"+this.Name+"\nAccount Balance:"+this.balance);
    }

}
