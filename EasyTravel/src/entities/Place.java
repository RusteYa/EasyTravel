package entities;

import java.util.Date;

/**
 * Created by Rustem.
 */
public class Place {
    private int id;
    private User author;
    private String photoPath;
    private String header;
    private String content;
    private int likes;
    private int dislikes;
    private Date date;

    public Place(int id, User author, String photoPath, String header, String content, int likes, int dislikes, Date date) {
        this.id = id;
        this.author = author;
        this.photoPath = photoPath;
        this.header = header;
        this.content = content;
        this.likes = likes;
        this.dislikes = dislikes;
        this.date = date;
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

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
