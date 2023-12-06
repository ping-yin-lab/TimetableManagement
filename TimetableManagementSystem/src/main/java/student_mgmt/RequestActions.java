package student_mgmt;

import org.bson.Document;

import Database.Databases;

public class RequestActions {
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
