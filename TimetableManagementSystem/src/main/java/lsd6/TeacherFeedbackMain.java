package lsd6;

import java.util.Scanner;
import java.util.function.Consumer;
import java.util.List;
import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.function.Consumer;



class TeacherFeedback {
    private String teacherId;
    private String feedbackText;
    private String rating;

    public TeacherFeedback(String teacherId, String feedbackText, String rating) {
        this.teacherId = teacherId;
        this.feedbackText = feedbackText;
        this.rating = rating;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public String getRating() {
        return rating;
    }
}

class TeacherFeedbackDatabase {
	 private MongoCollection<Document> feedbackCollection;

	    public TeacherFeedbackDatabase() {
	        String connectionString = "mongodb+srv://pfy1:uol123@timetablemanagement.uq12hfp.mongodb.net/?retryWrites=true&w=majority";
	        String databaseName = "FeedbackDB";
	        String collectionName = "FeedbackCollection";

	        try {
	            ConnectionString connString = new ConnectionString(connectionString);
	            MongoClient mongoClient = MongoClients.create(connectionString);
	            MongoDatabase database = mongoClient.getDatabase(databaseName);
	            feedbackCollection = database.getCollection(collectionName);
	            System.out.println("successfully connected to student Database");
	        } catch (Exception e) {
	            System.err.println("Error connecting: " + e.getMessage());
	            e.printStackTrace();
	            System.exit(1);
	        }
	    }

    public void displayTeacherFeedback(String teacherId) {
        System.out.println("List of Feedback for Teacher " + teacherId + ":");
        feedbackCollection.find(new Document("Teacher_ID", teacherId)).forEach((Consumer<? super Document>) document ->
                System.out.println("Student ID: " + document.get("Stu_ID") +
                        ", Feedback Text: " + document.get("Feedback_Text") +
                        ", Reply: " + document.get("Reply")));
    }

    // Add methods for updating and deleting feedback if needed
}

public class TeacherFeedbackMain {
    public static void main(String[] args) {
        TeacherFeedbackDatabase teacherFeedbackDatabase = new TeacherFeedbackDatabase();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Display Feedback (Teacher View)");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter Teacher ID: ");
                    String teacherId = scanner.nextLine();
                    teacherFeedbackDatabase.displayTeacherFeedback(teacherId);
                    break;

                case 2:
                    System.out.println("Exiting the program Successfully");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}

 