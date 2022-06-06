package Exception;

public class CurrencyAccountNotFound extends Exception{
    private String client;
    private String account;
    public CurrencyAccountNotFound(String client, String account) {

        this.client = client;
        this.account = account;
    }

    public String getClient() {
        return client;
    }

    public String getAccount() {
        return account;
    }
}
