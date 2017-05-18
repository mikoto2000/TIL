package jp.dip.oyasirazu.study.javafx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class TableViewSample extends Application {

    @Override
    public void start(Stage stage) {

        // TableView にルート要素を指定しながらインスタンス化
        TableView<TableData> tableView = new TableView<>();

        // 列の定義
        TableColumn<TableData, String> firstNameCol =
                new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<>("firstName"));
        TableColumn<TableData, String> lastNameCol =
                new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<>("lastName"));
        ObservableList<TableColumn<TableData, ?>> columns =
                tableView.getColumns();
        columns.add(firstNameCol);
        columns.add(lastNameCol);

        // テーブルレコードの作成
        ObservableList<TableData> tableDatas =
                FXCollections.observableArrayList();
        TableData tableData01 = new TableData("First", "Last");
        tableDatas.add(tableData01);
        TableData tableData02 = new TableData("Fukuzo", "Moguro");
        tableDatas.add(tableData02);
        TableData tableData03 = new TableData("Mikoto", "Ohyuki");
        tableDatas.add(tableData03);
        tableView.setItems(tableDatas);

        // シーン作成
        Scene scene = new Scene(tableView);
        stage.setScene(scene);
        stage.setTitle("Table View Sample");
        stage.show();
    }

    /** テーブルレコードにするクラス */
    public static class TableData {
        private String firstName;
        private String lastName;

        public TableData(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFirstName() {
            return this.firstName;
        }

        public String getLastName() {
            return this.lastName;
        }
    }
}

