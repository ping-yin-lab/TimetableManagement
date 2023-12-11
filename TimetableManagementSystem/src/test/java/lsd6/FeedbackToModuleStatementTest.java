package lsd6;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class FeedbackToModuleStatementTest {

    private ModuleFeedbackDatabase moduleFeedbackDatabase;
    private ModuleFeedMain moduleFeedMain;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        moduleFeedbackDatabase = new ModuleFeedbackDatabase();
        moduleFeedMain = new ModuleFeedMain();
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testAddModuleFeedback() {
        ModuleFeedback moduleFeedbackToAdd = new ModuleFeedback("123", "Math", "Good module", "5");
        moduleFeedbackDatabase.addModuleFeedback(moduleFeedbackToAdd);
        assertTrue(outContent.toString().contains("Module Feedback added successfully!"));
    }

    @Test
    public void testDisplayModuleFeedback() {
        // Add a module feedback to the database
        ModuleFeedback moduleFeedbackToAdd = new ModuleFeedback("123", "Math", "Good module", "5");
        moduleFeedbackDatabase.addModuleFeedback(moduleFeedbackToAdd);

        // Redirect System.out to capture the output
        moduleFeedbackDatabase.displayModuleFeedback();

        // Verify the output
        assertTrue(outContent.toString().contains("List of Module Feedback:"));
        assertTrue(outContent.toString().contains("Student ID: 123, Module Name: Math, Feedback Text: Good module, Rating: 5"));
    }

    @Test
    public void testDisplayEmptyModuleFeedback() {
        // Clear the module feedback collection to ensure it's empty
        moduleFeedbackDatabase.clearModuleFeedbackCollection();
        moduleFeedbackDatabase.displayModuleFeedback();

        // Verify the output
        System.out.println("Actual Output:\n" + outContent.toString().trim());

    }

  
}
