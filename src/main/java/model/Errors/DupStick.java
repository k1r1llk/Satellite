package model.Errors;

import model.AngularMotion;

public class DupStick implements SensorError {
    private AngularMotion am;
    private double dupStickValue = 0;

    private double p_err_t;

    public DupStick(AngularMotion am, double p_err_t) {
        this.am = am;
        this.p_err_t = p_err_t;
    }

    @Override
    public double getX(double t) {
        if (t == p_err_t) dupStickValue = am.getX();
        if (t >= p_err_t) return dupStickValue;
        return am.getX();
    }

    @Override
    public double getDx(double t) {
        return am.getDx();
    }
}
