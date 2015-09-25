package codered.crChop.Tasks;

import codered.crChop.Visual.Paint;
import codered.universal.Task;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

/**
 * Created by Dakota on 9/24/2015.
 */
public class Combat extends Task<ClientContext> {
    public Combat(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return ctx.players.local().inCombat();
    }

    @Override
    public void execute() {
        Tile currentTile = ctx.players.local().tile();
        Tile randomTile = new Tile((currentTile.x() + Random.nextInt(-50, 50)), (currentTile.y() + Random.nextInt(-50, 50)));
        Paint.status = "Running from combat (" + randomTile + ")";
        ctx.movement.findPath(randomTile).traverse();
    }
}
