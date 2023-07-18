package ex00;

import java.util.UUID;

public class Program {


    public static void main(String[] args) {
        User vic1 = new User(1, "Vic", -1000);
        User vic = new User(1, "Vic", 1000);
        User bob = new User(2, "Bob", 2000);
        UUID uuid1 = UUID.randomUUID();
        Transaction first_1 = new Transaction(uuid1, bob, vic,
                TransferСategory.OUTCOME, -400);
        Transaction first_2 = new Transaction(uuid1, vic, bob,
                TransferСategory.INCOME, 400);
        printUser(vic1);
        printUser(vic);
        printUser(bob);

        printTransaction(first_1);
        printTransaction(first_2);
    }

    private static void printUser(User user) {
        System.out.println("name: " + user.getName()
                + ", id: " + user.getIdentifier()
                + ", balance: " + user.getBalance());
    }

    private static void printTransaction(Transaction transaction) {
        System.out.println("id: " + transaction.getIdentifier()
                + ", Recipient: " + transaction.getRecipient().getName()
                + ", Sender: " + transaction.getSender().getName()
                + ", Transfer category " + transaction.getTransferCategory()
                + ", Transfer amount " + transaction.getTransferAmount());
    }


}
