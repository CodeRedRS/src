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
    public List<Task> taskList = new ArrayList<>();
    public boolean started;
    public int axeId, startLevel, startExperience;

    public StartUp(ClientContext ctx, int axeId, int startLevel, int startExperience) {
        super(ctx);
        this.axeId = axeId;
        this.startLevel = startLevel;
        this.startExperience = startExperience;
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
                axeId = i.id();
            }
        }

        startLevel = ctx.skills.realLevel(Constants.SKILLS_WOODCUTTING);
        startExperience = ctx.skills.experience(Constants.SKILLS_WOODCUTTING);

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
