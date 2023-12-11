package Stu_Search;

import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.Scanner;

public class studentDatabase {
    private MongoCollection<Document> searchcollection;
    private boolean exitRequested = false;

    public studentDatabase() {
        String connectionString = "mongodb+srv://pfy1:uol123@timetablemanagement.uq12hfp.mongodb.net/?retryWrites=true&w=majority";
        String databaseName = "Users_Student";
        String collectionName = "TT_Users";

        try {
            ConnectionString connString = new ConnectionString(connectionString);
            MongoClient mongoClient = MongoClients.create(connectionString);
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            searchcollection = database.getCollection(collectionName);
            System.out.println("Successfully connected to student Database");
        } catch (Exception e) {
            System.err.println("Error connecting: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error connecting to the database");
        }
    }

    public void searchSTUById(String studentId) {
        Document studentDocument = searchcollection.find(new Document("Student_ID", studentId)).first();

        if (studentDocument != null) {
            System.out.println("Details of the given userID are:");
            System.out.println("Student ID is " + studentDocument.get("Student_ID"));
            System.out.println("First Name is " + studentDocument.get("first_name"));
            System.out.println("Last Name is " + studentDocument.get("last_name"));
            System.out.println("Course selected: " + studentDocument.get("course"));
            System.out.println("Username of the student: " + studentDocument.get("username"));
        } else {
            System.out.println("Given Student ID not found.");
        }
    }

    // New method to gracefully exit the program
    public void shutdown() {
        System.out.println("Exiting the program.");
        exitRequested = true;
    }

    // New method to check if exit is requested
    public boolean isExitRequested() {
        return exitRequested;
    }

    public static void main(String[] args) {
        studentDatabase databaseInstance = new studentDatabase();
        Scanner scanner = new Scanner(System.in);

        while (!databaseInstance.isExitRequested()) {
            System.out.println("1. Search Student by ID");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter Student ID: ");
                    String studentId = scanner.nextLine();
                    databaseInstance.searchSTUById(studentId);
                    break;

                case 2:
                    databaseInstance.shutdown();
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
