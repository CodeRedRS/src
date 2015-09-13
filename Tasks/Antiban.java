package crChop.Tasks;

import crChop.Task;
import crChop.Visual.Paint;
import crChop.crChop;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Component;

/**
 * Created by Dakota on 9/11/2015.
 */
public class Antiban extends Task<ClientContext> {
    public Boolean antibanEnable = false;
    crChop crChop = new crChop();

    public Antiban(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return crChop.rand == 500;
    }

    @Override
    public void execute() {
        antibanEnable = true;
        int rand = Random.nextInt(1, 4);
        int sleepTime = Random.nextInt(500, 1000);
        String antiban = "[ANTIBAN] ";
        switch (rand) {
            case 1:
                int x = Random.nextInt(0, 500);
                int y = Random.nextInt(0, 500);
                Paint.status = antiban + "Moving Mouse (" + x + ", " + y + ")";
                System.out.println(antiban + "Moving Mouse (" + x + ", " + y + ")");
                ctx.input.move(x, y);
                Condition.sleep(sleepTime);
                antibanEnable = false;
                break;
            case 2:
                int a = sleepTime * 10;
                Paint.status = antiban + "AFK " + (a / 1000.0) + "s";
                System.out.println(antiban + "AFK " + (a / 1000.0) + "s");
                Condition.sleep(sleepTime * 10);
                antibanEnable = false;
                break;
            case 3:
                Component randWidget = ctx.widgets.widget(548).component(Random.nextInt(27, 57));
                Paint.status = antiban + "Opening Random Tab";
                System.out.println(antiban + "Opening Random Tab");
                randWidget.click();
                Condition.sleep(sleepTime);
                antibanEnable = false;
                break;
            case 4:
                int rAngle = Random.nextInt(0, 100);
                Paint.status = antiban + "Setting Angle to: " + rAngle;
                System.out.println(antiban + "Setting Angle to: " + rAngle);
                ctx.camera.angle(rAngle);
                Condition.sleep(sleepTime);
                antibanEnable = false;
                break;
//            case 5:
//                break;
//            case 6:
//                break;
//            case 7:
//                break;
//            case 8:
//                break;
//            case 9:
//                break;
//            case 10:
//                break;

        }
    }
}
