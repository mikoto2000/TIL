package jp.dip.oyasirazu.study.javafx;

/**
 * TreeViewUser
 */
public class TreeViewUser {

    private String id;
    private String displayName;

    /**
     * Constructor
     */
    public TreeViewUser(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return id + " : " + displayName;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }
}

