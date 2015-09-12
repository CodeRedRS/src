package crChop.Tasks;

import crChop.Task;
import crChop.Visual.Gui;
import crChop.Visual.Paint;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Item;

/**
 * Created by Dakota on 9/7/2015.
 */
public class Drop extends Task<ClientContext> {
    public Drop(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return ctx.inventory.select().count() == 28;
    }

    @Override
    public void execute() {
        Paint.status = "Dropping logs";

        for (Item i : ctx.inventory.items()) {
            if (i.name().toLowerCase().contains("logs")) {
                i.interact("Drop");
            }
        }
    }
}
