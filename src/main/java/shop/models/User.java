package shop.models;

public class User {
    private int id;          // Stored in both the database and in the JSON file
    private boolean isStaff; // Stored in the database only. Note: passwords are only stored in the JSON file
    private String password;

    public User(int id, String password) {
        this.id = id;
        this.isStaff = false;
        this.password = password;
    }
    public User(int id, boolean isStaff, String password) {
        this.id = id;
        this.isStaff = isStaff;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isStaff() {
        return isStaff;
    }

    public void setStaff(boolean staff) {
        isStaff = staff;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
