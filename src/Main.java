import java.util.HashMap;
import java.util.Scanner;

import Command.*;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        BankOperation cmd;
        int option;
        boolean cond = true;

        //use HashMap to do the option mapping
        HashMap<Integer, BankOperation> optSet = new HashMap<>();
        optSet.put(1, new CreateAccountOperation());
        optSet.put(2, new DepositOperation());
        optSet.put(3, new WithdrawOperation());
        optSet.put(4, new TransferOperation());
        optSet.put(5, new ShowBalanceOperation());
        optSet.put(6, new TransactionDisplayOperation());
        optSet.put(7, new ExitOperation());

        while(cond){
            try {
                //display the option list
                showOption();
                option = Integer.parseInt(input.next());

                //map to the hashmap and execute the corresponding command
                cmd = optSet.get(option);
                cmd.execute();

            }
            catch (Exception e){
                e.printStackTrace();
                System.out.println("Please input the valid option");
                System.out.println();

            }
        }
    }

    public static void showOption() {
        System.out.println("============================================");
        System.out.println("\t\t\tOSL Banking System");
        System.out.println("Feature List: ");
        System.out.println("1. Account Creation");
        System.out.println("2. Money Deposit");
        System.out.println("3. Money Withdrawal");
        System.out.println("4. Money Transfer");
        System.out.println("5. Show Account Balance");
        System.out.println("6. Display Transaction History");
        System.out.println("7. Exit");
        System.out.println();
        System.out.println("Please select an operation by entering [1-7]:");
    }
}
