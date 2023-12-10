package stud_User;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestFeedbackBDB {

	@Test
	 public void testAddFeedback() {
        FeedbackDatabase feedbackDatabase = new FeedbackDatabase();

        // Test Case 1: Add a new feedback
        Feedback newFeedback = new Feedback("st23", "te56", "Good job!", "Thank you!");
        feedbackDatabase.addFeedback(newFeedback);
    }

    @Test
    public void testUpdateFeedback() {
        FeedbackDatabase feedbackDatabase = new FeedbackDatabase();

        // Test Case 2: Update existing feedback
        String studentId = "st23";
        String teacherId = "te56";
        String newReply = "Updated reply!";
        feedbackDatabase.updateFeedback(studentId, teacherId, newReply);
    }

    @Test
    public void testDeleteFeedback() {
        FeedbackDatabase feedbackDatabase = new FeedbackDatabase();

        // Test Case 3: Delete existing feedback
        String studentId = "st23";
        String teacherId = "te56";
        feedbackDatabase.deleteFeedback(studentId, teacherId);
    }

    @Test
    public void testDisplayFeedback() {
        FeedbackDatabase feedbackDatabase = new FeedbackDatabase();

        // Test Case 4: Display feedback (no assertion as it's a console output)
        feedbackDatabase.displayFeedback();
    }
}


