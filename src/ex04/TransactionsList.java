package ex04;

import java.util.UUID;

public interface TransactionsList {

    public void addTransaction(Transaction transaction);

    public void removeTransactionById(UUID transactionId)
            throws TransactionNotFoundException;

    public Transaction[] transformIntoArray();
}
