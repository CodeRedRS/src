package codered.crChop.Variables;

import codered.crChop.Enums.Tree;
import org.powerbot.script.Tile;

/**
 * Created by Dakota on 9/25/2015.
 */
public class Presets {
    /**
     * The path is from the tree area to the bank
     */
    public static Presets[] presets = {
            new Presets("Edgeville (" + Tree.YEW.toString() + ")", new Tile[]{new Tile(3091, 3468), new Tile(3091, 3473), new Tile(3089, 3473), new Tile(3089, 3482), new Tile(3085, 3482), new Tile(3085, 3468)}, Tree.YEW.toString(), new Tile[]{new Tile(3087, 3476, 0), new Tile(3090, 3471, 0), new Tile(3094, 3474, 0), new Tile(3094, 3479, 0), new Tile(3093, 3484, 0), new Tile(3090, 3488, 0), new Tile(3094, 3491, 0)}, new Tile(3091, 3470)),
            new Presets("Lumbridge (" + Tree.YEW.toString() + ")", new Tile[]{new Tile(3189, 3227), new Tile(3166, 3215), new Tile(3149, 3230), new Tile(3142, 3259)}, Tree.YEW.toString(), new Tile[]{new Tile(3159, 3239, 0), new Tile(3154, 3239, 0), new Tile(3149, 3237, 0), new Tile(3145, 3233, 0), new Tile(3140, 3232, 0), new Tile(3135, 3231, 0), new Tile(3131, 3228, 0), new Tile(3127, 3224, 0), new Tile(3122, 3223, 0), new Tile(3119, 3219, 0), new Tile(3115, 3222, 0), new Tile(3113, 3227, 0), new Tile(3110, 3232, 0), new Tile(3106, 3235, 0), new Tile(3101, 3236, 0), new Tile(3098, 3240, 0), new Tile(3098, 3245, 0), new Tile(3093, 3247, 0), new Tile(3092, 3243, 0)}, null),
            new Presets("Varrock Palace (" + Tree.YEW.toString() + ")", new Tile[]{new Tile(3200, 3507), new Tile(3200, 3498), new Tile(3225, 3498), new Tile(3225, 3507)}, Tree.YEW.toString(), new Tile[]{new Tile(3215, 3502, 0), new Tile(3210, 3502, 0), new Tile(3205, 3502, 0), new Tile(3200, 3501, 0), new Tile(3199, 3496, 0), new Tile(3196, 3492, 0), new Tile(3191, 3491, 0), new Tile(3186, 3491, 0), new Tile(3181, 3491, 0), new Tile(3176, 3491, 0), new Tile(3171, 3491, 0), new Tile(3167, 3490, 0)}, null),
//            new Presets("South Falador (" + Tree.YEW.toString() + ")", new Tile[]{new Tile(2994, 3315), new Tile(3002, 3322), new Tile(3018, 3322), new Tile(3047, 3328), new Tile(3045, 3313), new Tile(3020, 3312), new Tile(2994, 3306)}, Tree.YEW.toString(), new Tile[]{new Tile(3019, 3318, 0), new Tile(3014, 3318, 0), new Tile(3009, 3321, 0), new Tile(3008, 3326, 0), new Tile(3008, 3331, 0), new Tile(3007, 3336, 0), new Tile(3007, 3341, 0), new Tile(3007, 3346, 0), new Tile(3007, 3351, 0), new Tile(3007, 3356, 0), new Tile(3011, 3359, 0), new Tile(3011, 3355, 0)}, null),
            new Presets("Seers' Village (" + Tree.YEW.toString() + ")", new Tile[]{new Tile(2718, 3468), new Tile(2718, 3456), new Tile(2704, 3456), new Tile(2704, 3468)}, Tree.YEW.toString(), new Tile[]{new Tile(2710, 3462, 0), new Tile(2715, 3462, 0), new Tile(2718, 3466, 0), new Tile(2720, 3471, 0), new Tile(2724, 3475, 0), new Tile(2726, 3480, 0), new Tile(2727, 3485, 0), new Tile(2726, 3490, 0), new Tile(2724, 3493, 0)}, null),
            new Presets("Sorcerers' Tower (" + Tree.MAGIC_TREE.toString() + ")", new Tile[]{new Tile(2690,3405), new Tile(2690,3390), new Tile(2715, 3390), new Tile(2715, 3405)}, Tree.MAGIC_TREE.toString(), new Tile[]{new Tile(2702, 3398, 0), new Tile(2703, 3393, 0), new Tile(2708, 3392, 0), new Tile(2713, 3392, 0), new Tile(2714, 3397, 0), new Tile(2716, 3402, 0), new Tile(2715, 3407, 0), new Tile(2716, 3412, 0), new Tile(2718, 3417, 0), new Tile(2717, 3422, 0), new Tile(2717, 3427, 0), new Tile(2719, 3432, 0), new Tile(2724, 3435, 0), new Tile(2723, 3440, 0), new Tile(2721, 3445, 0), new Tile(2721, 3450, 0), new Tile(2721, 3455, 0), new Tile(2721, 3460, 0), new Tile(2720, 3465, 0), new Tile(2722, 3470, 0), new Tile(2724, 3475, 0), new Tile(2726, 3480, 0), new Tile(2727, 3485, 0), new Tile(2726, 3490, 0), new Tile(2727, 3493, 0)}, null)

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
    public String trees;
    public Tile[] path;
    public Tile interactive;

    public Presets(String name, Tile[] area, String trees, Tile[] path, Tile interactive) {
        this.name = name;
        this.area = area;
        this.trees = trees;
        this.path = path;
        this.interactive = interactive;
    }
}
