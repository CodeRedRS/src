package codered.crPest.PestUtil;

import java.awt.*;

/**
 * Created by Dakota on 10/16/2015.
 */
public class PestConstants {
    public final static double scriptVersion = 0.4;

    // Integers

    public final static int veteranGangPlankId = 25632;
    public final static int intermediateGangPlankId = 25631;
    public final static int noviceGangPlankId = 14315;

    // Strings
    public final static String welcomeMessage = "<html><body>Welcome <strong>" + PestVariables.user + "</strong> to crPest " + PestConstants.scriptVersion + "!</body></html>";
    public final static String boardTheLanderString = "You board the lander.";
    public final static String deathMessage = "Oh dear, you are dead!";
    public final static String[] pestNames = {"Brawler", "Defiler", "Ravager", "Shifter", "Torcher", "Spinner", "Portal"};
    public final static String[] owner = {"CodeRed"};

    // Colors
    public final static Color purplePortal = new Color(16711935);
    public final static Color yellowPortal = new Color(16776960);
    public final static Color bluePortal = new Color(6711039);
    public final static Color redPortal = new Color(16724786);
}
