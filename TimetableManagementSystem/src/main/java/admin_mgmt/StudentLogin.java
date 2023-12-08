package admin_mgmt;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.InputMismatchException;
import java.util.Scanner;

class Session {
    private static String currentUser;

    static String getCurrentUser() {
        return currentUser;
    }

    static void setCurrentUser(String username) {
        currentUser = username;
    }

    static void resetSession() {
        currentUser = null;
    }
}

public class StudentLogin {

    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> studentCollection;

    public static void main(String[] args) {
        try {
            initializeMongoDB();

            Scanner scanner = new Scanner(System.in);

            while (true) {
                if (Session.getCurrentUser() == null) {
                    System.out.println("Student :");
                    System.out.println("1. Login");
                    System.out.println("2. Exit");

                    System.out.print("Choose an option: ");
                    int choice;
                    try {
                        choice = scanner.nextInt();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a number.");
                        scanner.nextLine();
                        continue;
                    }

                    switch (choice) {
                        case 1:
                            login(scanner, studentCollection);
                            break;
                        case 2:
                            System.out.println("Exiting the Student module.");
                            mongoClient.close();
                            System.exit(0);
                        default:
                            System.out.println("Invalid option. Please choose a valid option.");
                    }
                } else {
                    System.out.println("Student (Logged in as " + Session.getCurrentUser() + "):");
                    System.out.println("1. Perform Student Operations");
                    System.out.println("2. Logout");

                    System.out.print("Choose an option: ");
                    int choice;
                    try {
                        choice = scanner.nextInt();
                        scanner.nextLine(); 
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a number.");
                        scanner.nextLine();
                        continue;
                    }

                    switch (choice) {
                        case 1:
                            performStudentOperations();
                            break;
                        case 2:
                            logout();
                            break;
                        default:
                            System.out.println("Invalid option. Please choose a valid option.");
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private static void initializeMongoDB() {
        try {
            // Replace the connection string with your MongoDB connection string
            String connectionString = "mongodb+srv://pfy1:uol123@timetablemanagement.uq12hfp.mongodb.net/?retryWrites=true&w=majority";
            MongoClient mongoClient = MongoClients.create(connectionString);
                        
            database = mongoClient.getDatabase("Users_Student");
           
            studentCollection = database.getCollection("TT_Users");
        } catch (Exception e) {
            System.err.println("Error initializing MongoDB: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void login(Scanner scanner, MongoCollection<Document> collection) {
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            Document userDocument = collection.find(new Document("username", username)).first();

            if (userDocument != null && userDocument.getString("password").equals(password)) {
                System.out.println("Login successful!");
                Session.setCurrentUser(username);
            } else {
                System.out.println("Invalid username or password. Please try again.");
            }
        } catch (Exception e) {
            System.err.println("An unexpected error occurred during login: " + e.getMessage());
        }
    }

    private static void performStudentOperations() {
        try {
            System.out.println("Performing Student Operations...");
            // Add your student-specific operations here
        } catch (Exception e) {
            System.err.println("An unexpected error occurred during student operations: " + e.getMessage());
        }
    }

    private static void logout() {
        try {
            System.out.println("Logging out");
            Session.resetSession();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred during logout: " + e.getMessage());
        }
    }
}
