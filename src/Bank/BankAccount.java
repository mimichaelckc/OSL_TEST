package Bank;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class BankAccount {
    private String userName;
    private ArrayList<TransactionRecord> transactionRecords = new ArrayList<>();
    private LocalDateTime creationDateTime;
    private HashMap<String, CurrencyAccount> accountHashMap = new HashMap<>();

    private int withdrawCounter;
    private LocalDateTime lastWithdrawTime;

    public BankAccount(String userName) {
        this.userName = userName;
        this.withdrawCounter = 0;
        this.creationDateTime = LocalDateTime.now();
    }

    public String getUserName() {
        return userName;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void addCurrencyAccount(String cType, CurrencyAccount currencyAccount){
        accountHashMap.put(cType, currencyAccount);
    }

    public CurrencyAccount getCAccountByType(String cType){
        return accountHashMap.get(cType);
    }

    public void displayAccountBalance(){
        System.out.println("The Account Balance of User ["+userName+"]");
        for (CurrencyAccount account : accountHashMap.values()) {
            System.out.println(account.getCurrencyName()+" Account : $"+account.getBalance());
        }
    }

    public void addTransactionRecord(TransactionRecord tx){
        transactionRecords.add(tx);
    }

    public void incWithdrawCounter(){
        this.withdrawCounter++;
    }

    public void setWithdrawCounter(int withdrawCounter) {
        this.withdrawCounter = withdrawCounter;
    }

    public int getWithdrawCounter() {
        return withdrawCounter;
    }

    public LocalDateTime getLastWithdrawTime() {
        return lastWithdrawTime;
    }

    public void setLastWithdrawTime(LocalDateTime lastWithdrawTime) {
        this.lastWithdrawTime = lastWithdrawTime;
    }

    public void displayTransactionHistory(){
        System.out.println("Client Name: "+ this.userName);
        for (TransactionRecord tx : transactionRecords) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            System.out.printf("%s\t\t|%s|%s|%s|%n",dtf.format(tx.getTrxDate()),tx.getCurrency(),tx.getOperationType(),tx.getAmt());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.userName, this.creationDateTime);
    }
}
