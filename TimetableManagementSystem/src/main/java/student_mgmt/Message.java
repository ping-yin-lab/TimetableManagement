package student_mgmt;

import java.time.LocalDateTime;

public class Message {
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
