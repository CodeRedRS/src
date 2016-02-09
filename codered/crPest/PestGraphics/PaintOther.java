package codered.crPest.PestGraphics;

import org.powerbot.script.PaintListener;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;

import java.awt.*;

/**
 * Created by Dakota on 2/6/2016.
 */
public class PaintOther extends ClientAccessor implements PaintListener {
    public PaintOther(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void repaint(Graphics g) {
        final Graphics2D g2 = (Graphics2D) g;
        FontMetrics fm = g2.getFontMetrics();
        Tile t = ctx.players.local().interacting().tile();
        Point p = t.matrix(ctx).mapPoint();
        Polygon poly = t.matrix(ctx).bounds();

        g2.setColor(new Color(255, 0, 0, 50));
        g2.fillPolygon(poly);

        g2.setColor(new Color(255, 0, 0));
        g2.drawPolygon(poly);
        g2.drawLine(p.x-5, p.y+5, p.x+5, p.y-5);
        g2.drawLine(p.x+5, p.y+5, p.x-5, p.y-5);

        g2.setColor(new Color(0xB0A185));
        Rectangle rec = ctx.widgets.component(162, 42).boundingRect();
        int width = fm.stringWidth(ctx.widgets.component(162, 42).text().split(":")[0] + ": ");
        Rectangle name = new Rectangle(rec.x, rec.y + 1, width, rec.height - 1) ;
        g2.fill(name);

    }
}
