package jp.dip.oyasirazu.study.javafx;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TreeViewUseFxml extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Pane root = FXMLLoader.load(getClass().getResource("/TreeViewUseFxml.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Tree View Use Fxml");
        stage.show();
    }
}

