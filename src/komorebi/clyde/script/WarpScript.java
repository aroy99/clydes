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
  /** Creates a new warp script object
   * @param s The map file to which the warp will send Clyde
   * @param x The x location of the warp (tiles)
   * @param y The y location of the warp (tiles)
   * @param repeat Whether the warp is repeatable
   */
  public WarpScript(String map, int x, int y, boolean repeat) {
    super(null, x, y, repeat);
    this.map = map;
  }
  
  public String getMap()
  {
    return map;
  }
  
  public void read()
  {
    BranchList branches = new BranchList();
    InstructionList instructions = new InstructionList("Main");
    instructions.add(Instructions.FADE_OUT);
    instructions.add(Instructions.LOAD_MAP, map);
    instructions.add(Instructions.WAIT, 40);
    instructions.add(Instructions.FADE_IN);
    
    branches.add(instructions);
    
    execution = new Execution(null, branches);
  }

}
