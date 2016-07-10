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
  }
  
  /**
   * Could this be more terribly named?
   * @param i The abortion index, or the instruction number in the walking loop
   *        at which the execution thread was interrupted 
   */
  
}
