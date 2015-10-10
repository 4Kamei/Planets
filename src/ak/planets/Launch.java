package ak.planets;

import ak.planets.util.Reference;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Aleksander on 10/10/2015.
 */
public class Launch {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pre-launch settings");
        JPanel p = new JPanel(new GridLayout(3,1,10,10));
        JLabel label = new JLabel("Launching " + Reference.GAME_TITLE + " please choose a resolution");
        JButton proceed = new JButton("Proceed");
        JComboBox<DisplayModeHelper> c = new JComboBox<>();

        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice display = environment.getDefaultScreenDevice();
        ArrayList<java.awt.DisplayMode> modes = new ArrayList<>(Arrays.asList(display.getDisplayModes()));
        modes.sort((d2, d1) -> Integer.compare(d1.getWidth() * d1.getHeight(), d2.getWidth() * d2.getHeight()));;

        modes.stream().forEachOrdered((d) -> c.addItem(new DisplayModeHelper(d, d.getWidth() + "x" + d.getHeight() + "@" + d.getRefreshRate())));
        proceed.addActionListener(e -> {
            java.awt.DisplayMode m = ((DisplayModeHelper) c.getSelectedItem()).mode;
            String[] s = {m.getWidth() + "", m.getHeight() + ""};
            frame.dispose();
            Main.main(s);
        });

        p.add(label);
        p.add(c);
        p.add(proceed);
        p.setBorder(new EmptyBorder(10, 10, 10, 10));

        frame.add(p);
        frame.pack();
        frame.setVisible(true);

    }

    static class DisplayModeHelper {
        private java.awt.DisplayMode mode;
        private String name;
        DisplayModeHelper(java.awt.DisplayMode mode, String name){
            this.mode = mode;
            this.name = name;
        }

        //Required so that when added to the JComboBox, the name is displayed instead of class reference
        @Override
        public String toString(){
            return name;
        }
    }
}
