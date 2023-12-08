package TeacherAnnouncement;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TeacherAnnouncement {

    
	private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> announcementCollection;

    public static void main(String[] args) {
        try {
            initializeMongoDB(); 
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the subject of announcement: ");
            String title = scanner.nextLine();

            System.out.print("Enter the description of announcement: ");
            String content = scanner.nextLine();
           
            insertAnnouncement(title, content);

            
            mongoClient.close();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private static void initializeMongoDB() {
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

    private static void insertAnnouncement(String title, String content) {
        try {
          
            Document announcementDocument = new Document()
                    .append("title", title)
                    .append("content", content);

            
            announcementCollection.insertOne(announcementDocument);

            System.out.println("Announcement published!");
        } catch (Exception e) {
            System.err.println("Error publishing announcement: " + e.getMessage());
        }
    }
}
