package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class User {

    private int userId;
    private String username;
    private String password;
    private int status;
    // container for all users
    public static ObservableList<User> allUsers = FXCollections.observableArrayList();

    public User() { }

    public User(int userID, String username, String password, int status) {
        this.userId = userID;
        this.username = username;
        this.password = password;
        this.status = status;
    }

    // Setters
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setStatus(int status) { this.status = status; }
    public void addUser(User user) { allUsers.add(user); }

    // Getters
    public int getUserId() {
        return userId;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public int getStatus() {
        return status;
    }
    public ObservableList<User> getAllUsers() { return allUsers; }

}
