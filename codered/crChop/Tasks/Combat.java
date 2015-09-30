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
    private Tile player, random;

    @Override
    public boolean activate() {
        return ctx.players.local().inCombat() && ctx.inventory.count() != 28;
    }

    @Override
    public void execute() {
        player = ctx.players.local().tile();
        random = new Tile((player.x() + Random.nextInt(-150, 150)), (player.y() + Random.nextInt(-150, 150)));
        Paint.paintStatus("Running from combat " + random);
        if (ctx.movement.step(random)) {
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return (ctx.movement.destination().distanceTo(ctx.players.local()) < 10);
                }
            }, 250, 10);
        }
    }
}
