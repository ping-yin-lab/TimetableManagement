package admin_mgmt;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import teacher_personalmgnt.Personal_Schedule;

class checkDateValidity {
	public boolean checkdate(String date) {
		if (date.length() > 10) {
			return false;
		}
		if (!date.contains("-")) {
			return false;
		}
		return true;
	}
}

class contact {
	private String id;
	public String name;
	public String type;
	public String email;
	public String telephone;

	public contact(String name, String type, String email, String telephone) {
		this.name = name;
		this.type = type;
		this.email = email;
		this.telephone = telephone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
}

class contactDatabase {
	private MongoCollection<Document> ContactCollection;

	public contactDatabase() {
		String connectionString = "mongodb+srv://pfy1:uol123@timetablemanagement.uq12hfp.mongodb.net/?retryWrites=true&w=majority";
		String databaseName = "Contact";
		String collectionName = "Contactlist";

		try {
			ConnectionString connString = new ConnectionString(connectionString);
			MongoClient mongoClient = MongoClients.create(connectionString);
			MongoDatabase database = mongoClient.getDatabase(databaseName);
			ContactCollection = database.getCollection(collectionName);
			System.out.println("Connected Student Successfully");
		} catch (Exception e) {
			System.err.println("Error connecting: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void addContact(contact contactelement) {
		Document userDocument = new Document("name", contactelement.getName())
				.append("email", contactelement.getEmail())
				.append("phone", contactelement.getTelephone())
				.append("type", contactelement.getType());
		ContactCollection.insertOne(userDocument);
		System.out.println("Contact added successfully!");
	}

	public void displayContact() {
		System.out.println("List of Contacts:");
		ContactCollection.find().forEach(document -> System.out
				.println("Name: " + document.get("name") + ", Email: " + document.get("email")
						+ ", phone: " + document.get("phone")));
	}

	public void updateContact(contact updatecontactelement, Bson updatefilter) {
		Document updatecon = new Document("name", updatecontactelement.getName())
				.append("email", updatecontactelement.getEmail())
				.append("phone", updatecontactelement.getTelephone())
				.append("type", updatecontactelement.getType());
		Bson updateop = new Document("$set", updatecon);
		ContactCollection.updateOne(updatefilter, updateop);
		System.out.println("Contact updated successfully!");
	}

	public void deleteexam(Bson filter) {
		ContactCollection.deleteOne(filter);
		System.out.println("Exam Deleted successfully!");
	}
}

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

class scheduleDatabase {
	private MongoCollection<Document> SDCollection;

	public scheduleDatabase() {
		String connectionString = "mongodb+srv://pfy1:uol123@timetablemanagement.uq12hfp.mongodb.net/?retryWrites=true&w=majority";
		String databaseName = "Schedule";
		String collectionName = "TT_Sche";

		try {
			ConnectionString connString = new ConnectionString(connectionString);
			MongoClient mongoClient = MongoClients.create(connectionString);
			MongoDatabase database = mongoClient.getDatabase(databaseName);
			SDCollection = database.getCollection(collectionName);
			System.out.println("Connected Schedule Successfully");
		} catch (Exception e) {
			System.err.println("Error connecting: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void displayExam() {
		System.out.println("List of Exam:");
		Bson filtering = Filters.eq("type", "Exam");
		SDCollection.find(filtering)
				.forEach(document -> System.out
						.println("Title: " + document.get("title") + "\n Start time: " + document.get("start time")
								+ "\n End time: " + document.get("end time") + "\n==========================\n"));

	}

	public void addExamSchedule(Personal_Schedule TT) {
		Document userDocument = new Document("userid", TT.getUserid()).append("title", TT.getSchedulename())
				.append("start time", TT.getStarttime()).append("end time", TT.getEndtime())
				.append("type", TT.getType());
		SDCollection.insertOne(userDocument);
		System.out.println("Exam added successfully!");

	}

	public void updatePersonalSchedule(Personal_Schedule TT, Bson updatefilter) {
		Document updateDoc = new Document("userid", TT.getUserid()).append("title", TT.getSchedulename())
				.append("start time", TT.getStarttime()).append("end time", TT.getEndtime())
				.append("type", TT.getType());
		Bson updateop = new Document("$set", updateDoc);
		SDCollection.updateOne(updatefilter, updateop);
		System.out.println("Exam updated successfully!");
	}

	public void deleteexam(Bson filter) {
		SDCollection.deleteOne(filter);
		System.out.println("Exam Deleted successfully!");
	}

	public void displayHoliday() {
		System.out.println("List of Holidays:");
		Bson filtering = Filters.eq("type", "Holiday");
		SDCollection.find(filtering)
				.forEach(document -> System.out
						.println("Title: " + document.get("title") + "\n Start time: " + document.get("start time")
								+ "\n End time: " + document.get("end time") + "\n==========================\n"));

	}

	public void addHoliday(Personal_Schedule TT) {
		Document userDocument = new Document("userid", TT.getUserid()).append("title", TT.getSchedulename())
				.append("start time", TT.getStarttime()).append("end time", TT.getEndtime())
				.append("type", TT.getType());
		SDCollection.insertOne(userDocument);
		System.out.println("Holiday added successfully!");

	}

	public void updateHoliday(Personal_Schedule TT, Bson updatefilter) {
		Document updateDoc = new Document("userid", TT.getUserid()).append("title", TT.getSchedulename())
				.append("start time", TT.getStarttime()).append("end time", TT.getEndtime())
				.append("type", TT.getType());
		Bson updateop = new Document("$set", updateDoc);
		SDCollection.updateOne(updatefilter, updateop);
		System.out.println("Holiday updated successfully!");
	}

	public void deleteHoliday(Bson filter) {
		SDCollection.deleteOne(filter);
		System.out.println("Holiday Deleted successfully!");
	}

	// Custom_evenet_Managment
	public void displayCEvent() {
		System.out.println("List of Custom Event:");
		Bson filtering = Filters.eq("type", "Event");
		SDCollection.find(filtering)
				.forEach(document -> System.out
						.println("Title: " + document.get("title") + "\n Start time: " + document.get("start time")
								+ "\n End time: " + document.get("end time") + "\n==========================\n"));

	}

	public void addCEvent(Personal_Schedule TT) {
		Document userDocument = new Document("userid", TT.getUserid()).append("title", TT.getSchedulename())
				.append("start time", TT.getStarttime()).append("end time", TT.getEndtime())
				.append("type", TT.getType());
		SDCollection.insertOne(userDocument);
		System.out.println("Custom event added successfully!");

	}

	public void updateCEvent(Personal_Schedule TT, Bson updatefilter) {
		Document updateDoc = new Document("userid", TT.getUserid()).append("title", TT.getSchedulename())
				.append("start time", TT.getStarttime()).append("end time", TT.getEndtime())
				.append("type", TT.getType());
		Bson updateop = new Document("$set", updateDoc);
		SDCollection.updateOne(updatefilter, updateop);
		System.out.println("Custom event updated successfully!");
	}

	public void deleteCEvent(Bson filter) {
		SDCollection.deleteOne(filter);
		System.out.println("Custom Event Deleted successfully!");
	}
}

class Schedule {
	private int id;
	public int Userid;
	public String schedulename;
	public LocalDateTime starttime;
	public LocalDateTime endtime;
	public String type;

	public Schedule(int userid, String name, LocalDateTime sttime, LocalDateTime edtime, String type) {
		this.Userid = userid;
		this.schedulename = name;
		this.starttime = sttime;
		this.endtime = edtime;
		this.type = type;
	}

	public int getUserid() {
		return Userid;
	}

	public String getSchedulename() {
		return schedulename;
	}

	public void setSchedulename(String schedulename) {
		this.schedulename = schedulename;
	}

	public LocalDateTime getStarttime() {
		return starttime;
	}

	public void setStarttime(LocalDateTime starttime) {
		this.starttime = starttime;
	}

	public LocalDateTime getEndtime() {
		return endtime;
	}

	public void setEndtime(LocalDateTime endtime) {
		this.endtime = endtime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
			MongoClient mongoClient = MongoClients.create(connectionString);
			MongoDatabase database = mongoClient.getDatabase(databaseName);
			STusersCollection = database.getCollection(collectionName);
			System.out.println("Connected Student Successfully");
		} catch (Exception e) {
			System.err.println("Error connecting: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void STaddUser(studentUser user) {
		if (userExists(user.getUsername(), user.getID())) {
			System.out.println("Error: Username or ID already exists. Please choose different values.");
			return;
		} else {
			Document userDocument = new Document("Student_ID", user.getID()).append("first_name", user.getFName())
					.append("last_name", user.getLName()).append("course", user.getCourse())
					.append("username", user.getUsername()).append("password", user.getPassword());

			STusersCollection.insertOne(userDocument);
			System.out.println("User added successfully!");
		}
	}

	private boolean userExists(String username, String id) {
		return STusersCollection.countDocuments(
				new Document("$or", List.of(new Document("username", username), new Document("id", id)))) > 0;
	}

	public void displaySTUsers() {
		System.out.println("List of Students:");
		STusersCollection.find().forEach(document -> System.out
				.println("Username: " + document.get("username") + ", Password: " + document.get("password")));
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
			MongoClient mongoClient = MongoClients.create(connectionString);
			MongoDatabase database = mongoClient.getDatabase(databaseName);
			TEusersCollection = database.getCollection(collectionName);
			System.out.println("Connected Teacher Successfully");
		} catch (Exception e) {
			System.err.println("Error connecting: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void TEaddUser(teacherUser user) {
		if (userExists(user.getUsername(), user.getID())) {
			System.out.println("Error: Username or ID already exists. Please choose different values.");
			return;
		} else {
			Document userDocument = new Document("Teacher_ID", user.getID()).append("first_name", user.getFName())
					.append("last_name", user.getLName()).append("course", user.getCourse())
					.append("username", user.getUsername()).append("password", user.getPassword());

			TEusersCollection.insertOne(userDocument);
			System.out.println("User added successfully!");
		}
	}

	private boolean userExists(String username, String id) {
		return TEusersCollection.countDocuments(
				new Document("$or", List.of(new Document("username", username), new Document("id", id)))) > 0;
	}

	public void displayTEUsers() {
		System.out.println("List of Teachers:");
		TEusersCollection.find().forEach(document -> System.out
				.println("Username: " + document.get("username") + ", Password: " + document.get("password")));
	}
}

public class tt_admin {
	public static void main(String[] args) {
		checkDateValidity dttester = new checkDateValidity();
		Scanner scanner = new Scanner(System.in);
		studentDatabase STdatabase = new studentDatabase();
		teacherDatabase TEdatabase = new teacherDatabase();
		scheduleDatabase SDDatabase = new scheduleDatabase();
		contactDatabase CTDatabase = new contactDatabase();
		while (true) {
			System.out.println("1. Add Student");
			System.out.println("2. Display Students");
			System.out.println("3. Add Teacher");
			System.out.println("4. Display Teachers");
			System.out.println("5. Schedule Management");
			System.out.println("6. Contact Management");
			System.out.println("7. Exit");

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

					studentUser STnewUser = new studentUser(STid, STfname, STlname, STcourse, STusername, STpassword);
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

					teacherUser TEnewUser = new teacherUser(TEid, TEfname, TElname, TEcourse, TEusername, TEpassword);
					TEdatabase.TEaddUser(TEnewUser);
					break;

				case 4:
					TEdatabase.displayTEUsers();
					break;
				case 5:
					System.out.println("Schedule Management System");
					System.out.println("1. Class Schedule Management");
					System.out.println("2. Exam Management");
					System.out.println("3. Holiday Management");
					System.out.println("4. Custom Event Teachers");
					System.out.println("5. exit to main menu");
					System.out.println("Input : ");
					int Schedule_choice = scanner.nextInt();
					scanner.nextLine(); // Consume the newline character
					String stdate;
					String sttime;
					String eddate;
					String edtime;
					LocalDateTime stdt;
					LocalDateTime eddt;
					switch (Schedule_choice) {
						case 1:
							break;
						case 2:
							System.out.println("==== Exam Management ====");
							SDDatabase.displayExam();
							System.out.println("1. Add new Exam Schedule");
							System.out.println("2. Update exam schedule");
							System.out.println("3. Delete exam schedule");
							System.out.println("4. Back to main menu");
							System.out.print("Input : ");
							int exam_choice = Integer.parseInt(scanner.nextLine());
							switch (exam_choice) {
								case 1:
									System.out.println("Enter Exam Schedule title: ");
									String stitle = scanner.nextLine();
									System.out.println("==Input Starting time==");
									System.out.print("Enter Date in format yyyy-mm-dd :");
									stdate = scanner.nextLine();
									if (dttester.checkdate(stdate)) {
										System.out.print("Enter Starting time in format HH:mm :");
										sttime = scanner.nextLine();
										stdt = LocalDateTime.parse(stdate + "T" + sttime);
										System.out.println("Your startting time : " + stdt);
										System.out.println("==Input Ending time==");
										System.out.print("Enter Date in format yyyy-mm-dd :");
										eddate = scanner.nextLine();
										System.out.print("Enter Ending time in format HH:mm :");
										edtime = scanner.nextLine();
										eddt = LocalDateTime.parse(eddate + "T" + edtime);
										System.out.println("Your Ending time : " + eddt);
										Personal_Schedule examsche = new Personal_Schedule(1, stitle, stdt, eddt,
												"Exam");
										SDDatabase.addExamSchedule(examsche);
									} else {
										System.out.println("Invalid date time input! Please follow the format");
										break;
									}
									break;
								case 2:
									System.out.println("Updating exist Exam");
									System.out.print("Enter Exam title you want to change: ");
									String target = scanner.nextLine();
									Bson Updatefilter = Filters.eq("title", target);
									System.out.println("New Exam Title : ");
									stitle = scanner.nextLine();
									System.out.println("==Input New Starting time==");
									System.out.print("Enter Date in format yyyy-mm-dd :");
									stdate = scanner.nextLine();
									System.out.print("Enter Starting time in format HH:mm :");
									sttime = scanner.nextLine();
									stdt = LocalDateTime.parse(stdate + "T" + sttime);
									System.out.println("==Input New Ending time==");
									System.out.print("Enter Date in format yyyy-mm-dd :");
									eddate = scanner.nextLine();
									System.out.print("Enter Ending time in format HH:mm :");
									edtime = scanner.nextLine();
									eddt = LocalDateTime.parse(eddate + "T" + edtime);
									Personal_Schedule Updatepsche = new Personal_Schedule(1, stitle, stdt, eddt,
											"Exam");
									SDDatabase.updatePersonalSchedule(Updatepsche, Updatefilter);
									break;
								case 3:
									System.out.println("Enter the title of which item you want to delete : ");
									String title = scanner.nextLine();
									Bson filter = Filters.eq("title", title);
									SDDatabase.deleteexam(filter);
									break;

								case 4:
									break;
							}
						case 3:
							System.out.println("==== Holiday Management ====");
							SDDatabase.displayHoliday();
							System.out.println("1. Add new Holiday Schedule");
							System.out.println("2. Update Holiday schedule");
							System.out.println("3. Delete Holiday schedule");
							System.out.println("4. Back to main menu");
							System.out.print("Input : ");
							int holi_choice = Integer.parseInt(scanner.nextLine());
							switch (holi_choice) {
								case 1:
									System.out.println("Enter Holiday title: ");
									String stitle = scanner.nextLine();
									System.out.println("==Input Starting time==");
									System.out.print("Enter Date in format yyyy-mm-dd :");
									stdate = scanner.nextLine();
									System.out.print("Enter Starting time in format HH:mm :");
									sttime = scanner.nextLine();
									stdt = LocalDateTime.parse(stdate + "T" + sttime);
									System.out.println("Your startting time : " + stdt);
									System.out.println("==Input Ending time==");
									System.out.print("Enter Date in format yyyy-mm-dd :");
									eddate = scanner.nextLine();
									System.out.print("Enter Ending time in format HH:mm :");
									edtime = scanner.nextLine();
									eddt = LocalDateTime.parse(eddate + "T" + edtime);
									System.out.println("Your Ending time : " + eddt);
									Personal_Schedule holische = new Personal_Schedule(1, stitle, stdt, eddt,
											"Holiday");
									SDDatabase.addHoliday(holische);
									break;
								case 2:
									System.out.println("Updating exist Holiday");
									System.out.print("Enter Holiday title you want to change: ");
									String target = scanner.nextLine();
									Bson Updatefilter = Filters.eq("title", target);

									System.out.println("New Holiday Title : ");
									stitle = scanner.nextLine();
									System.out.println("==Input New Starting time==");
									System.out.print("Enter Date in format yyyy-mm-dd :");
									stdate = scanner.nextLine();
									System.out.print("Enter Starting time in format HH:mm :");
									sttime = scanner.nextLine();
									stdt = LocalDateTime.parse(stdate + "T" + sttime);
									System.out.println("==Input New Ending time==");
									System.out.print("Enter Date in format yyyy-mm-dd :");
									eddate = scanner.nextLine();
									System.out.print("Enter Ending time in format HH:mm :");
									edtime = scanner.nextLine();
									eddt = LocalDateTime.parse(eddate + "T" + edtime);
									Personal_Schedule Updatepsche = new Personal_Schedule(1, stitle, stdt, eddt,
											"Holiday");
									SDDatabase.updateHoliday(Updatepsche, Updatefilter);
									break;
								case 3:
									System.out.println("Enter the title of which item you want to delete : ");
									String title = scanner.nextLine();
									Bson filter = Filters.eq("title", title);
									SDDatabase.deleteHoliday(filter);
									break;

								case 4:
									break;
								case 6:
									System.out.println("Exiting the admin panel. Goodbye!");
									System.exit(0);

								default:
									System.out.println("Invalid choice. Please enter a valid option.");
							}
						case 4:
							System.out.println("==== Customer Event Management ====");
							SDDatabase.displayCEvent();
							System.out.println("1. Add new Event Schedule");
							System.out.println("2. Update Event schedule");
							System.out.println("3. Delete Event schedule");
							System.out.println("4. Back to main menu");
							System.out.print("Input : ");
							int event_choice = Integer.parseInt(scanner.nextLine());
							switch (event_choice) {
								case 1:
									System.out.println("Enter Event title: ");
									String stitle = scanner.nextLine();
									System.out.println("==Input Starting time==");
									System.out.print("Enter Date in format yyyy-mm-dd :");
									stdate = scanner.nextLine();
									System.out.print("Enter Starting time in format HH:mm :");
									sttime = scanner.nextLine();
									stdt = LocalDateTime.parse(stdate + "T" + sttime);
									System.out.println("==Input Ending time==");
									System.out.print("Enter Date in format yyyy-mm-dd :");
									eddate = scanner.nextLine();
									System.out.print("Enter Ending time in format HH:mm :");
									edtime = scanner.nextLine();
									eddt = LocalDateTime.parse(eddate + "T" + edtime);
									System.out.println("Your Ending time : " + eddt);
									Personal_Schedule holische = new Personal_Schedule(1, stitle, stdt, eddt, "Event");
									SDDatabase.addCEvent(holische);
									break;
								case 2:
									System.out.println("Updating exist Event");
									System.out.print("Enter Event title you want to change: ");
									String target = scanner.nextLine();
									Bson Updatefilter = Filters.eq("title", target);

									System.out.println("New Event Title : ");
									stitle = scanner.nextLine();
									System.out.println("==Input New Starting time==");
									System.out.print("Enter Date in format yyyy-mm-dd :");
									stdate = scanner.nextLine();
									System.out.print("Enter Starting time in format HH:mm :");
									sttime = scanner.nextLine();
									stdt = LocalDateTime.parse(stdate + "T" + sttime);
									System.out.println("==Input New Ending time==");
									System.out.print("Enter Date in format yyyy-mm-dd :");
									eddate = scanner.nextLine();
									System.out.print("Enter Ending time in format HH:mm :");
									edtime = scanner.nextLine();
									eddt = LocalDateTime.parse(eddate + "T" + edtime);
									Personal_Schedule Updatepsche = new Personal_Schedule(1, stitle, stdt, eddt,
											"Event");
									SDDatabase.updateCEvent(Updatepsche, Updatefilter);
									break;
								case 3:
									System.out.println("Enter the title of which item you want to delete : ");
									String title = scanner.nextLine();
									Bson filter = Filters.eq("title", title);
									SDDatabase.deleteCEvent(filter);
									break;
								case 4:
									System.out.println("Returning back to main menu ..");
									break;
								default:
									System.out.println("Invalid input entered");
							}
					}
				case 6:
					System.out.println("==== Contact Management ====");
					CTDatabase.displayContact();
					System.out.println("1. Add new Contact");
					System.out.println("2. Update Contact");
					System.out.println("3. Delete Contact");
					System.out.println("4. Back to main menu");
					System.out.print("Input : ");
					int contact_choice = Integer.parseInt(scanner.nextLine());
					switch (contact_choice) {
						case 1:
							System.out.println("Enter Contact name: ");
							String contactName = scanner.nextLine();

							System.out.print("Enter your email :");
							String conemail = scanner.nextLine();

							System.out.print("Enter your phone :");
							String conphone = scanner.nextLine();

							contact contactelement = new contact(contactName, "Admin", conemail, conphone);
							CTDatabase.addContact(contactelement);
							break;
						case 2:
							System.out.println("Updating exist Contact");
							System.out.print("Enter name you want to change: ");
							String target = scanner.nextLine();
							Bson Updatefilter = Filters.eq("name", target);

							System.out.print("Enter your new email :");
							conemail = scanner.nextLine();

							System.out.print("Enter your new phone :");
							conphone = scanner.nextLine();

							contact updatecontactelement = new contact(target, "Admin", conemail, conphone);
							CTDatabase.updateContact(updatecontactelement, Updatefilter);
							break;
						case 3:
							System.out.println("Enter the name of which item you want to delete : ");
							String name = scanner.nextLine();
							Bson filter = Filters.eq("name", name);
							SDDatabase.deleteCEvent(filter);
							break;
						case 4:
							System.out.println("Returning back to main menu ..");
							break;
						default:
							System.out.println("Invalid input entered");
					}
					break;
			}
		}
	}
}
