package Bank;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionRecord {
    private LocalDateTime trxDate;
    private String currency;
    private String operationType;
    private String operationDesc;
    private BigDecimal amt;

    public TransactionRecord(String currency, String operationType, BigDecimal amt) {
        this.trxDate = LocalDateTime.now();
        this.currency = currency;
        this.operationType = operationType;

        this.amt = amt;
    }

    public LocalDateTime getTrxDate() {
        return trxDate;
    }

    public void setTrxDate(LocalDateTime trxDate) {
        this.trxDate = trxDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }


    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }
}
