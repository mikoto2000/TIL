package jp.dip.oyasirazu.study.javafx;

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

        Text t = new Text();
        t.setText(item.getIcon() + " : " + item.getName());

        HBox hbox = new HBox(8);
        Image image = new Image(getClass().getResourceAsStream("/icon.png"));
        hbox.getChildren().addAll(new ImageView(image), t);
        setGraphic(hbox);

        return;
    }
}
