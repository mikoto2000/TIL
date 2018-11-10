package jp.dip.oyasirazu.study.javafx;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.BarChart;
import javafx.stage.Stage;

/**
 * BarChart
 */
public class BarChartSample extends Application {

    public static void main(String[] args) {
        Application.launch(BarChartSample.class, args);
    }

    @Override
    public void start(Stage stage) {
        // 縦横の Axis 定義
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        // Axis を組み合わせてチャートを定義
        final BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        // チャートに登録するデータ(シリーズ)を作成
        BarChart.Series<String, Number> series1 = createUserA();
        BarChart.Series<String, Number> series2 = createUserB();
        BarChart.Series<String, Number> series3 = createUserC();

        // シリーズをチャートに登録
        ObservableList<BarChart.Series<String, Number>> barChartDatas = barChart.getData();
        barChartDatas.add(series1);
        barChartDatas.add(series2);
        barChartDatas.add(series3);

        // シーンにチャートを追加して表示
        Scene scene = new Scene(barChart, 800, 600);
        stage.setScene(scene);
        stage.setTitle("BarChart Sample");
        stage.show();
    }

    // UserA のシリーズを生成
    private BarChart.Series<String, Number> createUserA() {
        BarChart.Series<String, Number> series = new BarChart.Series<>();
        series.setName("Twitter");

        series.getData().add(new BarChart.Data<String, Number>("User A", 60));
        series.getData().add(new BarChart.Data<String, Number>("User B", 5));
        series.getData().add(new BarChart.Data<String, Number>("User C", 80));

        return series;
    }

    // UserB のシリーズを生成
    private BarChart.Series<String, Number> createUserB() {
        BarChart.Series<String, Number> series = new BarChart.Series<>();
        series.setName("Facebook");

        series.getData().add(new BarChart.Data<String, Number>("User A", 30));
        series.getData().add(new BarChart.Data<String, Number>("User B", 90));
        series.getData().add(new BarChart.Data<String, Number>("User C", 5));

        return series;
    }

    // UserC のシリーズを生成
    private BarChart.Series<String, Number> createUserC() {
        BarChart.Series<String, Number> series = new BarChart.Series<>();
        series.setName("Tumblr");

        series.getData().add(new BarChart.Data<String, Number>("User A", 10));
        series.getData().add(new BarChart.Data<String, Number>("User B", 5));
        series.getData().add(new BarChart.Data<String, Number>("User C", 15));

        return series;
    }
}



