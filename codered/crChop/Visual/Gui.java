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
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.GameObject;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

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
    final JSpinner spnEndTime;
    final JSpinner spnEndLevel;
    final JSpinner spnEndLogs;

    private int axeId;
    private Tree tree;
    private Boolean mousehop;

    public Gui(final ClientContext ctx, final int axeId) {

        this.axeId = axeId;
        JPanel pnlNorth = new JPanel();
        JPanel pnlLineStart = new JPanel();
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

        spnEndTime = new JSpinner(new SpinnerNumberModel(15, 15, 990, 15));
        ((JSpinner.DefaultEditor) spnEndTime.getEditor()).getTextField().setEditable(false);
        ((JSpinner.DefaultEditor) spnEndTime.getEditor()).getTextField().setColumns(5);
        spnEndLevel = new JSpinner(new SpinnerNumberModel(ctx.skills.realLevel(Constants.SKILLS_WOODCUTTING) + 1, ctx.skills.realLevel(Constants.SKILLS_WOODCUTTING) + 1, null, 1));
        ((JSpinner.DefaultEditor) spnEndLevel.getEditor()).getTextField().setEditable(false);
        ((JSpinner.DefaultEditor) spnEndLevel.getEditor()).getTextField().setColumns(5);
        spnEndLogs = new JSpinner(new SpinnerNumberModel(100, 0, null, 100));
        ((JSpinner.DefaultEditor) spnEndLogs.getEditor()).getTextField().setColumns(5);

        this.setTitle("crChop " + crChop.version + " Gui");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(Frame.getFrames()[0]);
        this.setLayout(new BorderLayout());

        // LINE_START
        pnlLineStart.setLayout(new BoxLayout(pnlLineStart, BoxLayout.Y_AXIS));
        pnlLineStart.setToolTipText("End Events");
        this.add(pnlLineStart, BorderLayout.LINE_START);

        // TIME
        JPanel pnlTime = new JPanel(new FlowLayout());
        final JCheckBox chkEndTime = new JCheckBox("Time: ");
        pnlTime.add(chkEndTime);
        chkEndTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chkEndTime.isSelected()) {
                    spnEndTime.setEnabled(true);
                } else {
                    spnEndTime.setEnabled(false);
                }
            }
        });
        pnlTime.add(spnEndTime).setEnabled(false);

        // LEVEL
        JPanel pnlLevel = new JPanel(new FlowLayout());
        final JCheckBox chkEndLevel = new JCheckBox("Level: ");
        pnlLevel.add(chkEndLevel);
        chkEndLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chkEndLevel.isSelected()) {
                    spnEndLevel.setEnabled(true);
                } else {
                    spnEndLevel.setEnabled(false);
                }
            }
        });
        pnlLevel.add(spnEndLevel).setEnabled(false);

        JPanel pnlLog = new JPanel(new FlowLayout());
        final JCheckBox chkEndLog = new JCheckBox("Logs: ");
        pnlLog.add(chkEndLog);
        chkEndLog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chkEndLog.isSelected()) {
                    spnEndLogs.setEnabled(true);
                } else {
                    spnEndLogs.setEnabled(false);
                }
            }
        });
        pnlLog.add(spnEndLogs).setEnabled(false);

        pnlLineStart.add(pnlTime);
        pnlLineStart.add(pnlLevel);
        pnlLineStart.add(pnlLog);

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

        // NORTH
        pnlNorth.setLayout(new BoxLayout(pnlNorth, BoxLayout.Y_AXIS));
        this.add(pnlNorth, BorderLayout.NORTH);
        pnlNorth.add(new JLabel("<html><body style='width:" + this.getWidth() + "px'><h3 style='padding: 0; margin: 0;'>News & Announcements: </h3><p style='text-indent: 15px;'>" + crChop.news[Random.nextInt(0, crChop.news.length)] + "</p></body></html>"));
        this.pack();

//        chkRadius.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (chkRadius.isSelected()) {
//                    spnRadius.setVisible(true);
//                    pack();
//                } else {
//                    spnRadius.setVisible(false);
//                    pack();
//                }
//            }
//        });

        spnEndTime.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                pack();
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
                crChop.taskList.clear();
                ctx.controller.resume();

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

                if ((Integer) spnEndTime.getValue() > 0 && chkEndTime.isSelected()) {
                    crChop.taskList.add(new GoalStop(ctx, -1, TimeUnit.MINUTES.toMillis((Integer) spnEndTime.getValue()), -1));
                }

                if ((Integer) spnEndLevel.getValue() > ctx.skills.realLevel(Constants.SKILLS_WOODCUTTING) && chkEndLevel.isSelected()) {
                    crChop.taskList.add(new GoalStop(ctx, (Integer) spnEndLevel.getValue(), -1, -1));
                }
                if (chkEndLog.isSelected()) {
                    crChop.taskList.add(new GoalStop(ctx, -1, -1, (Integer) spnEndLogs.getValue()));
                }

                if (ctx.inventory.id(axeId).count() == 0) {
                    crChop.taskList.add(new Special(ctx));
                }

                crChop.taskList.addAll(Arrays.asList(new Run(ctx), new Inventory(ctx)));

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
            cboTrees.addItem(presets.trees);

        }
    }
}
