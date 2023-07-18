package ex04;

import java.util.UUID;

public class TransactionsService {

    UsersList userList;

    public TransactionsService() {
        this.userList = new UsersArrayList();
    }

    public void addUser(User user) {
        userList.addUser(user);
    }

    public double getUserBalance(int userId) throws UserNotFoundException {
        return userList.getUserById(userId).getBalance();
    }

    public void performTransferTransaction(int senderId, int recipientId, double amount) throws UserNotFoundException, IllegalTransactionException {
        double senderBalance = userList.getUserById(senderId).getBalance();
        double recipientBalance = userList.getUserById(recipientId).getBalance();
        if (senderBalance < amount) {
            throw new IllegalTransactionException("Illegal transaction: " + " user " + senderId + " send to " + " user " + recipientId + " amount " + amount);
        }

        // здесть сделать генерацию id
        UUID id = TransactionIdGenerator.generateId();
        Transaction debitTransaction = new Transaction(id, userList.getUserById(recipientId), userList.getUserById(senderId), TransferСategory.DEBIT, amount);
        Transaction creditTransaction = new Transaction(id, userList.getUserById(senderId), userList.getUserById(recipientId), TransferСategory.CREDIT, amount);

        userList.addTransaction(senderId, debitTransaction);
        userList.addTransaction(recipientId, creditTransaction);
    }


    public Transaction[] getUserTransfers(int userId) throws UserNotFoundException {
        return userList.getUserById(userId).getTransactions().transformIntoArray();
    }

    public void removeUserTransaction(int userId, UUID transactionId) throws UserNotFoundException, TransactionNotFoundException {
        userList.getUserById(userId).getTransactions().removeTransactionById(transactionId);
    }

    public Transaction[] getUnpairedTransactions() throws UserNotFoundException {

        TransactionsList unpairedTransactions = new TransactionsLinkedList();
        for (int i = 0; i < userList.getUsersNumber(); i++) {
            TransactionsList userTransactions = userList.getUserByIndex(i).getTransactions();
            for (int j = 0; j < userList.getUsersNumber(); j++) {
                if (i == j) continue;
                Transaction[] otherUserTransaction = userList.getUserByIndex(j).getTransactions().transformIntoArray();
                for (int k = 0; k < otherUserTransaction.length; k++) {
                    try {
                        userTransactions.removeTransactionById(otherUserTransaction[k].getIdentifier());
                    } catch (TransactionNotFoundException e) {
                    }
                }
            }
            Transaction[] unpairedUserTransactions = userTransactions.transformIntoArray();
            for (int l = 0; l < unpairedUserTransactions.length; l++) {
                unpairedTransactions.addTransaction(unpairedUserTransactions[l]);
            }
        }
        return unpairedTransactions.transformIntoArray();
    }
}

