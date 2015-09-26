package codered.crChop;

import codered.crChop.Variables.Widget;
import codered.crChop.Visual.CursorPaint;
import codered.crChop.Visual.Gui;
import codered.crChop.Visual.Paint;
import codered.universal.Task;
import org.powerbot.script.*;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

/**
 * Created by Dakota on 9/7/2015.
 */
@Script.Manifest(
        name = "crChop",
        description = "AIO Woodcutter v1.2",
        properties = "topic=1283889;client=4;"
)
public class crChop extends PollingScript<ClientContext> implements PaintListener, MessageListener {

    public static int startExperience, startLevel;
    public static List<Task> taskList = new ArrayList<Task>();
    public static int logs;
    private final int width = ctx.game.dimensions().width, height = ctx.game.dimensions().height;
    private Gui gui;

    private CursorPaint cursor = new CursorPaint(ctx);
    private BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    public void savePaint(String name) {
        repaint(img.createGraphics());
        img = img.getSubimage(2, 2, codered.crChop.Visual.Paint.width + 1, codered.crChop.Visual.Paint.height + 1);

        final File path = new File(ctx.controller.script().getStorageDirectory().getPath(), name + ".png");

        try {
            ImageIO.write(img, "png", path);
            JOptionPane.showMessageDialog(null, "Screenshot(" + name + ") saved at: " + ctx.controller.script().getStorageDirectory().getPath(), "Screenshot Saved", INFORMATION_MESSAGE);
            System.out.println("Screenshot(" + name + ") saved at: " + ctx.controller.script().getStorageDirectory().getPath());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Screenshot failed to save", "Screenshot Error", ERROR_MESSAGE);
            System.out.println("Screenshot Failed to save");
        }
    }

    @Override
    public void start() {
        gui = new Gui(ctx);
        if (ctx.game.loggedIn()) {

            startExperience = ctx.skills.experience(Constants.SKILLS_WOODCUTTING);
            startLevel = ctx.skills.realLevel(Constants.SKILLS_WOODCUTTING);

            codered.crChop.Visual.Paint.status = "[i]Setting up script";
            Widget.initiateWidgets(ctx);

            if (!Widget.settingsWidget.visible()) {
                Widget.settingsButtonWidget.click();
                Widget.zoomWidget.interact("Restore Default Zoom");
                if (!Widget.inventoryWidget.visible()) {
                    Widget.inventoryButtonWidget.click();
                }
            }

            ctx.camera.pitch(50);
            gui.setVisible(true);

            while (gui.isVisible()) {
                Condition.sleep(100);
            }


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
        cursor.drawMouse(g);
        if (ctx.game.loggedIn()) {
            try {
                Paint paint = new Paint(ctx, gui.getTree(), logs);
                paint.repaint(g);
            } catch (Exception ex) {

            }
        }
    }

    @Override
    public void messaged(MessageEvent messageEvent) {
        String msg = messageEvent.text();
        if (msg.contains("You get some " + gui.getTree().getName().toLowerCase())) {
            logs++;
        } else if (msg.contains("You get some logs.")) {
            logs++;
        }
    }
}
