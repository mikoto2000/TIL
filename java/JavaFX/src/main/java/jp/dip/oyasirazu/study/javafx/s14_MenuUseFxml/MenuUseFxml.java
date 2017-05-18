package jp.dip.oyasirazu.study.javafx;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MenuUseFxml extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // fxml ロード
        Pane root = FXMLLoader.load(getClass().getResource("/MenuUseFxml.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Menu Use Fxml");
        stage.show();
    }

}


