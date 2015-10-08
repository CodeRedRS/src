package codered.crChop.Visual;

import org.powerbot.script.PaintListener;
import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;

import java.awt.*;

/**
 * Created by Dakota on 10/5/2015.
 */
public class PaintInteract extends ClientAccessor implements PaintListener {
    int width = Paint.width;
    PaintMethods pm = new PaintMethods(ctx);
    private Color bg = new Color(0, 0, 0, 175);
    public PaintInteract(ClientContext ctx) {
        super(ctx);
    }

    public void repaint(Graphics g) {
        final Graphics2D g2 = (Graphics2D) g;
        FontMetrics fm = g2.getFontMetrics();

        pm.borderedRect(width + 2, 2, fm.stringWidth("Screenshot") + 4, fm.getHeight() + 4, Color.white, bg, g2);
        pm.centerString("Screenshot", fm.stringWidth("Screenshot") + 4, width + 2, 2, g2);
    }
}
