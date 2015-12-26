package codered.crChop.Tasks.Interact;

import codered.crChop.Visual.Paint;
import codered.universal.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;

import java.util.concurrent.Callable;

/**
 * Created by Dakota on 10/4/2015.
 */
public class BankDeposit extends Task<ClientContext> {
    private int axe;
    private boolean axeEquiped;

    public BankDeposit(ClientContext ctx, int axe, boolean axeEquiped) {
        super(ctx);
        this.axe = axe;
        this.axeEquiped = axeEquiped;
    }

    @Override
    public boolean activate() {
        return ctx.bank.opened();
    }

    @Override
    public void execute() {
        if (ctx.inventory.select().count() > 1) {
            Paint.paintStatus("Depositing inventory");
            ctx.bank.depositInventory();
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.inventory.count() == 0;
                }
            }, 100, 10);
        }

        if (!axeEquiped) {
            if (ctx.inventory.id(axe).count() != 1) {
                Paint.paintStatus("Withdrawing axe");
                if (ctx.bank.withdraw(axe, 1)) {
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return ctx.inventory.id(axe).count() == 1;
                        }
                    }, 100, 10);
                }
                Paint.paintStatus("Closing bank");
                ctx.bank.close();
            }
        } else {
            Paint.paintStatus("Closing bank");
            ctx.bank.close();
        }
    }
}
