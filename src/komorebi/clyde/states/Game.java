
/**
 * Game.java        May 16, 2016, 9:52:23 PM
 */
package komorebi.clyde.states;

import komorebi.clyde.engine.GameHandler;
import komorebi.clyde.engine.Key;
import komorebi.clyde.engine.KeyHandler;
import komorebi.clyde.entities.Clyde;
import komorebi.clyde.entities.NPC;
import komorebi.clyde.entities.NPCType;
import komorebi.clyde.map.Map;
import komorebi.clyde.script.AreaScript;
import komorebi.clyde.script.BranchList;
import komorebi.clyde.script.Execution;
import komorebi.clyde.script.Fader;
import komorebi.clyde.script.InstructionList;
import komorebi.clyde.script.Instructions;

import org.lwjgl.input.Keyboard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;




/**
 * Represents the game
 * 
 * @author Aaron Roy
 * @version 
 */
public class Game extends State{

  public ArrayList<NPC> npcs;
  public ArrayList<AreaScript> scripts;

  private boolean hasText, hasChoice, isPaused;
  private int pickIndex;
  private int maxOpt;

  private NPC speaker;
  
  private Execution pauser;
  private int pauseFrames;
  private BufferedReader read;



  /**
   * Creates the player and loads the map
   */
  public Game(){
    play = new Clyde(120,100);
    map = new Map("res/maps/Some Town.map");

    npcs = new ArrayList<NPC>();
    scripts = new ArrayList<AreaScript>();


    //loadMap("Map1");

    //NPC joe = new NPC("joe",-1,6, NPCType.POKEMON);
    //NPC vin = new NPC("vin",0,0, NPCType.NESS);


    /*npcs.add(joe);
        npcs.add(vin);

        scripts.add(new Script("joe", joe, 5, 5, false));
        scripts.add(new Script("vin", vin, 1, 1, false));
     */



  }

  /* (non-Javadoc)
   * @see komorebi.clyde.states.State#getInput()
   */
  @Override
  public void getInput() {
    // TODO Auto-generated method stub
    play.getInput();

    if (KeyHandler.keyClick(Key.SPACE))
    {
      if (hasText)
      {
        speaker.clearText();

        if (hasChoice) {
          speaker.branch(pickIndex);
        }

        hasChoice=false;
        hasText=false;
      }
    } 
    
    //Debug
    
  

    if (KeyHandler.keyClick(Key.LEFT))
    {
      if (hasChoice && KeyHandler.keyDown(Key.LEFT))
      {
        pickIndex--;
        if (pickIndex < 1) {
          pickIndex = maxOpt;
        }
        speaker.setPickerIndex(pickIndex);
        //choosesLeft=!choosesLeft;

      }  
    }

    if (KeyHandler.keyClick(Key.RIGHT))
    {
      if (hasChoice && KeyHandler.keyDown(Key.RIGHT))
      {
        pickIndex++;
        if (pickIndex > maxOpt) {
          pickIndex = 1;
        }
        speaker.setPickerIndex(pickIndex);
      }
    } 

    if ((KeyHandler.keyDown(Key.LCTRL) || KeyHandler.keyDown(Key.RCTRL)) &&
        KeyHandler.keyClick(Key.M))
    {
      GameHandler.switchState(States.MENU);
    }




  }

  /* (non-Javadoc)
   * @see komorebi.clyde.states.State#update()
   */
  @Override
  public void update() {
    // TODO Auto-generated method stub
    KeyHandler.update();
    
    play.update();
    map.setClydeLocation(play.getX(), play.getY(), play.getDirection());
    
    map.update();
    Fader.update();

    if (isPaused)
    {
      pauseFrames--;
      if (pauseFrames == 0)
      {
        isPaused=false;
        pauser.getLock().resumeThread();
      }
    }
    //System.out.println(play.getTileX() + ", " + play.getTileY());

  }

  /* (non-Javadoc)
   * @see komorebi.clyde.states.State#render()
   */
  @Override
  public void render() {
    map.render();
    play.render();

    Fader.render();

  }

  public static Map getMap(){
    return map;
  }
  
  public static void setMap(Map m)
  {
    map = m;
  }

  public Clyde getClyde()
  {
    return play;
  }


  public void setSpeaker(NPC npc)
  {
    this.speaker = npc;
    this.hasText = true;
  }
  
  /**
   * Pauses the game for a specified number of frames
   * @param frames The number of frames to be paused
   * @param ex The script execution whose thread will be locked while the game
   *        is paused
   */
  public void pause(int frames, Execution ex)
  { 
    isPaused = true;
    pauseFrames=frames;
    pauser = ex;
    pauser.getLock().pauseThread();
  }

  /**
   * Sets the NPC currently presenting a question to the player
   * @param npc The asking NPCS
   */
  public void setAsker(NPC npc)
  {
    this.speaker = npc;
    this.hasText = true;
    this.hasChoice = true;
    //this.choosesLeft = true;

    pickIndex = 1;
  }

  public void setMaxOptions(int i)
  {
    maxOpt = i;
  }

  /**
   * Returns the NPC with a specified name
   * 
   * @param s The name of the NPC to be retrieved
   * @return The NPC with name, s
   */
  public NPC getNpc(String s)
  {
    for (NPC person: NPC.getNPCs())
    {
      if (person.getName().equals(s))
      {
        return person;
      }
    }
    return null;
  }

  /**
   * Loads a given map into the game
   * @param mapFile The name of the file (sans .txt) in the res/ folder
   */
  public void loadMap(String mapFile)
  {
    try {
      read = new BufferedReader(
          new FileReader(new File("res/maps/"+mapFile+".txt")));
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    }
    String s;

    try {
      while ((s = read.readLine()) != null) {
        if (s.startsWith("npc"))
        {
          s = s.replace("npc ", "");
          String[] split = s.split(" ");

          NPC.add(new NPC(split[0], Integer.parseInt(split[1]), 
              Integer.parseInt(split[2]), NPCType.toEnum(split[3])));
        } else if (s.startsWith("script"))
        {
          s = s.replace("script ", "");
          String[] split = s.split(" ");

          scripts.add(new AreaScript(split[0], Integer.parseInt(split[1]), 
              Integer.parseInt(split[2]), false, NPC.get(split[3])));
        }

      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


  }
  
  /**
   * Fades the screen out, loads a new map and fades the screen back in
   * @param newMap The new map to be loaded (sans .map extension)
   */
  public void warp(String newMap)
  {
    BranchList branches = new BranchList();
    InstructionList instructions = new InstructionList("Main");
    instructions.add(Instructions.FADE_OUT);
    instructions.add(Instructions.LOAD_MAP, newMap);
    instructions.add(Instructions.WAIT, 40);
    instructions.add(Instructions.FADE_IN);
    
    branches.add(instructions);
    
    (new Thread(new Execution(null, branches))).start();
    
  }
}