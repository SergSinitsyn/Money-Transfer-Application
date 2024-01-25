package application;

import java.util.UUID;

public interface TransactionsList {

    public void addTransaction(Transaction transaction);

    public void removeTransactionById(UUID transactionId)
            throws TransactionNotFoundException;

    public Transaction[] transformIntoArray();

    public Transaction getTransactionById(UUID transactionId)
            throws TransactionNotFoundException;
}
