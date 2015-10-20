package codered.crPest.PestTask;

import codered.crPest.PestUtil.PestConstants;
import codered.crPest.PestUtil.PestVariables;
import codered.universal.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.PaintListener;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Npc;

import java.awt.*;
import java.util.concurrent.Callable;

/**
 * Created by Dakota on 10/20/2015.
 */
public class DefendKnight extends Task<ClientContext> implements PaintListener {

    private Npc enemy;

    public DefendKnight(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return PestVariables.voidKnightTile != null
                && ctx.players.local().interacting().name().equals("");
    }

    @Override
    public void execute() {

        if (ctx.npcs.select().within(PestVariables.voidKnightTile, PestConstants.knightRadius).name(PestConstants.pestNames).isEmpty()) {
            enemy = ctx.npcs.select().within(PestVariables.voidKnightTile, PestConstants.knightRadius).action("Attack").poll();
        } else {
            enemy = ctx.npcs.select().within(PestVariables.voidKnightTile, PestConstants.knightRadius).name(PestConstants.pestNames).action("Attack").poll();
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
            }
        }
    }

    @Override
    public void repaint(Graphics g) {
        final Graphics2D g2 = (Graphics2D) g;

    }
}
