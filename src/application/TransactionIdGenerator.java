package application;

import java.util.UUID;

public class TransactionIdGenerator {
    public static UUID generateId() {
        return UUID.randomUUID();
    }
}
