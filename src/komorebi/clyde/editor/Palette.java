/**
 * Palette.java		May 22, 2016, 2:23:43 PM
 *
 * -
 */
package komorebi.clyde.editor;

import komorebi.clyde.engine.Animation;
import komorebi.clyde.engine.MainE;
import komorebi.clyde.engine.Playable;
import komorebi.clyde.map.Tile;
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

  private Tile[][] tiles = new Tile[14][4]; //Holds current palette tiles
                                            /**Offset of palette in tiles*/
  public static int xOffset = Display.getWidth()/(MainE.scale*16) - 4;
  public static int yOffset = Display.getHeight()/(MainE.scale*16) - 14;

  private int selX = xOffset, selY = yOffset + 13;  //Selector X and Y, in tiles
  private Animation selection;              //The Selector itself

  //Removes repeated input
  private boolean lButtonWasDown, lButtonIsDown;//Left Click


  /**
   * Creates a blank palette
   */
  public Palette(){
    for (int i = tiles.length-1, k=0; i >= 0; i--) {
      for (int j = 0; j < tiles[0].length; j++, k++) {
        tiles[i][j] = new Tile(j+xOffset, i + yOffset, TileList.getTile(k));
      }
    }
    
    System.out.println("SelX: "+selX + ", SelY: "+selY);

    selection = new Animation(8, 8, 16, 16, 2);
    for(int i=3; i>=0; i--){
      selection.add(0 , 0 , i);
      selection.add(16, 0 , i);
    }
  }

  /**
   * Renders everything in tiles and selection box
   */
  public void render(){
    for(Tile[] t : tiles){
      for (Tile tile : t) {
        tile.render();
      }
    }

    selection.play(selX*16, selY*16);
  }

  /**
   * Gets clicks and tells what tile was clicked
   */
  public void getInput() {
    lButtonWasDown = lButtonIsDown;
    lButtonIsDown = Mouse.isButtonDown(0);
  }

  public Tile getSelected(){
    return tiles[selY-yOffset][selX-xOffset];
  }

  public void setLoc(TileList tl) {
    for (int i = 0; i < tiles.length; i++) {
      for (int j = 0; j < tiles[0].length; j++) {
        if (tl == tiles[i][j].getType()) {
          selX = j+xOffset;
          selY = i+yOffset;
        }
      }
    }
  }

  /* (non-Javadoc)
   * @see komorebi.clyde.engine.Renderable#update()
   */
  @Override
  public void update() {
    if (Mouse.getX() /( MainE.getScale()*16) >= xOffset &&
            Mouse.getY() /( MainE.getScale()*16) >= yOffset
        && lButtonIsDown && !lButtonWasDown) {
      selX = (Mouse.getX() / (16 * MainE.getScale()));
      selY = (Mouse.getY() / (16 * MainE.getScale()));
    }

  }
  
  /**
   * Reloads the Palette
   */
  public void reload(){
      xOffset = Display.getWidth()/(MainE.scale*16) - 4;    
      yOffset = Display.getHeight()/(MainE.scale*16) - 14;  
      selX = (int)(xOffset*Editor.xSpan);
      selY = (int)(yOffset*Editor.ySpan);
      
      System.out.println("SelX: "+selX + ", SelY: "+selY);
      
      for (int i = tiles.length-1, k=0; i >= 0; i--) {
          for (int j = 0; j < tiles[0].length; j++, k++) {
              tiles[i][j].move(j+xOffset, i + yOffset);
          }
      }

  }

}
