package ru.vsu.cs.legostaev.entity.solarsystem;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.awt.*;

public class Sun extends AbstractSpaceObject {

    public Sun(double mass, int radius, Color color) {
        super(mass, radius, color);
        setPosition(new Vector2D(0, 0));
    }

    @Override
    public void update(int t) {
    }
}
