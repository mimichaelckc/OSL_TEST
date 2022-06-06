package Command;

import Bank.CentralSystem;

public class TransactionDisplayOperation implements BankOperation{
    @Override
    public void execute() {
        CentralSystem centralSystem = CentralSystem.getInstance();
        centralSystem.displayTransaction();
    }
}
