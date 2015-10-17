package codered.crPest.PestTask;

import codered.crPest.PestUtil.PestVariables;
import codered.crPest.PestUtil.PestWidgets;
import codered.universal.Task;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Npc;

/**
 * Created by Dakota on 10/16/2015.
 */
public class ToVoidKnight extends Task<ClientContext> {

    Npc voidKnight;

    public ToVoidKnight(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return PestWidgets.damageDealt.visible();
//        return true;
    }

    @Override
    public void execute() {
        System.out.println("ToVoidKnight");
        voidKnight = ctx.npcs.select().name("Void Knight").nearest().poll();

        ctx.movement.step(voidKnight);

        PestVariables.boarded = false;
    }
}
