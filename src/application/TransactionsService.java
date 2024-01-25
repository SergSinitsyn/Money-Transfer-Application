package application;

import java.util.HashMap;
import java.util.UUID;

public class TransactionsService {

    private UsersList userList;

    public UsersList getUserList() {
        return userList;
    }

    public void setUserList(UsersList userList) {
        this.userList = userList;
    }

    public TransactionsService() {
        this.userList = new UsersArrayList();
    }

    public void addUser(User user) {
        userList.addUser(user);
    }

    public double getUserBalance(int userId) throws UserNotFoundException {
        return userList.getUserById(userId).getBalance();
    }

    public void performTransferTransaction(int senderId,
                                           int recipientId,
                                           double amount)
            throws UserNotFoundException, IllegalTransactionException {

        if (amount <= 0) {
            throw new IllegalTransactionException(
                    "Illegal transaction: " + amount + " <= 0");
        }

        User sender = userList.getUserById(senderId);
        User recipient = userList.getUserById(recipientId);

        double senderBalance = sender.getBalance();
        double recipientBalance = recipient.getBalance();

        if (senderBalance < amount) {
            throw new IllegalTransactionException(
                    "Illegal transaction: "
                            + " user " + senderId
                            + " send to"
                            + " user " + recipientId
                            + " amount " + amount);
        }

        UUID id = TransactionIdGenerator.generateId();
        userList.addTransaction(senderId,
                new Transaction(id,
                        recipient, sender,
                        TransferСategory.CREDIT, -amount));
        userList.addTransaction(recipientId,
                new Transaction(id,
                        sender, recipient,
                        TransferСategory.DEBIT, amount));

        userList.getUserById(senderId).setBalance(senderBalance - amount);
        userList.getUserById(recipientId).setBalance(recipientBalance + amount);
    }


    public Transaction[] getUserTransfers(int userId)
            throws UserNotFoundException {
        return userList.getUserById(userId)
                .getTransactions().transformIntoArray();
    }

    public void removeUserTransaction(int userId, UUID transactionId)
            throws UserNotFoundException, TransactionNotFoundException {
        userList.getUserById(userId).getTransactions()
                .removeTransactionById(transactionId);
    }

    public Transaction[] getUnpairedTransactions() {
        HashMap<UUID, Transaction> unpairedTransactions = new HashMap<>();
        for (int i = 0; i < userList.getUsersNumber(); i++) {
            Transaction[] userTransactions;
            try {
                userTransactions =
                        userList.getUserByIndex(i).getTransactions().transformIntoArray();
            } catch (UserNotFoundException e) {
                continue;
            }
            for (int j = 0; j < userTransactions.length; j++) {
                UUID id = userTransactions[j].getIdentifier();
                if (unpairedTransactions.containsKey(id)) {
                    unpairedTransactions.remove(id);
                } else {
                    unpairedTransactions.put(id, userTransactions[j]);
                }
            }
        }
        return unpairedTransactions.values().toArray(new Transaction[0]);
    }
}

