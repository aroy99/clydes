/**
 * Map.java    May 30, 2016, 11:32:19 AM
 */

package komorebi.clyde.map;

import komorebi.clyde.editor.Palette;
import komorebi.clyde.engine.MainE;
import komorebi.clyde.engine.Playable;
import komorebi.clyde.states.Editor;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Represents a map of tiles
 * 
 * @author Aaron Roy
 * @version 
 */
public class Map implements Playable{

  //Mouse buttons
  private boolean lButtonIsDown, lButtonWasDown;//Left Button Clicked
  private boolean rButtonIsDown, rButtonWasDown;//Right Button Clicked
  private boolean mButtonIsDown, mButtonWasDown;//Middle Button Pressed

  //Arrow keys
  private boolean up, down, left, right;        //Directions for movement

  //Special commands
  private boolean isSave, wasSave;              //Save the map
  private boolean isNewSave, wasNewSave;        //Saves a new map file
  private boolean isReset, wasReset;            //Resets tiles position
  private boolean isGrid, wasGrid;              //Turn on/off Grid
  private boolean startDragging;                //Starting a group selection
  private boolean isDragging;                   //Is making a group selection
  private boolean isSelection;                  //A selection is active
  private boolean isClearSel, wasClearSel;      //Clears the selection
  
  private boolean saved = true;


  private Palette pal;
  private Tile[][] tiles;                       //The Map itself
  private Tile[][] selection;



  private float x, y;         //Current location
  private int initX, initY; //Location at the beginning of a drag
  private float dx, dy;
  private float speed = 10;

  private String savePath;  //Path to save the map by default
  private String saveName;  //Name to save the map by default


  /**
   * Creates a new Map of the dimensions col x row
   * @param col number of columns (x)
   * @param row number of rows (y)
   */
  public Map(int col, int row){
    tiles = new Tile[row][col];
    pal = Editor.getPalette();

    for (int i = tiles.length-1; i >= 0; i--) {
      for (int j = 0; j < tiles[0].length; j++) {
        tiles[i][j] = new Tile(j, i, TileList.BLANK);
      }
    }
  }
  
  /**
   * Creates a map from a map file, used for the Editor
   * 
   * @param key The location of the map
   * @param name The name of the file
   */
  public Map(String key, String name){
    pal = Editor.getPalette();
    try {
      BufferedReader reader = new BufferedReader(new FileReader(
          new File(key)));
      int rows = Integer.parseInt(reader.readLine());
      int cols = Integer.parseInt(reader.readLine());

      tiles = new Tile[rows][cols];


      for (int i = 0; i < tiles.length; i++) {
        String[] str = reader.readLine().split(" ");
        int index = 0;
        for (int j = 0; j < cols; j++, index++) {
          if(str[index].equals("")){
            index++;  //pass this token, it's blank
          }
          tiles[i][j] =new Tile(j, i, 
              TileList.getTile(Integer.parseInt(str[index])));
        }
      }

      savePath = key;
      saveName = name;

      Display.setTitle("Clyde\'s Editor - "+name);
      
      reader.close();
    } catch (IOException | NumberFormatException e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, 
          "The file was not found / was corrupt, therefore, the default settings were used");
      tiles = new Tile[10][10];
      pal = Editor.getPalette();

      for (int i = tiles.length-1; i >= 0; i--) {
        for (int j = 0; j < tiles[0].length; j++) {
          tiles[i][j] = new Tile(j, i, TileList.BLANK);
        }
      }
      
      Editor.reloadKeyboard();
    }         


  }


  /**
   * Creates a map from a map file, used for the game
   * 
   * @param key The location of the map
   */
  public Map(String key){
    try {
      BufferedReader reader = new BufferedReader(new FileReader(
          new File(key)));
      int rows = Integer.parseInt(reader.readLine());
      int cols = Integer.parseInt(reader.readLine());

      tiles = new Tile[rows][cols];


      for (int i = 0; i < tiles.length; i++) {
        String[] str = reader.readLine().split(" ");
        int index = 0;
        for (int j = 0; j < cols; j++, index++) {
          if(str[index].equals("")){
            index++;  //pass this token, it's blank
          }
          tiles[i][j] =new Tile(j, i, 
              TileList.getTile(Integer.parseInt(str[index])));
        }
      }
      
      reader.close();
    } catch (IOException | NumberFormatException e) {
      e.printStackTrace();
    }         


  }

  /* (non-Javadoc)
   * @see komorebi.clyde.engine.Playable#getInput()
   */
  @Override
  public void getInput() {
    lButtonWasDown = lButtonIsDown;
    lButtonIsDown = Mouse.isButtonDown(0);

    rButtonWasDown = rButtonIsDown;
    rButtonIsDown = Mouse.isButtonDown(1) && !controlPressed();

    mButtonWasDown = mButtonIsDown;
    mButtonIsDown = Mouse.isButtonDown(2);

    startDragging = Mouse.isButtonDown(1) && controlPressed() && 
        !isDragging;

    isDragging = Mouse.isButtonDown(1) && isDragging || startDragging;

    //Makes sure that up and down / left and right can't be both true
    up =   upPressed()    && !downPressed();

    down = downPressed()  && !(upPressed() || controlPressed());

    left = leftPressed()  && !rightPressed();

    right =rightPressed() && !leftPressed();

    wasReset = isReset;
    isReset = Keyboard.isKeyDown(Keyboard.KEY_R) && !controlPressed();

    wasSave = isSave;
    isSave = Keyboard.isKeyDown(Keyboard.KEY_S) && controlPressed() && !shiftPressed();
    
    wasNewSave = isNewSave;
    isNewSave = Keyboard.isKeyDown(Keyboard.KEY_S) && controlPressed() && shiftPressed();

    wasGrid = isGrid;
    isGrid = Keyboard.isKeyDown(Keyboard.KEY_G) && controlPressed();

    wasClearSel = isClearSel;
    isClearSel = Keyboard.isKeyDown(Keyboard.KEY_C) && controlPressed();

  }


  /* (non-Javadoc)
   * @see komorebi.clyde.engine.Renderable#update()
   */
  @Override
  public void update() {
    //Sets mouse tile to the one from the palette
    if(lButtonIsDown && checkBounds() && !isSelection){
      tiles[getMouseY()][getMouseX()].setType(pal.getSelected().getType());
      saved = false;
      if(Display.getTitle().charAt(Display.getTitle().length()-1) != '*'){
        Display.setTitle(Display.getTitle()+"*");
      }
    }

    //Sets palette's selected to mouse tile
    if(rButtonIsDown && !rButtonWasDown && checkBounds()){
      pal.setLoc(tiles[getMouseY()][getMouseX()].getType());
      clearSelection();
    }

    //Flood Fills tiles
    if(mButtonIsDown && !mButtonWasDown && checkBounds()){
      flood(getMouseX(), getMouseY(), 
          tiles[getMouseY()][getMouseX()].getType());
      saved = false;
      if(Display.getTitle().charAt(Display.getTitle().length()-1) != '*'){
        Display.setTitle(Display.getTitle()+"*");
      }
    }

    //Creates a selection
    if(startDragging){
      initX = getMouseX();
      initY = getMouseY();
    }
    if(isDragging && checkBounds()){
      createSelection();
    }

    if(isSelection && lButtonIsDown && checkBounds() && !lButtonWasDown){
      for(int i = 0; i < selection.length; i++){
        for (int j = 0; j < selection[0].length; j++) {
          if(checkTileBounds(getMouseY()+i, getMouseX()+j)){
            tiles[getMouseY()+i][getMouseX()+j].setType(
                selection[i][j].getType());
          }
        }
      }
      saved = false;
      if(Display.getTitle().charAt(Display.getTitle().length()-1) != '*'){
        Display.setTitle(Display.getTitle()+"*");
      }
    }

    if(isClearSel && isSelection && !wasClearSel){
      clearSelection();
    }

    //Resets tiles to default position
    if(isReset && !wasReset){
      x = 0;
      y = 0;
      
      for (int i = 0; i < tiles.length; i++) {
        for (int j = 0; j < tiles[0].length; j++) {
          tiles[i][j].setLoc(i*16, j*16);
          System.out.println(tiles[i][j]);
        }
      }
    }

    if(isSave && !wasSave){
      if(savePath == null){
        newSave();
      }else{
        save(savePath, saveName);
      }
    }
    
    if(isNewSave && !wasNewSave){
      newSave();
    }

    if(isGrid && !wasGrid){
      Tile.changeGrid();
    }


    if(up){
      dy =  speed;
    }
    if(down){
      dy = -speed;
    }
    if(right){
      dx =  speed;
    }
    if(left){
      dx = -speed;
    }

    move(dx, dy);

    dx = 0;
    dy = 0;
  }

  /* (non-Javadoc)
   * @see komorebi.clyde.engine.Renderable#render()
   */
  @Override
  public void render() {
    for (Tile[] tileL : tiles) {
      for (Tile t : tileL) {
        t.render();
      }
    }
    if(selection != null){
      for (Tile[] tileL : selection) {
        for (Tile t : tileL) {
          t.render();
        }
      }

    }
  }


  /**
   * Moves the entire map by the specified amount
   * 
   * @param dx pixels to move left/right
   * @param dy pixels to move up/down
   */
  public void move(float dx, float dy) {
    x+=dx;
    y+=dy;
    for (int i = 0; i < tiles.length; i++) {
      for (int j = 0; j < tiles[0].length; j++) {
        tiles[i][j].setLoc(x+j*16, y+i*16);
        tiles[i][j].update();
      }
    }

    if(selection != null){
      for (int i = 0; i < selection.length; i++) {
        for (int j = 0; j < selection[0].length; j++) {
          selection[i][j].move(dx, dy);
          selection[i][j].update();
        }
      }
    }
  }
  
  /**
   * Saves the Map file
   * 
   * @return true if the save completed successfully, false if not
   */
  public boolean newSave() {

    JFileChooser chooser = new JFileChooser("res/maps/"){
      /**
       * I don't know what this does, but it does something...
       */
      private static final long serialVersionUID = 3881189161552826430L;

      @Override
      public void approveSelection(){
        File f = getSelectedFile();
        if(f.exists() && getDialogType() == SAVE_DIALOG){
          int result = JOptionPane.showConfirmDialog(this,
              "The file exists, overwrite?","Existing file",JOptionPane.YES_NO_CANCEL_OPTION);
          switch(result){
            case JOptionPane.YES_OPTION:
              super.approveSelection();
              return;
            case JOptionPane.NO_OPTION:
              return;
            case JOptionPane.CLOSED_OPTION:
              return;
            case JOptionPane.CANCEL_OPTION:
              cancelSelection();
              return;
            default:
              return;
          }
        }
        super.approveSelection();
      }
    };
    
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "Map Files", "map");
    chooser.setFileFilter(filter);
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    chooser.setDialogTitle("Enter the name of the map to save");
    int returnee = chooser.showSaveDialog(null);

    Editor.reloadKeyboard();
    
    if(returnee == JFileChooser.APPROVE_OPTION){

      savePath = chooser.getSelectedFile().getAbsolutePath();
      saveName = chooser.getSelectedFile().getName();
      
      return save(savePath, saveName);
    }
    
    return false;
  }


  /**
   * @param path The file location of the path being saved
   * @param name The name of the file that is being saved
   */
  public boolean save(String path,String name) {
    PrintWriter writer;

    try {
      if(path.substring(path.length()-4).equals(".map")){
        writer = new PrintWriter(path, "UTF-8");
      }else{
        writer = new PrintWriter(path+".map", "UTF-8");
      }
      writer.println(tiles.length);
      writer.println(tiles[0].length);

      for (Tile[] tile : tiles) {
        for (Tile t : tile) {
          writer.print(t.getType().getID() + " ");
        }
        writer.println();
      }
      System.out.println("Save complete");
      saved = true;
      writer.close();
      if(name.substring(name.length()-4).equals(".map")){
        Display.setTitle("Clyde\'s Editor - " + name);
      }else{
        Display.setTitle("Clyde\'s Editor - " + name + ".map");
      }

      
      return true;
    } catch (FileNotFoundException | UnsupportedEncodingException e) {
      e.printStackTrace();
      return false;
    }
    
  }

  /**
   * @return if the up key was pressed
   */
  private boolean upPressed() {
    return (Keyboard.isKeyDown(Keyboard.KEY_UP) ||
        Keyboard.isKeyDown(Keyboard.KEY_W));
  }

  /**
   * @return if the down key was pressed
   */
  private boolean downPressed() {
    return (Keyboard.isKeyDown(Keyboard.KEY_DOWN) ||
        Keyboard.isKeyDown(Keyboard.KEY_S));
  }

  /**
   * @return if the left key was pressed
   */
  private boolean leftPressed() {
    return (Keyboard.isKeyDown(Keyboard.KEY_LEFT) ||
        Keyboard.isKeyDown(Keyboard.KEY_A));
  }

  /**
   * @return if the right key was pressed
   */
  private boolean rightPressed() {
    return (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) ||
        Keyboard.isKeyDown(Keyboard.KEY_D));
  }

  /**
   * @return if the control key was pressed
   */
  private boolean controlPressed() {
    return (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) ||
        Keyboard.isKeyDown(Keyboard.KEY_RCONTROL));
  }
  
  /**
   * @return if the shift key was pressed
   */
  private boolean shiftPressed() {
    return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) ||
        Keyboard.isKeyDown(Keyboard.KEY_RSHIFT));
  }


  /**
   * Creates a new selection
   */
  private void createSelection() {
    selection = new Tile[Math.abs(getMouseY()-initY)+1]
        [Math.abs(getMouseX()-initX)+1];
    int firstX, lastX;
    int firstY, lastY;

    firstX = Math.min(initX, getMouseX());
    firstY = Math.min(initY, getMouseY());

    lastX = Math.max(initX, getMouseX());
    lastY = Math.max(initY, getMouseY());


    for(int i = 0; i <= lastY - firstY; i++){
      for(int j = 0; j <= lastX - firstX; j++){
        selection[i][j] = new Tile(x + tiles[0].length*16 + j*16, 
            y + i*16, tiles[firstY+i][firstX+j].getType(), true);
      }
    }

    isSelection = true;
  }


  /**
   * Recursive method that flood fills tiles
   * 
   * @param mouseX starting tile x
   * @param mouseY starting tile y
   * @param type tile to search and destroy
   */
  private void flood(int mouseX, int mouseY, TileList type) {
    if (mouseX < 0 || mouseX >= tiles[0].length ||
        mouseY < 0 || mouseY >= tiles.length){
      return;
    }
    if(tiles[mouseY][mouseX].getType() != type ||
        tiles[mouseY][mouseX].getType() == pal.getSelected().getType()){
      return;
    }
    tiles[mouseY][mouseX].setType(pal.getSelected().getType());
    flood(mouseX-1, mouseY,   type);
    flood(mouseX+1, mouseY,   type);
    flood(mouseX,   mouseY+1, type);
    flood(mouseX,   mouseY-1, type);

  }

  /**
   * Checks if the Mouse is in bounds of the map
   * @return Mouse is in map
   */
  private boolean checkBounds() {
    return (Mouse.getX()/MainE.getScale() < Palette.xOffset*16 ||
        Mouse.getY()/MainE.getScale() < Palette.yOffset*16) &&
       (getMouseY() >= 0 &&
        getMouseY() < tiles.length &&
        getMouseX() >= 0 &&
        getMouseX() < tiles[0].length);
  }

  /**
   * Checks if the tile is valid
   * 
   * @param tx the X index of the tile
   * @param ty the Y index of the tile
   * @return true if the tile is in bounds
   */
  private boolean checkTileBounds(int tx, int ty) {
    return ty >= 0 &&           
        ty < tiles.length && 
        tx >= 0 &&           
        tx < tiles[0].length;
  }


  /**
   * Converts Mouse X into a tile index, adjusting for map position
   * @return adjusted mouse x
   */
  private int getMouseX(){
    return ((Mouse.getX()/MainE.getScale())-(int)x)/(16);
  }

  /**
   * Converts Mouse Y into a tile index, adjusting for map position
   * @return adjusted mouse y
   */
  private int getMouseY() {
    return ((Mouse.getY()/MainE.getScale())-(int)y)/(16);
  }

  public Tile[][] getSelection(){
    return selection;
  }

  public void setSelection(Tile[][] sel){
    selection = sel;
  }

  public int getWidth(){
    return tiles[0].length;
  }

  public int getHeight(){
    return tiles.length;
  }

  /**
   * Sets isSelection to s
   * 
   * @param selec value to set the selection to
   */
  public void setIsSelection(boolean selec) {
    isSelection = selec;        
  }

  /**
   * Clears the selection, making it disappear
   */
  public void clearSelection(){
    selection = null;
    isSelection = false;
  }
  
  public float getX() {
    return x;
  }

  public String getPath() {
    return savePath;
  }

  public String getName() {
    return saveName;
  }

  public float getY() {
    return y;
  }
  
  public boolean wasSaved(){
    return saved;
  }


}

