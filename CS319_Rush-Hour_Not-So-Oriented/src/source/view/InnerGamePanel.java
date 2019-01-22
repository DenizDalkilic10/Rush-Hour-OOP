package source.view;

import source.controller.GameEngine;
import source.model.GameObject;
import source.model.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;


/**
 * The panel that the actual game is running
 */
@SuppressWarnings("serial")
public class InnerGamePanel extends JPanel
{
   private GuiPanelManager guiManager;
   private Map map;
   private BufferedImage blackedOutImage;

   private ArrayList<BufferedImage> poofImages;
   private BufferedImage poof0;
   private BufferedImage poof1;
   private BufferedImage poof2;
   private BufferedImage poof3;
   private BufferedImage poof4;
   private BufferedImage poof5;
   private BufferedImage poof6;
   private BufferedImage poof7;
   private BufferedImage poof8;



   /**
    * Initializes and configures the panel.
    * @param guiManager The GuiPanelManager instance for easy access to its functions.
    * @throws FileNotFoundException file not found exception
    */
   InnerGamePanel(GuiPanelManager guiManager) throws FileNotFoundException
   {
      super(null);
      this.guiManager = guiManager;
      setPreferredSize(new Dimension(480, 480));

      blackedOutImage = GameEngine.getInstance().themeManager.getDisabledImage("obstacle");

      poofImages = new ArrayList<>();

      loadImages();
      addPoofImages();

      setOpaque(false);
      setVisible(true);
   }


   /**
    * Updates the panel to display the latest changes to the components.
    */
   void updatePanel()
   {
      if ( !isShowing() )
      {
         return;
      }
      map = GameEngine.getInstance().mapController.getMap();
      repaint();
   }


   /**
    * Loads the images from memory.
    */
   public void loadImages()
   {
//      poof0 = guiManager.LoadImage("image/poof/poof0.png");
//      poof1 = guiManager.LoadImage("image/poof/poof1.png");
//      poof2 = guiManager.LoadImage("image/poof/poof2.png");
//      poof3 = guiManager.LoadImage("image/poof/poof3.png");
//      poof4 = guiManager.LoadImage("image/poof/poof4.png");
//      poof5 = guiManager.LoadImage("image/poof/poof5.png");
//      poof6 = guiManager.LoadImage("image/poof/poof6.png");
//      poof7 = guiManager.LoadImage("image/poof/poof7.png");
//      poof8 = guiManager.LoadImage("image/poof/poof8.png");
//
      poof0 = guiManager.LoadImage("image/poof2/poof0.png");
      poof1 = guiManager.LoadImage("image/poof2/poof1.png");
      poof2 = guiManager.LoadImage("image/poof2/poof2.png");
      poof3 = guiManager.LoadImage("image/poof2/poof3.png");
      poof4 = guiManager.LoadImage("image/poof2/poof4.png");
      poof5 = guiManager.LoadImage("image/poof2/poof5.png");
      poof6 = guiManager.LoadImage("image/poof2/poof6.png");
      poof7 = guiManager.LoadImage("image/poof2/poof7.png");
      poof8 = guiManager.LoadImage("image/poof2/poof8.png");
   }




   /**
    * Adds images of the poof effect to the inner game panel.
    */
   private void addPoofImages()
   {
      poofImages.add(poof0);
      poofImages.add(poof1);
      poofImages.add(poof2);
      poofImages.add(poof3);
      poofImages.add(poof4);
      poofImages.add(poof5);
      poofImages.add(poof6);
      poofImages.add(poof7);
      poofImages.add(poof8);
   }


   /**
    * The method that paints the panel to the screen.
    * @param g An instance of the Graphics.
    */
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      if ( map == null )
      {
         return;
      }

      Graphics2D g2D = (Graphics2D) g;

      try
      {
         for ( GameObject gameObject : map.getGameObjects() )
         {
            gameObject.draw(g2D);
         }
      }
      catch (ConcurrentModificationException e)
      {
         //do nothing
      }

      if ( GameEngine.getInstance().powerUpManager.isPowerUpActive() )
      {
         Graphics2D temp = (Graphics2D) g.create();
         Composite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
         temp.setComposite(composite);
         String[][] grid = GameEngine.getInstance().mapController.getMap().getGrid();
         for ( int i = 0; i < grid.length; i++ )
         {
            for ( int j = 0; j < grid.length; j++ )
            {
               if ( grid[j][i].equals("Space") )
               {
                  temp.drawImage(blackedOutImage, i * 60, j * 60, null);
               }
            }
         }
      }

      int counter = GameEngine.getInstance().powerUpManager.getCurrentCount();
      if ( counter > 0 )
      {
         Graphics2D temp = (Graphics2D) g.create();
         int obstacleX = GameEngine.getInstance().powerUpManager.getObstacleToRemoveX();
         int obstacleY = GameEngine.getInstance().powerUpManager.getObstacleToRemoveY();
         int[] vehicleCells = GameEngine.getInstance().powerUpManager.getVehicleToShrinkCells();

         if (obstacleX == -1 || obstacleY == - 1)
         {
            int x;
            int y;
            //vehicle
            for (int i : vehicleCells)
            {
               x = i % GameEngine.getInstance().mapController.getMap().getMapSize();
               y = i / GameEngine.getInstance().mapController.getMap().getMapSize();
               temp.drawImage(poofImages.get(counter / ( GameEngine.getInstance().powerUpManager.getPoofDuration() / poofImages.size() )), x * 60, y * 60, null);
            }
         }
         else
         {
            //obstacle
            temp.drawImage(poofImages.get(counter / ( GameEngine.getInstance().powerUpManager.getPoofDuration() / poofImages.size() )), obstacleX * 60, obstacleY * 60, null);
         }

      }
   }
}