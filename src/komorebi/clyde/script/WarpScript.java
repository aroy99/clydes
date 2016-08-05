/**
 * WarpScript.java  Jul 6, 2016, 1:51:41 PM
 */
package komorebi.clyde.script;

import komorebi.clyde.engine.Draw;

/**
 * 
 * @author Aaron Roy
 * @version 
 */
public class WarpScript extends AreaScript {

  String map;
  /**
   * @param s
   * @param x
   * @param y
   * @param repeat
   */
  public WarpScript(String map, float x, float y, boolean repeat) {
    super(null, x, y, repeat);
    this.map = map;
  }
  
  public String getMap()
  {
    return map;
  }
  
  /**
   * Renders the "W" tile
   */
  public void render(){
    Draw.rect(x, y, 16, 16, 48, 0, 2);
  }

}
