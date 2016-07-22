/**
 * WalkingScript.java  
 * Jul 7, 2016, 3:06:51 PM
 */
package komorebi.clyde.script;

import komorebi.clyde.entities.NPC;

/**
 * 
 * @author Aaron Roy
 * @version 
 */
public class WalkingScript extends Script {

  NPC npc;
  
  public WalkingScript(String s, NPC npc)
  {
    this.script = s;
    this.npc = npc;
  }
  
  /**
   * Runs the walking script
   */
  public void run()
  {
    isRunning = true;
    ScriptHandler.read(this, npc, abortionIndex);
  }
 
  /* (non-Javadoc)
   * @see komorebi.clyde.script.Script#abort()
   */
  @Override
  public void abort() {
    isInterrupted = true;
    execution.abort();
    isRunning = false;
  }
}
