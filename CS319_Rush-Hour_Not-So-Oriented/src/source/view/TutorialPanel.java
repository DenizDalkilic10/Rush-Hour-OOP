package source.view;

import source.controller.GameEngine;
import source.controller.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;

@SuppressWarnings("Duplicates")
public class TutorialPanel extends JPanel {

    private JButton forwardButton;
    private JButton backButton;
    private JButton cancelButton;
    private JLabel currentArrow;
    private GuiPanelManager guiManager;
    private int index; //for activating current tutorial
    private boolean isTutorialActive; //will be accessed from guiManager
    private ArrayList<JLabel> tutorials = new ArrayList<>();
    private ArrayList<JLabel> backgrounds = new ArrayList<>(); //for images and gifs
    private ArrayList<Point> arrowCooardinates = new ArrayList<>();

    private JLabel labelBackground;
    private JLabel left;
    private JLabel right;
    private JLabel up;
    private JLabel down;
    private JLabel activeBackground = null;
    private JLabel activeLabel = null;
    private JLabel coverPage = LoadMediaToLabel("/image/tutorial_Backgrounds/cover.png");
    private JLabel coverInfo = LoadMediaToLabel("/image/tutorial_Labels/coverInfo.png");

    private BufferedImage background;
    private BufferedImage next ;
    private BufferedImage nextH;
    private BufferedImage back ;
    private BufferedImage backH;
    private BufferedImage backD;
    private BufferedImage cancel;
    private BufferedImage cancelH;

    TutorialPanel(boolean isTutorialActive, GuiPanelManager _guiManager) { //yeni playersa true olcak ve oyun ilk defa açılıyosa
        setLayout(null);

        guiManager = _guiManager;

        next = GuiPanelManager.getInstance().LoadImage("image/icons/nextO.png");
        nextH = GuiPanelManager.getInstance().LoadImage("image/icons/nextOH.png");
        back = GuiPanelManager.getInstance().LoadImage("image/icons/backO.png");
        backH = GuiPanelManager.getInstance().LoadImage("image/icons/backOH.png");
        backD = GuiPanelManager.getInstance().LoadImage("image/icons/back_OD.png");
        cancel = GuiPanelManager.getInstance().LoadImage("image/icons/quitO.png");
        cancelH = GuiPanelManager.getInstance().LoadImage("image/icons/quitOH.png");

        index = -1;
        this.isTutorialActive = isTutorialActive;

        setPreferredSize(new Dimension(guiManager.panelWidth, guiManager.panelHeight));
        createComponents();
        setBoundsOfComponents();
        update();
        this.setVisible(isTutorialActive);
    }

    void update() {
        if (index == tutorials.size()) {
            backButton.setEnabled(false);
            guiManager.setPanelVisible("MainMenu");
            index = -1;
        } else {
            backButton.setEnabled(index != -1);
            setLabels();
        }

    }


    private void setLabels() {
        for (int i = 0; i < backgrounds.size(); i++) {
            backgrounds.get(i).setVisible(false);
        }
        coverInfo.setVisible(false);
        coverPage.setVisible(false);
        left.setVisible(false);
        right.setVisible(false);
        up.setVisible(false);
        down.setVisible(false);
        if (index == -1) {
            coverInfo.setVisible(true);
            activeBackground = coverPage;
            activeLabel = coverInfo;
            updateLabelBackground(-200, 130);
        } else if (index >= 0 && index < 5) {
            if (index == 2) {

                currentArrow = down;
            } else if (index == 4) {

                currentArrow = right;
            } else {

                currentArrow = up;
            }
            activeBackground = backgrounds.get(0);
            updateLabelBackground(50, -22);
        } else if (index >= 5 && index < 10) {

            currentArrow = up;
            activeBackground = backgrounds.get(1);
            updateLabelBackground(-190, 140);
        } else if (index == 10) {

            currentArrow = down;
            activeBackground = backgrounds.get(0);
            updateLabelBackground(50, -22);
        } else if (index >= 11 && index < 13) {
            if (index == 11) {

                currentArrow = down;
            } else {

                currentArrow = left;
            }
            activeBackground = backgrounds.get(2);
            updateLabelBackground(-47, 130);
        } else if (index == 13) {

            currentArrow = down;
            activeBackground = backgrounds.get(0);
            updateLabelBackground(50, -22);
        } else if (index >= 14 && index < 18) {
            activeBackground = backgrounds.get(3);
            if (index == 16) {

                currentArrow = right;
            } else {

                currentArrow = up;
            }
            updateLabelBackground(50, 70);
        } else if (index == 18) {

            currentArrow = right;
            activeBackground = backgrounds.get(0);
            updateLabelBackground(50, -22);
        } else if (index == 19) {

            currentArrow = up;
            activeBackground = backgrounds.get(4);
            updateLabelBackground(-51, -190);

        } else if (index == 20) {
            activeBackground = backgrounds.get(5);
        } else if (index == 21) {

            currentArrow = down;
            activeBackground = backgrounds.get(6);
        } else if (index == 22) {

            currentArrow = left;
            activeBackground = backgrounds.get(7);
        } else if (index == 23) {

            currentArrow = left;
            activeBackground = backgrounds.get(8);
        } else if (index == 24) {

            currentArrow = down;
            activeBackground = backgrounds.get(9);
        }

        activeBackground.setVisible(true);
        if (index != 20 && index != -1)
            currentArrow.setVisible(true);
        for (int i = 0; i < tutorials.size(); i++) {
            tutorials.get(i).setVisible(false);
        }
        if (index != -1) {
            tutorials.get(index).setVisible(true); //labellar için bunu açın
            currentArrow.setBounds(arrowCooardinates.get(index).x, arrowCooardinates.get(index).y, currentArrow.getPreferredSize().width, currentArrow.getPreferredSize().height);
            activeLabel = tutorials.get(index);
        }
    }


    private void createComponents() {


        forwardButton = UIFactory.createButton(next, nextH, "square", actionListener);
        backButton = UIFactory.createButton(back, backH, "square", actionListener);
        backButton.setDisabledIcon(new ImageIcon(backD));
        cancelButton = UIFactory.createButton(cancel, cancelH, "square", actionListener);

        labelBackground = LoadMediaToLabel("/image/icons/tutorialBG.png");

        add(forwardButton);
        add(backButton);
        add(cancelButton);

        //manipulate tutorialImages, backgroundImages and points(for tutorials) here sizes should match for cooardinates and labels
        arrowCooardinates.add(new Point(20, 100));
        arrowCooardinates.add(new Point(114, 100));
        arrowCooardinates.add(new Point(143, 295));
        arrowCooardinates.add(new Point(712, 100));
        arrowCooardinates.add(new Point(195, 166));
        arrowCooardinates.add(new Point(712, 100));
        arrowCooardinates.add(new Point(568, 150));
        arrowCooardinates.add(new Point(285, 127));
        arrowCooardinates.add(new Point(166, 295));
        arrowCooardinates.add(new Point(20, 100));
        arrowCooardinates.add(new Point(367, 300));
        arrowCooardinates.add(new Point(300, 83));
        arrowCooardinates.add(new Point(251, 252));
        arrowCooardinates.add(new Point(593, 295));
        arrowCooardinates.add(new Point(67, 231));
        arrowCooardinates.add(new Point(165, 231));
        arrowCooardinates.add(new Point(476, 169));
        arrowCooardinates.add(new Point(66, 407));
        arrowCooardinates.add(new Point(203, 275));
        arrowCooardinates.add(new Point(309, 260));//maybe turn to up
        arrowCooardinates.add(new Point(0, 0));
        arrowCooardinates.add(new Point(23, 240));
        arrowCooardinates.add(new Point(94, 431));
        arrowCooardinates.add(new Point(83, 195));
        arrowCooardinates.add(new Point(694, 52));


        for (int i = 1; i <= 25; i++) { // change size !!!!
            if (LoadMediaToLabel("/image/tutorial_Labels/" + i + ".png") != null) {
                tutorials.add(LoadMediaToLabel("/image/tutorial_Labels/" + i + ".png"));
                tutorials.get(i - 1).setPreferredSize(new Dimension(labelBackground.getPreferredSize().width, labelBackground.getPreferredSize().height));
                add(tutorials.get(i - 1));
                tutorials.get(i - 1).setVisible(false);
            } else {
                tutorials.add(LoadMediaToLabel("/image/tutorial_Labels/" + i + ".gif"));
                add(tutorials.get(i - 1));
                tutorials.get(i - 1).setVisible(false);
            }


        }
        coverInfo.setPreferredSize(new Dimension(labelBackground.getPreferredSize().width, labelBackground.getPreferredSize().height));
        coverInfo.setVisible(false);
        add(coverInfo);
        add(labelBackground);

        add(coverPage);

        for (int i = 1; i <= 10; i++) {

            if (LoadMediaToLabel("/image/tutorial_Backgrounds/" + i + ".gif") != null) {
                backgrounds.add(LoadMediaToLabel("/image/tutorial_Backgrounds/" + i + ".gif"));
            } else {
                backgrounds.add(LoadMediaToLabel("/image/tutorial_Backgrounds/" + i + ".png"));
            }
        }
        down = LoadMediaToLabel("/image/tutorial_Backgrounds/Down.gif");
        left = LoadMediaToLabel("/image/tutorial_Backgrounds/Left.gif");
        right = LoadMediaToLabel("/image/tutorial_Backgrounds/Right.gif");
        up = LoadMediaToLabel("/image/tutorial_Backgrounds/Up.gif");


        add(left);
        add(right);
        add(up);
        add(down);
        for (int i = 1; i <= 10; i++) {
            add(backgrounds.get(i - 1));
            backgrounds.get(i - 1).setVisible(false);
        }
        background = GameEngine.getInstance().themeManager.getGamePanelBackgroundImage();
        Image scaledImage = background.getScaledInstance(guiManager.panelWidth, guiManager.panelHeight, Image.SCALE_DEFAULT);
        background = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = background.createGraphics();
        bGr.drawImage(scaledImage, 0, 0, null);
        bGr.dispose();
    }

    private JLabel LoadMediaToLabel(String fileName) {
        URL url = this.getClass().getResource(fileName);
        System.out.println(url);
        ImageIcon icon = new ImageIcon(url);
        JLabel label = new JLabel(icon);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        return label;
    }

    private void updateLabelBackground(int x, int y) {
        labelBackground.setBounds(guiManager.findCenter(guiManager.panelWidth, labelBackground) + 200 + x, 200 + y, labelBackground.getPreferredSize().width, labelBackground.getPreferredSize().height);
        for (int i = 0; i < tutorials.size(); i++) {
            setBounds(tutorials.get(i), labelBackground.getX(), labelBackground.getY()); //label background coordinates
        }
        forwardButton.setBounds(guiManager.findCenter(guiManager.panelWidth, backButton) + 75 + 200 + x, 275 + y, forwardButton.getPreferredSize().width, forwardButton.getPreferredSize().height);
        backButton.setBounds(guiManager.findCenter(guiManager.panelWidth, backButton) - 75 + 200 + x, 275 + y, backButton.getPreferredSize().width, backButton.getPreferredSize().height);
        cancelButton.setBounds(guiManager.findCenter(guiManager.panelWidth, cancelButton) + 200 + x, 275 + y, backButton.getPreferredSize().width, backButton.getPreferredSize().height);
        setBounds(coverInfo, labelBackground.getX(), labelBackground.getY());
    }

    private void setBoundsOfComponents() {
        forwardButton.setBounds(guiManager.findCenter(guiManager.panelWidth, backButton) + 75 + 200, 275, forwardButton.getPreferredSize().width, forwardButton.getPreferredSize().height);
        backButton.setBounds(guiManager.findCenter(guiManager.panelWidth, backButton) - 75 + 200, 275, backButton.getPreferredSize().width, backButton.getPreferredSize().height);
        cancelButton.setBounds(guiManager.findCenter(guiManager.panelWidth, cancelButton) + 200, 275, backButton.getPreferredSize().width, backButton.getPreferredSize().height);
        labelBackground.setBounds(guiManager.findCenter(guiManager.panelWidth, labelBackground) + 200, 200, labelBackground.getPreferredSize().width, labelBackground.getPreferredSize().height);
        setBounds(coverInfo, labelBackground.getX(), labelBackground.getY());
        for (int i = 0; i < tutorials.size(); i++) {
            setBounds(tutorials.get(i), labelBackground.getX(), labelBackground.getY()); //label background coordinates
        }
        for (int i = 0; i < backgrounds.size(); i++) {
            setBounds(backgrounds.get(i), 0, 0);
        }
        setBounds(coverPage, 0, 0);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
    }

    private void setBounds(JLabel label, int x, int y) {
        label.setBounds(x, y, label.getPreferredSize().width, label.getPreferredSize().height);
    }

    public int getIndex() {
        return index;
    }

    public boolean isTutorialActive() {
        return isTutorialActive;
    }

    void setIndex(int index) {
        this.index = index;
        setLabels();
    }

    public void setTutorialActive(boolean tutorialActive) {
        isTutorialActive = tutorialActive;
    }

    private ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == forwardButton) {
                index++;

            } else if (e.getSource() == backButton) {
                index--;

            } else if (e.getSource() == cancelButton) {
                index = tutorials.size();

            }
            SoundManager.getInstance().buttonClick();
            update();
        }
    };

}
