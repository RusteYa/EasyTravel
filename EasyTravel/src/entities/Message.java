package entities;

import java.util.Date;

/**
 * Created by Rustem.
 */
public class Message {
    private int id;
    private User author;
    private Date date;
    private String header;
    private String content;
    private Topic topic;

    public Message(int id, User author, Date date, String header, String content, Topic topic) {
        this.id = id;
        this.author = author;
        this.date = date;
        this.header = header;
        this.content = content;
        this.topic = topic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
