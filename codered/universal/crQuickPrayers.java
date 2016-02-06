package codered.universal;

import codered.crPest.PestUtil.PestVariables;
import org.powerbot.script.rt4.ClientContext;

/**
 * Created by Dakota on 11/8/2015.
 */
public class crQuickPrayers extends Task<ClientContext> {
    public crQuickPrayers(ClientContext ctx) {
        super(ctx);
    }

    public boolean activate() {
        return crVariables.quickPrayers
                && ctx.prayer.prayerPoints() > 0
                && PestVariables.inGame;
    }

    @Override
    public void execute() {
        System.out.println("Quick Prayers");
        if (!ctx.players.local().interacting().name().isEmpty()) {
            System.out.println("Enabling Quick Prayers");
            ctx.prayer.quickPrayer(true);
        } else {
            System.out.println("Enabling Quick Prayers");
            ctx.prayer.quickPrayer(false);
        }
    }
}
