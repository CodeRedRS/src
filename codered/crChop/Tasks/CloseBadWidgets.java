package codered.crChop.Tasks;

import codered.crChop.Variables.Widget;
import codered.crChop.Visual.Paint;
import codered.universal.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;

import java.util.concurrent.Callable;

/**
 * Created by Dakota on 10/13/2015.
 */
public class CloseBadWidgets extends Task<ClientContext> {
    public CloseBadWidgets(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return Widget.collectionBoxCloseButtonWidget.visible() ||
                Widget.reportCloseButtonWidget.visible();
    }

    @Override
    public void execute() {
        Paint.paintStatus("Closing Interface");
        if (Widget.collectionBoxCloseButtonWidget.visible()) {
            if(Widget.collectionBoxCloseButtonWidget.click()) {
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return !Widget.collectionBoxCloseButtonWidget.visible();
                    }
                }, 100, 10);
            }
        } else if (Widget.reportCloseButtonWidget.visible()) {
            if (Widget.reportCloseButtonWidget.click()) {
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return !Widget.reportCloseButtonWidget.visible();
                    }
                }, 100, 10);
            }
        }
    }
}
