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
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import xw256.Database.Databases;

import java.time.LocalDateTime;
import java.time.ZoneId;

class StudentScheduleActions {

    public static void AddStudentSchedule(StudentSchedule ss) {
        Document userDocument = new Document("student id", ss.studentId)
                .append("title", ss.getSchedulename())
                .append("start time", ss.getStarttime())
                .append("end time", ss.getEndtime())
                .append("type", ss.getType());
        // validate if local datetime is overlapped with existed schedules
        if (ValidateSchedule(ss)) {
            Databases.STUDENT_SCHE_DATABASE().insertOne(userDocument);
            System.out.println("Schedule added successfully!\n");
        } else {
            System.out.println("Add schedule failed, please choose another available time.\n");
        }
    }

    public static void UpdateStudentSchedule(StudentSchedule ss, Bson updateFilter) {
        Document updateDoc = new Document("userid", ss.getUserid())
                .append("title", ss.getSchedulename())
                .append("start time", ss.getStarttime())
                .append("end time", ss.getEndtime())
                .append("type", ss.getType());

        if (ValidateSchedule(ss)) {
            Databases.STUDENT_SCHE_DATABASE().insertOne(updateDoc);
            System.out.println("Schedule updated successfully!");
            Bson updateop = new Document("$set", updateDoc);
            Databases.STUDENT_SCHE_DATABASE().updateOne(updateFilter, updateop);
        } else {
            System.out.println("Update schedule failed, please choose another available time.\n");
        }
    }

    public static void DisplaySchedule() {
        System.out.println("List of Schedule:");
        Databases.TEACHER_SCHE_DATABASE().find()
                .forEach(document -> System.out
                        .println("Teacher Schedule" + "\n Title: " + document.get("title") + "\n Start time: "
                                + document.get("start time")
                                + "\n End time: " + document.get("end time") + "\n==========================\n"));

        Databases.STUDENT_SCHE_DATABASE().find()
                .forEach(document -> System.out
                        .println("Student Schedule" + "\nTitle: " + document.get("title") + "\n Start time: "
                                + document.get("start time")
                                + "\n End time: " + document.get("end time") + "\n==========================\n"));
    }

    public static boolean ValidateSchedule(StudentSchedule ss) {
        MongoCursor<Document> st_cursor = Databases.STUDENT_SCHE_DATABASE().find().iterator();
        MongoCursor<Document> te_cursor = Databases.TEACHER_SCHE_DATABASE().find().iterator();

        LocalDateTime sdt = ss.getStarttime();
        LocalDateTime edt = ss.getEndtime();
        while (te_cursor.hasNext()) {
            Document document = te_cursor.next();
            LocalDateTime scheduledSdt = ((Date) document.get("start time")).toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            LocalDateTime scheduledEdt = ((Date) document.get("end time")).toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            // if (sdt.compareTo(scheduledSdt)>=0&&sdt.compareTo(scheduledEdt)>0 ||
            // edt.compareTo(scheduledSdt)
            // ) {

            // }
            if (sdt.equals(scheduledSdt) || sdt.equals(edt) ||
                    sdt.isAfter(scheduledSdt) && sdt.isBefore(scheduledEdt) ||
                    edt.isAfter(scheduledSdt) && edt.isBefore(scheduledEdt) ||
                    sdt.isBefore(scheduledSdt) && edt.isAfter(scheduledEdt)) {
                return false;
            }
        }
        while (st_cursor.hasNext()) {
            Document document = st_cursor.next();
            LocalDateTime scheduledSdt = ((Date) document.get("start time")).toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            LocalDateTime scheduledEdt = ((Date) document.get("end time")).toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            if (sdt.equals(scheduledSdt) ||
                    sdt.equals(edt) ||
                    edt.equals(scheduledEdt) ||
                    sdt.isAfter(scheduledSdt) && sdt.isBefore(scheduledEdt) ||
                    edt.isAfter(scheduledSdt) && edt.isBefore(scheduledEdt) ||
                    sdt.isBefore(scheduledSdt) && edt.isAfter(scheduledEdt)) {
                return false;
            }
        }
        return true;
    }

    public static void DisplaySchedulewithID() {
        System.out.println("List of Schedule:");
        AtomicInteger s_index = new AtomicInteger(1);
        AtomicInteger t_index = new AtomicInteger(1);

        Databases.STUDENT_SCHE_DATABASE().find().forEach(document -> System.out.println(s_index.getAndIncrement()
                + "  Id : " + document.get("_id")
                + "\nTitle: " + document.get("title")
                + "\nStart time: " + document.get("start time")
                + "\nEnd time: " + document.get("end time")
                + "\n==========================\n"));

        Databases.TEACHER_SCHE_DATABASE().find().forEach(document -> System.out.println(t_index.getAndIncrement()
                + "  Id : " + document.get("_id")
                + "\nTitle: " + document.get("title")
                + "\nStart time: " + document.get("start time")
                + "\nEnd time: " + document.get("end time")
                + "\n==========================\n"));
    }
}

class StudentSchedule {
    private int id;
    public int studentId;
    public String schedulename;
    public LocalDateTime starttime;
    public LocalDateTime endtime;
    public String type;

    public StudentSchedule(int studentId, String name, LocalDateTime sttime, LocalDateTime edtime, String type) {
        this.studentId = studentId;
        this.schedulename = name;
        this.starttime = sttime;
        this.endtime = edtime;
        this.type = type;
    }

    public int getUserid() {
        return studentId;
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

class Message {
    private String id;
    private String title;
    private String content;
    private LocalDateTime stamp;
    private String sender;
    private String reciever;

    public Message(String id, String title, String content, LocalDateTime stamp, String sender, String reciever) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.stamp = stamp;
        this.sender = sender;
        this.reciever = reciever;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getStamp() {
        return stamp;
    }

    public void setStamp(LocalDateTime stamp) {
        this.stamp = stamp;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }
}

class MessageActions {
    public static void WriteMessage(Message m) {
        Document userDocument = new Document("message id", m.getId())
                .append("title", m.getTitle())
                .append("content", m.getContent())
                .append("time", m.getStamp())
                .append("sender", m.getSender())
                .append("receiver", m.getReciever());
        Databases.MESSAGE_DATABASE().insertOne(userDocument);
        System.out.println("Message Sent successfully!\n");
    }

    public static void UpdateMessage(Message m, Bson updateFilter) {
        Document updateDoc = new Document("message id", m.getId())
                .append("title", m.getTitle())
                .append("content", m.getContent())
                .append("time", m.getStamp())
                .append("sender", m.getSender())
                .append("receiver", m.getReciever());

        Databases.STUDENT_SCHE_DATABASE().insertOne(updateDoc);
        System.out.println("Message updated successfully!\n");
        Bson updateop = new Document("$set", updateDoc);
        Databases.STUDENT_SCHE_DATABASE().updateOne(updateFilter, updateop);
    }

    public static void DisplayMessage() {
        System.out.println("Messages:");
        Databases.MESSAGE_DATABASE().find()
                .forEach(document -> System.out
                        .println("Message" + "\nTitle: " + document.get("title") + "\n Content: "
                                + document.get("content")
                                + "\n Time: " + document.get("time")
                                + "\t Sender: " + document.get("sender")
                                + "\t Receiver: " + document.get("receiver")
                                + "\n==========================\n"));
    }
}

class Request {
    private String id;
    private String content;
    private LocalDateTime stamp;
    private String sender;
    private String reciever;

    public Request(String id, String content, LocalDateTime stamp, String sender) {
        this.id = id;
        this.content = content;
        this.stamp = stamp;
        this.sender = sender;
        this.reciever = "Admin";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getStamp() {
        return stamp;
    }

    public void setStamp(LocalDateTime stamp) {
        this.stamp = stamp;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}

class RequestActions {
    public static void WriteRequest(Request r) {
        Document userDocument = new Document("request id", r.getId())
                .append("content", r.getContent())
                .append("time", r.getStamp())
                .append("sender", r.getSender());
        Databases.REQUEST_DATABASE().insertOne(userDocument);
        System.out.println("Request sent successfully!\n");
    }

    public static void DisplayRequest() {
        System.out.println("Requests:");
        Databases.REQUEST_DATABASE().find()
                .forEach(document -> System.out
                        .println("Request" + "\n Content: " + document.get("content")
                                + "\n Time: " + document.get("time")
                                + "\t Sender: " + document.get("sender")
                                + "\n==========================\n"));
    }
}

class FeedbackDatabase {
    private MongoCollection<Document> feedbackCollection;

    public FeedbackDatabase() {
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

    public void addFeedback(Feedback feedback) {
        Document feedbackDocument = new Document("Stu_ID", feedback.getStuId())
                .append("Teacher_ID", feedback.getTeacherId())
                .append("Feedback_Text", feedback.getFeedbackText())
                .append("Reply", feedback.getReply());

        feedbackCollection.insertOne(feedbackDocument);
        System.out.println("Feedback added successfully!");
    }

    public void displayFeedback() {
        System.out.println("List of Feedback:");
        feedbackCollection.find().forEach(
                (Consumer<? super Document>) document -> System.out.println("Student ID: " + document.get("Stu_ID") +
                        ", Teacher ID: " + document.get("Teacher_ID") +
                        ", Feedback Text: " + document.get("Feedback_Text") +
                        ", Reply: " + document.get("Reply")));
    }

    public void updateFeedback(String studentId, String teacherId, String newReply) {
        Document filter = new Document("Stu_ID", studentId).append("Teacher_ID", teacherId);
        Document update = new Document("$set", new Document("Reply", newReply));

        feedbackCollection.updateOne(filter, update);
        System.out.println("Feedback updated successfully!");
    }

    public void deleteFeedback(String studentId, String teacherId) {
        Document filter = new Document("Stu_ID", studentId).append("Teacher_ID", teacherId);
        feedbackCollection.deleteOne(filter);
        System.out.println("Feedback deletion is successful");
    }
}

class Feedback {
    private String stuId;
    private String teacherId;
    private String feedbackText;
    private String reply;

    public Feedback(String stuId, String teacherId, String feedbackText, String reply) {
        this.stuId = stuId;
        this.teacherId = teacherId;
        this.feedbackText = feedbackText;
        this.reply = reply;
    }

    public String getStuId() {
        return stuId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public String getReply() {
        return reply;
    }
}

class ModuleFeedback {
    private String stuId;
    private String moduleName;
    private String feedbackText;
    private String rating;

    public ModuleFeedback(String stuId, String moduleName, String feedbackText, String rating) {
        this.stuId = stuId;
        this.moduleName = moduleName;
        this.feedbackText = feedbackText;
        this.rating = rating;
    }
    
    public String getStuId() {
        return stuId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getFeedbackText() {
        return feedbackText;
    }
    
    public String getRating() {
        return rating;
    }
}

class ModuleFeedbackDatabase {
    private MongoCollection<Document> moduleFeedbackCollection;
    private boolean exitRequested = false;

    public ModuleFeedbackDatabase() {
        String connectionString = "mongodb+srv://pfy1:uol123@timetablemanagement.uq12hfp.mongodb.net/?retryWrites=true&w=majority";
        String databaseName = "ModuleFeedbackDB";
        String collectionName = "ModuleFeedback";

        try {
            ConnectionString connString = new ConnectionString(connectionString);
            MongoClient mongoClient = MongoClients.create(connectionString);
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            moduleFeedbackCollection = database.getCollection(collectionName);
            System.out.println("Successfully connected to Module Feedback Database");
        } catch (Exception e) {
            System.err.println("Error connecting: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void addModuleFeedback(ModuleFeedback moduleFeedback) {
        Document feedbackDocument = new Document("Stu_ID", moduleFeedback.getStuId())
                .append("Module_Name", moduleFeedback.getModuleName())
                .append("Feedback_Text", moduleFeedback.getFeedbackText())
                .append("Rating", moduleFeedback.getRating());

        moduleFeedbackCollection.insertOne(feedbackDocument);
        System.out.println("Module Feedback added successfully!");
    }
    public void displayModuleFeedback() {
        System.out.println("List of Module Feedback:");
        moduleFeedbackCollection.find().forEach((Consumer<? super Document>) document ->
                System.out.println("Student ID: " + document.get("Stu_ID") +
                        ", Module Name: " + document.get("Module_Name") +
                        ", Feedback Text: " + document.get("Feedback_Text") +
                        ", Rating: " + document.get("Rating")));
    }
    public void clearModuleFeedbackCollection() {
        moduleFeedbackCollection.deleteMany(new Document());
    }
    public void shutdown() {
    	System.out.println("Exiting the program.");
    	exitRequested = true;
    }
}

public class studentOperation {

    public static void main(String[] args) {
        FeedbackDatabase feedbackDatabase = new FeedbackDatabase();
        ModuleFeedbackDatabase moduleFeedbackDatabase = new ModuleFeedbackDatabase();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nWelcome to Student System");
            System.out.println("==========================");
            System.out.println("Choose the menu below");
            System.out.println("1. View Announcement");
            System.out.println("2. Student Schedule Management");
            System.out.println("3. Message System");
            System.out.println("4. Request System");
            System.out.println("5. Feedback System");
            System.out.println("6. Quit");
            System.out.print("Input : ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:

                    Databases.ANNOUNCEMENT_DATABASE().find()
                            .forEach(document -> System.out
                                    .println("Announcement:\n" + document.get("announcement")
                                            + "\n==========================\n"));
                    break;
                case 2:
                    StudentManagementSystem: while (true) {
                        while (true) {
                            System.out.println("\nStudent Schedule Management");
                            System.out.println("==========================");
                            System.out.println("1. Display schedule");
                            System.out.println("2. Add new Student Schedule");
                            System.out.println("3. Update schedule");
                            System.out.println("4. Delete schedule");
                            System.out.println("5. Back to main menu");
                            System.out.println("6. Quit");
                            System.out.print("Input : ");
                            int stScheChoice = scanner.nextInt();
                            scanner.nextLine();
                            switch (stScheChoice) {
                                case 1:
                                    StudentScheduleActions.DisplaySchedule();
                                    break;
                                case 2:
                                    System.out.println("Enter Schedule title: ");
                                    String stitle = scanner.nextLine();
                                    System.out.println("==Input Starting time==");
                                    System.out.print("Enter Date in format yyyy-mm-dd :");
                                    String stdate = scanner.nextLine();
                                    System.out.print("Enter Starting time in format HH:mm :");
                                    String sttime = scanner.nextLine();
                                    LocalDateTime sdt = LocalDateTime.parse(stdate + "T" + sttime);
                                    System.out.println("Your startting time : " + sdt);
                                    System.out.println("==Input Ending time==");
                                    System.out.print("Enter Date in format yyyy-mm-dd :");
                                    String eddate = scanner.nextLine();
                                    System.out.print("Enter Ending time in format HH:mm :");
                                    String edtime = scanner.nextLine();
                                    LocalDateTime edt = LocalDateTime.parse(eddate + "T" + edtime);
                                    System.out.println("Your Ending time : " + edt);
                                    StudentSchedule sSche = new StudentSchedule(1, stitle, sdt, edt, "Student");
                                    StudentScheduleActions.AddStudentSchedule(sSche);
                                    break;
                                case 3:
                                    System.out.println("Updating exist schedule");
                                    System.out.print("Enter Student Schedule title you want to change: ");
                                    String target = scanner.nextLine();
                                    Bson Updatefilter = Filters.eq("title", target);

                                    System.out.println("New Title : ");
                                    stitle = scanner.nextLine();
                                    System.out.println("==Input New Starting time==");
                                    System.out.print("Enter Date in format yyyy-mm-dd :");
                                    stdate = scanner.nextLine();
                                    System.out.print("Enter Starting time in format HH:mm :");
                                    sttime = scanner.nextLine();
                                    sdt = LocalDateTime.parse(stdate + "T" + sttime);
                                    System.out.println("==Input New Ending time==");
                                    System.out.print("Enter Date in format yyyy-mm-dd :");
                                    eddate = scanner.nextLine();
                                    System.out.print("Enter Ending time in format HH:mm :");
                                    edtime = scanner.nextLine();
                                    edt = LocalDateTime.parse(eddate + "T" + edtime);
                                    StudentSchedule Updatepsche = new StudentSchedule(1, stitle, sdt, edt, "Student");
                                    StudentScheduleActions.UpdateStudentSchedule(Updatepsche, Updatefilter);
                                    break;
                                case 4:
                                    StudentScheduleActions.DisplaySchedulewithID();
                                    System.out.println("Enter the title of which item you want to delete : ");
                                    String title = scanner.nextLine();
                                    Bson filter = Filters.eq("title", title);
                                    Databases.STUDENT_SCHE_DATABASE().deleteOne(filter);
                                    System.out.println("Deletion successfully");
                                    break;
                                case 5:
                                    break StudentManagementSystem;
                                case 6:
                                    System.exit(0);
                                default:
                                    break;
                            }

                        }
                    }
                    break;

                case 3:
                    MsgSystem: while (true) {
                        while (true) {
                            System.out.println("\nMessage System");
                            System.out.println("==========================");
                            System.out.println("1. Display Messages");
                            System.out.println("2. Send a Message");
                            System.out.println("3. Delete a Message");
                            System.out.println("4. Back to main menu");
                            System.out.println("5. Quit");
                            System.out.print("Input : ");
                            int msgChoice = scanner.nextInt();
                            scanner.nextLine();
                            switch (msgChoice) {
                                case 1:
                                    MessageActions.DisplayMessage();
                                    break;
                                case 2:
                                    System.out.println("Enter the name of receiver: ");
                                    String receiver = scanner.nextLine();
                                    System.out.print("Message Title: ");
                                    String title = scanner.nextLine();
                                    System.out.println("Message Content: ");
                                    String content = scanner.nextLine();
                                    System.out.println("Are you sure to send this message? (y/n)");
                                    String sendMsgChoice = scanner.nextLine().toLowerCase();
                                    switch (sendMsgChoice) {
                                        case "y":
                                            LocalDateTime stamp = LocalDateTime.now();
                                            Message m = new Message("1", title, content, stamp, "Student 1", receiver);
                                            MessageActions.WriteMessage(m);
                                            break;
                                        case "n":
                                            break;
                                        default:
                                            break;
                                    }
                                    break;
                                case 3:
                                    MessageActions.DisplayMessage();
                                    System.out.println("Enter the title of which message you want to delete : ");
                                    String msgTitle = scanner.nextLine();
                                    Bson filter = Filters.eq("title", msgTitle);
                                    Databases.STUDENT_SCHE_DATABASE().deleteOne(filter);
                                    System.out.println("Message delete successfully");
                                    break;
                                case 4:
                                    break MsgSystem;
                                case 5:
                                    System.exit(0);
                                default:
                                    break;
                            }
                            break;
                        }
                    }
                    break;
                case 4:
                    RequestSystem: while (true) {
                        while (true) {
                            System.out.println("\nRequest System");
                            System.out.println("==========================");
                            System.out.println("1. Display requests sent");
                            System.out.println("2. Send a request to Admin");
                            System.out.println("3. Back to main menu");
                            System.out.println("4. Quit");
                            System.out.print("Input : ");

                            int requestChoice = scanner.nextInt();
                            scanner.nextLine();
                            switch (requestChoice) {
                                case 1:
                                    RequestActions.DisplayRequest();
                                    break;
                                case 2:
                                    System.out.println("Request to the admin: ");
                                    String content = scanner.nextLine();
                                    System.out.println("Are you sure to send this request? (y/n)");
                                    String sendMsgChoice = scanner.nextLine().toLowerCase();
                                    switch (sendMsgChoice) {
                                        case "y":
                                            LocalDateTime stamp = LocalDateTime.now();
                                            Request m = new Request("1", content, stamp, "Student 1");
                                            RequestActions.WriteRequest(m);
                                            break;
                                        case "n":
                                            break;
                                        default:
                                            break;
                                    }
                                    break;
                                case 3:
                                    break RequestSystem;
                                case 4:
                                    System.exit(0);
                                default:
                                    break;
                            }
                            break;
                        }
                    }
                    break;
                case 5:
                    FeedbackSystem: while (true) {
                        while (true) {
                            System.out.println("\nFeedback System");
                            System.out.println("==========================");
                            System.out.println("1. Add Feedback");
                            System.out.println("2. Display Feedback");
                            System.out.println("3. Update Feedback");
                            System.out.println("4. Delete Feedback");
                            System.out.println("5. Add Module Feedback");
                            System.out.println("6. Display Module Feedback");
                            System.out.println("7. Quit");
                            System.out.print("Input : ");

                            int requestChoice = scanner.nextInt();
                            scanner.nextLine();
                            switch (requestChoice) {
                                case 1:
                                    System.out.print("Enter Student ID: ");
                                    String studentId = scanner.nextLine();
                                    System.out.print("Enter Teacher ID: ");
                                    String teacherId = scanner.nextLine();
                                    System.out.print("Enter Feedback Text: ");
                                    String feedbackText = scanner.nextLine();
                                    System.out.print("Enter Reply: ");
                                    String reply = scanner.nextLine();
                                    Feedback feedbackToAdd = new Feedback(studentId, teacherId, feedbackText, reply);
                                    feedbackDatabase.addFeedback(feedbackToAdd);
                                    break;
                                case 2:
                                    feedbackDatabase.displayFeedback();
                                    break;
                                case 3:
                                    System.out.print("Enter Student ID: ");
                                    String updateStudentId = scanner.nextLine();
                                    System.out.print("Enter Teacher ID: ");
                                    String updateTeacherId = scanner.nextLine();
                                    System.out.print("Enter New Reply: ");
                                    String newReply = scanner.nextLine();

                                    feedbackDatabase.updateFeedback(updateStudentId, updateTeacherId, newReply);
                                    break;

                                case 4:
                                    System.out.print("Enter Student ID: ");
                                    String deleteStudentId = scanner.nextLine();
                                    System.out.print("Enter Teacher ID: ");
                                    String deleteTeacherId = scanner.nextLine();

                                    feedbackDatabase.deleteFeedback(deleteStudentId, deleteTeacherId);
                                    break;
                                case 5:
                                    System.out.print("Enter Student ID: ");
                                    studentId = scanner.nextLine();
                                    System.out.print("Enter Module Name: ");
                                    String moduleName = scanner.nextLine();
                                    System.out.print("Enter Feedback Text: ");
                                    feedbackText = scanner.nextLine();
                                    System.out.print("Enter Rating: ");
                                    String rating = scanner.nextLine();

                                    ModuleFeedback moduleFeedbackToAdd = new ModuleFeedback(studentId, moduleName,
                                            feedbackText, rating);
                                    moduleFeedbackDatabase.addModuleFeedback(moduleFeedbackToAdd);
                                    break;
                                case 6:
                                    moduleFeedbackDatabase.displayModuleFeedback();
                                    break;
                                case 7:
                                    break FeedbackSystem;
                                default:
                                    break;
                            }
                            break;
                        }
                    }
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }
}