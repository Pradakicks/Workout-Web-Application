package Model;

public class User {
    private int userId;
    private String username;
    private String name;
    private String passwordHash;
    private String email;
    private String profilePicture;
    private String role;

    public User(int userId, String username, String name, String passwordHash, String email, String profilePicture, String role) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.passwordHash = passwordHash;
        this.email = email;
        this.profilePicture = profilePicture;
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
