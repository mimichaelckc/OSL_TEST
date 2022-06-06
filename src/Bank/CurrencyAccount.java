package Bank;

import java.math.BigDecimal;
import Exception.*;
public class CurrencyAccount {
    BigDecimal balance;
    String currencyName;
    /*
     * prepared-field for future usage
     * private double exchangeRate;
     * */

    public CurrencyAccount(String currencyName,BigDecimal balance) {
        this.balance = balance;
        this.currencyName = currencyName;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


    public void cashIn(BigDecimal amt) {

        this.balance = this.balance.add(amt);

    }


    public void cashOut(BigDecimal amt) throws InsufficientFundsException {
        if(this.balance.subtract(amt).compareTo(BigDecimal.valueOf(0))==-1)
            throw new InsufficientFundsException(this.balance);
        this.balance = this.balance.subtract(amt);
    }


    public BigDecimal getBalance() {
        return this.balance;
    }
}
