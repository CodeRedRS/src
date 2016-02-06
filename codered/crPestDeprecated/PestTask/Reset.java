package codered.crPestDeprecated.PestTask;

import codered.crPest.PestUtil.PestMethods;
import codered.crPest.PestUtil.PestVariables;
import codered.universal.Task;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

/**
 * Created by Dakota on 11/1/2015.
 */
//TODO: Redo this class
public class Reset extends Task<ClientContext> {
    public Reset(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return !PestVariables.boarded
                && !ctx.objects.select().name("Gangplank").action("Cross").isEmpty();
    }

    @Override
    public void execute() {
        PestMethods.resetGame();
        board();
    }

    private void board() {
        System.out.println("Reset");
        GameObject gangplank = ctx.objects.nearest().poll();
        for (int i = 0; i < 50; i++) {
            if (!PestVariables.boarded) {
                gangplank.interact("Cross");
            }
            i++;
        }
    }

}
