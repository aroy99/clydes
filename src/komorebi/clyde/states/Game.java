/**
 * Game.java		May 16, 2016, 9:52:23 PM
 *
 * -
 */
package komorebi.clyde.states;

import org.lwjgl.input.Keyboard;

import komorebi.clyde.engine.GameHandler;
import komorebi.clyde.entities.Clyde;
import komorebi.clyde.map.Map;

/**
 * Represents the game
 * 
 * @author Aaron Roy
 * @version 
 */
public class Game extends State{

    public Game(){
        play = new Clyde(120,100);
        map = new Map("res/Terra.png");

    }
    
    /* (non-Javadoc)
     * @see komorebi.clyde.states.State#getInput()
     */
    @Override
    public void getInput() {
        // TODO Auto-generated method stub
        play.getInput();
    }

    /* (non-Javadoc)
     * @see komorebi.clyde.states.State#update()
     */
    @Override
    public void update() {
        // TODO Auto-generated method stub
        play.update();
    }

    /* (non-Javadoc)
     * @see komorebi.clyde.states.State#render()
     */
    @Override
    public void render() {
        // TODO Auto-generated method stub
        map.render();
        play.render();

    }
    
    public static Map getMap(){
        return map;
    }

}
