package Teacher_Feed_Stud;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class StateTestTeacher {

	    private TeacherFeedbackDatabase teacherFeedbackDatabase;
	    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	    @Before
	    public void setUpStreams() {
	        System.setOut(new PrintStream(outContent));
	        teacherFeedbackDatabase = new TeacherFeedbackDatabase();
	    }

	    @After
	    public void restoreStreams() {
	        System.setOut(System.out);
	    }

	    @Test
	    public void testDisplayTeacherFeedbackWithNoFeedback() {
	        String teacherId = "sampleTeacherId";
	        teacherFeedbackDatabase.displayTeacherFeedback(teacherId);
	        String expectedOutput = "List of Feedback for Teacher " + teacherId + ":\n";
	       // assertEquals(expectedOutput, outContent.toString());
	    }

	    @Test
	    public void testDisplayTeacherFeedbackWithFeedback() {
	        // Assuming some feedback documents are already present in the collection
	        String teacherId = "sampleTeacherId";
	        teacherFeedbackDatabase.displayTeacherFeedback(teacherId);
	        
	        String expectedOutput = "List of Feedback for Teacher " + teacherId + ":\nStudent ID: ...";
	        //assertEquals(expectedOutput, outContent.toString().substring(0, expectedOutput.length()));
	    }

	    @Test
	    public void testInvalidChoice() {
	        simulateUserInput("default");
	       
	        String expectedOutput = "Invalid choice. Please enter a valid option.\n";
	        //assertEquals(expectedOutput, outContent.toString());
	    }

	    // Helper method to simulate user input for testing main method
	    private void simulateUserInput(String input) {
	        System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));
	    }
	}

