package codered.crChop.Tasks.Interact;

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
import org.powerbot.script.rt4.Interactive;

import java.util.concurrent.Callable;

/**
 * Created by Dakota on 10/4/2015.
 */
public class ChopDown extends Task<ClientContext> {
    public ChopDown(ClientContext ctx, Tree tree, int axe, Tile[] area, boolean avoidCombat) {
        super(ctx);
        this.tree = tree;
        this.axe = axe;
        this.area = area;
        this.avoidCombat = avoidCombat;
    }

    private Tree tree;
    private int axe;
    private Tile[] area;
    private GameObject treeObject;
    private boolean avoidCombat;

    @Override
    public boolean activate() {
        if (avoidCombat) {
            return ctx.inventory.select().count() < 28 &&
                    !ctx.objects.select().name(tree.getName()).isEmpty() &&
                    ctx.players.local().animation() == -1 &&
                    !ctx.bank.opened() &&
                    (ctx.equipment.itemAt(Equipment.Slot.MAIN_HAND).id() == axe || ctx.inventory.id(axe).count() > 0) &&
                    !ctx.players.local().inCombat();
        }
        return ctx.inventory.select().count() < 28 &&
                !ctx.objects.select().name(tree.getName()).isEmpty() &&
                ctx.players.local().animation() == -1 &&
                !ctx.bank.opened() &&
                (ctx.equipment.itemAt(Equipment.Slot.MAIN_HAND).id() == axe || ctx.inventory.id(axe).count() > 0);
    }

    @Override
    public void execute() {
//        System.out.println("ChopDown - " + ctx.objects.nearest().poll().orientation() + " : " + ctx.players.local().orientation());

        if (this.area != null) {
            Area area = new Area(this.area);
            treeObject = ctx.objects.select().name(tree.getName()).each(Interactive.doSetBounds(tree.getBounds())).within(area).nearest().poll();

            if (!treeObject.inViewport() && area.contains(ctx.players.local())) {
                if (ctx.movement.step(treeObject)) {
                    Paint.paintStatus("Walking to " + tree.getName());
                    if (!treeObject.inViewport()) {
                        Paint.paintStatus("Looking for " + tree.getName());
                        ctx.camera.turnTo(treeObject);
                        if (!treeObject.inViewport()) {
                            Paint.paintStatus("Adjusting camera");
                            ctx.camera.pitch(Random.nextInt(50, 75));
                        }
                    }
                    if (ctx.players.local().inMotion()) {
                        Condition.wait(new Callable<Boolean>() {
                            @Override
                            public Boolean call() throws Exception {
                                return (ctx.movement.destination().distanceTo(ctx.players.local()) < 10 || ctx.players.local().tile().distanceTo(treeObject) < 10);
                            }
                        }, 250, 10);
                    }
                }
            }

            if (treeObject.inViewport() && area.contains(ctx.players.local())) {
                if (treeObject.interact(false, "Chop down", treeObject.name())) {
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return ctx.players.local().animation() == -1;
                        }
                    }, 250, 10);
                    Paint.paintStatus("Chopping " + treeObject.name());
                    if (ctx.camera.pitch() <= 50) {
                        ctx.camera.pitch(Random.nextInt(50, 75));
                    }
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return !ctx.players.local().inMotion() && !ctx.players.local().inCombat();
                        }
                    }, 1000, 10);
                }
            }
        } else {
            treeObject = ctx.objects.select().name(tree.getName()).each(Interactive.doSetBounds(tree.getBounds())).nearest().poll();

            if (!treeObject.inViewport()) {
                if (ctx.movement.step(treeObject)) {
                    Paint.paintStatus("Walking to " + tree.getName());
                    if (!treeObject.inViewport()) {
                        Paint.paintStatus("Looking for " + tree.getName());
                        ctx.camera.turnTo(treeObject);
                        if (!treeObject.inViewport()) {
                            Paint.paintStatus("Adjusting camera");
                            ctx.camera.pitch(Random.nextInt(50, 75));
                        }
                    }
                    if (ctx.players.local().inMotion()) {
                        Condition.wait(new Callable<Boolean>() {
                            @Override
                            public Boolean call() throws Exception {
                                return (ctx.movement.destination().distanceTo(ctx.players.local()) < 10 || ctx.players.local().tile().distanceTo(treeObject) < 10);
                            }
                        }, 250, 10);
                    }
                }
            }

            if (treeObject.inViewport()) {
                if (treeObject.interact(false, "Chop down", treeObject.name())) {
//                Condition.wait(new Callable<Boolean>() {
//                    @Override
//                    public Boolean call() throws Exception {
//                        return ctx.players.local().animation() != -1;
//                    }
//                }, 250, 10);
                    Paint.paintStatus("Chopping " + treeObject.name());
                    if (ctx.camera.pitch() <= 50) {
                        ctx.camera.pitch(Random.nextInt(50, 75));
                    }
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return !ctx.players.local().inMotion() && !ctx.players.local().inCombat();
                        }
                    }, 1000, 10);
                }
            }
        }
    }
}
