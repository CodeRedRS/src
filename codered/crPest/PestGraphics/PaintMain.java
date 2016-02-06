package codered.crPest.PestGraphics;

import codered.crPest.PestUtil.PestVariables;
import codered.universal.crPaint;
import org.powerbot.script.PaintListener;
import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dakota on 2/5/2016.
 */
public class PaintMain extends ClientAccessor implements PaintListener {
    public PaintMain(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void repaint(Graphics g) {
        // Variables
        crPaint p = new crPaint();
        final Graphics2D g2 = (Graphics2D) g;
        final FontMetrics fm = g2.getFontMetrics();
        int top = 125;
        int left = 10;
        long runtime = ctx.controller.script().getTotalRuntime();
        String runtimeFormat = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(runtime),
                TimeUnit.MILLISECONDS.toMinutes(runtime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(runtime)),
                TimeUnit.MILLISECONDS.toSeconds(runtime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(runtime)));
        ArrayList<String> info = new ArrayList<String>();

        // Info
        info.add(0, runtimeFormat);
        info.add("InGame: " + PestVariables.inGame);
        info.add("Boarded: " + PestVariables.boarded);
        info.add("Points: " + PestVariables.totalPestPoints + "(+" + PestVariables.gainedPestPoints + ")");

        // Info Loop
        for (String s : info) {
            p.shadowString(s, left, top, Color.white, g2);
            top += fm.getHeight();
        }
    }
}
