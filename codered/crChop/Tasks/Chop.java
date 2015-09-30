package codered.crChop.Tasks;

import codered.crChop.Enums.Tree;
import codered.crChop.Visual.Paint;
import codered.universal.Task;
import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Equipment;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.TilePath;

import java.util.concurrent.Callable;

/**
 * Created by Dakota on 9/7/2015.
 */
public class Chop extends Task<ClientContext> {
    private Tile[] path;
    private Area area;
    private Tree tree;
    private int axeId;
    private boolean avoidCombat;

    public Chop(ClientContext ctx, Tree tree, int axeId, Boolean avoidCombat, Tile[] path, Area area) {
        super(ctx);
        this.tree = tree;
        this.axeId = axeId;
        this.avoidCombat = avoidCombat;
        this.path = path;
        this.area = area;
    }

    public boolean activate() {
        if (avoidCombat) {
            return ctx.inventory.select().count() < 28
                    && !ctx.players.local().inCombat()
                    && ctx.players.local().animation() == -1
                    && (ctx.inventory.id(axeId).count() == 1 || ctx.equipment.itemAt(Equipment.Slot.MAIN_HAND) != null);
        }
        return ctx.inventory.select().count() < 28
                && ctx.players.local().animation() == -1
                && (ctx.inventory.id(axeId).count() == 1 || ctx.equipment.itemAt(Equipment.Slot.MAIN_HAND) != null);
    }

    @Override
    public void execute() {
        final GameObject t;
        final TilePath p;
        if (area != null) {
             t = ctx.objects.select().name(tree.getName()).within(area).action("Chop down").nearest().poll();
        } else {
            t =ctx.objects.select().name(tree.getName()).action("Chop down").nearest().poll();
        }

        if (ctx.bank.opened()) {
            if (ctx.inventory.id(this.axeId).count() < 1) {
                ctx.bank.withdraw(this.axeId, 1);
            } else {
                ctx.movement.step(t);
            }
        }

        if (path == null) {
            if (!t.inViewport() && !ctx.bank.opened()) {
                ctx.camera.pitch(Random.nextInt(0, 15));
                ctx.camera.turnTo(t);
                Paint.paintStatus("Going to " + t.name());
                if (!t.inViewport() || ctx.players.local().tile().distanceTo(t) > 15) {
                    if (ctx.movement.step(t)) {
                        ctx.camera.turnTo(t);
                        if (ctx.players.local().inMotion()) {
                            Condition.wait(new Callable<Boolean>() {
                                @Override
                                public Boolean call() throws Exception {
                                    return (ctx.movement.destination().distanceTo(ctx.players.local()) < 10 || ctx.players.local().tile().distanceTo(t) < 10);
                                }
                            }, 250, 10);
                        }
                    }
                }
            }
        } else {
            p = ctx.movement.newTilePath(path).reverse();
            if (!area.contains(ctx.players.local())) {
                if (p.end().matrix(ctx).reachable()) {
                    p.traverse();
                } else {
                    System.out.println("Not reachable");
                }
            }
        }


        if (area == null) {
            if (t.interact(false, "Chop down") && !ctx.bank.opened() && ctx.players.local().tile().distanceTo(t) < 15) {
                Paint.paintStatus("Chopping " + t.name());
                if (ctx.camera.pitch() <= 15) {
                    ctx.camera.pitch(Random.nextInt(25, 75));
                }
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return !ctx.players.local().inMotion();
                    }
                }, 1000, 10);
            }
        } else if (area.contains(ctx.players.local())) {
            final GameObject presetTree = ctx.objects.select().name(tree.getName()).within(area).nearest().poll();
            if (area.contains(presetTree)) {
                if (!presetTree.inViewport()) {
                    ctx.camera.pitch(Random.nextInt(0, 15));
                    ctx.camera.turnTo(presetTree);
                }
                if (presetTree.interact(false, "Chop down")) {
                    Paint.paintStatus("Chopping " + presetTree.name());
                    if (ctx.camera.pitch() <= 15) {
                        ctx.camera.pitch(Random.nextInt(25, 75));
                    }
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return !ctx.players.local().inMotion();
                        }
                    }, 1000, 10);
                }
            }

        }

    }
}
