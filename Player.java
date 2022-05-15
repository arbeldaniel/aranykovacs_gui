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
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class Player {

    private Virologist v;
    private Game game;

    public JPanel getStepPanel() {
        return stepPanel;
    }

    public JPanel getTakePanel() {
        return takePanel;
    }

    public JPanel getActionPanel() {
        return actionPanel;
    }

    public JPanel getUnctionPanel() {
        return unctionPanel;
    }

    private JPanel playerPanel;
    private JButton step0;
    private JButton takeButton;
    private JButton makeButton;
    private JButton useButton;
    private JButton stealButton;
    private JButton freezeButton;
    private JButton protectionButton;
    private JButton forgetButton;
    private JButton dancingButton;
    private JPanel iconPanel;
    private JPanel stepPanel;
    private JPanel takePanel;
    private JPanel actionPanel;
    private JPanel unctionPanel;
    private JButton step1;
    private JButton step2;
    private JButton step3;
    private JButton step4;
    private JPanel targetPanel;
    private JButton playerButton;
    private JButton targetButton;
    private JLabel winLabel;
    private JPanel winlabelPanel;
    private JButton restartButton;
    private JPanel winbuttonPanel;
    private JButton stayButton;
    private JPanel inventoryPanel;
    private JLabel codeLabel;
    private JLabel unctionLabel;
    private JLabel equipmentLabel;
    private JButton doNothingButton;
    private JButton skipTurnButton;
    private JLabel effectLabel;
    private JLabel fieldLabel;
    private JLabel aminoacidLabel;
    private JLabel nucleotidLabel;
    private JPanel nextPanel;
    private JButton nextButton;
    private JPanel whatPanel;
    private JButton materialButton;
    private JButton equipmentButton;
    private JLabel messageLabel;
    private JButton axeButton;
    private JLabel playerIcon;

    public JPanel getPanel() {
        return playerPanel;
    }

    private boolean usingUnction;
    private Unction usedUnction;

    private Virologist frozen;


    public Player(String name, Game game) {
        v = new Virologist();
        this.game = game;
        usingUnction = false;
        $$$setupUI$$$();
        winLabel.setText("Woohoo, " + name + " has won!");
        playerPanel.setBorder(BorderFactory.createTitledBorder(null, name, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        playerIcon = new JLabel(new ImageIcon("player.png"));
        playerButton.setText(name);
        step0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                takePhase();
                if (step0.isVisible())
                    v.setField(v.getField().getNeighbours().get(0), fieldLabel);
            }
        });
        step1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                takePhase();
                if (step1.isVisible())
                    v.setField(v.getField().getNeighbours().get(1), fieldLabel);
            }
        });
        step2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                takePhase();
                if (step2.isVisible())
                    v.setField(v.getField().getNeighbours().get(2), fieldLabel);
            }
        });
        step3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                takePhase();
                if (step3.isVisible())
                    v.setField(v.getField().getNeighbours().get(3), fieldLabel);
            }
        });
        step4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                takePhase();
                if (step4.isVisible())
                    v.setField(v.getField().getNeighbours().get(4), fieldLabel);
            }
        });
        takeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                v.takeStuff();
                if (v.getCodes().size() == 4) {
                    winbuttonPanel.setVisible(true);
                    winlabelPanel.setVisible(true);
                } else
                    actionPhase();
            }
        });
        makeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makePhase();
            }
        });
        useButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usePhase();
            }
        });
        stealButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextPlayerPhase(actionPanel);
                for (Virologist virologist : v.getField().getVirologists()) {
                    if (virologist != v) {
                        for (Unction effect : virologist.getEffects()) {
                            if (effect.getName().equals("freeze")) {
                                whatPhase();
                                frozen = virologist;
                                break;
                            }
                        }
                    }
                }

            }
        });
        freezeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unctionPanel.setVisible(false);
                if (usingUnction) {
                    targetPanel.setVisible(true);
                    usedUnction = new Freeze();
                } else {
                    v.addUnction(new Freeze());
                    nextPlayerPhase(unctionPanel);
                }
                updateUnctions();
                updateMaterial();
            }
        });
        protectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unctionPanel.setVisible(false);
                if (usingUnction) {
                    targetPanel.setVisible(true);
                    usedUnction = new Protection();
                } else {
                    v.addUnction(new Protection());
                    nextPlayerPhase(unctionPanel);
                }
                updateUnctions();
                updateMaterial();
            }
        });
        dancingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unctionPanel.setVisible(false);
                if (usingUnction) {
                    targetPanel.setVisible(true);
                    usedUnction = new Dancing();
                } else {
                    v.addUnction(new Dancing());
                    nextPlayerPhase(unctionPanel);
                }
                updateUnctions();
                updateMaterial();
            }
        });
        forgetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unctionPanel.setVisible(false);
                if (usingUnction) {
                    targetPanel.setVisible(true);
                    usedUnction = new Forget();
                } else {
                    v.addUnction(new Forget());
                    nextPlayerPhase(unctionPanel);
                }
                updateUnctions();
                updateMaterial();
            }
        });
        playerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                targetPanel.setVisible(false);
                usedUnction.apply(v);
                nextPlayerPhase(targetPanel);
                updateUnctions();
                updateEffects();
            }
        });
        targetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                targetPanel.setVisible(false);
                boolean outcome = false;
                ArrayList<Virologist> virologists = v.getField().getVirologists();
                if (virologists.size() == 1)
                    nextPlayerPhase(targetPanel);
                else {
                    virologists.remove(v);
                    outcome = usedUnction.apply(v, virologists.get(new Random().nextInt(virologists.size())));
                    if (usedUnction.getName().equals("freeze") && outcome) {
                        stealPhase();
                    }

                }
                if (!outcome)
                    nextPlayerPhase(targetPanel);
                updateUnctions();
            }
        });
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.gameReset();
            }
        });
        stayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                takePhase();
            }
        });
        updateMaterial();
        doNothingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionPhase();
            }
        });
        skipTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextPlayerPhase(actionPanel);
            }
        });
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.nextPlayer();
            }
        });
        materialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (frozen.getMaterial().getAminoacid() <= 10) {
                    v.getMaterial().setAminoacid(v.getMaterial().getAminoacid() + frozen.getMaterial().getAminoacid());
                    frozen.getMaterial().setAminoacid(0);
                } else {
                    int stolen = new Random().nextInt(10);
                    v.getMaterial().setAminoacid(stolen);
                    frozen.getMaterial().setAminoacid(frozen.getMaterial().getAminoacid() - stolen);
                }
                if (frozen.getMaterial().getNucletoid() <= 10) {
                    v.getMaterial().setNucletoid(v.getMaterial().getNucletoid() + frozen.getMaterial().getNucletoid());
                    frozen.getMaterial().setNucletoid(0);
                } else {
                    int stolen = new Random().nextInt(10);
                    v.getMaterial().setNucletoid(stolen);
                    frozen.getMaterial().setNucletoid(frozen.getMaterial().getNucletoid() - stolen);
                }
                nextPlayerPhase(whatPanel);
            }
        });
        equipmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (frozen.getEquipments().size() > 0) {
                    int stolen = new Random().nextInt(frozen.getEquipments().size());
                    v.addEquipment(frozen.getEquipments().get(stolen));
                    frozen.removeEquipment(frozen.getEquipments().get(stolen));
                }
                nextPlayerPhase(whatPanel);
            }
        });
        axeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Virologist virologist : v.getField().getVirologists()) {
                    if (virologist != v) {
                        virologist.setDead(true);
                        for (Equipment equipment : v.getEquipments()) {
                            if (equipment.getName().equals("axe"))
                                v.removeEquipment(equipment);
                        }
                        break;
                    }
                }
                nextPlayerPhase(unctionPanel);
            }
        });
    }

    public void panelReset() {
        if (v.getDead()) {
            nextPlayerPhase(stepPanel);
            messageLabel.setText("you're dead");
        } else {
            nextPanel.setVisible(false);
            stepPanel.setVisible(true);
            int neighbours = v.getField().getNeighbours().size();
            boolean freeze = false, dancing = false, bear = false;
            for (Unction effect : v.getEffects()) {
                switch (effect.getName()) {
                    case "freeze":
                        freeze = true;
                        int f = effect.getTime() - 1;
                        if (f == 0)
                            v.removeEffect(effect);
                        else effect.setTime(f);
                        break;
                    case "dancing":
                        dancing = true;
                        int d = effect.getTime() - 1;
                        if (d == 0)
                            v.removeEffect(effect);
                        else effect.setTime(d);
                        break;
                    case "bear":
                        bear = true;
                        break;
                    default:
                        break;
                }
            }
            if (bear)
                bearPhase();
            else if (freeze)
                actionPhase();
            else if (dancing) {
                v.setField(v.getField().getNeighbours().get(new Random().nextInt(v.getField().getNeighbours().size())), fieldLabel);
                takePhase();
            } else {
                for (int i = 0; i < neighbours; ++i)
                    stepButtonVisibility(i, true);
                for (int i = neighbours; i < 5; ++i)
                    stepButtonVisibility(i, false);
            }
        }
        updateEffects();
        updateEquipments();
        updateMaterial();
    }

    private boolean updateUnctions() {
        boolean ret = false;
        unctionLabel.setText("Unctions:");
        ArrayList<Unction> currentUnctions = v.getUnctions();
        for (Unction currentUnction : currentUnctions) {
            unctionLabel.setText(unctionLabel.getText() + " " + currentUnction.getName() + ",");
            ret = true;
        }
        boolean axe = false;
        for (Equipment equipment : v.getEquipments()) {
            if (equipment.getName().equals("axe")) {
                axe = true;
                ret = true;
            }
        }
        if (axe)
            axeButton.setVisible(true);
        else axeButton.setVisible(false);

        return ret;
    }

    private boolean updateCodes() {
        boolean ret = false;
        codeLabel.setText("Codes:");
        ArrayList<Code> currentCodes = v.getCodes();
        for (Code currentCode : currentCodes) {
            codeLabel.setText(codeLabel.getText() + " " + currentCode.getCodeID() + ",");
            ret = true;
        }
        return ret;
    }

    private boolean updateEffects() {
        boolean ret = false;
        effectLabel.setText("Effects:");
        ArrayList<Unction> effects = v.getEffects();
        for (Unction effect : effects) {
            effectLabel.setText(effectLabel.getText() + " " + effect.getName() + ",");
            ret = true;
        }
        return ret;
    }

    private void updateEquipments() {
        equipmentLabel.setText("Equipments:");
        ArrayList<Equipment> currentEquipments = v.getEquipments();
        for (Equipment currentEquipment : currentEquipments) {
            equipmentLabel.setText(equipmentLabel.getText() + " " + currentEquipment.getName() + ",");
        }
    }

    private void updateMaterial() {
        aminoacidLabel.setText("Aminoacid: " + v.getMaterial().getAminoacid());
        nucleotidLabel.setText("Nucleotid: " + v.getMaterial().getNucletoid());
    }

    private void stepButtonVisibility(int x, boolean s) {
        switch (x) {
            case 0:
                step0.setVisible(s);
                break;
            case 1:
                step1.setVisible(s);
                break;
            case 2:
                step2.setVisible(s);
                break;
            case 3:
                step3.setVisible(s);
                break;
            case 4:
                step4.setVisible(s);
                break;
            default:
                break;
        }
    }

    public void setField(Field f) {
        v.setField(f, fieldLabel);
    }

    private void takePhase() {
        stepPanel.setVisible(false);
        takePanel.setVisible(true);
    }

    private void actionPhase() {
        stepPanel.setVisible(false);
        takePanel.setVisible(false);
        actionPanel.setVisible(true);
        makeButton.setVisible(true);
        for (Unction effect : v.getEffects()) {
            if (effect.getName().equals("forget")) {
                int f = effect.getTime() - 1;
                if (f == 0)
                    v.removeEffect(effect);
                else effect.setTime(f);
                makeButton.setVisible(false);
            }
        }
        if (makeButton.isVisible())
            makeButton.setVisible(updateCodes());
        useButton.setVisible(updateUnctions());
        updateMaterial();
        updateEquipments();
    }


    private void makePhase() {
        actionPanel.setVisible(false);
        unctionPanel.setVisible(true);
        freezeButton.setVisible(false);
        protectionButton.setVisible(false);
        dancingButton.setVisible(false);
        forgetButton.setVisible(false);
        for (Code code : v.getCodes()) {
            switch (code.getCodeID()) {
                case "frz":
                    freezeButton.setVisible(true);
                    break;
                case "prtctn":
                    protectionButton.setVisible(true);
                    break;
                case "dncng":
                    dancingButton.setVisible(true);
                    break;
                case "frgt":
                    forgetButton.setVisible(true);
                    break;
                default:
                    break;
            }
        }

        usingUnction = false;
    }

    private void usePhase() {
        actionPanel.setVisible(false);
        unctionPanel.setVisible(true);
        freezeButton.setVisible(false);
        protectionButton.setVisible(false);
        dancingButton.setVisible(false);
        forgetButton.setVisible(false);
        for (Unction unction : v.getUnctions()) {
            switch (unction.getName()) {
                case "freeze":
                    freezeButton.setVisible(true);
                    break;
                case "protection":
                    protectionButton.setVisible(true);
                    break;
                case "dancing":
                    dancingButton.setVisible(true);
                    break;
                case "forget":
                    forgetButton.setVisible(true);
                    break;
                default:
                    break;
            }
        }
        usingUnction = true;
    }

    private void nextPlayerPhase(JPanel jp) {
        for (Unction effect : v.getEffects()) {
            if (effect.getName().equals("protection")) {
                int p = effect.getTime() - 1;
                if (p == 0)
                    v.removeEffect(effect);
                else effect.setTime(p);
            }
        }
        jp.setVisible(false);
        nextPanel.setVisible(true);
    }

    private void stealPhase() {
        actionPanel.setVisible(true);
        targetPanel.setVisible(false);
        makeButton.setVisible(false);
        useButton.setVisible(false);
    }

    private void whatPhase() {
        actionPanel.setVisible(false);
        nextPanel.setVisible(false);
        whatPanel.setVisible(true);
    }

    private void bearPhase() {
        v.setField(v.getField().getNeighbours().get(new Random().nextInt(v.getField().getNeighbours().size())), fieldLabel);
        for (Virologist virologist : v.getField().getVirologists()) {
            if (virologist != v)
                if (new Bear().apply(v, virologist))
                    return;
                else v.setDead(true);
        }
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
        playerPanel.setLayout(new GridLayoutManager(3, 9, new Insets(0, 0, 0, 0), -1, -1));
        playerPanel.setBorder(BorderFactory.createTitledBorder(null, "player1", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        iconPanel = new JPanel();
        iconPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        iconPanel.setOpaque(false);
        playerPanel.add(iconPanel, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setIcon(new ImageIcon(getClass().getResource("/player2.png")));
        label1.setText("");
        iconPanel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        stepPanel = new JPanel();
        stepPanel.setLayout(new GridLayoutManager(6, 1, new Insets(0, 0, 0, 0), -1, -1));
        stepPanel.setOpaque(false);
        stepPanel.setVisible(true);
        playerPanel.add(stepPanel, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        step0 = new JButton();
        step0.setBackground(new Color(-16777216));
        Font step0Font = this.$$$getFont$$$(null, Font.BOLD, -1, step0.getFont());
        if (step0Font != null) step0.setFont(step0Font);
        step0.setForeground(new Color(-1));
        step0.setHideActionText(false);
        step0.setText("->");
        stepPanel.add(step0, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        step1 = new JButton();
        step1.setBackground(new Color(-16777216));
        Font step1Font = this.$$$getFont$$$(null, Font.BOLD, -1, step1.getFont());
        if (step1Font != null) step1.setFont(step1Font);
        step1.setForeground(new Color(-1));
        step1.setHideActionText(false);
        step1.setText("->");
        stepPanel.add(step1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        step2 = new JButton();
        step2.setBackground(new Color(-16777216));
        Font step2Font = this.$$$getFont$$$(null, Font.BOLD, -1, step2.getFont());
        if (step2Font != null) step2.setFont(step2Font);
        step2.setForeground(new Color(-1));
        step2.setHideActionText(false);
        step2.setText("->");
        stepPanel.add(step2, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        step3 = new JButton();
        step3.setBackground(new Color(-16777216));
        Font step3Font = this.$$$getFont$$$(null, Font.BOLD, -1, step3.getFont());
        if (step3Font != null) step3.setFont(step3Font);
        step3.setForeground(new Color(-1));
        step3.setHideActionText(false);
        step3.setText("->");
        stepPanel.add(step3, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        step4 = new JButton();
        step4.setBackground(new Color(-16777216));
        Font step4Font = this.$$$getFont$$$(null, Font.BOLD, -1, step4.getFont());
        if (step4Font != null) step4.setFont(step4Font);
        step4.setForeground(new Color(-1));
        step4.setHideActionText(false);
        step4.setText("->");
        stepPanel.add(step4, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        stayButton = new JButton();
        stayButton.setBackground(new Color(-16777216));
        Font stayButtonFont = this.$$$getFont$$$(null, Font.BOLD, -1, stayButton.getFont());
        if (stayButtonFont != null) stayButton.setFont(stayButtonFont);
        stayButton.setForeground(new Color(-1));
        stayButton.setHideActionText(false);
        stayButton.setText("stay");
        stepPanel.add(stayButton, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        takePanel = new JPanel();
        takePanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        takePanel.setOpaque(false);
        takePanel.setVisible(false);
        playerPanel.add(takePanel, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        takeButton = new JButton();
        takeButton.setBackground(new Color(-16777216));
        Font takeButtonFont = this.$$$getFont$$$(null, Font.BOLD, -1, takeButton.getFont());
        if (takeButtonFont != null) takeButton.setFont(takeButtonFont);
        takeButton.setForeground(new Color(-1));
        takeButton.setText("take");
        takeButton.setToolTipText("");
        takePanel.add(takeButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        doNothingButton = new JButton();
        doNothingButton.setBackground(new Color(-16777216));
        Font doNothingButtonFont = this.$$$getFont$$$(null, Font.BOLD, -1, doNothingButton.getFont());
        if (doNothingButtonFont != null) doNothingButton.setFont(doNothingButtonFont);
        doNothingButton.setForeground(new Color(-1));
        doNothingButton.setText("do nothing");
        takePanel.add(doNothingButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fieldLabel = new JLabel();
        Font fieldLabelFont = this.$$$getFont$$$(null, Font.BOLD, -1, fieldLabel.getFont());
        if (fieldLabelFont != null) fieldLabel.setFont(fieldLabelFont);
        fieldLabel.setForeground(new Color(-16777216));
        fieldLabel.setText("Field");
        takePanel.add(fieldLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        actionPanel = new JPanel();
        actionPanel.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        actionPanel.setOpaque(false);
        actionPanel.setVisible(false);
        playerPanel.add(actionPanel, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        makeButton = new JButton();
        makeButton.setBackground(new Color(-16777216));
        Font makeButtonFont = this.$$$getFont$$$(null, Font.BOLD, -1, makeButton.getFont());
        if (makeButtonFont != null) makeButton.setFont(makeButtonFont);
        makeButton.setForeground(new Color(-1));
        makeButton.setText("make");
        actionPanel.add(makeButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        useButton = new JButton();
        useButton.setBackground(new Color(-16777216));
        Font useButtonFont = this.$$$getFont$$$(null, Font.BOLD, -1, useButton.getFont());
        if (useButtonFont != null) useButton.setFont(useButtonFont);
        useButton.setForeground(new Color(-1));
        useButton.setText("use");
        actionPanel.add(useButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        stealButton = new JButton();
        stealButton.setBackground(new Color(-16777216));
        Font stealButtonFont = this.$$$getFont$$$(null, Font.BOLD, -1, stealButton.getFont());
        if (stealButtonFont != null) stealButton.setFont(stealButtonFont);
        stealButton.setForeground(new Color(-1));
        stealButton.setText("steal");
        actionPanel.add(stealButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        skipTurnButton = new JButton();
        skipTurnButton.setBackground(new Color(-16777216));
        Font skipTurnButtonFont = this.$$$getFont$$$(null, Font.BOLD, -1, skipTurnButton.getFont());
        if (skipTurnButtonFont != null) skipTurnButton.setFont(skipTurnButtonFont);
        skipTurnButton.setForeground(new Color(-1));
        skipTurnButton.setText("skip turn");
        actionPanel.add(skipTurnButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        unctionPanel = new JPanel();
        unctionPanel.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        unctionPanel.setOpaque(false);
        unctionPanel.setVisible(false);
        playerPanel.add(unctionPanel, new GridConstraints(1, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        freezeButton = new JButton();
        freezeButton.setBackground(new Color(-16777216));
        Font freezeButtonFont = this.$$$getFont$$$(null, Font.BOLD, -1, freezeButton.getFont());
        if (freezeButtonFont != null) freezeButton.setFont(freezeButtonFont);
        freezeButton.setForeground(new Color(-1));
        freezeButton.setText("freeze");
        unctionPanel.add(freezeButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        protectionButton = new JButton();
        protectionButton.setBackground(new Color(-16777216));
        Font protectionButtonFont = this.$$$getFont$$$(null, Font.BOLD, -1, protectionButton.getFont());
        if (protectionButtonFont != null) protectionButton.setFont(protectionButtonFont);
        protectionButton.setForeground(new Color(-1));
        protectionButton.setText("protection");
        unctionPanel.add(protectionButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        forgetButton = new JButton();
        forgetButton.setBackground(new Color(-16777216));
        Font forgetButtonFont = this.$$$getFont$$$(null, Font.BOLD, -1, forgetButton.getFont());
        if (forgetButtonFont != null) forgetButton.setFont(forgetButtonFont);
        forgetButton.setForeground(new Color(-1));
        forgetButton.setText("forget");
        unctionPanel.add(forgetButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        dancingButton = new JButton();
        dancingButton.setBackground(new Color(-16777216));
        Font dancingButtonFont = this.$$$getFont$$$(null, Font.BOLD, -1, dancingButton.getFont());
        if (dancingButtonFont != null) dancingButton.setFont(dancingButtonFont);
        dancingButton.setForeground(new Color(-1));
        dancingButton.setText("dancing");
        unctionPanel.add(dancingButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        axeButton = new JButton();
        axeButton.setBackground(new Color(-16777216));
        Font axeButtonFont = this.$$$getFont$$$(null, Font.BOLD, -1, axeButton.getFont());
        if (axeButtonFont != null) axeButton.setFont(axeButtonFont);
        axeButton.setForeground(new Color(-1));
        axeButton.setText("axe");
        unctionPanel.add(axeButton, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        targetPanel = new JPanel();
        targetPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        targetPanel.setOpaque(false);
        targetPanel.setVisible(false);
        playerPanel.add(targetPanel, new GridConstraints(1, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        playerButton = new JButton();
        playerButton.setBackground(new Color(-16777216));
        Font playerButtonFont = this.$$$getFont$$$(null, Font.BOLD, -1, playerButton.getFont());
        if (playerButtonFont != null) playerButton.setFont(playerButtonFont);
        playerButton.setForeground(new Color(-1));
        playerButton.setText("myself");
        targetPanel.add(playerButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        targetButton = new JButton();
        targetButton.setBackground(new Color(-16777216));
        Font targetButtonFont = this.$$$getFont$$$(null, Font.BOLD, -1, targetButton.getFont());
        if (targetButtonFont != null) targetButton.setFont(targetButtonFont);
        targetButton.setForeground(new Color(-1));
        targetButton.setText("someone else");
        targetPanel.add(targetButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        inventoryPanel = new JPanel();
        inventoryPanel.setLayout(new GridLayoutManager(6, 1, new Insets(0, 0, 0, 0), -1, -1));
        inventoryPanel.setForeground(new Color(-16777216));
        inventoryPanel.setOpaque(false);
        playerPanel.add(inventoryPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        unctionLabel = new JLabel();
        Font unctionLabelFont = this.$$$getFont$$$(null, Font.BOLD, -1, unctionLabel.getFont());
        if (unctionLabelFont != null) unctionLabel.setFont(unctionLabelFont);
        unctionLabel.setForeground(new Color(-16777216));
        unctionLabel.setText("Unctions:");
        inventoryPanel.add(unctionLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        codeLabel = new JLabel();
        Font codeLabelFont = this.$$$getFont$$$(null, Font.BOLD, -1, codeLabel.getFont());
        if (codeLabelFont != null) codeLabel.setFont(codeLabelFont);
        codeLabel.setForeground(new Color(-16777216));
        codeLabel.setText("Codes:");
        inventoryPanel.add(codeLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        equipmentLabel = new JLabel();
        Font equipmentLabelFont = this.$$$getFont$$$(null, Font.BOLD, -1, equipmentLabel.getFont());
        if (equipmentLabelFont != null) equipmentLabel.setFont(equipmentLabelFont);
        equipmentLabel.setForeground(new Color(-16777216));
        equipmentLabel.setText("Equipments:");
        inventoryPanel.add(equipmentLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        effectLabel = new JLabel();
        Font effectLabelFont = this.$$$getFont$$$(null, Font.BOLD, -1, effectLabel.getFont());
        if (effectLabelFont != null) effectLabel.setFont(effectLabelFont);
        effectLabel.setForeground(new Color(-16777216));
        effectLabel.setText("Active effects:");
        inventoryPanel.add(effectLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        aminoacidLabel = new JLabel();
        Font aminoacidLabelFont = this.$$$getFont$$$(null, Font.BOLD, -1, aminoacidLabel.getFont());
        if (aminoacidLabelFont != null) aminoacidLabel.setFont(aminoacidLabelFont);
        aminoacidLabel.setForeground(new Color(-16777216));
        aminoacidLabel.setText("Aminoacid:");
        inventoryPanel.add(aminoacidLabel, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nucleotidLabel = new JLabel();
        Font nucleotidLabelFont = this.$$$getFont$$$(null, Font.BOLD, -1, nucleotidLabel.getFont());
        if (nucleotidLabelFont != null) nucleotidLabel.setFont(nucleotidLabelFont);
        nucleotidLabel.setForeground(new Color(-16777216));
        nucleotidLabel.setText("Nucleotid:");
        inventoryPanel.add(nucleotidLabel, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        winbuttonPanel = new JPanel();
        winbuttonPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        winbuttonPanel.setOpaque(false);
        winbuttonPanel.setVisible(false);
        playerPanel.add(winbuttonPanel, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        restartButton = new JButton();
        restartButton.setBackground(new Color(-16777216));
        restartButton.setForeground(new Color(-1));
        restartButton.setText("restart");
        winbuttonPanel.add(restartButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        winlabelPanel = new JPanel();
        winlabelPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        winlabelPanel.setEnabled(false);
        winlabelPanel.setOpaque(false);
        winlabelPanel.setVisible(false);
        playerPanel.add(winlabelPanel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        winLabel = new JLabel();
        Font winLabelFont = this.$$$getFont$$$(null, Font.BOLD, -1, winLabel.getFont());
        if (winLabelFont != null) winLabel.setFont(winLabelFont);
        winLabel.setForeground(new Color(-16777216));
        winLabel.setText("winLabel");
        winlabelPanel.add(winLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nextPanel = new JPanel();
        nextPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        nextPanel.setOpaque(false);
        nextPanel.setVisible(false);
        playerPanel.add(nextPanel, new GridConstraints(1, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        nextButton = new JButton();
        nextButton.setBackground(new Color(-16777216));
        nextButton.setForeground(new Color(-1));
        nextButton.setText("next player");
        nextPanel.add(nextButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        messageLabel = new JLabel();
        Font messageLabelFont = this.$$$getFont$$$(null, Font.BOLD, -1, messageLabel.getFont());
        if (messageLabelFont != null) messageLabel.setFont(messageLabelFont);
        messageLabel.setText("sample text lol xd");
        messageLabel.setVisible(false);
        nextPanel.add(messageLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        whatPanel = new JPanel();
        whatPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        whatPanel.setVisible(false);
        playerPanel.add(whatPanel, new GridConstraints(1, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        materialButton = new JButton();
        materialButton.setBackground(new Color(-16777216));
        Font materialButtonFont = this.$$$getFont$$$(null, Font.BOLD, -1, materialButton.getFont());
        if (materialButtonFont != null) materialButton.setFont(materialButtonFont);
        materialButton.setForeground(new Color(-1));
        materialButton.setText("material");
        whatPanel.add(materialButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        equipmentButton = new JButton();
        equipmentButton.setBackground(new Color(-16777216));
        Font equipmentButtonFont = this.$$$getFont$$$(null, Font.BOLD, -1, equipmentButton.getFont());
        if (equipmentButtonFont != null) equipmentButton.setFont(equipmentButtonFont);
        equipmentButton.setForeground(new Color(-1));
        equipmentButton.setText("equipment");
        whatPanel.add(equipmentButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        return playerPanel;
    }

    private void createUIComponents() {
        playerPanel = new JPanel() {
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
    }
}
