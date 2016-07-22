/**
 * InstructionList.java  Jun 12, 2016, 11:57:16 AM
 */
package komorebi.clyde.script;

import java.util.ArrayList;

import komorebi.clyde.entities.NPC;
import komorebi.clyde.entities.NPCType;

/**
 * 
 * @author Aaron Roy
 * @version 
 */
public class InstructionList {

  private String branchName;

  ArrayList<Instructions> instructions;
  ArrayList<Integer> waitIndices;
  ArrayList<NPCType> sprites;
  ArrayList<Integer> xLocations;
  ArrayList<Integer> yLocations;
  ArrayList<String> words;
  ArrayList<String[]> questions;
  ArrayList<NPC> npcs;

  private int instructionIndex;

  /**
   * Creates an instruction list object
   * @param s The branch name of the instruction list
   */
  public InstructionList(String s)
  {

    branchName = s;
    instructionIndex = 0;

    instructions = new ArrayList<Instructions>();
    waitIndices = new ArrayList<Integer>();
    sprites = new ArrayList<NPCType>();
    xLocations = new ArrayList<Integer>();
    yLocations = new ArrayList<Integer>();
    words = new ArrayList<String>();
    questions = new ArrayList<String[]>();
    npcs = new ArrayList<NPC>();
  }

  /**
   * Adds a task to the end of the NPC's queue
   * @param task The instruction for the NPC
   */
  public void add(Instructions task)
  {
    instructions.add(task);
    waitIndices.add(null);
    sprites.add(null);
    xLocations.add(null);
    yLocations.add(null);
    words.add(null);
    questions.add(null);
    npcs.add(null);
  }

  /**
   * Adds a task to the end of the NPC's queue
   * @param task The instruction for the NPC
   * @param frames An integer specification of the number of frames
   *        (for WAIT)
   */
  public void add(Instructions task, int frames)
  {
    instructions.add(task);
    waitIndices.add(frames);
    sprites.add(null);
    xLocations.add(null);
    yLocations.add(null);
    words.add(null);
    questions.add(null);
    npcs.add(null);
  }

  /**
   * Adds a task to the end of the NPC's queue
   * @param task The instruction for the NPC
   * @param newSprite An NPCType specification (for CHANGE_SPRITE)
   */
  public void add(Instructions task, NPCType newSprite)
  {
    instructions.add(task);
    waitIndices.add(null);
    sprites.add(newSprite);
    xLocations.add(null);
    yLocations.add(null);
    words.add(null);
    questions.add(null);
    npcs.add(null);

  }

  /**
   *
   * Adds a task to the end of the NPC's queue
   * @param task The instruction for the NPC
   * @param x Any x specification for the instruction (in tiles)
   * @param y Any y specification for the instruction (in tiles)
   */
  public void add(Instructions task, int x, int y)
  {
    instructions.add(task);
    waitIndices.add(null);
    sprites.add(null);
    xLocations.add(x);
    yLocations.add(x);
    words.add(null);
    questions.add(null);
    npcs.add(null);

  }

  /**
   * Adds a task to the end of the NPC's queue
   * @param task The instruction for the NPC
   * @param s Any string associated with the instruction
   */
  public void add(Instructions task, String s)
  {
    instructions.add(task);
    waitIndices.add(null);
    sprites.add(null);
    xLocations.add(null);
    yLocations.add(null);
    words.add(s);
    questions.add(null);
    npcs.add(null);
  }

  /**
   * Adds a task to the end of the NPC's queue
   * @param task The instruction for the NPC
   * @param q An array list of questions and options (for ASK)
   */
  public void add(Instructions task, String[] q)
  {
    instructions.add(task);
    waitIndices.add(null);
    sprites.add(null);
    xLocations.add(null);
    yLocations.add(null);
    words.add(null);
    questions.add(q);
    npcs.add(null);
  }

  /**
   * Adds a task to the end of the NPC's queue
   * @param task The instruction for the NPC
   * @param script The script to be executed (for RUN_SCRIPT)
   * @param npc The NPC who will execute the script (for RUN_SCRIPT)
   */
  public void add(Instructions task, String script, NPC npc)
  {
    instructions.add(task);
    waitIndices.add(null);
    sprites.add(null);
    xLocations.add(null);
    yLocations.add(null);
    words.add(script);
    questions.add(null);
    npcs.add(npc);
  }
  
  /**
   * Adds a task to the end of the NPC's queue
   * @param task The instruction for the NPC
   * @param tileID An integer specification of the new tile ID (for RETILE)
   * @param tx The x value of the tile to be re-tiled
   * @param ty The y value of the tile to be re-tiled
   */
  public void add(Instructions task, int tileID, int tx, int ty)
  {
    instructions.add(task);
    waitIndices.add(tileID);
    sprites.add(null);
    xLocations.add(tx);
    yLocations.add(ty);
    words.add(null);
    questions.add(null);
    npcs.add(null);
  }

  public ArrayList<Instructions> getInstructions()
  {
    return instructions;
  }

  public int getWaitIndex(int i)
  {
    return waitIndices.get(i);
  }
  
  public int getTileID(int i)
  {
    return waitIndices.get(i);
  }

  public NPCType getSprite(int i)
  {
    return sprites.get(i);
  }

  public int getXLocation(int i)
  {
    return xLocations.get(i);
  }

  public int getYLocation(int i)
  {
    return yLocations.get(i);
  }

  public String getString(int i)
  {
    return words.get(i);
  }

  public String[] getQuestionOptions(int i)
  {
    return questions.get(i);
  }

  public void setBranchName(String s)
  {
    branchName = s;
  }

  public String getBranchName()
  {
    return branchName;
  }

  public void setInstructionIndex(int num)
  {
    instructionIndex = num;
  }

  public int getInstructionIndex()
  {
    return instructionIndex;
  }

  public NPC getNPC(int i)
  {
    return npcs.get(i);
  }

}
