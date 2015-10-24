package codered.crPest.PestTask;

import codered.crPest.PestUtil.PestConstants;
import codered.crPest.PestUtil.PestVariables;
import codered.universal.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Npc;

import java.util.concurrent.Callable;

/**
 * Created by Dakota on 10/20/2015.
 */
public class DefendKnight extends Task<ClientContext> {

    private Npc enemy;

    public DefendKnight(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return PestVariables.voidKnightTile != null
                && (ctx.players.local().interacting().name().isEmpty() || ctx.players.local().interacting().name() == null);
    }

    @Override
    public void execute() {
//        if (!ctx.npcs.select().within(PestVariables.voidKnightTile, PestConstants.knightRadius).name(PestConstants.pestNames).action("Attack").isEmpty()) {
//            enemy = ctx.npcs.nearest().poll();
//        } else {
//            enemy = ctx.npcs.select().name(PestConstants.pestNames).action("Attack").nearest().poll();
//        }

        if (!ctx.npcs.select().within(PestVariables.voidKnightTile, PestConstants.knightRadius).name(PestConstants.pestNames).action("Attack").isEmpty()) {
            enemy = ctx.npcs.nearest().poll();
        }

//        TODO: Look into detecting what npcs are interacting with the knight and attack them
//        enemy = ctx.npcs.select().action("Attack").poll().interacting().name().equalsIgnoreCase("void knight");

        if (enemy != null) {
            // Open the gate because it's closer than the enemy
            final GameObject gate = ctx.objects.select().name("Gate").action("Open").nearest().poll();
            if (ctx.players.local().tile().distanceTo(gate) < ctx.players.local().tile().distanceTo(enemy) && enemy.tile().compareTo(PestVariables.voidKnightTile) > PestConstants.knightRadius) {
                if (!gate.inViewport()) {
                    if (ctx.movement.step(gate)) {
                        Condition.wait(new Callable<Boolean>() {
                            @Override
                            public Boolean call() throws Exception {
                                return ctx.movement.destination().distanceTo(ctx.players.local()) < 5;
                            }
                        }, 100, 10);
                        if (!gate.inViewport()) {
                            ctx.camera.turnTo(gate, Random.nextInt(10, 20));
                            ctx.camera.pitch(Random.nextInt(25, 35));
                        }
                    }
                } else {
                    if (gate.interact("Open", gate.name())) {
                        System.out.println("Opening: " + gate.name() + " " + gate.tile());
                        Condition.wait(new Callable<Boolean>() {
                            @Override
                            public Boolean call() throws Exception {
                                return ctx.objects.select().name("Gate").action("Close").nearest().poll().tile() == gate.tile();
                            }
                        }, 100, 10);
                    }
                }
                // Attack enemy because its closter than the gate
            } else {
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
                    enemy = ctx.npcs.limit(10).shuffle().poll();
                    if (enemy.interact("Attack", enemy.name())) {
//                        System.out.println("Target: " + enemy.name() + "(" + enemy.combatLevel() + ") " + " " + enemy.tile());
                        Condition.wait(new Callable<Boolean>() {
                            @Override
                            public Boolean call() throws Exception {
                                return ctx.players.local().interacting().name().isEmpty()
                                        || ctx.players.local().interacting().name() == null
                                        || !ctx.players.local().inCombat()
                                        && enemy.health() <= 0;
                            }
                        }, 10, 10);
                        enemy = null;
                    }
                }
            }
        }
    }
}

