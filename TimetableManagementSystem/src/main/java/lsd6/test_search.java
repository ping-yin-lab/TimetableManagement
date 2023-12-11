package lsd6;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StudentDatabaseTest {

    @Test
    public void testSTUById() {
        studentDatabase stDatabase = new studentDatabase();

        // Test Case 1: Student ID which is existing
        String existingStudentId = "ST902";
        stDatabase.STUById(existingStudentId);
        

        // Test Case 2: Student ID which is not existing
        String nonExistingStudentId = "LA526";
        stDatabase.STUById(nonExistingStudentId);
       
    }
}
