/**
 * Camera.java		Aug 21, 2016, 9:01:33 PM
 */
package komorebi.clyde.engine;

import komorebi.clyde.states.Game;

/**
 * Handles what's visible on the screen
 * 
 * @author Aaron Roy
 */
public class Camera{
  private static float x;
  private static float y;
    
  /**
   * Moves the camera by the specified amount
   * 
   * @param dx delta x
   * @param dy delta y
   */
  public static void move(float dx, float dy){
    boolean[] check = Game.getMap().checkBoundaries(x, y, dx, dy);
    
    if(check[0]){
      x += dx;
    }
    if(check[1]){
      y += dy;
    }
  }
  
  /**
   * Moves the camera to the specified location
   * 
   * @param x new x
   * @param y new y
   */
  public static void setLoc(float x, float y){
    Camera.x = x;
    Camera.y = y;
  }

  /**
   * Centers the camera on clyde at the specified location
   * 
   * @param x new x
   * @param y new y
   */
  public static void center(float x, float y){
    Camera.x = x-128+8;
    Camera.y = y-112+12;
  }

  
  
  public static float getX() {
    return x;
  }

  public static float getY() {
    return y;
  }

  
}
