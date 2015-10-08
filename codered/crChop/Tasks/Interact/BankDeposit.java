package codered.crChop.Tasks.Interact;

import codered.crChop.Enums.Tree;
import codered.crChop.Visual.Paint;
import codered.universal.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;

import java.util.concurrent.Callable;

/**
 * Created by Dakota on 10/4/2015.
 */
public class BankDeposit extends Task<ClientContext> {
    private Tree tree;
    private int axe;

    public BankDeposit(ClientContext ctx, Tree tree, int axe) {
        super(ctx);
        this.tree = tree;
        this.axe = axe;
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
                    return ctx.inventory.select().count() == 0;
                }
            }, 100, 10);
        }

        if (ctx.inventory.select().id(axe).count() != 1 && ctx.inventory.count() == 0) {
            Paint.paintStatus("Withdrawing axe");
            ctx.bank.withdraw(axe, 1);
        }
        if (ctx.inventory.select().id(axe).count() == 1 && ctx.inventory.count() == 1) {
            Paint.paintStatus("Closing bank");
            ctx.bank.close();
        }
    }
}
