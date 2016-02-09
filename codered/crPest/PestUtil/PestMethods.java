package codered.crPest.PestUtil;

import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Npc;

/**
 * Created by Dakota on 10/22/2015.
 */
public class PestMethods {

    public static void resetGame(ClientContext ctx) {
        PestVariables.purplePortal = false;
        PestVariables.yellowPortal = false;
        PestVariables.bluePortal = false;
        PestVariables.redPortal = false;
        PestVariables.purplePortalTile = null;
        PestVariables.purplePortalTile = null;
        PestVariables.yellowPortalTile = null;
        PestVariables.bluePortalTile = null;
        PestVariables.redPortalTile = null;
        PestVariables.voidKnightTile = null;
        PestVariables.gameStarted = false;
        PestVariables.inGame = ctx.widgets.component(408, 5).valid();
        PestVariables.boarded = ctx.widgets.component(407, 15).valid();
    }

    public static void getPortalTiles(ClientContext ctx) {
        PestVariables.inGame = ctx.widgets.component(408, 5).valid();
        PestVariables.boarded = ctx.widgets.component(407, 15).valid();
        Tile tempTile = new Tile(0, 0, 0);
        if (PestVariables.voidKnightTile == null) {
            Tile t = ctx.npcs.select().name("Void knight").nearest().poll().tile();
            if (t.compareTo(tempTile) > 0) {
                PestVariables.voidKnightTile = t;
                PestVariables.purplePortalTile = new Tile(t.x() - 27, t.y(), 0);
                PestVariables.bluePortalTile = new Tile(t.x() + 25, t.y() - 3, 0);
                PestVariables.yellowPortalTile = new Tile(t.x() + 14, t.y() - 21, 0);
                PestVariables.redPortalTile = new Tile(t.x() - 10, t.y() - 22, 0);

                System.out.println("\r\n-=====- NEW GAME -=====-");
                System.out.println("Void knight: " + PestVariables.voidKnightTile);
                System.out.println("Purple portal: " + PestVariables.purplePortalTile);
                System.out.println("Blue portal: " + PestVariables.bluePortalTile);
                System.out.println("Yellow portal: " + PestVariables.yellowPortalTile);
                System.out.println("Red portal: " + PestVariables.redPortalTile);
            }
        }
    }

    /*
    * blue - 1740
    * yello - 1741
    * red - 1742
    * purp - 1739
    * */

    public static boolean notFighting(ClientContext ctx, Npc enemy) {
        return ctx.players.local().interacting().name().isEmpty()
                || ctx.players.local().interacting().name() == null
                || !ctx.players.local().inCombat()
                && enemy.health() <= 0
                && ctx.players.local().tile().distanceTo(PestVariables.voidKnightTile) <= PestVariables.knightRadius;
    }
}
