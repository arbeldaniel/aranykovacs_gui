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

/**
 * A játékot reprezentálja, elindítja és befejezi, tárolja, hogy hány megtanulható genetikai
 * kód van összesen.
 */
public class Game extends JFrame {
    /**
     * Nagy felirat
     */
    private JLabel topSign;
    /**
     * Mező, ahová a játékosok a nevüket írják
     */
    private JTextField playerName;
    /**
     * Játékos törlése gomb
     */
    private JButton removeButton;
    /**
     * Játék indítása gomb
     */
    private JButton playButton;
    /**
     * Játékos hozzáadása gomb
     */
    private JButton addButton;
    /**
     * A menüt megjelenítő JPanel
     */
    private JPanel menuPanel;

    /**
     * játékosok
     */
    private final ArrayList<Player> players;
    /**
     * pálya
     */
    private final ArrayList<Field> map;
    /**
     * soron következő játékos
     */
    private Player playerTurn;
    /**
     * játékosok száma
     */
    private int numPlayers;
    /**
     * kövezkező játékos
     */
    private int lastPlayer;
    /**
     * gráf csúcsainak és szomszédjaiknak listája
     */
    private final ArrayList<ArrayList<Integer>> adjacencyList;
    /**
     * csúcsok száma
     */
    int vertices;

    /**
     * Getter
     * @return  -this
     */
    private Game getGame() {
        return this;
    }

    /**
     * Konstruktor
     */
    public Game() {
        //IntelliJ Swing UI designer által generált GUI inicializálás
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
        //A játékost törlő gomb logikája
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
        //A játékost hozzáadó gomb logikája
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
        //A játék indítása
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
    }

    /**
     * Pálya inicializálása és a játék indítása
     */
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

    /**
     * Léptetés a következő játékosra
     */
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

    /**
     * A játék újraindítása
     */
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

    /**
     * Pálya inicializálása véletlenszerű gráf algoritmussal
     */
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

            ArrayList<Integer> list = adjacencyList.get(i);
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

    /**
     * Konkrét mezők létrehozása
     * @param playerBasedFields -Játékosoktól függő számú mezők darabszáma
     */
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

    /**
     * A gráfon oldal, a pályán szomszédos mező hozzáadása
     * @param v -egyik csúcs/mező
     * @param w -másik csúcs/mező
     */
    private void addEdge(int v, int w) {
        adjacencyList.get(v).add(w);
        adjacencyList.get(w).add(v);
        map.get(v).setNeighbour(map.get(w));
        map.get(w).setNeighbour(map.get(v));
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
        removeButton.setBackground(new Color(-16777216));
        Font removeButtonFont = this.$$$getFont$$$(null, Font.BOLD, -1, removeButton.getFont());
        if (removeButtonFont != null) removeButton.setFont(removeButtonFont);
        removeButton.setForeground(new Color(-1));
        removeButton.setText("-");
        removeButton.setVisible(false);
        panel2.add(removeButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        playButton = new JButton();
        playButton.setBackground(new Color(-16777216));
        Font playButtonFont = this.$$$getFont$$$(null, Font.BOLD, -1, playButton.getFont());
        if (playButtonFont != null) playButton.setFont(playButtonFont);
        playButton.setForeground(new Color(-1));
        playButton.setText("play");
        playButton.setVisible(false);
        panel2.add(playButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addButton = new JButton();
        addButton.setBackground(new Color(-16777216));
        Font addButtonFont = this.$$$getFont$$$(null, Font.BOLD, -1, addButton.getFont());
        if (addButtonFont != null) addButton.setFont(addButtonFont);
        addButton.setForeground(new Color(-1));
        addButton.setText("+");
        addButton.setVisible(true);
        panel2.add(addButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setForeground(new Color(-1));
        panel3.setOpaque(false);
        menuPanel.add(panel3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        playerName = new JTextField();
        playerName.setBackground(new Color(-16777216));
        Font playerNameFont = this.$$$getFont$$$(null, Font.BOLD, -1, playerName.getFont());
        if (playerNameFont != null) playerName.setFont(playerNameFont);
        playerName.setForeground(new Color(-1));
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
