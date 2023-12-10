package admin_mgmt;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class StudentLoginTest {

    private static InputStream originalSystemIn;
    private static Scanner scanner;

    @BeforeClass
    public static void setUp() {
        originalSystemIn = System.in;
    }

    @AfterClass
    public static void tearDown() {
        System.setIn(originalSystemIn);
    }

    @Test
    public void testValidLogin() {
        
        String input = "1\nRandomUsername64\nRandomPassword40\n2\n";
        simulateUserInput(input);

        StudentLogin.main(null);

        assertEquals("RandomUsername64", Session.getCurrentUser());
    }

    @Test
    public void testInvalidLogin() {
        
        String input = "1\n@123@\n1234\n2\n";
        simulateUserInput(input);

        StudentLogin.main(null);									

        assertNull(Session.getCurrentUser());
    }

    @Test
    public void testExitOption() {
        
        String input = "2\n";
        simulateUserInput(input);

        StudentLogin.main(null);

        assertNull(Session.getCurrentUser());
    }

    @Test
    public void testNonNumericChoice() {
        
        String input = "k\n2\n";
        simulateUserInput(input);

        try {
            StudentLogin.main(null);
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }

    @Test
    public void testInvalidUsernameDuringLogin() {
        
        String input = "1\nhero\npassword\n2\n";
        simulateUserInput(input);

        StudentLogin.main(null);

        assertNull(Session.getCurrentUser());
    }

    @Test
    public void testIncorrectPasswordDuringLogin() {
        
        String input = "1\nSTpfy11\n143143\n2\n";
        simulateUserInput(input);

        StudentLogin.main(null);

        assertNull(Session.getCurrentUser());
    }

   

    private void simulateUserInput(String input) {
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        setScanner(new Scanner(System.in));
    }

	public static Scanner getScanner() {
		return scanner;
	}

	public static void setScanner(Scanner scanner) {
		StudentLoginTest.scanner = scanner;
	}
}
