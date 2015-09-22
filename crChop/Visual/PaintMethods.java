package org.crChop.Visual;

import org.crChop.crChop;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Dakota on 9/7/2015.
 */
public class PaintMethods extends ClientAccessor {
    // CONSTANTS
    final int woodcutting = Constants.SKILLS_WOODCUTTING;
    // FORMATTING
    public NumberFormat formatNumber = new DecimalFormat("###,###,###");
    DecimalFormat df = new DecimalFormat("#.0");
    private int logs = crChop.logs;
    private int startLevel = crChop.startLevel;
    private int startExperience = crChop.startExperience;

    public PaintMethods(ClientContext ctx) {
        super(ctx);
    }

    public String formatTime(long time) {
        long seconds = time / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        if (days > 0) {
            return (days < 10 ? "0" + days : days) + ":"
                    + (hours % 24 < 10 ? "0" + hours % 24 : hours % 24) + ":"
                    + (minutes % 60 < 10 ? "0" + minutes % 60 : minutes % 60) + ":"
                    + (seconds % 60 < 10 ? "0" + seconds % 60 : seconds % 60);
        }
        return (hours % 24 < 10 ? "0" + hours % 24 : hours % 24) + ":"
                + (minutes % 60 < 10 ? "0" + minutes % 60 : minutes % 60) + ":"
                + (seconds % 60 < 10 ? "0" + seconds % 60 : seconds % 60);
    }

    public String formatLetter(int n) {
        if (n > 10000) {
            return df.format(n / 1000.0) + "k";
        } else if (n > 1000000) {
            return df.format(n / 1000000.0) + "m";
        }
        return formatNumber.format(n);
    }

    // CALCULATIONS
    public String getLongestString(String[] array, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int maxLength = 0;
        String longestString = null;
        for (String s : array) {
            if (fm.stringWidth(s) > maxLength) {
                maxLength = fm.stringWidth(s);
                longestString = s;
            }
        }
        return longestString;
    }

    public int experienceAt(int level) {
        double total = 0;
        for (int i = 1; i < level; i++) {
            total += Math.floor(i + 300 * Math.pow(2, i / 7.0));
        }

        return (int) Math.floor(total / 4);
    }

    public long logsPerHour() {
        if (this.logs < 1) {
            return 0;
        }
        return (long) (this.logs * 3600000D) / ctx.controller.script().getTotalRuntime();
    }

    public String timeTillLevel() {
        if (experienceGained() < 1) {
            return formatTime(0L);
        }
        return formatTime((long) ((experienceAt(ctx.skills.realLevel(woodcutting) + 1) - ctx.skills.experience(woodcutting)) * 3600000D / hourlyExperience()));
    }

    public String timeTillMax() {
        if (experienceGained() < 1) {
            return formatTime(0L);
        }
        return formatTime((long) ((experienceAt(99) - ctx.skills.experience(woodcutting)) * 3600000D / hourlyExperience()));
    }

    public int hourlyExperience() {
        if (experienceGained() < 1) {
            return 0;
        }
        return (int) (experienceGained() * 3600000D / ctx.controller.script().getTotalRuntime());
    }

    public int levelsGained() {
        return ctx.skills.realLevel(woodcutting) - startLevel;
    }

    public int experienceGained() {
        return ctx.skills.experience(woodcutting) - startExperience;
    }

    // PAINT ELEMENTS
    public void stringTitle(String s, int w, Graphics g) {
        final Graphics2D g2 = (Graphics2D) g;
        FontMetrics fm = g2.getFontMetrics();
        int x = ((w - fm.stringWidth(s)) / 2);
        int y = fm.getAscent() + 2;
        g2.drawString(s, x + 2, y);
        g2.drawLine(2, y + 3, w, y + 3);
    }

    public void centerString(String s, int w, int x, int y, Graphics g) {
        final Graphics2D g2 = (Graphics2D) g;
        FontMetrics fm = g2.getFontMetrics();
        x += ((w - fm.stringWidth(s)) / 2);
        g2.drawString(s, x + 2, y);
    }

    public void borderedRect(int x, int y, int width, int height, Color borderColor, Color fillColor, Graphics g) {
        final Graphics2D g2 = (Graphics2D) g;
        g2.setColor(fillColor);
        g2.fillRect(x, y, width, height);

        g2.setColor(borderColor);
        g2.drawRect(x, y, width, height);
    }

    public void shadowString(String s, int x, int y, Color c, Graphics g) {
        final Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);
        g2.drawString(s, x + 1, y + 1);

        g2.setColor(c);
        g2.drawString(s, x, y);

    }

    public void outlineString(String s, int x, int y, Color c, Graphics g) {
        final Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);
        g2.drawString(s, x + 1, y);
        g2.drawString(s, x - 1, y);
        g2.drawString(s, x, y + 1);
        g2.drawString(s, x, y - 1);

        g2.setColor(c);
        g2.drawString(s, x, y);

    }

    public Double mapArea() {
        Tile playerTile = ctx.players.local().tile();
        Tile mapBorder = new Tile(playerTile.x(), playerTile.y() + 4);

        Point playerPoint = playerTile.matrix(ctx).mapPoint();
        Point mapPoint = mapBorder.matrix(ctx).mapPoint();

        return Math.sqrt((playerPoint.x - mapPoint.x) * (playerPoint.x - mapPoint.x) + (playerPoint.y - mapPoint.y) * (playerPoint.y - mapPoint.y));
    }
}
