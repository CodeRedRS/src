package codered.crPest.PestUtil;

import codered.crPest.PestTask.*;
import codered.universal.Run;
import codered.universal.crAntiban;
import org.powerbot.script.rt4.ClientContext;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * Created by Dakota on 10/28/2015.
 */
public class PestGui extends JFrame {
    private final static String[] playStyleList = {"Defend Knight", "Random Attack Npcs"/*, "Target Portals", "Shuffle"*/};
    private final static String[] boatLevelList = {"Automatic", "Novice (40)", "Intermediate (70)", "Veteran (100)"};
    private final static String[] enemyStringList = {"Brawler", "Defiler", "Ravager", "Shifter", "Torcher", "Spinner", "Portal", "Splatter"};
    private final static String guiTitle = "crPest Gui " + PestConstants.scriptVersion;
    private final static ClientContext ctx = PestVariables.ctx;

    public PestGui() {
        /**
         * Initialize swing objects
         */
        final JComboBox playStyles = new JComboBox(playStyleList);
        final JComboBox boatLevel = new JComboBox(boatLevelList);
        final JSlider knightRadius = new JSlider(5, 15, PestVariables.knightRadius);
        final JList enemyList = new JList(enemyStringList);
        final JButton start = new JButton("Start");
        final JLabel welcomeMessage = new JLabel(PestConstants.welcomeMessage);

        final JPanel optionsPane = new JPanel();

        /**
         * Enemy List Settings
         */
        enemyList.setSelectedIndices(new int[] {0, 1, 2, 3, 4, 5, 6});
        enemyList.setToolTipText("Ctrl + Click to select multiple enemies");
        PestVariables.enemyNames = new String[]{"Brawler", "Defiler", "Ravager", "Shifter", "Torcher", "Spinner", "Portal"};
        enemyList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                PestVariables.enemyNames = Arrays.copyOf(enemyList.getSelectedValues(), enemyList.getSelectedValues().length, String[].class);
            }
        });

        /**
         * Play Style Settings
         */
        playStyles.setSelectedIndex(0);
        playStyles.setEnabled(true);
        playStyles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionsPane.removeAll();
                switch (playStyles.getSelectedIndex()) {
                    // Defend Knight
                    case 0:
                        optionsPane.add(knightRadius, BorderLayout.EAST);
                        break;

                    // Random Attack Npcs
                    case 1:
                        optionsPane.add(enemyList, BorderLayout.EAST);
                        break;

                    // Target Portals
                    case 2:
                        break;

                    // Shuffle
                    case 3:
                        break;

                    // None Selected(Error)
                    default:
                        break;
                }
                optionsPane.add(welcomeMessage, BorderLayout.NORTH);
                optionsPane.add(start, BorderLayout.SOUTH);
                optionsPane.add(boatLevel, BorderLayout.WEST);
                optionsPane.add(playStyles, BorderLayout.CENTER);
                pack();
            }
        });

        /**
         * Defend Radius Settings
         */
        knightRadius.setMajorTickSpacing(5);
        knightRadius.setMinorTickSpacing(1);
        knightRadius.setSnapToTicks(true);
        knightRadius.setPaintTicks(true);
        knightRadius.setPaintLabels(true);

        /**
         * Default Gui Settings
         */
        setAlwaysOnTop(true);
        setTitle(guiTitle);
        setResizable(false);
        setMinimumSize(new Dimension(450, 100));
        setDefaultLookAndFeelDecorated(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPanel);
        final JTabbedPane tabbedPanel = new JTabbedPane();
        contentPanel.add(tabbedPanel);

        /**
         * Options
         */
        optionsPane.setLayout(new BorderLayout(0, 0));
        optionsPane.add(welcomeMessage, BorderLayout.NORTH);
        optionsPane.add(start, BorderLayout.SOUTH);
        optionsPane.add(knightRadius, BorderLayout.EAST);
        optionsPane.add(boatLevel, BorderLayout.WEST);
        optionsPane.add(playStyles, BorderLayout.CENTER);
        tabbedPanel.addTab("Options", null, optionsPane, "");

        /**
         * Information
         */
        //JPanel infoPane = new JPanel();

        /**
         * Coming Soon
         */
        JPanel comingSoon = new JPanel();
        comingSoon.setEnabled(false);
        comingSoon.add(new JLabel("Hello World!"));
        tabbedPanel.addTab("Coming Soon...", null, comingSoon, "");
        tabbedPanel.setEnabledAt(1, false);

        /**
         * Slider Action
         */
        knightRadius.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                PestVariables.knightRadius = knightRadius.getValue();
            }
        });

        /**
         * Start Button Action
         */
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedStyle = playStyles.getSelectedIndex();
                PestVariables.taskList.clear();
                PestVariables.taskList.addAll(Arrays.asList(new crAntiban(ctx, 999999, PestWidgets.pestPoints.visible()), new ToLander(ctx, boatLevel.getSelectedIndex()), new GameStarted(ctx), new Run(ctx), new Reset(ctx)));
                PestVariables.playStyle = playStyleList[selectedStyle];
                switch (selectedStyle) {
                    // Defend Knight
                    case 0:
                        PestVariables.taskList.addAll(Arrays.asList(new DefendKnight(ctx)/*, new ReturnToKnight(ctx)*/));
                        break;

                    // Random Attack Npcs
                    case 1:
                        PestVariables.taskList.add(new Fight(ctx));
                        break;

                    // Target Portals
                    case 2:
                        break;

                    // Shuffle
                    case 3:
                        break;

                    // None Selected(Error)
                    default:
                        break;
                }
                start.setText("Update Settings");
                setLocation(Frame.getFrames()[0].getX() - getWidth() + 10, Frame.getFrames()[0].getY());
            }
        });
        pack();
        setLocationRelativeTo(Frame.getFrames()[0]);
    }
}
