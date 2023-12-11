package sk1033;

import java.util.Scanner;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class TeacherPasswordUpdate {
	private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> teacherCollection;
    
    public static void TeacherPasswordUpdateStart() {
        try {
            initializeMongoDB();

           
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            System.out.print("Enter current password: ");
            String currentPassword = scanner.nextLine();

            
            Document userDocument = teacherCollection.find(Filters.eq("username", username)).first();
            if (userDocument != null && userDocument.getString("password").equals(currentPassword)) {
                System.out.print("Enter new password: ");
                String newPassword = scanner.nextLine();

               
                updatePassword(username, newPassword);

                System.out.println("Password updated successfully!");
            } else {
                System.out.println("Invalid username or password. Password update failed.");
            }

            
            mongoClient.close();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private static void initializeMongoDB() {
        try {
        	String connectionString = "mongodb+srv://pfy1:uol123@timetablemanagement.uq12hfp.mongodb.net/?retryWrites=true&w=majority";
            MongoClient mongoClient = MongoClients.create(connectionString);
                        
            database = mongoClient.getDatabase("Users_Teacher");
           
            teacherCollection = database.getCollection("TT_Users");
        } catch (Exception e) {
            System.err.println("Error initializing MongoDB: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void updatePassword(String username, String newPassword) {
        try {
            
            teacherCollection.updateOne(
                    Filters.eq("username", username),
                    new Document("$set", new Document("password", newPassword))
            );
        } catch (Exception e) {
            System.err.println("Error updating password: " + e.getMessage());
        }
    }

	public void setMongoClient(Object object) {
		// TODO Auto-generated method stub
		
	}
}
