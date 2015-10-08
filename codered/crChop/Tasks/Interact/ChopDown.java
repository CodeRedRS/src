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
    private Tree tree;
    private int axe;
    private Tile[] area;
    private GameObject treeObject;
    private boolean avoidCombat;

    public ChopDown(ClientContext ctx, Tree tree, int axe, Tile[] area, boolean avoidCombat) {
        super(ctx);
        this.tree = tree;
        this.axe = axe;
        this.area = area;
        this.avoidCombat = avoidCombat;
    }

    @Override
    public boolean activate() {
        if (avoidCombat) {
            return ctx.inventory.select().count() < 28 &&
                    !ctx.players.local().inCombat() &&
                    ctx.players.local().animation() == -1 &&
                    !ctx.objects.select().name(tree.getName()).isEmpty() &&
                    (ctx.equipment.itemAt(Equipment.Slot.MAIN_HAND).id() == axe || ctx.inventory.id(axe).count() > 0) ||
                    (area != null && new Area(area).contains(ctx.players.local()));
        }
        return ctx.inventory.select().count() < 28 &&
                ctx.players.local().animation() == -1 &&
                !ctx.objects.select().name(tree.getName()).isEmpty() &&
                (ctx.equipment.itemAt(Equipment.Slot.MAIN_HAND).id() == axe || ctx.inventory.id(axe).count() > 0) ||
                (area != null && new Area(area).contains(ctx.players.local()));
    }

    @Override
    public void execute() {
        System.out.println("ChopDown");

        if (this.area != null) {
            treeObject = ctx.objects.select().name(tree.getName()).each(Interactive.doSetBounds(tree.getBounds())).within(new Area(area)).nearest().poll();
        } else {
            treeObject = ctx.objects.select().name(tree.getName()).each(Interactive.doSetBounds(tree.getBounds())).nearest().poll();
        }

        if (!treeObject.inViewport()) {
            if (ctx.movement.step(treeObject)) {
                Paint.paintStatus("Walking to " + tree.getName());
                if (!treeObject.inViewport()) {
                    Paint.paintStatus("Looking for " + tree.getName());
                    ctx.camera.turnTo(treeObject);
                    if (!treeObject.inViewport()) {
                        Paint.paintStatus("Adjusting camera");
                        ctx.camera.pitch(Random.nextInt(25, 50));
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
            if (treeObject.interact("Chop down", treeObject.name())) {
                Paint.paintStatus("Chopping " + treeObject.name());
                if (ctx.camera.pitch() <= 50) {
                    Paint.paintStatus("Fixing camera pitch");
                    ctx.camera.pitch(Random.nextInt(50, 75));
                }
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return !ctx.players.local().inMotion() || !ctx.players.local().inCombat();
                    }
                }, 1000, 10);
            }
        }
    }
}
