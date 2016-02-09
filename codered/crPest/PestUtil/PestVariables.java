package codered.crPest.PestUtil;

import codered.universal.Task;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.Npc;

import java.util.ArrayList;

/**
 * Created by Dakota on 10/16/2015.
 */
public class PestVariables {

    public static ArrayList<Task> taskList = new ArrayList<Task>();

    // Integers
    public static int combatLevel = 0;
    public static int startPestPoints = 0;
    public static int gainedPestPoints = 0;
    public static int totalPestPoints = 0;
    public static int knightRadius = 5;
    public static int gamesPlayed = 0;
    public static int gamesWon = 0;

    public static int purplePortalHealth = -1;
    public static int yellowPortalHealth = -1;
    public static int bluePortalHealth = -1;
    public static int redPortalHealth = -1;
    public static int voidKnightHealth = -1;

    // Booleans
    public static boolean boarded = false;
    public static boolean purplePortal = false;
    public static boolean yellowPortal = false;
    public static boolean bluePortal = false;
    public static boolean redPortal = false;
    public static boolean inGame = false;
    public static boolean gameStarted = false;

    // Strings
    public static String userId = "";
    public static String user = "";
    public static String playStyle = "";
    public static String[] enemyNames = {""};

    // Longs

    // Tiles
    public static Tile purplePortalTile = null;
    public static Tile yellowPortalTile = null;
    public static Tile bluePortalTile = null;
    public static Tile redPortalTile = null;
    public static Tile voidKnightTile = null;

    // Npcs
    public static Npc target = null;
}
