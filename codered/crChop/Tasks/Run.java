package codered.crChop.Tasks;

import codered.crChop.Variables.Widget;
import codered.universal.Task;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;


/**
 * Created by Dakota on 9/10/2015.
 */
public class Run extends Task<ClientContext> {
    Widget widget = new Widget();

    public Run(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return Integer.parseInt(ctx.widgets.widget(160).component(23).text()) > Random.nextInt(25, 35) && Widget.runButtonStateWidget.textureId() == 1064;
//                && !ctx.bank.opened();
//                ctx.movement.energyLevel() > Random.nextInt(25, 35) ;
    }

    @Override
    public void execute() {
        Widget.runButtonWidget.click();
    }
}
