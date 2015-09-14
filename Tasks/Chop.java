package crChop.Tasks;

import crChop.Task;
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
    private String tree;

    public Chop(ClientContext ctx, String tree) {
        super(ctx);
        this.tree = tree;
    }

    @Override
    public boolean activate() {
        return ctx.inventory.select().count() < 28
                && !ctx.objects.select().name(tree).isEmpty()
                && !ctx.players.local().inCombat() //TODO: Running from combat to bank temporary fix
                && ctx.players.local().animation() == -1;
    }

    @Override
    public void execute() {
        GameObject tree = ctx.objects.nearest().poll();
        if (!tree.inViewport()) {
            ctx.camera.pitch(Random.nextInt(0, 15));
            ctx.camera.turnTo(tree);
            Paint.status = "Going to " + tree.name();
            if (ctx.movement.step(tree) && !tree.inViewport()) {
                ctx.camera.turnTo(tree);
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return ctx.movement.destination().distanceTo(ctx.players.local()) < 10 || ctx.players.local().tile().distanceTo(tree) < 10;
                    }
                }, 1000, 10);
            }
            ctx.movement.step(tree);
        }

        if (tree.interact(true, "Chop down")) {
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
