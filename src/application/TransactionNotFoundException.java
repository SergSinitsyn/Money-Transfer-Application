package application;

public class TransactionNotFoundException extends Exception {
    TransactionNotFoundException(String description) {
        super(description);
    }
}
