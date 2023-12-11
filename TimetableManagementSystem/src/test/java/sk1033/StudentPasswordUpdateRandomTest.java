package sk1033;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StudentPasswordUpdateRandomTest {

    private StudentPasswordUpdate studentPasswordUpdate;

    @Before
    public void setUp() {
        studentPasswordUpdate = new StudentPasswordUpdate();
    }

    @After
    public void tearDown() {
        
    }

    @Test
    public void testRandomSuccessfulPasswordUpdate() {
     
        String username = generateRandomString(8);
        String currentPassword = generateRandomString(10);
        String newPassword = generateRandomString(12);

        InputStream inputStream = new ByteArrayInputStream((username + "\n" + currentPassword + "\n" + newPassword + "\n").getBytes());
        System.setIn(inputStream);

     
        studentPasswordUpdate.StudentPasswordUpdateStart();

        
    }

    @Test
    public void testRandomInvalidUsernameOrPassword() {
        
        String nonExistingUser = generateRandomString(8);
        String wrongPassword = generateRandomString(10);

        InputStream inputStream = new ByteArrayInputStream((nonExistingUser + "\n" + wrongPassword + "\n").getBytes());
        System.setIn(inputStream);

        
        studentPasswordUpdate.StudentPasswordUpdateStart();

        
    }

    @Test
    public void testRandomUnexpectedError() {
       
        String username = generateRandomString(8);
        String currentPassword = generateRandomString(10);
        String newPassword = generateRandomString(12);

        InputStream inputStream = new ByteArrayInputStream((username + "\n" + currentPassword + "\n" + newPassword + "\n").getBytes());
        System.setIn(inputStream);

       
        studentPasswordUpdate.setMongoClient(null);

      
        studentPasswordUpdate.StudentPasswordUpdateStart();

        
    }

    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            randomString.append(characters.charAt(random.nextInt(characters.length())));
        }
        return randomString.toString();
    }
}