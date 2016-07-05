/**
 * WalkingScript.java Jul 5, 2016, 3:15:41 PM
 */
package komorebi.clyde.script;

import komorebi.clyde.entities.NPC;

/**
 * 
 * @author Aaron Roy
 * @version 
 */
public class WalkingScript {
  private String script; //
  private boolean hasRun;
  private NPC npc;

  private int x,y;

  public WalkingScript(NPC person)
  {
    x = person.getTileX();
    y = person.getTileY();
  }

  public void run()
  {
    hasRun = true;
    ScriptHandler.read(script, npc);
  }

}
