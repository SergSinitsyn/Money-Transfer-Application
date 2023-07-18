package ex04;

public class Program {
    public static void main(String[] args) {

        TransactionsService transactionsService = new TransactionsService();
        transactionsService.addUser(new User("Vic", 1000));
        transactionsService.addUser(new User("Boc", 2000));
        transactionsService.addUser(new User("Max", 3000));

        try {
            transactionsService.performTransferTransaction(1, 2, 500);
            transactionsService.performTransferTransaction(2, 3, 600);
            transactionsService.performTransferTransaction(3, 1, 700);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IllegalTransactionException e) {
            throw new RuntimeException(e);
        }

        try {
            transactionsService.removeUserTransaction(1, transactionsService.getUserTransfers(1)[0].getIdentifier());
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        } catch (TransactionNotFoundException e) {
            throw new RuntimeException(e);
        }


        try {
            System.out.println(transactionsService.getUnpairedTransactions()[0]);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}
