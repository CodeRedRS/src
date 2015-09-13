package crChop.Visual;

import crChop.Enums.Tree;
import crChop.Tasks.*;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

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
    // VARIABLES
    public static String selectedTree, method;
    final Tree[] treeTypes = Tree.values();
    // JFRAME ELEMENTS
    final JComboBox<Tree> cboTrees = new JComboBox<Tree>(treeTypes);
    final JButton btnStart = new JButton("Start Script");
    final JCheckBox chkDebug = new JCheckBox("Debug");
    public boolean guiConfigured, debug;
    DefaultComboBoxModel<String> cboDefault = new DefaultComboBoxModel<>(new String[]{"Drop"});
    final JComboBox<String> cboMethod = new JComboBox<>(cboDefault);

    public Gui(final ClientContext ctx) {
        if (isVisible())
            Paint.status = "GUI";

        setLayout(new FlowLayout());
        setTitle("crChop GUI");
        setSize(550, 250);
        setVisible(true);

        add(cboTrees);
        add(cboMethod);
//        add(chkDebug);
        add(btnStart);

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

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final Tree t = (Tree) cboTrees.getSelectedItem();
                selectedTree = t.getName();
                method = (String) cboMethod.getSelectedItem();
                System.out.println("Selected Bank: " + cboMethod.getSelectedItem());

                StartUp.taskList.addAll(Arrays.asList(new Run(ctx), new Inventory(ctx), new Chop(ctx)));

                if (method.toLowerCase().contains("bank")) {
                    StartUp.taskList.add(new Banking(ctx));
                } else if (method.toLowerCase().contains("drop")) {
                    StartUp.taskList.add(new Drop(ctx));
                }

                if (chkDebug.isSelected()) {
                    debug = true;
                }

                guiConfigured = true;
                dispose();
            }
        });
    }
}
