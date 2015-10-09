package codered.crChop.Enums;

/**
 * Created by Dakota on 9/7/2015.
 */
public enum Tree {
    TREE("Tree", -8, 8, -350, 0, -8, 8),
    DEAD_TREE("Dead tree", -8, 8, -64, 0, -8, 8),
    ACHEY("Achey", -8, 8, -64, 0, -8, 8),
    OAK("Oak", -8, 8, -430, 0, -8, 8),
    WILLOW("Willow", -8, 8, -300, 0, -8, 8),
    TEAK("Teak", -8, 8, -64, 0, -8, 8),
    MAPLE_TREE("Maple tree", -8, 8, -280, 0, -8, 8),
    HOLLOW("Hollow", -8, 8, -64, 0, -8, 8),
    MAHOGANY("Mahogany", -8, 8, -64, 0, -8, 8),
    ARCTIC("Arctic", -8, 8, -64, 0, -8, 8),
    YEW("Yew", -8, 8, -450, 0, -8, 8),
    MAGIC_TREE("Magic tree", -8, 8, -425, 0, -8, 8);

    private final String treeName;
    private final int[] bounds;

    Tree(final String treeName, final int... bounds) {
        this.treeName = treeName;
        this.bounds = bounds;
    }


    public String getName() {
        return this.treeName;
    }

    public int[] getBounds() {
        return this.bounds;
    }
}
