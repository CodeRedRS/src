package codered.crChop.Enums;

/**
 * Created by Dakota on 9/7/2015.
 */
public enum Tree {
    TREE("Tree", 1511, -100, 125, -350, 0, -108, 108),
    DEAD_TREE("Dead tree", 1511, -32, 32, -64, 0, -32, 32),
    ACHEY("Achey", 2862, -32, 32, -64, 0, -32, 32),
    OAK("Oak", 1521, -180, 180, -430, 0, -180, 150),
    WILLOW("Willow", 1519, -100, 100, -300, 0, -125, 125),
    TEAK("Teak", 6333, -32, 32, -64, 0, -32, 32),
    MAPLE_TREE("Maple tree", 1517, -32, 32, -64, 0, -32, 32),
    HOLLOW("Hollow", -1, -32, 32, -64, 0, -32, 32),
    MAHOGANY("Mahogany", 6332, -32, 32, -64, 0, -32, 32),
    ARCTIC("Arctic", 10810, -32, 32, -64, 0, -32, 32),
    YEW("Yew", 1515, -125, 125, -450, 0, -125, 125),
    MAGIC_TREE("Magic tree", 1513, -32, 32, -64, 0, -32, 32);

    private final String treeName;
    private final int logId;
    private final int[] bounds;

    Tree(final String treeName, final int logId, final int... bounds) {
        this.treeName = treeName;
        this.logId = logId;
        this.bounds = bounds;
    }

    public int getLogId() {
        return this.logId;
    }

    public String getName() {
        return this.treeName;
    }

    public int[] getBounds() {
        return this.bounds;
    }
}
