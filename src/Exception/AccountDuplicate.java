package Exception;

public class AccountDuplicate extends Exception{
    public AccountDuplicate(String currencyType) {
        System.out.println("This user already have this "+currencyType+" account!");
    }
}
