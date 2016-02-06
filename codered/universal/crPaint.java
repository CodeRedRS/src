package codered.universal;

import java.awt.*;

/**
 * Created by Dakota on 2/4/2016.
 */
public class crPaint {
    public void shadowString(String s, int x, int y, Color c, Graphics g) {
        final Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);
        g2.drawString(s, x + 1, y + 1);

        g2.setColor(c);
        g2.drawString(s, x, y);

    }
}
