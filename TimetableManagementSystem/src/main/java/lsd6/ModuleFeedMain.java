package lsd6;


import org.bson.Document;


import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.Scanner;
import java.util.function.Consumer;

class ModuleFeedback {
    private String stuId;
    private String moduleName;
    private String feedbackText;
    private String rating;

    public ModuleFeedback(String stuId, String moduleName, String feedbackText, String rating) {
        this.stuId = stuId;
        this.moduleName = moduleName;
        this.feedbackText = feedbackText;
        this.rating = rating;
    }
    
    public String getStuId() {
        return stuId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getFeedbackText() {
        return feedbackText;
    }
    
    public String getRating() {
        return rating;
    }
}

class ModuleFeedbackDatabase {
    private MongoCollection<Document> moduleFeedbackCollection;
    private boolean exitRequested = false;

    public ModuleFeedbackDatabase() {
        String connectionString = "mongodb+srv://pfy1:uol123@timetablemanagement.uq12hfp.mongodb.net/?retryWrites=true&w=majority";
        String databaseName = "ModuleFeedbackDB";
        String collectionName = "ModuleFeedback";

        try {
            ConnectionString connString = new ConnectionString(connectionString);
            MongoClient mongoClient = MongoClients.create(connectionString);
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            moduleFeedbackCollection = database.getCollection(collectionName);
            System.out.println("Successfully connected to Module Feedback Database");
        } catch (Exception e) {
            System.err.println("Error connecting: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void addModuleFeedback(ModuleFeedback moduleFeedback) {
        Document feedbackDocument = new Document("Stu_ID", moduleFeedback.getStuId())
                .append("Module_Name", moduleFeedback.getModuleName())
                .append("Feedback_Text", moduleFeedback.getFeedbackText())
                .append("Rating", moduleFeedback.getRating());

        moduleFeedbackCollection.insertOne(feedbackDocument);
        System.out.println("Module Feedback added successfully!");
    }
    public void displayModuleFeedback() {
        System.out.println("List of Module Feedback:");
        moduleFeedbackCollection.find().forEach((Consumer<? super Document>) document ->
                System.out.println("Student ID: " + document.get("Stu_ID") +
                        ", Module Name: " + document.get("Module_Name") +
                        ", Feedback Text: " + document.get("Feedback_Text") +
                        ", Rating: " + document.get("Rating")));
    }
    public void clearModuleFeedbackCollection() {
        moduleFeedbackCollection.deleteMany(new Document());
    }
    public void shutdown() {
    	System.out.println("Exiting the program.");
    	exitRequested = true;
    }
}

public class ModuleFeedMain {
	public Scanner getScanner() {
	    return new Scanner(System.in);
	}

    public static void main(String[] args) {
        ModuleFeedbackDatabase moduleFeedbackDatabase = new ModuleFeedbackDatabase();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Module Feedback");
            System.out.println("2. Display Module Feedback");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter Student ID: ");
                    String studentId = scanner.nextLine();
                    System.out.print("Enter Module Name: ");
                    String moduleName = scanner.nextLine();
                    System.out.print("Enter Feedback Text: ");
                    String feedbackText = scanner.nextLine();
                    System.out.print("Enter Rating: ");
                    String rating = scanner.nextLine();

                    ModuleFeedback moduleFeedbackToAdd = new ModuleFeedback(studentId, moduleName, feedbackText, rating);
                    moduleFeedbackDatabase.addModuleFeedback(moduleFeedbackToAdd);
                    break;

                case 2:
                    moduleFeedbackDatabase.displayModuleFeedback();
                    break;

                case 3:
                    moduleFeedbackDatabase.shutdown();
                    
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}



