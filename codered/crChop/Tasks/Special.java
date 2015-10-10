package codered.crChop.Tasks;

import codered.crChop.Variables.Widget;
import codered.crChop.Visual.Paint;
import codered.universal.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Equipment;

import java.util.concurrent.Callable;

/**
 * Created by Dakota on 10/9/2015.
 */
public class Special extends Task<ClientContext> {
    public Special(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return (ctx.equipment.itemAt(Equipment.Slot.MAIN_HAND).name().toLowerCase().equals("dragon axe") || ctx.equipment.itemAt(Equipment.Slot.MAIN_HAND).name().toLowerCase().equals("infernal axe")) &&
                ctx.varpbits.varpbit(300) == 1000 &&
                ctx.varpbits.varpbit(301) != 1 &&
                ctx.players.local().animation() != -1;
    }

    @Override
    public void execute() {
        Paint.paintStatus("Activating Special");
        Widget.combatButtonWidget.click();
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return Widget.specialAttackButtonWidget.visible();
            }
        }, 100, 10);

        if (Widget.specialAttackButtonWidget.visible()) {
            Widget.specialAttackButtonWidget.click();
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.varpbits.varpbit(300) != 1000;
                }
            }, 100, 10);
        }
    }
}
