package codered.universal;

import codered.crChop.Visual.Paint;
import codered.crChop.Visual.PaintMethods;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Component;

/**
 * Created by Dakota on 9/11/2015.
 */
public class Antiban extends Task<ClientContext> {
    public Boolean antibanEnable = false;
    private int frequency;
    private PaintMethods PaintMethods = new PaintMethods(ctx);

    public Antiban(ClientContext ctx, int frequency) {
        super(ctx);
        this.frequency = frequency;
    }

    @Override
    public boolean activate() {
        return ctx.players.local().animation() != -1 &&
                !ctx.players.local().inMotion() &&
                !ctx.players.local().inCombat();
    }

    @Override
    public void execute() {
        String temp = Paint.getStatus();
        antibanEnable = true;
        int rand = Random.nextInt(1, frequency);
        int sleepTime = Random.nextInt(500, 1000);
        String antiban = "[i][ANTIBAN - " + PaintMethods.formatTime(ctx.controller.script().getTotalRuntime()) + "] ";
        switch (rand) {
            case 1:
                int x = Random.nextInt(0, 500);
                int y = Random.nextInt(0, 500);
                Paint.paintStatus(antiban + "Moving Mouse (" + x + ", " + y + ")");
                System.out.println(antiban + "Moving Mouse (" + x + ", " + y + ")");
                ctx.input.move(x, y);
                Condition.sleep(sleepTime);
                Paint.paintStatus(temp);
                antibanEnable = false;
                break;
            case 2:
                int a = sleepTime * 10;
                Paint.paintStatus(antiban + "AFK " + (a / 1000.0) + "s");
                System.out.println(antiban + "AFK " + (a / 1000.0) + "s");
                Condition.sleep(sleepTime * 10);
                Paint.paintStatus(temp);
                antibanEnable = false;
                break;
            case 3:
                Component randWidget = ctx.widgets.widget(548).component(Random.nextInt(27, 57));
                Paint.paintStatus(antiban + "Opening Random Tab");
                System.out.println(antiban + "Opening Random Tab");
                randWidget.click();
                Condition.sleep(sleepTime);
                Paint.paintStatus(temp);
                antibanEnable = false;
                break;
            case 4:
                int rAngle = Random.nextInt(0, 100);
                Paint.paintStatus(antiban + "Setting Angle to: " + rAngle);
                System.out.println(antiban + "Setting Angle to: " + rAngle);
                ctx.camera.angle(rAngle);
                Condition.sleep(sleepTime);
                Paint.paintStatus(temp);
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
            default:
                break;

        }
    }
}
