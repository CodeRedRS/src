package codered.crChop.Tasks;

import codered.crChop.Visual.Paint;
import codered.universal.Task;
import org.powerbot.script.Random;
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
        return ctx.inventory.select().count() == 28;
    }

    @Override
    public void execute() {
        Paint.status = "Dropping logs";

        if (mousehop) {
            int dropStyle = Random.nextInt(1, 5);

            switch (dropStyle) {
                case 1:
                    for (Item i : ctx.inventory.items()) {
                        Point p = new Point(i.centerPoint().x + Random.nextInt(0, 10), i.centerPoint().y + Random.nextInt(0, 10));
                        ctx.input.hop(p);
                        ctx.input.click(false);
                        ctx.input.hop(ctx.input.getLocation().x, ctx.input.getLocation().y + 38);
                        ctx.input.click(true);
                    }
                    break;
                default:
                    Point p;
                    ctx.input.move(572 + Random.nextInt(0, 13), 224 + Random.nextInt(0, 11));
                    for (int rows = 0; rows < 4; rows++) {
                        for (int columns = 0; columns < 6; columns++) {
                            ctx.input.click(false);
                            p = ctx.input.getLocation();
                            ctx.input.hop(p.x, p.y + 38);
                            ctx.input.click(true);
                        }
                        if (rows != 3) {
                            p = ctx.input.getLocation();
                            ctx.input.hop(p.x + 43, p.y - 228);
                        }
                    }
                    break;
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
