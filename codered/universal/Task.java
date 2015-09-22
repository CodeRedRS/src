package codered.universal;

import org.powerbot.script.ClientAccessor;
import org.powerbot.script.ClientContext;

/**
 * Created by Dakota on 9/7/2015.
 */
public abstract class Task<C extends ClientContext> extends ClientAccessor<C> {
    public Task(C ctx) {
        super(ctx);
    }

    public abstract boolean activate();

    public abstract void execute();
}
