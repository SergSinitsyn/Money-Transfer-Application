package ex01;

public class UserIdsGenerator {
    private static UserIdsGenerator instance;
    private int lastGeneratedID;

    public static synchronized UserIdsGenerator getInstance() {
        if (instance == null) {
            instance = new UserIdsGenerator();
        }
        return instance;
    }

    public int generateId() {
        return ++lastGeneratedID;
    }

    private UserIdsGenerator() {
        lastGeneratedID = 0;
    }
}
