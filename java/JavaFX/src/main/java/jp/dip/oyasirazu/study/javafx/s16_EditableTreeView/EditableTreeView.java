package jp.dip.oyasirazu.study.javafx;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class EditableTreeView extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Pane root = FXMLLoader.load(getClass().getResource("/EditableTreeView.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Editable Tree View Use Fxml");
        stage.show();
    }
}

