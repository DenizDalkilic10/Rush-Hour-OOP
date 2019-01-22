package source.view;

import source.controller.GameEngine;
import source.controller.PlayerManager;
import source.controller.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * The panel that holds the Settings Screen Layout for our game
 */
public class SettingsPanel extends JPanel {
    private GuiPanelManager guiManager;

    private JLabel heading;
    private JLabel volume;
    private JLabel theme;
    private JLabel control;
    private ArrayList<JLabel> starActiveLabel = new ArrayList<>();

    private ArrayList<JLabel> starAmount = new ArrayList<>();

    private JLabel selectedLabel;

    private BufferedImage starActive;
    private JButton music;
    private JButton sfx;
    //    private JButton mouse;
//    private JButton keyboard;
    private JButton controlPreference;

    private JButton back;
    private JButton minimalistic;
    private JButton classic;
    private JButton safari;
    private JButton space;
    private BufferedImage mouseControlImage;
    private BufferedImage mouseControlHighlightedImage;
    private BufferedImage keyboardControlImage;
    private BufferedImage keyboardControlHighlightedImage;
    private BufferedImage background;
    private BufferedImage title;
    private BufferedImage backButtonImage;
    private BufferedImage backButtonHighlightedImage;
    private BufferedImage musicImage;
    private BufferedImage musicHighlightedImage;
    private BufferedImage musicOffImage;
    private BufferedImage musicOffHighlightedImage;
    private BufferedImage sfxImage;
    private BufferedImage sfxHighlightedImage;
    private BufferedImage sfxOffImage;
    private BufferedImage sfxOffHighlightedImage;
    private BufferedImage simpleImage;
    private BufferedImage simpleHighlightedImage;
    private BufferedImage classicImage;
    private BufferedImage classicHighlightedImage;
    private BufferedImage safariImage;
    private BufferedImage safariHighlightedImage;
    private BufferedImage spaceImage;
    private BufferedImage spaceHighlightedImage;
    private BufferedImage classicD;
    private BufferedImage safariD;
    private BufferedImage selectedImage;
    //    private BufferedImage simpleD;
    private BufferedImage spaceD;
    private String previousPanel = "";
    private int panelWidth;
    private int panelHeight;

    /**
     * Initializes and configures the panel.
     */
    SettingsPanel(GuiPanelManager _guiManager) {
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

    /**
     * Loads the images from the images directory into the memory.
     */
    public void loadImages() {
        background = ThemeManager.getInstance().getBackgroundImage();
        Image scaledImage = background.getScaledInstance(panelWidth, panelHeight, Image.SCALE_DEFAULT);
        background = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = background.createGraphics();
        bGr.drawImage(scaledImage, 0, 0, null);
        bGr.dispose();
        starActive = guiManager.LoadImage("image/icons/miniStar.png");
        title = guiManager.LoadImage("image/icons/settingsTitle.png");
        backButtonImage = guiManager.LoadImage("image/icons/back.png");
        backButtonHighlightedImage = guiManager.LoadImage("image/icons/backH.png");
        musicImage = guiManager.LoadImage("image/icons/musicon.png");
        musicHighlightedImage = guiManager.LoadImage("image/icons/musiconH.png");
        musicOffImage = guiManager.LoadImage("image/icons/musicoff.png");
        musicOffHighlightedImage = guiManager.LoadImage("image/icons/musicoffH.png");
        sfxImage = guiManager.LoadImage("image/icons/soundon.png");
        sfxHighlightedImage = guiManager.LoadImage("image/icons/soundonH.png");
        sfxOffImage = guiManager.LoadImage("image/icons/soundoff.png");
        sfxOffHighlightedImage = guiManager.LoadImage("image/icons/soundoffH.png");
        simpleImage = guiManager.LoadImage("image/icons/simple.png");
        simpleHighlightedImage = guiManager.LoadImage("image/icons/simpleH.png");
        classicImage = guiManager.LoadImage("image/icons/classic.png");
        classicHighlightedImage = guiManager.LoadImage("image/icons/classicH.png");
        safariImage = guiManager.LoadImage("image/icons/safari.png");
        safariHighlightedImage = guiManager.LoadImage("image/icons/safariH.png");
        spaceImage = guiManager.LoadImage("image/icons/space.png");
        spaceHighlightedImage = guiManager.LoadImage("image/icons/spaceH.png");
        mouseControlImage = guiManager.LoadImage("image/icons/control_mouse.png");
        mouseControlHighlightedImage = guiManager.LoadImage("image/icons/control_mouseH.png");
        keyboardControlImage = guiManager.LoadImage("image/icons/control_keyboard.png");
        keyboardControlHighlightedImage = guiManager.LoadImage("image/icons/control_keyboardH.png");
        classicD = guiManager.LoadImage("image/icons/classicD.png");
        safariD = guiManager.LoadImage("image/icons/safariD.png");
        spaceD = guiManager.LoadImage("image/icons/spaceD.png");
        selectedImage = guiManager.LoadImage("image/icons/checkmark.png");
    }

    /**
     * Creates the components from the loaded images.
     */
    @SuppressWarnings("Duplicates")
    private void createComponents() {
        music = UIFactory.createButton(musicImage, musicHighlightedImage, "square", actionListener);
        sfx = UIFactory.createButton(sfxImage, sfxHighlightedImage, "square", actionListener);
        back = UIFactory.createButton(backButtonImage, backButtonHighlightedImage, "square", actionListener);
//        mouse = UIFactory.createButton(mouseControlImage, mouseControlHighlightedImage, "square", actionListener);
//        keyboard = UIFactory.createButton(keyboardControlImage, keyboardControlHighlightedImage, "square", actionListener);
        if (PlayerManager.getInstance().getCurrentPlayer().getSettings().getControlPreference().equals("Slide")) {
            controlPreference = UIFactory.createButton(mouseControlImage, mouseControlHighlightedImage, "square", actionListener);
        } else {
            controlPreference = UIFactory.createButton(keyboardControlImage, keyboardControlHighlightedImage, "square", actionListener);
        }
        minimalistic = UIFactory.createButton(simpleImage, simpleHighlightedImage, "square", actionListener);
        classic = UIFactory.createButton(classicImage, classicHighlightedImage, "square", actionListener);
        safari = UIFactory.createButton(safariImage, safariHighlightedImage, "square", actionListener);
        space = UIFactory.createButton(spaceImage, spaceHighlightedImage, "square", actionListener);
        classic.setDisabledIcon(new ImageIcon(classicD));
        safari.setDisabledIcon(new ImageIcon(safariD));
        space.setDisabledIcon(new ImageIcon(spaceD));
        selectedLabel = UIFactory.createLabelIcon(selectedImage, new Dimension(26, 26));
        for (int i = 0; i < 3; i++) {
            JLabel temp = UIFactory.createLabelIcon(starActive, "miniStar");
            starActiveLabel.add(temp);
            starAmount.add(new JLabel());
            starAmount.get(i).setText(ThemeManager.getInstance().findRequiredStars() + "");
            starAmount.get(i).setPreferredSize(new Dimension(26, 26));
            starAmount.get(i).setFont(new Font("Odin Rounded", Font.PLAIN, 20));
            starAmount.get(i).setForeground(Color.white);
        }

        heading = new JLabel();
        heading.setIcon(new ImageIcon(title));
        heading.setPreferredSize(new Dimension(263, 81));

        volume = new JLabel("Volume", SwingConstants.CENTER);
        volume.setPreferredSize(new Dimension(150, 40));
        volume.setFont(new Font("Odin Rounded", Font.PLAIN, 40));
        volume.setForeground(Color.white);

        control = new JLabel("Control", SwingConstants.CENTER);
        control.setPreferredSize(new Dimension(150, 40));
        control.setFont(new Font("Odin Rounded", Font.PLAIN, 40));
        control.setForeground(Color.white);

        theme = new JLabel("Theme", SwingConstants.CENTER);
        theme.setPreferredSize(new Dimension(150, 40));
        theme.setFont(new Font("Odin Rounded", Font.PLAIN, 40));
        theme.setForeground(Color.white);
    }

    /**
     * Adds the components to the panel.
     */
    private void addComponents() {
        add(back);

        add(music);
        add(sfx);
        //add(mouse);
        //add(keyboard);
        add(heading);
        add(volume);
        add(theme);
        for (int i = 0; i < 3; i++) {
            add(starActiveLabel.get(i));
            add(starAmount.get(i));
        }
        //add(starActiveLabel);
        add(control);
        add(controlPreference);
        add(selectedLabel);
        add(minimalistic);
        add(classic);
        add(safari);
        add(space);
    }

    /**
     * Sets the sizes and positions of the components in the panel. Parameter page controls the current page number.
     */
    private void setBoundsOfComponents() {
        heading.setBounds(guiManager.findCenter(panelWidth, heading), 25, heading.getPreferredSize().width, heading.getPreferredSize().height);

        volume.setBounds(50, 125, volume.getPreferredSize().width, volume.getPreferredSize().height);
        music.setBounds(75, 175, music.getPreferredSize().width, music.getPreferredSize().height);
        sfx.setBounds(175, 175, sfx.getPreferredSize().width, sfx.getPreferredSize().height);

        back.setBounds(30, 30, back.getPreferredSize().width, back.getPreferredSize().height);

        theme.setBounds(50, 300, theme.getPreferredSize().width, theme.getPreferredSize().height);

        minimalistic.setBounds(75, 350, minimalistic.getPreferredSize().width, minimalistic.getPreferredSize().height);

        classic.setBounds(175, 350, classic.getPreferredSize().width, classic.getPreferredSize().height);

        safari.setBounds(275, 350, safari.getPreferredSize().width, safari.getPreferredSize().height);

        space.setBounds(375, 350, space.getPreferredSize().width, space.getPreferredSize().height);

        control.setBounds(550, 125, control.getPreferredSize().width, control.getPreferredSize().height);

        controlPreference.setBounds(605, 175, controlPreference.getPreferredSize().width, controlPreference.getPreferredSize().height);
        selectedLabel.setBounds(75, 370, selectedLabel.getPreferredSize().width, selectedLabel.getPreferredSize().height);
        int gap = 200;
        for (int i = 0; i < 3; i++) {
            starActiveLabel.get(i).setBounds(gap, 400, classic.getPreferredSize().width, classic.getPreferredSize().height);
            starAmount.get(i).setBounds(gap - 25, 400, classic.getPreferredSize().width, classic.getPreferredSize().height);
            gap += 100;
        }

//        keyboard.setBounds(645, 175, mouse.getPreferredSize().width, mouse.getPreferredSize().height);
    }

    /**
     * Updates the panel to display the latest changes to the components.
     */
    void updatePanel(String previousPanel) {
        this.previousPanel = previousPanel;
        updateSoundButtons("SFX");
        updateSoundButtons("Music");
        updateThemeButtons();
        updateThemeLabels();
        updateControlButton();
    }

    /**
     * Updates the theme labels to reflect the current state of the themes in game.
     */
    private void updateThemeLabels() {

        for (int i = 0; i < 3; i++) {
            if (PlayerManager.getInstance().getCurrentPlayer().getSettings().getThemes().get(ThemeManager.getInstance().getThemes()[i + 1])) {
                System.out.println(ThemeManager.getInstance().findRequiredStars());
                starAmount.get(i).setVisible(false);
                starActiveLabel.get(i).setVisible(false);
            } else {
                starAmount.get(i).setText(ThemeManager.getInstance().findRequiredStars() + "");
                starAmount.get(i).setVisible(true);
                starActiveLabel.get(i).setVisible(true);
            }

        }

        if (ThemeManager.getInstance().currentTheme.name.equals("minimalistic")) {
            selectedLabel.setBounds(72, 370, minimalistic.getPreferredSize().width, minimalistic.getPreferredSize().height);
        } else if (ThemeManager.getInstance().currentTheme.name.equals("classic")) {
            selectedLabel.setBounds(172, 370, classic.getPreferredSize().width, classic.getPreferredSize().height);
        } else if (ThemeManager.getInstance().currentTheme.name.equals("safari")) {

            selectedLabel.setBounds(272, 370, safari.getPreferredSize().width, safari.getPreferredSize().height);
        } else if (ThemeManager.getInstance().currentTheme.name.equals("space")) {
            selectedLabel.setBounds(372, 370, space.getPreferredSize().width, space.getPreferredSize().height);
        }
    }

    /**
     * Updates the sound buttons to reflect the current state of the sounds in game.
     * @param type The type of the button (music/sfx)
     */
    @SuppressWarnings("Duplicates")
    private void updateSoundButtons(String type) {
        boolean enabled;
        JButton button;

        if (type.equals("Music")) {
            enabled = GameEngine.getInstance().playerManager.getCurrentPlayer().getSettings().getMusic();
        } else if (type.equals("SFX")) {
            enabled = GameEngine.getInstance().playerManager.getCurrentPlayer().getSettings().getSfx();
        } else {
            enabled = false;
        }

        if (type.equals("Music")) {
            button = music;
            if (enabled) {
                button.setIcon(new ImageIcon(musicImage));
                button.setRolloverIcon(new ImageIcon(musicHighlightedImage));
                button.setPressedIcon(new ImageIcon(musicHighlightedImage));
            } else {
                button.setIcon(new ImageIcon(musicOffImage));
                button.setRolloverIcon(new ImageIcon(musicOffHighlightedImage));
                button.setPressedIcon(new ImageIcon(musicOffHighlightedImage));
            }
        } else if (type.equals("SFX")) {
            button = sfx;
            if (enabled) {
                button.setIcon(new ImageIcon(sfxImage));
                button.setRolloverIcon(new ImageIcon(sfxHighlightedImage));
                button.setPressedIcon(new ImageIcon(sfxHighlightedImage));
            } else {
                button.setIcon(new ImageIcon(sfxOffImage));
                button.setRolloverIcon(new ImageIcon(sfxOffHighlightedImage));
                button.setPressedIcon(new ImageIcon(sfxOffHighlightedImage));
            }
        }

    }

    /**
     * Updates the control preference buttons to reflect the current state of the control schemes in game.
     */
    private void updateControlButton() {
        if (PlayerManager.getInstance().getCurrentPlayer().getSettings().getControlPreference().equals("Slide")) {
            controlPreference.setIcon(new ImageIcon(mouseControlImage));
            controlPreference.setRolloverIcon(new ImageIcon(mouseControlHighlightedImage));
        } else {
            controlPreference.setIcon(new ImageIcon(keyboardControlImage));
            controlPreference.setRolloverIcon(new ImageIcon(keyboardControlHighlightedImage));
        }
    }

    private void toggleControlButton() {
        if (PlayerManager.getInstance().getCurrentPlayer().getSettings().getControlPreference().equals("Slide")) {
            controlPreference.setIcon(new ImageIcon(keyboardControlImage));
            controlPreference.setRolloverIcon(new ImageIcon(keyboardControlHighlightedImage));
        } else {
            controlPreference.setIcon(new ImageIcon(mouseControlImage));
            controlPreference.setRolloverIcon(new ImageIcon(mouseControlHighlightedImage));
        }
    }

    /**
     * Method to get the theme name by button.
     * @param button The button to get the theme name by it
     * @return The name of the theme
     */
    private String getThemeNameByButton(JButton button) {
        if (button == minimalistic) {
            return "minimalistic";
        }
        if (button == classic) {
            return "classic";
        }
        if (button == safari) {
            return "safari";
        }
        if (button == space) {
            return "space";
        }
        return ""; //should return null
    }

    /**
     * Updates the theme buttons to reflect the current state of the themes in game.
     */
    private void updateThemeButtons() {
        ThemeManager themeManager = GameEngine.getInstance().themeManager;
        if (!themeManager.minimalistic.isUnlocked()) {
            minimalistic.setIcon(new ImageIcon(simpleImage));
        } else {
            minimalistic.setIcon(new ImageIcon(simpleImage));
        }

        if (!themeManager.classic.isUnlocked()) {
            classic.setEnabled(false);
            if (themeManager.getThemeStatus("classic") == 1)
                classic.setEnabled(true);
            else
                classic.setEnabled(false);
        } else {

            classic.setEnabled(true);

        }

        if (!themeManager.safari.isUnlocked()) {
            safari.setEnabled(false);
            if (themeManager.getThemeStatus("safari") == 1)
                safari.setEnabled(true);
            else
                safari.setEnabled(false);
        } else {

            safari.setEnabled(true);


        }

        if (!themeManager.space.isUnlocked()) {
            space.setEnabled(false);
            if (themeManager.getThemeStatus("space") == 1)
                space.setEnabled(true);
            else
                space.setEnabled(false);
        } else {

            space.setEnabled(true);


        }
    }

    private ActionListener actionListener = e ->
    {
        GameEngine.getInstance().soundManager.buttonClick();
        if (e.getSource() == back) {
            guiManager.setPanelVisible(previousPanel);
        } else if (e.getSource() == music) {
            GameEngine.getInstance().playerManager.toggleMusic();
            updateSoundButtons("Music");
            GameEngine.getInstance().soundManager.themeSongToggle();
            GameEngine.getInstance().soundManager.updateTheme();
        } else if (e.getSource() == sfx) {
            GameEngine.getInstance().playerManager.toggleSfx();
            updateSoundButtons("SFX");
            GameEngine.getInstance().soundManager.effectsToggle();
        } else if (e.getSource() == controlPreference) {
            //Sırasıyla oynadım hızı değişmedi
            toggleControlButton();
            GameEngine.getInstance().gameManager.toggleControlType();
        } else {
            // This will call game managers change / unlock theme methods
            String themeName = getThemeNameByButton((JButton) e.getSource());
            int themeStatus = GameEngine.getInstance().themeManager.getThemeStatus(themeName);
            if (themeStatus == 1) {
                GameEngine.getInstance().themeManager.unlockTheme(themeName);
            } else if (themeStatus == 2) {
                GameEngine.getInstance().themeManager.changeTheme(themeName);
            }
            ThemeManager.getInstance().update();
            updatePanel(previousPanel);
            loadImages();
            repaint();
        }
    };

    /**
     * The method that paints the panel to the screen.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
    }

    /**
     * The method that draws the background image to the background of the panel.
     */
    private void drawBackground(Graphics graphics) {
        Graphics2D graphics2d = (Graphics2D) graphics;
        graphics2d.drawImage(background, 0, 0, null);
    }
}
