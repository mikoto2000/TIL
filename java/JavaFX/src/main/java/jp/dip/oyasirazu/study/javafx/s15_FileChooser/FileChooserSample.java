package jp.dip.oyasirazu.study.javafx;

import java.io.File;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FileChooserSample
 */
public class FileChooserSample extends Application {

    @Override
    public void start(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("File Chooser Dialog");
        File choosedFile = fileChooser.showOpenDialog(stage);

        System.out.println(choosedFile);
        System.exit(0);
    }
}
