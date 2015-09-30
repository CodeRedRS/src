package codered.crChop.Visual;

import codered.crChop.Enums.Tree;
import codered.crChop.Tasks.*;
import codered.crChop.Variables.Presets;
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
    final JButton btnScan;
    final JButton btnStart;
    final JButton btnCancel;

    final JComboBox cboMethod;
    final JComboBox cboTrees;
    final JComboBox cboPresets;

    final JCheckBox chkScreenshot;
    final JCheckBox chkMouseHop;
    final JCheckBox chkRunFromCombat;
    final JCheckBox chkChatResponder;

    private int axeId;
    private Tree tree;
    private Boolean mousehop;

    public Gui(final ClientContext ctx) {
        JPanel pnlCenter = new JPanel();
        JPanel pnlLineEnd = new JPanel();
        JPanel pnlPageEnd = new JPanel();
        btnScan = new JButton("Rescan for Trees & Banks");
        btnStart = new JButton("Start Script");
        btnCancel = new JButton("Cancel");

        cboMethod = new JComboBox(new String[]{"Drop"});
        cboTrees = new JComboBox();
        cboPresets = new JComboBox(Presets.getNames());

        chkScreenshot = new JCheckBox("Save Screenshot");
        chkMouseHop = new JCheckBox("Mouse Hop Drop");
        chkRunFromCombat = new JCheckBox("Run From Combat");
        chkChatResponder = new JCheckBox("Basic Chat Responder (BETA)");

        this.setTitle("crChop Gui");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(Frame.getFrames()[0]);
        this.setLayout(new BorderLayout());

        // CENTER
        pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.PAGE_AXIS));
        this.add(pnlCenter, BorderLayout.CENTER);
        pnlCenter.add(cboPresets);
        pnlCenter.add(cboTrees);
        pnlCenter.add(cboMethod);

        // LINE_END
        pnlLineEnd.setLayout(new BoxLayout(pnlLineEnd, BoxLayout.Y_AXIS));
        this.add(pnlLineEnd, BorderLayout.LINE_END);
        pnlLineEnd.add(chkScreenshot);
        pnlLineEnd.add(chkMouseHop);
        pnlLineEnd.add(chkRunFromCombat);
        pnlLineEnd.add(chkChatResponder);

        // PAGE_END
        pnlPageEnd.setLayout(new FlowLayout());
        this.add(pnlPageEnd, BorderLayout.PAGE_END);
        pnlPageEnd.add(btnStart);
        pnlPageEnd.add(btnScan);
        pnlPageEnd.add(btnCancel);

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

        // Preset Action
        cboPresets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getPreset() >= 0) {
                    presetChange();
                    btnScan.setEnabled(false);
                    cboMethod.setEnabled(true);
                    chkMouseHop.setEnabled(true);
                } else {
                    btnScan.setEnabled(true);
                    btnScan.doClick();
                }
            }
        });

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

        // Scan Action
        btnScan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cboTrees.removeAllItems();
                cboMethod.removeItem("Bank");

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

                if (cboMethod.getSelectedItem().toString().toLowerCase().contains("bank")) {
                    if (getPreset() >= 0) {
                        crChop.taskList.add(new Banking(ctx, Presets.presets[getPreset()].path, axeId));
                    } else {
                        crChop.taskList.add(new Banking(ctx, null, axeId));
                    }
                } else if (cboMethod.getSelectedItem().toString().toLowerCase().contains("drop")) {
                    crChop.taskList.add(new Drop(ctx, mousehop));
                }

                if (chkRunFromCombat.isSelected()) {
                    crChop.taskList.add(new Combat(ctx));
                }

                if (getPreset() >= 0) {
                    crChop.taskList.add(new Chop(ctx, tree, axeId, chkRunFromCombat.isSelected(), Presets.presets[getPreset()].path, Presets.presets[getPreset()].area));
                } else {
                    crChop.taskList.add(new Chop(ctx, tree, axeId, chkRunFromCombat.isSelected(), null, null));
                }

                crChop.taskList.addAll(Arrays.asList(new Run(ctx), new Inventory(ctx), new Antiban(ctx)));

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

    public boolean chatResponder() {
        return chkChatResponder.isSelected();
    }

    public int getPreset() {
        return cboPresets.getSelectedIndex() - 1; // -1 for no preset
    }

    private void presetChange() {
        int p = getPreset();
        if (p >= 0) {
            Presets presets = Presets.presets[p];
            cboTrees.removeAllItems();
            for (String s : presets.trees) {
                cboTrees.addItem(s.toUpperCase());
            }
        }
    }
}
