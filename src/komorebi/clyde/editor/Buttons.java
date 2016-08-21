/**
 * Buttons.java    Aug 6, 2016, 10:06:07 PM
 */
package komorebi.clyde.editor;

import komorebi.clyde.engine.Draw;
import komorebi.clyde.engine.Key;
import komorebi.clyde.engine.KeyHandler;
import komorebi.clyde.engine.MainE;
import komorebi.clyde.engine.Playable;
import komorebi.clyde.map.EditorMap;
import komorebi.clyde.map.EditorMap.Modes;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

/**
 * 
 * @author Aaron Roy
 */
public class Buttons implements Playable{
  
  public static final int WIDTH = Display.getWidth();
  public static final int HEIGHT = Display.getHeight();
  public static final int SIZE = 16;         //Width and height of a tile
  
  public EditorMap map;
  
  public Buttons(EditorMap m){
    map = m;
  }
  
  public void setMap(EditorMap m){
    map = m;
  }
  
  @Override
  public void getInput(){
    
  }

  @Override
  public void update() {
    if(KeyHandler.keyClick(Key.LBUTTON) && checkButtonBounds()){
      switch(Mouse.getX()/(16*MainE.scale)-Palette.xOffset){
        case 0:
          switch(Mouse.getY()/(16*MainE.scale)-(Palette.HEIGHT+4)){
            case 0: Editor.newMap();    break;
            case 1: EditorMap.setMode(Modes.TILE); break;
            default: //Do nothing
          }
          break;
        case 1:
          switch(Mouse.getY()/(16*MainE.scale)-(Palette.HEIGHT+4)){
            case 0:           
              if(map.getPath() == null){
                map.newSave();
              }else{
                map.save();
              }
              break;
            case 1: EditorMap.setMode(Modes.MOVE); break;
            default:
          }
          break;
        case 2:
          switch(Mouse.getY()/(16*MainE.scale)-(Palette.HEIGHT+4)){
            case 0: map.newSave(); break;
            case 1: EditorMap.setMode(Modes.NPC);; break;
            default:
          }
          break;
        case 3:
          switch(Mouse.getY()/(16*MainE.scale)-(Palette.HEIGHT+4)){
            case 0: Editor.revertMap();      break;
            case 1: EditorMap.changeGrid();  break;
            default:
          }

          break;
        case 4:
          switch(Mouse.getY()/(16*MainE.scale)-(Palette.HEIGHT+4)){
            case 0: Editor.loadMap(); break;
            case 1: ; break;
            default:
          }
          break;
        case 5:
          switch(Mouse.getY()/(16*MainE.scale)-(Palette.HEIGHT+4)){
            case 0: Editor.testGame(); break;
            case 1: ; break;
            default:
          }
          break;
        case 6:
          switch(Mouse.getY()/(16*MainE.scale)-(Palette.HEIGHT+4)){
            case 0: ; break;
            case 1: ; break;
            default:
          }
          break;
        case 7:
          switch(Mouse.getY()/(16*MainE.scale)-(Palette.HEIGHT+4)){
            case 0: ; break;
            case 1: ; break;
            default:
          }
          break;
        default:
          //Do nothing, invalid (and impossible, I hope)
          System.out.println("This shouldn't be happening m8");
      }
    }

  }

  @Override
  public void render() {
    Draw.rect(WIDTH - SIZE*8, HEIGHT-SIZE*2, SIZE*8, SIZE*2, 0, 32, 2);

    if(checkButtonBounds()){
      int x = WIDTH - SIZE*(8-(Mouse.getX()/(16*MainE.scale)-Palette.xOffset));
      int y = HEIGHT - SIZE*(2-(Mouse.getY()/(16*MainE.scale)-(Palette.HEIGHT+4)));

      Draw.rect(x, y, SIZE, SIZE, 64, 0, 2);
    }

  }
  
  /**
   * Checks if the Mouse is in bounds of the buttons
   * @return Mouse is on a button
   */
  private boolean checkButtonBounds() {
    return (Mouse.getX()/MainE.getScale() >= WIDTH-SIZE*8 &&
        Mouse.getY()/MainE.getScale() > HEIGHT-SIZE*2);
  }

}
