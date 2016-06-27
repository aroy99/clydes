/**
 * Game.java		May 16, 2016, 9:52:23 PM
 *
 * -
 */
package komorebi.clyde.states;


import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import komorebi.clyde.entities.Clyde;

import komorebi.clyde.entities.NPC;
import komorebi.clyde.entities.NPCType;
import komorebi.clyde.script.Script;


/**
 * Represents the game
 * 
 * @author Aaron Roy
 * @version 
 */
public class Game extends State{
	
	public ArrayList<NPC> npcs;
	public ArrayList<Script> scripts;
	
	private boolean hasText, hasChoice, choosesLeft;
	private int pickIndex;
	private int maxOpt;
	
	private NPC speaker;
	
	private boolean wasSpaceDown, wasLeftDown, wasRightDown;
	
    public Game(){
    	
    	
        play = new Clyde(32,32);
        
        npcs = new ArrayList<NPC>();
        scripts = new ArrayList<Script>();
        
        NPC joe = new NPC(-1,6, NPCType.POKEMON);
        NPC vin = new NPC(0,0, NPCType.NESS);
        
        npcs.add(joe);
        npcs.add(vin);
        
        scripts.add(new Script("joe", joe, 5, 5, false));
        scripts.add(new Script("vin", vin, 1, 1, false));
    }
    
    /* (non-Javadoc)
     * @see komorebi.clyde.states.State#getInput()
     */
    @Override
    public void getInput() {
        
        play.getInput();  
        
        
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && !wasSpaceDown)
        {
        	wasSpaceDown = true;
        	if (hasText&&Keyboard.isKeyDown(Keyboard.KEY_SPACE))
            {
            	speaker.clearText();
            	
            	if (hasChoice) {
            		speaker.branch(pickIndex);
            	}
            	hasChoice=false;
            	hasText=false;
            }
        } else if (!Keyboard.isKeyDown(Keyboard.KEY_SPACE))
        {
        	wasSpaceDown = false;
        }
        
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) && !wasLeftDown)
        {
        	wasLeftDown = true;
        	if (hasChoice&&Keyboard.isKeyDown(Keyboard.KEY_LEFT))
        	{
        		pickIndex--;
        		if (pickIndex<1) pickIndex = maxOpt;
        		speaker.setPickerIndex(pickIndex);
        		//choosesLeft=!choosesLeft;
        		
        	}  
        } else if (!Keyboard.isKeyDown(Keyboard.KEY_LEFT))
        {
        	wasLeftDown = false;
        }
        
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && !wasRightDown)
        {
        	wasRightDown = true;
        	if (hasChoice&&Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
        	{
        		pickIndex++;
        		if (pickIndex>maxOpt) pickIndex = 1;
        		speaker.setPickerIndex(pickIndex);
        	}
        } else if (!Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
        {
        	wasRightDown = false;
        }
        
        
    }

    /* (non-Javadoc)
     * @see komorebi.clyde.states.State#update()
     */
    @Override
    public void update() {
        // TODO Auto-generated method stub
        play.update();
        for (NPC person: npcs)
        {
        	person.update();
        }
        
        
        
        for (Script s: scripts)
        {
        	if (!s.hasRun() && s.isLocationIntersected(play))
        	{
        		s.run();
        	}
        }
        
        Fader.update();
        
        //System.out.println(play.getTileX() + ", " + play.getTileY());
        
    }

    /* (non-Javadoc)
     * @see komorebi.clyde.states.State#render()
     */
    @Override
    public void render() {
        // TODO Auto-generated method stub
        play.render();
        
        for (NPC person: npcs)
        {
        	person.render();
        }
        
        Fader.render();
        
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
    
}
