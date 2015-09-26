package codered.crChop.Tasks;

import codered.crChop.Visual.Paint;
import codered.universal.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

import java.util.concurrent.Callable;

/**
 * Created by Dakota on 9/8/2015.
 */
public class Banking extends Task<ClientContext> {
    private Tile bankTile;
    private final int axeId;

    public Banking(ClientContext ctx, Tile bankTile, int axeId) {
        super(ctx);
        this.bankTile = bankTile;
        this.axeId = axeId;
    }

    public boolean activate() {
        return ctx.inventory.count() == 28;
    }


    @Override
    public void execute() {
        final GameObject bank = ctx.objects.select().action("Bank").nearest().poll();
        if (!bank.inViewport()) {
            ctx.camera.pitch(Random.nextInt(0, 15));
            ctx.camera.turnTo(bank);
            Paint.status = "Waking to bank : " + bank.name();
            if (!bank.inViewport()) {
                if (ctx.movement.step(bankTile)) {
                    ctx.camera.turnTo(bank);
                    if (ctx.players.local().inMotion()) {
                        Condition.wait(new Callable<Boolean>() {
                            @Override
                            public Boolean call() throws Exception {
                                return (ctx.movement.destination().distanceTo(ctx.players.local()) < 10 || ctx.players.local().tile().distanceTo(bank) < 10);
                            }
                        }, 250, 10);
                    }
                }
            }
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
                        return !ctx.players.local().inMotion();
                    }
                }, 1000, 10);
            }
        }
        if (this.axeId > 1) {
            ctx.bank.withdraw(this.axeId, 1);
        }
    }
}