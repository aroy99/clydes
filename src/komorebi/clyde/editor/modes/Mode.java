/**
 * Mode.java		Aug 4, 2016, 6:21:09 PM
 */
package komorebi.clyde.editor.modes;

import komorebi.clyde.editor.Palette;
import komorebi.clyde.engine.Key;
import komorebi.clyde.engine.KeyHandler;
import komorebi.clyde.engine.MainE;
import komorebi.clyde.engine.Playable;
import komorebi.clyde.engine.Renderable;
import komorebi.clyde.map.EditorMap;
import komorebi.clyde.map.TileList;

import org.lwjgl.input.Mouse;

/**
 * 
 * @author Aaron Roy
 */
public abstract class Mode implements Renderable{
  
  protected static boolean lButtonIsDown, lButtonWasDown;//Left Button Clicked
  protected static boolean rButtonIsDown, rButtonWasDown;//Right Button Clicked
  protected static boolean mButtonIsDown, mButtonWasDown;//Middle Button Pressed
  
  protected static TileList[][] tiles;

  
  protected static boolean mouseSame;                    //Mouse is in same pos as last frame

  protected static int mx, my;            //To track mouse position
  
  protected static boolean rStartDragging, rIsDragging;//Starting a group selection
  protected static boolean lStartDragging, lIsDragging;//Is making a group selection
  protected static boolean isSelection;                //A selection is active

  protected static int initX, initY; //Location at the beginning of a drag

  public static final int SIZE = 16;         //Width and height of a tile
    
  /**
   * Gets input in a static way
   */
  public static void getInput(){
    lButtonWasDown = lButtonIsDown;
    lButtonIsDown = Mouse.isButtonDown(0);

    mouseSame = getMouseX() == mx && getMouseY() == my && lButtonIsDown;
    
    mx = getMouseX();
    my = getMouseY();

    rButtonWasDown = rButtonIsDown;
    rButtonIsDown = Mouse.isButtonDown(1) && !KeyHandler.keyDown(Key.CTRL);

    mButtonWasDown = mButtonIsDown;
    mButtonIsDown = KeyHandler.keyClick(Key.MBUTTON);

    rStartDragging = Mouse.isButtonDown(1) && KeyHandler.keyDown(Key.CTRL) && 
        !rIsDragging;

    rIsDragging = Mouse.isButtonDown(1) && rIsDragging || rStartDragging;

    lStartDragging = Mouse.isButtonDown(0) && KeyHandler.keyDown(Key.CTRL) && 
        !lIsDragging;

    lIsDragging = Mouse.isButtonDown(0) && lIsDragging || lStartDragging;
    



  }
  
  /**
   * Converts Mouse X into a tile index, adjusting for map position
   * @return adjusted mouse x
   */
  protected static int getMouseX(){
    return ((Mouse.getX()/MainE.getScale())-(int)EditorMap.getX())/(16);
  }

  /**
   * Converts Mouse Y into a tile index, adjusting for map position
   * @return adjusted mouse y
   */
  protected static int getMouseY() {
    return ((Mouse.getY()/MainE.getScale())-(int)EditorMap.getY())/(16);
  }

  /**
   * Checks if the Mouse is in bounds of the map
   * @return Mouse is in map
   */
  protected boolean checkMapBounds() {
    return (Mouse.getX()/MainE.getScale() < Palette.xOffset*16 ||
        Mouse.getY()/MainE.getScale() < Palette.yOffset*16) &&
        (getMouseY() >= 0 &&
        getMouseY() < tiles.length &&
        getMouseX() >= 0 &&
        getMouseX() < tiles[0].length);
  }
  
  public static void setMap(TileList[][] map){
    tiles = map;
  }

}
