package ru.vsu.cs.legostaev.entity.solarsystem;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import ru.vsu.cs.legostaev.entity.IUpdatable;

import java.awt.*;

public abstract class AbstractSpaceObject implements IUpdatable {

    public static final Color DEFAULT_OBJECT_COLOR = Color.DARK_GRAY;

    private double mass; // kg
    private int radius; // km
    private Color color;
    private Vector2D position; // radius vector

    public AbstractSpaceObject(double mass, int radius, Color color) {
        this.mass = mass;
        this.radius = radius;
        this.color = color;
    }

    public AbstractSpaceObject(double mass, int radius) {
        this(mass, radius, DEFAULT_OBJECT_COLOR);
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }
}
