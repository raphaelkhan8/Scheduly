package Model;

public class User {

    private int userId;
    private String username;
    private String password;
    private int status;

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
    public void setStatus(int status) {
        // 0 = inactive; 1 = active
        this.status = status;
    }

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

}
