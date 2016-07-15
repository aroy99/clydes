/**
 * Palette.java     May 22, 2016, 2:23:43 PM
 */

package komorebi.clyde.editor;

import komorebi.clyde.engine.Animation;
import komorebi.clyde.engine.Draw;
import komorebi.clyde.engine.Key;
import komorebi.clyde.engine.KeyHandler;
import komorebi.clyde.engine.MainE;
import komorebi.clyde.engine.Playable;
import komorebi.clyde.map.EditorMap;
import komorebi.clyde.map.Map;
import komorebi.clyde.map.TileList;
import komorebi.clyde.states.Editor;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;


/**
 * The current palette to choose from
 * 
 * @author Aaron Roy
 * @version 0.0.1.0
 */
public class Palette implements Playable{

  private boolean scripting;

  private static final int HEIGHT = 32;
  private static final int WIDTH = 4;


  public static final int SIZE = 16;  //Width and height of a tile

  //Holds current palette tiles
  private TileList[][] tiles = new TileList[HEIGHT][WIDTH];

  //Offset of palette in tiles
  public static int xOffset = Display.getWidth() / (MainE.scale * 16) - WIDTH;
  public static int yOffset = Display.getHeight() / (MainE.scale * 16) - HEIGHT;

  //Selector X and Y, in tiles
  private int selX = xOffset;
  private int selY = yOffset + HEIGHT - 1;

  //The Selector itself
  private Animation selection;
  private EditorMap map;

  //Removes repeated input
  private boolean lButtonWasDown, lButtonIsDown; //Left Click
  private boolean rButtonWasDown, rButtonIsDown; //Right Click

  //Special commands
  private boolean startDragging;                //Starting a group selection
  private boolean isDragging;                   //Is making a group selection

  private int initX, initY; //Location at the beginning of a drag




  /**
   * Creates a blank palette
   */
  public Palette(){

    for (int i = tiles.length-1, k = 0; i >= 0; i--){
      for (int j = 0; j < tiles[0].length; j++, k++){
        tiles[i][j] = TileList.getTile(k);
      }
    }

    selection = new Animation(8, 8, 16, 16, 2);
    for(int i=3; i >= 0; i--){
      selection.add(0 , 0 , i);
      selection.add(16, 0 , i);
    }
  }

  /* (non-Javadoc)
   * @see komorebi.clyde.engine.Playable#getInput()
   */
  @Override
  public void getInput(){
            
    lButtonWasDown = lButtonIsDown;
    lButtonIsDown = Mouse.isButtonDown(0);

    rButtonWasDown = rButtonIsDown;
    rButtonIsDown = Mouse.isButtonDown(1);
    
    startDragging = Mouse.isButtonDown(1) && controlPressed() && 
        !isDragging;
    
    isDragging = Mouse.isButtonDown(1) && controlPressed();    
  }


  /* (non-Javadoc)
   * @see komorebi.clyde.engine.Renderable#update()
   */
  @Override
  public void update(){
    if (checkBounds() && lButtonIsDown && !lButtonWasDown) {
      selX = getMouseX()+xOffset;
      selY = getMouseY()+yOffset;
      map.clearSelection();
    }

    if(checkBounds() && rButtonIsDown && !rButtonWasDown && 
        !KeyHandler.keyDown(Key.CTRL)){
      map.clearSelection();
    }
    
    if(startDragging){
      initX = getMouseX();
      initY = getMouseY();
    }
    if(isDragging && checkBounds()){
      createSelection();
    }


  }

  /* (non-Javadoc)
   * @see komorebi.clyde.engine.Renderable#render()
   */
  @Override
  public void render(){
    for (int i = 0; i < tiles.length; i++) {
      for (int j = 0; j < tiles[0].length; j++) {
        Draw.rect((j+xOffset)*SIZE, (i+yOffset)*SIZE, SIZE, SIZE, 
            tiles[i][j].getX(), tiles[i][j].getY(), 1);
        if(EditorMap.grid){
          Draw.rect((j+xOffset)*SIZE, (i+yOffset)*SIZE, SIZE, SIZE, 0, 16, SIZE,
              16+SIZE, 2);
        }
      }
    }
    
    selection.play(selX*16, selY*16);
  }

  public TileList getSelected(){
    return tiles[selY-yOffset][selX-xOffset];
  }

  /**
   * Sets the location of the selector
   * 
   * @param tl The tile to look for
   */
  public void setLoc(TileList tl){
    for (int i = 0; i < tiles.length; i++) {
      for (int j = 0; j < tiles[0].length; j++) {
        if (tl == tiles[i][j]) {
          selX = j+xOffset;
          selY = i+yOffset;
          return;
        }
      }
    }
  }


  /**
   * Reloads the Palette
   */
   @Deprecated
  public void reload(){
    xOffset = Display.getWidth()/(MainE.scale*16) - 4;    
    yOffset = Display.getHeight()/(MainE.scale*16) - 14;  
    selX = (int)(xOffset*Editor.xSpan);
    selY = (int)(yOffset*Editor.ySpan);

    System.out.println("SelX: "+selX + ", SelY: "+selY);
  }




  /**
   * @return if the control key was pressed
   */
  private boolean controlPressed(){
    return (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) ||
        Keyboard.isKeyDown(Keyboard.KEY_RCONTROL));
  }

  /**
   * Creates a new selection
   */
  private void createSelection() {
    TileList[][] sel = new TileList[Math.abs(getMouseY()-initY)+1]
        [Math.abs(getMouseX()-initX)+1];
    int firstX, lastX;
    int firstY, lastY;

    firstX = Math.min(initX, getMouseX());
    firstY = Math.min(initY, getMouseY());

    lastX = Math.max(initX, getMouseX());
    lastY = Math.max(initY, getMouseY());


    for(int i = 0; i <= lastY - firstY; i++){
      for(int j = 0; j <= lastX - firstX; j++){
        sel[i][j] = tiles[firstY+i][firstX+j];
      }

    }

    map.setSelection(sel);

    map.setIsSelection(true);
  }


  /**
   * Checks if the Mouse is in bounds of the map
   * @return Mouse is in map
   */
  private boolean checkBounds() {
    return (Mouse.getX()/MainE.getScale() >= Palette.xOffset*16 &&
        Mouse.getY()/MainE.getScale() >= Palette.yOffset*16);
  }

  /**
   * Converts Mouse X into a tile index, adjusting for map position
   * @return adjusted mouse x
   */
  private int getMouseX(){
    return (Mouse.getX()/MainE.getScale())/(16)-xOffset;
  }


       


  /**
   * Converts Mouse Y into a tile index, adjusting for map position
   * @return adjusted mouse y
   */
  private int getMouseY() {
    return (Mouse.getY()/MainE.getScale())/(16)-yOffset;
  }


  /**
   * Sets the palette's map to a new map
   * 
   * @param map The map to set the palette to
   */
  public void setMap(EditorMap map) {
    this.map = map;
  }



}