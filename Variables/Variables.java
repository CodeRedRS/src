package crChop.Variables;

import crChop.Enums.Tree;

/**
 * Created by Dakota on 9/14/2015.
 */
public class Variables {
    private static int startExperience, startLevel, axeId;
    private static Tree tree;

    public int getStartExperience() {
        return Variables.startExperience;
    }

    public void setStartExperience(int startExperience) {
        Variables.startExperience = startExperience;
    }

    public int getStartLevel() {
        return Variables.startLevel;
    }

    public void setStartLevel(int startLevel) {
        Variables.startLevel = startLevel;
    }

    public int getAxeId() {
        return Variables.axeId;
    }

    public void setAxeId(int axeId) {
        Variables.axeId = axeId;
    }

    public Tree getTree() {
        return Variables.tree;
    }

    public void setTree(Tree tree) {
        Variables.tree = tree;
    }
}
