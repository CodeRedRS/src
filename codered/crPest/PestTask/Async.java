package codered.crPest.PestTask;

import codered.crPest.PestUtil.PestVariables;
import codered.crPest.PestUtil.PestWidgets;
import org.powerbot.script.rt4.ClientContext;

/**
 * Created by Dakota on 2/7/2016.
 */
public class Async implements Runnable {
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    private final ClientContext ctx;
    public Async(ClientContext ctx) {
        this.ctx = ctx;
    }
    @Override
    public void run() {
        PestVariables.inGame = ctx.widgets.component(408, 5).valid();
        PestVariables.boarded = ctx.widgets.component(407, 15).valid();
        if (ctx.widgets.component(407, 15).valid()) {
            PestVariables.totalPestPoints = Integer.parseInt(PestWidgets.pestPoints.text().split(" ")[2].trim());
            if (PestVariables.startPestPoints == 0) {
                PestVariables.startPestPoints = Integer.parseInt(PestWidgets.pestPoints.text().split(" ")[2].trim());
            } else {
                PestVariables.gainedPestPoints = (Integer.parseInt(PestWidgets.pestPoints.text().split(" ")[2].trim())) - PestVariables.startPestPoints;
            }
        }
    }
}
