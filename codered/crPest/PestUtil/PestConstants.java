package codered.crPest.PestUtil;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Dakota on 10/16/2015.
 */
public class PestConstants {
    public final static double scriptVersion = 0.6;

    // Integers

    public final static int veteranGangPlankId = 25632;
    public final static int intermediateGangPlankId = 25631;
    public final static int noviceGangPlankId = 14315;

    // Strings
    public final static String welcomeMessage = "<html><body>Welcome <strong>" + PestVariables.user + "</strong> to crPest " + PestConstants.scriptVersion + "!</body></html>";
    public final static String boardTheLanderString = "You board the lander.";
    public final static String deathMessage = "Oh dear, you are dead!";
    public final static String[] pestNames = {"Brawler", "Defiler", "Ravager", "Shifter", "Torcher", "Spinner", "Portal"};
    public final static String owner = "CodeRed";
    public final static String[] squires = {"Squire (Novice)", "Squire (Intermediate)", "Squire (Veteran)"};
    public final static String[] blacklistIds = {"1593915"};
    public final static HashSet<String> blacklist = new HashSet<String>(Arrays.asList(blacklistIds));

    // Colors
    public final static Color purplePortal = new Color(16711935);
    public final static Color yellowPortal = new Color(16776960);
    public final static Color bluePortal = new Color(6711039);
    public final static Color redPortal = new Color(16724786);
}
