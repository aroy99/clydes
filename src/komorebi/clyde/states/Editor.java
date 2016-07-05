/**
 * Editor.java    May 16, 2016, 10:03:58 PM
 *
 * -
 */
package komorebi.clyde.states;

import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glViewport;

import komorebi.clyde.editor.Palette;
import komorebi.clyde.map.Map;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * Represents the level editor
 * 
 * @author Aaron Roy
 * @version 
 */
public class Editor extends State{

  private static Map currMap;

  private boolean isLoad;
  private boolean wasLoad;

  private static Palette pal;
  public static float aspect;
  public static float xSpan = 1;
  public static float ySpan = 1;


  /**
   * Creates a new editor with a map that is 20*20
   */
  public Editor(){
    pal = new Palette();
    currMap = new Map(20, 20);
    pal.setMap(currMap);
  }


  /* (non-Javadoc)
   * @see komorebi.clyde.states.State#getInput()
   */
  @Override
  public void getInput() {
    //        if(Display.wasResized())resize();
    if(Keyboard.isKeyDown(Keyboard.KEY_P)){
      Runtime runTime = Runtime.getRuntime();
      try {
        runTime.exec("java -jar \"RealGame v 0.1.jar\"");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    wasLoad = isLoad;
    isLoad = Keyboard.isKeyDown(Keyboard.KEY_L) && controlPressed();


    pal.getInput();
    currMap.getInput();
  }

  /* (non-Javadoc)
   * @see komorebi.clyde.states.State#update()
   */
  @Override
  public void update() {
    if(isLoad && !wasLoad){
      JFileChooser chooser = new JFileChooser("res/maps/");
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
          "Map Files", "map");
      chooser.setFileFilter(filter);
      chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      chooser.setDialogTitle("Enter the name of the map to load");
      int returnee = chooser.showOpenDialog(null);

      if(returnee == JFileChooser.APPROVE_OPTION){
        currMap = new Map(chooser.getSelectedFile().getAbsolutePath());
        pal.setMap(currMap);
      }
    }

    pal.update();
    currMap.update();
  }

  /* (non-Javadoc)
   * @see komorebi.clyde.states.State#render()
   */
  @Override
  public void render() {
    currMap.render();
    pal.render();
  }


  /**
   * Returns the palette so it can be used by the map
   * 
   * @return pal
   */
  public static Palette getPalette() {
    return pal;
  }


  /**
   * Resizes the window
   */
  private static void resize() {
    final int height = Display.getHeight();
    final int width = Display.getWidth();
    aspect = (float)width/height;
    xSpan = 1;
    ySpan = 1;

    if(aspect > 1){
      xSpan *= aspect;
    }else{
      ySpan = xSpan/aspect;
    }
    glViewport(0, 0, width, height);

    glLoadIdentity();
    glOrtho(0,width,0,height,-1,1);     //Updates 3D space
    //        glOrtho(-xSpan,xSpan,-ySpan,ySpan,-1,1);     //Updates 3D space
    pal.reload();
  }

  /**
   * @return if the control key was pressed
   */
  private boolean controlPressed() {
    return (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) ||
        Keyboard.isKeyDown(Keyboard.KEY_RCONTROL));
  }


}
