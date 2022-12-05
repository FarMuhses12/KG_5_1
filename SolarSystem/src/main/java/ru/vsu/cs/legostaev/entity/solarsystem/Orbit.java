package ru.vsu.cs.legostaev.entity.solarsystem;

public class Orbit {

    protected static final double GRAVITY_CONSTANT = 1.4878 * Math.pow(10, -34); // AU^3 / (kg*d^2)

    protected final double semiMajor;
    protected final double semiMinor;
    protected final double eccentricity;
    protected final double period;
    protected final double orbitalPeriod;
    protected final double focus;
    protected final double standardGravParam;

    public Orbit(double objMass, double parentMass, double semiMajor, double eccentricity) {
        this.semiMajor = semiMajor;
        this.eccentricity = eccentricity;
        this.focus = this.semiMajor * this.eccentricity;
        this.semiMinor = this.semiMajor * Math.sqrt(1 - this.eccentricity * this.eccentricity);
        this.period = Math.pow(this.semiMajor, 1.5);
        this.standardGravParam = GRAVITY_CONSTANT * (objMass + parentMass);
        this.orbitalPeriod = 2 * Math.PI * Math.sqrt(Math.pow(this.semiMajor, 3) / this.standardGravParam);
    }

}
