package jp.dip.oyasirazu.study.javafx;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.stage.Stage;

/**
 * StackedBarChart
 */
public class StackedBarChartSample extends Application {

    public static void main(String[] args) {
        Application.launch(StackedBarChartSample.class, args);
    }

    @Override
    public void start(Stage stage) {
        // 縦横の Axis 定義
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        // Axis を組み合わせてチャートを定義
        final StackedBarChart<String, Number> barChart = new StackedBarChart<>(xAxis, yAxis);

        // チャートに登録するデータ(シリーズ)を作成
        StackedBarChart.Series<String, Number> series1 = createUserA();
        StackedBarChart.Series<String, Number> series2 = createUserB();
        StackedBarChart.Series<String, Number> series3 = createUserC();

        // シリーズをチャートに登録
        ObservableList<StackedBarChart.Series<String, Number>> barChartDatas = barChart.getData();
        barChartDatas.add(series1);
        barChartDatas.add(series2);
        barChartDatas.add(series3);

        // シーンにチャートを追加して表示
        Scene scene = new Scene(barChart, 800, 600);
        stage.setScene(scene);
        stage.setTitle("StackedBarChart Sample");
        stage.show();
    }

    // UserA のシリーズを生成
    private StackedBarChart.Series<String, Number> createUserA() {
        StackedBarChart.Series<String, Number> series = new StackedBarChart.Series<>();
        series.setName("Twitter");

        series.getData().add(new StackedBarChart.Data<String, Number>("User A", 60));
        series.getData().add(new StackedBarChart.Data<String, Number>("User B", 5));
        series.getData().add(new StackedBarChart.Data<String, Number>("User C", 80));

        return series;
    }

    // UserB のシリーズを生成
    private StackedBarChart.Series<String, Number> createUserB() {
        StackedBarChart.Series<String, Number> series = new StackedBarChart.Series<>();
        series.setName("Facebook");

        series.getData().add(new StackedBarChart.Data<String, Number>("User A", 30));
        series.getData().add(new StackedBarChart.Data<String, Number>("User B", 90));
        series.getData().add(new StackedBarChart.Data<String, Number>("User C", 5));

        return series;
    }

    // UserC のシリーズを生成
    private StackedBarChart.Series<String, Number> createUserC() {
        StackedBarChart.Series<String, Number> series = new StackedBarChart.Series<>();
        series.setName("Tumblr");

        series.getData().add(new StackedBarChart.Data<String, Number>("User A", 10));
        series.getData().add(new StackedBarChart.Data<String, Number>("User B", 5));
        series.getData().add(new StackedBarChart.Data<String, Number>("User C", 15));

        return series;
    }
}

