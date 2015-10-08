package codered.crChop.Tasks;

import codered.crChop.Visual.Paint;
import codered.universal.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import java.util.concurrent.Callable;

/**
 * Created by Dakota on 9/24/2015.
 */
public class Combat extends Task<ClientContext> {
    public Combat(ClientContext ctx) {
        super(ctx);
    }

    private Tile rand;

    @Override
    public boolean activate() {
        Tile player = ctx.players.local().tile();
        rand = new Tile((player.x() + Random.nextInt(100, 150)), (player.y() + Random.nextInt(100, 150)));
        return ctx.players.local().inCombat() && ctx.inventory.count() != 28;
    }

    @Override
    public void execute() {
        Paint.paintStatus("Running from combat " + rand);
        if (ctx.movement.step(rand)) {
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.players.local().tile().distanceTo(ctx.movement.destination()) < 1;
                }
            }, 250, 10);
        }
    }
}
