package student_mgmt;

import java.time.LocalDateTime;

public class Request {
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
