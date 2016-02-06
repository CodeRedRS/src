package codered.crPest.PestTask;

import codered.crPest.PestUtil.PestVariables;
import codered.crPest.PestUtil.PestWidgets;
import codered.universal.Task;
import org.powerbot.script.rt4.ClientContext;

/**
 * Created by Dakota on 2/6/2016.
 */
public class WaitingForGame extends Task<ClientContext> {
    private boolean waiting = false;
    public WaitingForGame(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return !PestVariables.inGame
                && PestVariables.boarded
                && !waiting;
    }

    @Override
    public void execute() {
        PestVariables.totalPestPoints = Integer.parseInt(PestWidgets.pestPoints.text().split(" ")[2].trim());
        if (PestVariables.startPestPoints == 0) {
            PestVariables.startPestPoints = Integer.parseInt(PestWidgets.pestPoints.text().split(" ")[2].trim());
        } else {
            PestVariables.gainedPestPoints = (Integer.parseInt(PestWidgets.pestPoints.text().split(" ")[2].trim())) - PestVariables.startPestPoints;
        }
        waiting = true;
    }
}
