/**
 * AreaScript.java  Jun 13, 2016, 9:38:40 AM
 */
package komorebi.clyde.script;

import komorebi.clyde.engine.Draw;
import komorebi.clyde.engine.Renderable;
import komorebi.clyde.entities.Clyde;
import komorebi.clyde.entities.NPC;

/**
 * 
 * @author Andrew Faulkenberry
 */
public class AreaScript extends Script{

  private boolean hasRun = false;
  private boolean isRepeatable;
  private NPC npc;

  private boolean isOptional;

  protected float x, y;

  /**
   * Creates a new area script
   * 
   * @param s The name of the script in res/scripts/
   * @param x The x location (in tiles) of the script
   * @param y The y location (in tiles) of the script
   * @param repeat Whether the script can be repeated or not
   */
  public AreaScript(String s, float x, float y, boolean repeat)
  {

    script = s;
    isRepeatable = repeat;
    this.x=x;
    this.y=y;

  }

  public AreaScript(String s, float x, float y, boolean repeat, NPC person)
  {
    script = s;
    isRepeatable = repeat;
    this.x=x;
    this.y=y;
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
    if (clyde.getTileX() == getTileX() && clyde.getTileY() == getTileY()){
      return true;
    }
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

  public float getX(){
    return x;
  }
  
  public float getY(){
    return y;
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

  /**
   * Renders the "S" tile
   */
  public void render() {
    Draw.rect(x, y, 16, 16, 32, 0, 2);
  }

}
