package model;

/**
 * User
 */
public class User {
    private String id;
    private String name;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String toString() {
        return "[ id:" + this.id + ", name:" + this.name + " ]";
    }
}
