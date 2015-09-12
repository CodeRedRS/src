package crChop;

import crChop.Tasks.StartUp;
import crChop.Variables.Widget;
import crChop.Visual.CursorPaint;
import crChop.Visual.Gui;
import crChop.Visual.Paint;
import org.powerbot.script.*;
import org.powerbot.script.rt4.ClientContext;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Dakota on 9/7/2015.
 */
@Script.Manifest(
        name = "crChop",
        description = "AIO Woodcutter"
)
public class crChop extends PollingScript<ClientContext> implements PaintListener, MessageListener {
    Paint paint = new Paint(ctx);
    CursorPaint cursor = new CursorPaint(ctx);

    @Override
    public void start() {
        StartUp.taskList.add(new StartUp(ctx));
        Widget.initiateWidgets(ctx);
    }

    @Override
    public void stop() {
        System.out.println("Run time: " + ctx.controller.script().getTotalRuntime());
    }

    @Override
    public void poll() {
        for (Task t : StartUp.taskList) {
            if (t.activate()) {
                t.execute();
            }
        }
    }

    @Override
    public void repaint(Graphics g) {
        cursor.drawMouse(g);
        if (ctx.game.loggedIn()) {
            paint.repaint(g);
        } else {
            Paint.status = "Logging in";
        }
    }

    @Override
    public void messaged(MessageEvent messageEvent) {
        String msg = messageEvent.text();

        if (msg.contains("You get some " + Gui.selectedTree.toLowerCase())) {
            Paint.logs++;
        }
    }
}
