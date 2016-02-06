package codered.crPestDeprecated.PestTask;

import codered.crPest.PestUtil.PestVariables;
import codered.universal.Task;
import org.powerbot.script.rt4.ClientContext;

/**
 * Created by Dakota on 11/1/2015.
 */
public class ReturnToKnight extends Task<ClientContext> {
    public ReturnToKnight(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return ctx.objects.select().name("Gangplank").action("Cross").isEmpty()
                && (ctx.players.local().tile().distanceTo(PestVariables.voidKnightTile) >= PestVariables.knightRadius || (ctx.movement.destination().distanceTo(PestVariables.voidKnightTile) >= PestVariables.knightRadius && ctx.movement.destination().distanceTo(PestVariables.voidKnightTile) != Double.POSITIVE_INFINITY));
    }

    @Override
    public void execute() {
        System.out.println("ReturnToKnight");
        if (PestVariables.voidKnightTile != null)
            ctx.movement.step(PestVariables.voidKnightTile);
    }
}
