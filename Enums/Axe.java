package crChop.Enums;

/**
 * Created by Dakota on 9/10/2015.
 */
public enum Axe {
    BRONZE("Bronze axe", 1351, 1),
    IRON("Iron axe", 1349, 1),
    STEEL("Steel axe", 1353, 6),
    BLACK("Black axe", 1361, 6),
    MITHRIL("Mithril axe", 1355, 21),
    ADAMANT("Adamant axe", 1357, 31),
    RUNE("Rune axe", 1359, 41),
    DRAGON("Dragon axe", 6739, 61),
    INFERNAL("Infernal axe", -1, 61);

    final String axeName;
    final int axeId;
    final int axeLevel;

    Axe(final String axeName, final int axeId, final int axeLevel) {
        this.axeName = axeName;
        this.axeId = axeId;
        this.axeLevel = axeLevel;
    }

    public String getAxeName() {
        return this.axeName;
    }

    public int getAxeId() {
        return this.axeId;
    }

    public int getAxeLevel() {
        return this.axeLevel;
    }
}
