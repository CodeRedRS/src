package crChop.Tasks;

import crChop.Task;
import crChop.Variables.Variables;
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
    public boolean started;
    Variables variables = new Variables();

    public StartUp(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return !started && ctx.game.loggedIn();
    }

    @Override
    public void execute() {
        Gui gui = new Gui(ctx);
        Paint.status = "Setting up script";
        Widget.initiateWidgets(ctx);
        if (!Widget.inventoryWidget.visible()) {
            Widget.inventoryButtonWidget.click();
        }

        // GET AXE ID
        for (Item i : ctx.inventory.items()) {
            if (i.name().toLowerCase().contains("axe")) {
                System.out.println(i.name() + "(" + i.id() + ")");
                variables.setAxeId(i.id());
            }
        }

        variables.setStartExperience(ctx.skills.experience(Constants.SKILLS_WOODCUTTING));
        variables.setStartLevel(ctx.skills.realLevel(Constants.SKILLS_WOODCUTTING));

        if (!Widget.settingsWidget.visible()) {
            Widget.settingsButtonWidget.click();
            Widget.zoomWidget.interact("Restore Default Zoom");
            Widget.inventoryButtonWidget.click();
        }
        ctx.camera.pitch(50);
        gui.setVisible(true);
        started = true;
    }

}
