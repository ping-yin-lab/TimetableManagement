import org.bson.Document;

import java.util.List;
import java.util.ArrayList;

public class studentDatabase {

    public void searchSTUById(String studentId) {
        Document studentDocument = STusersCollection.find(new Document("Student_ID", studentId)).first();

        if (studentDocument != null) {
            System.out.println("Details of the given userID are:");
            System.out.println("Student ID is " + studentDocument.get("Student_ID"));
            System.out.println("First Name is " + studentDocument.get("first_name"));
            System.out.println("Last Name is " + studentDocument.get("last_name"));
            System.out.println("Course selected: " + studentDocument.get("course"));
            System.out.println("Username of the student: " + studentDocument.get("username"));
        } else {
            System.out.println("Given Student ID not found.");
        }
    }
}

