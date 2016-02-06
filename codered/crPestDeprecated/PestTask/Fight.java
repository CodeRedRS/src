package codered.crPestDeprecated.PestTask;

import codered.crPest.PestUtil.PestVariables;
import codered.universal.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Npc;

import java.util.concurrent.Callable;

/**
 * Created by Dakota on 10/16/2015.
 */
public class Fight extends Task<ClientContext> {

    Npc enemy;
    GameObject door;

    public Fight(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return (ctx.players.local().interacting().name().isEmpty() || ctx.players.local().interacting().name() == null)
                && !ctx.npcs.select().name(PestVariables.enemyNames).action("Attack").isEmpty();
    }

    @Override
    public void execute() {
        System.out.println("Fight");
        enemy = ctx.npcs.nearest().poll();
        if (!enemy.inViewport()) {
            if (ctx.movement.step(enemy)) {
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return ctx.movement.destination().distanceTo(ctx.players.local()) < 5;
                    }
                }, 100, 10);
                if (!enemy.inViewport()) {
                    ctx.camera.turnTo(enemy, Random.nextInt(10, 20));
                    ctx.camera.pitch(Random.nextInt(25, 35));
                }
            }
        } else {
            if (ctx.npcs.size() > 1) {
                enemy = ctx.npcs.shuffle().poll();
            }
            if (enemy.interact("Attack", enemy.name())) {
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return ctx.players.local().interacting().name().isEmpty()
                                || ctx.players.local().interacting().name() == null
                                || !ctx.players.local().inCombat()
                                && enemy.health() <= 0;
                    }
                }, 10, 10);
            }

        }
    }
}

