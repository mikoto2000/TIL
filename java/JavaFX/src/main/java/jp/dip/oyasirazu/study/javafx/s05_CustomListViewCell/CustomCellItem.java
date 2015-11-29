package jp.dip.oyasirazu.study.javafx;

/**
 * CustomCellItem
 */
public class CustomCellItem {
    private final String icon;
    private final String name;
    public CustomCellItem(String icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }
}
