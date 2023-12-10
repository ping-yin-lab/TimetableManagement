package admin_mgmt;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

public class StudentLoginRandomTest {

    private static InputStream originalSystemIn;
    private static Scanner scanner;
    private static Faker faker;

    @BeforeClass
    public static void setUp() {
        originalSystemIn = System.in;
        faker = new Faker();
    }

    @AfterClass
    public static void tearDown() {
        System.setIn(originalSystemIn);
    }

    @Test
    public void testRandomLogin() {
        // Generate random username and password using faker
        String randomUsername = faker.internet().userName();
        String randomPassword = faker.internet().password();

        // Provide random credentials to simulate a login
        String input = "1\n" + randomUsername + "\n" + randomPassword + "\n2\n";
        simulateUserInput(input);

        StudentLogin.main(null);

        // Assuming that Session.setCurrentUser(username) is called during a successful login
        // Check if the current user is set correctly
        assertEquals(randomUsername, Session.getCurrentUser());
    }

    @Test(expected = NoSuchElementException.class)
    public void testRandomNonNumericChoice() {
        // Generate random non-numeric input using faker
        String randomInput = faker.lorem().word() + "\n2\n";
        simulateUserInput(randomInput);

        try {
            StudentLogin.main(null);
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }

    private void simulateUserInput(String input) {
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        scanner = new Scanner(System.in);
    }
}
