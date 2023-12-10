package admin_mgmt;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class StudentPasswordUpdateTest {

    private static InputStream originalSystemIn;
    private static Scanner scanner;
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> studentCollection;

    @BeforeClass
    public static void setUp() {
        originalSystemIn = System.in;
        initializeMongoDBForTesting();
        scanner = new Scanner(System.in);
    }

    @AfterClass
    public static void tearDown() {
        System.setIn(originalSystemIn);
        mongoClient.close();
    }

    @Test
    public void testValidUserAndCorrectCurrentPassword() {
        String username = "stabc";
        String currentPassword = "pswd";
        String newPassword = "newPassword";

        // Insert a test user into the database
        insertTestUser(username, currentPassword);

        // Simulate user input
        String input = username + "\n" + currentPassword + "\n" + newPassword + "\n";
        simulateUserInput(input);

        // Run the update method
        StudentPasswordUpdate.StudentPasswordUpdateStart();

        // Verify that the password is updated successfully
        Document updatedUser = studentCollection.find(Filters.eq("username", username)).first();
        assertEquals(newPassword, updatedUser.getString("password"));
    }

    @Test
    public void testInvalidUser() {
        String invalidUsername = "invalidUser";
        String currentPassword = "anyPassword";

        // Simulate user input
        String input = invalidUsername + "\n" + currentPassword + "\n";
        simulateUserInput(input);

        // Run the update method
        StudentPasswordUpdate.StudentPasswordUpdateStart();

        
    }

    

    private static void initializeMongoDBForTesting() {
        try {
            String connectionString = "mongodb+srv://pfy1:uol123@timetablemanagement.uq12hfp.mongodb.net/?retryWrites=true&w=majority";  
            mongoClient = MongoClients.create(connectionString);

            database = mongoClient.getDatabase("Users_Student_Test");
            studentCollection = database.getCollection("TT_Users_Test");
        } catch (Exception e) {
            System.err.println("Error initializing MongoDB for testing: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void insertTestUser(String username, String password) {
        Document testUser = new Document("username", username)
                .append("password", password);
        studentCollection.insertOne(testUser);
    }

    private static void simulateUserInput(String input) {
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
    }
}
