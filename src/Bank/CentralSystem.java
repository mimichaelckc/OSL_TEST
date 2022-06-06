package Bank;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Scanner;
import Exception.*;
/*
* A centralized operation class for management all the accounts, operations
* This class complies the Singleton Pattern
* */
public class CentralSystem {

    //implement the Singleton Pattern instance
    private static CentralSystem centralSystem;
    BankAccount oslAccount;

    HashMap<String, BankAccount> staffSet = new HashMap<>();
    String[] currencyList = {"HKD", "USD", "SGD"};
    Scanner sc = new Scanner(System.in);

    private CentralSystem(){}



    public static CentralSystem getInstance(){
        if(centralSystem == null) {
            centralSystem = new CentralSystem();

        }
        return centralSystem;
    }


    public void createAccount(){
        try{
            //init OSL_FEE account
            //trigger when the first account has been created
            if(staffSet.get("OSL_FEE") == null){
                oslAccount = new BankAccount("OSL_FEE");
                CurrencyAccount hkd_osl = new CurrencyAccount("HKD",BigDecimal.valueOf(0));
                CurrencyAccount usd_osl = new CurrencyAccount("USD",BigDecimal.valueOf(0));
                CurrencyAccount sgd_osl = new CurrencyAccount("SGD",BigDecimal.valueOf(0));

                oslAccount.addCurrencyAccount("HKD",hkd_osl);
                oslAccount.addCurrencyAccount("USD",usd_osl);
                oslAccount.addCurrencyAccount("SGD",sgd_osl);
                staffSet.put("OSL_FEE", oslAccount);
            }


            String usrName;
            int currencyIdx;
            BigDecimal balanceAmt;

            String currencyType;

            System.out.println("Please Enter the name of the user: ");
            usrName = sc.next();

            System.out.println("Please Select the currency type of the account: ");
            System.out.println("1. HKD Account ");
            System.out.println("2. USD Account ");
            System.out.println("3. SGD Account ");
            currencyIdx = sc.nextInt();
            currencyType = currencyList[currencyIdx-1];


            System.out.println("Please Enter the balance of the account: ");
            balanceAmt = sc.nextBigDecimal();



            //1. create the bank account object
            BankAccount bankAccount = new BankAccount(usrName);
            //2. create the currency account object
            CurrencyAccount currencyAccount = new CurrencyAccount(currencyType, balanceAmt);
            //3. add the currency account to the bank account
            bankAccount.addCurrencyAccount(currencyType,currencyAccount);

            if(staffSet.get(usrName)!=null){
                if(staffSet.get(usrName).getCAccountByType(currencyType)!=null)
                    throw new AccountDuplicate(currencyType);
                else
                    staffSet.get(usrName).addCurrencyAccount(currencyType, currencyAccount);
                    System.out.println("Currency Account Added!");
            }
            else{

                //4. add to the staff list
                staffSet.put(usrName, bankAccount);
                System.out.println("Account Created!");
            }
        }
        catch (Exception ex){
            System.out.println("Please create a valid account!");
        }


    }

    public void deposit(){
        String userName,currencyType;
        int currencyIdx;
        BigDecimal amt;
        //1. get the username
        System.out.println("Please Enter the name of the user: ");
        userName = sc.next();
        //2. get the account type
        System.out.println("Please Select the currency type of the account: ");
        System.out.println("1. HKD Account ");
        System.out.println("2. USD Account ");
        System.out.println("3. SGD Account ");
        currencyIdx = sc.nextInt();
        currencyType = currencyList[currencyIdx-1];

        //3. get the deposit amt
        System.out.println("Please Enter the deposit amount: ");
        amt = sc.nextBigDecimal();

        BankAccount bankAccount = findBankAccountByUser(userName);
        CurrencyAccount account = findCAccountByUserAndType(userName, currencyType);
        account.cashIn(amt);

        //4. create Transaction history of deposit operation
        TransactionRecord depositTx = new TransactionRecord(currencyType, "Deposit",amt );
        bankAccount.addTransactionRecord(depositTx);

        bankAccount.displayAccountBalance();

    }

    public void withdrawFromUser(){
        String userName,currencyType;
        int currencyIdx;
        BigDecimal amt;
        //1. get the username
        System.out.println("Please Enter the name of the user: ");
        userName = sc.next();
        //2. get the account type
        System.out.println("Please Select the currency type of the account: ");
        System.out.println("1. HKD Account ");
        System.out.println("2. USD Account ");
        System.out.println("3. SGD Account ");
        currencyIdx = sc.nextInt();
        currencyType = currencyList[currencyIdx-1];

        //3. get the withdrawn amt
        System.out.println("Please Enter the withdraw amount: ");
        amt = sc.nextBigDecimal();

      try{

          //4. withdrawMoney
          BankAccount bankAccount = findBankAccountByUser(userName);

          //check if the withdraw counter equals to 5
          if(bankAccount.getWithdrawCounter() == 5){
              //check if the last withdraw operation is within 5 minutes
              if(LocalDateTime.now().isBefore(bankAccount.getLastWithdrawTime().plusMinutes(5))){
                  throw new WithdrawLimitExceed();
              }
              else{
                  bankAccount.setWithdrawCounter(0);
              }
          }
          CurrencyAccount account = findCAccountByUserAndType(userName, currencyType);
          account.cashOut(amt);

          //5. charge OSL_FEE
          BigDecimal chargeFee = amt.divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
          account.cashOut(chargeFee);
          oslAccount.getCAccountByType(currencyType).cashIn(chargeFee);

          //6. create Transaction history of withdraw operation
          TransactionRecord withdrawTx = new TransactionRecord(currencyType, "Withdraw",amt.negate() );
          bankAccount.addTransactionRecord(withdrawTx);

          //7. create Transaction history of charge fee operation
          TransactionRecord feeTx = new TransactionRecord(currencyType, "Withdraw FEE",chargeFee.negate() );
          bankAccount.addTransactionRecord(feeTx);

          //8. create Transaction history of FEE_IN operation of OSL_FEE
          TransactionRecord oslfeeTx = new TransactionRecord(currencyType, "FEE IN",chargeFee );
          oslAccount.addTransactionRecord(oslfeeTx);
          System.out.println("Charge Fee: "+chargeFee);

          //9. increased withdraw counter by 1
          bankAccount.incWithdrawCounter();

          //10. set the last withdraw time
          bankAccount.setLastWithdrawTime(LocalDateTime.now());

          bankAccount.displayAccountBalance();
      }
      catch (InsufficientFundsException ex){
          System.out.println("Sorry! This client does not have enough funds. The current balance is :$"+ex.getAmount());
      }
      catch (WithdrawLimitExceed ex){
          System.out.println("Sorry! Your withdraw operation is too frequent, please try it later");
      }
    }

    public void transfer(){
        String transferFrom;
        String transferTo;
        String currencyType;
        BigDecimal amt;
        int currencyIdx;

        //1. get the transferFrom
        System.out.println("Please Enter the name of the user who transfer from: ");
        transferFrom = sc.next();

        //2. get the transferTo
        System.out.println("Please Enter the name of the user who transfer to: ");
        transferTo = sc.next();

        //2. get the account type
        System.out.println("Please Select the currency type of the account: ");
        System.out.println("1. HKD Account ");
        System.out.println("2. USD Account ");
        System.out.println("3. SGD Account ");
        currencyIdx = sc.nextInt();
        currencyType = currencyList[currencyIdx-1];


        //3. get the transfer amt
        System.out.println("Please Enter the transfer amount: ");
        amt = sc.nextBigDecimal();
        try{

            //4. get the corresponding accounts
            BankAccount transferFromBA = findBankAccountByUser(transferFrom);
            BankAccount transferToBA = findBankAccountByUser(transferTo);

            //check if the client from exists
            if(transferFromBA == null)
                throw new BankAccountNotFound(transferFrom);
            //check if the client to exists
            if(transferToBA == null)
                throw new BankAccountNotFound(transferTo);

            CurrencyAccount transferFromCA = transferFromBA.getCAccountByType(currencyType);
            CurrencyAccount transferToCA =  transferToBA.getCAccountByType(currencyType);

            //check if the client from exists
            if(transferFromCA == null)
                throw new CurrencyAccountNotFound(transferFrom,currencyType);
            //check if the client to exists
            if(transferToCA == null)
                throw new CurrencyAccountNotFound(transferTo,currencyType);

            //5. cash in & out
            transferFromCA.cashOut(amt);
            transferToCA.cashIn(amt);

            //6.  create Transaction history
            TransactionRecord txFrom = new TransactionRecord(currencyType, "Transfer out", amt.negate());
            TransactionRecord txTo = new TransactionRecord(currencyType, "Transfer In", amt);
            transferFromBA.addTransactionRecord(txFrom);
            transferToBA.addTransactionRecord(txTo);

            transferFromBA.displayAccountBalance();
            transferToBA.displayAccountBalance();
        }
        catch (BankAccountNotFound ex){
            System.out.println("Sorry! Client: "+ex.getClient()+" could not found!");
        }
        catch (CurrencyAccountNotFound ex){
            System.out.println("Sorry! Client: "+ex.getClient()+" does not have "+ex.getAccount()+" account.");
        }
        catch (InsufficientFundsException ex){
            System.out.println("Sorry! The client does not have enough funds. The current balance is :$"+ex.getAmount());
        }
    }

    public void listBalance(){
        String userName,currencyType;
        int currencyIdx;
        BigDecimal amt;
        BankAccount bankAccount;

        //1. get the username
        System.out.println("Please Enter the name of the user: ");
        userName = sc.next();

        bankAccount = findBankAccountByUser(userName);
        bankAccount.displayAccountBalance();
    }

    public void displayTransaction(){
        String userName;

        //1. get the username
        System.out.println("Please Enter the name of the user: ");
        userName = sc.next();
        //2. displayHistory
        BankAccount bankAccount = findBankAccountByUser(userName);
        bankAccount.displayTransactionHistory();
    }

    public CurrencyAccount findCAccountByUserAndType(String username, String currencyType){
        CurrencyAccount currencyAccount;
        BankAccount bankAccount = findBankAccountByUser(username);
        currencyAccount = bankAccount.getCAccountByType(currencyType);

        return currencyAccount;
    }

    public BankAccount findBankAccountByUser(String userName){
        BankAccount account;
        account = staffSet.get(userName);
        return account;
    }
}
