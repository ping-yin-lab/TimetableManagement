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
    private static String Schedule_databaseName = "Schedule";

    public static void config() {
        try {
            ConnectionString connString = new ConnectionString(
                    "mongodb+srv://pfy1:uol123@timetablemanagement.uq12hfp.mongodb.net/?retryWrites=true&w=majority");
            MongoClient mongoClient = MongoClients.create(connString);
            MongoDatabase database = mongoClient.getDatabase(Schedule_databaseName);
            TEACHER_SCHE_DATABASE = database.getCollection("TT_Sche");
            STUDENT_SCHE_DATABASE = database.getCollection("Student_Sche");
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

}
