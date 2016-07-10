/**
 * WarpScript.java  Jul 6, 2016, 1:51:41 PM
 */
package komorebi.clyde.script;

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
  public WarpScript(String map, int x, int y, boolean repeat) {
    super(null, x, y, repeat);
    this.map = map;
  }
  
  public String getMap()
  {
    return map;
  }

}
