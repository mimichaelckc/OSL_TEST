package Command;

import Bank.CentralSystem;

public class WithdrawOperation implements BankOperation{
    @Override
    public void execute() {
        CentralSystem centralSystem = CentralSystem.getInstance();
        centralSystem.withdrawFromUser();
    }
}
