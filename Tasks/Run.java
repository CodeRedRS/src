package crChop.Tasks;

import crChop.Task;
import crChop.Variables.Widget;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;


/**
 * Created by Dakota on 9/10/2015.
 */
public class Run extends Task<ClientContext> {
    public Run(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return ctx.movement.energyLevel() > Random.nextInt(25, 35) && Widget.runButtonStateWidget.textureId() == 1064
                && !ctx.bank.opened();
    }

    @Override
    public void execute() {
        Widget.runButtonWidget.click();
    }
}
