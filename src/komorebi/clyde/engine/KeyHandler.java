/**
 * Keyboard.java Jul 5, 2016, 5:53:57 PM
 */
package komorebi.clyde.engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
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
  
  public enum Control{
    UP, DOWN, LEFT, RIGHT, TALK, MENU, 
    
    MAP_UP, MAP_DOWN, MAP_LEFT, MAP_RIGHT, SAVE, NEW_SAVE, LOAD, NEW, GRID, 
    REVERT_MAP, RESET_LOC, PLAY, MOVE_SET, NPC;
  }

  public static int totalKeys()
  {
    return Keyboard.KEYBOARD_SIZE;
  }

  /**
   * 
   */
  public static void getInput()
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
    if(k == Key.SHIFT){
      return isKeyDown[Keyboard.KEY_LSHIFT] || isKeyDown[Keyboard.KEY_RSHIFT];
    }
    return (isKeyDown[k.getGLKey()]);
  }
  
  /**
   * @param c The control to survey
   * @return If the requested button was pressed
   */
  public static boolean button(Control c){
    switch(c){
      case UP:    return keyDown(Key.UP);
      case DOWN:  return keyDown(Key.DOWN);
      case LEFT:  return keyDown(Key.LEFT);
      case RIGHT: return keyDown(Key.RIGHT);
      case TALK:  return keyClick(Key.Z);
      case MENU:  return keyClick(Key.ENTER);
        
      case MAP_DOWN:   return keyDown(Key.DOWN)  || keyDown(Key.S) && !keyDown(Key.CTRL);
      case MAP_LEFT:   return keyDown(Key.LEFT)  || keyDown(Key.A);
      case MAP_RIGHT:  return keyDown(Key.RIGHT) || keyDown(Key.D);
      case MAP_UP:     return keyDown(Key.UP)    || keyDown(Key.W);
      case SAVE:       return !keyDown(Key.SHIFT) && keyDown(Key.CTRL) && keyClick(Key.S);
      case NEW_SAVE:   return keyDown(Key.SHIFT) && keyDown(Key.CTRL) && keyClick(Key.S);
      case LOAD:       return keyDown(Key.CTRL)  && keyClick(Key.L);
      case NEW:        return keyDown(Key.CTRL)  && keyClick(Key.N);
      case REVERT_MAP: return keyDown(Key.CTRL)  && keyClick(Key.R);
      case GRID:       return keyDown(Key.CTRL)  && keyClick(Key.G);
      case PLAY:       return keyDown(Key.CTRL)  && keyClick(Key.P);
      case MOVE_SET:   return keyDown(Key.CTRL)  && keyClick(Key.M);
      case NPC:        return keyDown(Key.CTRL)  && keyClick(Key.C);
      case RESET_LOC:  return keyClick(Key.R);

      default:         return false;
      
    }
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
  
  /**
   * For some stupid reason the keys stick when JDialogs are opened, so this
   * method resets the Keyboard by destroying and creating it
   */
  public static void reloadKeyboard(){
    Keyboard.destroy();
    try {
      Keyboard.create();
    } catch (LWJGLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

}
