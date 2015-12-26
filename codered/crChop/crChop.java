package codered.crChop;

import codered.crChop.Enums.Axe;
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
import org.powerbot.script.rt4.Equipment;
import org.powerbot.script.rt4.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
        description = "AIO Woodcutter v1.8",
        properties = "topic=1283889;client=4;"
)
public class crChop extends PollingScript<ClientContext> implements PaintListener, MessageListener, MouseListener {
    public static final double version = 1.8;

    public static int startExperience, startLevel;
    public static List<Task> taskList = new ArrayList<Task>();
    private List<Player> players = new ArrayList<Player>();
    public static int logs, radius;
    private final int width = ctx.game.dimensions().width, height = ctx.game.dimensions().height;
    private int delay;
    private Gui gui;
    private boolean axeEquiped;

    public static final String[] news = {"Coming soon!"};

    private CursorPaint cursor = new CursorPaint(ctx);
    private PaintInteract paintInteract;
    private BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd hh-mm-ssaaa");
    private Date date = new Date();

    public void savePaint(String name) {
        repaint(img.createGraphics());
        img = img.getSubimage(2, 2, Paint.width + 1, Paint.height + 1);

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

    private int getAxeId() {
        Widget.inventoryButtonWidget.click();
        for (int i = 0; i < Axe.values().length; i++) {
            if (ctx.inventory.select().id(Axe.values()[i].getAxeId()).count() >= 1) {
                axeEquiped = false;
                System.out.println("Inventory: '" + Axe.values()[i].getAxeName() + "'(" + Axe.values()[i].getAxeId() + ")");
                return Axe.values()[i].getAxeId();
            }
        }
        Widget.equipmentButtonWidget.click();
        System.out.println("Equipped: '" + ctx.equipment.itemAt(Equipment.Slot.MAIN_HAND).name() + "'(" + ctx.equipment.itemAt(Equipment.Slot.MAIN_HAND).id() + ")");
        return ctx.equipment.itemAt(Equipment.Slot.MAIN_HAND).id();
    }

    @Override
    public void start() {

        Widget.initiateWidgets(ctx);
        int axeId = getAxeId();

        gui = new Gui(ctx, axeId, this.axeEquiped);
        gui.setVisible(true);

        Paint.paintStatus("[i]Setting up script");
        if (ctx.game.loggedIn())
        {
            startExperience = ctx.skills.experience(Constants.SKILLS_WOODCUTTING);
            startLevel = ctx.skills.realLevel(Constants.SKILLS_WOODCUTTING);

            ctx.camera.pitch(Random.nextInt(50, 75));
            if (axeId > -1)
                gui.setVisible(true);

        } else

        {
            JOptionPane.showMessageDialog(null, "Please login then start the script.\nThank you!", "Start Logged In", ERROR_MESSAGE);
            System.out.println("Please login then start.");
            ctx.controller.stop();
        }

    }

    @Override
    public void stop() {
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
        try {
            gui.repaint(g);
            Paint paint;
            cursor.drawMouse(g);
            if (ctx.game.loggedIn()) {
                try {
                    if (gui.getPreset() >= 0) {
                        paint = new Paint(ctx, gui.getTree(), logs, Presets.presets[gui.getPreset()].area, gui.treeRadius(), gui.getStartTile());
                        paintInteract = new PaintInteract(ctx);
                        paintInteract.repaint(g);
                    } else {
                        paint = new Paint(ctx, gui.getTree(), logs, null, gui.treeRadius(), gui.getStartTile());
                        paintInteract = new PaintInteract(ctx);
                        paintInteract.repaint(g);
                    }
                    paint.repaint(g);
                } catch (Exception ex) {

                }
            }
        } catch (Exception ex) {
        }
    }


    @Override
    public void messaged(MessageEvent messageEvent) {
        final Timer timer = new Timer();
        String msg = messageEvent.text();
        String receivedMsg = msg.toLowerCase();

        if (gui.chatResponder()) {
            int delaySeconds = 60;
            if ((receivedMsg.contains("lvl") || receivedMsg.contains("level")) && !receivedMsg.contains(ctx.players.local().name()) && delay < 1) {
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
            if ((receivedMsg.contains("nice") || receivedMsg.contains("cool") || receivedMsg.contains("sweet")) && delay < (delaySeconds / 2)) {
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

        if (msg.contains("You get some " + gui.getTree().getName().toLowerCase().replace("tree", ""))) {
            logs++;
        } else if (msg.contains("You get some logs")) {
            logs++;
        }
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        Point mouse = e.getPoint();

        if (paintInteract.btnScreenshot.contains(mouse) && !Paint.hidden) {
            savePaint(dateFormat.format(date));
        }
        if (paintInteract.btnGui.contains(mouse)) {
            gui.setVisible(true);
            ctx.controller.suspend();
        }
        if (paintInteract.btnHidePaint.contains(mouse)) {
            Paint.hidden = !Paint.hidden;
        }
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
