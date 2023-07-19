package ex05;

public class IllegalTransactionException extends Exception {
    IllegalTransactionException(String description) {
        super(description);
    }
}
