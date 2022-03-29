package model.Errors;

import model.AngularMotion;

public class NormalSensor implements SensorError {

    AngularMotion am;


    @Override
    public double getX(double t) {
        return am.getX();
    }

    public NormalSensor(AngularMotion am) {
        this.am = am;
    }

    @Override
    public double getDx(double t) {
        return am.getDx();
    }
}
