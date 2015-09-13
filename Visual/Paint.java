package crChop.Visual;

import org.powerbot.script.PaintListener;
import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.GameObject;

import java.awt.*;

/**
 * Created by Dakota on 9/7/2015.
 */
public class Paint extends ClientAccessor implements PaintListener {
    public static String status = "";
    public static int logs;
    public static int width, height;
    PaintMethods PaintMethods = new PaintMethods(ctx);
    long runtime;
    private String selectedTree;
    private Color bg = new Color(0, 0, 0, 150);
    private Color paint = Color.white;
    private Color debug = Color.red;
    private String tree;

    public Paint(ClientContext ctx, String selectedTree) {
        super(ctx);
        this.selectedTree = selectedTree;
    }

    public void repaint(Graphics g) {
        if (selectedTree != null) {
            tree = selectedTree;
        }
        runtime = ctx.controller.script().getTotalRuntime();
        ctx.objects.select().name(selectedTree).poll();
        final Graphics2D g2 = (Graphics2D) g;
        FontMetrics fm = g2.getFontMetrics();
        int textOffset = fm.getHeight();
        width = 125;
        height = (textOffset * 6) + 2;

        // SCRIPT PAINT
        // ============
        PaintMethods.borderedRect(2, 2, width, height, paint, bg, g2);
        g2.setColor(paint);

        // Paint Title
        PaintMethods.stringTitle("crChop - " + PaintMethods.formatTime(runtime), width + 1, g2);

        // Level Information
        g2.drawString("Level: " + ctx.skills.realLevel(Constants.SKILLS_WOODCUTTING) + " (+" + PaintMethods.levelsGained() + ")", 5, textOffset * 2);

        // Experience Information
        g2.drawString("Exp: " + PaintMethods.formatLetter(PaintMethods.experienceGained()) + " (" + PaintMethods.formatLetter(PaintMethods.hourlyExperience()) + " /hr)", 5, textOffset * 3);

        // Logs Cut
        g2.drawString(tree + "s: " + logs + " (" + PaintMethods.logsPerHour() + " /hr)", 5, textOffset * 4);

        // Time Till Level
        g2.drawString("Leveling in: " + PaintMethods.timeTillLevel(), 5, textOffset * 5);

        // Time Till Max
        g2.drawString("Maxing in: " + PaintMethods.timeTillMax(), 5, textOffset * 6);

        g2.setColor(Color.cyan);

        for (GameObject trees : ctx.objects.within(PaintMethods.mapArea())) {
            Point tp = trees.tile().matrix(ctx).mapPoint();

            g2.fillOval(tp.x, tp.y, 5, 5);
        }

        GameObject t = ctx.objects.nearest().poll();
        Polygon poly = t.tile().matrix(ctx).getBounds();

        g2.drawPolygon(poly);
    }
}
