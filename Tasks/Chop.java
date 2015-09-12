package crChop.Tasks;

import crChop.Task;
import crChop.Visual.Gui;
import crChop.Visual.Paint;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

import java.util.concurrent.Callable;

/**
 * Created by Dakota on 9/7/2015.
 */
public class Chop extends Task<ClientContext> {
    public Chop(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return ctx.inventory.select().count() < 28
                && !ctx.objects.select().name(Gui.selectedTree).isEmpty()
                && !ctx.players.local().inCombat() //TODO: Running from combat to bank temporary fix
                && ctx.players.local().animation() == -1;
    }

    @Override
    public void execute() {
        GameObject tree = ctx.objects.nearest().poll();
        ctx.camera.turnTo(tree);
        if (!tree.inViewport()) {
            Paint.status = "Going to " + tree.name();
            if (ctx.movement.step(tree)) {
                ctx.camera.turnTo(tree);
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return ctx.movement.destination().distanceTo(ctx.players.local()) < 10 || ctx.players.local().tile().distanceTo(tree) < 10;
                    }
                }, 1000, 10);
                if (ctx.players.local().tile().distanceTo(tree) < 10 && !tree.inViewport()) {
                    ctx.camera.pitch(Random.nextInt(0, 15));
                }
            }
            ctx.movement.step(tree);
        }

        if (tree.interact("Chop down")) {
            Paint.status = "Chopping " + tree.name();
            if (ctx.camera.pitch() <= 15) {
                ctx.camera.pitch(Random.nextInt(25, 75));
            }
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.players.local().animation() != -1;
                }
            }, 300, 10);
        }
    }
}
