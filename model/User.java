import java.sql.Timestamp;

public class User {
    private int userId;
    private String name;
    private String email;
    private Timestamp createdAt;

    // Default constructor
    public User() {}

    // Parameterized constructor
    public User(int userId, String name, String email, Timestamp createdAt) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    // Constructor without userId and createdAt (for creation)
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getters and Setters
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}