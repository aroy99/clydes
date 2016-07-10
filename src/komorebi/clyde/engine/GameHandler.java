/*
 * GameHandler.java     Apr 27, 2016, 8:28:43 PM
 */

package komorebi.clyde.engine;

import komorebi.clyde.states.Editor;
import komorebi.clyde.states.Game;
import komorebi.clyde.states.Menu;
import komorebi.clyde.states.State.States;

/**
 * Updates, renders and gets input depending on the current state
 * 
 * @author Aaron Roy
 * @version 0.0.1.0
 * 
 */
public class GameHandler implements Playable{

  public static States state;

  private static Editor editor;
  public static Game game;
  private static Menu menu;

  /**
   * Creates the GameHandler
   */
  public GameHandler(){
    state = States.GAME;
    game = new Game();
    editor = new Editor();
    menu = new Menu();
  }

  /**
   * @see komorebi.clyde.engine.Playable#getInput()
   */
  public void getInput() {
    switch(state){
      case EDITOR:
        editor.getInput();
        break;
      case GAME:
        game.getInput();
        break;
      case MENU:
        menu.getInput();
        break;
      default:
        break;
    }
  }

  /**
   * @see komorebi.clyde.engine.Playable#update()
   */
  public void update() {
    switch(state){
      case EDITOR:
        editor.update();
        break;
      case GAME:
        game.update();
        break;
      case MENU:
        menu.update();
        break;
      default:
        break;
    }
  }

  /**
   * @see komorebi.clyde.engine.Playable#render()
   */
  public void render() {
    switch (state) {
      case EDITOR:
        editor.render();
        break;
      case GAME:
        game.render();
        break;
      case MENU:
        menu.render();
        break;
      default:
        break;
    }
  }

  /**
   * Switches to a new state
   * 
   * @param nstate The state to switch to
   */
  public static void switchState(States nstate){
    state = nstate;
  }

}
