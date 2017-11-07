package Repositories;

import Entities.User;
import Helpers.PasswordHasher;

import javax.servlet.http.Cookie;
import java.sql.*;
import java.util.Arrays;

/**
 * Created by Rustem.
 */
public class UsersRepository {
    private static UsersRepository repository;
    private Connection connection;

    private UsersRepository() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/EasyTravel",
                    "postgres",
                    "postgres"
            );
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static UsersRepository getRepository() {
        if (repository == null) {
            repository = new UsersRepository();
        }
        return repository;
    }

    public void createUser(User user) {
        byte[] salt = PasswordHasher.getSalt();
        String hashedPassword = PasswordHasher.getHashedPassword(user.getHashedPassword(), salt);
        user.setHashedPassword(hashedPassword);
        try {
            PreparedStatement st = connection.prepareStatement(
                    "INSERT INTO users (login, email, hashed_password, salt) VALUES (?, ?, ?, ?)");
            st.setString(1, user.getLogin());
            st.setString(2, user.getEmail());
            st.setString(3, user.getHashedPassword());
            st.setString(4, Arrays.toString(salt));
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean hasUser(String username, String password) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT * FROM users WHERE login = ?"
            );
            st.setString(1, username);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
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

    public boolean hasUser(Cookie loginCookie, Cookie tokenCookie) {
        try {
            String login = loginCookie.getValue();
            String token = tokenCookie.getValue();
            PreparedStatement st = connection.prepareStatement(
                    "SELECT * FROM users WHERE login = ?"
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

    public boolean hasUser(User user) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT * FROM users WHERE login = ?"
            );
            st.setString(1, user.getLogin());
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                String hashedPassword = resultSet.getString("hashed_password");
                String userHashedPassword = user.getHashedPassword();
                if (hashedPassword != null && userHashedPassword != null &&
                        hashedPassword.equals(userHashedPassword)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean hasUsername(String login) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT users.login FROM users WHERE login = ?"
            );
            st.setString(1, login);
            ResultSet resultSet = st.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getUser(String username) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT * FROM users WHERE login = ?"
            );
            st.setString(1, username);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("hashed_password");
                String email = resultSet.getString("email");
                return new User(username, email, hashedPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}