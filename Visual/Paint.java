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
    public static String status = "";
    public static int logs, width, height;
    Gui gui = new Gui(ctx);
    private int startLevel, startExperience;
    private PaintMethods PaintMethods = new PaintMethods(ctx, this.startLevel, this.startExperience);
    private String tree = gui.getTree().getName();
    private Color bg = new Color(0, 0, 0, 150);
    private Color paint = Color.white;
    private Color debug = Color.red;

    public Paint(ClientContext ctx, int startLevel, int startExperience) {
        super(ctx);
        this.startLevel = startLevel;
        this.startExperience = startExperience;
    }

    public void repaint(Graphics g) {
        long runtime = ctx.controller.script().getTotalRuntime();
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

//        for (GameObject t : ctx.objects.select().name(tree.getName()).within(PaintMethods.mapArea()).limit(10)) {
//            Point p = t.tile().matrix(ctx).mapPoint();
//            g2.setColor(bg);
//            g2.fillRect(p.x + 8, p.y - 2, fm.stringWidth(t.name() + " (" + (int) ctx.players.local().tile().distanceTo(t.tile()) + ")") + 2, textOffset - 2);
//            PaintMethods.shadowString(t.name() + " (" + (int) ctx.players.local().tile().distanceTo(t.tile()) + ")", p.x + 10, p.y + (textOffset / 2 + 1), Color.cyan, g2);
//            g2.setColor(Color.cyan);
//            g2.fillOval(p.x, p.y, 10, 10);
//        }
    }
}
