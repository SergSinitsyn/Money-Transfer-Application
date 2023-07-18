package ex02;

public class Program {
    public static void main(String[] args) {
        UsersList usersList = new UsersArrayList();
        usersList.addUser(new User("Vic", 1000));
        usersList.addUser(new User("Bob", 2000));
        usersList.addUser(new User("Vic", 3400));
        usersList.addUser(new User("Vic", 1000));
        usersList.addUser(new User("Bob", 2000));
        usersList.addUser(new User("Vic", 3400));
        usersList.addUser(new User("Vic", 1000));
        usersList.addUser(new User("Bob", 2000));
        usersList.addUser(new User("Vic", 3400));
        usersList.addUser(new User("Vic", 1000));
        usersList.addUser(new User("Bob", 2000));
        usersList.addUser(new User("Vic", 3400));


        try {
            System.out.println(usersList.getUserByIndex(3).getName());
        } catch (UserNotFoundException exception) {
            System.out.println(exception);
        }

        try {
            System.out.println(usersList.getUserById(2).getName());
        } catch (UserNotFoundException exception) {
            System.out.println(exception);
        }

        try {
            System.out.println(usersList.getUserByIndex(11).getIdentifier());
        } catch (UserNotFoundException exception) {
            System.out.println(exception);
        }


        System.out.println(usersList.getUsersNumber());

    }
}
