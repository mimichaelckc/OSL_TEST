package Command;

import Bank.CentralSystem;

public class CreateAccountOperation implements BankOperation{
    @Override
    public void execute() {
        CentralSystem centralSystem = CentralSystem.getInstance();
        centralSystem.createAccount();
    }
}
