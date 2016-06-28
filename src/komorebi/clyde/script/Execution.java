/**
 * Execution.java		Jun 11, 2016, 11:58:10 AM
 */
package komorebi.clyde.script;

import komorebi.clyde.engine.Main;
import komorebi.clyde.entities.Face;
import komorebi.clyde.entities.NPC;
import komorebi.clyde.states.Fader;

/**
 * 
 * @author Aaron Roy
 * @version 
 */
public class Execution implements Runnable {

	private Lock lock = new Lock();
	private BranchList branches;
	private NPC npc;
	
	private String currentBranch;
	private boolean isBlank;
	
	public Execution(NPC myNpc, BranchList toDo)
	{
		branches = toDo;
		npc = myNpc;
		
		isBlank = false;
		currentBranch = "Main";
	}
	
	
	public Execution(int zero)
	{
		isBlank=true;
	}
	
	@Override
	public void run() {
	
		
		int j=0;
		
		if (!isBlank)
		{
			//System.out.println(branches.getBranch(currentBranch).getBranchName());
			for (Instructions i: branches.getBranch(currentBranch).getInstructions())
			{
				//int j=0;
				
				
				//j = branches.getBranch(currentBranch).getInstructions().indexOf(i);
				
				switch (i)
				{
				case WALK_DOWN:
					npc.walk(Face.DOWN, 1, this);
					break;
				case WALK_LEFT:
					npc.walk(Face.LEFT, 1, this);
					break;
				case WALK_RIGHT:
					npc.walk(Face.RIGHT, 1, this);
					break;
				case WALK_UP:
					npc.walk(Face.UP, 1, this);
					break;
				case TURN_LEFT:
					npc.turn(Face.LEFT, this);
					break;
				case TURN_RIGHT:
					npc.turn(Face.RIGHT, this);
					break;
				case TURN_UP:
					npc.turn(Face.UP, this);
					break;
				case TURN_DOWN:
					npc.turn(Face.DOWN, this);
					break;
				case WAIT:
					npc.pause(branches.getBranch(currentBranch).getWaitIndex(j), this);
					break;
				case JOG_LEFT:
					npc.jog(Face.LEFT, 1, this);
					break;
				case JOG_RIGHT:
					System.out.println("Jog right");
					npc.jog(Face.RIGHT, 1, this);
					break;
				case JOG_UP:
					npc.jog(Face.UP, 1, this);
					break;
				case JOG_DOWN:
					npc.jog(Face.DOWN, 1, this);
					break;
				case CHANGE_SPRITE:
					npc.setNPCType(branches.getBranch(currentBranch).getSprite(j));
					break;
				case SET_LOCATION:
					npc.setLocation(branches.getBranch(currentBranch).getXLocation(j), branches.getBranch(currentBranch).getYLocation(j), this);
					break;
				case LOCK:
					Main.getGame().getClyde().lock();
					break;
				case UNLOCK:
					Main.getGame().getClyde().unlock();
					break;
				case SAY:
					npc.say(branches.getBranch(currentBranch).getString(j), this);
					break;
				case ASK:
					String[] s = branches.getBranch(currentBranch).getQuestionOptions(j);
					npc.ask(s, this);
					break;
				case BRANCH:
					run();
					break;
				case FADE_OUT:
					Fader.fadeOut(this);
					break;
				case FADE_IN:
					Fader.fadeIn(this);
					break;
				case RUN_SCRIPT:
					//Script script = new Script(branches.getBranch(currentBranch).getString(j), branches.getBranch(currentBranch).getNPC(j));
					//script.run();
					break;
				default:
					break;
				}
				
				j++;

			}
		}
		
	}
	public Lock getLock()
	{
		return lock;
	}
	
	public void setCurrentBranch(String s)
	{
		currentBranch = s;
		
		//System.out.println("The new current branch is " + currentBranch);
		
	}
}
