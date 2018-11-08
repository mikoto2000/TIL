package jp.dip.oyasirazu.study.javafx;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.stage.Stage;

/**
 * ScatterChart
 */
public class ScatterChartSample extends Application {

    public static void main(String[] args) {
        Application.launch(ScatterChartSample.class, args);
    }

    @Override
    public void start(Stage stage) {
        // 縦横の Axis 定義
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        // Axis を組み合わせてチャートを定義
        final ScatterChart<Number, Number> ScatterChart = new ScatterChart<Number, Number>(xAxis, yAxis);

        // チャートに登録するデータ(シリーズ)を作成
        ScatterChart.Series<Number, Number> series1 = createSin();
        ScatterChart.Series<Number, Number> series2 = createCos();
        ScatterChart.Series<Number, Number> series3 = createAtan();

        // シリーズをチャートに登録
        ObservableList<ScatterChart.Series<Number, Number>> ScatterChartDatas =ScatterChart.getData();
        ScatterChartDatas.add(series1);
        ScatterChartDatas.add(series2);
        ScatterChartDatas.add(series3);

        // シーンにチャートを追加して表示
        Scene scene = new Scene(ScatterChart, 800, 600);
        stage.setScene(scene);
        stage.setTitle("ScatterChart Sample");
        stage.show();
    }

    // sin のシリーズを生成
    private ScatterChart.Series<Number, Number> createSin() {
        ScatterChart.Series<Number, Number> series = new ScatterChart.Series<>();
        series.setName("sin");

        for (double i = 0; i <= Math.PI * 2; i += 0.1) {
            series.getData().add(new ScatterChart.Data<Number, Number>(i, Math.sin(i)));
        }

        return series;
    }

    // cos のシリーズを生成
    private ScatterChart.Series<Number, Number> createCos() {
        ScatterChart.Series<Number, Number> series = new ScatterChart.Series<>();
        series.setName("cos");

        for (double i = 0; i <= Math.PI * 2; i += 0.1) {
            series.getData().add(new ScatterChart.Data<Number, Number>(i, Math.cos(i)));
        }

        return series;
    }

    // tan のシリーズを生成
    private ScatterChart.Series<Number, Number> createAtan() {
        ScatterChart.Series<Number, Number> series = new ScatterChart.Series<>();
        series.setName("atan");

        for (double i = 0; i <= Math.PI * 2; i += 0.1) {
            series.getData().add(new ScatterChart.Data<Number, Number>(i, Math.atan(i)));
        }

        return series;
    }
}


