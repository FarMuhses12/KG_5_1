package ru.vsu.cs.legostaev.frame;

import ru.vsu.cs.legostaev.entity.solarsystem.AbstractSpaceObject;
import ru.vsu.cs.legostaev.entity.solarsystem.Planet;
import ru.vsu.cs.legostaev.entity.solarsystem.Sun;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class DialogNewPlanet extends JDialog {
    private JPanel panelMain;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldMass;
    private JTextField textFieldRadius;
    private JTextField textFieldSemiMajor;
    private JTextField textFieldEccentricity;
    private JButton chooseColorButton;
    private JLabel labelColor;

    private Color color = AbstractSpaceObject.DEFAULT_OBJECT_COLOR;
    private Sun sun;

    private Planet result = null;

    public DialogNewPlanet(Sun sun) {

        this.sun = sun;
        setContentPane(panelMain);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setPaint(color);
        graphics.fillRect(0, 0, 50, 50);
        labelColor.setIcon(new ImageIcon(image));

        pack();

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        panelMain.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        chooseColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                color = javax.swing.JColorChooser.showDialog(
                        new javax.swing.JDialog(), "Выберите цвет", color);
                BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = image.createGraphics();
                graphics.setPaint(color);
                graphics.fillRect(0, 0, 50, 50);
                labelColor.setIcon(new ImageIcon(image));
            }
        });

        setLocationRelativeTo(null);
    }

    private void onOK() {
        try {
            double mass = Double.parseDouble(textFieldMass.getText());
            int radius = Integer.parseInt(textFieldRadius.getText());
            double semiMajor = Double.parseDouble(textFieldSemiMajor.getText());
            double eccentricity = Double.parseDouble(textFieldEccentricity.getText());
            result = new Planet(sun, mass, radius, color, semiMajor, eccentricity);
            dispose();
        } catch (Exception e) {
            onCancel();
        }
    }

    private void onCancel() {
        dispose();
    }

    public Planet getResult() {
        Planet planet = result;
        result = null;
        return planet;
    }
}
