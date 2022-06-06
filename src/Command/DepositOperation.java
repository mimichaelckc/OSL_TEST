package Command;

import Bank.CentralSystem;

public class DepositOperation implements BankOperation{
    @Override
    public void execute() {
        CentralSystem centralSystem = CentralSystem.getInstance();
        centralSystem.deposit();
    }
}
