package source.view;

import source.controller.GameEngine;
import source.controller.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * The panel that holds the pop-up layout for the create new player functionality our game
 */
public class CreatePlayerPopUp extends JPanel
{
   private GuiPanelManager guiManager;
   private ChangePlayerPanel changePlayerPanel;

   private JTextField playerName;

   private JButton close;
   private JButton confirm;

   private BufferedImage background;
   private BufferedImage closeImage;
   private BufferedImage closeHighlightedImage;
   private BufferedImage confirmImage;
   private BufferedImage confirmHighlightedImage;

   private int panelWidth = 400;
   private int panelHeight = 250;

   enum Mode
   {
      Edit, New
   }

   private Mode currentMode;
   private String oldName;

   /**
    * Initializes and configures the panel.
    * @param _guiManager  The GuiPanelManager instance for easy access to its functions.
    * @param _changePlayerPanel The ChangePlayerPanel instance for easy access to its functions.
    */
   CreatePlayerPopUp(GuiPanelManager _guiManager, ChangePlayerPanel _changePlayerPanel)
   {
      super(null);
      guiManager = _guiManager;
      changePlayerPanel = _changePlayerPanel;
      setPreferredSize(new Dimension(panelWidth, panelHeight));

      loadImages();
      createComponents();
      addComponents();
      setBoundsOfComponents();
      setOpaque(false);
      setVisible(false);
   }

   /**
    * Loads the images from the images directory into the memory.
    */
   public void loadImages()
   {
      background = ThemeManager.getInstance().getPopupBackgroundImage();

      closeImage = guiManager.LoadImage("image/icons/quit.png");
      closeHighlightedImage = guiManager.LoadImage("image/icons/quitH.png");

      confirmImage = guiManager.LoadImage("image/icons/miniPlay.png");
      confirmHighlightedImage = guiManager.LoadImage("image/icons/miniPlayH.png");
   }

   /**
    * Creates the components from the loaded images.
    */
   void createComponents()
   {
      playerName = new JTextField();
      playerName.setPreferredSize(new Dimension(300, 100));
      playerName.setFont(new Font("Odin Rounded", Font.PLAIN, 30));
      playerName.setHorizontalAlignment(JTextField.CENTER);
      playerName.setForeground(Color.gray);
      playerName.setBorder(BorderFactory.createEmptyBorder());
      playerName.setOpaque(false);
      playerName.setText("Enter Player name...");
      playerName.addMouseListener(new MouseAdapter()
      {
         @Override
         public void mousePressed(MouseEvent e)
         {
            if ( currentMode == Mode.New )
            {
               playerName.setText("");
               playerName.setForeground(Color.WHITE);
            }
            else
            {
               playerName.setForeground(Color.WHITE);
            }
         }
      });

      close = UIFactory.createButton(closeImage, closeHighlightedImage, "square", actionListener);
      confirm = UIFactory.createButton(confirmImage, confirmHighlightedImage, "square", actionListener);
   }

   /**
    * Adds the components to the panel.
    */
   private void addComponents()
   {
      add(playerName);
      add(close);
      add(confirm);
   }

   /**
    * Sets the sizes and positions of the components in the panel.
    */
   private void setBoundsOfComponents()
   {
      playerName.setBounds(guiManager.findCenter(panelWidth, playerName), 20, playerName.getPreferredSize().width, playerName.getPreferredSize().height);

      close.setBounds(guiManager.findCenter(panelWidth, close) - 120, 150, close.getPreferredSize().width, close.getPreferredSize().height);
      confirm.setBounds(guiManager.findCenter(panelWidth, close) + 120, 150, close.getPreferredSize().width, close.getPreferredSize().height);
   }

    /**
    * Updates the panel to display the latest changes to the components.
    */
   void updatePanel()
   {
      //reset the panel when being set visible
   }

   /**
    * The method to update the default text field.
    * @param defaultText The default text that will be shown in the text field.
    */
   private void updateDefaultTextField(String defaultText)
   {
      playerName.setText(defaultText);
      playerName.setForeground(Color.gray);
   }

   /**
    * The method to show the pop-up.
    * @param mode Mode of the pop-up. (add/edit)
    */
   void showPopUp(Mode mode)
   {
      setVisible(true);
      currentMode = mode;
      if ( mode == Mode.New )
      {
         updateDefaultTextField("Enter player name...");
      }
   }

   /**
    * The method to show the pop-up.
    * @param mode Mode of the pop-up. (add/edit)
    * @param playerName The name of the player.
    */
   void showPopUp(Mode mode, String playerName)
   {
      setVisible(true);
      currentMode = mode;
      updateDefaultTextField(playerName);
      oldName = playerName;
   }

   /**
    * The method to hide the pop-up
    */
   void hidePopUp()
   {
      setVisible(false);
   }

   /**
    * The method that paints the panel to the screen.
    * @param g the graphics object for the component.
    */
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      drawBackground(g);
   }

   /**
    * The method that draws the background image to the background of the panel.
    * @param graphics The graphics object to draw the background.
    */
   private void drawBackground(Graphics graphics)
   {

      Graphics2D graphics2d = (Graphics2D) graphics;

      graphics2d.drawImage(background, 0, 0, null);

   }

   private ActionListener actionListener = e ->
   {
      GameEngine.getInstance().soundManager.buttonClick();
      if ( e.getSource() == close )
      {
         setVisible(false);
         playerName.setText("Enter Player name...");
         playerName.setForeground(Color.gray);
         requestFocus();
      }

      if ( e.getSource() == confirm )
      {
         if ( currentMode == Mode.New )
         {
            if ( playerName.getText().equals("") )
            {
               return;
            }

            if ( playerName.getText().equals("Enter Player name...") )
            {
               return;
            }
            changePlayerPanel.addPlayer(playerName.getText());
            playerName.setText("Enter Player name...");
            playerName.setForeground(Color.gray);
            setVisible(false);
            guiManager.setPanelVisible("Tutorial");
            requestFocus();
         }
         else
         {
            changePlayerPanel.editPlayer(oldName, playerName.getText());
            playerName.setText("Enter Player name...");
            playerName.setForeground(Color.gray);
            setVisible(false);
            guiManager.setPanelVisible("ChangePlayer");
         }

      }
      changePlayerPanel.hideBlackBackground();
   };


}
