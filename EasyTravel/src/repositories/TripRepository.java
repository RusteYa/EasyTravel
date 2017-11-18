package repositories;

import entities.Trip;
import entities.User;
import helpers.DbHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rustem.
 */
public class TripRepository {
    private static TripRepository repository;
    private Connection connection;

    private TripRepository() {
        this.connection = DbHelper.getConnection();
    }

    public static TripRepository getRepository() {
        if (repository == null) {
            repository = new TripRepository();
        }
        return repository;
    }

    public int createTrip(Trip trip) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "INSERT INTO trip (name) VALUES (?)  RETURNING id");
            st.setString(1, trip.getName());
            st.executeUpdate();
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void updateVisitedTripsForUser(User user) {
        updateTripsForUser(user, user.getVisitedTrip(), true);
    }

    public void updateUnVisitedTripsForUser(User user) {
        updateTripsForUser(user, user.getUnVisitedTrip(), false);
    }

    public void updateTripsForUser(User user, List<Trip> trips, boolean visited) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "DELETE FROM user_trip WHERE user_id = ? AND visited = ?"
            );
            st.setInt(1, user.getId());
            st.setBoolean(2, true);
            st.executeQuery();
            for (Trip trip : trips) {
                st = connection.prepareStatement(
                        "INSERT INTO user_trip (user_id, trip_id, visited) VALUES (?, ?, ?)"
                );
                st.setInt(1, user.getId());
                st.setInt(2, trip.getId());
                st.setBoolean(3, visited);
                st.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Trip getTripById(int id) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT * FROM trip WHERE id = ?"
            );
            st.setInt(1, id);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                return new Trip(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Trip> getVisitedTripsForUser(int userId) {
        return getTripsForUser(userId, true);
    }

    public List<Trip> getUnVisitedTripsForUser(int userId) {
        return getTripsForUser(userId, false);
    }

    public List<Trip> getTripsForUser(int userId, boolean visited) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT * FROM user_trip WHERE user_id = ? AND visited = ?"
            );
            st.setInt(1, userId);
            st.setBoolean(2, visited);
            ResultSet resultSet = st.executeQuery();
            List<Trip> visitedTrips = new ArrayList<>();
            while (resultSet.next()) {
                int trip_id = resultSet.getInt("trip_id");
                Trip trip = getTripById(trip_id);
                visitedTrips.add(trip);
            }
            return visitedTrips;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
