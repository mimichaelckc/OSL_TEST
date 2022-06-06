package Command;

import Bank.CentralSystem;

public class ShowBalanceOperation implements BankOperation{
    @Override
    public void execute() {
        CentralSystem centralSystem = CentralSystem.getInstance();
        centralSystem.listBalance();
    }
}
