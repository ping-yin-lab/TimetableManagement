package lsd6;

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.Test;

public class BranchTestSearch {

	    @Test
	    public void testSearchStudentById_ExistingStudent() {
	        // Simulate user input for searching an existing student
	        String input = "1\nexistingStudentID\n2\n";
	        System.setIn(new ByteArrayInputStream(input.getBytes()));

	        // Redirect System.out to capture the output
	        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	        System.setOut(new PrintStream(outContent));

	        // Execute the main method
	        studentDatabase.main(null);

	        // Verify the output
	        String expectedOutput = "Successfully connected to student Database\n" +
	                                "1. Search Student by ID\n" +
	                                "2. Exit\n" +
	                                "Enter your choice: Enter Student ID: Details of the given userID are:\n" +
	                                "Student ID is existingStudentID\n" +
	                                "First Name is ExampleFirstName\n" +
	                                "Last Name is ExampleLastName\n" +
	                                "Course selected: ExampleCourse\n" +
	                                "Username of the student: ExampleUsername\n" +
	                                "Exiting the program.\n";
	        
	    }

	    @Test
	    public void testSearchStudentById_NonExistingStudent() {
	        // Simulate user input for searching a non-existing student
	        String input = "1\nnonExistingStudentID\n2\n";
	        System.setIn(new ByteArrayInputStream(input.getBytes()));

	        // Redirect System.out to capture the output
	        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	        System.setOut(new PrintStream(outContent));

	        // Execute the main method
	        studentDatabase.main(null);

	        // Verify the output
	        String expectedOutput = "Successfully connected to student Database\n" +
	                                "1. Search Student by ID\n" +
	                                "2. Exit\n" +
	                                "Enter your choice: Enter Student ID: Given Student ID not found.\n" +
	                                "Exiting the program.\n";
	        
	    }

	    @Test
	    public void testExitProgram() {
	        // Simulate user input to exit the program
	        String input = "2\n";
	        System.setIn(new ByteArrayInputStream(input.getBytes()));

	        // Redirect System.out to capture the output
	        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	        System.setOut(new PrintStream(outContent));

	        // Execute the main method
	        studentDatabase.main(null);

	        // Verify the output
	        String expectedOutput = "Successfully connected to student Database\n" +
	                                "1. Search Student by ID\n" +
	                                "2. Exit\n" +
	                                "Enter your choice: Exiting the program.\n";
	        
	    }

	    @Test
	    public void testInvalidChoice() {
	        // Simulate user input for an invalid choice
	        String input = "3\n2\n";
	        System.setIn(new ByteArrayInputStream(input.getBytes()));

	        // Redirect System.out to capture the output
	        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	        System.setOut(new PrintStream(outContent));

	        // Execute the main method
	        studentDatabase.main(null);

	        // Verify the output
	        String expectedOutput = "Successfully connected to student Database\n" +
	                                "1. Search Student by ID\n" +
	                                "2. Exit\n" +
	                                "Enter your choice: Invalid choice. Please enter a valid option.\n" +
	                                "Enter your choice: Exiting the program.\n";
	        
	    }
	}


