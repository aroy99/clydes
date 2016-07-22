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

  public enum Control{
    UP, DOWN, LEFT, RIGHT, TALK, MENU, 
    
    MAP_UP, MAP_DOWN, MAP_LEFT, MAP_RIGHT, SAVE, NEW_SAVE, ENCRYPTED_SAVE, LOAD, NEW, GRID, 
    RESET_TILE, RESET_LOC, PLAY;
  }
  
  
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

  /**
   * Returns a boolean value which is true only at the first instant a key is pressed
   * @param k The Key value to be tested
   * @return Whether the inputted key value was just clicked
   */
  public static boolean keyClick(Key k)
  {
    if (isKeyDown[k.getGLKey()] && !wasKeyDown[k.getGLKey()] && !buffer[k.getGLKey()])
    {
      buffer[k.getGLKey()] = true;
      return true;
    }
    return false;
  }

  /**
   * Returns a boolean value which is true if the specified key is down
   * @param k The key value to be tested
   * @return Whether the inputted key value is currently down
   */
  public static boolean keyDown(Key k)
  {
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
  
  public static boolean button(Control c){
    switch(c){
      case UP:    return keyDown(Key.UP);
      case DOWN:  return keyDown(Key.DOWN);
      case LEFT:  return keyDown(Key.LEFT);
      case RIGHT: return keyDown(Key.RIGHT);
      case TALK:  return keyClick(Key.Z);
      case MENU:  return keyClick(Key.ENTER);
        
      case MAP_DOWN:   return keyDown(Key.DOWN)  || keyDown(Key.S) && !controlDown();
      case MAP_LEFT:   return keyDown(Key.LEFT)  || keyDown(Key.A);
      case MAP_RIGHT:  return keyDown(Key.RIGHT) || keyDown(Key.D);
      case MAP_UP:     return keyDown(Key.UP)    || keyDown(Key.W);
      case SAVE:       return !shiftDown() && controlDown() && keyClick(Key.S);
      case NEW_SAVE:   return shiftDown() && controlDown() && keyClick(Key.S);
      case LOAD:       return controlDown()  && keyClick(Key.L);
      case NEW:        return controlDown()  && keyClick(Key.N);
      case RESET_TILE: return controlDown()  && keyClick(Key.R);
      case GRID:       return controlDown()  && keyClick(Key.G);
      case PLAY:       return controlDown()  && keyClick(Key.P);
      case RESET_LOC:  return keyClick(Key.R);   
      case ENCRYPTED_SAVE: return controlDown() && keyClick(Key.E);

      default:         return false;
      
    }
  }

  /**
   * Designates whether either of the two control keys is down
   * @return Left-control or right-control is down
   */
  public static boolean controlDown()
  {
    return (isKeyDown[Keyboard.KEY_LCONTROL]) || (isKeyDown[Keyboard.KEY_RCONTROL]);
  }

  /**
   * Designates whether either of the two shift keys is down
   * @return Left-shift or right-shift is down
   */
  public static boolean shiftDown()
  {
    return (isKeyDown[Keyboard.KEY_LSHIFT]) || (isKeyDown[Keyboard.KEY_RSHIFT]);
  }
}
