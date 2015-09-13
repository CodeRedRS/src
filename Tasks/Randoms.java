package crChop.Tasks;

import crChop.Task;
import crChop.Visual.Paint;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Npc;

/**
 * Created by Dakota on 9/12/2015.
 */
public class Randoms extends Task<ClientContext> {
    public Randoms(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return !ctx.npcs.select().action("Dismiss").isEmpty();
    }

    @Override
    public void execute() {
        Npc randomEvent = ctx.npcs.nearest().poll();

        if (randomEvent.inViewport()) {
            Paint.status = "Dismissing Random " + randomEvent.name();
            System.out.println("Dismissing Random " + randomEvent.name());
            randomEvent.interact("Dismiss");
        }
    }
}
