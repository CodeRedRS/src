package codered.crPest.PestUtil;

import codered.universal.crPaint;
import org.powerbot.script.PaintListener;
import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Npc;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dakota on 2/4/2016.
 */
public class PestPaint extends ClientAccessor implements PaintListener {
    public PestPaint(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void repaint(Graphics g) {
        crPaint crPaint = new crPaint();
        final Graphics2D g2 = (Graphics2D)g;

        FontMetrics fm = g2.getFontMetrics();

        int top = 150;
        int left = 9;
        int gainedPestPoints = PestVariables.gainedPestPoints;
        int totalPestPoints = PestVariables.totalPestPoints;
        int gamesPlayed = PestVariables.gamesPlayed;
        long millis = ctx.controller.script().getTotalRuntime();

        String rt = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

        String interaction = ctx.players.local().interacting().name();
        ArrayList<String> paintStrings = new ArrayList<String>();

        if (interaction.length() > 0) {
            interaction += " (" + ctx.players.local().interacting().health() + "hp)";
        }

        paintStrings.add(rt);
        paintStrings.add(gainedPestPoints + "/" + totalPestPoints);
        paintStrings.add(String.valueOf(gamesPlayed));
        paintStrings.add(interaction);

        g2.setColor(Color.magenta);

        for (String s : paintStrings) {
            crPaint.shadowString(s, left, top, Color.white, g2);
            top += (fm.getHeight());
        }

        enemyTile(g2);
        mouse(g2);
    }

    private void mouse(Graphics2D g) {
        Point p = ctx.input.getLocation();

        g.setColor(new Color(0, 0, 0, 100));
        g.fillOval(p.x - 8, p.y - 8, 16, 16);

        g.setColor(Color.white);
        g.drawLine(p.x + 5, p.y + 5, p.x - 5, p.y - 5);
        g.drawLine(p.x + 5, p.y - 5, p.x - 5, p.y + 5);
        g.drawOval(p.x - 8, p.y - 8, 16, 16);
    }

    private void enemyTile(Graphics2D g) {
        Iterator<Npc> pests = ctx.npcs.select().name(PestConstants.pestNames).within(PestVariables.voidKnightTile, PestVariables.knightRadius).action("Attack").iterator();

        while (pests.hasNext()) {
            Npc pest = pests.next();
            Polygon tile = pest.tile().matrix(ctx).bounds();
            Point point = pest.tile().matrix(ctx).mapPoint();
            g.setColor(new Color(255, 0, 0, 50));
            g.fillPolygon(tile);

            g.setColor(new Color(255, 0, 0));
            g.drawPolygon(tile);

            g.drawLine(point.x - 5, point.y + 5, point.x + 5, point.y - 5);
            g.drawLine(point.x + 5, point.y + 5, point.x - 5, point.y - 5);
        }
    }
}
