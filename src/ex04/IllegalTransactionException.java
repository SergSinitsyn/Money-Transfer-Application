package ex04;

public class IllegalTransactionException extends Exception {
    IllegalTransactionException(String description) {
        super(description);
    }
}
