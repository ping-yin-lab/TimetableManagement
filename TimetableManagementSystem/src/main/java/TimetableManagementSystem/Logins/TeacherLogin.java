package TimetableManagementSystem.Logins;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import TimetableManagementSystem.Operations.*;

import org.bson.Document;


import java.util.InputMismatchException;
import java.util.Scanner;

public class TeacherLogin {

    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> teacherCollection;

    public static void teacherLoginStart() {
        try {
            initializeMongoDB();

            Scanner scanner = new Scanner(System.in);

            while (true) {
                if (Session.getCurrentUser() == null) {
                    System.out.println("Teacher:");
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
                            login(scanner, teacherCollection);
                            break;
                        case 2:
                            System.out.println("Exiting the Teacher module.");
                            mongoClient.close();
                            System.exit(0);
                        default:
                            System.out.println("Invalid option. Please choose a valid option.");
                    }
                } else {
                    String[] currentuser = {Session.getCurrentUser()};
                    System.out.println("Techer (Logged in as " + Session.getCurrentUser() + "):");
                    System.out.println("1. Perform Teacher Operations");
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
                            teacherOperation teachersys = new teacherOperation();
                            teachersys.main(currentuser);
                            // performStudentOperations();
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
                        
            database = mongoClient.getDatabase("Users_Teacher");
           
            teacherCollection = database.getCollection("TT_Users");
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
            System.out.println("Perform Teacher Operations...");
            TeacherAnnouncement teacherAnnouncement = new TeacherAnnouncement();
            System.out.println("Techer operations:");
            System.out.println("1. Upload Announcement");
            System.out.println("2. Read Announcements");
            Scanner opscanner = new Scanner(System.in);
            System.out.print("Choose an option: ");
            int choice;
            choice = opscanner.nextInt();
            opscanner.nextLine(); 
            
            switch (choice) {
            case 1:
                teacherAnnouncement.TeacherAnnouncementStart();
                break;
            case 2:
            	teacherAnnouncement.getAnnouncements();
                break;
            default:
                System.out.println("Invalid option. Please choose a valid option.");
        }
        }catch (Exception e) {
            System.err.println("An unexpected error occurred during Announcement operations: " + e.getMessage());
        }
    }

    private static void logout() {
        try {
            System.out.println("Logging out...");
            Session.resetSession();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred during logout: " + e.getMessage());
        }
    }
}