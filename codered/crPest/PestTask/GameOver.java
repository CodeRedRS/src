package codered.crPest.PestTask;

import codered.crPest.PestUtil.PestMethods;
import codered.crPest.PestUtil.PestVariables;
import codered.universal.Task;
import org.powerbot.script.rt4.ClientContext;

/**
 * Created by Dakota on 2/6/2016.
 */
public class GameOver extends Task<ClientContext> {
    public GameOver(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return !ctx.widgets.widget(408).component(5).valid()
                && !PestVariables.boarded;
    }

    @Override
    public void execute() {
        PestMethods.resetGame(ctx);
    }
}
