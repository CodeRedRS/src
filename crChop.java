package crChop;

import crChop.Tasks.StartUp;
import crChop.Variables.Widget;
import crChop.Visual.CursorPaint;
import crChop.Visual.Gui;
import crChop.Visual.Paint;
import org.powerbot.script.*;
import org.powerbot.script.rt4.ClientContext;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dakota on 9/7/2015.
 */
@Script.Manifest(
        name = "crChop",
        description = "AIO Woodcutter"
)
public class crChop extends PollingScript<ClientContext> implements PaintListener, MessageListener {
    Paint paint = new Paint(ctx);
    CursorPaint cursor = new CursorPaint(ctx);
    final int width = ctx.game.dimensions().width, height = ctx.game.dimensions().height;
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);


    public void savePaint(String name) {
        System.out.println("Screenshot saved at: " + ctx.controller.script().getStorageDirectory().getPath());
        repaint(img.createGraphics());
        img = img.getSubimage(2, 2, Paint.width + 1, Paint.height + 1);
        final File path = new File(ctx.controller.script().getStorageDirectory().getPath(), name + ".png");

        try {
            ImageIO.write(img, "png", path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
        StartUp.taskList.add(new StartUp(ctx));
        Widget.initiateWidgets(ctx);
    }

    @Override
    public void stop() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        savePaint(dateFormat.format(date));
    }

    @Override
    public void poll() {
        for (Task t : StartUp.taskList) {
            if (t.activate()) {
                t.execute();
            }
        }
    }

    @Override
    public void repaint(Graphics g) {
        cursor.drawMouse(g);
        if (ctx.game.loggedIn()) {
            paint.repaint(g);
        } else {
            Paint.status = "Logging in";
        }
    }

    @Override
    public void messaged(MessageEvent messageEvent) {
        String msg = messageEvent.text();

        if (msg.contains("You get some " + Gui.selectedTree.toLowerCase())) {
            Paint.logs++;
        }
    }
}
