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

import org.lwjgl.input.Keyboard;

import komorebi.clyde.engine.GameHandler;
import komorebi.clyde.entities.Clyde;
import komorebi.clyde.map.Map;

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
    
    private boolean isSpace,wasSpace;
    private boolean isLeft,wasLeft;
    private boolean isRight,wasRight;
    
    private BufferedReader read;


    public Game(){
        play = new Clyde(120,100);
        map = new Map("res/maps/Some Town.map");
        
        npcs = new ArrayList<NPC>();
        scripts = new ArrayList<Script>();
        
        loadMap("Map1");
                
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
        
        wasSpace = isSpace;
        isSpace = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
        
        wasLeft = isLeft;
        isLeft = Keyboard.isKeyDown(Keyboard.KEY_LEFT);
        
        wasRight = isRight;
        isRight = Keyboard.isKeyDown(Keyboard.KEY_RIGHT);
    }

    /* (non-Javadoc)
     * @see komorebi.clyde.states.State#update()
     */
    @Override
    public void update() {
        play.update();
        
        if (hasText && isSpace && !wasSpace) {
            speaker.clearText();
            
            if (hasChoice) {
                speaker.branch(pickIndex);
            }
            hasChoice=false;
            hasText=false;
        }
        
        if (hasChoice && isLeft && !wasLeft) {
            pickIndex--;
            if (pickIndex<1) pickIndex = maxOpt;
            speaker.setPickerIndex(pickIndex);
            
        }  

        if (hasChoice && isRight && !wasRight) {
            pickIndex++;
            if (pickIndex>maxOpt) pickIndex = 1;
            speaker.setPickerIndex(pickIndex);
        }


        
        for (NPC person: NPC.getNPCs()) {
            person.update();
        }
        
        for (Script s: scripts) {
            if (!s.hasRun() && s.isLocationIntersected(play)) {
                s.run();
            }
        }
        
        Fader.update();
    }

    /* (non-Javadoc)
     * @see komorebi.clyde.states.State#render()
     */
    @Override
    public void render() {
        // TODO Auto-generated method stub
        map.render();
        play.render();
        
        for (NPC person: NPC.getNPCs())
        {
            person.render();
        }
        
        Fader.render();

    }
    
    public static Map getMap(){
        return map;
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
    
    public NPC getNpc(String s) {
        for (NPC person: NPC.getNPCs())
        {
            if (person.getName().equals(s))
            {
                return person;
            }
        }
        return null;
    }
    
    public void loadMap(String mapFile) {
        try {
            read = new BufferedReader(
                    new FileReader(new File("res/scripts/"+mapFile+".txt")));
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
                    
                    NPC.add(new NPC(split[0], Integer.parseInt(split[1]), Integer.parseInt(split[2]), NPCType.toEnum(split[3])));
                }
                else if (s.startsWith("script"))
                {
                    s = s.replace("script ", "");
                    String[] split = s.split(" ");
                    
                    scripts.add(new Script(split[0], Integer.parseInt(split[1]), Integer.parseInt(split[2]), false, NPC.get(split[3])));
                }
                
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
    }


}
