package sk1033;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
 
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
 
import static org.junit.jupiter.api.Assertions.*;
 
public class TeacherAnnouncementTest {
 
    private static MongoClient testMongoClient;
    private static MongoDatabase testDatabase;
    private static MongoCollection<Document> testAnnouncementCollection;
 
    @BeforeAll
    static void setUp() {
        // Connect to a test MongoDB instance
        String testConnectionString = "mongodb+srv://pfy1:uol123@timetablemanagement.uq12hfp.mongodb.net/?retryWrites=true&w=majority";
        testMongoClient = MongoClients.create(testConnectionString);
        testDatabase = testMongoClient.getDatabase("Announcement");
        testAnnouncementCollection = testDatabase.getCollection("TT_Teacher_Announcement");
    }
 
    @AfterAll
    static void tearDown() {
        // Clean up resources
        testAnnouncementCollection.drop();
        testMongoClient.close();
    }
 
    @Test
    void testInsertAnnouncement() {
        // Initialize the TeacherAnnouncement class
        TeacherAnnouncement teacherAnnouncement = new TeacherAnnouncement();
 
        // Redirect System.out to capture the console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
 
        // Simulate user input
        simulateUserInput("Test Subject", "Test Announcement");
 
        // Call the method
        teacherAnnouncement.TeacherAnnouncementStart();
 
        // Reset System.out
        System.setOut(System.out);
 
        // Check if the announcement was inserted successfully in the test database
        Document testAnnouncement = testAnnouncementCollection.find().first();
        assertNotNull(testAnnouncement);
        assertEquals("Test Subject", testAnnouncement.getString("subject"));
        assertEquals("Test Announcement", testAnnouncement.getString("announcement"));
 
        // Check if the console output contains the expected message
        //assertTrue(outContent.toString().contains("Announcement published!"));
    }
 
    @Test
    void testGetAnnouncements() {
        // Initialize the TeacherAnnouncement class
        TeacherAnnouncement teacherAnnouncement = new TeacherAnnouncement();
 
        // Redirect System.out to capture the console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
 
        // Insert a test announcement
        testAnnouncementCollection.insertOne(new Document()
                .append("subject", "Test Subject")
                .append("announcement", "Test Announcement"));
 
        // Call the getAnnouncements method
        teacherAnnouncement.getAnnouncements();
 
        // Reset System.out
        System.setOut(System.out);
 
        // Check if the console output contains the expected announcement details
        assertTrue(outContent.toString().contains("Subject : Test Subject"));
        assertTrue(outContent.toString().contains("Announcement : Test Announcement"));
    }
 
    // Helper method to simulate user input for the Scanner
    private void simulateUserInput(String subject, String announcement) {
        // Redirect System.in to provide simulated user input
        System.setIn(new ByteArrayInputStream((subject + System.lineSeparator() + announcement).getBytes()));
    }
}