package Exception;

import java.math.BigDecimal;

public class InsufficientFundsException extends Exception{
    private BigDecimal amount;
    public InsufficientFundsException(BigDecimal amount)
    {
        this.amount = amount;
    }
    public BigDecimal getAmount()
    {
        return amount;
    }
}
