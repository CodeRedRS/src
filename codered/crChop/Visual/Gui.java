package codered.crChop.Visual;

import codered.crChop.Enums.Tree;
import codered.crChop.Tasks.*;
import codered.crChop.crChop;
import codered.universal.Antiban;
import org.powerbot.script.Tile;
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
    final JButton btnScan;
    final JButton btnStart;
    final JButton btnCancel;
    final JComboBox cboMethod;
    final JComboBox cboTrees;
    final JCheckBox chkScreenshot;
    final JCheckBox chkMouseHop;
    final JCheckBox chkRunFromCombat;

    private int axeId;
    private Tree tree;
    private Boolean mousehop;

    public Gui(final ClientContext ctx) {
        JPanel panelCenter = new JPanel();
        JPanel panelLineEnd = new JPanel();
        JPanel panelPageEnd = new JPanel();
        btnScan = new JButton("Rescan for Trees & Banks");
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
        panelLineEnd.add(chkRunFromCombat);

        // PAGE_END
        panelPageEnd.setLayout(new FlowLayout());
        this.add(panelPageEnd, BorderLayout.PAGE_END);
        panelPageEnd.add(btnStart);
        panelPageEnd.add(btnScan);
        panelPageEnd.add(btnCancel);

        if (!ctx.objects.select().action("Bank").isEmpty()) {
            cboMethod.addItem("Bank");
        }

        if (!ctx.objects.select().action("Chop down").isEmpty()) {
            HashSet<Tree> set = new HashSet<Tree>();
            for (GameObject t : ctx.objects) {
                try {
                    if (!set.contains(Tree.valueOf(t.name().toUpperCase().replace(" ", "_")))) {
                        cboTrees.addItem(Tree.valueOf(t.name().toUpperCase().replace(" ", "_")));
                        set.add(Tree.valueOf(t.name().toUpperCase().replace(" ", "_")));
                    }
                } catch (Exception ex) {
                }
            }
        }

        this.pack();

        // Method Action
        cboMethod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cboMethod.getSelectedItem().toString().toLowerCase().contains("drop")) {
                    chkMouseHop.setEnabled(true);
                } else {
                    chkMouseHop.setEnabled(false);
                }
            }
        });

        btnScan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                cboTrees.removeAll();
                cboMethod.removeAll();
                cboMethod.addItem("Drop");

                if (!ctx.objects.select().action("Bank").isEmpty()) {
                    cboMethod.addItem("Bank");
                }

                if (!ctx.objects.select().action("Chop down").isEmpty()) {
                    HashSet<Tree> set = new HashSet<Tree>();
                    for (GameObject t : ctx.objects) {
                        try {
                            if (!set.contains(Tree.valueOf(t.name().toUpperCase().replace(" ", "_")))) {
                                cboTrees.addItem(Tree.valueOf(t.name().toUpperCase().replace(" ", "_")));
                                set.add(Tree.valueOf(t.name().toUpperCase().replace(" ", "_")));
                            }
                        } catch (Exception ex) {
                        }
                    }
                }

                pack();
            }
        });

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

                tree = Tree.valueOf(cboTrees.getSelectedItem().toString());
                mousehop = chkMouseHop.isSelected();

                Tile bankTile = ctx.objects.select().action("Bank").nearest().poll().tile();

                if (cboMethod.getSelectedItem().toString().toLowerCase().contains("bank")) {
                    crChop.taskList.add(new Banking(ctx, bankTile, axeId));
                    System.out.println(bankTile);
                } else if (cboMethod.getSelectedItem().toString().toLowerCase().contains("drop")) {
                    crChop.taskList.add(new Drop(ctx, mousehop));
                }

                if (chkRunFromCombat.isSelected()) {
                    crChop.taskList.add(new Combat(ctx));
                }

                crChop.taskList.addAll(Arrays.asList(new Run(ctx), new Inventory(ctx), new Chop(ctx, tree, axeId, chkRunFromCombat.isSelected()), new Antiban(ctx), new Randoms(ctx)));

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
