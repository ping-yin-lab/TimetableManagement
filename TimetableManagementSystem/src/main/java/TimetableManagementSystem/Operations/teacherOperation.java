package TimetableManagementSystem.Operations;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import java.time.LocalDateTime;

class TSchedule {
    private int id;
    public String Userid;
    public String schedulename;
    public LocalDateTime starttime;
    public LocalDateTime endtime;
    public String type;

    public TSchedule(String userid, String name, LocalDateTime sttime, LocalDateTime edtime, String type) {
        this.Userid = userid;
        this.schedulename = name;
        this.starttime = sttime;
        this.endtime = edtime;
        this.type = type;
    }

    public String getUserid() {
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

class Tcontact {
    private String id;
    public String userid;
    public String name;
    public String type;
    public String email;
    public String telephone;

    public Tcontact(String userid, String name, String type, String email, String telephone) {
        this.userid = userid;
        this.name = name;
        this.type = type;
        this.email = email;
        this.telephone = telephone;
    }

    public String getuserid() {
        return userid;
    }

    public void setuserid(String userid) {
        this.userid = userid;
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

class TcontactDatabase {
    private MongoCollection<Document> ContactCollection;

    public TcontactDatabase() {
        String connectionString = "mongodb+srv://pfy1:uol123@timetablemanagement.uq12hfp.mongodb.net/?retryWrites=true&w=majority";
        String databaseName = "Contact";
        String collectionName = "Contactlist";

        try {
            ConnectionString connString = new ConnectionString(connectionString);
            MongoClient mongoClient = MongoClients.create(connectionString);
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            ContactCollection = database.getCollection(collectionName);
        } catch (Exception e) {
            System.err.println("Error connecting: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public String addContact(Tcontact contactelement) {
        if (contactExists(contactelement.name, contactelement.type)) {
            System.out.println("Error: Contact already exists. Please choose different values.");
            return "Rejected";
        } else {
            Document userDocument = new Document("userid", contactelement.getuserid())
                    .append("name", contactelement.getName())
                    .append("email", contactelement.getEmail()).append("phone", contactelement.getTelephone())
                    .append("type", contactelement.getType());
            ContactCollection.insertOne(userDocument);
            System.out.println("Contact added successfully!");
            return "Passed";
        }
    }

    private boolean contactExists(String name, String type) {
        return ContactCollection.countDocuments(
                new Document("$and", List.of(new Document("name", name), new Document("type", type)))) > 0;
    }

    public void displayContact(String userid) {
        System.out.println("List of Contacts:");
        Bson filter = Filters.and(
                Filters.eq("userid", userid),
                Filters.eq("type", "Teacher"));
        ContactCollection.find(filter).forEach(document -> System.out.println("Name: " + document.get("name")
                + ", Email: " + document.get("email") + ", phone: " + document.get("phone")));
    }

    public Document displayContactspecific(String name) {
        Document result = new Document();
        Bson filtering = Filters.eq("name", name);
        ContactCollection.find(filtering).forEach(document -> result.append("name", document.get("name"))
                .append("email", document.get("email")).append("phone", document.get("phone")));
        return result;
    }

    public String updateContact(Tcontact updatecontactelement) {
        if (contactExists(updatecontactelement.name, updatecontactelement.type)) {
            Document updatecon = new Document("userid", updatecontactelement.getuserid())
                    .append("name", updatecontactelement.getName())
                    .append("email", updatecontactelement.getEmail())
                    .append("phone", updatecontactelement.getTelephone())
                    .append("type", updatecontactelement.getType());
            Bson updateop = new Document("$set", updatecon);
            Bson Updatefilter = Filters.eq("name", updatecontactelement.getName());
            ContactCollection.updateOne(Updatefilter, updateop);
            System.out.println("Contact updated successfully!");
            return "Passed";
        } else {
            System.out.println("Error: Contact not exists. Please create new contact.");
            return "Rejected";
        }
    }

    public String deletecontact(Bson filter) {
        Document result = new Document();
        ContactCollection.find(filter).forEach(document -> result.append("name", document.get("name"))
                .append("email", document.get("email")).append("phone", document.get("phone")));
        if (result.isEmpty()) {
            System.out.println("User not exist!");
            return "Rejected";
        } else {
            ContactCollection.deleteOne(filter);
            System.out.println("Contact Deleted successfully!");
            return "Passed";
        }
    }
}

class TmoduleDatabase {
    private MongoCollection<Document> moduleCollection;

    public TmoduleDatabase() {
        String connectionString = "mongodb+srv://pfy1:uol123@timetablemanagement.uq12hfp.mongodb.net/?retryWrites=true&w=majority";
        String databaseName = "Module";
        String collectionName = "TT_modules";

        try {
            ConnectionString connString = new ConnectionString(connectionString);
            MongoClient mongoClient = MongoClients.create(connectionString);
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            moduleCollection = database.getCollection(collectionName);
            System.out.println("Connected Module Successfully");
        } catch (Exception e) {
            System.err.println("Error connecting: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void displayModuleTeacher(String Teacher_ID) {
        System.out.println("List of Module:");
        Bson filtering = Filters.eq("Module_Teacher", Teacher_ID);
        moduleCollection.find(filtering)
                .forEach(document -> System.out.println("Module Code: " + document.get("Module_Code")
                        + "\n Module Name: " + document.get("Module_Name") + "\n Module Description : "
                        + document.get("Module_Description") + "\n=========================\n"));
    }
}

class TscheduleDatabase {
    private MongoCollection<Document> scheduleCollection;

    public TscheduleDatabase() {
        String connectionString = "mongodb+srv://pfy1:uol123@timetablemanagement.uq12hfp.mongodb.net/?retryWrites=true&w=majority";
        String databaseName = "Schedule";
        String collectionName = "TT_Sche";

        try {
            ConnectionString connString = new ConnectionString(connectionString);
            MongoClient mongoClient = MongoClients.create(connectionString);
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            scheduleCollection = database.getCollection(collectionName);
        } catch (Exception e) {
            System.err.println("Error connecting: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void addPersonalSchedule(Schedule TT) {
        Document userDocument = new Document("userid", TT.getUserid()).append("title", TT.getSchedulename())
                .append("start time", TT.getStarttime()).append("end time", TT.getEndtime())
                .append("type", TT.getType());
        scheduleCollection.insertOne(userDocument);
        System.out.println("Schedule added successfully!");
    }

    public void updatePersonalSchedule(Schedule TT, Bson Updatefilter) {
        Document updateDoc = new Document("userid", TT.getUserid()).append("title", TT.getSchedulename())
                .append("start time", TT.getStarttime()).append("end time", TT.getEndtime())
                .append("type", TT.getType());
        Bson updateop = new Document("$set", updateDoc);
        scheduleCollection.updateOne(Updatefilter, updateop);
        System.out.println("Schedule updated successfully!");
    }

    public void displaySchedule(String userid) {
        System.out.println("List of Schedule:");
        Bson filter = Filters.and(
                Filters.eq("userid", userid),
                Filters.eq("type", "PersonalT"));
        scheduleCollection.find(filter)
                .forEach(document -> System.out
                        .println("Title: " + document.get("title") + "\n Start time: " + document.get("start time")
                                + "\n End time: " + document.get("end time") + "\n==========================\n"));
    }

    public boolean displaySpecific(String title, String type) {
        Document result = new Document();
        Bson filtering = Filters.and(
                Filters.eq("type", type),
                Filters.eq("title", title));
        scheduleCollection.find(filtering)
                .forEach(document -> result.append("title", document.get("title"))
                        .append("start time", document.get("start time"))
                        .append("end time", document.get("end time"))
                        .append("type", document.get("type")));
        if (result.get("title") == null) {
            return false;
        }
        return true;
    }

    public void deleteSchedule(String title, String type) {
        Bson filtering = Filters.and(
                Filters.eq("type", type),
                Filters.eq("title", title));
        scheduleCollection.deleteOne(filtering);
        System.out.println("Schedule Deleted successfully!");
    }

    public void displayTimeoff(String userid) {
        System.out.println("List of TimeOff:");
        Bson filter = Filters.and(
                Filters.eq("userid", userid),
                Filters.eq("type", "TimeoffT"));
        scheduleCollection.find(filter)
                .forEach(document -> System.out
                        .println("Title: " + document.get("title") + "\n Start time: " + document.get("start time")
                                + "\n End time: " + document.get("end time") + "\n==========================\n"));
    }

    public void displayClass(String userid) {
        System.out.println("List of Class:");
        Bson filter = Filters.and(
                Filters.eq("userid", userid),
                Filters.eq("type", "Class"));
        scheduleCollection.find(filter)
                .forEach(document -> System.out
                        .println("Title: " + document.get("title") + "\n Start time: " + document.get("start time")
                                + "\n End time: " + document.get("end time") + "\n==========================\n"));

    }
}

class TeacherFeedback {
    private String teacherId;
    private String feedbackText;
    private String rating;

    public TeacherFeedback(String teacherId, String feedbackText, String rating) {
        this.teacherId = teacherId;
        this.feedbackText = feedbackText;
        this.rating = rating;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public String getRating() {
        return rating;
    }
}

class TeacherFeedbackDatabase {
    private MongoCollection<Document> feedbackCollection;

    public TeacherFeedbackDatabase() {
        String connectionString = "mongodb+srv://pfy1:uol123@timetablemanagement.uq12hfp.mongodb.net/?retryWrites=true&w=majority";
        String databaseName = "FeedbackDB";
        String collectionName = "FeedbackCollection";

        try {
            ConnectionString connString = new ConnectionString(connectionString);
            MongoClient mongoClient = MongoClients.create(connectionString);
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            feedbackCollection = database.getCollection(collectionName);
            System.out.println("successfully connected to student Database");
        } catch (Exception e) {
            System.err.println("Error connecting: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void displayTeacherFeedback(String teacherId) {
        System.out.println("List of Feedback for Teacher " + teacherId + ":");
        feedbackCollection.find(new Document("Teacher_ID", teacherId)).forEach(
                (Consumer<? super Document>) document -> System.out.println("Student ID: " + document.get("Stu_ID") +
                        ", Feedback Text: " + document.get("Feedback_Text") +
                        ", Reply: " + document.get("Reply")));
    }
    // Add methods for updating and deleting feedback if needed
}

public class teacherOperation {
    public static void main(String[] args) {
        String sessionid = args[0];
        System.out.print(sessionid);
        TcontactDatabase CTDatabase = new TcontactDatabase();
        TmoduleDatabase ModuleDatabase = new TmoduleDatabase();
        TscheduleDatabase SDDatabase = new TscheduleDatabase();
        TeacherFeedbackDatabase teacherFeedbackDatabase = new TeacherFeedbackDatabase();
        Scanner reader = new Scanner(System.in);
        int dummy = 0;
        while (dummy == 0) {
            System.out.println("Welcome to Teacher System");
            System.out.println("==========================");
            System.out.println("Choose the menu below");
            System.out.println("1. Personal Schedule Management");
            System.out.println("2. Class Management");
            System.out.println("3. Time off Management");
            System.out.println("4. Contact Management");
            System.out.println("5. Your Modules");
            System.out.println("6. Your feedback");
            System.out.println("7. Exit");
            System.out.print("Input : ");
            String choice = reader.nextLine();
            switch (choice) {
                case "1":
                    int PerSche;
                    do {
                        System.out.println("Personal Schedule Management");
                        System.out.println("Your Schedule");
                        System.out.println("==========================");
                        SDDatabase.displaySchedule(sessionid);
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
                                Schedule psche = new Schedule(sessionid, stitle, stdt, eddt, "PersonalT");
                                SDDatabase.addPersonalSchedule(psche);
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
                                Schedule Updatepsche = new Schedule(sessionid, stitle, stdt, eddt,
                                        "PersonalT");
                                SDDatabase.updatePersonalSchedule(Updatepsche, Updatefilter);

                                break;
                            case 3:
                                System.out.println("Enter the title of which item you want to delete : ");
                                String title = reader.nextLine();
                                SDDatabase.deleteSchedule(title, "PersonalT");
                                System.out.println("Deletion successfully");
                                break;
                            case 4:
                                break;
                            default:
                                break;
                        }
                    } while (PerSche != 4);
                    break;
                case "2":
                    int classindex;
                    do {
                        System.out.println("Class Management");
                        System.out.println("Your class");
                        System.out.println("==========================");
                        SDDatabase.displayClass(sessionid);
                        System.out.println("1. Add new Class Schedule");
                        System.out.println("2. Update Class schedule");
                        System.out.println("3. Delete Class schedule");
                        System.out.println("4. Back to main menu");
                        System.out.print("Input : ");
                        classindex = Integer.parseInt(reader.nextLine());
                        switch (classindex) {
                            case 1:
                                System.out.println("Enter Class title: ");
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
                                Schedule psche = new Schedule(sessionid, stitle, stdt, eddt, "Class");
                                SDDatabase.addPersonalSchedule(psche);
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
                                Schedule Updatepsche = new Schedule(sessionid, stitle, stdt, eddt,
                                        "Class");
                                SDDatabase.updatePersonalSchedule(Updatepsche, Updatefilter);

                                break;
                            case 3:
                                System.out.println("Enter the title of which item you want to delete : ");
                                String title = reader.nextLine();
                                SDDatabase.deleteSchedule(title, "title");
                                System.out.println("Deletion successfully");
                                break;
                            case 4:
                                break;
                            default:
                                break;
                        }
                    } while (classindex != 4);
                    break;
                case "3":
                    int TimeOff;
                    do {
                        System.out.println("Time off Management");
                        System.out.println("Your Time off");
                        System.out.println("==========================");
                        SDDatabase.displayTimeoff(sessionid);
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
                                Schedule psche = new Schedule(sessionid, stitle, stdt, eddt, "TimeoffT");
                                SDDatabase.addPersonalSchedule(psche);
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
                                Schedule Updatepsche = new Schedule(sessionid, stitle, stdt, eddt,
                                        "TimeoffT");
                                SDDatabase.updatePersonalSchedule(Updatepsche, Updatefilter);

                                break;
                            case 3:
                                System.out.println("Enter the title of which item you want to delete : ");
                                String title = reader.nextLine();
                                SDDatabase.deleteSchedule(title, "title");
                                System.out.println("Deletion successfully");
                                break;
                            case 4:
                                break;
                            default:
                                break;
                        }
                    } while (TimeOff != 4);
                    break;
                case "4":
                    System.out.println("==== Contact Management ====");
                    CTDatabase.displayContact(sessionid);
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

                            Tcontact contactelement = new Tcontact(sessionid, contactName, "Teacher", conemail,
                                    conphone);
                            CTDatabase.addContact(contactelement);
                            break;
                        case 2:
                            System.out.println("Updating exist Contact");
                            System.out.print("Enter name you want to change: ");
                            String target = reader.nextLine();

                            System.out.print("Enter your new email :");
                            conemail = reader.nextLine();

                            System.out.print("Enter your new phone :");
                            conphone = reader.nextLine();

                            Tcontact updatecontactelement = new Tcontact(sessionid, target, "Teacher", conemail,
                                    conphone);
                            CTDatabase.updateContact(updatecontactelement);
                            break;
                        case 3:
                            System.out.println("Enter the name of which item you want to delete : ");
                            String name = reader.nextLine();
                            Bson filter = Filters.eq("name", name);
                            CTDatabase.deletecontact(filter);
                            break;
                        case 4:
                            System.out.println("Returning back to main menu ..");
                            break;
                        default:
                            System.out.println("Invalid input entered");
                    }
                    break;
                case "5":
                    ModuleDatabase.displayModuleTeacher("teacher1");
                    break;
                case "6":
                    System.out.print("Enter Teacher ID: ");
                    String teacherId = reader.nextLine();
                    teacherFeedbackDatabase.displayTeacherFeedback(teacherId);
                    break;
                case "7":
                System.out.println("Going back to login .. ");
                    dummy = 1;
                    break;
                default:
                    System.out.println("Invalid input!");
            }
        }
    }
}