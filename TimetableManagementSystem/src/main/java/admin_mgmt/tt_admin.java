package admin_mgmt;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

class studentUser {
	private String id;
    private String fname;
    private String lname;
    private String course;
    private String username;
    private String password;

    public studentUser(String id, String fname, String lname, String course, String username, String password) {
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
}

class teacherUser {
	private String id;
    private String fname;
    private String lname;
    private String course;
    private String username;
    private String password;

    public teacherUser(String id, String fname, String lname, String course, String username, String password) {
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
}



public class tt_admin {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        studentDatabase STdatabase = new studentDatabase();
        teacherDatabase TEdatabase = new teacherDatabase();

        while (true) {
            System.out.println("1. Add Student");
            System.out.println("2. Display Students");
            System.out.println("3. Add Teacher");
            System.out.println("4. Display Teachers");
            System.out.println("5. Exit");
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
                    
                case 4:
                	TEdatabase.displayTEUsers();
                    break;

                case 5:
                    System.out.println("Exiting the admin panel. Goodbye!");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
