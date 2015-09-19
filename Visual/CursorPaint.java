package crChop.Visual;

import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;

import java.awt.*;

/**
 * Created by Dakota on 9/7/2015.
 */
public class CursorPaint extends ClientAccessor {
    private int startLevel, startExpeience, logs;

    public CursorPaint(ClientContext ctx, int startLevel, int startExpeience, int logs) {
        super(ctx);
        this.startLevel = startLevel;
        this.startExpeience = startExpeience;
        this.logs = logs;
    }

    public void drawMouse(Graphics g) {
        PaintMethods paintMethods = new PaintMethods(ctx, this.startLevel, this.startExpeience, this.logs);
        final Graphics2D g2 = (Graphics2D) g;
        Point p = ctx.input.getLocation();

        g2.setColor(Color.white);
        g2.drawOval(p.x - 10, p.y - 10, 20, 20);
        g2.drawLine(p.x + 5, p.y + 5, p.x - 5, p.y - 5);
        g2.drawLine(p.x + 5, p.y - 5, p.x - 5, p.y + 5);

        if (!ctx.game.loggedIn()) {
            paintMethods.shadowString(Paint.status, p.x + 15, p.y + 5, Color.red, g2);
        } else {
            paintMethods.shadowString(Paint.status, p.x + 15, p.y + 5, Color.white, g2);
        }

    }

}
