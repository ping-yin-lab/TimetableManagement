package lsd6;

import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

public class FeedbackToModuleRandomTest {

	    private ModuleFeedbackDatabase moduleFeedbackDatabase;
	    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	    private final PrintStream originalOut = System.out;

	    @Before
	    public void setUp() {
	        moduleFeedbackDatabase = new ModuleFeedbackDatabase();
	        System.setOut(new PrintStream(outContent));
	    }

	    @Test
	    public void testAddAndDisplayModuleFeedback() {
	        ModuleFeedback moduleFeedback = generateRandomModuleFeedback();

	        moduleFeedbackDatabase.addModuleFeedback(moduleFeedback);
	        moduleFeedbackDatabase.displayModuleFeedback();

	        assertTrue(outContent.toString().contains(moduleFeedback.getStuId()));
	        assertTrue(outContent.toString().contains(moduleFeedback.getModuleName()));
	        assertTrue(outContent.toString().contains(moduleFeedback.getFeedbackText()));
	        assertTrue(outContent.toString().contains(moduleFeedback.getRating()));
	    }

	    @Test
	    public void testAddMultipleModuleFeedback() {
	        ModuleFeedback feedback1 = generateRandomModuleFeedback();
	        ModuleFeedback feedback2 = generateRandomModuleFeedback();

	        moduleFeedbackDatabase.addModuleFeedback(feedback1);
	        moduleFeedbackDatabase.addModuleFeedback(feedback2);
	        assertNotNull(feedback1);
	        assertNotNull(feedback2);
	    }

	    @Test
	    public void testDisplayEmptyModuleFeedback() {
	        moduleFeedbackDatabase.displayModuleFeedback();
	        assertTrue(outContent.toString().contains("List of Module Feedback:"));
	    }

	    @Test
	    public void testInvalidRating() {
	        ModuleFeedback invalidRatingFeedback = new ModuleFeedback("S123", "Module1", "Feedback", "6");
	        moduleFeedbackDatabase.addModuleFeedback(invalidRatingFeedback);
	        assertNotNull(invalidRatingFeedback);
	    }

	    @Test
	    public void testInvalidModuleName() {
	        ModuleFeedback invalidModuleNameFeedback = new ModuleFeedback("S456", "", "Feedback", "4");
	        moduleFeedbackDatabase.addModuleFeedback(invalidModuleNameFeedback);
	        assertNotNull(invalidModuleNameFeedback);
	    }

	    // Helper method to generate random ModuleFeedback for testing
	    private ModuleFeedback generateRandomModuleFeedback() {
	        Random random = new Random();
	        String studentId = "S" + random.nextInt(1000); // Random student ID
	        String moduleName = "Module" + random.nextInt(10); // Random module name
	        String feedbackText = "Feedback" + random.nextInt(100); // Random feedback text
	        String rating = String.valueOf(random.nextInt(5) + 1); // Random rating between 1 and 5

	        return new ModuleFeedback(studentId, moduleName, feedbackText, rating);
	    }

	}

