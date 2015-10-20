package codered.crPest.PestUtil;

import codered.universal.Task;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayList;

/**
 * Created by Dakota on 10/16/2015.
 */
public class PestVariables {

    public static ArrayList<Task> taskList = new ArrayList<Task>();
    public static ClientContext ctx;

    // Integers
    public static int combatLevel = 0;
    public static int startPestPoints = 0;
    public static int gainedPestPoints = 0;

    // Booleans
    public static boolean boarded = false;
    public static boolean purplePortal = false;
    public static boolean yellowPortal = false;
    public static boolean bluePortal = false;
    public static boolean redPortal = false;

    // Strings
    public static String user = "";

    // Longs
    public static long runTime = 0;
    public static long startTime = 0;
    public static long hours = 0;
    public static long minutes = 0;
    public static long seconds = 0;
    public static long millis = 0;

    // Tiles
    public static Tile purplePortalTile = null;
    public static Tile yellowPortalTile = null;
    public static Tile bluePortalTile = null;
    public static Tile redPortalTile = null;
    public static Tile voidKnightTile = null;

//    public static GUI gui;
}
