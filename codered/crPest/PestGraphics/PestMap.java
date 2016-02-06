package codered.crPest.PestGraphics;

import codered.crPest.PestUtil.PestConstants;
import codered.crPest.PestUtil.PestVariables;
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
        if (PestVariables.voidKnightTile != null) {
            Point knightPoint = PestVariables.voidKnightTile.matrix(ctx).mapPoint();
            Tile tempTile = new Tile(PestVariables.voidKnightTile.x(), PestVariables.voidKnightTile.y() + PestVariables.knightRadius);
            Point radiusPoint = tempTile.matrix(ctx).mapPoint();
            double distance = (Math.sqrt((knightPoint.x - radiusPoint.x) * (knightPoint.x - radiusPoint.x) + (knightPoint.y - radiusPoint.y) * (knightPoint.y - radiusPoint.y)));

            g2.setColor(Color.white);
            g2.draw(new Ellipse2D.Double(knightPoint.x - distance, knightPoint.y - distance, distance * 2, distance * 2));
        }

        if (PestVariables.voidKnightTile != null) {
            Point p = PestVariables.voidKnightTile.matrix(ctx).mapPoint();

            g2.setColor(new Color(0));
            g2.fillOval(p.x - 5, p.y - 5, 10, 10);
            g2.setColor(Color.white);
            g2.drawOval(p.x - 5, p.y - 5, 10, 10);
        }

        if (PestVariables.purplePortalTile != null) {
            Point p = PestVariables.purplePortalTile.matrix(ctx).mapPoint();

            g2.setColor(PestVariables.purplePortal ? PestConstants.purplePortal : PestConstants.purplePortal.darker());
            g2.fillOval(p.x - 5, p.y - 5, 10, 10);
            g2.setColor(Color.white);
            g2.drawOval(p.x - 5, p.y - 5, 10, 10);
        }

        if (PestVariables.yellowPortalTile != null) {
            Point p = PestVariables.yellowPortalTile.matrix(ctx).mapPoint();

            g2.setColor(PestVariables.yellowPortal ? PestConstants.yellowPortal : PestConstants.yellowPortal.darker());
            g2.fillOval(p.x - 5, p.y - 5, 10, 10);
            g2.setColor(Color.white);
            g2.drawOval(p.x - 5, p.y - 5, 10, 10);
        }

        if (PestVariables.bluePortalTile != null) {
            Point p = PestVariables.bluePortalTile.matrix(ctx).mapPoint();

            g2.setColor(PestVariables.bluePortal ? PestConstants.bluePortal : PestConstants.bluePortal.darker());
            g2.fillOval(p.x - 5, p.y - 5, 10, 10);
            g2.setColor(Color.white);
            g2.drawOval(p.x - 5, p.y - 5, 10, 10);
        }

        if (PestVariables.redPortalTile != null) {
            Point p = PestVariables.redPortalTile.matrix(ctx).mapPoint();

            g2.setColor(PestVariables.redPortal ? PestConstants.redPortal : PestConstants.redPortal.darker());
            g2.fillOval(p.x - 5, p.y - 5, 10, 10);
            g2.setColor(Color.white);
            g2.drawOval(p.x - 5, p.y - 5, 10, 10);
        }
    }
}
