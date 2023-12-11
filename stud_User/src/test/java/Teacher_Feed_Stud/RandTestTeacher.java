package Teacher_Feed_Stud;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class RandTestTeacher {

	    private TeacherFeedbackDatabase teacherFeedbackDatabase;
	    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	    @Before
	    public void setUpStreams() {
	        System.setOut(new PrintStream(outContent));
	        teacherFeedbackDatabase = new TeacherFeedbackDatabase();
	    }

	    @Test
	    public void testDisplayTeacherFeedbackWithNoFeedback() {
	        String teacherId = generateRandomString();
	        teacherFeedbackDatabase.displayTeacherFeedback(teacherId);
	        //assertEquals("List of Feedback for Teacher " + teacherId + ":\n", outContent.toString());
	    }

	    @Test
	    public void testDisplayTeacherFeedbackWithFeedback() {
	        // Assuming some feedback documents are already present in the collection
	        String teacherId = generateRandomString();
	        teacherFeedbackDatabase.displayTeacherFeedback(teacherId);
	        
	        //assertEquals("List of Feedback for Teacher " + teacherId + ":\nStudent ID: ...", outContent.toString().substring(0, 30));
	    }

	    // Helper method to generate a random string for testing
	    private String generateRandomString() {
	        int leftLimit = 97; // letter 'a'
	        int rightLimit = 122; // letter 'z'
	        int targetStringLength = 10;
	        Random random = new Random();
	        StringBuilder buffer = new StringBuilder(targetStringLength);
	        for (int i = 0; i < targetStringLength; i++) {
	            int randomLimitedInt = leftLimit + (int)
	                    (random.nextFloat() * (rightLimit - leftLimit + 1));
	            buffer.append((char) randomLimitedInt);
	        }
	        return buffer.toString();
	    }
	}

