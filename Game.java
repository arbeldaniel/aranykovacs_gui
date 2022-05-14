import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class Game extends JFrame {
    private JLabel topSign;
    private JTextField playerName;
    private JButton removeButton;
    private JButton playButton;
    private JButton addButton;
    private JPanel menuPanel;

    private final ArrayList<Player> players;
    private final ArrayList<Field> map;
    private Player playerTurn;
    private int numPlayers;
    private int lastPlayer;

    private final ArrayList<ArrayList<Integer>> adjacencyList;
    int vertices;

    private Game getGame() {
        return this;
    }

    public Game() {
        $$$setupUI$$$();

        players = new ArrayList<>();
        map = new ArrayList<>();
        adjacencyList = new ArrayList<>();
        numPlayers = 0;
        setContentPane(menuPanel);
        setTitle("aranykovacs - projlab");
        setSize(600, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800, 525);
        setResizable(false);
        setVisible(true);
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                players.remove(numPlayers - 1);
                --numPlayers;
                playerName.setText("player " + (numPlayers + 1));
                switch (numPlayers) {
                    case 0:
                        removeButton.setVisible(false);
                        break;
                    case 1:
                        playButton.setVisible(false);
                        break;
                    case 9:
                        addButton.setVisible(true);
                        playerName.setVisible(true);
                        break;
                    default:
                        break;
                }
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                players.add(new Player(playerName.getText(), getGame()));
                ++numPlayers;
                playerName.setText("player " + (numPlayers + 1));
                switch (numPlayers) {
                    case 1:
                        removeButton.setVisible(true);
                        break;
                    case 2:
                        playButton.setVisible(true);
                        break;
                    case 10:
                        addButton.setVisible(false);
                        playerName.setVisible(false);
                    default:
                        break;
                }
            }
        });
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
    }

    private void startGame() {
        lastPlayer = 0;
        initMap();
        Random rand = new Random();
        for (int i = 0; i < numPlayers; ++i)
            players.get(i).setField(map.get(rand.nextInt(vertices)));
        playerTurn = players.get(lastPlayer);
        setContentPane(playerTurn.getPanel());
        validate();
    }

    public void nextPlayer() {
        if (lastPlayer == numPlayers - 1)
            lastPlayer = 0;
        else
            lastPlayer++;
        playerTurn = players.get(lastPlayer);
        playerTurn.panelReset();
        setContentPane(playerTurn.getPanel());
        validate();
    }

    public void gameReset() {
        players.clear();
        numPlayers = 0;
        playerName.setText("player 1");
        removeButton.setVisible(false);
        addButton.setVisible(true);
        playButton.setVisible(false);
        setContentPane(menuPanel);
        validate();
    }

    private void initMap() {
        map.clear();
        if (adjacencyList != null)
            adjacencyList.clear();
        int playerBasedFields = numPlayers * 4;
        vertices = 8 + 2 * playerBasedFields;
        Random rand = new Random();

        addFields(playerBasedFields);
        for (int i = 0; i < vertices; ++i)
            adjacencyList.add(new ArrayList<>());

        for (int i = 0; i < vertices; ++i) {
            for (int j = 0; j < 2; ++j) {
                int v = rand.nextInt(vertices);

                while ((i == v) ||
                        adjacencyList.get(i).contains(v) ||
                        adjacencyList.get(v).size() == 5)
                    v = rand.nextInt(vertices);

                addEdge(i, v);

                if (adjacencyList.get(i).size() == 5)
                    break;
            }
        }

        for (int i = 0; i < adjacencyList.size(); i++) {
            System.out.print(i + " -> { ");

            ArrayList<Integer> list
                    = adjacencyList.get(i);

            if (list.isEmpty())
                System.out.print(" No adjacent vertices ");
            else {
                int size = list.size();
                for (int j = 0; j < size; j++) {

                    System.out.print(list.get(j));
                    if (j < size - 1)
                        System.out.print(" , ");
                }
            }

            System.out.println("}");
            System.out.println(adjacencyList.get(i).size());
        }
    }

    private void addFields(int playerBasedFields) {
        for (int i = 0; i < playerBasedFields; ++i) {
            map.add(new Field());
            map.add(new Storage());
        }
        map.add(new Laboratory(new Code("frz")));
        map.add(new Laboratory(new Code("prtctn")));
        map.add(new Laboratory(new Code("dncng")));
        map.add(new Laboratory(new Code("frgt")));
        map.add(new Shelter(new Bag()));
        map.add(new Shelter(new Cloak()));
        map.add(new Shelter(new Glove()));
        map.add(new Shelter(new Axe()));
    }

    private void addEdge(int v, int w) {
        adjacencyList.get(v).add(w);
        adjacencyList.get(w).add(v);
        map.get(v).setNeighbour(map.get(w));
        map.get(w).setNeighbour(map.get(v));
    }

    public void endGame() {

    }


    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        menuPanel.setLayout(new GridLayoutManager(3, 1, new Insets(10, 10, 10, 10), 10, 10));
        menuPanel.setEnabled(true);
        menuPanel.setForeground(new Color(-1));
        menuPanel.setVerifyInputWhenFocusTarget(true);
        menuPanel.setVisible(true);
        menuPanel.setBorder(BorderFactory.createTitledBorder(null, "menu", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setForeground(new Color(-1));
        panel1.setOpaque(false);
        menuPanel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        topSign = new JLabel();
        Font topSignFont = this.$$$getFont$$$(null, Font.BOLD, 20, topSign.getFont());
        if (topSignFont != null) topSign.setFont(topSignFont);
        topSign.setForeground(new Color(-16777216));
        topSign.setText("THE WORLD OF THE WORLDLESS VIROLOGISTS");
        panel1.add(topSign, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setForeground(new Color(-1));
        panel2.setOpaque(false);
        panel2.setVisible(true);
        menuPanel.add(panel2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        removeButton = new JButton();
        Font removeButtonFont = this.$$$getFont$$$(null, Font.BOLD, -1, removeButton.getFont());
        if (removeButtonFont != null) removeButton.setFont(removeButtonFont);
        removeButton.setText("-");
        removeButton.setVisible(false);
        panel2.add(removeButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        playButton = new JButton();
        Font playButtonFont = this.$$$getFont$$$(null, Font.BOLD, -1, playButton.getFont());
        if (playButtonFont != null) playButton.setFont(playButtonFont);
        playButton.setText("play");
        playButton.setVisible(false);
        panel2.add(playButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addButton = new JButton();
        Font addButtonFont = this.$$$getFont$$$(null, Font.BOLD, -1, addButton.getFont());
        if (addButtonFont != null) addButton.setFont(addButtonFont);
        addButton.setText("+");
        addButton.setVisible(true);
        panel2.add(addButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setForeground(new Color(-1));
        panel3.setOpaque(false);
        menuPanel.add(panel3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        playerName = new JTextField();
        playerName.setText("player 1");
        panel3.add(playerName, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return menuPanel;
    }

    private void createUIComponents() {
        menuPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Image image = null;

                try {
                    image = ImageIO.read(new File("bg.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                super.paintComponent(g);
                g.drawImage(image, 0, 0, null);
            }
        };
        menuPanel.setBorder(BorderFactory.createTitledBorder(null, "menu", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, Color.white));
    }
}
