package ex04;

public class Program {
    private static TransactionsService transactionsService =
            new TransactionsService();

    public static void main(String[] args) {

        transactionsService.addUser(new User("Vic", 1000));
        transactionsService.addUser(new User("Boc", 2000));
        transactionsService.addUser(new User("Max", 3000));

        System.out.println("\nBalance of all users");
        try {
            System.out.println(transactionsService.getUserBalance(1));
            System.out.println(transactionsService.getUserBalance(2));
            System.out.println(transactionsService.getUserBalance(3));
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\nPerform 3 transfers");
        try {
            transactionsService.
                    performTransferTransaction(1, 2, 1);
            transactionsService.
                    performTransferTransaction(2, 3, 10);
            transactionsService.
                    performTransferTransaction(1, 3, 100);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IllegalTransactionException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\nBalance of all users");
        try {
            System.out.println(transactionsService.getUserBalance(1));
            System.out.println(transactionsService.getUserBalance(2));
            System.out.println(transactionsService.getUserBalance(3));
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\nPrint Users Transactions");
        try {
            printUserTransactions(1);
            printUserTransactions(2);
            printUserTransactions(3);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\nDelete 1st transaction of 1st user");
        System.out.println("Delete 1st transaction of 3rd user");

        try {
            transactionsService.removeUserTransaction(
                    1,
                    transactionsService.getUserTransfers(1)[0].getIdentifier());
            transactionsService.removeUserTransaction(
                    3,
                    transactionsService.getUserTransfers(3)[0].getIdentifier());
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        } catch (TransactionNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\nPrint Users Transactions");
        try {
            printUserTransactions(1);
            printUserTransactions(2);
            printUserTransactions(3);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\nUnpaired transactions");
        try {
            Transaction[] unpairedTransactions = transactionsService.
                    getUnpairedTransactions();
            for (int i = 0; i < transactionsService.
                    getUnpairedTransactions().length; i++) {
                System.out.println(unpairedTransactions[i].getIdentifier());
            }
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printUserTransactions(int identifier)
            throws UserNotFoundException {
        System.out.println("Transactions of user " + identifier);
        for (int i = 0; i < transactionsService.
                getUserTransfers(identifier).length; i++) {
            System.out.println(transactionsService.
                    getUserTransfers(identifier)[i].getIdentifier());
        }
    }
}
