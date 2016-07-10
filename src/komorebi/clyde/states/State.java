/**
 * State.java    May 15, 2016, 11:43:10 PM
 *
 * -
 */

package komorebi.clyde.states;

import komorebi.clyde.entities.Clyde;
import komorebi.clyde.map.Map;

/**
 * Represents a state in the game
 * 
 * @author Aaron Roy
 */
public abstract class State {
  protected static Clyde play;
  protected static Map map;

  /**
   * Represents all of the states in the game
   * 
   * @author Aaron Roy
   */
  public enum States{
    GAME, EDITOR, MENU;
  }

  public abstract void getInput();
  public abstract void update();
  public abstract void render();
}
