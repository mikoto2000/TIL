package jp.dip.oyasirazu.study.javafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * CustomCell
 */
public class CustomCell extends ListCell<CustomCellItem> {
 

    @Override
    public void updateItem(CustomCellItem item, boolean isEmpty) {
        super.updateItem(item, isEmpty);
        if (isEmpty || item == null) {
            Pane p = new Pane();
            setGraphic(p);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CustomCell.fxml"));
            Pane root = loader.load();
            CustomCellController c = loader.getController();
            Image image = new Image(getClass().getResourceAsStream("/icon.png"));
            c.icon.setImage(image);
            c.text.setText(item.getName());
            setGraphic(root);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("erroe!");
        }

        return;
    }
}
