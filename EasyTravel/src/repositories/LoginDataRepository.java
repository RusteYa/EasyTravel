package repositories;

import entities.LoginData;
import helpers.DbHelper;
import helpers.PasswordHasher;

import javax.servlet.http.Cookie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Rustem.
 */
public class LoginDataRepository {
    private static LoginDataRepository repository;
    private Connection connection;

    private LoginDataRepository() {
        this.connection = DbHelper.getConnection();
    }

    public static LoginDataRepository getRepository() {
        if (repository == null) {
            repository = new LoginDataRepository();
        }
        return repository;
    }

    public int createLoginData(LoginData loginData) {
        byte[] salt = PasswordHasher.getSalt();
        String hashedPassword = PasswordHasher.getHashedPassword(loginData.getHashedPassword(), salt);
        loginData.setHashedPassword(hashedPassword);
        try {
            PreparedStatement st = connection.prepareStatement(
                    "INSERT INTO login_data (login, email, hashed_password, salt, token) VALUES (?, ?, ?, ?, ?)  RETURNING id");
            st.setString(1, loginData.getLogin());
            st.setString(2, loginData.getEmail());
            st.setString(3, loginData.getHashedPassword());
            System.out.println(new String(salt));
            st.setString(4, new String(salt));
            st.setString(5, loginData.getToken());
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                System.out.println("Создано login_data с id " + id);
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void updateLoginData(LoginData loginData) {
        try {
            System.out.println(loginData.getEmail());
            System.out.println(loginData.getId());
            PreparedStatement st = connection.prepareStatement(
                    "UPDATE login_data SET login = ?, hashed_password = ?, " +
                            "email = ? WHERE id = ?");
            st.setString(1, loginData.getLogin());
            st.setString(2, loginData.getHashedPassword());
            st.setString(3, loginData.getEmail());
            st.setInt(4, loginData.getId());
            st.executeUpdate();
            st = connection.prepareStatement(
                    "SELECT email FROM login_data WHERE id = ?");
            st.setInt(1, loginData.getId());
            ResultSet rs = st.executeQuery();
            rs.next();
            System.out.println(rs.getString("email"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean hasLoginData(String login, String password) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT * FROM login_data WHERE login = ?"
            );
            st.setString(1, login);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                byte[] salt = resultSet.getString("salt").getBytes();
                String hashedPassword = resultSet.getString("hashed_password");
                if (hashedPassword != null && hashedPassword.equals(
                        PasswordHasher.getHashedPassword(password, salt))) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean hasLoginData(Cookie loginCookie, Cookie tokenCookie) {
        try {
            String login = loginCookie.getValue();
            String token = tokenCookie.getValue();
            PreparedStatement st = connection.prepareStatement(
                    "SELECT * FROM login_data WHERE login = ?"
            );
            st.setString(1, login);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                String dbToken = resultSet.getString("token");
                if (token != null && token.equals(dbToken)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean hasLogin(String login) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT login FROM login_data WHERE login = ?"
            );
            st.setString(1, login);
            ResultSet resultSet = st.executeQuery();
            boolean b = resultSet.next();
            System.out.println("Логин имеется в базе данных " + b);
            return b;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public LoginData getLoginDataById(int id) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT * FROM login_data WHERE id = ?"
            );
            st.setInt(1, id);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                String login = resultSet.getString("login");
                String hashedPassword = resultSet.getString("hashed_password");
                String email = resultSet.getString("email");
                String token = resultSet.getString("token");
                return new LoginData(login, email, hashedPassword, token);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateToken(String token, String login) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "UPDATE login_data SET token = ? WHERE login = ?"
            );
            st.setString(1, token);
            st.setString(2, login);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
