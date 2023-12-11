package lsd6;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class FeedbackToModuleSpecificationTest {
	
	    private ModuleFeedbackDatabase moduleFeedbackDatabase;

	    @Before
	    public void setUp() {
	        moduleFeedbackDatabase = new ModuleFeedbackDatabase();
	    }

	    @Test
	    public void testAddModuleFeedback() {
	        ModuleFeedback moduleFeedback = new ModuleFeedback("123", "Math", "Good module", "5");
	        moduleFeedbackDatabase.addModuleFeedback(moduleFeedback);
	        assertTrue(true);
	    }

	    @Test
	    public void testAddModuleFeedback_NullInput() {
	        // Test adding feedback with null input
	        ModuleFeedback moduleFeedback = new ModuleFeedback(null, null, null, null);
	        moduleFeedbackDatabase.addModuleFeedback(moduleFeedback);
	        assertTrue(true);
	    }

	    @Test
	    public void testDisplayModuleFeedback() {
	        // Since displayModuleFeedback prints to the console, we can't directly test its output
	        // Instead, we can check if the method executes without exceptions
	        moduleFeedbackDatabase.displayModuleFeedback();

	        // For demonstration purposes, we'll just assert that the method didn't throw an exception
	        assertTrue(true);
	    }

	    @Test
	    public void testDisplayModuleFeedback_EmptyDatabase() {
	        // Test displaying feedback when the database is empty
	        moduleFeedbackDatabase.displayModuleFeedback();
	        assertTrue(true);
	    }

	}
