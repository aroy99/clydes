/**
 * State.java		May 15, 2016, 11:43:10 PM
 *
 * -
 */
package komorebi.clyde.states;

import komorebi.clyde.entities.Clyde;

/**
 * Represents a state in the game
 * 
 * @author Aaron Roy
 * @version 0.0.1.0
 */
public abstract class State {
    protected static Clyde play;

    public enum States{
        GAME, EDITOR;
    }
    
    public abstract void getInput();
    public abstract void update();
    public abstract void render();
}
