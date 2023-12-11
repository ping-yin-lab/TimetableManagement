package lsd6;

import static org.junit.Assert.*;

import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class RandomTestTeacher {

    private FeedbackDatabase feedbackDatabase;

    @Before
    public void setUp() {
        // Initialize your database or use a test database
        feedbackDatabase = new FeedbackDatabase();
    }

    @Test
    public void testAddFeedback() {
        Feedback feedbackToAdd = new Feedback("student1", "teacher1", "Good feedback", "Thanks");

        // Add feedback to the database
        feedbackDatabase.addFeedback(feedbackToAdd);
    }

    @Test
    public void testDisplayFeedback() {
        // Display feedback (no assertions as this is a display method)
        feedbackDatabase.displayFeedback();
    }

    @Test
    public void testUpdateFeedback() {
        // Add a sample feedback to the database for testing
        Feedback feedbackToAdd = new Feedback("student2", "teacher2", "Constructive feedback", "Under review");
        feedbackDatabase.addFeedback(feedbackToAdd);

        // Update the feedback
        feedbackDatabase.updateFeedback("student2", "teacher2", "Reviewed and addressed");
    }

    @Test
    public void testDeleteFeedback() {
        // Add a sample feedback to the database for testing
        Feedback feedbackToAdd = new Feedback("student3", "teacher3", "Negative feedback", "Sorry");
        feedbackDatabase.addFeedback(feedbackToAdd);

        // Delete the feedback
        feedbackDatabase.deleteFeedback("student3", "teacher3");
    }

    @Test
    public void testDeleteNonexistentFeedback() {
        // Trying to delete feedback that doesn't exist
        feedbackDatabase.deleteFeedback("nonexistentStudent", "nonexistentTeacher");

    }
}

