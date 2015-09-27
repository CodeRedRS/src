package codered.crChop.Variables;

import codered.crChop.Enums.Tree;
import org.powerbot.script.Area;
import org.powerbot.script.Tile;

/**
 * Created by Dakota on 9/25/2015.
 */
public class Presets {
    public static Presets[] presets = {
            new Presets("Edgeville", new Area(new Tile(3085, 3482), new Tile(3091, 3468)), new String[]{Tree.YEW.getName()}, new Tile[]{new Tile(3087, 3476, 0), new Tile(3091, 3470, 0), new Tile(3094, 3476, 0), new Tile(3094, 3483, 0), new Tile(3090, 3488, 0), new Tile(3094, 3490, 0),})
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
