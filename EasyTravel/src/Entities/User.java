package Entities;

/**
 * Created by Rustem.
 */
public class User {
    private int id;
    private String login;
    private String email;
    private String hashedPassword;

    public User(String login, String email, String hashedPassword) {
        this.login = login;
        this.hashedPassword = hashedPassword;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

