package codered.crChop.Visual;

import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;

import java.awt.*;

/**
 * Created by Dakota on 9/7/2015.
 */
public class CursorPaint extends ClientAccessor {
    public CursorPaint(ClientContext ctx) {
        super(ctx);
    }

    private Color warning = new Color(255, 100, 100);

    public void drawMouse(Graphics g) {
        PaintMethods paintMethods = new PaintMethods(ctx);
        final Graphics2D g2 = (Graphics2D) g;
        Point p = ctx.input.getLocation();

        try {
            if (!ctx.game.loggedIn() || Paint.getStatus().contains("[i]")) {
                g2.setColor(warning);
                g2.drawOval(p.x - 10, p.y - 10, 20, 20);
                g2.drawLine(p.x + 5, p.y + 5, p.x - 5, p.y - 5);
                g2.drawLine(p.x + 5, p.y - 5, p.x - 5, p.y + 5);
                paintMethods.shadowString(Paint.getStatus().replace("[i]", ""), p.x + 15, p.y + 5, warning, g2);
            } else {
                g2.setColor(Color.white);
                paintMethods.shadowString(Paint.getStatus(), p.x + 15, p.y + 5, Color.white, g2);
                g2.drawOval(p.x - 10, p.y - 10, 20, 20);
                g2.drawLine(p.x + 5, p.y + 5, p.x - 5, p.y - 5);
                g2.drawLine(p.x + 5, p.y - 5, p.x - 5, p.y + 5);
            }
        } catch (Exception ex) {

        }


    }

}
