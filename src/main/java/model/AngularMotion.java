package model;

import model.Errors.SensorError;


import static java.lang.Math.abs;

public class AngularMotion {

    private double x = 0.01; // начальное отклонение угла
    private double dx = 0; // начальная угловая скорость
    public double w = 0 ; // начальная угловая скорость маховика
    private double v = 0; // начальная линейная скорость маховика
    public double dw; // угловое ускорение
    private double M_f;
    private double dt = 1;
    private double T = 1;
    private double fi;
    private double f;

    private double prevX = 0;
    private double prevDx = 0;
    private double discreteX = 0.01;
    private double discreteDx = 0;


    private double t = 0;



    private SensorError sensors;



    private double dupValue = 0.01;
    private double dusValue = 0;



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
        this.discreteX = sensors.getX(t);
        this.dupValue = discreteX;

    }


    public void setDiscreteDx() {
        this.discreteDx = sensors.getDx(t);
        this.dusValue = discreteDx;
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



    public void accelerationFlywheel(Satellite satellite) {
        if (w <= satellite.getW_max()) {
            dw = satellite.getA0() * this.discreteX + satellite.getA1() * this.discreteDx;
        } else {
            dw = 0;
        }
        this.w += dw * dt;
        this.v = this.w * satellite.getR();
        M_f = satellite.getJ_m() * dw ;
    }

    public void rotationSatelliteOnAcceleration(Satellite satellite) {
        prevX = this.x;
        prevDx = this.dx;
        this.x += dx * dt;
        this.dx += dt * (satellite.getM_p_() - (M_f / satellite.getJ_z()));
    }

    public void brakingFlywheel(Satellite satellite) {
        this.w -= satellite.getW_1_b() * dt ;
        this.v = this.w * satellite.getR() ;
        fi = satellite.getK0() * this.discreteX + satellite.getK1() * this.discreteDx ;
        if (abs(fi) > satellite.getA()) {
            f = Math.copySign(1, fi);
        } else {
            f = 0;
        }
    }

    public void rotationSatelliteOnBracking(Satellite satellite) {
        prevX = this.x;
        prevDx = this.dx;
        this.x += this.dx * dt;
        this.dx += dt * (satellite.getM_p_() + satellite.getM_b_() - satellite.getM_rc_() * f);
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





}
