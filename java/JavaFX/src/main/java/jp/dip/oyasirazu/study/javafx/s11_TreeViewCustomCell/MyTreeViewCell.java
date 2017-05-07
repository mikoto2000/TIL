package jp.dip.oyasirazu.study.javafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * MyTreeViewCell
 */
public class MyTreeViewCell extends TreeCell<TreeViewUser> {

    private FXMLLoader loader = new FXMLLoader(getClass().getResource("/MyTreeViewCell.fxml"));
    private Image image = new Image(getClass().getResourceAsStream("/TabPane/TabIcon.png"));

    @Override
    public void updateItem(TreeViewUser item, boolean isEmpty) {
        super.updateItem(item, isEmpty);
        if (isEmpty || item == null) {
            Pane p = new Pane();
            setGraphic(p);
            return;
        }

        try {
            Pane root = loader.load();
            MyTreeViewCellController c = loader.getController();
            c.icon1.setImage(image);
            c.icon2.setImage(image);
            c.icon3.setImage(image);
            c.id.setText(item.getId());
            c.displayName.setText(item.getDisplayName());
            setGraphic(root);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("erroe!");
        }

        return;
    }
}
