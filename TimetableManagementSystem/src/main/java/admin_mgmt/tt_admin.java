package admin_mgmt;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

class User {
	private String id;
    private String fname;
    private String lname;
    private String course;
    private String username;
    private String password;

    public User(String id, String fname, String lname, String course, String username, String password) {
    	this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.course = course;
        this.username = username;
        this.password = password;
    }

    public String getID() {
        return id;
    }
    public String getFName() {
        return fname;
    }
    public String getLName() {
        return lname;
    }
    public String getCourse() {
        return course;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}

class studentUser extends User{
	public studentUser(String id, String fname, String lname, String course, String username, String password) {
		super(id, fname, lname, course, username, password);
	}
}

class teacherUser extends User{
	public teacherUser(String id, String fname, String lname, String course, String username, String password) {
		super(id, fname, lname, course, username, password);
	}
}
	
class studentDatabase { 
	private MongoCollection<Document> STusersCollection;
	public studentDatabase() { 
	String connectionString = "mongodb+srv://pfy1:uol123@timetablemanagement.uq12hfp.mongodb.net/?retryWrites=true&w=majority";
	String databaseName = "Users_Student"; 
	String collectionName = "TT_Users";

    try { 
    	ConnectionString connString = new ConnectionString(connectionString); 
    	MongoClient mongoClient =MongoClients.create(connectionString); 
    	MongoDatabase database = mongoClient.getDatabase(databaseName); 
    	STusersCollection = database.getCollection(collectionName);
	    System.out.println("Connected Successfully"); 
	    }catch(Exception e) {
	    	System.err.println("Error connecting: "+ e.getMessage());
	    	e.printStackTrace(); System.exit(1); 
	    } 
    }
    
    public void STaddUser(studentUser user) {
    	if (userExists(user.getUsername(), user.getID())) {
            System.out.println("Error: Username or ID already exists. Please choose different values.");
            return;
        }else {
        Document userDocument = new Document("Student_ID", user.getID())
        		.append("first_name", user.getFName())
        		.append("last_name", user.getLName())
        		.append("course", user.getCourse())
        		.append("username", user.getUsername())
                .append("password", user.getPassword());

        STusersCollection.insertOne(userDocument);
        System.out.println("User added successfully!");
        }
    }
    
    private boolean userExists(String username, String id) {
        return STusersCollection.countDocuments(
                new Document("$or", List.of(
                        new Document("username", username),
                        new Document("id", id)
                ))
        ) > 0;
    }
       

    public void displaySTUsers() {
        System.out.println("List of Students:");
        STusersCollection.find().forEach(document ->
                System.out.println("Username: " + document.get("username") + ", Password: " + document.get("password")));
    }
    
    public void STUpdate(String studentId, String newFName, String newLName, String newCourse, String newUsername, String newPassword) {
        try {
            Document filter = new Document("Student_id", studentId);
            Document update = new Document();
            if (newFName != null && !newFName.isEmpty()) {
            	
                Updates.set("first_name", newFName);
            }
            if (newLName != null && !newLName.isEmpty()) {
                update.append("$set", new Document("last_name", newLName));
            }
            if (newCourse != null && !newCourse.isEmpty()) {
                update.append("$set", new Document("name", newCourse));
            }
            if (newUsername != null && !newUsername.isEmpty()) {
                update.append("$set", new Document("username", newUsername));
            }
            if (newPassword != null && !newPassword.isEmpty()) {
                update.append("$set", new Document("password", newPassword));
            }
            if (!update.isEmpty()) {
            	STusersCollection.updateOne(filter, update);
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("No valid fields to update for the student.");
            }
        } catch (com.mongodb.MongoException e) {
            System.err.println("Error updating student in MongoDB: " + e.getMessage());
        }
    }
    
    public void deleteSTuser(String teacherId) {
        try {
        	STusersCollection.deleteOne(new Document("Student_ID", teacherId));
            System.out.println("Student deleted successfully!");
        } catch (com.mongodb.MongoException e) {
            System.err.println("Error deleting student: " + e.getMessage());
        }
    }
}


class teacherDatabase {
	private MongoCollection<Document> TEusersCollection;

    public teacherDatabase() {
    	String connectionString = "mongodb+srv://pfy1:uol123@timetablemanagement.uq12hfp.mongodb.net/?retryWrites=true&w=majority";
    	String databaseName = "Users_Teacher"; 
    	String collectionName = "TT_Users";

        try { 
        	ConnectionString connString = new ConnectionString(connectionString); 
        	MongoClient mongoClient =MongoClients.create(connectionString); 
        	MongoDatabase database = mongoClient.getDatabase(databaseName); 
        	TEusersCollection = database.getCollection(collectionName);
    	    System.out.println("Connected Successfully"); 
    	    }catch(Exception e) {
    	    	System.err.println("Error connecting: "+ e.getMessage());
    	    	e.printStackTrace(); System.exit(1); 
    	    } 
    }

    public void TEaddUser(teacherUser user) {
    	if (userExists(user.getUsername(), user.getID())) {
            System.out.println("Error: Username or ID already exists. Please choose different values.");
            return;
        }else {
        Document userDocument = new Document("Teacher_ID", user.getID())
        		.append("first_name", user.getFName())
        		.append("last_name", user.getLName())
        		.append("course", user.getCourse())
        		.append("username", user.getUsername())
                .append("password", user.getPassword());

        TEusersCollection.insertOne(userDocument);
        System.out.println("User added successfully!");
        }
    }
    
    private boolean userExists(String username, String id) {
        return TEusersCollection.countDocuments(
                new Document("$or", List.of(
                        new Document("username", username),
                        new Document("id", id)
                ))
        ) > 0;
    }

    public void displayTEUsers() {
    	System.out.println("List of Teachers:");
        TEusersCollection.find().forEach(document ->
                System.out.println("Username: " + document.get("username") + ", Password: " + document.get("password")));
    }
    
    public void TEUpdate(String userIdToUpdate, String newFName, String newLName, String newCourse, String newUsername, String newPassword) {
        try {        	
            Document filter = new Document("Teacher_ID", userIdToUpdate);
            Document update = new Document();
            if (newFName != null && !newFName.isEmpty()) {
            	TEusersCollection.updateOne(Filters.eq("Teacher_ID", userIdToUpdate), Updates.set("first_name", newFName));
            }
            if (newLName != null && !newLName.isEmpty()) {
                update.append("$set", new Document("last_name", newLName));
            }
            if (newCourse != null && !newCourse.isEmpty()) {
                update.append("$set", new Document("course", newCourse));
            }
            if (newUsername != null && !newUsername.isEmpty()) {
            	update.append("$set", new Document("course", newUsername));
            }
            if (newPassword != null && !newPassword.isEmpty()) {
                update.append("$set", new Document("password", newPassword));
            }
            if (!update.isEmpty()) {
            	TEusersCollection.updateOne(filter, update);
                System.out.println("Teacher updated successfully!");
            } else {
                System.out.println("No valid fields to update for the teacher.");
            }
        } catch (com.mongodb.MongoException e) {
            System.err.println("Error updating teacher in MongoDB: " + e.getMessage());
        }
    }
    
    public void deleteTEuser(String teacherId) {
        try {
        	TEusersCollection.deleteOne(new Document("Teacher_ID", teacherId));
            System.out.println("Teacher deleted successfully!");
        } catch (com.mongodb.MongoException e) {
            System.err.println("Error deleting teacher from MongoDB: " + e.getMessage());
        }
    }
}


public class tt_admin {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        studentDatabase STdatabase = new studentDatabase();
        teacherDatabase TEdatabase = new teacherDatabase();

        while (true) {
            System.out.println("1. Add Student");
            System.out.println("2. Display Students");
            System.out.println("3. Delete Student");
            System.out.println("4. Add Teacher");
            System.out.println("5. Display Teachers");
            System.out.println("6. Delete Teacher");
            System.out.println("7. Update User");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                	System.out.print("Enter StudentId: ");
                	String STid = scanner.nextLine();
                	System.out.print("Enter First Name: ");
                    String STfname = scanner.nextLine();
                    System.out.print("Enter Last Name: ");
                    String STlname = scanner.nextLine();
                    System.out.print("Enter Course: ");
                    String STcourse = scanner.nextLine();
                    System.out.print("Enter username: ");
                    String STusername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String STpassword = scanner.nextLine();
                    
                    
                    studentUser STnewUser = new studentUser(STid, STfname, STlname, STcourse,STusername, STpassword);
                    STdatabase.STaddUser(STnewUser);
                    break;

                case 2:
                    STdatabase.displaySTUsers();
                    break;
                    
                case 3:
               	 System.out.print("Enter student ID to delete: ");
                    String STId = scanner.nextLine();
                    STdatabase.deleteSTuser(STId);
                    break;
                
                case 4:
                	System.out.print("Enter Teacher Id: ");
                	String TEid = scanner.nextLine();
                	System.out.print("Enter First Name: ");
                    String TEfname = scanner.nextLine();
                    System.out.print("Enter Last Name: ");
                    String TElname = scanner.nextLine();
                    System.out.print("Enter Course: ");
                    String TEcourse = scanner.nextLine();
                    System.out.print("Enter username: ");
                    String TEusername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String TEpassword = scanner.nextLine();

                    teacherUser TEnewUser = new teacherUser(TEid, TEfname, TElname, TEcourse,TEusername, TEpassword);
                    TEdatabase.TEaddUser(TEnewUser);
                    break;
                    
                case 5:
                	TEdatabase.displayTEUsers();
                    break;
                    
                case 6:
                	 System.out.print("Enter teacher ID to delete: ");
                     String TEId = scanner.nextLine();
                     TEdatabase.deleteTEuser(TEId);
                     break;
                     
                case 7:
                	System.out.print("Enter user type (1 for Teacher, 2 for Student): ");
                    int userTypeToUpdate = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter user ID to update: ");
                    String userIdToUpdate = scanner.nextLine();
                    System.out.print("Enter new First Name (or press Enter to skip): ");
                    String newFName = scanner.nextLine();
                    System.out.print("Enter new Last Name (or press Enter to skip): ");
                    String newLName = scanner.nextLine();
                    System.out.print("Enter new Course (or press Enter to skip): ");
                    String newCourse = scanner.nextLine();
                    System.out.print("Enter new username (or press Enter to skip): ");
                    String newUsername = scanner.nextLine();
                    System.out.print("Enter new password (or press Enter to skip): ");
                    String newPassword = scanner.nextLine();

                    if (userTypeToUpdate == 1) {
                        TEdatabase.TEUpdate(userIdToUpdate, newFName, newLName, newCourse, newUsername, newPassword);
                    } else if (userTypeToUpdate == 2) {
                        STdatabase.STUpdate(userIdToUpdate, newFName, newLName, newCourse, newUsername, newPassword);
                    } else {
                        System.out.println("Invalid user type. Please enter 1 for Teacher or 2 for Student.");
                    }
                    break;
                    
                case 8:
                    System.out.println("Exiting the admin panel. Goodbye!");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}