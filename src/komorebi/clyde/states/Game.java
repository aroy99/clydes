
/**
 * Game.java        May 16, 2016, 9:52:23 PM
 */
package komorebi.clyde.states;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import komorebi.clyde.engine.GameHandler;
import komorebi.clyde.engine.Item;
import komorebi.clyde.engine.Item.Items;
import komorebi.clyde.engine.Key;
import komorebi.clyde.engine.KeyHandler;
import komorebi.clyde.entities.Clyde;
import komorebi.clyde.entities.NPC;
import komorebi.clyde.entities.NPCType;
import komorebi.clyde.map.Map;
import komorebi.clyde.script.AreaScript;
import komorebi.clyde.script.Execution;
import komorebi.clyde.script.Fader;
import komorebi.clyde.script.InstructionList;
import komorebi.clyde.script.Instructions;
import komorebi.clyde.script.Lock;
import komorebi.clyde.script.Task;
import komorebi.clyde.script.Task.TaskWithNumber;
import komorebi.clyde.script.Task.TaskWithString;

/**
 * Represents the game
 * 
 * @author Aaron Roy
 * @version 
 */
public class Game extends State{

  public ArrayList<NPC> npcs;
  public ArrayList<AreaScript> scripts;
  public ArrayList<Item> items = new ArrayList<Item>();
  
  public boolean[] booleans;

  private boolean hasText, hasChoice;
  private int pickIndex;
  private int maxOpt;

  private NPC speaker;

  private BufferedReader read;

  private ArrayList<Lock> waitingLocks;
  private ArrayList<Int> pauseFrames;
  
  private int confidence, money;
  
  public class Int {
    private int val;
    
    public Int(int value)
    {
      val = value;
    }
    
    public void increment()
    {
      val++;
    }
    
    public void decrement()
    {
      val--;
    }
    
    public int intValue()
    {
      return val;
    }
  }

  /**
   * Creates the player and loads the map
   */
  public Game(){
    play = new Clyde(120,100);
    map = new Map("res/maps/Some Town.map");

    npcs = new ArrayList<NPC>();
    scripts = new ArrayList<AreaScript>();

    pauseFrames = new ArrayList<Int>();
    waitingLocks = new ArrayList<Lock>();

    booleans = new boolean[256];

    confidence = 0;
    money = 15;


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
      if (speaker!=null)
      {
        if (speaker.isWaitingOnParagraph())
        {
          System.out.println("Next");
          speaker.nextParagraph();
        } else {
          if (!speaker.doneAsking())
          {
            speaker.skipScroll();
          } else
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
        } 
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

    if (KeyHandler.keyClick(Key.P))
    {
      GameHandler.switchState(States.PAUSE);
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
    map.setClydeLocation(play.getAbsoluteArea(), play.getDirection());

    map.update();
    Fader.update();

    for (Iterator<Int> it = pauseFrames.iterator(); it.hasNext();)
    {      
      Int i = it.next();
      i.decrement();
      if (i.intValue()==0)
      {
        waitingLocks.get(pauseFrames.indexOf(i)).resumeThread();
        waitingLocks.remove(pauseFrames.indexOf(i));
        it.remove();
      }

    }



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


  public void pause(int frames, Lock lock)
  {
    pauseFrames.add(new Int(frames));
    waitingLocks.add(lock);

    lock.pauseThread();
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
    InstructionList instructions = new InstructionList("Main");
    instructions.add(new Task(Instructions.FADE_OUT));
    
    instructions.add(new TaskWithString(Instructions.LOAD_MAP, newMap));
    
    instructions.add(new TaskWithNumber(Instructions.WAIT, 40));
    instructions.add(new Task(Instructions.FADE_IN));


    (new Thread(new Execution(null, instructions))).start();

  }

  public void receiveItem(Items item)
  {
    items.add(new Item(item));
  }

  public ArrayList<Item> getItems()
  {
    return items;
  }
  
  public int getMoney()
  {
    return money;
  }
  
  public int getConfidence()
  {
    return confidence;
  }
  
  public void giveMoney(int add)
  {
    money += add;
  }
  
  public void takeMoney(int subtract)
  {
    money -= subtract;
  }
  
  public void giveConfidence(int add)
  {
    confidence += add;
  }
  
  public void setFlag(int index, boolean b)
  {
    booleans[index] = b;
  }
  
  public boolean checkFlag(int index)
  {
    return booleans[index];
  }
}