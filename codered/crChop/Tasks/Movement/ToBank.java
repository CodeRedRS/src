package codered.crChop.Tasks.Movement;

import codered.crChop.Visual.Paint;
import codered.universal.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.TilePath;

import java.util.concurrent.Callable;

/**
 * Created by Dakota on 10/4/2015.
 */
public class ToBank extends Task<ClientContext> {
    public ToBank(ClientContext ctx, Tile[] path) {
        super(ctx);
        this.path = path;
    }

    private Tile[] path;

    @Override
    public boolean activate() {
        return ctx.inventory.select().count() == 28 &&
                !ctx.bank.opened();
    }

    @Override
    public void execute() {
        final TilePath p;
        boolean preset = this.path != null;
        GameObject bankObject = ctx.objects.select().action("Bank").nearest().poll();
        ctx.camera.turnTo(bankObject);
//        System.out.println("ToBank");
        Paint.paintStatus("Going to bank");
        if (preset) {
            Paint.paintStatus("Walking path to " + bankObject.name());
            p = ctx.movement.newTilePath(path);
            if (!bankObject.inViewport()) {
                if (p.next() == null) {
                    ctx.movement.step(p.start());
                } else if (p.next().matrix(ctx).reachable()) {
                    p.traverse();
                } else {
                    GameObject door = ctx.objects.select().action("Open", "Door").nearest().poll();
                    if (!door.inViewport()) {
                        door.interact(false, "Open", "Door");
                    }
                }
            } else if (bankObject.inViewport()) {
                if (bankObject.interact("Bank")) {
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return !ctx.players.local().inMotion();
                        }
                    }, 1000, 10);
                }
            }
        } else {
            if (!bankObject.inViewport()) {
                if (ctx.movement.step(bankObject)) {
                    Paint.paintStatus("Walking to bank");
                    if (!bankObject.inViewport()) {
                        Paint.paintStatus("Looking for bank");
                        ctx.camera.turnTo(bankObject);
                    }
                }
                Paint.paintStatus("Walking to nearest " + bankObject.name());
            } else {
                if (bankObject.interact("Bank")) {
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
