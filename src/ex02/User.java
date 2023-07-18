package ex02;

public class User {
    private int identifier;
    private String name;
    private double balance;

    public User(String name, double balance) {
        this.identifier = UserIdsGenerator.getInstance().generateId();
        this.name = name;
        if (balance < 0) {
            return;
        }
        this.balance = balance;
    }

    public int getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(double balance) {
        if (balance < 0) {
            return;
        }
        this.balance = balance;
    }
}
