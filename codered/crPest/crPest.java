package codered.crPest;

import codered.crPest.PestGraphics.*;
import codered.crPest.PestTask.Async;
import codered.crPest.PestUtil.*;
import codered.universal.Task;
import codered.universal.crProperties;
import org.powerbot.script.*;
import org.powerbot.script.rt4.ClientContext;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Dakota on 2/5/2016.
 */
@Script.Manifest(
        name = "crPest",
        description = "Pest Control " + PestConstants.scriptVersion,
        properties = "topic=1287172;client=4;"
)
public class crPest extends PollingScript<ClientContext> implements PaintListener, MessageListener {
    PestGui gui;
    @Override
    public void start() {
        if (PestConstants.blacklist.contains(PestVariables.userId)) {
            JOptionPane.showMessageDialog(null, "You're added to the Blacklist. If you think this is a mistake please " +
                    "feel free to contact me.", "Blacklisted", JOptionPane.ERROR_MESSAGE);

            ctx.controller.stop();
        }

        PestVariables.combatLevel = ctx.players.local().combatLevel();
        PestVariables.user = ctx.properties.getProperty(crProperties.userName);
        PestVariables.userId = ctx.properties.getProperty(crProperties.userId);
        PestWidgets.initiateWidgets(ctx);

        gui = new PestGui(ctx);
        gui.setVisible(true);
    }

    @Override
    public void stop() {
        gui.dispose();
    }

    @Override
    public void poll() {
        new Thread(new Async(ctx)).start();
        for (Task t : PestVariables.taskList) {
            if (t.activate()) {
                t.execute();
            }
        }
    }


    @Override
    public void messaged(MessageEvent messageEvent) {
        String msg = messageEvent.text();

        if (msg.contains("You board the lander.")) {
            PestVariables.gamesPlayed++;
        }
    }

    @Override
    public void repaint(Graphics g) {
        PaintMain main = new PaintMain(ctx);
        PaintOther other = new PaintOther(ctx);
        PestMap map = new PestMap(ctx);
        PestMouse mouse = new PestMouse(ctx);
        MouseTrail trail = new MouseTrail(ctx, null);

        main.repaint(g);
        other.repaint(g);

        mouse.repaint(g);
        trail.paint(g);
        map.repaint(g);
    }
}
