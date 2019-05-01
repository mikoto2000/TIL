package jp.dip.oyasirazu.study.yaml;

/**
 * Config
 */
public class Config {

    private String label;
    private String description;
    private String newText;

    /**
     * Constructor
     */
    public Config(String label, String description, String newText) {
        this.label = label;
        this.description = description;
        this.newText = newText;
    }

    public String toString() {
        return String.format("{label: %s, description: %s, newText: %s}",
                this.label, this.description, this.newText);
    }
}

