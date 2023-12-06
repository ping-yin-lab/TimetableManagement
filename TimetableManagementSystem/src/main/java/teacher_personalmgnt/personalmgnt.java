package teacher_personalmgnt;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import java.time.LocalDateTime;

public class personalmgnt {
	public static class contact {
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

	public static class contactDatabase {
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
			Bson filtering = Filters.eq("type", "Teacher");
			ContactCollection.find(filtering).forEach(document -> System.out
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

	private static MongoCollection<Document> scheduleCollection;

	public static void addPersonalSchedule(Personal_Schedule TT) {
		Document userDocument = new Document("userid", TT.getUserid())
				.append("title", TT.getSchedulename())
				.append("start time", TT.getStarttime())
				.append("end time", TT.getEndtime())
				.append("type", TT.getType());
		scheduleCollection.insertOne(userDocument);
		System.out.println("Schedule added successfully!");
	}

	public static void updatePersonalSchedule(Personal_Schedule TT, Bson Updatefilter) {
		Document updateDoc = new Document("userid", TT.getUserid())
				.append("title", TT.getSchedulename())
				.append("start time", TT.getStarttime())
				.append("end time", TT.getEndtime())
				.append("type", TT.getType());
		Bson updateop = new Document("$set", updateDoc);
		scheduleCollection.updateOne(Updatefilter, updateop);
		System.out.println("Schedule updated successfully!");
	}

	public static void displaySchedule() {
		System.out.println("List of Schedule:");
		scheduleCollection.find()
				.forEach(document -> System.out
						.println("Title: " + document.get("title") + "\n Start time: " + document.get("start time")
								+ "\n End time: " + document.get("end time") + "\n==========================\n"));
	}

	public static void displayTimeoff() {
		System.out.println("List of Timeoff:");
		Bson filtering = Filters.eq("type", "TimeoffT");
		scheduleCollection.find(filtering)
				.forEach(document -> System.out
						.println("Title: " + document.get("title") + "\n Start time: " + document.get("start time")
								+ "\n End time: " + document.get("end time") + "\n==========================\n"));
	}

	public static void displaySchedulewithID() {
		System.out.println("List of Schedule:");
		AtomicInteger index = new AtomicInteger(1);

		scheduleCollection.find().forEach(document -> System.out.println(index.getAndIncrement()
				+ "  Id : " + document.get("_id")
				+ "\nTitle: " + document.get("title")
				+ "\nStart time: " + document.get("start time")
				+ "\nEnd time: " + document.get("end time")
				+ "\n==========================\n"));
	}

	

	public static void main(String[] args) {

		String connectionString = "mongodb+srv://pfy1:uol123@timetablemanagement.uq12hfp.mongodb.net/?retryWrites=true&w=majority";
		String databaseName = "Schedule";
		String collectionName = "TT_Sche";

		try {
			ConnectionString connString = new ConnectionString(connectionString);
			MongoClient mongoClient = MongoClients.create(connectionString);
			MongoDatabase database = mongoClient.getDatabase(databaseName);
			scheduleCollection = database.getCollection(collectionName);
			System.out.println("Schedule Connected Successfully");
		} catch (Exception e) {
			System.err.println("Error connecting: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
		contactDatabase CTDatabase = new contactDatabase();

		Scanner reader = new Scanner(System.in);
		System.out.println("Welcome to Teacher System");
		System.out.println("==========================");
		System.out.println("Choose the menu below");
		System.out.println("1. Personal Schedule Management");
		System.out.println("2. Module Management");
		System.out.println("3. Class Management");
		System.out.println("4. Time off Management");
		System.out.println("5. Contact Management");
		System.out.print("Input : ");
		String choice = reader.nextLine();
		switch (choice) {
			case "1":
				int PerSche;
				do {
					System.out.println("Personal Schedule Management");
					System.out.println("Your Schedule");
					System.out.println("==========================");
					displaySchedule();
					System.out.println("1. Add new Personal Schedule");
					System.out.println("2. Update schedule");
					System.out.println("3. Delete schedule");
					System.out.println("4. Back to main menu");
					System.out.print("Input : ");
					PerSche = Integer.parseInt(reader.nextLine());
					switch (PerSche) {
						case 1:
							System.out.println("Enter Personal Schedule title: ");
							String stitle = reader.nextLine();
							System.out.println("==Input Starting time==");
							System.out.print("Enter Date in format yyyy-mm-dd :");
							String stdate = reader.nextLine();
							System.out.print("Enter Starting time in format HH:mm :");
							String sttime = reader.nextLine();
							LocalDateTime stdt = LocalDateTime.parse(stdate + "T" + sttime);
							System.out.println("Your startting time : " + stdt);
							System.out.println("==Input Ending time==");
							System.out.print("Enter Date in format yyyy-mm-dd :");
							String eddate = reader.nextLine();
							System.out.print("Enter Ending time in format HH:mm :");
							String edtime = reader.nextLine();
							LocalDateTime eddt = LocalDateTime.parse(eddate + "T" + edtime);
							System.out.println("Your Ending time : " + eddt);
							Personal_Schedule psche = new Personal_Schedule(1, stitle, stdt, eddt, "PersonalT");
							addPersonalSchedule(psche);
							break;
						case 2:
							System.out.println("Updating exist schedule");
							System.out.print("Enter Personal Schedule title you want to change: ");
							String target = reader.nextLine();
							Bson Updatefilter = Filters.eq("title", target);

							System.out.println("New Title : ");
							stitle = reader.nextLine();
							System.out.println("==Input New Starting time==");
							System.out.print("Enter Date in format yyyy-mm-dd :");
							stdate = reader.nextLine();
							System.out.print("Enter Starting time in format HH:mm :");
							sttime = reader.nextLine();
							stdt = LocalDateTime.parse(stdate + "T" + sttime);
							System.out.println("==Input New Ending time==");
							System.out.print("Enter Date in format yyyy-mm-dd :");
							eddate = reader.nextLine();
							System.out.print("Enter Ending time in format HH:mm :");
							edtime = reader.nextLine();
							eddt = LocalDateTime.parse(eddate + "T" + edtime);
							Personal_Schedule Updatepsche = new Personal_Schedule(1, stitle, stdt, eddt, "PersonalT");
							updatePersonalSchedule(Updatepsche, Updatefilter);

							break;
						case 3:
							displaySchedulewithID();
							System.out.println("Enter the title of which item you want to delete : ");
							String title = reader.nextLine();
							Bson filter = Filters.eq("title", title);
							scheduleCollection.deleteOne(filter);
							System.out.println("Deletion successfully");
							break;
						case 4:
							break;
						default:
							break;
					}
				} while (PerSche != 5);

			case "2":
				break;
			case "3":
				break;
			case "4":
				int TimeOff;
				do {
					System.out.println("Time off Management");
					System.out.println("Your Time off");
					System.out.println("==========================");
					displayTimeoff();
					System.out.println("1. Add new Time off Schedule");
					System.out.println("2. Update Time off schedule");
					System.out.println("3. Delete Time off schedule");
					System.out.println("4. Back to main menu");
					System.out.print("Input : ");
					TimeOff = Integer.parseInt(reader.nextLine());
					switch (TimeOff) {
						case 1:
							System.out.println("Enter Time off title: ");
							String stitle = reader.nextLine();
							System.out.println("==Input Starting time==");
							System.out.print("Enter Date in format yyyy-mm-dd :");
							String stdate = reader.nextLine();
							System.out.print("Enter Starting time in format HH:mm :");
							String sttime = reader.nextLine();
							LocalDateTime stdt = LocalDateTime.parse(stdate + "T" + sttime);
							System.out.println("Your startting time : " + stdt);
							System.out.println("==Input Ending time==");
							System.out.print("Enter Date in format yyyy-mm-dd :");
							String eddate = reader.nextLine();
							System.out.print("Enter Ending time in format HH:mm :");
							String edtime = reader.nextLine();
							LocalDateTime eddt = LocalDateTime.parse(eddate + "T" + edtime);
							System.out.println("Your Ending time : " + eddt);
							Personal_Schedule psche = new Personal_Schedule(1, stitle, stdt, eddt, "TimeoffT");
							addPersonalSchedule(psche);
							break;
						case 2:
							System.out.println("Updating exist schedule");
							System.out.print("Enter Personal Schedule title you want to change: ");
							String target = reader.nextLine();
							Bson Updatefilter = Filters.eq("title", target);

							System.out.println("New Title : ");
							stitle = reader.nextLine();
							System.out.println("==Input New Starting time==");
							System.out.print("Enter Date in format yyyy-mm-dd :");
							stdate = reader.nextLine();
							System.out.print("Enter Starting time in format HH:mm :");
							sttime = reader.nextLine();
							stdt = LocalDateTime.parse(stdate + "T" + sttime);
							System.out.println("==Input New Ending time==");
							System.out.print("Enter Date in format yyyy-mm-dd :");
							eddate = reader.nextLine();
							System.out.print("Enter Ending time in format HH:mm :");
							edtime = reader.nextLine();
							eddt = LocalDateTime.parse(eddate + "T" + edtime);
							Personal_Schedule Updatepsche = new Personal_Schedule(1, stitle, stdt, eddt, "PersonalT");
							updatePersonalSchedule(Updatepsche, Updatefilter);

							break;
						case 3:
							displaySchedulewithID();
							System.out.println("Enter the title of which item you want to delete : ");
							String title = reader.nextLine();
							Bson filter = Filters.eq("title", title);
							scheduleCollection.deleteOne(filter);
							System.out.println("Deletion successfully");
							break;
						case 4:
							break;
						default:
							break;
					}
				} while (TimeOff != 5);
		case "5":
		System.out.println("==== Contact Management ====");
					CTDatabase.displayContact();
					System.out.println("1. Add new Contact");
					System.out.println("2. Update Contact");
					System.out.println("3. Delete Contact");
					System.out.println("4. Back to main menu");
					System.out.print("Input : ");
					int contact_choice = Integer.parseInt(reader.nextLine());
					switch (contact_choice) {
						case 1:
							System.out.println("Enter Contact name: ");
							String contactName = reader.nextLine();

							System.out.print("Enter your email :");
							String conemail = reader.nextLine();

							System.out.print("Enter your phone :");
							String conphone = reader.nextLine();

							contact contactelement = new contact(contactName, "Teacher", conemail, conphone);
							CTDatabase.addContact(contactelement);
							break;
						case 2:
							System.out.println("Updating exist Contact");
							System.out.print("Enter name you want to change: ");
							String target = reader.nextLine();
							Bson Updatefilter = Filters.eq("name", target);

							System.out.print("Enter your new email :");
							conemail = reader.nextLine();

							System.out.print("Enter your new phone :");
							conphone = reader.nextLine();

							contact updatecontactelement = new contact(target, "Teacher", conemail, conphone);
							CTDatabase.updateContact(updatecontactelement, Updatefilter);
							break;
						case 3:
							System.out.println("Enter the name of which item you want to delete : ");
							String name = reader.nextLine();
							Bson filter = Filters.eq("name", name);
							CTDatabase.deleteexam(filter);
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
