package codered.universal;

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
        return !ctx.bank.opened()
                && !ctx.movement.running()
                && ctx.movement.energyLevel() > Random.nextInt(25, 35);
    }

    @Override
    public void execute() {
        ctx.movement.running(true);
    }
}
