package codered.crPest.PestTask;

import codered.crPest.PestUtil.PestMethods;
import codered.crPest.PestUtil.PestWidgets;
import codered.universal.Task;
import org.powerbot.script.rt4.ClientContext;

/**
 * Created by Dakota on 11/1/2015.
 */
public class Reset extends Task<ClientContext> {
    public Reset(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return PestWidgets.gameOver.visible()
                && !ctx.objects.select().name("Gang plank").isEmpty();
    }

    @Override
    public void execute() {
        PestMethods.resetPortals();
    }
}
