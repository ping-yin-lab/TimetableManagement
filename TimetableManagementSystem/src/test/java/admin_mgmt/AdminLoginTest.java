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
import java.util.Random;

public class AdminLoginTest {

    private static InputStream originalSystemIn;
    private static Scanner scanner;
    private static Random random;

    @BeforeClass
    public static void setUp() {
        originalSystemIn = System.in;
        random = new Random();
    }

    @AfterClass
    public static void tearDown() {
        System.setIn(originalSystemIn);
    }

    @Test
    public void testValidLogin() {
        
        String input = "1\nstabc\npass\n2\n";
        simulateUserInput(input);

        AdminLogin.adminLoginStart();
        Session.setCurrentUser("RandomUsername64");
        assertEquals("RandomUsername64", Session.getCurrentUser());
    }

    @Test
    public void testInvalidLogin() {
        
        String input = "1\n@123@\n1234\n2\n";
        simulateUserInput(input);

        AdminLogin.adminLoginStart();									

        assertNull(Session.getCurrentUser());
    }

    @Test
    public void testExitOption() {
        
        String input = "2\n";
        simulateUserInput(input);

        AdminLogin.adminLoginStart();

        assertNull(Session.getCurrentUser());
    }

    @Test
    public void testNonNumericChoice() {
        
        String input = "k\n2\n";
        simulateUserInput(input);

        try {
            AdminLogin.adminLoginStart();
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }

    @Test
    public void testInvalidUsernameDuringLogin() {
        
        String input = "1\nhero\npassword\n2\n";
        simulateUserInput(input);

        AdminLogin.adminLoginStart();
        
        assertNull(Session.getCurrentUser());
    }

    @Test
    public void testIncorrectPasswordDuringLogin() {
        
        String input = "1\nSTpfy11\n143143\n2\n";
        simulateUserInput(input);

        AdminLogin.adminLoginStart();
        
        assertNull(Session.getCurrentUser());
    }
    
    @Test
    public void testRandomLogin() {
        
        String randomUsername = generateRandomString(10);
        String randomPassword = generateRandomString(12);

       
        String input = "1\n" + randomUsername + "\n" + randomPassword + "\n2\n";
        simulateUserInput(input);

        AdminLogin.adminLoginStart();
        assertEquals(randomUsername, Session.getCurrentUser());
    }
    @Test
	public void testPerformStudentOperationsAfterLogin() {
	    
	    Session.setCurrentUser("stabc");

	    String input = "1\n2\n";
	    simulateUserInput(input);

	    AdminLogin.adminLoginStart();

	}
    
    @Test
	public void testLogoutAfterLogin() {
	    
	    Session.setCurrentUser("stabc");

	    String input = "2\n";
	    simulateUserInput(input);

	    AdminLogin.adminLoginStart();

	    assertNull(Session.getCurrentUser());
	}
    
    private String generateRandomString(int length) {
        
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char randomChar = (char) (random.nextInt(26) + 'a');
            randomString.append(randomChar);
        }
        return randomString.toString();
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
		AdminLoginTest.scanner = scanner;
	}
}
