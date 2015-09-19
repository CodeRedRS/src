package org.crChop.Visual;

import org.crChop.Enums.Tree;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

/**
 * Created by Dakota on 9/9/2015.
 */
public class Gui extends JFrame {
    final JButton btnStart = new JButton("Start Script");
    final JComboBox<String> cboMethod = new JComboBox<>(new String[]{"Drop"});
    final JComboBox<Tree> cboTrees = new JComboBox<>(Tree.values());
    final JCheckBox chkHideName = new JCheckBox("Hide Username");
    final JCheckBox chkScreenshot = new JCheckBox("Save Screenshot");

    public Gui(final ClientContext ctx) {
        if (isVisible())
            Paint.status = "GUI";

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setTitle("org.crChop Gui");

        this.add(cboTrees);
        this.add(cboMethod);
        this.add(chkHideName);
        this.add(chkScreenshot);
        this.add(btnStart);

        setLayout(new FlowLayout());
        setSize(250, 150);

        if (!ctx.objects.select().name("Bank booth", "Grand Exchange booth").isEmpty()) {
            for (GameObject g : ctx.objects.nearest()) {
                HashSet<String> set = new HashSet<>();
                if (!set.contains("Bank : " + g.name() + g.id())) {
                    cboMethod.addItem("Bank : " + g.name() + " : " + (int) g.tile().distanceTo(ctx.players.local()));
                    set.add("Bank : " + g.name() + g.id());
                }
            }
        }

        cboTrees.setSelectedIndex(0);

        btnStart.addActionListener(e -> dispose());
    }

    public final String getMethod() {
        return cboMethod.getSelectedItem().toString();
    }

    public final Tree getTree() {
        return Tree.valueOf(cboTrees.getSelectedItem().toString());
    }

    public final Boolean hideName() {
        return chkHideName.isSelected();
    }

    public final Boolean screenshot() {
        return chkScreenshot.isSelected();
    }
}

