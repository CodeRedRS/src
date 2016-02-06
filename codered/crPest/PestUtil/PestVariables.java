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
    public static int totalPestPoints = 0;
    public static int knightRadius = 5;
    public static int gamesPlayed = 0;

    // Booleans
    public static boolean boarded = false;
    public static boolean purplePortal = false;
    public static boolean yellowPortal = false;
    public static boolean bluePortal = false;
    public static boolean redPortal = false;
    public static boolean inGame = false;


    // Strings
    public static String userId = "";
    public static String user = "";
    public static String target = "";
    public static String playStyle = "";
    public static String[] enemyNames = {""};

    // Longs
    public static long runTime = 0L;

    // Tiles
    public static Tile purplePortalTile = null;
    public static Tile yellowPortalTile = null;
    public static Tile bluePortalTile = null;
    public static Tile redPortalTile = null;
    public static Tile voidKnightTile = null;

//    public static GUI gui;
}
