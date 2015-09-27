package codered.crChop.Tasks;

import codered.crChop.Enums.Tree;
import codered.crChop.Visual.Paint;
import codered.universal.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Equipment;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.TilePath;

import java.util.concurrent.Callable;

/**
 * Created by Dakota on 9/7/2015.
 */
public class Chop extends Task<ClientContext> {
    private TilePath path;
    private Tree tree;
    private int axeId;
    private boolean avoidCombat;

    public Chop(ClientContext ctx, Tree tree, int axeId, Boolean avoidCombat) {
        super(ctx);
        this.tree = tree;
        this.axeId = axeId;
        this.avoidCombat = avoidCombat;
    }

    @Override
    public boolean activate() {
        if (avoidCombat) {
            return ctx.inventory.select().count() < 28
                    && !ctx.players.local().inCombat()
                    && !ctx.objects.select().name(tree.getName()).isEmpty()
                    && ctx.players.local().animation() == -1
                    && (ctx.inventory.id(axeId).count() == 1 || ctx.equipment.itemAt(Equipment.Slot.MAIN_HAND) != null);
        }
//        if (gui.getPreset() >= 0) {
//            System.out.println("PRESET");
//            return ctx.inventory.select().count() < 28
//                    && ctx.players.local().animation() == -1
//                    && (ctx.inventory.id(axeId).count() == 1 || ctx.equipment.itemAt(Equipment.Slot.MAIN_HAND) != null);
//        }
        return ctx.inventory.select().count() < 28
                && !ctx.objects.select().name(tree.getName()).isEmpty()
                && ctx.players.local().animation() == -1
                && (ctx.inventory.id(axeId).count() == 1 || ctx.equipment.itemAt(Equipment.Slot.MAIN_HAND) != null);
    }

    @Override
    public void execute() {
        final GameObject tree = ctx.objects.nearest().poll();

        if (ctx.bank.opened()) {
            if (ctx.inventory.id(this.axeId).count() < 1) {
                ctx.bank.withdraw(this.axeId, 1);
            } else {
                ctx.movement.step(tree);
            }
        }

        if (!tree.inViewport() && !ctx.bank.opened()) {
            ctx.camera.pitch(Random.nextInt(0, 15));
            ctx.camera.turnTo(tree);
            Paint.paintStatus("Going to " + tree.name());
            if (!tree.inViewport() || ctx.players.local().tile().distanceTo(tree) > 15) {
                if (ctx.movement.step(tree)) {
                    ctx.camera.turnTo(tree);
                    if (ctx.players.local().inMotion()) {
                        Condition.wait(new Callable<Boolean>() {
                            @Override
                            public Boolean call() throws Exception {
                                return (ctx.movement.destination().distanceTo(ctx.players.local()) < 10 || ctx.players.local().tile().distanceTo(tree) < 10);
                            }
                        }, 250, 10);
                    }
                }
            }
        }

        if (tree.interact(false, "Chop down") && !ctx.bank.opened() && ctx.players.local().tile().distanceTo(tree) < 15) {
            Paint.paintStatus("Chopping " + tree.name());
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
