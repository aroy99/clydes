/**
 * AreaScript.java  Jun 13, 2016, 9:38:40 AM
 */
package komorebi.clyde.script;

import komorebi.clyde.entities.Clyde;
import komorebi.clyde.entities.NPC;

/**
 * 
 * @author Aaron Roy
 * @version 
 */
public class AreaScript extends Script {

  private boolean hasRun = false;
  private boolean isRepeatable;
  private NPC npc;

  private float x, y;

  /**
   * Creates a new area script
   * @param s The name of the script in res/scripts/
   * @param x The x location (in tiles) of the script
   * @param y The y location (in tiles) of the script
   * @param repeat Whether the script can be repeated or not
   */
  public AreaScript(String s, int x, int y, boolean repeat)
  {

    script = s;
    isRepeatable = repeat;
    this.x=x*16;
    this.y=y*16;

  }

  /**
   * Creates a new area script
   * @param s The name of the script in res/scripts/
   * @param x The x location (in tiles) of the script
   * @param y The y location (in tiles) of the script
   * @param repeat Whether the script can be repeated or not
   * @param person The NPC to whom the script will be applied
   */
  public AreaScript(String s, int x, int y, boolean repeat, NPC person)
  {
    script = s;
    isRepeatable = repeat;
    this.x=x*16;
    this.y=y*16;
    this.npc = person;

  }

  /**
   * Executes the script
   */
  public void run()
  {
    hasRun = true;

    ScriptHandler.read(this, npc);
    if (isRepeatable)
    {
      hasRun = false;
    }
  }

  public boolean isLocationIntersected(Clyde clyde)
  {
    return (clyde.getTileX()==getTileX() && clyde.getTileY()==getTileY());
  }

  public boolean hasRun()
  {
    return hasRun;
  }

  public int getTileX() {
    return (int) x/16;
  }

  public int getTileY() {
    return (int) y/16;
  }

  public void setAbsoluteLocation(float x, float y)
  {
    this.x=x;
    this.y=y;
  }
  
  public void move(float dx, float dy)
  {
    x+=dx;
    y+=dy;
  }

  public String getName()
  {
    return script;
  }


  /* (non-Javadoc)
   * @see komorebi.clyde.script.Script#abort()
   */
  @Override
  public void abort() {
    
  }

}
