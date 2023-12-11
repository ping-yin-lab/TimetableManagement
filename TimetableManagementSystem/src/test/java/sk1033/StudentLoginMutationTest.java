package sk1033;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StudentLoginMutationTest {

    @Test
    public void testLoginWithCorrectCredentials() {
        // Provide valid username and password for login
        provideInput("correctUsername\ncorrectPassword\n");

        // Test that login is successful
        assertDoesNotThrow(() -> StudentLogin.login(StudentLogin.studentCollection));
    }

    @Test
    public void testLoginWithIncorrectCredentials() {
        // Provide invalid username and password for login
        provideInput("incorrectUsername\nincorrectPassword\n");

        // Test that login fails
        assertThrows(Exception.class, () -> StudentLogin.login(StudentLogin.studentCollection));
    }

    @Test
    public void testLoginWithInvalidUsername() {
        // Provide invalid username and valid password for login
        provideInput("\nvalidPassword\n");

        // Test that login fails
        assertThrows(Exception.class, () -> StudentLogin.login(StudentLogin.studentCollection));
    }

    @Test
    public void testLoginWithInvalidPassword() {
        // Provide valid usersname and invalid password for login
        provideInput("validUsername\n\n");

        // Test that login fails
        assertThrows(Exception.class, () -> StudentLogin.login(StudentLogin.studentCollection));
    }

    private void provideInput(String data) {
        InputStream original = System.in;
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Scanner scanner = new Scanner(System.in);
        System.setIn(original);
    }
}