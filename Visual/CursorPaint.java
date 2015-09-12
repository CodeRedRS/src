package crChop.Visual;

import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;

import java.awt.*;

/**
 * Created by Dakota on 9/7/2015.
 */
public class CursorPaint extends ClientAccessor {
    PaintMethods PaintMethods = new PaintMethods(ctx);
    public CursorPaint(ClientContext ctx) {
        super(ctx);
    }

    public void drawMouse(Graphics g) {
        final Graphics2D g2 = (Graphics2D) g;
        Point p = ctx.input.getLocation();

        g2.setColor(Color.white);
        g2.drawOval(p.x - 10, p.y - 10, 20, 20);
        g2.drawLine(p.x + 5, p.y + 5, p.x - 5, p.y - 5);
        g2.drawLine(p.x + 5, p.y - 5, p.x - 5, p.y + 5);

        if (!ctx.game.loggedIn()) {
            PaintMethods.shadowString(Paint.status, p.x + 15, p.y + 5, Color.red, g2);
        } else {
            PaintMethods.shadowString(Paint.status, p.x + 15, p.y + 5, Color.white, g2);
        }

    }

}
