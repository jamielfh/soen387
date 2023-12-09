package shop.models;

public class User {
    private int id;
    private boolean isStaff;
    private String passcode;

    public User(int id) {
        this.id = id;
    }

    public User(int id, boolean isStaff) {
        this.id = id;
        this.isStaff = isStaff;
    }

    public User(int id, String passcode) {
        this.id = id;
        this.isStaff = false;
        this.passcode = passcode;
    }
    public User(int id, boolean isStaff, String passcode) {
        this.id = id;
        this.isStaff = isStaff;
        this.passcode = passcode;
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

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }
}
