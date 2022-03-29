package controllers;

import FXUtils.Loader;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.stage.Stage;
import model.AngularMotion;
import model.Errors.*;
import model.Satellite;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    Stage primaryStage;
    private FXMLLoader chartsLoader = new FXMLLoader();
    private Parent chartsView;
    private ChartsController chartsController;
    private Stage chartsStage;
    private Satellite satellite;
    private SensorError sensors;
    private ArrayList<Double> x_plot = new ArrayList<>(); // значения для OX
    private ArrayList<Double> yv_plot = new ArrayList<>(); // значения для линейной скорости
    private ArrayList<Double> yx_plot = new ArrayList<>(); // значения угла
    private ArrayList<Double> ydx_plot = new ArrayList<>(); // значения угловой скорости
    private ArrayList<Double> yxm_plot = new ArrayList<>(); // значения угла с имитацией дискретности
    private ArrayList<Double> ydxm_plot = new ArrayList<>(); // значения угловой скорости с имитацией дискретности
    private ArrayList<Double> ex_plot = new ArrayList<>(); // значения угла по Эйлеру
    private ArrayList<Double> edx_plot = new ArrayList<>(); // значение угл. скорости по Эйлеру
    private ArrayList<Double> rx_plot = new ArrayList<>(); // угловое ускорение
    private AngularMotion am; // объект углового движения
    private double T = 1; // период дискретизации
    private double check_T = 0; // время съема для экстраполятора нулевого порядка
    private double ex = 0; // угол вычисленный по методу Эйлера
    private double edx = 0; // угловая скорость вычисленная по методу Эйлера
    private int i = 0; // счётчик
    private double t = 0; // время
    private double dt = 1;

    int countDupStick = 0;
    int countDusStick = 0;
    double curX = 0;
    double curDx = 0;

    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void defaultInit() {

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chartsLoader.setLocation(getClass().getResource("/ChartsView.fxml"));
        try {
            chartsView = chartsLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        chartsController = chartsLoader.getController();
        chartsStage = new Loader().initStage("Graph", chartsView, true);
        chartsController.setStage(chartsStage);

        satellite = new Satellite();
        am = new AngularMotion();
        defaultInit();

    }



    private void calculate() {
        clearAllValues();
        am.setTdis(T);
        chartsController.setT(T);
        sensors = new NormalSensor(am);
        am.setSensors(sensors);
        while (am.getW() * satellite.getJ_m() < satellite.getH_max() && t <= 1350) {
            am.setT(t);
            curX = am.getX();
            curDx = am.getDx();
            if (t == check_T) {
                valuePickupUnit();
                check_T += T;
            }

            recordingValuesUnit();

            am.accelerationFlywheel(satellite);
            am.rotationSatelliteOnAcceleration(satellite);

            t += dt;
            i += 1;

        }
        countDupStick = 0;
        countDusStick = 0;
        while (am.getW() > 0 && t <= 1350) {
            am.setT(t);

            if (t == check_T) {
                valuePickupUnit();
                check_T += T;
            }

            recordingValuesUnit();

            am.brakingFlywheel(satellite);
            am.rotationSatelliteOnBracking(satellite);

            t += dt;
            i += 1;

        }

    }

    public void recordingValuesUnit() {
        x_plot.add( (double) i);
        yv_plot.add(am.getV());

        yxm_plot.add(am.getDupValue());
        ydxm_plot.add(am.getDusValue());

        yx_plot.add(am.getNormalX());
        ydx_plot.add(am.getNormalDx());
        ex_plot.add(ex);
        edx_plot.add(edx);
        rx_plot.add(am.dw);
    }


    public void valuePickupUnit() {
        am.setDiscreteX();
        am.setDiscreteDx();
    }


    private void clearAllValues() {
        am.clearAllValues();
        ex = 0;
        edx = 0;
        i = 0;
        t = 0;
        check_T = 0;
        x_plot.clear();
        yv_plot.clear();
        yx_plot.clear();
        ydx_plot.clear();
        yxm_plot.clear();
        ydxm_plot.clear();
        ex_plot.clear();
        edx_plot.clear();
        countDupStick = 0;
        countDusStick = 0;
    }



    public void showAccelerationSection(ActionEvent actionEvent) {
        am = new AngularMotion();
        calculate();
        try {
            showGraph(0, x_plot.size()*0.07);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showBrakingSection(ActionEvent actionEvent) {
        am = new AngularMotion();
        calculate();
        try {
            showGraph(x_plot.size()*0.92, x_plot.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showGraph(double minX, double maxX) throws IOException {
        if (chartsStage.getOwner() == null) {
            chartsStage.initOwner(primaryStage);
        }
        chartsController.setGraphsData(x_plot, yv_plot, yx_plot, ydx_plot, yxm_plot, ydxm_plot, minX, maxX);
        chartsController.start();
    }







}
