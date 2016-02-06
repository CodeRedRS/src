package codered.crPest.PestGraphics;

import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by Dakota on 2/6/2016.
 */
public class MouseTrail extends ClientAccessor {
    private int size = 15; // default trail size
    private final double alphaStep = (255.0 / size);
    Color color;

    public MouseTrail(ClientContext ctx, Color color) {
        super(ctx);
        this.color = color;
    }

    private static LinkedList<Point> trail = new LinkedList<Point>();

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Color c = color;

        if (trail.size() <= size) {
            trail.add(ctx.input.getLocation());
        }
        if (trail.size() == size) {
            trail.removeFirst();
        }

        double alpha = 0;
        for (int x = 0; x < trail.size() - 1; x++) {
            Point p = trail.get(x);
            Point p1 = trail.get(x + 1);

            if (color == null) {
                c = Color.getHSBColor((float)(alpha / 255), 1, 1);
            } else {
                c = color;
            }

            g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), (int)alpha));
            g2.drawLine((int) p.getX(), (int) p.getY(), (int) p1.getX(), (int) p1.getY());
            alpha += alphaStep;
        }
    }

    private int getTrailPercentage(final int x) {
        return (x * 100) / size;

    }

    public void setTrailSize(final int size) {
        this.size = size;
    }

}
