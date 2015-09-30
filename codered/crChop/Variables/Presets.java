package codered.crChop.Variables;

import codered.crChop.Enums.Tree;
import org.powerbot.script.Area;
import org.powerbot.script.Tile;

/**
 * Created by Dakota on 9/25/2015.
 */
public class Presets {
    /**
     * The path is from the tree area to the bank
     */
    public static Presets[] presets = {
            new Presets("Edgeville", new Area(new Tile(3085, 3482), new Tile(3091, 3468)), new String[]{Tree.YEW.getName()}, new Tile[]{new Tile(3087, 3476, 0), new Tile(3091, 3470, 0), new Tile(3094, 3476, 0), new Tile(3094, 3483, 0), new Tile(3090, 3488, 0), new Tile(3094, 3490, 0),}),
            new Presets("Varrock Castle", new Area(new Tile(3200, 3507), new Tile(3225, 3498)), new String[]{Tree.YEW.getName()}, new Tile[]{new Tile(3213, 3502), new Tile(3209, 3503), new Tile(3199, 3501), new Tile(3196, 3495), new Tile(3187, 3491), new Tile(3179, 3491), new Tile(3172, 3489), new Tile(3167, 3489)}),
//            new Presets("Falador", new Area(new Tile(3062, 3260), new Tile(2990, 3330)), new String[]{Tree.TREE.getName(), Tree.OAK.getName(), Tree.YEW.getName()}, new Tile[]{new Tile(2998, 3314), new Tile(3002, 3319), new Tile(3007, 3323), new Tile(3006, 3329), new Tile(3007, 3335), new Tile(3007, 3341), new Tile(3007, 3347), new Tile(3008, 3353), new Tile(3011, 3355)})
    };

    public static String[] getNames() {
        String[] s = new String[Presets.presets.length + 1];
        s[0] = "No Preset";
        for (int i = 0; i < Presets.presets.length; i++)
            s[i + 1] = Presets.presets[i].name;
        return s;
    }

    public String name;
    public Area area;
    public String[] trees;
    public Tile[] path;

    public Presets(String name, Area area, String[] trees, Tile[] path) {
        this.name = name;
        this.area = area;
        this.trees = trees;
        this.path = path;
    }
}
