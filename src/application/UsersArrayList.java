package application;


public class UsersArrayList implements UsersList {

    private User[] users;
    private int size;

    public UsersArrayList() {
        users = new User[10];
        size = 0;
    }

    public void addUser(User user) {
        if (size == users.length) {
            int newCapacity = users.length + (users.length / 2);
            User[] newUsers = new User[newCapacity];
            System.arraycopy(users, 0, newUsers, 0, users.length);
            users = newUsers;
        }
        int index = 0;
        while (index < users.length && users[index] != null) {
            index++;
        }
        users[index] = user;
        size++;
    }

    public User getUserById(int id) throws UserNotFoundException {
        for (User user : users) {
            if ((user != null) && (user.getIdentifier() == id)) {
                return user;
            }
        }
        throw new UserNotFoundException(
                "User with ID " + id + " not found");
    }

    public User getUserByIndex(int index) throws UserNotFoundException {
        if ((index < 0) || (index >= size) || (users[index] == null)) {
            throw new UserNotFoundException(
                    "User with index " + index + " not found");
        }
        return users[index];
    }

    public int getUsersNumber() {
        return size;
    }

    public void addTransaction(int userId, Transaction transaction)
            throws UserNotFoundException {
        getUserById(userId).getTransactions().addTransaction(transaction);
    }
}
