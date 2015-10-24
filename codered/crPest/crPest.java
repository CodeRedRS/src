package codered.crPest;

import codered.crPest.PestTask.ClickContinue;
import codered.crPest.PestTask.DefendKnight;
import codered.crPest.PestTask.ToLander;
import codered.crPest.PestUtil.PestConstants;
import codered.crPest.PestUtil.PestVariables;
import codered.crPest.PestUtil.PestWidgets;
import codered.universal.Run;
import codered.universal.Task;
import codered.universal.crAntiban;
import codered.universal.crProperties;
import org.powerbot.script.*;
import org.powerbot.script.rt4.ClientContext;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.Callable;

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
        PestVariables.user = ctx.properties.getProperty(crProperties.userName);
        PestVariables.runTime = ctx.controller.script().getTotalRuntime();

        System.out.println("Welcome, " + PestVariables.user + ", to crPest " + PestConstants.scriptVersion + "!");

        PestWidgets.initiateWidgets(PestVariables.ctx);
        PestVariables.taskList.addAll(Arrays.asList(new crAntiban(ctx, 999999, PestWidgets.pestPoints.visible()), new ToLander(PestVariables.ctx, PestVariables.combatLevel), new DefendKnight(ctx), new ClickContinue(ctx), new Run(ctx)));

//        if (Arrays.asList(PestConstants.validUsers).contains(PestVariables.user)) {
//        } else {
//            ctx.controller.stop();
//            JOptionPane.showMessageDialog(null, "Sorry, " + PestVariables.user + ", but you're not a valid user.", "Not a Valid User", ERROR_MESSAGE);
//        }
    }

    @Override
    public void stop() {
        System.out.println("Points Gained: " + PestVariables.gainedPestPoints);
        System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date(PestVariables.runTime)));
    }

    @Override
    public void poll() {
        if ((PestVariables.purplePortalTile == null
                || PestVariables.yellowPortalTile == null
                || PestVariables.bluePortalTile == null
                || PestVariables.redPortalTile == null)
                && PestWidgets.damageDealt.visible()) {
            getPortalTiles();
        }

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

        if (msg.equalsIgnoreCase(PestConstants.deathMessage)) {
            System.out.println("Dead");
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.players.local().health() == ctx.players.local().maxHealth();
                }
            }, 100, 10);
            ctx.movement.step(PestVariables.voidKnightTile);
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
    public void repaint(Graphics g) {
        final Graphics2D g2 = (Graphics2D) g;
        if (PestVariables.voidKnightTile != null) {
            Point knightPoint = PestVariables.voidKnightTile.matrix(ctx).mapPoint();
            Tile tempTile = new Tile(PestVariables.voidKnightTile.x(), PestVariables.voidKnightTile.y() + PestConstants.knightRadius);
            Point radiusPoint = tempTile.matrix(ctx).mapPoint();
            double distance = (Math.sqrt((knightPoint.x - radiusPoint.x) * (knightPoint.x - radiusPoint.x) + (knightPoint.y - radiusPoint.y) * (knightPoint.y - radiusPoint.y)));

            g2.setColor(Color.white);
            g2.draw(new Ellipse2D.Double(knightPoint.x - distance, knightPoint.y - distance, distance * 2, distance * 2));
        }


        if (PestVariables.voidKnightTile != null) {
            Point p = PestVariables.voidKnightTile.matrix(ctx).mapPoint();

            g2.setColor(new Color(0));
            g2.fillOval(p.x - 5, p.y - 5, 10, 10);
            g2.setColor(Color.white);
            g2.drawOval(p.x - 5, p.y - 5, 10, 10);
        }

        if (PestVariables.purplePortalTile != null) {
            Point p = PestVariables.purplePortalTile.matrix(ctx).mapPoint();

            g2.setColor(PestVariables.purplePortal ? PestConstants.purplePortal : PestConstants.purplePortal.darker());
            g2.fillOval(p.x - 5, p.y - 5, 10, 10);
            g2.setColor(Color.white);
            g2.drawOval(p.x - 5, p.y - 5, 10, 10);
        }

        if (PestVariables.yellowPortalTile != null) {
            Point p = PestVariables.yellowPortalTile.matrix(ctx).mapPoint();

            g2.setColor(PestVariables.yellowPortal ? PestConstants.yellowPortal : PestConstants.yellowPortal.darker());
            g2.fillOval(p.x - 5, p.y - 5, 10, 10);
            g2.setColor(Color.white);
            g2.drawOval(p.x - 5, p.y - 5, 10, 10);
        }

        if (PestVariables.bluePortalTile != null) {
            Point p = PestVariables.bluePortalTile.matrix(ctx).mapPoint();

            g2.setColor(PestVariables.bluePortal ? PestConstants.bluePortal : PestConstants.bluePortal.darker());
            g2.fillOval(p.x - 5, p.y - 5, 10, 10);
            g2.setColor(Color.white);
            g2.drawOval(p.x - 5, p.y - 5, 10, 10);
        }

        if (PestVariables.redPortalTile != null) {
            Point p = PestVariables.redPortalTile.matrix(ctx).mapPoint();

            g2.setColor(PestVariables.redPortal ? PestConstants.redPortal : PestConstants.redPortal.darker());
            g2.fillOval(p.x - 5, p.y - 5, 10, 10);
            g2.setColor(Color.white);
            g2.drawOval(p.x - 5, p.y - 5, 10, 10);
        }
    }

    private void getPortalTiles() {
        Tile tempTile = new Tile(0, 0, 0);
        if (PestVariables.voidKnightTile == null) {
            Tile t = ctx.npcs.select().name("Void knight").poll().tile();
            if (t.compareTo(tempTile) > 0) {
                PestVariables.voidKnightTile = t;
                PestVariables.purplePortalTile = new Tile(t.x() - 27, t.y(), 0);
                PestVariables.bluePortalTile = new Tile(t.x() + 25, t.y() - 3, 0);
                PestVariables.yellowPortalTile = new Tile(t.x() + 14, t.y() - 21, 0);
                PestVariables.redPortalTile = new Tile(t.x() - 10, t.y() - 22, 0);
                System.out.println("Void knight: " + PestVariables.voidKnightTile);
                System.out.println("Purple portal: " + PestVariables.purplePortalTile);
                System.out.println("Blue portal: " + PestVariables.bluePortalTile);
                System.out.println("Yellow portal: " + PestVariables.yellowPortalTile);
                System.out.println("Red portal: " + PestVariables.redPortalTile);
            }
        }
    }
}
