package ex04;


public interface UsersList {
    void addUser(User user);

    User getUserById(int id) throws UserNotFoundException;

    User getUserByIndex(int index) throws UserNotFoundException;

    int getUsersNumber();

    public void addTransaction(int userId, Transaction transaction) throws UserNotFoundException;
}
