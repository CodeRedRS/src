package codered.crChop.Visual;

import codered.crChop.Enums.Tree;
import codered.crChop.crChop;
import org.powerbot.script.PaintListener;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Dakota on 9/7/2015.
 */
public class Paint extends ClientAccessor implements PaintListener {
    public Paint(ClientContext ctx, Tree tree, int logs, Tile[] area) {
        super(ctx);
        this.tree = tree.getName();
        this.logs = logs;
        this.area = area;
//        this.goalLevel = goalLevel;
    }

    PaintMethods PaintMethods = new PaintMethods(ctx);
    public static boolean hidden;
    private int goalLevel;
    private boolean paintOffset;
    private Tile[] area;
    private static String status;
    public static int width, height;
    private int logs, radius;
    private String tree = "";
    private Color bg = new Color(0, 0, 0, 175), paint = Color.white, areaC = new Color(255, 255, 255, 100);


    private void drawArea(Tile[] area, Graphics g) {
        Polygon polygon = new Polygon();
        Point point;
        for (Tile t : area) {
            point = t.matrix(ctx).mapPoint();
            polygon.addPoint(point.x, point.y);
        }
        g.setColor(areaC);
        g.fillPolygon(polygon);
        g.setColor(paint);
        g.drawPolygon(polygon);
    }

    public void repaint(Graphics g) {
        long runtime = ctx.controller.script().getTotalRuntime();
        final Graphics2D g2 = (Graphics2D) g;
        FontMetrics fm = g2.getFontMetrics();
        int textOffset = fm.getHeight();

        // SCRIPT PAINT
        // ============
        PaintMethods.borderedRect(2, 2, width, height, paint, bg, g2);

        // Paint Title
        String[] paintStrings = {"crChop " + crChop.version + " - " + PaintMethods.formatTime(runtime),
                status.replace("[i]", ""),
                "Level: " + ctx.skills.realLevel(Constants.SKILLS_WOODCUTTING) + " (+" + PaintMethods.levelsGained() + ")",
                "Exp gained: " + PaintMethods.formatLetter(PaintMethods.experienceGained()) + " (" + PaintMethods.formatLetter(PaintMethods.hourlyExperience()) + " /hr)",
                tree + "s: " + PaintMethods.formatLetter(logs) + " (" + PaintMethods.logsPerHour() + " /hr)",
                "To " + (ctx.skills.realLevel(Constants.SKILLS_WOODCUTTING) + 1) + ": ",
                "[t]Exp: " + PaintMethods.experienceTillLevel(),
                "[t]Time: " + PaintMethods.timeTillLevel(),
                "[t]Logs: " + PaintMethods.logsTillLevel(),
                "To 99: ",
                "[t]Exp: " + PaintMethods.experienceTillMax(),
                "[t]Time: " + PaintMethods.timeTillMax(),
                "[t]Logs: " + PaintMethods.logsTillMax(),
                "To goal: ",
                "[t]Coming soon!"
        };
        List<String> strings = new ArrayList<String>(Arrays.asList(paintStrings));
        if (!hidden) {
            width = PaintMethods.getLongestStringLength(strings, g2) + 4;
            g2.setColor(Color.white);
        } else {
            width = -1;
            g2.setColor(new Color(0, 0, 0, 0));
        }

        PaintMethods.stringTitle(String.valueOf(strings.toArray()[0]), width + 1, g2);

        for (int i = 1; i != paintStrings.length; i++) {
            if (paintStrings[i].contains("[t]")) {
                String s = paintStrings[i].replace("[t]", "");
                g2.drawString(s, 25, textOffset * (i + 1));
                paintOffset = true;
            } else {
                g2.drawString(paintStrings[i], 5, textOffset * (i + 1));
            }
        }

        if (area != null && area.length > 2) {
            drawArea(this.area, g2);
        }

        height = (textOffset * paintStrings.length) + 2;
    }

    public static void paintStatus(String s) {
        status = s;
    }

    public static String getStatus() {
        return status;
    }
}
