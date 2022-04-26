package model.Errors;

import model.Motion;

public class DusPomeh implements SensorError {
    private Motion am;

    private double s_err_t;


    @Override
    public double getX(double t) {
        return am.getX();
    }

    public DusPomeh(Motion am, double s_err_t) {
        this.am = am;
        this.s_err_t = s_err_t;
    }

    @Override
    public double getDx(double t) {
        if (t == s_err_t) return am.getPrevDx() + 0.2001;
        return am.getDx();
    }
}
