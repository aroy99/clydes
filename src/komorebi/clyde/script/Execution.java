/**
 * Execution.java Jun 11, 2016, 11:58:10 AM
 */
package komorebi.clyde.script;

import komorebi.clyde.audio.AudioHandler;
import komorebi.clyde.audio.Song;
import komorebi.clyde.engine.Main;
import komorebi.clyde.entities.Face;
import komorebi.clyde.entities.NPC;
import komorebi.clyde.map.Map;
import komorebi.clyde.map.TileList;
import komorebi.clyde.states.Game;

/**
 * 
 * @author Andrew Faulkenberry
 * @version 
 */
public class Execution implements Runnable {
  
  private boolean aborted = false;
  private int abortedIndex;
  
  private Lock lock = new Lock();
  private Lock lock2 = new Lock();
  
  private BranchList branches;
  private NPC npc;

  private String currentBranch;
  private boolean isBlank;
  
  private Script sender;

  /**
   * Creates an execution object which will begin on the "Main" branch
   * @param myNpc The NPC the execution should affect
   * @param toDo The list of instructions to be executed on the new thread
   */
  public Execution(NPC myNpc, BranchList toDo)
  {
    branches = toDo;
    npc = myNpc;

    isBlank = false;
    currentBranch = "Main";
    abortedIndex = 0;
  }
  
  /**
   * Creates an execution object which will begin on the "Main" branch
   * @param myNpc The NPC the execution should affect
   * @param toDo The list of instructions to be executed on the new thread
   * @param abortedIndex The index in the branch list the execution should begin
   */
  public Execution(NPC myNpc, BranchList toDo, int abortedIndex)
  {
    branches = toDo;
    npc = myNpc;

    isBlank = false;
    currentBranch = "Main";
    this.abortedIndex = abortedIndex;
  }


  /**
   * Creates an execution object
   * @param myNpc The NPC the execution should affect
   * @param toDo The list of instructions to be executed on the new thread
   * @param firstBranch the branch on which the execution will begin
   */
  public Execution(NPC myNpc, BranchList toDo, String firstBranch)
  {
    branches = toDo;
    npc = myNpc;

    isBlank = false;
    currentBranch = firstBranch;
    abortedIndex = 0;
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
            npc.walk(Face.DOWN, 1, lock);
            break;
          case WALK_LEFT:
            npc.walk(Face.LEFT, 1, lock);
            break;
          case WALK_RIGHT:
            npc.walk(Face.RIGHT, 1, lock);
            break;
          case WALK_UP:
            npc.walk(Face.UP, 1, lock);
            break;
          case TURN_LEFT:
            npc.turn(Face.LEFT);
            break;
          case TURN_RIGHT:
            npc.turn(Face.RIGHT);
            break;
          case TURN_UP:
            npc.turn(Face.UP);
            break;
          case TURN_DOWN:
            npc.turn(Face.DOWN);
            break;
          case WAIT:
            Main.getGame().pause(branches.getBranch(str).getWaitIndex(j), lock);
            break;
          case JOG_LEFT:
            npc.jog(Face.LEFT, 1, lock);
            break;
          case JOG_RIGHT:
            npc.jog(Face.RIGHT, 1, lock);
            break;
          case JOG_UP:
            npc.jog(Face.UP, 1, lock);
            break;
          case JOG_DOWN:
            npc.jog(Face.DOWN, 1, lock);
            break;
          case CHANGE_SPRITE:
            npc.setNPCType(branches.getBranch(str).getSprite(j));
            break;
          case SET_LOCATION:
            npc.setTileLocation(branches.getBranch(str).getXLocation(j), 
                branches.getBranch(str).getYLocation(j));
            break;
          case LOCK:
            Main.getGame().getClyde().lock();
            break;
          case UNLOCK:
            Main.getGame().getClyde().unlock();
            break;
          case SAY:
            npc.say(branches.getBranch(str).getString(j), lock);
            break;
          case ASK:
            String[] s = branches.getBranch(str).getQuestionOptions(j);
            npc.ask(s, this, lock);
            break;
          case RUN_BRANCH_ASK:
            run();
            break;
          case RUN_BRANCH:
            currentBranch = branches.getBranch(str).getString(j);
            run();
            lock2.resumeThread();
            break;
          case FADE_OUT:
            Fader.fadeOut(lock);
            break;
          case FADE_IN:
            Fader.fadeIn(lock);
            break;
          case RUN_SCRIPT:
            AreaScript script = Game.getMap().getScript(branches.getBranch(str).getString(j));
            script.run();
            break;
          case LOAD_MAP:
            Game.setMap(new Map("res/maps/" + branches.getBranch(str).getString(j) + ".mapx", true));
            break;
          case RETILE:
            Game.getMap().setTile(TileList.getTile(branches.getBranch(str).getTileID(j)), 
               branches.getBranch(str).getXLocation(j), branches.getBranch(str).getYLocation(j));
            break;
          case CLYDE_WALK_LEFT:
            Main.getGame().getClyde().walk(Face.LEFT, 1, lock);
            break;
          case CLYDE_WALK_RIGHT:
            Main.getGame().getClyde().walk(Face.RIGHT, 1, lock);
            break;
          case CLYDE_WALK_UP:
            Main.getGame().getClyde().walk(Face.UP, 1, lock);
            break;
          case CLYDE_WALK_DOWN:
            Main.getGame().getClyde().walk(Face.DOWN, 1, lock);
            break;
          case CLYDE_PAUSE:
            Main.getGame().getClyde().pause(branches.getBranch(str).getWaitIndex(j), lock);
            break;
          case SIMUL_RUN_BRANCH:
            Execution ex = new Execution(npc, branches);
            ex.setCurrentBranch(branches.getBranch(str).getString(j));
            
            new Thread(ex).start();
            break;
          case CLYDE_TURN_LEFT:
            Main.getGame().getClyde().turn(Face.LEFT);
            break;
          case CLYDE_TURN_RIGHT:
            Main.getGame().getClyde().turn(Face.RIGHT);
            break;
          case CLYDE_TURN_UP:
            Main.getGame().getClyde().turn(Face.UP);
            break;
          case CLYDE_TURN_DOWN:
            Main.getGame().getClyde().turn(Face.DOWN);
            break;
          case ALIGN_LEFT:
            Main.getGame().getClyde().align(Face.LEFT, lock);
            break;
          case ALIGN_RIGHT:
            Main.getGame().getClyde().align(Face.RIGHT, lock);
            break;
          case ALIGN_DOWN:
            Main.getGame().getClyde().align(Face.DOWN, lock);
            break;
          case ALIGN_UP:
            Main.getGame().getClyde().align(Face.UP, lock);
            break;
          case PLAY_SONG:
            AudioHandler.play(Song.get(branches.getBranch(str).getString(j)));
            break;
          case GO_TO:
            Main.getGame().getClyde().goTo(true, 
                branches.getBranch(str).getXLocation(j), lock);
            Main.getGame().getClyde().goTo(false, 
                branches.getBranch(str).getYLocation(j), lock);
            break;
          case STOP_SONG:
            AudioHandler.stop();
            break;
          case END:
            if (npc != null)
            {
              npc.disengage();
            }
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
    aborted = true;
  }
}
