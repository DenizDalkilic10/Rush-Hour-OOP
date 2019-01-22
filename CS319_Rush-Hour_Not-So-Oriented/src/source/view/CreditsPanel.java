package source.view;

import source.controller.GameEngine;
import source.controller.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class CreditsPanel extends JPanel {
    private GuiPanelManager guiManager;

    private JLabel heading;
    private JLabel developers;
    private JLabel name1;
    private JLabel name2;
    private JLabel name3;
    private JLabel name4;
    private JLabel name5;

    private JButton back;

    private BufferedImage background;
    private BufferedImage developersImage;
    private BufferedImage title;
    private BufferedImage backButtonImage;
    private BufferedImage backButtonHighlightedImage;

    private int panelWidth;
    private int panelHeight;

    CreditsPanel(GuiPanelManager _guiManager) {
        super(null);

        guiManager = _guiManager;

        panelWidth = guiManager.panelWidth;
        panelHeight = guiManager.panelHeight;

        setPreferredSize(new Dimension(panelWidth, panelHeight));

        loadImages();
        createComponents();
        addComponents();
        setBoundsOfComponents();
        this.setVisible(false);
    }

    public void loadImages() {
        background = ThemeManager.getInstance().getBackgroundImage();
        Image scaledImage = background.getScaledInstance(panelWidth, panelHeight, Image.SCALE_DEFAULT);
        background = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = background.createGraphics();
        bGr.drawImage(scaledImage, 0, 0, null);
        bGr.dispose();

        developersImage = guiManager.LoadImage("image/creditsUs.png");
        title = guiManager.LoadImage("image/icons/creditsTitle.png");
        backButtonImage = guiManager.LoadImage("image/icons/back.png");
        backButtonHighlightedImage = guiManager.LoadImage("image/icons/backH.png");
    }

    private void createComponents() {
        back = UIFactory.createButton(backButtonImage, backButtonHighlightedImage, "square", actionListener);

        heading = new JLabel();
        heading.setIcon(new ImageIcon(title));
        heading.setPreferredSize(new Dimension(233, 65));

        developers = new JLabel();
        developers.setIcon(new ImageIcon(developersImage));
        developers.setPreferredSize(new Dimension(820,300));

        name1 = new JLabel("Ahmet Ayrancioglu", SwingConstants.CENTER);
        name1.setPreferredSize(new Dimension(300, 35));
        name1.setFont(new Font("Odin Rounded", Font.PLAIN, 30));
        name1.setForeground(Color.white);

        name2 = new JLabel("Deniz Dalkilic", SwingConstants.CENTER);
        name2.setPreferredSize(new Dimension(300, 30));
        name2.setFont(new Font("Odin Rounded", Font.PLAIN, 30));
        name2.setForeground(Color.white);

        name3 = new JLabel("Kaan Gonc", SwingConstants.CENTER);
        name3.setPreferredSize(new Dimension(300, 30));
        name3.setFont(new Font("Odin Rounded", Font.PLAIN, 30));
        name3.setForeground(Color.white);

        name4 = new JLabel("Ali Yumsel", SwingConstants.CENTER);
        name4.setPreferredSize(new Dimension(300, 30));
        name4.setFont(new Font("Odin Rounded", Font.PLAIN, 30));
        name4.setForeground(Color.white);

        name5 = new JLabel("Sina Sahan", SwingConstants.CENTER);
        name5.setPreferredSize(new Dimension(300, 30));
        name5.setFont(new Font("Odin Rounded", Font.PLAIN, 30));
        name5.setForeground(Color.white);
    }

    private void addComponents() {
        this.add(heading);
        add(name1);
        add(name2);
        add(name3);
        add(name4);
        add(name5);
        add(back);
        add(developers);
    }

    private void setBoundsOfComponents() {
        back.setBounds(30, 30, back.getPreferredSize().width, back.getPreferredSize().height);

        heading.setBounds(guiManager.findCenter(panelWidth, heading), 25, heading.getPreferredSize().width, heading.getPreferredSize().height);

        developers.setBounds(guiManager.findCenter(panelWidth, developers), panelHeight - developers.getPreferredSize().height, developers.getPreferredSize().width, developers.getPreferredSize().height);

        name1.setBounds(guiManager.findCenter(panelWidth, name1), 150, name1.getPreferredSize().width, name1.getPreferredSize().height);

        name2.setBounds(guiManager.findCenter(panelWidth, name1), 200, name2.getPreferredSize().width, name2.getPreferredSize().height);

        name3.setBounds(guiManager.findCenter(panelWidth, name1), 250, name3.getPreferredSize().width, name3.getPreferredSize().height);

        name4.setBounds(guiManager.findCenter(panelWidth, name1), 300, name4.getPreferredSize().width, name4.getPreferredSize().height);

        name5.setBounds(guiManager.findCenter(panelWidth, name1), 350, name5.getPreferredSize().width, name5.getPreferredSize().height);

    }

    private ActionListener actionListener = e -> {
        GameEngine.getInstance().soundManager.buttonClick();
        if (e.getSource() == back) {
            guiManager.setPanelVisible("MainMenu");
        }
    };

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawBackground(g);
        // setBackground(Color.WHITE);
    }

    private void drawBackground(Graphics graphics) {

        Graphics2D graphics2d = (Graphics2D) graphics;

        graphics2d.drawImage(background, 0, 0, null);

    }
}
