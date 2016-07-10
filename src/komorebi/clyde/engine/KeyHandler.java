/**
 * Keyboard.java Jul 5, 2016, 5:53:57 PM
 */
package komorebi.clyde.engine;

import org.lwjgl.input.Keyboard;

/**
 * 
 * @author Aaron Roy
 * @version 
 */
public class KeyHandler {

  private static boolean[] isKeyDown = new boolean[Keyboard.KEYBOARD_SIZE];
  private static boolean[] wasKeyDown = new boolean[Keyboard.KEYBOARD_SIZE];
  private static boolean[] buffer = new boolean[Keyboard.KEYBOARD_SIZE];

  public static int totalKeys()
  {
    return Keyboard.KEYBOARD_SIZE;
  }

  /**
   * 
   */
  public static void update()
  {

    for (int i=0; i < Keyboard.KEYBOARD_SIZE; i++)
    {
      wasKeyDown[i]=isKeyDown[i];
      isKeyDown[i]=Keyboard.isKeyDown(i);

      if (buffer[i] && !isKeyDown[i])
      {
        buffer[i] = false;
      }
    }
  }

  public static boolean keyClick(Key k)
  {
    if (isKeyDown[k.getGLKey()] && !wasKeyDown[k.getGLKey()] && !buffer[k.getGLKey()])
    {
      buffer[k.getGLKey()] = true;
      return true;
    }
    return false;
  }

  public static boolean keyDown(Key k)
  {
    if (k == Key.CTRL) 
    {
      return (isKeyDown[Keyboard.KEY_LCONTROL]) || (isKeyDown[Keyboard.KEY_RCONTROL]);
    }
    return (isKeyDown[k.getGLKey()]);
  }

  /**
   * Resets the KeyHandler
   */
  public static void reset()
  {
    for (int i=0; i < Keyboard.KEYBOARD_SIZE; i++)
    {
      wasKeyDown[i]=false;
      isKeyDown[i]=false;
    }
  }
}
