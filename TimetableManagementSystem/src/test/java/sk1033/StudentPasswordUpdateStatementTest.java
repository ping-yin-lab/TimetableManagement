package sk1033;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;

public class StudentPasswordUpdateStatementTest {

    private StudentPasswordUpdate studentPasswordUpdate;
    private InputStream sysInBackup;

    @Before
    public void setUp() {
        studentPasswordUpdate = new StudentPasswordUpdate();
        sysInBackup = System.in;
    }

    @After
    public void tearDown() {
        System.setIn(sysInBackup);
        // Clean up any resources if needed
    }

    @Test
    public void testSuccessfulPasswordUpdate() {
        // Mocking Scanner to provide input
        String input = "TestUser\nCurrentPassword\nNewPassword\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // Run the test
        studentPasswordUpdate.StudentPasswordUpdateStart();

        // Assertions
        // You may want to add assertions based on your implementation
    }

    @Test
    public void testInvalidUsernameOrPassword() {
        // Mocking Scanner to provide input
        String input = "NonExistingUser\nWrongPassword\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // Run the test
        studentPasswordUpdate.StudentPasswordUpdateStart();

        // Assertions
        // You may want to add assertions based on your implementation
    }

    @Test
    public void testUnexpectedError() {
        // Mocking Scanner to provide input
        String input = "TestUser\nCurrentPassword\nNewPassword\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // Modifying the MongoDB connection to simulate an unexpected error
        studentPasswordUpdate.setMongoClient(null);

        // Run the test
        studentPasswordUpdate.StudentPasswordUpdateStart();

        // Assertions
        // You may want to add assertions based on your implementation
    }
}