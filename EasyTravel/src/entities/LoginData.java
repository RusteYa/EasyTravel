package entities;

/**
 * Created by Rustem.
 */
public class LoginData {
    private int id;
    private String login;
    private String email;
    private String hashedPassword;
    private String token;

    public LoginData(String login, String email, String hashedPassword, String token) {
        this.login = login;
        this.hashedPassword = hashedPassword;
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

