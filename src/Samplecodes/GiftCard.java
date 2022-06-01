package Samplecodes;

import java.util.ArrayList;

class GiftCard {
    private int  cardNumber,pin;
    private double giftCardAmount=0;
    private int rewardPoints=0;
    private ArrayList<Transaction> t=new ArrayList<>();
    GiftCard(int cardNumber, int pin, double amount) // To give details of account for the gift card
    {
        this.cardNumber=cardNumber;
        this.pin=pin;
        this.giftCardAmount=amount;
    }
    void addAmount(double amount)//->Change it based on condition topup
    {
        this.giftCardAmount+=amount;
        Transaction topup=new Transaction(amount,true);
        t.add(topup);
    }
    void removeAmount(double amount)//->Change it based on condition  purchase and  change reward points accordingly
    {
        this.giftCardAmount-=amount;
        if(amount>=100)// To check for reward points
            this.rewardPoints+=(int)amount/100;
        if(rewardPoints>=10){ // Adds amount when points greater than 10
            this.giftCardAmount+=rewardPoints;
            this.rewardPoints=0;
        }
        Transaction purchase=new Transaction(amount,false);
        t.add(purchase);
    }
    void  Display()//To display TransactionHistory
    {
        this.t.forEach((a) -> {
            if(a.type)
                System.out.println("Top-up of amount:"+a.amount);
            else
                System.out.println("Purchased amount:"+a.amount);


        });

    }

    int getCardNumber() // To check no during purchase
    {
        return this.cardNumber;
    }
    int getCardPin() //To check pin during purchase
    {
        return this.pin;

    }
    int getRewardPoints()
    {
        return this.rewardPoints;
    }
    double getAmount()
    {
        return this.giftCardAmount;
    }

}