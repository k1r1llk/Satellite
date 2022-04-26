package model.Errors;

import model.Motion;

public class DupPomeh implements SensorError {
    private Motion am;

    private double p_err_t;

    public DupPomeh(Motion am, double p_err_t) {
        this.am = am;
        this.p_err_t = p_err_t;
    }

    @Override
    public double getX(double t) {
        if (t == p_err_t) return am.getPrevX()*2.2;
        return am.getX();
    }

    @Override
    public double getDx(double t) {
        return am.getDx();
    }
}
