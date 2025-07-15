package view;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;

public class CircleThumbSliderUI extends BasicSliderUI {
    public CircleThumbSliderUI(JSlider slider) {
        super(slider);
    }

    @Override
    public void paintThumb(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        int diameter = 14; // size of the circle
        int x = thumbRect.x + (thumbRect.width - diameter)/2;
        int y = thumbRect.y + (thumbRect.height - diameter)/2;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE); // inside color
        g2d.fillOval(x, y, diameter, diameter);
        g2d.setColor(Color.GRAY);  // border color
        g2d.drawOval(x, y, diameter, diameter);
        g2d.dispose();
    }
}