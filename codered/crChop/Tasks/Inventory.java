package codered.crChop.Tasks;

import codered.crChop.Variables.Widget;
import codered.crChop.Visual.Paint;
import codered.universal.Task;
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
