package entities;

import java.util.Date;

/**
 * Created by Rustem.
 */
public class Post {
    private int id;
    private User author;
    private String photo_path;
    private String header;
    private String content;
    private int likes;
    private int dislikes;
    private Date date;

    public Post(int id, User author, String photo_path, String header, String content, int likes, int dislikes, Date date) {
        this.id = id;
        this.author = author;
        this.photo_path = photo_path;
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

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
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
