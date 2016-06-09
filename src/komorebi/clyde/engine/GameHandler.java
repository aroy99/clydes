/*
 * GameHandler.java     Apr 27, 2016, 8:28:43 PM
 */

package komorebi.clyde.engine;

import komorebi.clyde.states.Editor;
import komorebi.clyde.states.Game;
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

  public GameHandler(){
    state = States.GAME;
    game = new Game();
    editor = new Editor();
  }

  public void getInput() {
    // TODO Auto-generated method stub
    switch(state){
    case EDITOR:
      editor.getInput();
      break;
    case GAME:
      game.getInput();
      break;
    default:
      break;
    }
  }

  public void update() {
    // TODO Auto-generated method stub
    switch(state){
    case EDITOR:
      editor.update();
      break;
    case GAME:
      game.update();
      break;
    default:
      break;
    }
  }

  public void render() {
    // TODO Auto-generated method stub
    switch (state) {
      case EDITOR:
        editor.render();
        break;
      case GAME:
        game.render();
        break;
      default:
        break;
    }
  }

  public static void switchState(States nstate){
    state = nstate;
  }

}
