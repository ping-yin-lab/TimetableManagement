package student_mgmt;

import org.bson.Document;
import org.bson.conversions.Bson;

public class MessageActions {
    public static void WriteMessage(Message m) {
        Document userDocument = new Document("message id", m.getId())
                .append("title", m.getTitle())
                .append("content", m.getContent())
                .append("time", m.getStamp())
                .append("sender", m.getSender())
                .append("receiver", m.getReciever());
        Databases.MESSAGE_DATABASE().insertOne(userDocument);
    }

    public static void UpdateMessage(Message m, Bson updateFilter) {
        Document updateDoc = new Document("message id", m.getId())
                .append("title", m.getTitle())
                .append("content", m.getContent())
                .append("time", m.getStamp())
                .append("sender", m.getSender())
                .append("receiver", m.getReciever());

        Databases.STUDENT_SCHE_DATABASE().insertOne(updateDoc);
        System.out.println("Message updated successfully!");
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
