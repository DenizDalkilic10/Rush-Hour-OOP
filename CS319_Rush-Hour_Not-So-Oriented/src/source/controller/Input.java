package source.controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Input class is responsible for handling player inputs.
 */
public class Input
{
   private static Component gamePanel;

//   private static boolean[] mouseButtons = new boolean[5];

   private static String[] mouseButtons2 = new String[5];

   private static java.util.Map<String, Boolean> keys = new HashMap<String, Boolean>()
   {
      {
         put("w", false);
         put("a", false);
         put("s", false);
         put("d", false);
         put("n", false);
      }
   };

   private static int mouseX;
   private static int mouseY;

   /**
    * Cehcks if keys are pressed.
    * @param keyID ID of the keys.
    * @return true during the frame the user presses the the key given as parameter.
    */
   static boolean getKeyPressed(String keyID)
   {
      return keys.get(keyID);
   }

   /**
    * Checks if the button of the mouse is pressed.
    * @param buttonID ID of the mouse button.
    * @return true during the frame the user presses the the mouse button given as parameter.
    */
   @SuppressWarnings("SameParameterValue")
   static boolean getMouseButtonPressed(int buttonID)
   {
      return mouseButtons2[buttonID].equals("Pressed");
   }

   /**
    * Checks if mouse button is reales or not.
    * @param buttonID ID of the mouse button.
    * @return true during the frame the user releases the the mouse button given as parameter.
    */
   @SuppressWarnings("SameParameterValue")
   static boolean getMouseButtonReleased(int buttonID)
   {
      return mouseButtons2[buttonID].equals("Released");
   }

   /**
    * Checks the activity of the mouse
    * @return the mouse listener used by the input class.
    */
   public static MouseListener getMouseListener()
   {
      return new MouseEventHandler();
   }

//   public static KeyListener getKeyListener()
//   {
//      return new KeyEventHandler();
//   }

   /**
    * Setter of the bindings of the keys.
    * @param component instance of JComponent.
    */
   public static void setKeyBindings(JComponent component)
   {
      int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
      component.getInputMap(IFW).put(KeyStroke.getKeyStroke("W"), "w");
      component.getInputMap(IFW).put(KeyStroke.getKeyStroke("A"), "a");
      component.getInputMap(IFW).put(KeyStroke.getKeyStroke("S"), "s");
      component.getInputMap(IFW).put(KeyStroke.getKeyStroke("D"), "d");
      component.getInputMap(IFW).put(KeyStroke.getKeyStroke("N"), "n");

      component.getActionMap().put("w", new KeyAction("w"));
      component.getActionMap().put("a", new KeyAction("a"));
      component.getActionMap().put("s", new KeyAction("s"));
      component.getActionMap().put("d", new KeyAction("d"));
      component.getActionMap().put("n", new KeyAction("n"));
   }

   /**
    * Setter of the game panel.
    * @param component instance of JComponent.
    */
   public static void setGamePanel(Component component)
   {
      gamePanel = component;
   }

   /**
    * Resets the activity of the inputs.
    */
   static void reset()
   {
      for ( int i = 0; i < mouseButtons2.length; i++ )
      {
//         mouseButtons[i] = false;
         mouseButtons2[i] = "default";
      }

      for ( Map.Entry<String, Boolean> entry : keys.entrySet() )
      {
         keys.put(entry.getKey(), false);
      }
   }

   /**
    * Getter of the mouse position.
    * @return the exact position of the mouse.
    */
   static int[] getMousePosition()
   {
      int[] mousePos = new int[2];

      if ( !gamePanel.isShowing() )
      {
         return mousePos;
      }

      mousePos[0] = (int) ( MouseInfo.getPointerInfo().getLocation().getX() - gamePanel.getLocationOnScreen().getX() );
      mousePos[1] = (int) ( MouseInfo.getPointerInfo().getLocation().getY() - gamePanel.getLocationOnScreen().getY() );
      return mousePos;
   }

   /**
    * Gets the mouse position as a matrix.
    * @return the position of the mouse as a matrix.
    */
   static int[] getMouseMatrixPosition()
   {
      int[] mousePos = new int[2];
      int pixelMultiplier = 60;
      mousePos[0] = mouseX / pixelMultiplier;
      mousePos[1] = mouseY / pixelMultiplier;

      return mousePos;
   }

   /**
    * A private class to understand key actions.
    */
   private static class KeyAction extends AbstractAction
   {
      String keyChar;
      KeyAction(String _keyChar)
      {
         keyChar = _keyChar;
      }

      /**
       * Checks the performed actions
       * @param e the instance of ActionEvent
       */
      @Override
      public void actionPerformed(ActionEvent e)
      {
         System.out.println("keyPressed: " + keyChar);
         if ( keys.containsKey(keyChar + "") )
         {
            keys.put(keyChar + "", true);
         }
      }
   }

   /**
    * A private class of event handler of the mouse.
    */
   private static class MouseEventHandler extends MouseAdapter
   {
      /**
       * Checks pressed mouse buttons.
       * @param e an instance of the MouseEvent.
       */
      @Override
      public void mousePressed(MouseEvent e)
      {
         System.out.println("mousePressed");
         if ( e.getButton() - 1 < mouseButtons2.length && e.getButton() - 1 >= 0 )
         {
            mouseButtons2[e.getButton() - 1] = "Pressed";

//            mouseButtons[e.getButton() - 1] = true;
            mouseX = e.getX();
            mouseY = e.getY();
         }
      }

      /**
       * Checks if the mouse is relaesed.
       * @param e an instance of the MouseEvent.
       */
      @Override
      public void mouseReleased(MouseEvent e)
      {
         System.out.println("mouseReleased");
         if ( e.getButton() - 1 < mouseButtons2.length && e.getButton() - 1 >= 0 )
         {
            mouseButtons2[e.getButton() - 1] = "Released";

//            mouseButtons[e.getButton() - 1] = false;
            mouseX = e.getX();
            mouseY = e.getY();
         }
      }
   }

//   private static class KeyEventHandler extends KeyAdapter
//   {
//      @Override
//      public void keyReleased(KeyEvent e)
//      {
//         System.out.println("keyPressed");
//         if ( keys.containsKey(e.getKeyChar() + "") )
//         {
//            keys.put(e.getKeyChar() + "", true);
//         }
//      }
//   }
}
