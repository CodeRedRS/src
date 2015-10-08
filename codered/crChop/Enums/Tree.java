package codered.crChop.Enums;

/**
 * Created by Dakota on 9/7/2015.
 */
public enum Tree {
    TREE("Tree", -16, 16, -350, 0, -16, 16),
    DEAD_TREE("Dead tree", -16, 16, -64, 0, -16, 16),
    ACHEY("Achey", -16, 16, -64, 0, -16, 16),
    OAK("Oak", -16, 16, -430, 0, -16, 16),
    WILLOW("Willow", -16, 16, -300, 0, -16, 16),
    TEAK("Teak", -16, 16, -64, 0, -16, 16),
    MAPLE_TREE("Maple tree", -16, 16, -280, 0, -16, 16),
    HOLLOW("Hollow", -16, 16, -64, 0, -16, 16),
    MAHOGANY("Mahogany", -16, 16, -64, 0, -16, 16),
    ARCTIC("Arctic", -16, 16, -64, 0, -16, 16),
    YEW("Yew", -16, 16, -450, 0, -16, 16),
    MAGIC_TREE("Magic tree", -16, 16, -425, 0, -16, 16);

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
