package codered.crChop.Variables;

import codered.crChop.Enums.Tree;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientAccessor;

/**
 * Created by Dakota on 9/25/2015.
 */
public class Presets {
    /**
     * The path is from the tree area to the bank
     */
    public static Presets[] presets = {
            new Presets("Edgeville (" + Tree.YEW.getName() + ")", new Tile[]{new Tile(3091, 3468), new Tile(3091, 3473), new Tile(3089, 3473), new Tile(3089, 3482), new Tile(3085, 3482), new Tile(3085, 3468)}, new String[]{Tree.YEW.getName()}, new Tile[]{new Tile(3087, 3476), new Tile(3091, 3470), new Tile(3092, 3470), new Tile(3094, 3477), new Tile(3094, 3484), new Tile(3094, 3491),}),
            new Presets("Lumbridge (" + Tree.YEW.getName() + ")", new Tile[]{new Tile(3189, 3227), new Tile(3166, 3215), new Tile(3149, 3230), new Tile(3142, 3259)}, new String[]{Tree.YEW.getName()}, new Tile[]{new Tile(3154, 3234), new Tile(3146, 3232), new Tile(3141, 3228), new Tile(3132, 3226), new Tile(3125, 3223), new Tile(3115, 3227), new Tile(3109, 3232), new Tile(3102, 3238), new Tile(3093, 3243)}),
            new Presets("Varrock Castle (" + Tree.YEW.getName() + ")", new Tile[]{new Tile(3200, 3507), new Tile(3200, 3498), new Tile(3225, 3498), new Tile(3225, 3507)}, new String[]{Tree.YEW.getName()}, new Tile[]{new Tile(3213, 3502), new Tile(3207, 3502), new Tile(3209, 3503), new Tile(3199, 3501), new Tile(3196, 3495), new Tile(3187, 3491), new Tile(3179, 3491), new Tile(3172, 3489), new Tile(3167, 3489)}),
            new Presets("Falador (" + Tree.YEW.getName() + ")", new Tile[]{new Tile(2994, 3315), new Tile(3002, 3322), new Tile(3018, 3322), new Tile(3047, 3328), new Tile(3045, 3313), new Tile(3020, 3312), new Tile(2994, 3306)}, new String[]{Tree.YEW.getName()}, new Tile[]{new Tile(3007, 3319), new Tile(3007, 3323), new Tile(3006, 3329), new Tile(3007, 3335), new Tile(3007, 3341), new Tile(3007, 3347), new Tile(3008, 3353), new Tile(3011, 3355)})
    };

    public static String[] getNames() {
        String[] s = new String[Presets.presets.length + 1];
        s[0] = "No Preset";
        for (int i = 0; i < Presets.presets.length; i++)
            s[i + 1] = Presets.presets[i].name;
        return s;
    }

    public String name;
    public Tile[] area;
    public String[] trees;
    public Tile[] path;

    public Presets(String name, Tile[] area, String[] trees, Tile[] path) {
        this.name = name;
        this.area = area;
        this.trees = trees;
        this.path = path;
    }
}
