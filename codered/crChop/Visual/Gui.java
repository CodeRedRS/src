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
import org.powerbot.script.Area;
import org.powerbot.script.PaintListener;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.BasicQuery;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.GameObject;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dakota on 9/24/2015.
 */
public class Gui extends JFrame implements PaintListener {
    private final ClientContext ctx;
    private ArrayList<GameObject> trees = new ArrayList<GameObject>();

    private Tile startTile;
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
    private Boolean mousehop, axeEquiped, started = false;

    public Gui(final ClientContext ctx, final int axeId, final boolean axeEquiped) {
        this.ctx = ctx;
        this.axeId = axeId;
        this.axeEquiped = axeEquiped;

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

        spnRadius = new JSpinner(new SpinnerNumberModel(10, 10, 50, 5));
        spnRadius.setEnabled(false);

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
        chkScreenshot.setSelected(true);
        pnlLineEnd.add(chkScreenshot);
        pnlLineEnd.add(chkMouseHop);
        pnlLineEnd.add(chkRunFromCombat);
        pnlLineEnd.add(chkChatResponder);
        pnlLineEnd.add(chkRadius);

        // PAGE_END
        pnlPageEnd.setLayout(new FlowLayout());
        this.add(pnlPageEnd, BorderLayout.PAGE_END);
        pnlPageEnd.add(btnStart);
        pnlPageEnd.add(btnScan);
        pnlPageEnd.add(btnCancel);

        updateTreeList();
        if (!ctx.objects.select().action("Bank").isEmpty()) {
            cboMethod.addItem("Bank");
        }


        this.pack();

        // NORTH
        pnlNorth.setLayout(new BoxLayout(pnlNorth, BoxLayout.Y_AXIS));
        this.add(pnlNorth, BorderLayout.NORTH);
        pnlNorth.add(new JLabel("<html><body style='width:" + this.getWidth() + "px'><h3 style='padding: 0; margin: 0;'>News & Announcements: </h3><p style='text-indent: 15px;'>" + crChop.news[Random.nextInt(0, crChop.news.length)] + "</p></body></html>"));
        this.pack();

        // Radius Action
        chkRadius.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chkRadius.isSelected()) {
                    cboPresets.setEnabled(false);
                    cboPresets.setSelectedIndex(0);
                    spnRadius.setEnabled(true);
                    updateTreeList();
                } else {
                    spnRadius.setEnabled(false);
                    cboPresets.setEnabled(true);
                    updateTreeList();
                }
            }
        });

        spnRadius.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateTreeList();
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
                    chkRadius.setEnabled(true);
                    updateTreeList();
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
                cboMethod.removeItem("Bank");

                if (!ctx.objects.select().action("Bank").isEmpty()) {
                    cboMethod.addItem("Bank");
                }

                updateTreeList();

                pack();
            }
        });

        // START BUTTON ACTION
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("Axe Equipped: " + axeEquiped);
                crChop.taskList.clear();
                ctx.controller.resume();

                tree = Tree.valueOf(cboTrees.getSelectedItem().toString());
                mousehop = chkMouseHop.isSelected();

                //METHOD SELECTION
                if (cboMethod.getSelectedItem().toString().toLowerCase().contains("bank")) {
                    if (getPreset() >= 0) {
                        crChop.taskList.addAll(Arrays.asList(new BankDeposit(ctx, axeId, axeEquiped), new ToBank(ctx, Presets.presets[getPreset()].path, Presets.presets[getPreset()].interactive)));
                    } else {
                        crChop.taskList.addAll(Arrays.asList(new BankDeposit(ctx, axeId, axeEquiped), new ToBank(ctx, null, null)));
                    }
                } else if (cboMethod.getSelectedItem().toString().toLowerCase().contains("drop")) {
                    crChop.taskList.add(new Drop(ctx, mousehop));
                }

                if (chkRunFromCombat.isSelected()) {
                    crChop.taskList.add(new Combat(ctx));
                }

                // USING PRESETS
                if (getPreset() >= 0) {
                    crChop.taskList.addAll(Arrays.asList(new ChopDown(ctx, tree, axeId, Presets.presets[getPreset()].area, chkRunFromCombat.isSelected(), treeRadius(), startTile), new ToTree(ctx, Presets.presets[getPreset()].path, Presets.presets[getPreset()].area, tree, Presets.presets[getPreset()].interactive, axeId, treeRadius(), startTile)));
                } else {
                    crChop.taskList.addAll(Arrays.asList(new ChopDown(ctx, tree, axeId, null, chkRunFromCombat.isSelected(), treeRadius(), startTile), new ToTree(ctx, null, null, tree, null, axeId, treeRadius(), startTile)));
                }

                if (cboTrees.getSelectedItem().toString().toLowerCase().contains("magic")) {
                    crChop.taskList.add(new Antiban(ctx, 100));
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

                crChop.taskList.addAll(Arrays.asList(new Run(ctx), new Inventory(ctx), new CloseBadWidgets(ctx)));

                startTile = ctx.players.local().tile();

                started = true;
                updateInfo();
                dispose();
            }
        });

        // CANCEL BUTTON ACTION
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chkScreenshot.setSelected(false);
                ctx.controller.stop();
                dispose();
            }
        });

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                chkScreenshot.setSelected(false);
                ctx.controller.stop();
                dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    public String getMethod() {
        return cboMethod.getSelectedItem().toString();
    }

    public Tree getTree() {
        return Tree.valueOf(cboTrees.getSelectedItem().toString());
    }

    private void updateTreeList() {
        cboTrees.removeAllItems();
        BasicQuery<GameObject> trees;

        if (!chkRadius.isSelected()) {
            trees = ctx.objects.select().action("Chop down").nearest();
        } else {
            trees = ctx.objects.select().action("Chop down").within(startTile, treeRadius()).nearest();
        }

        HashSet<Tree> set = new HashSet<Tree>();
        for (GameObject t : trees) {
            try {
                for (Tree t1 : Tree.values()) {
                    if (t1.name().replace("_", " ").equalsIgnoreCase(t.name())) {
                        if (!set.contains(Tree.valueOf(t.name().toUpperCase().replace(" ", "_")))) {
                            cboTrees.addItem(Tree.valueOf(t.name().toUpperCase().replace(" ", "_")));
                            set.add(Tree.valueOf(t.name().toUpperCase().replace(" ", "_")));
                        }
                    }
                }

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public boolean saveScreenshot() {
        return chkScreenshot.isSelected();
    }

    public boolean chatResponder() {
        return chkChatResponder.isSelected();
    }

    public int treeRadius() {
        if (!chkRadius.isSelected() || !chkRadius.isEnabled()) {
            return -1;
        }
        return Integer.parseInt(spnRadius.getValue().toString());
    }

    public Tile getStartTile() {
        return this.startTile;
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
            chkRadius.setEnabled(false);

        }
    }

    private BasicQuery<GameObject> treeQuery() {
        if (chkRadius.isSelected()) {
            return ctx.objects.select().name(getTree().getName()).within(startTile, treeRadius()).limit(15);
        } else if (getPreset() >= 0) {
            return ctx.objects.select().name(getTree().getName()).within(new Area(Presets.presets[getPreset()].area)).nearest();
        }
        return ctx.objects;
    }

    public void updateInfo() {
        trees.clear();
        treeQuery().addTo(trees);
//        for (GameObject t : treeQuery()) {
//            if (!trees.contains(t) || trees.size() != treeQuery().size()) {
//            }
//        }
    }

    @Override
    public void repaint(Graphics g) {
        Color areaC = new Color(255, 255, 255, 100);
        Color areaBorder = Color.white;
        final Graphics2D g2 = (Graphics2D) g;
        if (!started) {
            this.startTile = ctx.players.local().tile();
        }
        if (chkRadius.isSelected()) {

            Point p = this.startTile.matrix(ctx).mapPoint();

            Tile t = new Tile(this.startTile.x(), this.startTile.y() + treeRadius());
            Point p2 = t.matrix(ctx).mapPoint();
            double distance = Math.sqrt((p.x - p2.x) * (p.x - p2.x) + (p.y - p2.y) * (p.y - p2.y));

            g.setColor(areaC);
            g2.fill(new Ellipse2D.Double(p.x - distance, p.y - distance, distance * 2, distance * 2));
            g.setColor(areaBorder);
            g2.draw(new Ellipse2D.Double(p.x - distance, p.y - distance, distance * 2, distance * 2));

//            for (GameObject tree : trees) {
//                Point treePoint = tree.tile().matrix(ctx).mapPoint();
//                g2.setColor(Color.green);
//                g2.fillOval(treePoint.x - 6, p.y - 6, 12, 12);
//                g2.setColor(new Color(0, 100, 0));
//                g2.drawOval(treePoint.x - 6, p.y - 6, 12, 12);
//            }
        } else if (getPreset() >= 0) {
            Polygon polygon = new Polygon();
            Point point;

            for (Tile t : Presets.presets[getPreset()].area) {
                point = t.matrix(ctx).mapPoint();
                polygon.addPoint(point.x, point.y);
            }

            g.setColor(areaC);
            g.fillPolygon(polygon);
            g.setColor(areaBorder);
            g.drawPolygon(polygon);

//            for (GameObject tree : trees) {
//                if (new Area(Presets.presets[getPreset()].area).contains(tree)) {
//                    Point p = tree.tile().matrix(ctx).mapPoint();
//                    g2.setColor(Color.green);
//                    g2.fillOval(p.x - 6, p.y - 6, 12, 12);
//                    g2.setColor(new Color(0, 100, 0));
//                    g2.drawOval(p.x - 6, p.y - 6, 12, 12);
//                }
//            }
        }
    }
}
