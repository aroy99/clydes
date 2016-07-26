/**
 * Execution.java Jun 11, 2016, 11:58:10 AM
 */
package komorebi.clyde.script;

import komorebi.clyde.engine.Main;
import komorebi.clyde.entities.Face;
import komorebi.clyde.entities.NPC;
import komorebi.clyde.map.Map;
import komorebi.clyde.states.Game;

/**
 * 
 * @author Aaron Roy
 * @version 
 */
public class Execution implements Runnable {

  
  private boolean aborted = false;
  private int abortedIndex;
  
  private Lock lock = new Lock();
  private BranchList branches;
  private NPC npc;

  private String currentBranch;
  private boolean isBlank;
  
  private Script sender;

  /**
   * 
   * @param myNpc
   * @param toDo
   */
  public Execution(NPC myNpc, BranchList toDo)
  {
    branches = toDo;
    npc = myNpc;

    isBlank = false;
    currentBranch = "Main";
    abortedIndex = 0;
  }
  
  public Execution(NPC myNpc, BranchList toDo, int abortedIndex)
  {
    branches = toDo;
    npc = myNpc;

    isBlank = false;
    currentBranch = "Main";
    this.abortedIndex = abortedIndex;
  }


  public Execution(int zero)
  {
    isBlank=true;
  }

  @Override
  public void run() {
    
    if (!isBlank)
    {

      String str = currentBranch;
      
      for (int j=abortedIndex; j < branches.getBranch(str).getInstructions().size(); j++)
      {
        
        if (aborted)
        {
          abortedIndex = j;
          sender.setAbortionIndex(j);
          break;
        }

        switch (branches.getBranch(str).getInstructions().get(j))
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
            Main.getGame().pause(branches.getBranch(str).getWaitIndex(j), this);
            break;
          case JOG_LEFT:
            npc.jog(Face.LEFT, 1, this);
            break;
          case JOG_RIGHT:
            npc.jog(Face.RIGHT, 1, this);
            break;
          case JOG_UP:
            npc.jog(Face.UP, 1, this);
            break;
          case JOG_DOWN:
            npc.jog(Face.DOWN, 1, this);
            break;
          case CHANGE_SPRITE:
            npc.setNPCType(branches.getBranch(str).getSprite(j));
            break;
          case SET_LOCATION:
            npc.setTileLocation(branches.getBranch(str).getXLocation(j), 
                branches.getBranch(str).getYLocation(j), this);
            break;
          case LOCK:
            Map.getClyde().lock();
            break;
          case UNLOCK:
            Map.getClyde().unlock();
            break;
          case SAY:
            npc.say(branches.getBranch(str).getString(j), this);
            break;
          case ASK:
            String[] s = branches.getBranch(str).getQuestionOptions(j);
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
            AreaScript script = Game.getMap().getScript(branches.getBranch(str).getString(j));
            script.run();
            break;
          case LOAD_MAP:
            Game.setMap(new Map("res/maps/" + branches.getBranch(str).getString(j) + ".map"));
            break;
          default:
            break;
        }
      }
      
      
      
      if (sender != null) 
      {
        if (!aborted)
        {
          sender.setAbortionIndex(0);
        }
        sender.setIsRunning(false);
      }
      
    }

  }
  public Lock getLock()
  {
    return lock;
  }
  
  public void setSender(Script sender)
  {
    this.sender = sender;
  }

  public void setCurrentBranch(String s)
  {
    currentBranch = s;
  }
  
  public void abort()
  {
    System.out.println("Execution aborted");
    aborted = true;
  }
}
