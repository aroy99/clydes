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

  private boolean isOptional;

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

  public AreaScript(String s, int x, int y, boolean repeat, NPC person)
  {
    script = s;
    isRepeatable = repeat;
    this.x=x*16;
    this.y=y*16;
    this.npc = person;

  }

  public void run()
  {
    //System.out.println("running a script");
    hasRun = true;

    ScriptHandler.read(this, npc);
  }

  public boolean isLocationIntersected(Clyde clyde)
  {
    if (clyde.getTileX()==getTileX() && clyde.getTileY()==getTileY()) return true;
    return false;
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

  public String getName()
  {
    return script;
  }

  public void setOptional(boolean tf)
  {
    isOptional = tf;
  }

  /* (non-Javadoc)
   * @see komorebi.clyde.script.Script#abort()
   */
  @Override
  public void abort() {
    
  }

}
