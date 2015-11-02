package codered.crPest.PestUtil;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Component;

/**
 * Created by Dakota on 10/16/2015.
 */
public class PestWidgets {

    public static Component damageDealt;
    public static Component pestPoints;
    public static Component clickToContinue;
    public static Component gameOver;

    public static void initiateWidgets(ClientContext ctx) {
        damageDealt = ctx.widgets.widget(408).component(4);
        pestPoints = ctx.widgets.widget(407).component(15);
        clickToContinue = ctx.widgets.widget(231).component(2);
        gameOver = ctx.widgets.widget(231).component(3);
    }
}
