package ru.vsu.cs.legostaev.frame;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import ru.vsu.cs.legostaev.entity.IUpdatable;
import ru.vsu.cs.legostaev.entity.World;
import ru.vsu.cs.legostaev.entity.solarsystem.AbstractSpaceObject;
import ru.vsu.cs.legostaev.entity.solarsystem.Planet;
import ru.vsu.cs.legostaev.entity.solarsystem.Sun;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;

public class FrameMain extends JFrame implements ActionListener {

    private DialogNewPlanet dialogNewPlanet;

    private final Color DEFAULT_BACKGROUND_COLOR = new Color(32, 43, 77);
    private final int DEFAULT_SIZE_SCALE_FOR_DRAW = 120;

    private static final Rectangle2D INITIAL_VISIBLE_RECT = new Rectangle2D.Double(-2, 2, 4, 4);
    private Rectangle2D visibleRect;


    private final static int DEFAULT_FRAME_DELAY = 40;
    private final Timer frameTimer;

    private World world;

    private JPanel panelMain;
    private JPanel panelDraw;
    private JPanel panelControllers;
    private JButton buttonStart;
    private JButton buttonStop;
    private JButton buttonReset;
    private JSlider sliderWorldTimer;
    private JLabel labelWorldTimer;
    private JLabel labelDays;
    private JButton buttonNewPlanet;

    public FrameMain(World world) {
        this.world = world;

        frameTimer = new Timer(DEFAULT_FRAME_DELAY, this);
        frameTimer.start();

        dialogNewPlanet = new DialogNewPlanet((Sun) world.getObjectsIterator().next());

        visibleRect = INITIAL_VISIBLE_RECT;
        labelWorldTimer.setVisible(true);
        labelWorldTimer.setText(String.format("Simulation speed: %d days per second", sliderWorldTimer.getValue()));

        setTitle("Solar System");
        setSize(1000, 1000);
        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for (Component component: panelControllers.getComponents()) {
            component.setFocusable(false);
        }
        panelMain.setFocusable(true);
        panelMain.grabFocus();

        panelDraw.setLayout(new GridLayout());
        panelDraw.add(new JPanel(){
            public void paint(Graphics g){
                super.repaint();
                BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = bi.createGraphics();
                g2d.setColor(DEFAULT_BACKGROUND_COLOR);
                g2d.fillRect(0,0, bi.getWidth(), bi.getHeight());
                drawAll(g2d);
                g.drawImage(bi, 0, 0, null);
                g2d.dispose();
            }
        });

        panelMain.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int dx = 0;
                int dy = 0;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        dy += visibleRect.getHeight() * 0.2;
                        break;
                    case KeyEvent.VK_DOWN:
                        dy -= visibleRect.getHeight() * 0.2;
                        break;
                    case KeyEvent.VK_LEFT:
                        dx -= visibleRect.getWidth() * 0.2;
                        break;
                    case KeyEvent.VK_RIGHT :
                        dx += visibleRect.getWidth() * 0.2;
                        break;
                }
                visibleRect.setRect(visibleRect.getX() + dx, visibleRect.getY() + dy,
                        visibleRect.getWidth(), visibleRect.getHeight());
            }
        });

        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent event) {
                int countOfRotations = event.getWheelRotation();
                double scaleX = countOfRotations * 0.1 * INITIAL_VISIBLE_RECT.getWidth();
                double scaleY = countOfRotations * 0.1 * INITIAL_VISIBLE_RECT.getHeight();
                visibleRect.setRect(visibleRect.getX() - scaleX,
                        visibleRect.getY() + scaleY,
                        visibleRect.getWidth() + 2 * scaleX,
                        visibleRect.getHeight() + 2 * scaleY);
            }
        });

        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                world.start();
            }
        });

        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                world.stop();
            }
        });

        buttonReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                world.reset();
            }
        });

        sliderWorldTimer.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                world.setDelay((int) (1000.0 / sliderWorldTimer.getValue()));
                labelWorldTimer.setText(String.format("Simulation speed: %d days per second", (int) (1000.0 / world.getDelay())));
            }
        });

        buttonNewPlanet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogNewPlanet.setVisible(true);
                Planet planet = dialogNewPlanet.getResult();
                if (planet != null) {
                    world.addObject(planet);
                }
            }
        });

        setLocationRelativeTo(null);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frameTimer) {
            repaint();
            labelDays.setText(String.format("Passed: %d days", world.getTicksFromStart()));
        }
    }

    private void drawAll(Graphics2D g2d) {
        Iterator<IUpdatable> it = world.getObjectsIterator();
        while (it.hasNext()) {
            IUpdatable object = it.next();
            if (object instanceof AbstractSpaceObject) {
                drawSpaceObject(g2d, (AbstractSpaceObject) object);
            }
        }
        g2d.setColor(Color.BLACK);
        g2d.drawString(String.format("Visible area is %.1f x %.1f AU", visibleRect.getWidth(), visibleRect.getHeight()),
                2, 12);
    }

    private void drawSpaceObject(Graphics2D g2d, AbstractSpaceObject object) {
        int sizeScale = DEFAULT_SIZE_SCALE_FOR_DRAW;
        g2d.setColor(object.getColor());
        if (object instanceof Sun) {
            sizeScale *= 50;
        }
        Vector2D position = object.getPosition();

        Vector2D positionOnImage = DrawUtils.fromPlaneToImage(position, visibleRect, panelDraw.getWidth(), panelDraw.getHeight());

        int objectSize = (int) (object.getRadius() / (sizeScale * (visibleRect.getWidth() + visibleRect.getHeight()) / 2));

        objectSize = Math.max(2, objectSize);

        g2d.fillOval((int) positionOnImage.getX() - objectSize / 2, (int) positionOnImage.getY() - objectSize / 2,
                objectSize, objectSize);
    }
}
