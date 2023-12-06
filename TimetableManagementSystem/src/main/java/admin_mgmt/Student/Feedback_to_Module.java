import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

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
        feedbackCollection.find().forEach(document ->
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

