package codered.crChop.Tasks.Movement;

import codered.crChop.Enums.Tree;
import codered.crChop.Visual.Paint;
import codered.universal.Task;
import org.powerbot.script.Area;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Equipment;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.TilePath;

/**
 * Created by Dakota on 10/4/2015.
 */
public class ToTree extends Task<ClientContext> {
    private Tile[] path, area;
    private Tree tree;
    private int axe;
    private Tile interactive;

    public ToTree(ClientContext ctx, Tile[] path, Tile[] area, Tree tree, Tile interactive, int axe) {
        super(ctx);
        this.path = path;
        this.area = area;
        this.tree = tree;
        this.interactive = interactive;
        this.axe = axe;
    }

    @Override
    public boolean activate() {
        if (area != null) {
            return ctx.inventory.count() < 28 &&
                    !ctx.bank.opened() &&
                    (ctx.equipment.itemAt(Equipment.Slot.MAIN_HAND).id() == axe || ctx.inventory.id(this.axe).count() == 1) &&
                    !ctx.objects.select().name(tree.getName()).within(new Area(this.area)).poll().inViewport() &&
                    ctx.players.local().animation() == -1 &&
                    !new Area(area).contains(ctx.players.local());
        }
        return ctx.inventory.count() < 28 &&
                !ctx.bank.opened() &&
                (ctx.equipment.itemAt(Equipment.Slot.MAIN_HAND).id() == axe || ctx.inventory.id(this.axe).count() == 1) &&
                !ctx.objects.select().name(tree.getName()).poll().inViewport() &&
                ctx.players.local().animation() == -1 &&
                !ctx.objects.isEmpty();
    }

    @Override
    public void execute() {
        System.out.println("ToTree");
        final TilePath p;
        boolean preset = this.path != null && this.area != null;

        if (preset) {
            GameObject door = ctx.objects.select().name("Door").action("Open").nearest().poll();
            Paint.paintStatus("Walking path to " + tree.getName());
            p = ctx.movement.newTilePath(path).reverse();
            Area a = new Area(this.area);
            if (!a.contains(ctx.players.local())) {
                if (p.next() == null) {
                    ctx.movement.step(p.start());
                } else if (p.next().matrix(ctx).reachable()) {
                    p.traverse();
                } else if (this.interactive != null && !p.next().matrix(ctx).reachable()) {
                    ctx.movement.step(interactive);
                    if (door.inViewport()) {
                        door.interact(false, "Open", "Door");
                    }
                }
            }
        } else {
            final GameObject t = ctx.objects.nearest().poll();
            if (!t.inViewport()) {
                if (ctx.movement.step(t)) {
                    Paint.paintStatus("Walking to " + tree.getName());
                    if (!t.inViewport()) {
                        Paint.paintStatus("Looking for " + tree.getName());
                        ctx.camera.turnTo(t);
                    }
                }
            }
        }
    }
}
