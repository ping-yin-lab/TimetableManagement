package pfy1.PingFungYin;

import java.util.List;
import java.util.Scanner;

import org.bson.Document;
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

class adminUser extends User{
	public adminUser(String id, String fname, String lname, String course, String username, String password) {
		super(id, fname, lname, course, username, password);
	}
}

class adminDatabase { 
	private MongoCollection<Document> ADusersCollection;
	public adminDatabase() { 
	String connectionString = "mongodb+srv://pfy1:uol123@timetablemanagement.uq12hfp.mongodb.net/?retryWrites=true&w=majority";
	String databaseName = "Users_Admin"; 
	String collectionName = "TT_Users";

    try { 
    	//ConnectionString connString = new ConnectionString(connectionString); 
    	MongoClient mongoClient =MongoClients.create(connectionString); 
    	MongoDatabase database = mongoClient.getDatabase(databaseName); 
    	ADusersCollection = database.getCollection(collectionName);
	    System.out.println("Connected Successfully"); 
	    }catch(Exception e) {
	    	System.err.println("Error connecting: "+ e.getMessage());
	    	e.printStackTrace(); System.exit(1); 
	    } 
    }
    
    public void ADaddUser(adminUser user) {
    	if (userExists(user.getUsername(), user.getID())) {
            System.out.println("Error: Username or ID already exists. Please choose different values.");
            return;
        }else {
        Document userDocument = new Document("Admin_ID", user.getID())
        		.append("first_name", user.getFName())
        		.append("last_name", user.getLName())
        		.append("course", user.getCourse())
        		.append("username", user.getUsername())
                .append("password", user.getPassword());

        ADusersCollection.insertOne(userDocument);
        System.out.println("User added successfully!");
        }
    }
    
    private boolean userExists(String username, String id) {
        return ADusersCollection.countDocuments(
                new Document("$or", List.of(
                        new Document("username", username),
                        new Document("Admin_ID", id)
                ))
        ) > 0;
    }
       

    public void displayADUsers() {
        System.out.println("List of Admins:");
        ADusersCollection.find().forEach(document ->
                System.out.println("Username: " + document.get("username") + ", Password: " + document.get("password")));
    }
    
    public void ADUpdate(String adminId, String newFName, String newLName, String newCourse, String newUsername, String newPassword) {
        try {
            if (newFName != null && !newFName.isEmpty()) {
            	ADusersCollection.updateOne(Filters.eq("Admin_ID", adminId), Updates.set("first_name", newFName));
            }
            if (newLName != null && !newLName.isEmpty()) {
            	ADusersCollection.updateOne(Filters.eq("Admin_ID", adminId), Updates.set("last_name", newLName));
            }
            if (newCourse != null && !newCourse.isEmpty()) {
            	ADusersCollection.updateOne(Filters.eq("Admin_ID", adminId), Updates.set("course", newCourse));
            }
            if (newUsername != null && !newUsername.isEmpty()) {
            	ADusersCollection.updateOne(Filters.eq("Admin_ID", adminId), Updates.set("username", newUsername));
            }
            if (newPassword != null && !newPassword.isEmpty()) {
            	ADusersCollection.updateOne(Filters.eq("Admin_ID", adminId), Updates.set("password", newPassword));
            }
            if (!newFName.isEmpty() || !newLName.isEmpty() || !newCourse.isEmpty() || !newUsername.isEmpty() || !newPassword.isEmpty() ) {
                System.out.println("Admin updated successfully!");
            } else {
                System.out.println("No valid fields to update for the admin.");
            }
        } catch (com.mongodb.MongoException e) {
            System.err.println("Error updating student in MongoDB: " + e.getMessage());
        }
    }
    
    public void deleteADuser(String adminId) {
        try {
        	ADusersCollection.deleteOne(new Document("Admin_ID", adminId));
            System.out.println("Admin deleted successfully!");
        } catch (com.mongodb.MongoException e) {
            System.err.println("Error deleting student: " + e.getMessage());
        }
    }
}

class studentDatabase { 
	private MongoCollection<Document> STusersCollection;
	public studentDatabase() { 
	String connectionString = "mongodb+srv://pfy1:uol123@timetablemanagement.uq12hfp.mongodb.net/?retryWrites=true&w=majority";
	String databaseName = "Users_Student"; 
	String collectionName = "TT_Users";

    try { 
    	//ConnectionString connString = new ConnectionString(connectionString); 
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
                        new Document("Student_ID", id)
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
            if (newFName != null && !newFName.isEmpty()) {
            	STusersCollection.updateOne(Filters.eq("Student_ID", studentId), Updates.set("first_name", newFName));
            }
            if (newLName != null && !newLName.isEmpty()) {
            	STusersCollection.updateOne(Filters.eq("Student_ID", studentId), Updates.set("last_name", newLName));
            }
            if (newCourse != null && !newCourse.isEmpty()) {
            	STusersCollection.updateOne(Filters.eq("Student_ID", studentId), Updates.set("course", newCourse));
            }
            if (newUsername != null && !newUsername.isEmpty()) {
            	STusersCollection.updateOne(Filters.eq("Student_ID", studentId), Updates.set("username", newUsername));
            }
            if (newPassword != null && !newPassword.isEmpty()) {
            	STusersCollection.updateOne(Filters.eq("Student_ID", studentId), Updates.set("password", newPassword));
            }
            if (!newFName.isEmpty() || !newLName.isEmpty() || !newCourse.isEmpty() || !newUsername.isEmpty() || !newPassword.isEmpty() ) {
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("No valid fields to update for the teacher.");
            }
        } catch (com.mongodb.MongoException e) {
            System.err.println("Error updating student in MongoDB: " + e.getMessage());
        }
    }
    
    public void deleteSTuser(String studentId) {
        try {
        	STusersCollection.deleteOne(new Document("Student_ID", studentId));
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
        	//ConnectionString connString = new ConnectionString(connectionString); 
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
                        new Document("Teacher_ID", id)
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
            if (newFName != null && !newFName.isEmpty()) {
            	TEusersCollection.updateOne(Filters.eq("Teacher_ID", userIdToUpdate), Updates.set("first_name", newFName));
            }
            if (newLName != null && !newLName.isEmpty()) {
            	TEusersCollection.updateOne(Filters.eq("Teacher_ID", userIdToUpdate), Updates.set("last_name", newLName));
            }
            if (newCourse != null && !newCourse.isEmpty()) {
            	TEusersCollection.updateOne(Filters.eq("Teacher_ID", userIdToUpdate), Updates.set("course", newCourse));
            }
            if (newUsername != null && !newUsername.isEmpty()) {
            	TEusersCollection.updateOne(Filters.eq("Teacher_ID", userIdToUpdate), Updates.set("username", newUsername));
            }
            if (newPassword != null && !newPassword.isEmpty()) {
            	TEusersCollection.updateOne(Filters.eq("Teacher_ID", userIdToUpdate), Updates.set("password", newPassword));
            }
            if (!newFName.isEmpty() || !newLName.isEmpty() || !newCourse.isEmpty() || !newUsername.isEmpty() || !newPassword.isEmpty() ) {
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

class Module {
	private String MDcode;
	private String MDname;
    private String MDdescription;
    private String MDteacher;

    public Module(String MDcode, String MDname, String MDdescription, String MDteacher) {
    	this.MDcode = MDcode;
    	this.MDname = MDname;
        this.MDdescription = MDdescription;
        this.MDteacher = MDteacher;
    }

    public String getCode() {
        return MDcode;
    }
    public String getName() {
        return MDname;
    }
    public String getDescription() {
        return MDdescription;
    }
    public String getTeacher() {
        return MDteacher;
    }
}

class moduleDatabase {
	private MongoCollection<Document> moduleCollection;

    public moduleDatabase() {
    	String connectionString = "mongodb+srv://pfy1:uol123@timetablemanagement.uq12hfp.mongodb.net/?retryWrites=true&w=majority";
    	String databaseName = "Module"; 
    	String collectionName = "TT_modules";

        try { 
        	//ConnectionString connString = new ConnectionString(connectionString); 
        	MongoClient mongoClient =MongoClients.create(connectionString); 
        	MongoDatabase database = mongoClient.getDatabase(databaseName); 
        	moduleCollection = database.getCollection(collectionName);
    	    System.out.println("Connected Successfully"); 
    	    }catch(Exception e) {
    	    	System.err.println("Error connecting: "+ e.getMessage());
    	    	e.printStackTrace(); System.exit(1); 
    	    } 
    }

    public void addModule(Module mod) {
    	if (moduleExists(mod.getCode())) {
            System.out.println("Error: Module already");
            return;
        }else {
        Document userDocument = new Document("Module_Code", mod.getCode())
        		.append("Module_Name", mod.getName())
        		.append("Module_Description", mod.getDescription())
        		.append("Module_Teacher", mod.getTeacher());

        moduleCollection.insertOne(userDocument);
        System.out.println("Module added successfully!");
        }
    }
    
    private boolean moduleExists(String code) {
        return moduleCollection.countDocuments(
                new Document("$or", List.of(
                        new Document("Module_Code", code)
                ))
        ) > 0;
    }
}

class LectureHall {
	private String LHcode;
	private String LHbuilding;
    private int LHroom;
    private int LHpeople;

    public LectureHall(String LHcode, String LHbuilding, int LHroom, int LHpeople) {
    	this.LHcode = LHcode;
    	this.LHbuilding = LHbuilding;
        this.LHroom = LHroom;
        this.LHpeople = LHpeople;
    }

    public String getCode() {
        return LHcode;
    }
    public String getBuilding() {
        return LHbuilding;
    }
    public int getRoom() {
        return LHroom;
    }
    public int getPeople() {
        return LHpeople;
    }
}

class lecturehallDatabase {
	private MongoCollection<Document> moduleCollection;

    public lecturehallDatabase() {
    	String connectionString = "mongodb+srv://pfy1:uol123@timetablemanagement.uq12hfp.mongodb.net/?retryWrites=true&w=majority";
    	String databaseName = "LectureHall"; 
    	String collectionName = "TT_LectureHall";

        try { 
        	//ConnectionString connString = new ConnectionString(connectionString); 
        	MongoClient mongoClient =MongoClients.create(connectionString); 
        	MongoDatabase database = mongoClient.getDatabase(databaseName); 
        	moduleCollection = database.getCollection(collectionName);
    	    System.out.println("Connected Successfully"); 
    	    }catch(Exception e) {
    	    	System.err.println("Error connecting: "+ e.getMessage());
    	    	e.printStackTrace(); System.exit(1); 
    	    } 
    }

    public void addLH(LectureHall lecture) {
    	if (lectureExists(lecture.getCode())) {
            System.out.println("Error: Module already");
            return;
        }else {
        Document userDocument = new Document("Hall_Code", lecture.getCode())
        		.append("Building_Name", lecture.getBuilding())
        		.append("Room_No", lecture.getRoom())
        		.append("No_of_people", lecture.getPeople());

        moduleCollection.insertOne(userDocument);
        System.out.println("Class added successfully!");
        }
    }
    
    private boolean lectureExists(String code) {
        return moduleCollection.countDocuments(
                new Document("$or", List.of(
                        new Document("Hall_Code", code)
                ))
        ) > 0;
    }
}


public class tt_admin {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        adminDatabase ADdatabase = new adminDatabase();
        studentDatabase STdatabase = new studentDatabase();
        teacherDatabase TEdatabase = new teacherDatabase();
        moduleDatabase MDdatabase = new moduleDatabase();       
        lecturehallDatabase LHdatabase = new lecturehallDatabase();

        while (true) {
        	System.out.println("1. Add Admin");
        	System.out.println("2. Display Admin");
            System.out.println("3. Delete Admin");
            System.out.println("4. Add Student");
            System.out.println("5. Display Students");
            System.out.println("6. Delete Student");
            System.out.println("7. Add Teacher");
            System.out.println("8. Display Teachers");
            System.out.println("9. Delete Teacher");
            System.out.println("10. Update User");
            System.out.println("11. Add Module");
            System.out.println("12. Add Lecture Hall");
            System.out.println("13. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
            	case 1:
            		System.out.print("Enter Admin Id: ");
            		String ADid = scanner.nextLine();
            		System.out.print("Enter First Name: ");
            		String ADfname = scanner.nextLine();
            		System.out.print("Enter Last Name: ");
            		String ADlname = scanner.nextLine();
            		System.out.print("Enter Course: ");
            		String ADcourse = scanner.nextLine();
            		System.out.print("Enter username: ");
            		String ADusername = scanner.nextLine();
            		System.out.print("Enter password: ");
            		String ADpassword = scanner.nextLine();
            		
            		adminUser ADnewUser = new adminUser(ADid, ADfname, ADlname, ADcourse, ADusername, ADpassword);
                    ADdatabase.ADaddUser(ADnewUser);
                    break;
                    
            	case 2:
                    ADdatabase.displayADUsers();
                    break;
                    
            	case 3:
                  	 System.out.print("Enter Admin ID to delete: ");
                       String ADId = scanner.nextLine();
                       ADdatabase.deleteADuser(ADId);
                       break;
            		
                case 4:
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

                case 5:
                    STdatabase.displaySTUsers();
                    break;
                    
                case 6:
               	 System.out.print("Enter student ID to delete: ");
                    String STId = scanner.nextLine();
                    STdatabase.deleteSTuser(STId);
                    break;
                
                case 7:
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
                    
                case 8:
                	TEdatabase.displayTEUsers();
                    break;
                    
                case 9:
                	 System.out.print("Enter teacher ID to delete: ");
                     String TEId = scanner.nextLine();
                     TEdatabase.deleteTEuser(TEId);
                     break;
                     
                case 10:
                	System.out.println("Enter user type");
                	System.out.println("1. Admin");
                	System.out.println("2. Teacher");
                	System.out.println("3. Student");
                	System.out.println("Enter your choice: ");
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
                        ADdatabase.ADUpdate(userIdToUpdate, newFName, newLName, newCourse, newUsername, newPassword);
                    } else if (userTypeToUpdate == 2) {
                    	TEdatabase.TEUpdate(userIdToUpdate, newFName, newLName, newCourse, newUsername, newPassword);
                    } else if (userTypeToUpdate == 3) {
                        STdatabase.STUpdate(userIdToUpdate, newFName, newLName, newCourse, newUsername, newPassword);
                    } else {
                        System.out.println("Invalid user type. Please enter 1 for Teacher 2 for Student.");
                    }
                    break;
                
                case 11:
                	System.out.print("Enter Module Code: ");
                	String MDcode = scanner.nextLine();
                	System.out.print("Enter Module Name: ");
                    String MDname = scanner.nextLine();
                    System.out.print("Enter Module Description: ");
                    String MDdescription = scanner.nextLine();
                    System.out.print("Enter Module Teacher: ");
                    String MDteacher = scanner.nextLine();
                                
                    Module newMod = new Module(MDcode, MDname, MDdescription, MDteacher);
                    MDdatabase.addModule(newMod);
                    break;
                    
                case 12:
                	System.out.print("Enter Class Code: ");
                	String LHcode = scanner.nextLine();
                	System.out.print("Enter Building Name: ");
                    String LHbuilding = scanner.nextLine();
                    System.out.print("Enter Room Number: ");
                    int LHroom = scanner.nextInt();
                    System.out.print("Enter maximum no. of people allowed in the class: ");
                    int LHpeople = scanner.nextInt();
                                
                    LectureHall newHall = new LectureHall(LHcode, LHbuilding, LHroom, LHpeople);
                    LHdatabase.addLH(newHall);
                    break;
                    
                case 13:
                	System.out.println("Exiting the admin panel. Goodbye!");
                    System.exit(0);
                    
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}