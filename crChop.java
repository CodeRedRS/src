package crChop;

import crChop.Enums.Tree;
import crChop.Tasks.*;
import crChop.Variables.Widget;
import crChop.Visual.CursorPaint;
import crChop.Visual.Gui;
import crChop.Visual.Paint;
import org.powerbot.script.*;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.Item;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Dakota on 9/7/2015.
 */
@Script.Manifest(
        name = "crChop",
        description = "AIO Woodcutter"
)
public class crChop extends PollingScript<ClientContext> implements PaintListener, MessageListener {
    private final int width = ctx.game.dimensions().width, height = ctx.game.dimensions().height;
    private List<Task> taskList = new ArrayList<>();
    private Gui gui = new Gui(ctx);
    private final Tree tree = gui.getTree();
    private final String method = gui.getMethod();
    private int startExperience, startLevel, axeId;
    private CursorPaint cursor = new CursorPaint(ctx, this.startLevel, this.startExperience);
    private Paint paint = new Paint(ctx, this.startLevel, this.startExperience);
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
        if (ctx.game.loggedIn()) {

            Paint.status = "Setting up script";
            Widget.initiateWidgets(ctx);

            this.startExperience = ctx.skills.experience(Constants.SKILLS_WOODCUTTING);
            this.startLevel = ctx.skills.realLevel(Constants.SKILLS_WOODCUTTING);

            if (!Widget.settingsWidget.visible()) {
                Widget.settingsButtonWidget.click();
                Widget.zoomWidget.interact("Restore Default Zoom");
                if (!Widget.inventoryWidget.visible()) {
                    Widget.inventoryButtonWidget.click();
                } else {
                    // GET AXE ID
                    for (Item i : ctx.inventory.items()) {
                        if (i.name().toLowerCase().contains("axe")) {
                            System.out.println(i.name() + "(" + i.id() + ")");
                            axeId = i.id();
                        }
                    }
                }
            }

            ctx.camera.pitch(50);
            gui.setVisible(true);

            while (gui.isVisible()) {
                Condition.sleep(100);
            }

            if (gui.getMethod().toLowerCase().contains("bank")) {
                taskList.addAll(Arrays.asList(new Banking(ctx, this.method, this.axeId), new Run(ctx), new Inventory(ctx), new Chop(ctx, this.tree), new Antiban(ctx)));
            } else if (gui.getMethod().toLowerCase().contains("drop")) {
                taskList.addAll(Arrays.asList(new Drop(ctx), new Run(ctx), new Inventory(ctx), new Chop(ctx, tree), new Antiban(ctx)));
            }
        } else {
            ctx.controller.stop();
        }
    }

    @Override
    public void stop() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
//        if (ctx.game.loggedIn())
//            savePaint(dateFormat.format(date));
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
        if (ctx.game.loggedIn() && this.tree != null) {
            paint.repaint(g);
        } else {
            Paint.status = "Starting up";
        }
    }

    @Override
    public void messaged(MessageEvent messageEvent) {
        String msg = messageEvent.text();

        if (msg.contains("You get some " + this.tree.getName().toLowerCase())) {
            Paint.logs++;
        } else if (msg.contains("You get some logs.")) {
            Paint.logs++;
        }
    }
}
