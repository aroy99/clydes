/**
 * NPCMode.java   Aug 16, 2016, 1:30:31 AM
 */

package komorebi.clyde.editor.modes;

import komorebi.clyde.entities.NPC;
import komorebi.clyde.script.AreaScript;

import java.util.ArrayList;

/**
 * The NPC Editor
 * 
 * @author Aaron Roy
 */
public class NPCMode extends Mode{

  private ArrayList<NPC> npcs;
  private ArrayList<AreaScript> scripts;
  
  /**
   * Creates an NPC Mode
   * 
   * @param npcs The list of NPCs
   * @param scripts The list of Scripts
   */
  public NPCMode(ArrayList<NPC> npcs, ArrayList<AreaScript> scripts) {
    // TODO Auto-generated constructor stub
    this.npcs = npcs;
    this.scripts = scripts;
  }
  
  @Override
  public void update() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void render() {
    // TODO Auto-generated method stub
    
  }
  
}
