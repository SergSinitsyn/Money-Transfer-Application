package ex01;


public class Program {

    public static void main(String[] args) {
        User vic1 = new User("Vic", 1000);
        User bob1 = new User("Bob", 2000);
        User vic2 = new User("Vic", 1000);
        User bob2 = new User("Bob", 2000);

        printUser(vic1);
        printUser(bob1);
        printUser(vic2);
        printUser(bob2);
    }

    public static void printUser(User user) {
        System.out.println("name: " + user.getName() + ", id: " + user.getIdentifier() + ", balance: " + user.getBalance());
    }
}
