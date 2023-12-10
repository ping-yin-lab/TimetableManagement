package admin_mgmt;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import java.util.Scanner;

public class StudentPasswordUpdate {
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> studentCollection;
    
    public static void main(String[] args) {
        try {
            initializeMongoDB();

            // Take input from the user
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            System.out.print("Enter current password: ");
            String currentPassword = scanner.nextLine();

            // Check if the user exists and the current password is correct
            Document userDocument = studentCollection.find(Filters.eq("username", username)).first();
            if (userDocument != null && userDocument.getString("password").equals(currentPassword)) {
                System.out.print("Enter new password: ");
                String newPassword = scanner.nextLine();

                // Update the password in the database
                updatePassword(username, newPassword);

                System.out.println("Password updated successfully!");
            } else {
                System.out.println("Invalid username or password. Password update failed.");
            }

            // Close MongoDB connection
            mongoClient.close();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private static void initializeMongoDB() {
        try {
        	String connectionString = "mongodb+srv://pfy1:uol123@timetablemanagement.uq12hfp.mongodb.net/?retryWrites=true&w=majority";
            MongoClient mongoClient = MongoClients.create(connectionString);
                        
            database = mongoClient.getDatabase("Users_Student");
           
            studentCollection = database.getCollection("TT_Users");
        } catch (Exception e) {
            System.err.println("Error initializing MongoDB: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void updatePassword(String username, String newPassword) {
        try {
            
            studentCollection.updateOne(
                    Filters.eq("username", username),
                    new Document("$set", new Document("password", newPassword))
            );
        } catch (Exception e) {
            System.err.println("Error updating password: " + e.getMessage());
        }
    }

}






    

