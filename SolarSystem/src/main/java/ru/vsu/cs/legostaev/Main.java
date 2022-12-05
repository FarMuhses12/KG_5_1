package ru.vsu.cs.legostaev;

import ru.vsu.cs.legostaev.entity.World;
import ru.vsu.cs.legostaev.entity.solarsystem.Planet;
import ru.vsu.cs.legostaev.entity.solarsystem.Sun;
import ru.vsu.cs.legostaev.frame.FrameMain;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
        World world = initializeWorld();

        new FrameMain(world).setVisible(true);
    }

    private static World initializeWorld() {
        World world = new World();

        Sun sun = new Sun(1.98892 * Math.pow(10, 30), 696340, Color.YELLOW);
        world.addObject(sun);

        Planet mercury = new Planet(sun, 3.26 * Math.pow(10, 23), 2420, new Color(139, 113, 34), 0.387, 0.206);
        world.addObject(mercury);

        Planet venus = new Planet(sun, 4.88 * Math.pow(4.88, 24), 6100, new Color(201, 178, 89), 0.723, 0.007);
        world.addObject(venus);

        Planet earth = new Planet(sun, 5.9742 * Math.pow(10, 24), 6378, new Color(102, 196, 196), 1, 0.017);
        world.addObject(earth);

        Planet mars = new Planet(sun, 6.43 * Math.pow(10, 23), 3380, new Color(201, 60, 3), 1.524, 0.093);
        world.addObject(mars);

        Planet jupiter = new Planet(sun, 1.90 * Math.pow(10, 27), 71300, new Color(177, 146, 85), 5.203, 0.048);
        world.addObject(jupiter);

        Planet saturn = new Planet(sun, 5.69 * Math.pow(10, 26), 60400, new Color(246, 208, 143), 9.555, 0.056);
        world.addObject(saturn);

        Planet uranus = new Planet(sun, 8.69 * Math.pow(10, 25), 23800, new Color(111, 169, 186), 19.22, 0.046);
        world.addObject(uranus);

        Planet neptune = new Planet(sun, 1.04 * Math.pow(10, 26), 22200, new Color(84, 144, 134), 30.11, 0.009);
        world.addObject(neptune);


        return world;
    }

}
