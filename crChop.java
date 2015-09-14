package crChop;

import crChop.Tasks.StartUp;
import crChop.Variables.Variables;
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
    private final int width = ctx.game.dimensions().width, height = ctx.game.dimensions().height;
    public int rand;
    Gui gui = new Gui(ctx);
    Variables variables = new Variables();
    //    private Paint paint = new Paint(ctx);
    private CursorPaint cursor = new CursorPaint(ctx);
    private BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

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
    }

    @Override
    public void stop() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        savePaint(dateFormat.format(date));
    }

    @Override
    public void poll() {
        rand = Random.nextInt(0, 1000);
//        if (!ctx.players.local().inMotion() && ctx.players.local().animation() == -1) {
//            Paint.status = "Idle";
//        }

        for (Task t : StartUp.taskList) {
            if (t.activate()) {
                t.execute();
            }
        }
    }

    @Override
    public void repaint(Graphics g) {
        Paint paint = new Paint(ctx);
        cursor.drawMouse(g);
        if (ctx.game.loggedIn() && variables.getTree() != null) {
            paint.repaint(g);
        } else {
            Paint.status = "Starting up";
        }
    }

    @Override
    public void messaged(MessageEvent messageEvent) {
        String msg = messageEvent.text();

        if (msg.contains("You get some " + variables.getTree().getName().toLowerCase())) {
            Paint.logs++;
        } else if (msg.contains("You get some logs.")) {
            Paint.logs++;
        }
    }
}
