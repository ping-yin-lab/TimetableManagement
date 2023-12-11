package Teacher_Feed_Stud;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;


import org.junit.Test;

public class SpecTestTeacher {

	    private TeacherFeedbackDatabase teacherFeedbackDatabase;
	    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	    @Before
	    public void setUpStreams() {
	        System.setOut(new PrintStream(outContent));
	        teacherFeedbackDatabase = new TeacherFeedbackDatabase();
	    }

	    @Test
	    public void testDisplayTeacherFeedbackWithNoFeedback() {
	        String teacherId = "123";
	        teacherFeedbackDatabase.displayTeacherFeedback(teacherId);
	     
	    }

	    @Test
	    public void testDisplayTeacherFeedbackWithFeedback() {
	        // Assuming some feedback documents are already present in the collection
	        String teacherId = "456";
	        teacherFeedbackDatabase.displayTeacherFeedback(teacherId);
	        
	        
	    }

	    @Test
	    public void testDisplayTeacherFeedbackWithInvalidTeacherId() {
	        String teacherId = "invalidTeacherId";
	        teacherFeedbackDatabase.displayTeacherFeedback(teacherId);
	       
	    }
	}

