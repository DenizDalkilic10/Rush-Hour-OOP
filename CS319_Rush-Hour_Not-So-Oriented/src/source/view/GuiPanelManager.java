package source.view;

import source.controller.DataConfiguration;
import source.controller.Input;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;




/**
 * GuiPanelManager class extends javax.swing.JFrame class,
 * and it is responsible for managing the different panels of the game.
 */
@SuppressWarnings({"serial", "Duplicates"})

public class GuiPanelManager extends JFrame
{
   private static GuiPanelManager instance = null;

   //   private int currentPanelIndex;
   private ArrayList<JPanel> panels;
   private GamePanel gamePanel;
   private MainMenuPanel mainMenuPanel;
   private CreditsPanel creditsPanel;
   private SettingsPanel settingsPanel;
   private LevelSelectionPanel levelSelectionPanel;
   private HelpPanel helpPanel;
   private TutorialPanel tutorialPanel;
   private ChangePlayerPanel changePlayerPanel;
   private JPanel targetPanel;
   //private BufferedImage cursorImage;
   int panelWidth;
   int panelHeight;

//   private BufferedImage cursorImage;


   /**
    * Initializes and configures the frame and the panels inside.
    */
   private GuiPanelManager()
   {
      super("Rush Hour");
      instance = this;
      setUndecorated(true);

      Toolkit toolkit = Toolkit.getDefaultToolkit();
      //Image image = toolkit.getImage("src/image/icons/cursor.png");
      Image image = null;
      try {
         image = ImageIO.read(GuiPanelManager.class.getClassLoader().getResourceAsStream("image/icons/cursor.png"));
      } catch (IOException e) {
         e.printStackTrace();
      }
      Cursor c = toolkit.createCustomCursor(image, new Point(0, 0), "img");
      this.setCursor(c);

      panels = new ArrayList<>();

      //File fontFile = new File("src/fonts/odin.ttf");
      InputStream input = GuiPanelManager.class.getClassLoader().getResourceAsStream("fonts/odin.ttf");
      try
      {
         Font odinRounded = Font.createFont(Font.TRUETYPE_FONT, input);
         GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
         ge.registerFont(odinRounded);
      } catch (FontFormatException | IOException e)
      {
         e.printStackTrace();
      }

      panelWidth = 800; //764
      panelHeight = 520; //468

      setLayout(new CardLayout());
      setResizable(false);
      setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

      addPanels();
      add(new JLabel()); // do not delete this very IMPORTANT!

      setListeners();
      setFocusable(true);
      setFocusTraversalKeysEnabled(false);
      pack();
      setLocationRelativeTo(null);

      if (DataConfiguration.getInstance().gameOpenedFirstTime) {
         setPanelVisible("Tutorial");
      }
      else {
         setPanelVisible("MainMenu");
      }

      setVisible(true);
      pack();

   }


   /**
    * Getter for instance of GuiPanelManager.
    * @return instance of GuiPanelManager.
    */
   public static GuiPanelManager getInstance()
   {
      if(instance == null) {
         instance = new GuiPanelManager();
      }
      return instance;
   }


   /**
    * Function that creates the panels and adds them to the frame and the panels ArrayList.
    */
   private void addPanels()
   {
      mainMenuPanel = new MainMenuPanel(this);
      tutorialPanel = new TutorialPanel(true,this);
      gamePanel = new GamePanel(this);
      creditsPanel = new CreditsPanel(this);
      settingsPanel = new SettingsPanel(this);
      helpPanel = new HelpPanel(this);
      levelSelectionPanel = new LevelSelectionPanel(this);
      changePlayerPanel = new ChangePlayerPanel(this);
      this.add(mainMenuPanel);

      this.add(gamePanel);
      this.add(creditsPanel);
      this.add(tutorialPanel);
      this.add(levelSelectionPanel);
      this.add(settingsPanel);
      this.add(helpPanel);
      this.add(changePlayerPanel);

      panels.add(mainMenuPanel);
      panels.add(tutorialPanel);
      panels.add(gamePanel);
      panels.add(creditsPanel);
      panels.add(levelSelectionPanel);
      panels.add(settingsPanel);
      panels.add(helpPanel);
      panels.add(changePlayerPanel);
   }


   /**
    * Function to access the game panel.
    * @return the game panel
    */
   public GamePanel getGamePanel()
   {
      return gamePanel;
   }



   /**
    * Sets the panel with the given name visible and sets the other panels invisible.
    * @param panelName the panel with the given name.
    */
   void setPanelVisible(String panelName)
   {
      if ( panelName.equals("MainMenu") )
      {
         mainMenuPanel.updatePanel();
         mainMenuPanel.loadImages();
         targetPanel = mainMenuPanel;
      }
      else if (panelName.equals("Tutorial")){
         tutorialPanel.update();
         tutorialPanel.setIndex(-1);
         targetPanel = tutorialPanel;
      }
      else if ( panelName.equals("Game") )
      {
         gamePanel.loadImages();
         gamePanel.getEndOfLevelPanel().loadImages();
         gamePanel.getTimeOverPopUp().loadImages();
         targetPanel = gamePanel;
      }
      else if ( panelName.equals("Credits") )
      {
         creditsPanel.loadImages();
         targetPanel = creditsPanel;
      }
      else if ( panelName.equals("LevelSelection") )
      {
         levelSelectionPanel.loadImages();
         levelSelectionPanel.updatePanel();
         targetPanel = levelSelectionPanel;
      }
      else if ( panelName.equals("Settings") )
      {
         settingsPanel.loadImages();
         targetPanel = settingsPanel;
         if ( mainMenuPanel.isVisible() )
         {
            mainMenuPanel.loadImages();
            settingsPanel.updatePanel("MainMenu");
         }
         else if ( gamePanel.isVisible() )
         {
            settingsPanel.updatePanel("Game");
         }

      }
      else if ( panelName.equals("Help") )
      {
         helpPanel.loadImages();
         targetPanel = helpPanel;
      }
      else if ( panelName.equals("ChangePlayer") )
      {
         changePlayerPanel.loadImages();
         changePlayerPanel.updatePanel();
         targetPanel = changePlayerPanel;
      }
      else
      {
         System.out.println("Error: Enter valid name");
      }
      for ( JPanel panel : panels )
      {
         panel.setVisible(false);
      }
      targetPanel.setVisible(true);
      setContentPane(targetPanel);
   }
   /**
    * Updates the panels to display the latest changes to the screen.
    */
   void updatePanels()
   {
      gamePanel.updatePanel(); // look into updating other panels
   }


   /**
    *  Updates the images in all panels.
    */
   void updateImages()
   {
   }

   /**
    * Sets the keyboard and mouse listeners.
    */
   private void setListeners()
   {
//      KeyListener keyListener = Input.getKeyListener();
      MouseListener mouseListener = Input.getMouseListener();
//      addKeyListener(keyListener);
      gamePanel.getInnerGamePanel().addMouseListener(mouseListener);
      Input.setGamePanel(gamePanel.getInnerGamePanel());
      Input.setKeyBindings(gamePanel);
   }


   /**
    * Function to load the images.
    * @param fileName the desired file's name
    * @return the image with the given filename as BufferedImage.
    */
   public BufferedImage LoadImage(String fileName)
   {
      //fileName = fileName.substring(fileName.indexOf('/') + 1);
      BufferedImage image = null;
      try
      {
         image = ImageIO.read(GuiPanelManager.class.getClassLoader().getResourceAsStream(fileName));
      } catch (IOException e)
      {
         e.printStackTrace();
      }
      return image;
   }



   /**
    * Finds the center of the panel with the given length for the given component.
    * @param _panelWidth the width of the panel.
    * @param _component instance of Component.
    * @return the center.
    */
   int findCenter(int _panelWidth, Component _component)
   {
      return ( _panelWidth - _component.getPreferredSize().width ) / 2;
   }


   /**
    * Finds the vertical center of the panel with the given height for the given component.
    * @param _panelHeight the width of the panel.
    * @param _component instance of Component.
    * @return the center.
    */
   int findCenterVertical(int _panelHeight, Component _component)
   {
      return ( _panelHeight - _component.getPreferredSize().height ) / 2;
   }

}