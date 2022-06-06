package Exception;

public class BankAccountNotFound extends Exception{
    private String client;

    public BankAccountNotFound(String client) {

        this.client = client;
    }

    public String getClient() {
        return client;
    }
}
