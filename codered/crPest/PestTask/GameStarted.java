package codered.crPest.PestTask;

import codered.crPest.PestUtil.PestMethods;
import codered.crPest.PestUtil.PestVariables;
import codered.universal.Task;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

/**
 * Created by Dakota on 2/6/2016.
 */
public class GameStarted extends Task<ClientContext> {
    public GameStarted(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return PestVariables.inGame
                && !PestVariables.gameStarted;
    }

    @Override
    public void execute() {
        Tile t = ctx.players.local().tile();
        ctx.movement.step(new Tile(t.x(), t.y() + Random.nextInt(-20, -30)));
        PestMethods.getPortalTiles(ctx);

        if (PestVariables.voidKnightTile != null) {
            PestVariables.gameStarted = true;
        }
    }
}
