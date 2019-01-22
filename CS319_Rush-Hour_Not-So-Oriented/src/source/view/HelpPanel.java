package source.view;

import source.controller.GameEngine;
import source.controller.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;


/**
 * The panel that holds the Help Screen for our game.
 */
@SuppressWarnings("Duplicates")
public class HelpPanel extends JPanel
{
   private GuiPanelManager guiManager;

   private final int HELP_LABEL_WIDTH = 244;   // Set this to the width of the help image with the largest width
   private final int HELP_LABEL_HEIGHT = 238;  // Set this to the height of the help image with the largest height

   private JLabel heading;
   private JLabel[] helpLabels;
   //private JLabel help1;
   //private JLabel help2;

   private JButton rightArrowButton;
   private JButton leftArrowButton;
   private JButton back;

   private BufferedImage background;
   private BufferedImage title;
   private BufferedImage backButtonImage;
   private BufferedImage backButtonHighlightedImage;
   //private BufferedImage help1Image;
   //private BufferedImage help2Image;
   private BufferedImage[] helpImages;
   private BufferedImage rightArrow;
   private BufferedImage rightArrowHighlighted;
   private BufferedImage leftArrow;
   private BufferedImage leftArrowHighlighted;

   private int panelWidth;
   private int panelHeight;
   private int pageLength = 2;
   private int page = 0;


   /**
    * Constructor that initializes regarding values and creates desired user interface of help screen.
    * @param _guiManager The GuiPanelManager instance for easy access to its functions.
    */
   HelpPanel(GuiPanelManager _guiManager)
   {
      super(null);

      guiManager = _guiManager;

      panelWidth = guiManager.panelWidth;
      panelHeight = guiManager.panelHeight;

      setPreferredSize(new Dimension(panelWidth, panelHeight));

      loadImages();
      createComponents();
      addComponents();
      setBoundsOfComponents();
      updateVisiblePage();
      this.setVisible(false);
   }


   /**
    * Loads the images from the images directory into the memory.
    */
   public void loadImages()
   {
      background = ThemeManager.getInstance().getBackgroundImage();
      Image scaledImage = background.getScaledInstance(panelWidth, panelHeight, Image.SCALE_DEFAULT);
      background = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
      Graphics2D bGr = background.createGraphics();
      bGr.drawImage(scaledImage, 0, 0, null);
      bGr.dispose();

      title = guiManager.LoadImage("image/icons/howToPlayTitle.png");
      backButtonImage = guiManager.LoadImage("image/icons/back.png");
      backButtonHighlightedImage = guiManager.LoadImage("image/icons/backH.png");
      //help1Image = guiManager.LoadImage("image/help_images/help0.png");
      //help2Image = guiManager.LoadImage("image/help_images/help1.png");

      rightArrow = guiManager.LoadImage("image/icons/rightarrow.png");
      rightArrowHighlighted = guiManager.LoadImage("image/icons/rightarrowH.png");
      leftArrow = guiManager.LoadImage("image/icons/leftarrow.png");
      leftArrowHighlighted = guiManager.LoadImage("image/icons/leftarrowH.png");

      String imagePath = "image/help_images";
      //File folder = new File(imagePath);
      //String[] filenameList = folder.list();
      helpImages = new BufferedImage[2];
      helpLabels = new JLabel[2];
      for ( int i = 0; i < 2; i++ )
      {
        helpImages[i] = guiManager.LoadImage(imagePath + "/help" + (i + 1) +".png");
      }

   }



   /**
    * Creates the components from the loaded images.
    */
   private void createComponents()
   {
      back = UIFactory.createButton(backButtonImage, backButtonHighlightedImage, "square", actionListener);

      heading = new JLabel();
      heading.setIcon(new ImageIcon(title));
      heading.setPreferredSize(new Dimension(355, 78));


      //help1 = new JLabel();
      //help1.setIcon(new ImageIcon(help1Image));
      //help1.setPreferredSize(new Dimension(244, 192));

      //help2 = new JLabel();
      //help2.setIcon(new ImageIcon(help2Image));
      //help2.setPreferredSize(new Dimension(204, 238));

      for ( int i = 0; i < helpImages.length; i++ )
      {
         JLabel tmp = new JLabel(new ImageIcon(helpImages[i]), SwingConstants.CENTER);
         tmp.setPreferredSize(new Dimension(HELP_LABEL_WIDTH, HELP_LABEL_HEIGHT));
         helpLabels[i] = tmp;
      }

      rightArrowButton = UIFactory.createButton(rightArrow, rightArrowHighlighted, "arrow", actionListener);
      leftArrowButton = UIFactory.createButton(leftArrow, leftArrowHighlighted, "arrow", actionListener);
   }


   /**
    *  Adds the components to the panel.
    */
   private void addComponents()
   {
      add(heading);
      add(back);
      //add(help1);
      //add(help2);
      for ( int i = 0; i < helpLabels.length; i++ )
      {
         add(helpLabels[i]);
      }

      add(rightArrowButton);
      add(leftArrowButton);
   }



   /**
    * Sets the sizes and positions of the components in the panel.
    * Parameter page controls the current page number.
    */
   private void setBoundsOfComponents()
   {
      heading.setBounds(guiManager.findCenter(panelWidth, heading), 25, heading.getPreferredSize().width, heading.getPreferredSize().height);

      back.setBounds(30, 30, back.getPreferredSize().width, back.getPreferredSize().height);

      //help1.setBounds(guiManager.findCenter(panelWidth, help1) - 150, 180, help1.getPreferredSize().width, help1.getPreferredSize().height);

      //help2.setBounds(guiManager.findCenter(panelWidth, help1) + 190, 180, help2.getPreferredSize().width, help2.getPreferredSize().height);

      leftArrowButton.setBounds(5, guiManager.findCenterVertical(panelHeight, leftArrowButton),
              leftArrowButton.getPreferredSize().width, leftArrowButton.getPreferredSize().height);
      rightArrowButton.setBounds(panelWidth - 135, guiManager.findCenterVertical(panelHeight, rightArrowButton),
              rightArrowButton.getPreferredSize().width, rightArrowButton.getPreferredSize().height);


      for ( int i = 0; i < helpLabels.length; i++ )
      {
         if ( i % 2 == 0 )
         {
            helpLabels[i].setBounds(guiManager.findCenter(panelWidth, helpLabels[i]) - 150, 180, helpLabels[i].getPreferredSize().width, helpLabels[i].getPreferredSize().height);
         }
         else
         {
            helpLabels[i].setBounds(guiManager.findCenter(panelWidth, helpLabels[i - 1]) + 150, 180, helpLabels[i].getPreferredSize().width, helpLabels[i].getPreferredSize().height);
         }
      }
   }


   /**
    * Method to update the number of visible pages.
    */
   private void updateVisiblePage()
   {
      int limit = page * pageLength;
      for ( int i = 0; i < helpLabels.length; i++ )
      {
         helpLabels[i].setVisible(false);
      }
      for ( int i = limit; i < limit + pageLength && i < helpLabels.length; i++ )
      {
         helpLabels[i].setVisible(true);
      }
   }


   /**
    * Action listener for the help panel.
    */
   private ActionListener actionListener = e ->
   {
      GameEngine.getInstance().soundManager.buttonClick();
      int noOfPages = helpLabels.length / pageLength;
      if ( helpLabels.length % pageLength == 0 )
      {
         noOfPages--;
      }
      if ( e.getSource() == back )
      {
         guiManager.setPanelVisible("MainMenu");
      }
      else if ( e.getSource() == leftArrowButton )
      {
         if ( page == 0 )
         {
            page = noOfPages;
         }
         else
         {
            page -= 1;
         }
         updateVisiblePage();
      }
      else if ( e.getSource() == rightArrowButton )
      {
         if ( page == noOfPages )
         {
            page = 0;
         }
         else
         {
            page += 1;
         }
         updateVisiblePage();
      }
   };


   /**
    * The method that paints the panel to the screen.
    * @param g An instance of the Graphics.
    */
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);

      drawBackground(g);
      // setBackground(Color.WHITE);
   }


   /**
    * The method that draws the background image to the background of the panel.
    * @param graphics An instance of the Graphics.
    */
   private void drawBackground(Graphics graphics)
   {

      Graphics2D graphics2d = (Graphics2D) graphics;

      graphics2d.drawImage(background, 0, 0, null);

   }

}
