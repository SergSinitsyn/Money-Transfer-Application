package application;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String description) {
        super(description);
    }

}
