package ex03;


import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        User vic = new User("Vic", 1000);
        User bob = new User("Bob", 2000);
        UUID uuid1 = UUID.randomUUID();
        Transaction first_1 = new Transaction(uuid1, bob, vic,
                TransferСategory.OUTCOME, -400);
        Transaction first_2 = new Transaction(uuid1, vic, bob,
                TransferСategory.INCOME, 400);

        vic.getTransactions().addTransaction(first_1);
        bob.getTransactions().addTransaction(first_2);

        System.out.println("List of transactions");
        System.out.println(vic.getTransactions()
                .transformIntoArray()[0].getTransferAmount());
        System.out.println(bob.getTransactions()
                .transformIntoArray()[0].getTransferAmount());
    }
}
