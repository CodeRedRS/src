package codered.crChop.Visual;

import codered.crChop.Enums.Tree;
import codered.crChop.Tasks.*;
import codered.crChop.crChop;
import codered.universal.Antiban;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Dakota on 9/9/2015.
 */
public class Gui extends JFrame {
    final JButton btnStart;
    final JButton btnCancel;
    final JComboBox cboMethod;
    final JComboBox cboTrees;
    final JCheckBox chkScreenshot;
    final JCheckBox chkMouseHop;
    int axeId;
    String method;
    Tree tree;
    Boolean mousehop;

    public Gui(final ClientContext ctx) {
        btnStart = new JButton("Start Script");
        btnCancel = new JButton("Cancel");
        cboMethod = new JComboBox(new String[]{"Drop"});
        cboTrees = new JComboBox(Tree.values());
        chkScreenshot = new JCheckBox("Save Screenshot");
        chkMouseHop = new JCheckBox("Mouse Hop Drop");

        this.setTitle("crChop Gui");

        this.add(cboTrees);
        this.add(cboMethod);
        this.add(chkScreenshot);
        this.add(chkMouseHop);
        this.add(btnStart);
        this.add(btnCancel);

        this.setLayout(new FlowLayout());
        this.pack();

        if (!ctx.objects.select().name("Bank booth", "Grand Exchange booth").isEmpty()) {
            for (GameObject g : ctx.objects.nearest()) {
                HashSet<String> set = new HashSet<String>();
                if (!set.contains("Bank : " + g.name())) {
                    cboMethod.addItem("Bank : " + g.name() + " : " + (int) g.tile().distanceTo(ctx.players.local()));
                    set.add("Bank : " + g.name());
                }
            }
        }

        cboTrees.setSelectedIndex(0);

        btnStart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // GET AXE ID
                for (Item i : ctx.inventory.items()) {
                    if (i.name().toLowerCase().contains("axe")) {
                        System.out.println(i.name() + "(" + i.id() + ")");
                        axeId = i.id();
                    }
                }

                method = cboMethod.getSelectedItem().toString();
                tree = Tree.valueOf(cboTrees.getSelectedItem().toString());
                mousehop = chkMouseHop.isSelected();

                if (cboMethod.getSelectedItem().toString().toLowerCase().contains("bank")) {
                    crChop.taskList.addAll(Arrays.asList(new Banking(ctx, method, axeId), new Run(ctx), new Inventory(ctx), new Chop(ctx, tree, axeId), new Antiban(ctx)));
                } else if (cboMethod.getSelectedItem().toString().toLowerCase().contains("drop")) {
                    crChop.taskList.addAll(Arrays.asList(new Drop(ctx, mousehop), new Run(ctx), new Inventory(ctx), new Chop(ctx, tree, axeId), new Antiban(ctx)));
                }

                dispose();
            }
        });

        btnCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ctx.controller.stop();
                dispose();
            }
        });
    }

    public String getMethod() {
        return cboMethod.getSelectedItem().toString();
    }

    public Tree getTree() {
        return Tree.valueOf(cboTrees.getSelectedItem().toString());
    }

    public boolean saveScreenshot() {
        return chkScreenshot.isSelected();
    }
}

