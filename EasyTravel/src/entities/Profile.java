package entities;

/**
 * Created by Rustem.
 */
public class Profile {
    private int id;
    private String name;
    private String surname;
    private String fathersName;
    private String userDataFile;
    private String photoPath;

    public Profile(int id, String name, String surname, String fathersName, String userDataFile, String photoPath) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.fathersName = fathersName;
        this.userDataFile = userDataFile;
        this.photoPath = photoPath;
    }

    public Profile() {
        this(0, "", "", "", "", "");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathers_name) {
        this.fathersName = fathersName;
    }

    public String getUserDataFile() {
        return userDataFile;
    }

    public void setUserDataFile(String userDataFile) {
        this.userDataFile = userDataFile;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
