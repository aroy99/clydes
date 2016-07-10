/**
 * Editor.java    May 16, 2016, 10:03:58 PM
 */
package komorebi.clyde.states;

import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glViewport;

import komorebi.clyde.editor.Palette;
import komorebi.clyde.map.Map;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * Represents the level editor
 * 
 * @author Aaron Roy
 * @version 
 */
public class Editor extends State{

  private boolean isLoad, wasLoad;  //Whether to load a new map or not
  private boolean isNew, wasNew;    //Whether to create a new map or not
  private boolean isResetTile, wasResetTile;//Reset the map
  
  private static Palette pal;
  public static float aspect;
  public static float xSpan = 1;
  public static float ySpan = 1;


  /**
   * Creates a new editor with a map that is 20*20
   */
  public Editor(){
    pal = new Palette();
    map = new Map(20, 20);
    pal.setMap(map);
  }


  /**
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
    
    wasResetTile = isResetTile;
    isResetTile=Keyboard.isKeyDown(Keyboard.KEY_R) && controlPressed();
    
    wasNew = isNew;
    isNew = Keyboard.isKeyDown(Keyboard.KEY_N) && controlPressed();

    pal.getInput();
    map.getInput();
  }

  /**
   * @see komorebi.clyde.states.State#update()
   */
  @Override
  public void update() {
    if(isLoad && !wasLoad){
      if(requestSave()){
        JFileChooser chooser = new JFileChooser("res/maps/");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Map Files", "map");
        chooser.setFileFilter(filter);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setDialogTitle("Enter the name of the map to load");
        int returnee = chooser.showOpenDialog(null);

        reloadKeyboard();
        
        if(returnee == JFileChooser.APPROVE_OPTION){
          map = new Map(chooser.getSelectedFile().getAbsolutePath(), 
              chooser.getSelectedFile().getName());
          pal.setMap(map);
        }
      }

    }

    if(isResetTile && !wasResetTile){
      if(map.getPath() != null && requestSave()){
        map = new Map(map.getPath(), map.getName());
      }else if(requestSave()){
        map = new Map(map.getWidth(),map.getHeight());
      }
    }
    
    if(isNew && !wasNew){
      if(requestSave()){
        NewMapDialog dialog = new NewMapDialog();
        dialog.pack();
        dialog.setVisible(true);

        int width = dialog.getActWidth();
        int height = dialog.getActHeight();

        if(width != 0 && height != 0){
          map = new Map(width, height);
          pal.setMap(map);
        }
        reloadKeyboard();
      }
    }
    
    pal.update();
    map.eUpdate();
  }

  /**
   * Asks the player if they want to save the map
   */
  public static boolean requestSave() {
    boolean continyu = true;
    if(!map.wasSaved()){
      
      int returnee = JOptionPane.showConfirmDialog(null, "Would you like to save?");
      
      reloadKeyboard();
      
      switch(returnee){
        case JFileChooser.APPROVE_OPTION:
          continyu = map.newSave();
          break;
        case JFileChooser.CANCEL_OPTION:
          continyu = true;
          break;
        default:
          continyu = false;
          break;
      }
    }
    return continyu;    
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
   * @return The palette so it can be used by the map
   */
  public static Palette getPalette() {
    return pal;
  }
  
  /**
   * @return true if the current map wasn't edited without saving, false if not
   */
  public static boolean wasSaved(){
    return map.wasSaved();
  }

  /**
   * For some stupid reason the keys stick when JDialogs are opened, so this
   * method resets the Keyboard by destroying and creating it
   */
  public static void reloadKeyboard(){
    Keyboard.destroy();
    try {
      Keyboard.create();
    } catch (LWJGLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }


  /**
   * Resizes the window, broken, so do not use
   */
  @Deprecated
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
  }

  /**
   * @return if the control key was pressed
   */
  private boolean controlPressed() {
    return (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) ||
        Keyboard.isKeyDown(Keyboard.KEY_RCONTROL));
  }

  
  /**
   * The Dialog that is created when Ctrl-N is pressed
   * 
   * @author Aaron Roy
   */
  private class NewMapDialog extends JDialog implements ActionListener, 
                                                        PropertyChangeListener{
    /**
     * I don't know what this does, but it does something...
     */
    private static final long serialVersionUID = -7199179946667631093L;

    JTextField width = new JTextField(3);
    JTextField height = new JTextField(3);
    String entWidth, entHeight;
    int actWidth, actHeight;
    JOptionPane options;
    String btnCreate = "Create";
    String btnCancel = "Cancel";
    
    public NewMapDialog(){
      super((Frame)null, true);
      setTitle("Create a New Map");
      setLocationRelativeTo(null);
      
      
      Object[] contents = {
          new JLabel("Width: "),width,
          Box.createHorizontalStrut(15),
          new JLabel("Height: "), height
      };
      
      Object[] buttons = {btnCreate, btnCancel};
      
      options = new JOptionPane(contents, JOptionPane.PLAIN_MESSAGE, 
          JOptionPane.YES_NO_OPTION, null, buttons, buttons[0]);
      
      addComponentListener(new ComponentAdapter() {
        public void componentShown(ComponentEvent ce) {
            width.requestFocusInWindow();
        }
      });
      
      setContentPane(options);
      
      width.addActionListener(this);
      height.addActionListener(this);

      options.addPropertyChangeListener(this);


    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
      options.setValue(btnCreate);
    }

    
    @Override
    public void propertyChange(PropertyChangeEvent e) {
      String prop = e.getPropertyName();
      
      if(e.getSource() == options &&
          (JOptionPane.VALUE_PROPERTY.equals(prop) ||
           JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
        Object value = options.getValue();

        if (value == JOptionPane.UNINITIALIZED_VALUE) {
          //ignore reset
          return;
        }

        //Reset the JOptionPane's value.
        //If you don't do this, then if the user
        //presses the same button next time, no
        //property change event will be fired.
        options.setValue(JOptionPane.UNINITIALIZED_VALUE);
        
        if(btnCreate.equals(value)){
          entWidth = width.getText();
          entHeight = height.getText();
          
          if(entWidth.matches("[1-9]\\d{0,2}?") && 
              entHeight.matches("[1-9]\\d{0,2}?")){
            int tWidth = Integer.parseInt(entWidth);
            int tHeight = Integer.parseInt(entHeight);
            
            if(tWidth <= 128 && tHeight <= 128){
              actWidth = tWidth;
              actHeight = tHeight;
              clearAndHide();
            }else{
              complain();

            }
          }else{
            complain();
          }
          
        }else{
          clearAndHide();
        }
      }
    }
    
    
    
    public int getActWidth(){
      System.out.println(actWidth);
      return actWidth;
    }

    public int getActHeight(){
      return actHeight;
    }
    
    
    private void complain(){
      JOptionPane.showMessageDialog(this, "Sorry, make sure your numbers are between 1 and 128", 
          "Please Try Again", JOptionPane.ERROR_MESSAGE);
    }
    
    /** This method clears the dialog and hides it. */
    public void clearAndHide() {
      width.setText(null);
      height.setText(null);
      setVisible(false);
    }

  }

}


