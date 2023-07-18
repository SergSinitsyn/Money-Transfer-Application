package ex03;

import java.util.UUID;

public class Transaction {
    private UUID identifier;
    private User recipient;
    private User sender;
    private TransferСategory transferCategory;
    private double transferAmount;

    public Transaction(UUID identifier, User recipient, User sender,
                       TransferСategory transferCategory,
                       double transferAmount) {
        if (!checkTransferCategory(transferCategory, transferAmount)) {
            return;
        }
        if (!checkTransferAmount(recipient, sender, transferCategory,
                transferAmount)) {
            return;
        }
        this.identifier = identifier;
        this.recipient = recipient;
        this.sender = sender;
        this.transferCategory = transferCategory;
        this.transferAmount = transferAmount;
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public void setIdentifier(UUID identifier) {
        this.identifier = identifier;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        if (!checkTransferAmount(recipient, this.sender,
                this.transferCategory, this.transferAmount)) {
            return;
        }
        this.recipient = recipient;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        if (!checkTransferAmount(this.recipient, sender,
                this.transferCategory, this.transferAmount)) {
            return;
        }
        this.sender = sender;
    }

    public TransferСategory getTransferCategory() {
        return transferCategory;
    }

    public void setTransferCategory(TransferСategory transferCategory) {
        if (!checkTransferCategory(transferCategory, this.transferAmount)) {
            return;
        }
        this.transferCategory = transferCategory;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(double transferAmount) {
        if (!checkTransferCategory(this.transferCategory, transferAmount)) {
            return;
        }
        this.transferAmount = transferAmount;
    }

    private boolean checkTransferCategory(TransferСategory transferCategory,
                                          double transferAmount) {
        return (((transferCategory == TransferСategory.OUTCOME)
                && (transferAmount < 0))
                || ((transferCategory == TransferСategory.INCOME)
                && (transferAmount > 0)));
    }

    private boolean checkTransferAmount(User recipient, User sender,
                                        TransferСategory transferCategory,
                                        double transferAmount) {
        if ((transferCategory == TransferСategory.OUTCOME)
                && (sender.getBalance() + transferAmount > 0)) {
            return true;
        }

        if ((transferCategory == TransferСategory.INCOME)
                && (recipient.getBalance() >= transferAmount)) {
            return true;
        }
        return false;
    }
}
