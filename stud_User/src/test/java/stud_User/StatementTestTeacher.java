package stud_User;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;


public class StatementTestTeacher {
	
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

	        // TODO: Assert the expected outcome based on the implementation
	        // For example, check if the feedback was added successfully in the database
	    }

	    @Test
	    public void testDisplayFeedback() {
	        // Display feedback (no assertions as this is a display method)
	        feedbackDatabase.displayFeedback();

	        // TODO: Manually verify the output on the console
	    }

	    @Test
	    public void testUpdateFeedback() {
	        // Add a sample feedback to the database for testing
	        Feedback feedbackToAdd = new Feedback("student2", "teacher2", "Constructive feedback", "Under review");
	        feedbackDatabase.addFeedback(feedbackToAdd);

	        // Update the feedback
	        feedbackDatabase.updateFeedback("student2", "teacher2", "Reviewed and addressed");

	        // TODO: Assert the expected outcome based on the implementation
	        // For example, check if the feedback was updated successfully in the database
	    }

	    @Test
	    public void testDeleteFeedback() {
	        // Add a sample feedback to the database for testing
	        Feedback feedbackToAdd = new Feedback("student3", "teacher3", "Negative feedback", "Sorry");
	        feedbackDatabase.addFeedback(feedbackToAdd);

	        // Delete the feedback
	        feedbackDatabase.deleteFeedback("student3", "teacher3");

	        // TODO: Assert the expected outcome based on the implementation
	        // For example, check if the feedback was deleted successfully from the database
	    }
	}

	