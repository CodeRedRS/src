package codered.crPest.PestGraphics;

import org.powerbot.script.PaintListener;
import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;

import java.awt.*;

/**
 * Created by Dakota on 2/6/2016.
 */
public class PestMouse extends ClientAccessor implements PaintListener {
    public PestMouse(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void repaint(Graphics g) {
        final Graphics2D g2 = (Graphics2D) g;
        Point p = ctx.input.getLocation();

        g2.setColor(Color.white);
        g2.drawLine(p.x - 5, p.y, p.x + 5, p.y);
        g2.drawLine(p.x, p.y - 5, p.x, p.y + 5);
    }
}
