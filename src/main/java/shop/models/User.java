package shop.models;

public class User {
    private int id;          // Stored in both the database and in the JSON file
    private boolean isStaff; // Stored in the database only. Note: passwords are only stored in the JSON file

    public User(int id) {
        this.id = id;
        this.isStaff = false;
    }
    public User(int id, boolean isStaff) {
        this.id = id;
        this.isStaff = isStaff;
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

}
