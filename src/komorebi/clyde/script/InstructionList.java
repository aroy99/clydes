/**
 * InstructionList.java		Jun 12, 2016, 11:57:16 AM
 */
package komorebi.clyde.script;

import java.util.ArrayList;

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
	
	private int instructionIndex;
	
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
	}
	
	/**
	 * Only used for the WAIT instruction: specifies both instruction type and wait delay
	 * @param task The instruction for the NPC - only applicable for WAIT
	 * @param frames The number of frames to delay
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
	}
	
	/**
	 * Only used for the CHANGE_SPRITE instruction: specifies both instruction type and new sprite
	 * @param task The instruction for the NPC - only applicable for CHANGE_SPRITE
	 * @param newSprite The new sprite of the NPC
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
		
	}
	
	/**
	 * Only used for the SET_LOCATION instruction: specifies both instruction type and (x,y) location
	 * @param task The instruction for the NPC - only applicable for SET_LOCATION
	 * @param x The new x location (in tiles)
	 * @param y The new y location (in tiles)
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
		
	}
	
	/**
	 * Only used for the SAY instruction: specifies the string to say
	 * @param task The instruction for the NPC -  only applicable for SAY
	 * @param s The string to be spoken by the NPC
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
	}
	
	public void add(Instructions task, String[] q)
	{
		instructions.add(task);
		waitIndices.add(null);
		sprites.add(null);
		xLocations.add(null);
		yLocations.add(null);
		words.add(null);
		questions.add(q);
	}
	
	public ArrayList<Instructions> getInstructions()
	{
		return instructions;
	}
	
	public int getWaitIndex(int i)
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
	
}
