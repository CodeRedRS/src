package codered.crPest.PestTask;

import codered.crPest.PestUtil.PestConstants;
import codered.crPest.PestUtil.PestWidgets;
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
        return !ctx.npcs.select().name(PestConstants.pestNames).action("Attack").isEmpty()
                && ctx.players.local().interacting().name().equals("");
    }

    @Override
    public void execute() {
        if (!ctx.players.local().inMotion()) {
            enemy = ctx.npcs.nearest().poll();
        }

        if (PestWidgets.clickToContinue.visible()) {
            PestWidgets.clickToContinue.click();
        }

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
            if (enemy.tile().matrix(ctx).reachable()) {
                enemy = ctx.npcs.shuffle().poll();
                if (enemy.interact("Attack")) {
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return !ctx.players.local().interacting().name().equalsIgnoreCase(enemy.name())
                                    || !ctx.players.local().inCombat()
//                                    || !ctx.players.local().inMotion()
                                    || !enemy.inCombat();
                        }
                    }, 100, 10);
                }
            } else {
                door = ctx.objects.select().action("Open").nearest().poll();
                if (!door.inViewport()) {
                    if (ctx.movement.step(door)) {
                        Condition.wait(new Callable<Boolean>() {
                            @Override
                            public Boolean call() throws Exception {
                                return ctx.movement.destination().distanceTo(ctx.players.local()) < 5;
                            }
                        }, 100, 10);
                    }
                    if (!door.inViewport()) {
                        ctx.camera.turnTo(door, Random.nextInt(10, 20));
                    }
                } else {
                    if (door.interact("Open", door.name())) {
                        Condition.wait(new Callable<Boolean>() {
                            @Override
                            public Boolean call() throws Exception {
                                return !ctx.players.local().inMotion();
                            }
                        }, 100, 10);
                    }
                }
            }
        }
    }
}
