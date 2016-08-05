/**
 * TileMode.java		Aug 4, 2016, 6:16:33 PM
 */
package komorebi.clyde.editor.modes;

import komorebi.clyde.editor.Editor;
import komorebi.clyde.editor.Palette;
import komorebi.clyde.engine.Draw;
import komorebi.clyde.engine.MainE;
import komorebi.clyde.map.EditorMap.Modes;
import komorebi.clyde.map.EditorMap;
import komorebi.clyde.map.TileList;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

/**
 * The tile editing part of the editor
 * 
 * @author Aaron Roy
 */
public class TileMode extends Mode{

  private static TileList[][] selection;            //The selection
  private static Palette pal = Editor.getPalette();  

  public void update(){
    //Sets mouse tile to the one from the palette
    if(lButtonIsDown && checkMapBounds() && !isSelection){

      tiles[my][mx] = pal.getSelected();

      EditorMap.setUnsaved();
      if(Display.getTitle().charAt(Display.getTitle().length()-1) != '*'){
        Display.setTitle(Display.getTitle()+"*");
      }
    }

    //Sets palette's selected to mouse tile
    if(rButtonIsDown && checkMapBounds() && !rButtonWasDown){
      pal.setLoc(tiles[getMouseY()][getMouseX()]);
      clearSelection();

    }

    //Flood Fills tiles
    if(mButtonIsDown && !mButtonWasDown && checkMapBounds()){
      int mx = getMouseX();
      int my = getMouseY();

      flood(mx, my, tiles[my][mx]);
      EditorMap.setUnsaved();
    }

    //Creates a selection
    if(rStartDragging || lStartDragging){
      initX = getMouseX();
      initY = getMouseY();
    }

    if(rIsDragging && checkMapBounds()){
      createSelection();
    }

    if(isSelection && lButtonIsDown && checkMapBounds() && !lButtonWasDown){
      for(int i = 0; i < selection.length; i++){
        for (int j = 0; j < selection[0].length; j++) {
          if(checkTileBounds(getMouseY()+i, getMouseX()+j)){
            tiles[getMouseY()+i][getMouseX()+j] = 
                selection[i][j];
          }
        }
      }
      EditorMap.setUnsaved();
    }

  }


  /**
   * Creates a new selection
   */
  private void createSelection() {
    selection = new TileList[Math.abs(getMouseY()-initY)+1]
        [Math.abs(getMouseX()-initX)+1];
    int firstX, lastX;
    int firstY, lastY;

    firstX = Math.min(initX, getMouseX());
    firstY = Math.min(initY, getMouseY());

    lastX = Math.max(initX, getMouseX());
    lastY = Math.max(initY, getMouseY());


    for(int i = 0; i <= lastY - firstY; i++){
      for(int j = 0; j <= lastX - firstX; j++){
        selection[i][j] =  tiles[firstY+i][firstX+j];
      }
    }

    isSelection = true;
  }

  public void render(){

    if(selection != null){
      for (int i = 0; i < selection.length; i++) {
        for (int j = 0; j < selection[0].length; j++) {
          Draw.rect(EditorMap.getX()+tiles[0].length*SIZE+j*SIZE, 
              EditorMap.getY()+i*SIZE, SIZE, SIZE, 
              selection[i][j].getX(), selection[i][j].getY(), 1);
        }
      }

      if(checkMapBounds()){
        Draw.rect(EditorMap.getX()+mx*SIZE, EditorMap.getY()+my*SIZE, 
            selection[0].length*SIZE, selection.length*SIZE, 
            16, 16,16,16, 2);
      }
    }

  }

  public TileList[][] getSelection(){
    return selection;
  }

  public static void setSelection(TileList[][] sel){
    selection = sel;
  }




  /**
   * Recursive method that flood fills tiles
   * 
   * @param mouseX starting tile x
   * @param mouseY starting tile y
   * @param type tile to search and destroy
   */
  private void flood(int mouseX, int mouseY, TileList type) {
    if (mouseX < 0 || mouseX >= tiles[0].length ||
        mouseY < 0 || mouseY >= tiles.length){
      return;
    }
    if(tiles[mouseY][mouseX] != type || 
        tiles[mouseY][mouseX] == pal.getSelected()){
      return;
    }

    tiles[mouseY][mouseX] = pal.getSelected();
    flood(mouseX-1, mouseY,   type);
    flood(mouseX+1, mouseY,   type);
    flood(mouseX,   mouseY+1, type);
    flood(mouseX,   mouseY-1, type);
  }

  /**
   * Sets isSelection to s
   * 
   * @param selec value to set the selection to
   */
  public static void setIsSelection(boolean selec) {
    isSelection = selec;
  }

  /**
   * Clears the selection, making it disappear
   */
  public static void clearSelection(){
    selection = null;
    isSelection = false;
  }

  /**
   * Checks if the tile is valid
   * 
   * @param tx the X index of the tile
   * @param ty the Y index of the tile
   * @return true if the tile is in bounds
   */
  private boolean checkTileBounds(int tx, int ty) {
    return ty >= 0 &&           
        ty < tiles.length && 
        tx >= 0 &&           
        tx < tiles[0].length;
  }



}
