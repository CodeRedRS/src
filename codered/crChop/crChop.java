package codered.crChop;

import codered.crChop.Variables.Presets;
import codered.crChop.Variables.Widget;
import codered.crChop.Visual.CursorPaint;
import codered.crChop.Visual.Gui;
import codered.crChop.Visual.Paint;
import codered.crChop.Visual.PaintInteract;
import codered.universal.Task;
import org.powerbot.script.*;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.Timer;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

/**
 * Created by Dakota on 9/7/2015.
 */
@Script.Manifest(
        name = "crChop",
        description = "AIO Woodcutter v1.4",
        properties = "topic=1283889;client=4;"
)
public class crChop extends PollingScript<ClientContext> implements PaintListener, MessageListener {
    public static double version = 1.4;

    public static int startExperience, startLevel;
    public static List<Task> taskList = new ArrayList<Task>();
    public static int logs, radius;
    private final int width = ctx.game.dimensions().width, height = ctx.game.dimensions().height;
    private int delay;
    private Gui gui;

    private CursorPaint cursor = new CursorPaint(ctx);
    private PaintInteract paintInteract = new PaintInteract(ctx);
    private BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    public void savePaint(String name) {
        repaint(img.createGraphics());
        img = img.getSubimage(2, 2, codered.crChop.Visual.Paint.width + 1, codered.crChop.Visual.Paint.height + 1);

        final File path = new File(ctx.controller.script().getStorageDirectory().getPath(), name + ".png");

        try {
            ImageIO.write(img, "png", path);
            System.out.println("Screenshot(" + name + ") saved at: " + ctx.controller.script().getStorageDirectory().getPath());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Screenshot failed to save", "Screenshot Error", ERROR_MESSAGE);
            System.out.println("Screenshot Failed to save");
        }
    }

    @Override
    public void start() {
        Widget.initiateWidgets(ctx);
        if (!Widget.inventoryWidget.visible())
            Widget.inventoryButtonWidget.click();

        gui = new Gui(ctx);
        Paint.paintStatus("[i]Setting up script");
        if (ctx.game.loggedIn()) {

            startExperience = ctx.skills.experience(Constants.SKILLS_WOODCUTTING);
            startLevel = ctx.skills.realLevel(Constants.SKILLS_WOODCUTTING);


            ctx.camera.pitch(50);
            gui.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(null, "Please login then start the script.\nThank you!", "Start Logged In", ERROR_MESSAGE);
            System.out.println("Please login then start.");
            ctx.controller.stop();
        }
    }

    @Override
    public void stop() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd hh-mm-ssaaa");
        Date date = new Date();
        if (gui.saveScreenshot())
            savePaint(dateFormat.format(date));
    }

    @Override
    public void poll() {
        for (Task t : taskList) {
            if (t.activate()) {
                t.execute();
            }
        }
    }

    @Override
    public void repaint(Graphics g) {
        Paint paint;
        cursor.drawMouse(g);
        if (ctx.game.loggedIn()) {
            try {
                if (gui.getPreset() >= 0) {
                    paint = new Paint(ctx, gui.getTree(), logs, Presets.presets[gui.getPreset()].area);
                } else {
                    paint = new Paint(ctx, gui.getTree(), logs, null);
                }
                paint.repaint(g);
            } catch (Exception ex) {

            }
        }
    }


    @Override
    public void messaged(MessageEvent messageEvent) {
        final Timer timer = new Timer();
        String msg = messageEvent.text();
        if (gui.chatResponder()) {
            String recivedMsg = msg.toLowerCase();
            int delaySeconds = 60;
            if ((recivedMsg.contains("lvl") || recivedMsg.contains("level")) && !recivedMsg.contains(ctx.players.local().name()) && delay < 1) {
                ctx.input.sendln(String.valueOf(ctx.skills.realLevel(Constants.SKILLS_WOODCUTTING)));
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        delay--;
                        if (delay < 1) {
                            timer.cancel();
                        }
                    }
                }, 0, 1000);

                delay = delaySeconds;
            }
            if ((recivedMsg.contains("nice") || recivedMsg.contains("cool") || recivedMsg.contains("sweet")) && delay < (delaySeconds / 2)) {
                switch (Random.nextInt(1, 2)) {
                    case 1:
                    default:
                        ctx.input.sendln("Thanks");
                        break;
                    case 2:
                        ctx.input.sendln("You?");
                        break;

                }

                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        delay--;
                        if (delay < 1) {
                            timer.cancel();
                        }
                    }
                }, 0, 1000);

                delay = delaySeconds;
            }
        }

        if (msg.contains("You get some " + gui.getTree().getName().toLowerCase())) {
            logs++;
        } else if (msg.contains("You get some logs")) {
            logs++;
        }
    }
}
