package codered.crChop.Visual;

import org.powerbot.script.PaintListener;
import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;

import java.awt.*;

/**
 * Created by Dakota on 10/5/2015.
 */
public class PaintInteract extends ClientAccessor implements PaintListener {
    private int height = Paint.height;
    private int width = Paint.width;
    private int optionWidth = 75;
    private PaintMethods pm = new PaintMethods(ctx);
    private Color bg = new Color(0, 0, 0, 175);

    public Rectangle btnScreenshot, btnGui, btnExpandPaint, btnHidePaint;

    public PaintInteract(ClientContext ctx) {
        super(ctx);
    }

    public void repaint(Graphics g) {
        final Graphics2D g2 = (Graphics2D) g;
        FontMetrics fm = g2.getFontMetrics();

        // Set Rectangles
        btnScreenshot = new Rectangle(width + 4, 2, optionWidth, fm.getHeight());
        btnGui = new Rectangle(width + 4, (int)btnScreenshot.getHeight() + 2, optionWidth, fm.getHeight());
//        btnScreenshot =
        btnHidePaint = new Rectangle(width + 4, (int)btnGui.getHeight() * 2 + 2, optionWidth, fm.getHeight());


        // Draw Rectangles
        pm.borderedRect(width + 4, 2, optionWidth, fm.getHeight(), Color.white, bg, g2);
        pm.borderedRect(width + 4, (int)btnScreenshot.getHeight() + 2, optionWidth, fm.getHeight(), Color.white, bg, g2);
        pm.borderedRect(width + 4, (int)btnGui.getHeight() * 2 + 2, optionWidth, fm.getHeight(), Color.white, bg, g2);

        // Draw Strings
        g2.setColor(Color.white);
        pm.centerString("Screenshot", optionWidth, width + 4, fm.getHeight() - 1, g2);
        pm.centerString("Reopen Gui", optionWidth, width + 4, (int)btnScreenshot.getHeight() * 2 - 1, g2);
        pm.centerString("Hide Paint", optionWidth, width + 4, (int)btnScreenshot.getHeight() * 3 - 1, g2);
    }
}
