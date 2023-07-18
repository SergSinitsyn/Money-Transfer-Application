package ex04;

public class TransactionNotFoundException extends Exception {
    TransactionNotFoundException(String description) {
        super(description);
    }
}
