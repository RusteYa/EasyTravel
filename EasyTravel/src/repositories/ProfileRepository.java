package repositories;

import entities.Profile;
import helpers.DbHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Rustem.
 */
public class ProfileRepository {
    private static ProfileRepository repository;
    private Connection connection;

    private ProfileRepository() {
        this.connection = DbHelper.getConnection();
    }

    public static ProfileRepository getRepository() {
        if (repository == null) {
            repository = new ProfileRepository();
        }
        return repository;
    }

    public int createProfile(Profile profile) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "INSERT INTO profile (name, surname, fathers_name, photo_path, user_data_file) VALUES (?, ?, ?, ?, ?)  RETURNING id");
            st.setString(1, profile.getName());
            st.setString(2, profile.getSurname());
            st.setString(3, profile.getFathersName());
            st.setString(4, profile.getPhotoPath());
            st.setString(5, profile.getUserDataFile());
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                System.out.println("Создан profile с id " + id);
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void updateProfile(Profile profile) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "UPDATE profile SET name = ?, surname = ?, " +
                            "fathers_name = ?, photo_path = ?, user_data_file = ? WHERE id = ?");
            st.setString(1, profile.getName());
            st.setString(2, profile.getSurname());
            st.setString(3, profile.getFathersName());
            st.setString(4, profile.getPhotoPath());
            st.setString(5, profile.getUserDataFile());
            st.setInt(6, profile.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Profile getProfileById(int id) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT * FROM profile WHERE id = ?"
            );
            st.setInt(1, id);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String fathersName = resultSet.getString("fathers_name");
                String photoPath = resultSet.getString("photo_path");
                String userDataFile = resultSet.getString("user_data_file");
                return new Profile(id, name, surname, fathersName, userDataFile, photoPath);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
