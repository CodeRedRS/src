package crChop.Tasks;

import crChop.Task;
import crChop.Variables.Widget;
import crChop.Visual.Paint;
import org.powerbot.script.rt4.ClientContext;

/**
 * Created by Dakota on 9/8/2015.
 */
public class Inventory extends Task<ClientContext> {
    Widget widget = new Widget();

    public Inventory(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return !widget.inventoryWidget.visible()
                && !ctx.bank.opened();
    }

    @Override
    public void execute() {
        Paint.status = "Opening inventory";

        widget.inventoryButtonWidget.click();
    }
}
