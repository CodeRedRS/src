package codered.crPest;

import codered.crPest.PestTask.ClickContinue;
import codered.crPest.PestTask.Fight;
import codered.crPest.PestTask.ToLander;
import codered.crPest.PestUtil.PestConstants;
import codered.crPest.PestUtil.PestVariables;
import codered.crPest.PestUtil.PestWidgets;
import codered.universal.Run;
import codered.universal.Task;
import org.powerbot.script.*;
import org.powerbot.script.rt4.ClientContext;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

/**
 * Created by Dakota on 10/16/2015.
 */
@Script.Manifest(
        name = "crPest",
        description = "Pest Control " + PestConstants.scriptVersion,
        properties = "topic=1287172;client=4;"
)
public class crPest extends PollingScript<ClientContext> implements PaintListener, MessageListener {

    @SuppressWarnings({"serial", "static-access"})
    @Override
    public void start() {
        PestVariables.ctx = ctx;
        PestVariables.combatLevel = ctx.players.local().combatLevel();
        PestVariables.user = ctx.properties.getProperty("user.name");
        PestVariables.startTime = System.currentTimeMillis();

        if (Arrays.asList(PestConstants.validUsers).contains(PestVariables.user)) {
            System.out.println("Welcome, " + PestVariables.user + ", to crPest " + PestConstants.scriptVersion + "!");

            PestWidgets.initiateWidgets(PestVariables.ctx);
            PestVariables.taskList.addAll(Arrays.asList(/*new Antiban(ctx, 999999999), */new ToLander(PestVariables.ctx, PestVariables.combatLevel), new Fight(ctx), new ClickContinue(ctx), new Run(ctx)));
        } else {
            ctx.controller.stop();
            JOptionPane.showMessageDialog(null, "Sorry, " + PestVariables.user + ", but you're not a valid user.", "Not a Valid User", ERROR_MESSAGE);
        }
    }

    @Override
    public void stop() {
    }

    @Override
    public void poll() {
        for (Task t : PestVariables.taskList) {
            if (t.activate())
                t.execute();
        }
    }

    @Override
    public void messaged(MessageEvent e) {
        String msg = e.text().toLowerCase();

        if (msg.equalsIgnoreCase(PestConstants.boardTheLanderString)) {
            PestVariables.boarded = true;
        }

        if (msg.contains("the purple")) {
            System.out.println("Purple portal down.");
            PestVariables.purplePortal = true;
        }
        if (msg.contains("the yellow")) {
            System.out.println("Yellow portal down.");
            PestVariables.yellowPortal = true;
        }
        if (msg.contains("the blue")) {
            System.out.println("Blue portal down.");
            PestVariables.bluePortal = true;
        }
        if (msg.contains("the red")) {
            System.out.println("Red portal down.");
            PestVariables.redPortal = true;
        }

    }

    @Override
    public void repaint(Graphics graphics) {

    }
}
