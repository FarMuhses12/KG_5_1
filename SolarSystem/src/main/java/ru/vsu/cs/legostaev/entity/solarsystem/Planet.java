package ru.vsu.cs.legostaev.entity.solarsystem;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.solvers.BisectionSolver;
import org.apache.commons.math3.analysis.solvers.UnivariateSolver;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.awt.*;

public class Planet extends AbstractSpaceObject {

    private final Orbit orbit;
    private final Sun sun;

    public Planet(Sun sun, double mass, int radius, Color color, double semiMajor, double eccentricity) {
        super(mass, radius, color);
        this.sun = sun;
        orbit = new Orbit(this.getMass(), this.sun.getMass(), semiMajor, eccentricity);
        update(0);
    }

    @Override
    public void update(int t) {
        double normalizedTime = (t - orbit.orbitalPeriod) / orbit.orbitalPeriod;
        double meanAnomaly = 2 * Math.PI * (normalizedTime - Math.floor(normalizedTime));

        UnivariateFunction function = eccentricAnomaly -> eccentricAnomaly - orbit.eccentricity * Math.sin(eccentricAnomaly) - meanAnomaly;

        UnivariateSolver solver = new BisectionSolver();

        double eccentricAnomaly = solver.solve(100, function, -7, 7);

        setPosition(this.sun.getPosition().add(new Vector2D(orbit.semiMajor * Math.cos(eccentricAnomaly) - orbit.focus,
                orbit.semiMinor * Math.sin(eccentricAnomaly))));
    }
}
    