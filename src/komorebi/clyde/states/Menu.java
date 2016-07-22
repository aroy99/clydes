/**
 * Menu.java Jul 6, 2016, 6:17:53 PM
 */
package komorebi.clyde.states;

import komorebi.clyde.engine.Draw;
import komorebi.clyde.engine.Key;
import komorebi.clyde.engine.KeyHandler;
import komorebi.clyde.script.TextHandler;

/**
 * 
 * @author Andrew Faulkenberry
 * @version 
 */
public class Menu extends State {

  private TextHandler text;
  private int pickIndex;


  /**
   * Creates a menu object
   */
  public Menu()
  {
    text = new TextHandler(false);
    text.write("Clyde's", 72, 160, 16);
    text.write("Controls", 72, 64, 12);
    pickIndex = 1;
  }

  /* (non-Javadoc)
   * @see komorebi.clyde.states.State#getInput()
   */
  @Override
  public void getInput() {
    if (KeyHandler.keyClick(Key.SPACE))
    {
      //Blah
    }

  }

  /* (non-Javadoc)
   * @see komorebi.clyde.states.State#update()
   */
  @Override
  public void update() {


  }

  /* (non-Javadoc)
   * @see komorebi.clyde.states.State#render()
   */
  @Override
  public void render() {
    Draw.rect(0, 0, 284, 284, 0, 0, 1, 1, 1);
    text.render();

    switch (pickIndex)
    {
      case 1:
        Draw.rect(60, 64, 8, 8, 0, 0, 8, 8, 7);
        break;
      default: break;
    }

  }

}
