package codered.crChop.Tasks;

import codered.crChop.Visual.Paint;
import codered.universal.Task;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Item;

import java.awt.*;

/**
 * Created by Dakota on 9/7/2015.
 */
public class Drop extends Task<ClientContext> {
    private boolean mousehop;

    public Drop(ClientContext ctx, boolean mousehop) {
        super(ctx);
        this.mousehop = mousehop;
    }

    @Override
    public boolean activate() {
        return ctx.inventory.select().count() == 28
                && !ctx.players.local().inCombat();
    }

    @Override
    public void execute() {
        Paint.status = "Dropping logs";

        if (mousehop) {
            for (Item i : ctx.inventory.items()) {
                if (i.name().toLowerCase().contains("logs")) {
                    ctx.input.hop(i.centerPoint());
                    ctx.input.click(false);
                    Point mouse = ctx.input.getLocation();
                    ctx.input.hop(mouse.x, mouse.y + 38);
                    ctx.input.click(true);
                }
            }
        } else {
            for (Item i : ctx.inventory.items()) {
                if (i.name().toLowerCase().contains("logs")) {
                    i.interact("Drop");
                }
            }
        }
    }
}
