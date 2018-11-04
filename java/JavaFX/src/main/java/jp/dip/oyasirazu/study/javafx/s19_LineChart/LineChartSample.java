package jp.dip.oyasirazu.study.javafx;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.stage.Stage;

/**
 * LineChart
 */
public class LineChartSample extends Application {

    public static void main(String[] args) {
        Application.launch(LineChartSample.class, args);
    }

    @Override
    public void start(Stage stage) {
        // 縦横の Axis 定義
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        // Axis を組み合わせてチャートを定義
        final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

        // チャートに登録するデータ(シリーズ)を作成
        LineChart.Series<Number, Number> series1 = createSin();
        LineChart.Series<Number, Number> series2 = createCos();
        LineChart.Series<Number, Number> series3 = createAtan();

        // シリーズをチャートに登録
        ObservableList<LineChart.Series<Number, Number>> lineChartDatas =lineChart.getData();
        lineChartDatas.add(series1);
        lineChartDatas.add(series2);
        lineChartDatas.add(series3);

        // シーンにチャートを追加して表示
        Scene scene = new Scene(lineChart, 800, 600);
        stage.setScene(scene);
        stage.setTitle("LineChart Sample");
        stage.show();
    }

    // sin のシリーズを生成
    private LineChart.Series<Number, Number> createSin() {
        LineChart.Series<Number, Number> series = new LineChart.Series<>();
        series.setName("sin");

        for (double i = 0; i <= Math.PI * 2; i += 0.1) {
            series.getData().add(new LineChart.Data<Number, Number>(i, Math.sin(i)));
        }

        return series;
    }

    // cos のシリーズを生成
    private LineChart.Series<Number, Number> createCos() {
        LineChart.Series<Number, Number> series = new LineChart.Series<>();
        series.setName("cos");

        for (double i = 0; i <= Math.PI * 2; i += 0.1) {
            series.getData().add(new LineChart.Data<Number, Number>(i, Math.cos(i)));
        }

        return series;
    }

    // tan のシリーズを生成
    private LineChart.Series<Number, Number> createAtan() {
        LineChart.Series<Number, Number> series = new LineChart.Series<>();
        series.setName("atan");

        for (double i = 0; i <= Math.PI * 2; i += 0.1) {
            series.getData().add(new LineChart.Data<Number, Number>(i, Math.atan(i)));
        }

        return series;
    }
}
