package ru.vsu.cs.legostaev.entity;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class World implements ActionListener, IUpdatable {

    private final static int DEFAULT_WORLD_DELAY = 100;
    private final Timer timer;
    private int ticksFromStart;

    private List<IUpdatable> listOfObjects;

    public World() {
        this(DEFAULT_WORLD_DELAY);
    }

    public World(int delay) {
        listOfObjects = new ArrayList<>();
        timer = new Timer(delay, this);
        ticksFromStart = 0;
    }

    public void setDelay(int newDelay) {
        timer.setDelay(newDelay);
    }

    public int getDelay() {
        return timer.getDelay();
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void reset() {
        timer.stop();
        ticksFromStart = 0;
        update(ticksFromStart);
    }

    public int getTicksFromStart() {
        return ticksFromStart;
    }

    public void addObject(IUpdatable object) {
        listOfObjects.add(object);
    }

    public Iterator<IUpdatable> getObjectsIterator() {
        return listOfObjects.listIterator();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            ticksFromStart++;
            update(ticksFromStart);
        }
    }

    @Override
    public void update(int t) {
        for (IUpdatable obj: listOfObjects) {
            obj.update(t);
        }
    }
}
