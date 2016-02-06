package codered.crPest.PestTask;

import codered.universal.Task;
import org.powerbot.script.rt4.ClientContext;

/**
 * Created by Dakota on 2/5/2016.
 */
public class Fight extends Task<ClientContext> {
    public Fight(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return false;
    }

    @Override
    public void execute() {

    }
}
