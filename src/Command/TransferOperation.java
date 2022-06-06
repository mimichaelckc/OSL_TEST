package Command;

import Bank.CentralSystem;

public class TransferOperation implements BankOperation{
    @Override
    public void execute() {
        CentralSystem centralSystem = CentralSystem.getInstance();
        centralSystem.transfer();
    }
}
