package repositories;

import entities.LoginData;
import entities.Profile;
import entities.Trip;
import entities.User;
import helpers.DbHelper;

import javax.servlet.http.Cookie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Rustem.
 */
public class UsersRepository {
    private static UsersRepository repository;
    private Connection connection;

    private UsersRepository() {
        this.connection = DbHelper.getConnection();
    }

    public static UsersRepository getRepository() {
        if (repository == null) {
            repository = new UsersRepository();
        }
        return repository;
    }

    public int createUser(User user) {
        int loginDataId = LoginDataRepository.getRepository().createLoginData(user.getLoginData());
        int profileId = ProfileRepository.getRepository().createProfile(user.getProfile());
        if (loginDataId != -1 && profileId != -1) {
            try {
                PreparedStatement st = connection.prepareStatement(
                        "INSERT INTO users (login_data_id, profile_id) VALUES (?, ?) RETURNING id");
                st.setInt(1, profileId);
                st.setInt(2, loginDataId);
                st.executeUpdate();
                ResultSet resultSet = st.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public boolean hasUser(String login, String password) {
        return LoginDataRepository.getRepository().hasLoginData(login, password);
    }

    public boolean hasUser(Cookie loginCookie, Cookie tokenCookie) {
        return LoginDataRepository.getRepository().hasLoginData(loginCookie, tokenCookie);
    }

    public boolean hasUser(User user) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT * FROM users JOIN login_data ON users.login_data_id = login_data.id " +
                            "WHERE users.id = ? AND login_data.login = ?"
            );
            st.setInt(1, user.getId());
            st.setString(2, user.getLoginData().getLogin());
            ResultSet resultSet = st.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean hasLogin(String login) {
        return LoginDataRepository.getRepository().hasLogin(login);
    }

    public User getUserByLogin(String login) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT * FROM users " +
                            "JOIN login_data ON users.login_data_id = login_data.id " +
                            "WHERE login_data.login = ?"
            );
            st.setString(1, login);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("users.id");
                int loginDataId = resultSet.getInt("users.login_data_id");
                int profileId = resultSet.getInt("users.profile_id");
                LoginData loginData = LoginDataRepository.getRepository().getLoginDataById(loginDataId);
                Profile profile = ProfileRepository.getRepository().getProfileById(profileId);
                List<Trip> visitedTrips = TripRepository.getRepository().getVisitedTripsForUser(id);
                List<Trip> unVisitedTrips = TripRepository.getRepository().getUnVisitedTripsForUser(id);
                return new User(id, profile, loginData, visitedTrips, unVisitedTrips);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserById(int userId) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT * FROM users WHERE id = ?"
            );
            st.setInt(1, userId);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                int loginDataId = resultSet.getInt("login_data_id");
                int profileId = resultSet.getInt("profile_id");
                LoginData loginData = LoginDataRepository.getRepository().getLoginDataById(loginDataId);
                Profile profile = ProfileRepository.getRepository().getProfileById(profileId);
                List<Trip> visitedTrips = TripRepository.getRepository().getVisitedTripsForUser(userId);
                List<Trip> unVisitedTrips = TripRepository.getRepository().getUnVisitedTripsForUser(userId);
                return new User(userId, profile, loginData, visitedTrips, unVisitedTrips);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateUser(User user) {
        LoginDataRepository.getRepository().updateLoginData(user.getLoginData());
        ProfileRepository.getRepository().updateProfile(user.getProfile());
        TripRepository.getRepository().updateVisitedTripsForUser(user);
        TripRepository.getRepository().updateUnVisitedTripsForUser(user);
    }
}