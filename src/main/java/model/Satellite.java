package model;


public class Satellite {
    private double J_z = 20; // момент инерции спутника
    private double m_z = 350; // масса спутника
    private double a_z = 0.8; // размер спутника
    private double J_m = 0.02; // момент инерции маховика
    private double w_max = 900; // максимальная скорось маховика
    private double H_max = J_m * w_max ; // = 18 максимально достижимый кинетический момент маховика
    private double P_rc = 0.2 ; // тяга управляющего ракетного двигателя
    private double L_m = 0.4 ; // плечо силы тяги
    private double M_rc = P_rc * L_m ; // = 0.08
    private double M_rc_ = M_rc / J_z ; // = 0.004 управляющее ускорение от УРД
    private double w_1_max = 10 ; // максимальное ускорение при разгоне маховика
    private double M_m_max = J_m * w_1_max ; // максимально возможный управляющий момент маховика
    private double w_1_b = 1 ; // ускорение торможения маховика при сбросе накопленного кинетического момента
    private double M_b = w_1_b * J_m ; // = 0.02 момент, возникающий при торможении маховика и действующий на спутник
    private double M_b_ = M_b / J_z ; // = 0.001 угловое ускорение спутника при торможении маховика
    private double M_p_ = 0.002 ; // возмущающее ускорение
    private double r = 0.0004 ; // радиус маховика для вычисления линейной скорости
    private double a0 = 10 ; // коэффициент перед углом в вычислении dw на участке разгона
    private double a1 = 40 ; // коэффициент перед угловой скоростью в вычислении dw научастке разгона
    private double a = 0.01 ; // значение для релейной функции РУД
    private double k0 = 10 ; // коэффициент перед углом в вычислении фи на участке торможения
    private double k1 = 40 ; // коэффициент перед угловой скоростью в вычислении фи на участке торможения
    private double dt = 1;

    public Satellite() {
    }
    public Satellite(double j_z, double m_z, double a_z, double j_m, double w_max, double p_rc, double l_m, double w_1_max, double w_1_b, double m_p_, double r, double a0, double a1, double a, double k0, double k1, double dt) {
        J_z = j_z;
        this.m_z = m_z;
        this.a_z = a_z;
        J_m = j_m;
        this.w_max = w_max;
        H_max = J_m * w_max;
        P_rc = p_rc;
        L_m = l_m;
        M_rc = P_rc * L_m ;
        M_rc_ = M_rc / J_z;
        this.w_1_max = w_1_max;
        M_m_max = J_m * w_1_max ;
        this.w_1_b = w_1_b;
        M_b = w_1_b * J_m;
        M_b_ = M_b / J_z;
        M_p_ = m_p_;
        this.r = r;
        this.a0 = a0;
        this.a1 = a1;
        this.a = a;
        this.k0 = k0;
        this.k1 = k1;
        this.dt = dt;
    }
    public double getJ_z() {
        return J_z;
    }
    public double getJ_m() {
        return J_m;
    }
    public double getW_max() {
        return w_max;
    }
    public double getH_max() {
        return H_max;
    }
    public double getM_rc_() {
        return M_rc_;
    }
    public double getW_1_b() {
        return w_1_b;
    }
    public double getM_b_() {
        return M_b_;
    }
    public double getM_p_() {
        return M_p_;
    }
    public double getR() {
        return r;
    }
    public double getA0() {
        return a0;
    }
    public double getA1() {
        return a1;
    }
    public double getA() {
        return a;
    }
    public double getK0() {
        return k0;
    }
    public double getK1() {
        return k1;
    }
}
