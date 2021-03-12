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

    /** Setter
     *
     * @param userId - the int value whih the User's Id will be set to
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
    /** Setter
     *
     * @param username - the String value which the User's username will be set to
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /** Setter
     *
     * @param password - the String value which the User's password will be set to
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /** Getter
     *
     * @return - the int value corresponding to the User's Id
     */
    public int getUserId() {
        return userId;
    }
    /** Getter
     *
     * @return - the String value corresponding to the User's username
     */
    public String getUsername() {
        return username;
    }
    /** Getter
     *
     * @return - the String value corresponding to the User's password
     */
    public String getPassword() {
        return password;
    }

}
