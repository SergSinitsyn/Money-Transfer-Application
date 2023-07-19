package ex05;

import java.util.Scanner;
import java.util.UUID;

public class Menu {

    boolean devMode;

    private TransactionsService transactionsService;

    public Menu(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    public void displayMenu(boolean devMode) {
        this.devMode = devMode;
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        do {
            System.out.println("---------------------------------------------------------");
            System.out.println("1. Add a user");
            System.out.println("2. View user balances");
            System.out.println("3. Perform a transfer");
            System.out.println("4. View all transactions for a specific user");
            System.out.println("5. DEV – remove a transfer by ID");
            System.out.println("6. DEV – check transfer validity");
            System.out.println("7. Finish execution");

            String[] input = scanner.nextLine().split(" ");
            if (!checkInputCommand(input, 1)) {
                continue;
            }

            try {
                choice = Integer.parseInt(input[0]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                continue;
            }

            switch (choice) {
                case 1:
                    addUser(scanner);
                    break;
                case 2:
                    viewUserBalances(scanner);
                    break;
                case 3:
                    performTransfer(scanner);
                    break;
                case 4:
                    viewAllTransactions(scanner);
                    break;
                case 5:
                    if (isDevMode()) {
                        removeTransferByID(scanner);
                    } else {
                        System.out.println("Unavailable in standard mode.");
                    }
                    break;
                case 6:
                    if (isDevMode()) {
                        checkTransferValidity();
                    } else {
                        System.out.println("Unavailable in standard mode.");
                    }
                    break;
                case 7:
                    System.out.println("Finishing execution...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 7);
    }

    private void addUser(Scanner scanner) {
        System.out.println("Enter a user name and a balance");
        String[] input = scanner.nextLine().split(" ");

        if (!checkInputCommand(input, 2)) {
            return;
        }

        String userName = input[0];
        double balance = 0;
        try {
            balance = Double.parseDouble(input[1]);
            if (balance < 0) {
                System.out.println("Invalid input. Please enter a valid balance.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid balance.");
            return;
        }

        User newUser = new User(userName, balance);
        transactionsService.addUser(newUser);
        System.out.println("User with id = " + newUser.getIdentifier()
                + " is added");
    }

    private void viewUserBalances(Scanner scanner) {
        System.out.println("Enter a user ID");

        int userId;
        try {
            userId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid input.");
            return;
        }

        try {
            double balance = transactionsService.getUserBalance(userId);
            String name =
                    transactionsService.getUserList().getUserById(userId).getName();
            System.out.println(name + " - " + balance);
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void performTransfer(Scanner scanner) {
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        String[] input = scanner.nextLine().split(" ");

        if (!checkInputCommand(input, 3)) {
            return;
        }

        int senderId;
        int recipientId;
        double amount;
        try {
            senderId = Integer.parseInt(input[0]);
            recipientId = Integer.parseInt(input[1]);
            amount = Double.parseDouble(input[2]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid input.");
            return;
        }

        try {
            transactionsService.performTransferTransaction(
                    senderId, recipientId, amount);
            System.out.println("The transfer is completed");
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IllegalTransactionException e) {
            System.out.println(e.getMessage());
        }
    }

    private void viewAllTransactions(Scanner scanner) {
        System.out.println("Enter a user ID");

        int userId;
        try {
            userId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid input.");
            return;
        }

        try {
            Transaction[] transactions =
                    transactionsService.getUserTransfers(userId);
            if (transactions.length > 0) {
                for (Transaction transaction : transactions) {
                    printTransaction(transaction);
                }
            } else {
                System.out.println("No transactions found for the user.");
            }
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }


    private void removeTransferByID(Scanner scanner) {
        System.out.println("Enter a user ID and a transfer ID");
        String[] input = scanner.nextLine().split(" ");

        if (!checkInputCommand(input, 2)) {
            return;
        }

        int userId;
        UUID transferId;
        try {
            userId = Integer.parseInt(input[0]);
            transferId = UUID.fromString(input[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid input.");
            return;
        }


        try {
            Transaction removedTransaction =
                    transactionsService
                            .getUserList().getUserById(userId)
                            .getTransactions().getTransactionById(transferId);
            transactionsService.removeUserTransaction(userId, transferId);

            StringBuilder message = new StringBuilder();
            message.append("Transfer ");
            message.append(transferCategoryToString(removedTransaction) + " ");
            message.append(removedTransaction.getRecipient().getName());
            message.append(" (id = " + removedTransaction.getRecipient().getIdentifier() + ") ");
            message.append(removedTransaction.getTransferAmount());
            message.append(" removed");

            System.out.println(message);
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (TransactionNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    private void checkTransferValidity() {
        Transaction[] transactions = transactionsService.getUnpairedTransactions();
        System.out.println("Check results:");
        if (transactions.length > 0) {
            for (Transaction transaction : transactions) {
                printUnacknowledgedTransaction(transaction);
            }
        } else {
            System.out.println("No unpaired transactions found.");
        }
    }

    private boolean isDevMode() {
        return devMode;
    }

    private boolean checkInputCommand(String[] input, int size) {
        if (input.length != size) {
            System.out.println("Invalid input format. Please try again.");
            return false;
        }
        return true;
    }

    private static void printTransaction(Transaction transaction) {
        StringBuilder message = new StringBuilder();

        message.append(transferCategoryToString(transaction) + " ");
        message.append(transaction.getRecipient().getName());
        message.append("(id = " + transaction.getRecipient().getIdentifier() + ") ");
        message.append(transaction.getTransferAmount());
        message.append(" with id = " + transaction.getIdentifier());
        System.out.println(message);
    }

    private static void printUnacknowledgedTransaction(Transaction transaction) {
        StringBuilder message = new StringBuilder();
        message.append(transaction.getSender().getName());
        message.append("(id = " + transaction.getSender().getIdentifier() + ") ");
        message.append("has an unacknowledged transfer id = ");
        message.append(transaction.getIdentifier());
        message.append(" " + transferCategoryToString(transaction) + " ");
        message.append(transaction.getRecipient().getName());
        message.append("(id = " + transaction.getRecipient().getIdentifier() + ") ");
        message.append("for " + transaction.getTransferAmount());
        System.out.println(message);
    }

    private static String transferCategoryToString(Transaction transaction) {
        if (transaction.getTransferCategory() == TransferСategory.DEBIT) {
            return "From";
        } else {
            return "To";
        }
    }
}
