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
 * Created by Dakota on 2/6/2016.
 */
public class DefendKnight extends Task<ClientContext> {
    private Npc enemy;
    private boolean shuffledPest = false;

    public DefendKnight(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return PestVariables.inGame
                && PestVariables.gameStarted
                && (ctx.players.local().interacting().name().isEmpty() || ctx.players.local().interacting().name() == null)
                && !ctx.npcs.select().name(PestConstants.pestNames).within(PestVariables.voidKnightTile, PestVariables.knightRadius).action("Attack").isEmpty();
    }

    @Override
    public void execute() {
        enemy = ctx.npcs.nearest().poll();
        PestVariables.target = enemy;

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
            if (!ctx.players.local().interacting().name().equalsIgnoreCase(enemy.name()) && (enemy.health() > 0 || enemy.health() == -1)) {
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
}
