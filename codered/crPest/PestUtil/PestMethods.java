package codered.crPest.PestUtil;

import org.powerbot.script.Tile;

/**
 * Created by Dakota on 10/22/2015.
 */
public class PestMethods {

    public static void resetPortals() {
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
    }

    public static void getPortalTiles() {
        Tile tempTile = new Tile(0, 0, 0);
        if (PestVariables.voidKnightTile == null) {
            Tile t = PestVariables.ctx.npcs.select().name("Void knight").poll().tile();
            if (t.compareTo(tempTile) > 0) {
                PestVariables.voidKnightTile = t;
                PestVariables.purplePortalTile = new Tile(t.x() - 27, t.y(), 0);
                PestVariables.bluePortalTile = new Tile(t.x() + 25, t.y() - 3, 0);
                PestVariables.yellowPortalTile = new Tile(t.x() + 14, t.y() - 21, 0);
                PestVariables.redPortalTile = new Tile(t.x() - 10, t.y() - 22, 0);
                System.out.println("\r\n=====- NEW GAME -=====");
                System.out.println("Void knight: " + PestVariables.voidKnightTile);
                System.out.println("Purple portal: " + PestVariables.purplePortalTile);
                System.out.println("Blue portal: " + PestVariables.bluePortalTile);
                System.out.println("Yellow portal: " + PestVariables.yellowPortalTile);
                System.out.println("Red portal: " + PestVariables.redPortalTile);
            }
        }
    }

}
