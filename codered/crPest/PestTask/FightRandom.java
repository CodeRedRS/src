package codered.crPest.PestTask;

import codered.crPest.PestUtil.PestConstants;
import codered.crPest.PestUtil.PestMethods;
import codered.crPest.PestUtil.PestVariables;
import codered.universal.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Npc;

import java.util.concurrent.Callable;

/**
 * Created by Dakota on 2/5/2016.
 */
public class FightRandom extends Task<ClientContext> {
    private Npc enemy;
    private boolean shuffledPest = false;
    public FightRandom(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return PestVariables.inGame
                && PestVariables.gameStarted
                && !ctx.npcs.select().name(PestConstants.pestNames).isEmpty()
                && (ctx.players.local().interacting().name().isEmpty() || ctx.players.local().interacting().name() == null);
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
            if (ctx.npcs.size() > 1 && !shuffledPest) {
                enemy = ctx.npcs.shuffle().poll();
                shuffledPest = true;
            }
            if (enemy.interact("Attack", enemy.name())) {
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        shuffledPest = false;
                        return PestMethods.notFighting(ctx, enemy);
                    }
                }, 10, 10);
            }
        }
    }
}
