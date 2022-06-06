package Command;

public class ExitOperation implements BankOperation{
    @Override
    public void execute() {
        System.out.println("Thanks for using OSL Banking System! See you next time~");
        System.exit(0);
    }
}
