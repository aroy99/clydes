/**
 * Grid.java		Jul 31, 2016, 7:41:47 PM
 */
package komorebi.clyde.tileseteditor;

import komorebi.clyde.engine.Draw;
import komorebi.clyde.engine.Renderable;

/**
 * 
 * @author Aaron Roy
 * @version 
 */
public class Grid {

  public static void render()
  {
    for (int x=9; x<18; x++)
    {
      for (int y=0; y<16; y++)
      {
        Draw.rect(x*16, y*16, 16, 16, 221, 109, 237, 125, 6);
      }
    }
  }
  
}
