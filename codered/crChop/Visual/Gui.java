package codered.crChop.Visual;

import codered.crChop.Enums.Tree;
import codered.crChop.Tasks.*;
import codered.crChop.Tasks.Interact.BankDeposit;
import codered.crChop.Tasks.Interact.ChopDown;
import codered.crChop.Tasks.Movement.ToBank;
import codered.crChop.Tasks.Movement.ToTree;
import codered.crChop.Variables.Presets;
import codered.crChop.crChop;
import codered.universal.Antiban;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

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
    private double range;
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
    final JCheckBox chkRadius;

    final JSpinner spnRadius;

    private int axeId;
    private Tree tree;
    private Boolean mousehop;

    public Gui(final ClientContext ctx, final int axeId) {
        this.axeId = axeId;
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
        chkRadius = new JCheckBox("Radius");

        spnRadius = new JSpinner();
        spnRadius.setVisible(false);

        this.setTitle("crChop " + crChop.version + " Gui");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(Frame.getFrames()[0]);
        this.setLayout(new BorderLayout());

        // CENTER
        pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.PAGE_AXIS));
        this.add(pnlCenter, BorderLayout.CENTER);
        pnlCenter.add(cboPresets);
        pnlCenter.add(cboTrees);
        pnlCenter.add(cboMethod);
        pnlCenter.add(spnRadius);

        // LINE_END
        pnlLineEnd.setLayout(new BoxLayout(pnlLineEnd, BoxLayout.Y_AXIS));
        this.add(pnlLineEnd, BorderLayout.LINE_END);
        pnlLineEnd.add(chkScreenshot);
        pnlLineEnd.add(chkMouseHop);
        pnlLineEnd.add(chkRunFromCombat);
        pnlLineEnd.add(chkChatResponder);
//        pnlLineEnd.add(chkRadius);

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

        chkRadius.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chkRadius.isSelected()) {
                    spnRadius.setVisible(true);
                    pack();
                } else {
                    spnRadius.setVisible(false);
                    pack();
                }
            }
        });

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

                tree = Tree.valueOf(cboTrees.getSelectedItem().toString());
                mousehop = chkMouseHop.isSelected();

                //METHOD SELECTION
                if (cboMethod.getSelectedItem().toString().toLowerCase().contains("bank")) {
                    if (getPreset() >= 0) {
                        crChop.taskList.addAll(Arrays.asList(new BankDeposit(ctx, tree, axeId), new ToBank(ctx, Presets.presets[getPreset()].path, Presets.presets[getPreset()].interactive)));
                    } else {
                        crChop.taskList.addAll(Arrays.asList(new BankDeposit(ctx, tree, axeId), new ToBank(ctx, null, null)));
                    }
                } else if (cboMethod.getSelectedItem().toString().toLowerCase().contains("drop")) {
                    crChop.taskList.add(new Drop(ctx, mousehop));
                }

                if (chkRunFromCombat.isSelected()) {
                    crChop.taskList.add(new Combat(ctx));
                }

                // USING PRESETS
                if (getPreset() >= 0) {
                    crChop.taskList.addAll(Arrays.asList(new ChopDown(ctx, tree, axeId, Presets.presets[getPreset()].area, chkRunFromCombat.isSelected()), new ToTree(ctx, Presets.presets[getPreset()].path, Presets.presets[getPreset()].area, tree, Presets.presets[getPreset()].interactive, axeId)));
                } else {
                    crChop.taskList.addAll(Arrays.asList(new ChopDown(ctx, tree, axeId, null, chkRunFromCombat.isSelected()), new ToTree(ctx, null, null, tree, null, axeId)));
                }

                if (cboTrees.getSelectedItem().toString().toLowerCase().contains("magic")) {
                    crChop.taskList.add(new Antiban(ctx, 1000));
                } else {
                    crChop.taskList.add(new Antiban(ctx, 10000));
                }

                crChop.taskList.addAll(Arrays.asList(new Run(ctx), new Inventory(ctx), new Special(ctx)));

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

    public int treeRadius() {
        return Integer.parseInt(spnRadius.getValue().toString());
    }

    public int getPreset() {
        return cboPresets.getSelectedIndex() - 1;
    }

    private void presetChange() {
        int p = getPreset();
        if (p >= 0) {
            Presets presets = Presets.presets[p];
            cboTrees.removeAllItems();
            if (cboMethod.getItemCount() == 1) {
                cboMethod.addItem("Bank");
            }
            for (String s : presets.trees) {
                cboTrees.addItem(s.toUpperCase());
            }
        }
    }
}
