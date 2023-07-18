package ex04;

public class UserIdsGenerator {

    private static UserIdsGenerator instance;
    private int lastGeneratedID;

    public static synchronized UserIdsGenerator getInstance() {
        if (instance == null) {
            instance = new UserIdsGenerator();
        }
        return instance;
    }

    private UserIdsGenerator() {
        lastGeneratedID = 0;
    }

    public int generateId() {
        return ++lastGeneratedID;
    }

}
