package student_mgmt;

import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Databases {
    private static MongoCollection<Document> TEACHER_SCHE_DATABASE;
    private static MongoCollection<Document> STUDENT_SCHE_DATABASE;
    private static MongoCollection<Document> MESSAGE_DATABASE;
    private static MongoCollection<Document> REQUEST_DATABASE;
    private static String Schedule_databaseName = "Schedule";
    private static String Message_databaseName = "Message";
    private static String Request_databaseName = "Request";

    public static void config() {
        try {
            ConnectionString connString = new ConnectionString(
                    "mongodb+srv://pfy1:uol123@timetablemanagement.uq12hfp.mongodb.net/?retryWrites=true&w=majority");
            MongoClient mongoClient = MongoClients.create(connString);
            MongoDatabase Schedule_database = mongoClient.getDatabase(Schedule_databaseName);
            MongoDatabase Message_database = mongoClient.getDatabase(Message_databaseName);
            MongoDatabase Request_database = mongoClient.getDatabase(Request_databaseName);
            TEACHER_SCHE_DATABASE = Schedule_database.getCollection("TT_Sche");
            STUDENT_SCHE_DATABASE = Schedule_database.getCollection("Student_Sche");
            MESSAGE_DATABASE = Message_database.getCollection("TT_Message");
            REQUEST_DATABASE = Request_database.getCollection("Student_Request");
            // System.out.println("Connected Successfully");
        } catch (Exception e) {
            System.err.println("Error connecting: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static MongoCollection<Document> TEACHER_SCHE_DATABASE() {
        config();
        return TEACHER_SCHE_DATABASE;
    }

    public static MongoCollection<Document> STUDENT_SCHE_DATABASE() {
        config();
        return STUDENT_SCHE_DATABASE;
    }

    public static MongoCollection<Document> MESSAGE_DATABASE() {
        config();
        return MESSAGE_DATABASE;
    }

    public static MongoCollection<Document> REQUEST_DATABASE() {
        config();
        return REQUEST_DATABASE;
    }
}
