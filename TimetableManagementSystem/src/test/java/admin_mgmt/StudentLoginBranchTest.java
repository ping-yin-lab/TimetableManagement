package admin_mgmt;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

class StudentLoginBranchTest {
	
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
	public void testLoginWithValidCredentials() {
	    String input = "1\njohn_doe\npassword123\n2\n";
	    simulateUserInput(input);

	    StudentLogin.main(null);

	    assertEquals("john_doe", Session.getCurrentUser());
	}

	@Test
	public void testLoginWithInvalidCredentials() {
	    String input = "1\ninvalid_user\ninvalid_password\n2\n";
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
	public void testPerformStudentOperationsAfterLogin() {
	    // Assume that a valid login is performed before this test
	    Session.setCurrentUser("john_doe");

	    String input = "1\n2\n";
	    simulateUserInput(input);

	    StudentLogin.main(null);

	    // Add assertions for student operations or other expected behavior
	}

	@Test
	public void testLogoutAfterLogin() {
	    // Assume that a valid login is performed before this test
	    Session.setCurrentUser("john_doe");

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
	    String input = "1\nnonexistent_user\npassword123\n2\n";
	    simulateUserInput(input);

	    StudentLogin.main(null);

	    assertNull(Session.getCurrentUser());
	}

	@Test
	public void testIncorrectPasswordDuringLogin() {
	    String input = "1\njohn_doe\nincorrect_password\n2\n";
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
		StudentLoginBranchTest.scanner = scanner;
	}

}
