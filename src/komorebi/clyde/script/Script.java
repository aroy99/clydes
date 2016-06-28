/**
 * Script.java		Jun 13, 2016, 9:38:40 AM
 */
package komorebi.clyde.script;

import komorebi.clyde.entities.Clyde;
import komorebi.clyde.entities.NPC;

/**
 * 
 * @author Aaron Roy
 * @version 
 */
public class Script {

	private String script;
	private boolean hasRun = false;
	private boolean isRepeatable;
	private NPC npc;
	
	private int x, y;
	
	public Script(String s, int x, int y, boolean repeat)
	{
		script = s;
		isRepeatable = repeat;
		this.x=x;
		this.y=y;
		
	}
	
	public Script(String s, int x, int y, boolean repeat, NPC person)
	{
		script = s;
		isRepeatable = repeat;
		this.x=x;
		this.y=y;
		this.npc = person;
		
	}
	
	public void run()
	{
		hasRun = true;
		
		ScriptHandler.read(script, npc);
	}
	
	public boolean isLocationIntersected(Clyde clyde)
	{
		if (clyde.getTileX()==x && clyde.getTileY()==y) return true;
		return false;
	}
	
	public boolean hasRun()
	{
		return hasRun;
	}
	
	public int getTileX() {
		return x;
	}
	
	public int getTileY() {
		return y;
	}
	
	
}
