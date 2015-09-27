package codered.crChop.Visual;

import codered.crChop.Enums.Tree;
import org.powerbot.script.PaintListener;
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
    private Gui gui;
    private static String status;
    public static int width, height;
    private int logs;
    private String tree = "";
    private Color bg = new Color(0, 0, 0, 175), paint = Color.white;

    public Paint(ClientContext ctx, Tree tree, int logs) {
        super(ctx);
        this.tree = tree.getName();
        this.logs = logs;
    }

    public void repaint(Graphics g) {
        PaintMethods PaintMethods = new PaintMethods(ctx);
        long runtime = ctx.controller.script().getTotalRuntime();
        final Graphics2D g2 = (Graphics2D) g;
        FontMetrics fm = g2.getFontMetrics();
        int textOffset = fm.getHeight();


        // SCRIPT PAINT
        // ============
        PaintMethods.borderedRect(2, 2, width, height, paint, bg, g2);
        g2.setColor(paint);

        // Paint Title
        String[] paintStrings = {"crChop - " + PaintMethods.formatTime(runtime),
                "Level: " + ctx.skills.realLevel(Constants.SKILLS_WOODCUTTING) + " (+" + PaintMethods.levelsGained() + ")",
                "Exp: " + PaintMethods.formatLetter(PaintMethods.experienceGained()) + " (" + PaintMethods.formatLetter(PaintMethods.hourlyExperience()) + " /hr)",
                tree + "s: " + PaintMethods.formatLetter(logs) + " (" + PaintMethods.logsPerHour() + " /hr)",
                "Leveling in: " + PaintMethods.timeTillLevel(),
                "Maxing in: " + PaintMethods.timeTillMax()
        };
        List<String> strings = new ArrayList<String>(Arrays.asList(paintStrings));

        PaintMethods.stringTitle(String.valueOf(strings.toArray()[0]), width + 1, g2);

        for (int i = 1; i != paintStrings.length; i++) {
            g2.drawString(paintStrings[i], 5, textOffset * (i + 1));
        }

//        if (gui.getPreset() >= 0) {
//            Presets p = Presets.presets[gui.getPreset()];
//            g2.fillPolygon(p.area.getPolygon());
//        }

        width = fm.stringWidth(PaintMethods.getLongestString(strings, g2)) + 4;
        height = (textOffset * paintStrings.length) + 2;
    }

    public static void paintStatus(String s) {
        status = s;
    }

    public static String getStatus() {
        return status;
    }
}
