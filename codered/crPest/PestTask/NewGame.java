package codered.crPest.PestTask;

import codered.crPest.PestUtil.PestConstants;
import codered.crPest.PestUtil.PestVariables;
import codered.universal.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

import java.util.concurrent.Callable;

/**
 * Created by Dakota on 2/5/2016.
 */
public class NewGame extends Task<ClientContext> {
    private int boatLevel;

    public NewGame(ClientContext ctx, int boatLevel) {
        super(ctx);
        this.boatLevel = boatLevel;
    }

    @Override
    public boolean activate() {
        return !PestVariables.boarded
                && !PestVariables.inGame
                && (!ctx.objects.select().id(landerId()).isEmpty() || (!PestVariables.inGame && ctx.widgets.widget(231).component(1).valid()));
    }

    @Override
    public void execute() {
        GameObject gangplank = ctx.objects.nearest().poll();

        if (gangplank.inViewport() && !PestVariables.boarded) {
            if (gangplank.interact("Cross")) {
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return PestVariables.boarded;
                    }
                }, 10, 10);
            }
        } else {
            ctx.camera.angleTo(gangplank.tile().y());
            ctx.camera.turnTo(gangplank);
            while (!gangplank.inViewport()) {
                ctx.movement.step(gangplank);
            }
        }
    }

    private int landerId() {
        int cmbLvl = PestVariables.combatLevel;

        if (cmbLvl < 70 || boatLevel == 1) {
            return PestConstants.noviceGangPlankId;
        } else if (cmbLvl >= 70 && cmbLvl < 100 || boatLevel == 2) {
            return PestConstants.intermediateGangPlankId;
        } else if (cmbLvl >= 100 || boatLevel == 3){
            return PestConstants.veteranGangPlankId;
        } else {
            return -1;
        }
    }
}
