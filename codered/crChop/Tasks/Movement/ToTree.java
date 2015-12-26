package codered.crChop.Tasks.Movement;

import codered.crChop.Enums.Tree;
import codered.crChop.Visual.Paint;
import codered.universal.Task;
import org.powerbot.script.Area;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.*;

/**
 * Created by Dakota on 10/4/2015.
 */
public class ToTree extends Task<ClientContext> {
    private Tile[] path, area;
    private Tree tree;
    private int axe, radius;
    private Tile interactive, startTile;

    public ToTree(ClientContext ctx, Tile[] path, Tile[] area, Tree tree, Tile interactive, int axe, int radius, Tile startTile) {
        super(ctx);
        this.path = path;
        this.area = area;
        this.tree = tree;
        this.interactive = interactive;
        this.axe = axe;
        this.radius = radius;
        this.startTile = startTile;
    }

    @Override
    public boolean activate() {
        if (area != null) {
            return ctx.inventory.select().count() < 28 &&
                    !ctx.bank.opened() &&
                    (ctx.equipment.itemAt(Equipment.Slot.MAIN_HAND).id() == axe || ctx.inventory.id(this.axe).count() == 1) &&
                    !ctx.objects.select().name(tree.getName()).within(new Area(this.area)).poll().inViewport() &&
                    ctx.players.local().animation() == -1 &&
                    !new Area(area).contains(ctx.players.local());
        }
        return ctx.inventory.select().count() < 28 &&
                !ctx.bank.opened() &&
                (ctx.equipment.itemAt(Equipment.Slot.MAIN_HAND).id() == axe || ctx.inventory.id(this.axe).count() == 1) &&
                !ctx.objects.select().name(tree.getName()).poll().inViewport() &&
                ctx.players.local().animation() == -1 &&
                !ctx.objects.isEmpty();
    }

    @Override
    public void execute() {
        boolean preset = this.path != null && this.area != null;

        if (preset) {
            TilePath p = ctx.movement.newTilePath(path).reverse();
            GameObject door = ctx.objects.select().name("Door").action("Open").nearest().poll();
            Paint.paintStatus("Walking path to " + tree.getName());
            Area a = new Area(this.area);
            if (!a.contains(ctx.players.local())) {
//                if (p.next() != null) {
                    if (this.interactive != null && !p.next().matrix(ctx).reachable()) {
                        ctx.movement.step(interactive);
                        if (door.inViewport()) {
                            door.interact(false, "Open", "Door");
                        }
                    } else {
                        p.traverse();
                    }
//                } else {
//                    System.out.println("ToTree: Re-randomizing");
//                    p.randomize(2, 2);
//                }
            }
        } else {
            final GameObject treeObject;
            if (radius != -1) {
                treeObject = ctx.objects.within(startTile, radius).each(Interactive.doSetBounds(tree.getBounds())).nearest().poll();
            } else {
                treeObject = ctx.objects.each(Interactive.doSetBounds(tree.getBounds())).nearest().poll();
            }
            if (!treeObject.inViewport()) {
                if (ctx.movement.step(treeObject)) {
                    Paint.paintStatus("Walking to " + tree.getName());
                    if (!treeObject.inViewport()) {
                        Paint.paintStatus("Looking for " + tree.getName());
                        ctx.camera.turnTo(treeObject, Random.nextInt(5, 10));
                    }
                }
            }
        }
    }
}
