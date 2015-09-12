package crChop.Tasks;

import crChop.Task;
import crChop.Variables.Player;
import crChop.Variables.Widget;
import crChop.Visual.Gui;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dakota on 9/8/2015.
 */
public class StartUp extends Task<ClientContext> {
    public StartUp(ClientContext ctx) {
        super(ctx);
    }
    Gui gui;

    public static List<Task> taskList = new ArrayList<>();
    public boolean started;

    @Override
    public boolean activate() {
        return !started && ctx.game.loggedIn();
    }

    @Override
    public void execute() {
         gui = new Gui(ctx);

        // GET AXE ID
        for (Item i : ctx.inventory.items()) {
            if (i.name().toLowerCase().contains("axe")) {
                System.out.println(i.name() + "(" + i.id() + ")");
                Banking.axeId = i.id();
            }
        }

        Player.startLevel = ctx.skills.realLevel(Constants.SKILLS_WOODCUTTING);
        Player.startExperience = ctx.skills.experience(Constants.SKILLS_WOODCUTTING);
        if (!Widget.settingsWidget.visible()) {
            Widget.settingsButtonWidget.click();
            Widget.zoomWidget.interact("Restore Default Zoom");
        }
        ctx.camera.pitch(50);
        started = true;
    }

}
