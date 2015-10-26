package codered.crPest.PestTask;

import codered.crPest.PestUtil.PestMethods;
import codered.crPest.PestUtil.PestVariables;
import codered.crPest.PestUtil.PestWidgets;
import codered.universal.Task;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

/**
 * Created by Dakota on 10/16/2015.
 */
public class GameStarted extends Task<ClientContext> {
    public GameStarted(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return PestWidgets.damageDealt.visible()
                && PestVariables.voidKnightTile == null;
    }

    @Override
    public void execute() {
        Tile t = ctx.players.local().tile();
        ctx.movement.step(new Tile(t.x(), t.y() + Random.nextInt(-20, -30)));
    }
}
