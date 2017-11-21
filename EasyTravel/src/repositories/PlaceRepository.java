package repositories;

import entities.Place;
import entities.User;
import helpers.DbHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Rustem.
 */
public class PlaceRepository {
    private static PlaceRepository repository;
    private Connection connection;

    private PlaceRepository() {
        this.connection = DbHelper.getConnection();
    }

    public static PlaceRepository getRepository() {
        if (repository == null) {
            repository = new PlaceRepository();
        }
        return repository;
    }

    public int createPlace(Place place) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "INSERT INTO place (author_id, photo_path, header, content, likes, dislikes, date) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)  RETURNING id");
            st.setInt(1, place.getAuthor().getId());
            st.setString(2, place.getPhotoPath());
            st.setString(3, place.getHeader());
            st.setString(4, place.getContent());
            st.setInt(5, place.getLikes());
            st.setInt(6, place.getDislikes());
            st.setTimestamp(7, Timestamp.from(place.getDate().toInstant()));
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Place> getPlacesForUser(User user) {
        try {
            int userId = user.getId();
            PreparedStatement st = connection.prepareStatement(
                    "SELECT * FROM place WHERE author_id = ?"
            );
            st.setInt(1, userId);
            ResultSet resultSet = st.executeQuery();
            List<Place> places = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String photoPath = resultSet.getString("photo_path");
                String header = resultSet.getString("header");
                String content = resultSet.getString("content");
                int likes = resultSet.getInt("likes");
                int dislikes = resultSet.getInt("dislikes");
                Date date = resultSet.getTimestamp("date");
                Place place = new Place(id, user, photoPath, header, content, likes, dislikes, date);
                places.add(place);
            }
            return places;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Place getPlaceById(int id) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT * FROM place WHERE id = ?"
            );
            st.setInt(1, id);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                int author_id = resultSet.getInt("author_id");
                String photoPath = resultSet.getString("photo_path");
                String header = resultSet.getString("header");
                String content = resultSet.getString("content");
                int likes = resultSet.getInt("likes");
                int dislikes = resultSet.getInt("dislikes");
                Date date = resultSet.getTimestamp("date");
                User user = UsersRepository.getRepository().getUserById(author_id);
                return new Place(id, user, photoPath, header, content, likes, dislikes, date);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Place> getPlace() {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT * FROM place"
            );
            ResultSet resultSet = st.executeQuery();
            List<Place> places = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int author_id = resultSet.getInt("author_id");
                String photoPath = resultSet.getString("photo_path");
                String header = resultSet.getString("header");
                String content = resultSet.getString("content");
                int likes = resultSet.getInt("likes");
                int dislikes = resultSet.getInt("dislikes");
                Date date = resultSet.getTimestamp("date");
                User user = UsersRepository.getRepository().getUserById(author_id);
                Place place = new Place(id, user, photoPath, header, content, likes, dislikes, date);
                places.add(place);
            }
            return places;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
