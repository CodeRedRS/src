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
 * Created by Dakota on 9/24/2015.
 */
public class Gui extends JFrame {
    final JButton btnStart;
    final JButton btnCancel;
    final JComboBox cboMethod;
    final JComboBox cboTrees;
    final JCheckBox chkScreenshot;
    final JCheckBox chkMouseHop;
    final JCheckBox chkRunFromCombat;

    private int axeId;
    private String method;
    private Tree tree;
    private Boolean mousehop, avoidCombat;

    public Gui(final ClientContext ctx) {
        JPanel panelCenter = new JPanel();
        JPanel panelLineEnd = new JPanel();
        JPanel panelPageEnd = new JPanel();
        btnStart = new JButton("Start Script");
        btnCancel = new JButton("Cancel");
        cboMethod = new JComboBox(new String[]{"Drop"});
        cboTrees = new JComboBox();
        chkScreenshot = new JCheckBox("Save Screenshot");
        chkMouseHop = new JCheckBox("Mouse Hop Drop");
        chkRunFromCombat = new JCheckBox("Run From Combat");

        this.setTitle("crChop Gui");
        this.setLocationRelativeTo(Frame.getFrames()[0]);
        this.setLayout(new BorderLayout());

        // CENTER
        panelCenter.setLayout(new FlowLayout());
        this.add(panelCenter, BorderLayout.CENTER);
        panelCenter.add(cboTrees);
        panelCenter.add(cboMethod);

        // LINE_END
        panelLineEnd.setLayout(new BoxLayout(panelLineEnd, BoxLayout.Y_AXIS));
        this.add(panelLineEnd, BorderLayout.LINE_END);
        panelLineEnd.add(chkScreenshot);
        panelLineEnd.add(chkMouseHop);
        //panelLineEnd.add(chkRunFromCombat);

        // PAGE_END
        panelPageEnd.setLayout(new FlowLayout());
        this.add(panelPageEnd, BorderLayout.PAGE_END);
        panelPageEnd.add(btnStart);
        panelPageEnd.add(btnCancel);

        if (!ctx.objects.select().name("Bank booth", "Grand Exchange booth").isEmpty()) {
            HashSet<String> set = new HashSet<String>();
            for (GameObject b : ctx.objects) {
                if (!set.contains("Bank : " + b.name())) {
                    cboMethod.addItem("Bank : " + b.name() + " : " + (int) b.tile().distanceTo(ctx.players.local()));
                    set.add("Bank : " + b.name());
                }
            }
        }

        if (!ctx.objects.select().action("Chop down").isEmpty()) {
            HashSet<Tree> set = new HashSet<Tree>();
            for (GameObject t : ctx.objects) {
                if (!set.contains(Tree.valueOf(t.name().toUpperCase().replace(" ", "_")))) {
                    cboTrees.addItem(Tree.valueOf(t.name().toUpperCase().replace(" ", "_")));
                    set.add(Tree.valueOf(t.name().toUpperCase().replace(" ", "_")));
                }

            }
        }

        this.pack();

        // START BUTTON ACTION
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
                avoidCombat = chkRunFromCombat.isSelected();

                if (avoidCombat) {
                    crChop.taskList.add(new Combat(ctx));
                }

                if (cboMethod.getSelectedItem().toString().toLowerCase().contains("bank")) {
                    crChop.taskList.addAll(Arrays.asList(new Banking(ctx, method, axeId), new Inventory(ctx), new Chop(ctx, tree, axeId, avoidCombat), new Antiban(ctx)));
                } else if (cboMethod.getSelectedItem().toString().toLowerCase().contains("drop")) {
                    crChop.taskList.addAll(Arrays.asList(new Drop(ctx, mousehop), new Inventory(ctx), new Chop(ctx, tree, axeId, avoidCombat), new Antiban(ctx)));
                }

                dispose();
            }
        });

        // CANCEL BUTTON ACTION
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
