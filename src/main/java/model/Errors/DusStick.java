package model.Errors;

import model.Motion;

public class DusStick implements SensorError {
    private Motion am;
    private double dusStickValue = 0;
    private double s_err_t;

    public DusStick(Motion am, double s_err_t) {
        this.am = am;
        this.s_err_t = s_err_t;
    }

    @Override
    public double getX(double t) {
        return am.getX();
    }

    @Override
    public double getDx(double t) {
        if (t == s_err_t) dusStickValue = am.getDx();
        if (t >= s_err_t) return dusStickValue;
        return am.getDx();
    }
}
