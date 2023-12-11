package sk1033;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TeacherPasswordUpdateTest {

    private TeacherPasswordUpdate teacherPasswordUpdate;

    @Before
    public void setUp() {
        teacherPasswordUpdate = new TeacherPasswordUpdate();
    }

   

    @Test
    public void testSuccessfulPasswordUpdate() {
        // Mocking Scanner to provide input
        InputStream inputStream = new ByteArrayInputStream("TestUser\nCurrentPassword\nNewPassword\n".getBytes());
        System.setIn(inputStream);

        // Run the test
        teacherPasswordUpdate.TeacherPasswordUpdateStart();

        // You may want to assert or verify the success condition in your actual implementation
    }

    @Test
    public void testInvalidUsernameOrPassword() {
        // Mocking Scanner to provide input
        InputStream inputStream = new ByteArrayInputStream("NonExistingUser\nWrongPassword\n".getBytes());
        System.setIn(inputStream);

        // Run the test
        teacherPasswordUpdate.TeacherPasswordUpdateStart();

        // You may want to assert or verify the failure condition in your actual implementation
    }

    @Test
    public void testUnexpectedError() {
        // Mocking Scanner to provide input
        InputStream inputStream = new ByteArrayInputStream("TestUser\nCurrentPassword\nNewPassword\n".getBytes());
        System.setIn(inputStream);

        // Modifying the MongoDB connection to simulate an unexpected error
        teacherPasswordUpdate.setMongoClient(null);

        // Run the test
        teacherPasswordUpdate.TeacherPasswordUpdateStart();

        // You may want to assert or verify the handling of unexpected errors in your actual implementation
    }
}