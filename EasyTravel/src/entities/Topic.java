package entities;

import java.util.Date;

/**
 * Created by Rustem.
 */
public class Topic {
    private int id;
    private User topicStarter;
    private String header;
    private String content;
    private Date date;
    private int likes;
    private int dislikes;
    private String photoPath;

    public Topic(int id, User topicStarter, String content, Date date, String header, int likes, int dislikes, String photoPath) {
        this.id = id;
        this.topicStarter = topicStarter;
        this.content = content;
        this.header = header;
        this.date = date;
        this.likes = likes;
        this.dislikes = dislikes;
        this.photoPath = photoPath;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getTopicStarter() {
        return topicStarter;
    }

    public void setTopicStarter(User topicStarter) {
        this.topicStarter = topicStarter;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }
}
