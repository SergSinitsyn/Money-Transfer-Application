package application;

public class IllegalTransactionException extends Exception {
    IllegalTransactionException(String description) {
        super(description);
    }
}
