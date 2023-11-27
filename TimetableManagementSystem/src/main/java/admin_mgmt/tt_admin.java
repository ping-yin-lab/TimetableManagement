package admin_mgmt;

import java.util.ArrayList;
import java.util.Scanner;

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class Database {
    private ArrayList<User> users;

    public Database() {
        this.users = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
        System.out.println("User added successfully!");
    }

    public void displayUsers() {
        System.out.println("List of Users:");
        for (User user : users) {
            System.out.println("Username: " + user.getUsername() + ", Password: " + user.getPassword());
        }
    }
}

public class tt_admin {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Database database = new Database();

        while (true) {
            System.out.println("1. Add User");
            System.out.println("2. Display Users");
            System.out.println("3. Exit");
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
                    System.out.println("Exiting the admin panel. Goodbye!");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
