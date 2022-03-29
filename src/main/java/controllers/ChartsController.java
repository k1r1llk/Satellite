package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChartsController implements Initializable {

    private double T = 1;
    @FXML
    public LineChart<Double,Double> lineChart;
    private Stage stage;
    XYChart.Series series1;
    XYChart.Series series2;
    XYChart.Series series3;
    XYChart.Series series4;
    XYChart.Series series5;


    public void start(){
        stage.showAndWait();
    }

    public void setGraphsData(ArrayList<Double> x_plot, ArrayList<Double> yv_plot,
                              ArrayList<Double> yx_plot, ArrayList<Double> ydx_plot,
                              ArrayList<Double> yxm_plot, ArrayList<Double> ydxm_plot,
                              double minX, double maxX) {
        series1 = new XYChart.Series();
        series1.setName("Скорость маховика");
        series2 = new XYChart.Series();
        series2.setName("ДСДУП");
        series3 = new XYChart.Series();
        series3.setName("ДСДУС");
        series4 = new XYChart.Series();
        series4.setName("Угол отклонения спутника");
        series5 = new XYChart.Series();
        series5.setName("Угловая скорость спутника");

        int k = (int) minX;
        int count = 1;
        int step = 2;
        int l = (int) minX;
        for (int i = (int) minX; i < maxX; ++i) {

            if (Math.abs(yv_plot.get(i)) < 50)
                series1.getData().add(new XYChart.Data(String.valueOf(x_plot.get(i)), yv_plot.get(i)));
            if (Math.abs(yxm_plot.get(i)) < 50)
                series2.getData().add(new XYChart.Data(String.valueOf(x_plot.get(k)), yxm_plot.get(i)));
            if (Math.abs(ydxm_plot.get(i)) < 50)
                series3.getData().add(new XYChart.Data(String.valueOf(x_plot.get(k)), ydxm_plot.get(i) * 3));
            if (Math.abs(yx_plot.get(i)) < 50)
                series4.getData().add(new XYChart.Data(String.valueOf(x_plot.get(i)), yx_plot.get(i)));
            if (Math.abs(ydx_plot.get(i)) < 50)
                series5.getData().add(new XYChart.Data(String.valueOf(x_plot.get(i)), ydx_plot.get(i) * 3));


            if (count % 2 == 0 ) l+=step;
            if (count % 2 != 0 && k < maxX) k += step;
            count++;
        }
        lineChart.getData().clear();
        lineChart.getData().addAll(series1, series2, series3, series4, series5);
        lineChart.setHorizontalGridLinesVisible(false);
        lineChart.setVerticalGridLinesVisible(false);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lineChart.setTitle("График");
        lineChart.setCreateSymbols(false);
    }



    public void setT(double T) {
        this.T = T;
    }
}
