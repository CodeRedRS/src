package codered.crPest.PestTask;

import codered.crPest.PestUtil.PestConstants;
import codered.crPest.PestUtil.PestVariables;
import codered.crPest.PestUtil.PestWidgets;
import codered.universal.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

import java.util.concurrent.Callable;

/**
 * Created by Dakota on 10/16/2015.
 */
public class ToLander extends Task<ClientContext> {

    int gangPlankId;
    GameObject gangplank;

    public ToLander(ClientContext ctx, int combatLevel) {
        super(ctx);

        System.out.println(combatLevel);
        if (combatLevel < 70) {
            gangPlankId = PestConstants.noviceGangPlankId;
        } else if (combatLevel >= 70 && combatLevel < 100) {
            gangPlankId = PestConstants.intermediateGangPlankId;
        } else {
            gangPlankId = PestConstants.veteranGangPlankId;
        }
    }

    @Override
    public boolean activate() {
        return !PestWidgets.pestPoints.visible() &&
                !PestWidgets.damageDealt.visible();
    }

    @Override
    public void execute() {
        gangplank = ctx.objects.select().id(gangPlankId).nearest().poll();

        if (!gangplank.inViewport()) {
            if (ctx.movement.step(gangplank)) {
                if (!gangplank.inViewport()) {
                    ctx.camera.turnTo(gangplank, Random.nextInt(5, 15));
                }
            }
        } else {
            if (gangplank.interact("Cross", gangplank.name())) {
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return PestWidgets.pestPoints.visible();
                    }
                }, 50, 10);
                resetPortals();
                try {
                    if (PestVariables.startPestPoints == 0) {
                        if (PestWidgets.pestPoints.visible())
                            PestVariables.startPestPoints = Integer.parseInt(PestWidgets.pestPoints.text().split(" ")[2]);
                    } else {
                        PestVariables.gainedPestPoints = (Integer.parseInt(PestWidgets.pestPoints.text().split(" ")[2]) - PestVariables.startPestPoints);
                        System.out.println("Points Gained: " + PestVariables.gainedPestPoints);
                    }
                } catch (Exception ex) {
                    System.out.println("Unable to get Pest Points");
                }
            }
        }
    }

    private void resetPortals() {
        System.out.println("Resetting portals.\r\n");
        PestVariables.purplePortal = false;
        PestVariables.yellowPortal = false;
        PestVariables.bluePortal = false;
        PestVariables.redPortal = false;
        PestVariables.purplePortalTile = null;
        PestVariables.purplePortalTile = null;
        PestVariables.yellowPortalTile = null;
        PestVariables.bluePortalTile = null;
        PestVariables.redPortalTile = null;
        PestVariables.voidKnightTile = null;
    }
}
