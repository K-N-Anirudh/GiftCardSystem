class Transaction{
    double amount;
    boolean type;//Checks for topup(True) nd purchase(False)
    Transaction(double amount,boolean type)
    {this.amount=amount;
        this.type=type;}
}
