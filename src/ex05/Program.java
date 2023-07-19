package ex05;

public class Program {
    private static TransactionsService transactionsService =
            new TransactionsService();
    private static Menu menu = new Menu(transactionsService);

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Select the mode: production or dev");
            return;
        }

        String input = args[0];
        String prefix = "--profile=";
        String mode = null;

        if (input.startsWith(prefix)) {
            mode = input.substring(prefix.length());
            if (mode.equals("production")) {
                menu.displayMenu(false);
            } else if (mode.equals("dev")) {
                menu.displayMenu(true);
            }
        } else {
            System.out.println("Select the mode: production or dev");
        }


    }
}
