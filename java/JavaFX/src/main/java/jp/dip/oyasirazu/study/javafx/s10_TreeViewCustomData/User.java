package jp.dip.oyasirazu.study.javafx;

/**
 * User
 */
public class User {

    private String id;
    private String displayName;

    /**
     * Constructor
     */
    public User(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return id + " : " + displayName;
    }
}
