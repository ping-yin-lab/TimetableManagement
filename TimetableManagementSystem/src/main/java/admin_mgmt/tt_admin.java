package admin_mgmt;

import java.util.Scanner;

public class tt_admin {
    public static Database database = new Database();

    // Edited by Xiao for Main Menu
    public void AdminPanel() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add User");
            System.out.println("2. Display Users");
            System.out.println("3. Log Out");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    User newUser = new User(username, password);
                    database.addUser(newUser);
                    break;
                case 2:
                    database.displayUsers();
                    break;
                case 3:
                    MainMenu.Start();
                case 4:
                    System.out.println("Exiting the admin panel. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
