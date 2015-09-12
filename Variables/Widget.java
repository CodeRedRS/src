package crChop.Variables;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Component;

/**
 * Created by Dakota on 9/8/2015.
 */
public class Widget {

    public static Component inventoryWidget, inventoryButtonWidget, runButtonWidget, runButtonStateWidget,
            zoomWidget, settingsWidget, settingsButtonWidget;

    public static void initiateWidgets(ClientContext ctx) {
        inventoryWidget = ctx.widgets.widget(548).component(65);
        inventoryButtonWidget = ctx.widgets.widget(548).component(47);
        runButtonWidget = ctx.widgets.widget(160).component(22);
        runButtonStateWidget = ctx.widgets.widget(160).component(24);
        zoomWidget = ctx.widgets.widget(261).component(4);
        settingsWidget = ctx.widgets.widget(261).component(0);
        settingsButtonWidget = ctx.widgets.widget(548).component(38);
    }
}