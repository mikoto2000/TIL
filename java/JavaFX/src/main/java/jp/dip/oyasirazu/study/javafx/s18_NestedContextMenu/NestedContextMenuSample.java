package jp.dip.oyasirazu.study.javafx;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class NestedContextMenuSample extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/NestedContextMenu.fxml"));

        Pane root = loader.load();

        stage.setScene(new Scene(root));
        stage.setTitle("Nested Context Menu");

        stage.show();
    }
}

