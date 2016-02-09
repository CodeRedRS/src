package codered.crPest.PestGraphics;

import org.powerbot.script.PaintListener;
import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Dakota on 2/6/2016.
 */
public class PestMouse extends ClientAccessor implements PaintListener, MouseListener {
    Color color = Color.white;
    public PestMouse(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void repaint(Graphics g) {
        final Graphics2D g2 = (Graphics2D) g;
        Point p = ctx.input.getLocation();

        g2.setColor(color);
        g2.drawLine(p.x - 5, p.y, p.x + 5, p.y);
        g2.drawLine(p.x, p.y - 5, p.x, p.y + 5);
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        color = Color.red;
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
