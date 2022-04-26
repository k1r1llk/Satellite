package model.Errors;

import model.Motion;

public class NormalSensor implements SensorError {

    Motion am;


    @Override
    public double getX(double t) {
        return am.getX();
    }

    public NormalSensor(Motion am) {
        this.am = am;
    }

    @Override
    public double getDx(double t) {
        return am.getDx();
    }
}
