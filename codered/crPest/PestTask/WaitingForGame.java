package codered.crPest.PestTask;

import codered.crPest.PestUtil.PestVariables;
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
        waiting = true;
    }
}
