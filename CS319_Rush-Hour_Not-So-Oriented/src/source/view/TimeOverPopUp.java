package source.view;

import source.controller.GameEngine;
import source.controller.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/*
!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
This class was created for testing purposes of bonus levels.
There are many duplicate codes with EndOfLevelPopUp and has to be revised and redesigned (maybe with inheritance).
!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public class TimeOverPopUp extends JPanel
{
   private GuiPanelManager guiManager;

   private JButton retry;
   private JButton menu;
   private JLabel heading;

   private BufferedImage background;

   private BufferedImage menuButtonImage;
   private BufferedImage menuButtonHighlightedImage;
   private BufferedImage retryButtonImage;
   private BufferedImage retryButtonHighlightedImage;

   private int panelWidth = 400;
   private int panelHeight = 250;

   TimeOverPopUp(GuiPanelManager _guiManager)
   {
      super(null);
      guiManager = _guiManager;
      setPreferredSize(new Dimension(panelWidth, panelHeight));

      loadImages();
      createComponents();
      addComponents();
      setBoundsOfComponents();
      setOpaque(false);
   }

   public void loadImages()
   {
      background = ThemeManager.getInstance().getPopupBackgroundImage();

      menuButtonImage = guiManager.LoadImage("image/icons/menu.png");
      menuButtonHighlightedImage = guiManager.LoadImage("image/icons/menuH.png");

      retryButtonImage = guiManager.LoadImage("image/icons/reset.png");
      retryButtonHighlightedImage = guiManager.LoadImage("image/icons/resetH.png");
   }


   void updatePanel()
   {
      if ( !isShowing() )
      {
         return;
      }
      repaint();
   }

   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);

      drawBackground(g);
   }

   private void drawBackground(Graphics graphics)
   {

      Graphics2D graphics2d = (Graphics2D) graphics;

      graphics2d.drawImage(background, 0, 0, null);

   }

   private void createComponents()
   {
      heading = new JLabel("Time Over!", SwingConstants.CENTER);
      heading.setPreferredSize(new Dimension(300, 60));
      heading.setFont(new Font("Odin Rounded", Font.PLAIN, 45));
      heading.setForeground(Color.white);
      menu = UIFactory.createButton(menuButtonImage, menuButtonHighlightedImage, "square", actionListener);
      retry = UIFactory.createButton(retryButtonImage, retryButtonHighlightedImage, "square", actionListener);

   }

   private void addComponents()
   {
      add(retry);
      add(menu);
      add(heading);
   }

   private void setBoundsOfComponents()
   {
      heading.setBounds(50, 60, heading.getPreferredSize().width,
              heading.getPreferredSize().height);

      menu.setBounds(125, 150, menu.getPreferredSize().width, menu.getPreferredSize().height);
      retry.setBounds(230, 150, retry.getPreferredSize().width, retry.getPreferredSize().height);

   }

   private ActionListener actionListener = new ActionListener()
   {
      @Override
      public void actionPerformed(ActionEvent e)
      {
         GameEngine.getInstance().soundManager.buttonClick();
         if ( e.getSource() == retry )
         {
            GameEngine.getInstance().gameManager.resetLevel();
         }

         if ( e.getSource() == menu )
         {
            guiManager.setPanelVisible("MainMenu");
         }
         guiManager.getGamePanel().hideBlackBackground();
         setVisible(false);
      }
   };
}
