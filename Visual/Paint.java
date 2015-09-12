package crChop.Visual;

import org.powerbot.script.PaintListener;
import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;

import java.awt.*;

/**
 * Created by Dakota on 9/7/2015.
 */
public class Paint extends ClientAccessor implements PaintListener {
    PaintMethods PaintMethods = new PaintMethods(ctx);

    public Paint(ClientContext ctx) {
        super(ctx);
    }

    Color bg = new Color(0, 0, 0, 150);
    Color paint = Color.white;
    Color debug = Color.red;

    public static String status = "";
    public static int logs;
    int width, height;
    long runtime;

    public void repaint(Graphics g) {
        runtime = ctx.controller.script().getTotalRuntime();
        final Graphics2D g2 = (Graphics2D) g;
        FontMetrics fm = g2.getFontMetrics();
        int textOffset = fm.getHeight();
        width = 125 ;
        height = (textOffset * 6) + 2;

        // SCRIPT PAINT
        // ============
        PaintMethods.borderedRect(2, 2, width, height, paint, bg, g2);

        g2.setColor(paint);
        PaintMethods.stringTitle("crChop - " + PaintMethods.formatTime(runtime), width + 1, g2);
        g2.drawString("Level: " + ctx.skills.realLevel(Constants.SKILLS_WOODCUTTING) + " (+" + PaintMethods.levelsGained() + ")", 5, textOffset * 2);
//        g2.drawString("Exp: " + PaintMethods.formatLetter(PaintMethods.experienceGained()), 5, textOffset * 3);
//        g2.drawString("Exp: " + PaintMethods.formatExperience.format(PaintMethods.experienceGained()), 5, textOffset * 3);
        g2.drawString("Exp: " + PaintMethods.formatLetter(PaintMethods.experienceGained()) + "(" + PaintMethods.formatLetter(PaintMethods.hourlyExperience()) + " /hr)", 5, textOffset * 3);
        g2.drawString("Logs: " + logs + "(" + PaintMethods.logsPerHour() + " /hr)", 5, textOffset * 4);
        g2.drawString("Leveling in: " + PaintMethods.timeTillLevel(), 5, textOffset * 5);
        g2.drawString("Maxing in: " + PaintMethods.timeTillMax(), 5, textOffset * 6);
    }
}
