package crChop.Enums;

import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;

/**
 * Created by Dakota on 9/10/2015.
 */
class Paths extends ClientAccessor {
    public Paths(ClientContext ctx) {
        super(ctx);
    }
    // LUMBRIDGE
    public static Tile[] lumbridgeWestNomal = new Tile[]{new Tile(0, 0, 0)};
    public static Tile[] lumbridgeWestOak = new Tile[]{new Tile(0, 0, 0)};
    public static Tile[] lumbridgeWestYew = new Tile[]{new Tile(0, 0, 0)};
    public static Tile[] lumbridgeSwampDead = new Tile[]{new Tile(0, 0, 0)};

    // VARROCK
    public static Tile[] varrockWestNormal = new Tile[]{new Tile(0, 0, 0)};
    public static Tile[] varrockWestOak = new Tile[]{new Tile(0, 0, 0)};

}

public enum Location {
    VARROCK_WEST_NORMAL(Paths.varrockWestNormal, Tree.NORMAL.getName()),
    VARROCK_WEST_OAK(Paths.varrockWestOak, Tree.OAK.getName());

    final Tile[] path;
    final String treeName;

    Location(final Tile[] path, final String treeName) {
        this.path = path;
        this.treeName = treeName;
    }

    public Tile[] getPath() {
        return this.path;
    }

    public String getTreeName() {
        return this.treeName;
    }
}
