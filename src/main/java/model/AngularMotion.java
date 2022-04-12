package model;

import model.Errors.SensorError;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class AngularMotion {

    private double x = 0.01; // начальное отклонение угла
    private double dx = 0; // начальная угловая скорость
    public double w = 0 ; // начальная угловая скорость маховика
    private double v = 0; // начальная линейная скорость маховика
    public double dw;
    private double M_f;
    private double dt = 1;
    private double T = 1;
    private double fi;
    private double f;

    private double prevX = 0;
    private double prevDx = 0;
    private double discreteX = 0.01;
    private double discreteDx = 0;
    public double discreteDx1 = 0;

    private double t = 0;

    private double prevDisX = 0.01;
    private double prevDisDx = 0;
    private double prevDisDx1 = 0;
    private ArrayList<Double> difDup = new ArrayList<>();
    private ArrayList<Double> intDus = new ArrayList<>();
    private double integralDus = 0.01;
    private SensorError sensors;
    private double diffDup = 0;
    private double dupValue = 0.01;
    private double dusValue = 0;
    private double dusValue1 = 0;
    private double prevDup = 0;
    private double prevDus = 0;
    private double prevDus1 = 0;


    public double getDupValue() {
        return dupValue;
    }
    public double getDusValue() {
        return dusValue;
    }

    public void setSensors(SensorError sensors) {
        this.sensors = sensors;
    }

    public void setT(double t) {
        this.t = t;
    }

    public void setDiscreteX() {
        prevDisX = discreteX ;
        prevDup = dupValue ;
        this.discreteX = sensors.getX(t);
        //this.discreteX = integralDus;
        this.dupValue = discreteX;

    }


    public void setDiscreteDx() {
        prevDisDx = discreteDx ;
        prevDus = dusValue;
        this.discreteDx = sensors.getDx(t);
        //this.discreteDx = diffDup;
        this.dusValue = discreteDx;
    }
    public void setDiscreteDx1() {
        prevDisDx1 = discreteDx1 ;
        prevDus1 = dusValue1;
        this.discreteDx = sensors.getDx(t);
        //this.discreteDx1 = diffDup;
        this.dusValue1 = discreteDx1;
    }





    public void clearAllValues() {
        discreteX = 0;
        discreteDx = 0;
        prevX = 0;
        prevDx = 0;
        M_f = 0;
        fi = 0;
        f = 0;
        dw = 0;
        w = 0;
        v = 0;
        x = 0.01;
        dx = 0;

    }

    public AngularMotion() {
    }

    //Вычсиление нормальных значений
    //--------------------------------------------------------------------------------------------
    public void accelerationFlywheel(Satellite satellite) { //функция участка разгона
        // угловое ускорение маховика равно ... или 0, если достигнута максимальная скрость
        if (w <= satellite.getW_max()) {
            dw = satellite.getA0() * this.discreteX + satellite.getA1() * this.discreteDx;
        } else {
            dw = 0; // по формуле (4)
        }
        this.w += dw * dt; //вычисление угловой скорости маховика по угловому ускорению
        this.v = this.w * satellite.getR(); // вычисление линейной скорости маховика
        M_f = satellite.getJ_m() * dw ; // момент маховика
    }

    public void rotationSatelliteOnAcceleration(Satellite satellite) {
        prevX = this.x;
        prevDx = this.dx;
        this.x += dx * dt; // по "жёлтой" формуле X1(n+1)
        this.dx += dt * (satellite.getM_p_() - (M_f / satellite.getJ_z()));
    }

    public void brakingFlywheel(Satellite satellite) { // функция участка торможения
        this.w -= satellite.getW_1_b() * dt ; // вычисление угловой скорости маховикапо угловому ускорению торможения
        this.v = this.w * satellite.getR() ; // вычисление линейной скорости маховика
        fi = satellite.getK0() * this.discreteX + satellite.getK1() * this.discreteDx ; // вычисление фи по формуле (7)
        // релейная функция, если фи по модулю > a, то 1/-1, в зависимости от знака фи, иначе 0
        if (abs(fi) > satellite.getA()) {
            f = Math.copySign(1, fi);
        } else {
            f = 0;
        }
    }

    public void rotationSatelliteOnBracking(Satellite satellite) {
        prevX = this.x;
        prevDx = this.dx;
        this.x += this.dx * dt; // по "жёлтой" формуле X1(n+1)
        this.dx += dt * (satellite.getM_p_() + satellite.getM_b_() - satellite.getM_rc_() * f);
    }


    public double getPrevX() {
        return prevX;
    }

    public double getPrevDx() {
        return prevDx;
    }

    public double getX() {
        return this.x;
    }

    public double getDx() {
        return this.dx;
    }


    public double getNormalX() {
        return this.x;
    }

    public double getNormalDx() {
        return this.dx;
    }

    public void setTdis(double T) {
        this.T = T;
    }

    public double getW() {
        return w;
    }

    public double getV() {
        return v;
    }

    public void doDiffDup() {
        diffDup = (dupValue - prevDup ) / T;
    }

    public void addDiffDup(double diffDup) {
        difDup.add(diffDup);
    }

    public void doIntDus() {
        integralDus += T / 2 * (prevDus + dusValue);
    }

    public void addIntDus(double integralDus) {
        intDus.add(integralDus);
    }
    public ArrayList<Double> getDiffDup() {
        return difDup;
    }

    public ArrayList<Double> getIntDus() {
        return intDus;
    }

    public double getCurDifDup() {
        return diffDup;
    }

    public double getCurIntDus() {
        return integralDus;
    }

}
