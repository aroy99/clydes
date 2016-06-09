/**
 * Editor.java		May 16, 2016, 10:03:58 PM
 *
 * -
 */
package komorebi.clyde.states;

import komorebi.clyde.editor.Palette;
import komorebi.clyde.engine.MainE;
import komorebi.clyde.map.Map;
import komorebi.clyde.map.Tile;
import komorebi.clyde.map.TileList;

import org.lwjgl.input.Mouse;

/**
 * Represents the level editor
 * 
 * @author Aaron Roy
 * @version 
 */
public class Editor extends State{
    
    private Map map;
    private static Palette pal;
    
    public Editor(){
        pal = new Palette();
        map = new Map(20, 20);
    }
    
    
    /* (non-Javadoc)
     * @see komorebi.clyde.states.State#getInput()
     */
    @Override
    public void getInput() {
        
        pal.getInput();
        map.getInput();
    }

    /* (non-Javadoc)
     * @see komorebi.clyde.states.State#update()
     */
    @Override
    public void update() {
        // TODO Auto-generated method stub
        pal.update();
        map.update();
    }

    /* (non-Javadoc)
     * @see komorebi.clyde.states.State#render()
     */
    @Override
    public void render() {
        map.render();
        pal.render();
    }


    /**
     * Returns the palette so it can be used by the map
     * 
     * @return pal
     */
    public static Palette getPalette() {
        // TODO Auto-generated method stub
        return pal;
    }

}
