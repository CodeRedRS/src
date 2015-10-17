package codered.crChop.Tasks;

import codered.crChop.Variables.Widget;
import codered.crChop.crChop;
import codered.universal.Task;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;

/**
 * Created by Dakota on 10/11/2015.
 */
public class GoalStop extends Task<ClientContext> {
    private int goalLevel, goalLogs;
    private long goalTime;

    public GoalStop(ClientContext ctx, int goalLevel, long goalTime, int goalLogs) {
        super(ctx);
        this.goalLevel = goalLevel;
        this.goalTime = goalTime;
        this.goalLogs = goalLogs;
    }

    @Override
    public boolean activate() {
        return (ctx.skills.realLevel(Constants.SKILLS_WOODCUTTING) == goalLevel && goalLevel != -1) ||
                (ctx.controller.script().getTotalRuntime() >= goalTime && goalTime != -1) ||
                (crChop.logs >= goalLogs && goalLogs != -1);
    }

    @Override
    public void execute() {
        crChop.taskList.clear();
        Widget.logoutButton.click();
        if (Widget.logoutButton2.click())
            ctx.controller.stop();
    }
}
