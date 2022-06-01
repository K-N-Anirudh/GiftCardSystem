import java.util.*;

class NumberRangeException extends Exception // This exception occurs when number typed is -ve and not in range
{
    public NumberRangeException(String str) {
        System.out.println(str);
    }
}


public class giftCardSystem {

    public static void main(String[] args)
    {
        int initialChoice=0,choiceAfterLogin=0,i,count=0;long accountNo;//i for loop traversal,count to count no of digits in anumber
        Customer cust;//To add inputted elements to the class
        boolean validName,validPassword,validPurchase,invalidType,cardNoExist,cardPinExist;
        HashMap<Long,Customer> accounts =new HashMap<>();// To store customer details mapping to their accountNo
        Iterator<Map.Entry<Long,Customer > > iterator ; //Traversing through map
        int topUpCardNo=0,blockCardNo=0,displayCardNo=0,attempt;//attempt is for no of attempts to enter password
        ArrayList<Integer> cards=new ArrayList<>();//To store cardNo generated and check everytime while generating another cardNo to avoid duplication
        String Password;//To input password
        char YorN;
        Scanner sc=new Scanner(System.in);
        String menu1="\nEnter \n 1.Signup \n 2.Login\n 3.Purchase\n 4.Exit\n Enter choice:",menu2="  --------**Menu**--------  \n 1.Create a new GiftCard \n 2.Top-up the existing Card \n 3.Show Gift Card Transaction History \n 4.Block existing Card  \n 5.LogOut  \nEnter choice:";
        do{// For 1st menu to login or signup or purchase

            do { // To make sure choice is input and in positive
                invalidType=false;
                try {
                    System.out.print(menu1);initialChoice=sc.nextInt();
                    if(initialChoice<0){
                        throw new NumberRangeException("Negative Values not allowed");
                    }

                }catch (InputMismatchException e) {
                    System.out.println("Numbers only allowed ");
                    invalidType=true;
                }
                catch (NumberRangeException e1){}
                sc.nextLine(); // clears the buffer
            } while ( initialChoice< 0||invalidType);
            switch(initialChoice)
            {
                case 1://Signup
                    String name;double amount=0;//amount is the amount in customer account

                    validName=false;

                    do {// Checking the Name is in specified format
                        System.out.print("\n Enter Name(Alphabets,Spacing and . allowed):");name = sc.nextLine();
                        if(name.matches("[a-zA-Z.\\s+]+"))
                        {validName=true;}
                        else
                        {System.out.println("Enter correctly");}
                    }while(!validName);
                    validPassword=false;
                    do { // Checking the Password in specified format
                        System.out.print(" Enter Password(Only alphabets and digits allowed):");Password = sc.nextLine();
                        if( Password.matches("[a-zA-Z0-9]+"))
                        {validPassword=true;}
                        else
                        {System.out.println("Enter correctly");}
                    }while(!validPassword);
                    do { //To check that we type digits and not use -ve value
                        try {
                            invalidType=false;
                            System.out.print(" Enter Amount:");
                            amount = sc.nextDouble();

                            if (amount < 0) {
                                throw new NumberRangeException("Negative values not allowed");
                            }

                        }catch (InputMismatchException e) {
                            System.out.println("Invalid (Only numbers allowed) ");
                            invalidType=true;

                        }
                        catch(NumberRangeException e1 )
                        {}
                        sc.nextLine(); // clears the buffer
                    } while (amount <0||invalidType);
                    cust=new Customer(name,amount,Password);//Adding to customer class
                    long accountNoSignUp=cust.getAccountNo();
                    accounts.put(accountNoSignUp,cust); // Adding customer details to accounts hashmap
                    System.out.println(" **-Successfully added Account!!!-**");
                    accounts.get(accountNoSignUp).Display();
                    break;

                case 2://Login and other activities
                    int custId=0;
                    YorN='y';
                    do {
                        do { // To make sure choice is input and in positive
                            invalidType = false;
                            try {
                                System.out.print("\n Enter your Id:");
                                custId = sc.nextInt();
                                if (custId < 0) {
                                    throw new NumberRangeException("Negative values not allowed");
                                }

                            } catch (InputMismatchException e) {
                                System.out.println("Numbers only allowed ");
                                invalidType = true;
                            } catch (NumberRangeException e1) {

                            }
                            sc.nextLine(); // clears the buffer
                        } while (custId < 0 || invalidType);
                        String samp = Integer.toString(custId);
                        samp = samp + "0";
                        samp += samp;
                        accountNo = Long.parseLong(samp);
                        if (accounts.keySet().contains(accountNo)) {
                            YorN='n';//To exit the last loop in this case
                            iterator = accounts.entrySet().iterator();
                            while (iterator.hasNext())// Iterating to check if account exist
                            {
                                Map.Entry<Long, Customer> entry = iterator.next();
                                if (accountNo == entry.getKey()) {
                                    attempt = 0;
                                    do {

                                        System.out.print("Enter Password:");
                                        Password = sc.nextLine();
                                        String str = entry.getValue().getPassword();
                                        if (str.equals(Password)) {
                                            attempt = 4;// To get out of that loop
                                            System.out.println(" --*Successfully LoggedIn AccountNo:" + accountNo + "*--");
                                            do {


                                                do { // To make sure choice is input and in positive
                                                    invalidType = false;
                                                    try {
                                                        System.out.print(menu2);
                                                        choiceAfterLogin = sc.nextInt();
                                                        if (choiceAfterLogin < 0) {
                                                            throw new NumberRangeException("Negative Values not allowed");
                                                        }

                                                    } catch (InputMismatchException e) {
                                                        System.out.println("Numbers only allowed ");
                                                        invalidType = true;
                                                    } catch (NumberRangeException e1) {

                                                    }
                                                    sc.nextLine(); // clears the buffer
                                                } while (choiceAfterLogin < 0 || invalidType);

                                                switch (choiceAfterLogin) {
                                                    case 1://Create New GiftCard
                                                        Random r = new Random();// To generate random number
                                                        int giftCardNo;

                                                        do { //To make sure one copy of card no is there
                                                            cardNoExist = false;
                                                            giftCardNo = (10000 + r.nextInt(90000));//Make sures in 5 digit
                                                            if (cards.contains(giftCardNo)) {// Checks if card no is there
                                                                cardNoExist = true;
                                                            } else cards.add(giftCardNo);
                                                        } while (cardNoExist);
                                                        int giftCardPin = (1000 + r.nextInt(9000));
                                                        double newAmount = 0;// Amount to be present in giftcard
                                                        do {// Check that input is number and +ve
                                                            try {
                                                                invalidType = false;
                                                                System.out.print("\n Available amount is Rs." + accounts.get(accountNo).getBalance() + "\n Enter amount to add :");
                                                                newAmount = sc.nextDouble();

                                                                if (newAmount < 0) {
                                                                    throw new NumberRangeException("Negative values not allowed");
                                                                } else if (newAmount > accounts.get(accountNo).getBalance()) {
                                                                    throw new NumberRangeException("That much amount is not available.");
                                                                }

                                                            } catch (InputMismatchException e) {
                                                                System.out.println("Invalid (Only numbers allowed) ");
                                                                invalidType = true;

                                                            } catch (NumberRangeException e1) {
                                                            }
                                                            sc.nextLine(); // clears the buffer
                                                        } while (newAmount < 0 || newAmount > accounts.get(accountNo).getBalance() || invalidType);
                                                        GiftCard g = new GiftCard(giftCardNo, giftCardPin, newAmount);
                                                        accounts.get(accountNo).getCard().add(g);// Adding giftcard to the account
                                                        accounts.get(accountNo).removeBalance(newAmount);// Remove the amount added to card from customer balance
                                                        System.out.println("\n --*Added Successfully*--\n  1.Card No :" + giftCardNo +
                                                                "\n  2.Card Pin:" + giftCardPin);

                                                        break;
                                                    case 2://Top-up Existing Card
                                                        double topUpAmount = 0;
                                                        do {
                                                            try {
                                                                invalidType = false;
                                                                System.out.print("\n Enter cardNo to topUp:");
                                                                topUpCardNo = sc.nextInt();

                                                                if (topUpCardNo < 0) {
                                                                    throw new NumberRangeException("Negative values not allowed");
                                                                }

                                                            } catch (InputMismatchException e) {
                                                                System.out.println("Invalid (Only numbers allowed) ");
                                                                invalidType = true;

                                                            } catch (NumberRangeException e1) {
                                                            }
                                                            sc.nextLine(); // clears the buffer
                                                        } while (topUpCardNo < 0 || invalidType);

                                                        for (i = 0; i < accounts.get(accountNo).getCard().size(); i++) { //Traversing to giftcard array in accountvtill we get required card
                                                            if (accounts.get(accountNo).getCard().get(i).getCardNumber() == topUpCardNo) {
                                                                do {
                                                                    try {
                                                                        invalidType = false;
                                                                        System.out.print("\n Available amount is Rs." + accounts.get(accountNo).getBalance() + "\n Enter amount to topUp:");
                                                                        topUpAmount = sc.nextDouble();

                                                                        if (topUpAmount < 0) {
                                                                            throw new NumberRangeException("Negative values not allowed");
                                                                        } else if (topUpAmount > accounts.get(accountNo).getBalance()) {
                                                                            throw new NumberRangeException("Amount not present");
                                                                        }

                                                                    } catch (InputMismatchException e) {
                                                                        System.out.println("Invalid (Only numbers allowed) ");
                                                                        invalidType = true;

                                                                    } catch (NumberRangeException e1) {
                                                                    }
                                                                    sc.nextLine(); // clears the buffer
                                                                } while (topUpAmount < 0 || topUpAmount > accounts.get(accountNo).getBalance() || invalidType);
                                                                accounts.get(accountNo).removeBalance(topUpAmount);//To remove topup amount from balance
                                                                accounts.get(accountNo).getCard().get(i).addAmount(topUpAmount); // To add amount to the giftcard
                                                                System.out.println(" --*Successfully added and now the current amount:" + accounts.get(accountNo).getCard().get(i).getAmount() + "*--");
                                                            }

                                                        }


                                                        break;
                                                    case 3://Display
                                                        do {
                                                            try {
                                                                invalidType = false;
                                                                System.out.print("\n Enter cardNo to display:");
                                                                displayCardNo = sc.nextInt();

                                                                if (displayCardNo < 0) {
                                                                    throw new NumberRangeException("Negative values not allowed");
                                                                }

                                                            } catch (InputMismatchException e) {
                                                                System.out.println("Invalid (Only numbers allowed) ");
                                                                invalidType = true;

                                                            } catch (NumberRangeException e1) {
                                                            }
                                                            sc.nextLine(); // clears the buffer
                                                        } while (displayCardNo < 0 || invalidType);
                                                        //      accounts.get(accountNo).getCard().remove();
                                                        for (i = 0; i < accounts.get(accountNo).getCard().size(); i++) {//Traversing to get the required card
                                                            if (accounts.get(accountNo).getCard().get(i).getCardNumber() == displayCardNo) {
                                                                accounts.get(accountNo).getCard().get(i).Display();

                                                            }
                                                        }
                                                        break;
                                                    case 4://Blocking Card
                                                        do {
                                                            try {
                                                                invalidType = false;
                                                                System.out.print("\n Enter cardNo to block:");
                                                                blockCardNo = sc.nextInt();

                                                                if (blockCardNo < 0) {
                                                                    throw new NumberRangeException("Negative values not allowed");
                                                                }

                                                            } catch (InputMismatchException e) {
                                                                System.out.println("Invalid (Only numbers allowed) ");
                                                                invalidType = true;

                                                            } catch (NumberRangeException e1) {
                                                            }
                                                            sc.nextLine(); // clears the buffer
                                                        } while (blockCardNo < 0 || invalidType);
                                                        //      accounts.get(accountNo).getCard().remove();
                                                        for (i = 0; i < accounts.get(accountNo).getCard().size(); i++) {
                                                            if (accounts.get(accountNo).getCard().get(i).getCardNumber() == blockCardNo) {
                                                                double blockedAmount = accounts.get(accountNo).getCard().get(i).getAmount();
                                                                accounts.get(accountNo).getCard().remove(i);// To remove card
                                                                accounts.get(accountNo).addBalance(blockedAmount);// Add back the ampunt to customer balance
                                                                System.out.println("Amount added back to account successfully");
                                                                System.out.println("Current amount in account" + accounts.get(accountNo).getBalance());

                                                            }
                                                        }
                                                        break;
                                                    case 5:
                                                        System.out.println("Logged Out successfully");
                                                        break;
                                                    default:
                                                        System.out.println("Wrong choice");


                                                }


                                            } while (choiceAfterLogin != 5);
                                        } else if (attempt == 3) {
                                            System.out.println("Wrong Password(Attempts over)");
                                            attempt++;
                                        } else {
                                            System.out.println("Wrongly typed !!(" + (3 - attempt++) + " attempts left)");

                                        }
                                    } while (attempt <= 3);

                                }
                            }
                        } else {
                            System.out.println("Account does not exist");

                            do{
                                String s = "temp";
                                while (s.length() > 1) {// To make sure you enter a single character
                                System.out.print("Enter Y to try again and N to exit:");
                                try {
                                    s = sc.nextLine();
                                    if (s.length() > 1) {
                                        throw new RuntimeException("Input should be a single character!\n");

                                    } else {
                                        YorN = s.charAt(0);
                                        if (YorN != 'Y' && YorN != 'y' && YorN != 'N' && YorN != 'n')
                                            System.out.println("Enter either Y or N");


                                    }


                                } catch (RuntimeException re) {
                                    System.out.print(re.getMessage());
                                    // you can break the loop or try again
                                }
                            }
                            }while(YorN != 'Y' && YorN != 'y' && YorN != 'N' && YorN != 'n');
                        }
                    }while(YorN=='Y'||YorN=='y');
                    break;
                case 3:
                    Customer temp;int index;
                    int purchaseCardNo=0,purchaseCardPin=0;
                    attempt=0;YorN='n';

                    do {

                        System.out.print("Enter bill amount:");
                        amount = 0;
                        do {
                            try {
                                invalidType = false;

                                amount = sc.nextDouble();

                                if (amount < 0) {
                                    throw new NumberRangeException("Negative values not allowed");
                                }


                            } catch (InputMismatchException e) {
                                System.out.println("Invalid (Only numbers allowed) ");
                                invalidType = true;

                            } catch (NumberRangeException e1) {
                            }
                            sc.nextLine(); // clears the buffer
                        } while (amount < 0 || invalidType);

                        do {
                            try {
                                invalidType = false;
                                System.out.print("Enter your cardNo :");
                                purchaseCardNo = sc.nextInt();

                                if (purchaseCardNo < 0) {
                                    throw new NumberRangeException("Negative values not allowed");
                                } else {
                                    count = 0;
                                    int purchaseCardNo2 = purchaseCardNo;
                                    while (purchaseCardNo2 != 0) {
                                        // num = num/10
                                        purchaseCardNo2 /= 10;
                                        ++count;
                                    }
                                    if (count != 5)// To check if count is 5 digit
                                        throw new NumberRangeException("To be 5 digit");


                                }

                            } catch (InputMismatchException e) {
                                System.out.println("Invalid (Only numbers allowed) ");
                                invalidType = true;

                            } catch (NumberRangeException e1) {
                            }
                            sc.nextLine(); // clears the buffer
                        } while (purchaseCardNo < 0 || count != 5 || invalidType);

                        do {
                            try {
                                invalidType = false;
                                System.out.print("Enter your cardPin:");
                                purchaseCardPin = sc.nextInt();

                                if (purchaseCardPin < 0) {
                                    throw new NumberRangeException("Negative values not allowed");
                                } else {
                                    count = 0;
                                    int purchaseCardPin2 = purchaseCardPin;
                                    while (purchaseCardPin2 != 0) {
                                        // num = num/10
                                        purchaseCardPin2 /= 10;
                                        ++count;
                                    }
                                    if (count != 4)// To check if no of digits is 4
                                        throw new NumberRangeException("To be 4 digit");


                                }

                            } catch (InputMismatchException e) {
                                System.out.println("Invalid (Only numbers allowed) ");
                                invalidType = true;

                            } catch (NumberRangeException e1) {
                            }
                            sc.nextLine(); // clears the buffer
                        } while (purchaseCardPin < 0 || count != 4 || invalidType);

                        Iterator<Customer> iteratorGiftCard = accounts.values().iterator();//Traversing to all accounts
                        cardNoExist=false;cardPinExist=false;validPurchase=false;// To check if amount is there and card no and pin matching
                        while (iteratorGiftCard.hasNext())// Iterating to check if email exist
                        {
                            Customer customerGC = iteratorGiftCard.next();
                            Iterator<GiftCard> eachCard = customerGC.getCard().iterator();
                            while (eachCard.hasNext()) {
                                GiftCard card = eachCard.next();

                                if (card.getCardNumber() == purchaseCardNo) {// Check for cardNo

                                    cardNoExist=true;
                                    if (card.getCardPin() == purchaseCardPin) {//Check for pin
                                        cardPinExist=true;
                                        temp = customerGC;
                                        index = accounts.get(temp.getAccountNo()).getCard().indexOf(card);
                                        if (accounts.get(temp.getAccountNo()).getCard().get(index).getAmount() > amount) {

                                            validPurchase = true;
                                            attempt=4;
                                            accounts.get(temp.getAccountNo()).getCard().get(index).removeAmount(amount);
                                            System.out.print("\n AvailableBalance:" + accounts.get(temp.getAccountNo()).getCard().get(index).getAmount());
                                            System.out.print("\n Reward Points:" + accounts.get(temp.getAccountNo()).getCard().get(index).getRewardPoints());
                                            do{
                                                String s = "temp";
                                                while (s.length() > 1) {// To make sure you enter a single character
                                                    System.out.print("\n Enter Y to try again and N to exit:");
                                                    try {
                                                        s = sc.nextLine();
                                                        if (s.length() > 1) {
                                                            throw new RuntimeException(" Input should be a single character!\n");

                                                        } else {
                                                            YorN = s.charAt(0);
                                                            if (YorN != 'Y' && YorN != 'y' && YorN != 'N' && YorN != 'n')
                                                                System.out.println(" Enter either Y or N");


                                                        }


                                                    } catch (RuntimeException re) {
                                                        System.out.print(re.getMessage());
                                                        // you can break the loop or try again
                                                    }
                                                }
                                            }while(YorN != 'Y' && YorN != 'y' && YorN != 'N' && YorN != 'n');
                                        }
                                    }

                                }

                            }


                        }
                        if(attempt==3)
                        {
                            System.out.println("(Attempts over)");
                            attempt++;
                        }
                        else if(!cardNoExist)
                        {
                            System.out.println("CardNo Does not exist !!(" + (3 - attempt++) + "attempts left)");

                        }
                        else if(!cardPinExist)
                        {
                            System.out.println("Card Pin is not matching D !!(" + (3 - attempt++) + "attempts left)");

                        }
                        else if(!validPurchase)
                        {
                            System.out.println("Amount not present!!("+ (3 - attempt++) + "attempts left)");

                        }

                    }while(((!validPurchase||!cardNoExist||!cardPinExist)&&attempt<=3)||(YorN=='y'||YorN=='Y'));
                    break;
                case 4:System.out.println(" ---Exited the server---");break;
                default:System.out.println("Wrong choice");

            }
        }while(initialChoice!=4);

    }
}