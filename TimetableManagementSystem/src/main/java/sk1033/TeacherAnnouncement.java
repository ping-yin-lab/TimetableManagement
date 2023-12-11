package sk1033;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.InputMismatchException;
import java.util.Scanner;
import com.mongodb.client.FindIterable;

public class TeacherAnnouncement {

    
	private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> announcementCollection;

    public void TeacherAnnouncementStart() {
        
            initializeMongoDB(); 
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the subject of announcement: ");
            String subject = scanner.nextLine();

            System.out.print("Enter the description of announcement: ");
            String announcement = scanner.nextLine();
           
            insertAnnouncement(subject, announcement);

            
            mongoClient.close();
    }

    static void initializeMongoDB() {
    	try {
            
            String connectionString = "mongodb+srv://pfy1:uol123@timetablemanagement.uq12hfp.mongodb.net/?retryWrites=true&w=majority";
            MongoClient mongoClient = MongoClients.create(connectionString);
                        
            database = mongoClient.getDatabase("Announcement");
           
            announcementCollection = database.getCollection("TT_Teacher_Announcement");
        } 
    	catch (Exception e) {
            System.err.println("Error initializing MongoDB: " + e.getMessage());
            System.exit(1);
        }
    }

    static void insertAnnouncement(String subject, String announcement) {
        try {
          
            Document announcementDocument = new Document()
                    .append("subject", subject)
                    .append("announcement", announcement);

            
            announcementCollection.insertOne(announcementDocument);

            System.out.println("Announcement published!");
        } catch (Exception e) {
            System.err.println("Error publishing announcement: " + e.getMessage());
        }
    }
    public static void getAnnouncements() {
        try {
        	initializeMongoDB();
            FindIterable<Document> announcements = announcementCollection.find();

            
            System.out.println("Announcements:");
            for (Document announcement : announcements) {
                System.out.println("Subject : " + announcement.getString("subject"));
                System.out.println("Announcement : " + announcement.getString("announcement"));
                System.out.println("--------------");
            }
        } catch (Exception e) {
            System.err.println("Error retrieving announcements: " + e.getMessage());
        }
    }
}
