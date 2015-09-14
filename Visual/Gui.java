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
    // JFRAME ELEMENTS
    final JButton btnStart = new JButton("Start Script");
    final JCheckBox chkDebug = new JCheckBox("Debug");
    final JComboBox<String> cboMethod = new JComboBox<>(new String[]{"Drop"});
    final JComboBox<Tree> cboTrees = new JComboBox<>(Tree.values());

    public String method, tree;
    public boolean guiConfigured, debug;
    StartUp startUp = new StartUp();

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
                method = cboMethod.getSelectedItem().toString();
                tree = cboTrees.getSelectedItem().toString();

                StartUp.taskList.addAll(Arrays.asList(new Run(ctx), new Inventory(ctx), new Chop(ctx, tree)));

                if (method.toLowerCase().contains("bank")) {
                    StartUp.taskList.add(new Banking(ctx, method));
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
