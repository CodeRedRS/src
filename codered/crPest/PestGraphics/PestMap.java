package codered.crPest.PestGraphics;

import codered.crPest.PestUtil.PestConstants;
import codered.crPest.PestUtil.PestVariables;
import codered.universal.crPaint;
import org.powerbot.script.PaintListener;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Created by Dakota on 2/5/2016.
 */
public class PestMap extends ClientAccessor implements PaintListener {
    public PestMap(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void repaint(Graphics g) {
        final Graphics2D g2 = (Graphics2D) g;
        crPaint paint = new crPaint();
        FontMetrics fm = g2.getFontMetrics();
        Color bg = new Color(0, 0, 0, 100);
        if (PestVariables.voidKnightTile != null) {
            Point knightPoint = PestVariables.voidKnightTile.matrix(ctx).mapPoint();
            Tile tempTile = new Tile(PestVariables.voidKnightTile.x(), PestVariables.voidKnightTile.y() + PestVariables.knightRadius);
            Point radiusPoint = tempTile.matrix(ctx).mapPoint();
            double distance = (Math.sqrt((knightPoint.x - radiusPoint.x) * (knightPoint.x - radiusPoint.x) + (knightPoint.y - radiusPoint.y) * (knightPoint.y - radiusPoint.y)));

            g2.setColor(bg);
            g2.fill(new Ellipse2D.Double(knightPoint.x - distance, knightPoint.y - distance, distance * 2, distance * 2));
            g2.setColor(Color.white);
            g2.draw(new Ellipse2D.Double(knightPoint.x - distance, knightPoint.y - distance, distance * 2, distance * 2));

        }

        if (PestVariables.voidKnightTile != null) {
            String health= ctx.widgets.widget(408).component(3).text();
            Point p = PestVariables.voidKnightTile.matrix(ctx).mapPoint();

            paint.shadowString(health, p.x - (fm.stringWidth(health) / 2), p.y + (fm.getHeight() / 4), Color.white, g2);
        }

        if (PestVariables.purplePortalTile != null) {
            String health= ctx.widgets.widget(408).component(14).text();
            Point p = PestVariables.purplePortalTile.matrix(ctx).mapPoint();
            Color portal = PestConstants.purplePortal;

            g2.setColor(bg);
            g2.fillOval(p.x - (fm.stringWidth(health) / 2) - 5, p.y - (fm.stringWidth(health) / 2) - 5, fm.stringWidth(health) + 9, fm.stringWidth(health) + 9);
            g2.setColor(portal);
            g2.drawOval(p.x - (fm.stringWidth(health) / 2) - 5, p.y - (fm.stringWidth(health) / 2) - 5, fm.stringWidth(health) + 9, fm.stringWidth(health) + 9);
            paint.shadowString(health, p.x - (fm.stringWidth(health) / 2), p.y + (fm.getHeight() / 4), PestConstants.purplePortal, g2);
        }

        if (PestVariables.yellowPortalTile != null) {
            String health= ctx.widgets.widget(408).component(16).text();
            Point p = PestVariables.yellowPortalTile.matrix(ctx).mapPoint();
            Color portal = PestConstants.yellowPortal;

            g2.setColor(bg);
            g2.fillOval(p.x - (fm.stringWidth(health) / 2) - 5, p.y - (fm.stringWidth(health) / 2) - 5, fm.stringWidth(health) + 9, fm.stringWidth(health) + 9);
            g2.setColor(portal);
            g2.drawOval(p.x - (fm.stringWidth(health) / 2) - 5, p.y - (fm.stringWidth(health) / 2) - 5, fm.stringWidth(health) + 9, fm.stringWidth(health) + 9);
            paint.shadowString(health, p.x - (fm.stringWidth(health) / 2), p.y + (fm.getHeight() / 4), PestConstants.yellowPortal, g2);
        }

        if (PestVariables.bluePortalTile != null) {
            String health= ctx.widgets.widget(408).component(15).text();
            Point p = PestVariables.bluePortalTile.matrix(ctx).mapPoint();
            Color portal = PestConstants.bluePortal;

            g2.setColor(bg);
            g2.fillOval(p.x - (fm.stringWidth(health) / 2) - 5, p.y - (fm.stringWidth(health) / 2) - 5, fm.stringWidth(health) + 9, fm.stringWidth(health) + 9);
            g2.setColor(portal);
            g2.drawOval(p.x - (fm.stringWidth(health) / 2) - 5, p.y - (fm.stringWidth(health) / 2) - 5, fm.stringWidth(health) + 9, fm.stringWidth(health) + 9);
            paint.shadowString(health, p.x - (fm.stringWidth(health) / 2), p.y + (fm.getHeight() / 4), PestConstants.bluePortal, g2);
        }

        if (PestVariables.redPortalTile != null) {
            String health= ctx.widgets.widget(408).component(17).text();
            Point p = PestVariables.redPortalTile.matrix(ctx).mapPoint();
            Color portal = PestConstants.redPortal;

            g2.setColor(bg);
            g2.fillOval(p.x - (fm.stringWidth(health) / 2) - 5, p.y - (fm.stringWidth(health) / 2) - 5, fm.stringWidth(health) + 9, fm.stringWidth(health) + 9);
            g2.setColor(portal);
            g2.drawOval(p.x - (fm.stringWidth(health) / 2) - 5, p.y - (fm.stringWidth(health) / 2) - 5, fm.stringWidth(health) + 9, fm.stringWidth(health) + 9);
            paint.shadowString(health, p.x - (fm.stringWidth(health) / 2), p.y + (fm.getHeight() / 4), PestConstants.redPortal, g2);
        }
    }
}
