package crChop.Tasks;

import crChop.Task;
import crChop.Visual.Paint;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

import java.util.concurrent.Callable;

/**
 * Created by Dakota on 9/8/2015.
 */
public class Banking extends Task<ClientContext> {
    private final String method;
    private final int axeId;

    public Banking(ClientContext ctx, String method, int axeId) {
        super(ctx);
        this.method = method;
        this.axeId = axeId;
    }

    public boolean activate() {
        return ctx.inventory.count() == 28
                || ctx.players.local().inCombat(); //TODO: Running from combat to bank temporary fix
    }


    @Override
    public void execute() {
        String bankName = method.split("\\s:\\s")[1];
        GameObject bank = ctx.objects.select().name(bankName).poll();
        if (!bank.inViewport()) {
            Paint.status = "Waking to bank : " + bankName;
            if (ctx.movement.step(bank)) {
                ctx.camera.turnTo(bank);
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return ctx.movement.destination().distanceTo(ctx.players.local()) < 10 || ctx.players.local().tile().distanceTo(bank) < 10;
                    }
                }, 1000, 10);
            }
            ctx.movement.step(bank);
        }

        if (ctx.bank.opened()) {
            if (ctx.inventory.count() > 1) {
                ctx.bank.depositInventory();
            }
        } else {
            if (bank.interact("Bank")) {
                Paint.status = "Banking";
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return ctx.bank.opened();
                    }
                }, 300, 10);
            }
        }
        ctx.bank.withdraw(this.axeId, 1);
    }
}
