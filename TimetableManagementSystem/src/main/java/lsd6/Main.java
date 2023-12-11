package lsd6;

import java.util.ArrayList;



import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;

import java.util.function.Consumer;


class Feedback {
	private String stuId;
   private String teacherId;
   private String feedbackText;
   private String reply;

   public Feedback(String stuId, String teacherId, String feedbackText, String reply) {
       this.stuId = stuId;
       this.teacherId = teacherId;
       this.feedbackText = feedbackText;
       this.reply = reply;
   }

   public String getStuId() {
       return stuId;
   }

   public String getTeacherId() {
       return teacherId;
   }

   public String getFeedbackText() {
       return feedbackText;
   }

   public String getReply() {
       return reply;
   }
}

 class FeedbackDatabase {
	    private MongoCollection<Document> feedbackCollection;

	    public FeedbackDatabase() {
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

	    public void addFeedback(Feedback feedback) {
	        Document feedbackDocument = new Document("Stu_ID", feedback.getStuId())
	                .append("Teacher_ID", feedback.getTeacherId())
	                .append("Feedback_Text", feedback.getFeedbackText())
	                .append("Reply", feedback.getReply());

	        feedbackCollection.insertOne(feedbackDocument);
	        System.out.println("Feedback added successfully!");
	    }

	    public void displayFeedback() {
	        System.out.println("List of Feedback:");
	        feedbackCollection.find().forEach((Consumer<? super Document>) document ->
	                System.out.println("Student ID: " + document.get("Stu_ID") +
	                        ", Teacher ID: " + document.get("Teacher_ID") +
	                        ", Feedback Text: " + document.get("Feedback_Text") +
	                        ", Reply: " + document.get("Reply")));
	    }



	    public void updateFeedback(String studentId, String teacherId, String newReply) {
	        Document filter = new Document("Stu_ID", studentId).append("Teacher_ID", teacherId);
	        Document update = new Document("$set", new Document("Reply", newReply));

	        feedbackCollection.updateOne(filter, update);
	        System.out.println("Feedback updated successfully!");
	    }

	    public void deleteFeedback(String studentId, String teacherId) {
	        Document filter = new Document("Stu_ID", studentId).append("Teacher_ID", teacherId);
	        feedbackCollection.deleteOne(filter);
	        System.out.println("Feedback deletion is successful");
	    }
	}






public class Main {
	public static void main(String[] args) {
        FeedbackDatabase feedbackDatabase = new FeedbackDatabase();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Feedback");
            System.out.println("2. Display Feedback");
            System.out.println("3. Update Feedback");
            System.out.println("4. Delete Feedback");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter Student ID: ");
                    String studentId = scanner.nextLine();
                    System.out.print("Enter Teacher ID: ");
                    String teacherId = scanner.nextLine();
                    System.out.print("Enter Feedback Text: ");
                    String feedbackText = scanner.nextLine();
                    System.out.print("Enter Reply: ");
                    String reply = scanner.nextLine();

                    Feedback feedbackToAdd = new Feedback(studentId, teacherId, feedbackText, reply);
                    feedbackDatabase.addFeedback(feedbackToAdd);
                    break;

                case 2:
                    feedbackDatabase.displayFeedback();
                    break;

                case 3:
                    System.out.print("Enter Student ID: ");
                    String updateStudentId = scanner.nextLine();
                    System.out.print("Enter Teacher ID: ");
                    String updateTeacherId = scanner.nextLine();
                    System.out.print("Enter New Reply: ");
                    String newReply = scanner.nextLine();

                    feedbackDatabase.updateFeedback(updateStudentId, updateTeacherId, newReply);
                    break;

                case 4:
                    System.out.print("Enter Student ID: ");
                    String deleteStudentId = scanner.nextLine();
                    System.out.print("Enter Teacher ID: ");
                    String deleteTeacherId = scanner.nextLine();

                    feedbackDatabase.deleteFeedback(deleteStudentId, deleteTeacherId);
                    break;

                case 5:
                    System.out.println("Exiting the program Successfully");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}










