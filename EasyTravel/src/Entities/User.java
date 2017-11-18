package entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rustem.
 */
public class User {
    private int id;
    private Profile profile;
    private LoginData loginData;
    private List<Trip> visitedTrip;
    private List<Trip> unvisitedTrip;

    public User(int id, Profile profile, LoginData loginData, List<Trip> visitedTrip, List<Trip> unvisitedTrip) {
        this.id = id;
        this.profile = profile;
        this.loginData = loginData;
        this.visitedTrip = visitedTrip;
        this.unvisitedTrip = unvisitedTrip;
    }

    public User(Profile profile, LoginData loginData) {
        this.profile = profile;
        this.loginData = loginData;
        this.visitedTrip = new ArrayList<>();
        this.unvisitedTrip = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public LoginData getLoginData() {
        return loginData;
    }

    public void setLoginData(LoginData loginData) {
        this.loginData = loginData;
    }

    public List<Trip> getVisitedTrip() {
        return visitedTrip;
    }

    public void setVisitedTrip(List<Trip> visitedTrip) {
        this.visitedTrip = visitedTrip;
    }

    public List<Trip> getUnVisitedTrip() {
        return unvisitedTrip;
    }

    public void setUnvisitedTrip(List<Trip> unvisitedTrip) {
        this.unvisitedTrip = unvisitedTrip;
    }


}
