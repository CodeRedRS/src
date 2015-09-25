package codered.crChop.Enums;

/**
 * Created by Dakota on 9/7/2015.
 */
public enum Tree {
    TREE("Tree", 1511),
    DEAD_TREE("Dead tree", 1511),
    ACHEY("Achey", 2862),
    OAK("Oak", 1521),
    WILLOW("Willow", 1519),
    TEAK("Teak", 6333),
    MAPLE("Maple", 1517),
    HOLLOW("Hollow", 0),
    MAHOGANY("Mahogany", 6332),
    ARCTIC("Arctic", 10810),
    YEW("Yew", 1515),
    MAGIC("Magic", 1513);

    private final String treeName;
    private final int logId;

    Tree(final String treeName, final int logId) {
        this.treeName = treeName;
        this.logId = logId;
    }

    public int getLogId() {
        return this.logId;
    }

    public String getName() {
        return this.treeName;
    }
}
