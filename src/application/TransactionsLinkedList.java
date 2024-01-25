package application;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {

    private TransactionNode head = null;

    public void addTransaction(Transaction transaction) {
        TransactionNode newNode = new TransactionNode(transaction);
        if (head == null) {
            head = newNode;
        } else {
            TransactionNode currentNode = head;
            while (currentNode.getNext() != null) {
                currentNode = currentNode.getNext();
            }
            currentNode.setNext(newNode);
        }

    }

    public void removeTransactionById(UUID transactionId)
            throws TransactionNotFoundException {
        if (head == null) {
            throw new TransactionNotFoundException(
                    "transaction with non-existent ID" + transactionId);
        }

        if ((head.getTransaction().getIdentifier() != null)
                && (head.getTransaction().getIdentifier().equals(transactionId))) {
            head = head.getNext();
        } else {
            TransactionNode currentNode = head;
            while ((currentNode.getNext() != null)
                    && (!currentNode.getNext().getTransaction()
                    .getIdentifier().equals(transactionId))) {
                currentNode = currentNode.getNext();
            }

            if (currentNode.getNext() == null) {
                throw new TransactionNotFoundException(
                        "transaction with non-existent ID" + transactionId);
            }

            currentNode.setNext(currentNode.getNext().getNext());
        }
    }

    public Transaction[] transformIntoArray() {
        List<Transaction> transactionList = new ArrayList<>();
        TransactionNode currentNode = head;
        while (currentNode != null) {
            transactionList.add(currentNode.getTransaction());
            currentNode = currentNode.getNext();
        }
        return transactionList.toArray(new Transaction[0]);
    }

    public Transaction getTransactionById(UUID transactionId)
            throws TransactionNotFoundException {
        TransactionNode currentNode = head;
        while (currentNode != null) {
            if (!currentNode.getTransaction().getIdentifier().equals(transactionId)) {
                currentNode = currentNode.getNext();
            } else {
                return currentNode.getTransaction();
            }
        }
        throw new TransactionNotFoundException(
                "Transaction with ID " + transactionId + " not found");
    }
}
