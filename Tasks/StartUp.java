package crChop.Tasks;

import crChop.Task;
import crChop.Variables.Widget;
import crChop.Visual.Gui;
import crChop.Visual.Paint;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dakota on 9/8/2015.
 */
public class StartUp extends Task<ClientContext> {
    public static List<Task> taskList = new ArrayList<>();
    public static int axeId, startLevel, startExperience;
    public boolean started;
    public StartUp(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return !started && ctx.game.loggedIn();
    }

    @Override
    public void execute() {
        Paint.status = "Setting up script";

        // GET AXE ID
        for (Item i : ctx.inventory.items()) {
            if (i.name().toLowerCase().contains("axe")) {
                System.out.println(i.name() + "(" + i.id() + ")");
                axeId = i.id();
            }
        }

        startLevel = ctx.skills.realLevel(Constants.SKILLS_WOODCUTTING);
        startExperience = ctx.skills.experience(Constants.SKILLS_WOODCUTTING);

        if (!Widget.settingsWidget.visible()) {
            Widget.settingsButtonWidget.click();
            Widget.zoomWidget.interact("Restore Default Zoom");
        }
        ctx.camera.pitch(50);
        Gui gui = new Gui(ctx);
        started = true;
    }

}
