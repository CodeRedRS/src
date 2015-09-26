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

    @Override
    public boolean activate() {
        return ctx.players.local().inCombat() && ctx.inventory.count() != 28;
    }

    @Override
    public void execute() {
        Tile currentTile = ctx.players.local().tile();
        Tile randomTile = new Tile((currentTile.x() + Random.nextInt(-150, 150)), (currentTile.y() + Random.nextInt(-150, 150)));
        Paint.status = "Running from combat " + randomTile;
        if (ctx.movement.findPath(randomTile).traverse()) {
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return !ctx.players.local().inMotion();
                }
            }, 1000, 10);
        }
    }
}
