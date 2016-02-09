package codered.crPest.PestTask;

import codered.crPest.PestUtil.PestVariables;
import codered.universal.Task;
import org.powerbot.script.rt4.ClientContext;

/**
 * Created by Dakota on 2/6/2016.
 */
public class ReturnToKnight extends Task<ClientContext> {
    public ReturnToKnight(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return PestVariables.inGame
                && PestVariables.voidKnightTile != null
                && outsideArea();
    }

    @Override
    public void execute() {
        if (PestVariables.inGame) {
            ctx.movement.step(PestVariables.voidKnightTile);
        }
    }

    private boolean outsideArea() {
        return (int)ctx.players.local().tile().distanceTo(PestVariables.voidKnightTile) >= PestVariables.knightRadius
                && (int)ctx.players.local().tile().distanceTo(PestVariables.voidKnightTile) < (PestVariables.knightRadius * 10)
                && (int)ctx.movement.destination().distanceTo(PestVariables.voidKnightTile) >= PestVariables.knightRadius;
    }
}
