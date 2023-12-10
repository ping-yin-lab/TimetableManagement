package stud_User;


import static org.junit.Assert.*;
import org.junit.Test;

import org.junit.Before;


import java.util.Random;

public class FeedbackDatabaseRandomTest {

    private FeedbackDatabase feedbackDatabase;
    private Random random;

    @Before
    public void setUp() {
        // Initialize your database or use a test database
        feedbackDatabase = new FeedbackDatabase();
        random = new Random();
    }

    @Test
    public void testAddFeedbackRandom() {
        // Generate random feedback data
        Feedback feedbackToAdd = generateRandomFeedback();

        // Add feedback to the database
        feedbackDatabase.addFeedback(feedbackToAdd);
    }

    @Test
    public void testUpdateFeedbackRandom() {
        // Add a sample feedback to the database for testing
        Feedback feedbackToAdd = generateRandomFeedback();
        feedbackDatabase.addFeedback(feedbackToAdd);

        // Generate random new reply
        String newReply = generateRandomString();

        // Update the feedback
        feedbackDatabase.updateFeedback(feedbackToAdd.getStuId(), feedbackToAdd.getTeacherId(), newReply);
    }

    @Test
    public void testDeleteFeedbackRandom() {
       
        Feedback feedbackToAdd = generateRandomFeedback();
        feedbackDatabase.addFeedback(feedbackToAdd);
        feedbackDatabase.deleteFeedback(feedbackToAdd.getStuId(), feedbackToAdd.getTeacherId());
    }

    // Helper method to generate random feedback data
    private Feedback generateRandomFeedback() {
        String stuId = generateRandomString();
        String teacherId = generateRandomString();
        String feedbackText = generateRandomString();
        String reply = generateRandomString();

        return new Feedback(stuId, teacherId, feedbackText, reply);
    }

    // Helper method to generate random string
    private String generateRandomString() {
        int length = random.nextInt(10) + 5; // Random length between 5 and 14
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < length; i++) {
            char randomChar = (char) (random.nextInt(26) + 'a');
            randomString.append(randomChar);
        }

        return randomString.toString();
    }
}

