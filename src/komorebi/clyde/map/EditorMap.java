/**
 * Map.java    May 30, 2016, 11:32:19 AM
 */

package komorebi.clyde.map;

import static komorebi.clyde.engine.KeyHandler.button;

import komorebi.clyde.editor.Palette;
import komorebi.clyde.engine.Draw;
import komorebi.clyde.engine.Key;
import komorebi.clyde.engine.KeyHandler;
import komorebi.clyde.engine.KeyHandler.Control;
import komorebi.clyde.engine.MainE;
import komorebi.clyde.engine.Playable;
import komorebi.clyde.entities.NPC;
import komorebi.clyde.entities.NPCType;
import komorebi.clyde.script.AreaScript;
import komorebi.clyde.script.TalkingScript;
import komorebi.clyde.script.WalkingScript;
import komorebi.clyde.script.WarpScript;
import komorebi.clyde.states.Editor;

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
public class EditorMap implements Playable{

  //Mouse buttons
  private boolean lButtonIsDown, lButtonWasDown;//Left Button Clicked
  private boolean rButtonIsDown, rButtonWasDown;//Right Button Clicked
  private boolean mButtonIsDown, mButtonWasDown;//Middle Button Pressed
  private boolean mouseSame;                    //Mouse is in same pos as last frame
  private int mx, my;                           //To track mouse position

  //Arrow keys
  private boolean up, down, left, right;        //Directions for movement

  //Special commands
  private boolean isSave;                    //Save the map
  private boolean isNewSave;                 //Saves a new map file
  private boolean isReset;                   //Resets tiles position
  private boolean isGrid;                    //Turn on/off Grid
  private boolean rStartDragging, rIsDragging;//Starting a group selection
  private boolean lStartDragging, lIsDragging;                //Is making a group selection
  private boolean isSelection;               //A selection is active
  private boolean isClearSel;                //Clears the selection

  //Map States
  private static boolean isMoveSet;          //Whether to set movement permission
  public static boolean grid;                //Whether the grid is on or not
  private boolean saved = true;


  private Palette pal;
  private TileList[][] tiles;                //The Map itself
  private boolean[][] collision;

  private TileList[][] selection;            //The selection

  public static final int SIZE = 16;  //Width and height of a tile

  private NPC[][] npcs;
  private AreaScript[][] scripts;

  private float x, y;       //Current location
  private int initX, initY; //Location at the beginning of a drag
  private float dx, dy;
  private float speed = 10;

  private String savePath;  //Path to save the map by default
  private String saveName;  //Name to save the map by default

  private static final int WIDTH = Display.getWidth();
  private static final int HEIGHT = Display.getHeight();




  /**
   * Creates a new Map of the dimensions col x row
   * @param col number of columns (x)
   * @param row number of rows (y)
   */
  public EditorMap(int col, int row){
    tiles = new TileList[row][col];
    collision = new boolean[row][col];
    npcs = new NPC[row][col];
    scripts = new AreaScript[row][col];
    pal = Editor.getPalette();

    for (int i = tiles.length-1; i >= 0; i--) {
      for (int j = 0; j < tiles[0].length; j++) {
        tiles[i][j] = TileList.BLANK;
      }
    }

  }


  /**
   * Creates a map from a map file, used for the Editor
   * 
   * @param key The location of the map
   * @param name The name of the file
   */
  public EditorMap(String key, String name){


    pal = Editor.getPalette();
    try {
      BufferedReader reader = new BufferedReader(new FileReader(
          new File(key)));

      int rows = Integer.parseInt(reader.readLine());
      int cols = Integer.parseInt(reader.readLine());

      tiles = new TileList[rows][cols];
      collision = new boolean[rows][cols];
      npcs = new NPC[rows][cols];
      scripts = new AreaScript[rows][cols];
      pal = Editor.getPalette();


      for (int i = 0; i < tiles.length; i++) {
        String[] str = reader.readLine().split(" ");
        int index = 0;
        for (int j = 0; j < cols; j++, index++) {
          if(str[index].equals("")){
            index++;  //pass this token, it's blank
          }
          tiles[i][j] = TileList.getTile(Integer.parseInt(str[index]));
          scripts[i][j] = null;
          npcs[i][j]=null;
        }
      }

      String s = reader.readLine();

      for (int i = 0; i < tiles.length; i++) {
        if(s == null || s.startsWith("npc")){
          break;
        }
        if(i!=0){
          s = reader.readLine();
        }
        String[] str = s.split(" ");
        int index = 0;
        for (int j = 0; j < cols; j++, index++) {
          if(str[index].equals("")){
            index++;  //pass this token, it's blank
          }
          collision[i][j]=str[index].equals("1")?true:false;
        }
      }


      savePath = key;
      saveName = name;

      Display.setTitle("Clyde\'s Editor - "+name);

      do
      {
        if(s==null){
          break;
        }
        
        if (s.startsWith("npc"))
        {
          s = s.replace("npc ", "");
          String[] split = s.split(" ");

          int arg0 = Integer.parseInt(split[2]);
          int arg1 = Integer.parseInt(split[1]);

          npcs[arg0][arg1] = new NPC(split[0], arg0, arg1,  NPCType.toEnum(split[3]));

          npcs[arg0][arg1].setWalkingScript(
              new WalkingScript(split[4], npcs[arg0][arg1]));
          npcs[arg0][arg1].setTalkingScript(
              new TalkingScript(split[5], npcs[arg0][arg1]));


        } else if (s.startsWith("script"))
        {
          s = s.replace("script ", "");
          String[] split = s.split(" ");

          int arg0 = Integer.parseInt(split[2]);
          int arg1 = Integer.parseInt(split[1]);

          scripts[arg0][arg1] = 
              new AreaScript(split[0], arg0, 
                  arg1, false, findNPC(split[3]));
        } else if (s.startsWith("warp"))
        {
          s = s.replace("warp ", "");
          String[] split = s.split(" ");

          int arg0 = Integer.parseInt(split[2]);
          int arg1 = Integer.parseInt(split[1]);

          scripts[arg0][arg1] =
              new WarpScript(split[0], arg0,
                  arg1, false);
        }
      }while ((s=reader.readLine()) != null);


      reader.close();
    } catch (IOException | NumberFormatException e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, 
          "The file was not found / was corrupt, therefore, the default settings were used");
      tiles = new TileList[10][10];
      pal = Editor.getPalette();


      for (int i = tiles.length-1; i >= 0; i--) {
        for (int j = 0; j < tiles[0].length; j++) {
          tiles[i][j] = TileList.BLANK;
        }
      }

      KeyHandler.reloadKeyboard();
    }         


  }

  /* (non-Javadoc)
   * @see komorebi.clyde.engine.Playable#getInput()
   */
  @Override
  public void getInput() {
    mouseSame = getMouseX() == mx && getMouseY() == my && lButtonIsDown;
    lButtonWasDown = lButtonIsDown;
    lButtonIsDown = Mouse.isButtonDown(0);

    mx = getMouseX();
    my = getMouseY();

    rButtonWasDown = rButtonIsDown;
    rButtonIsDown = Mouse.isButtonDown(1) && !KeyHandler.keyDown(Key.CTRL);

    mButtonWasDown = mButtonIsDown;
    mButtonIsDown = Mouse.isButtonDown(2);

    rStartDragging = Mouse.isButtonDown(1) && KeyHandler.keyDown(Key.CTRL) && 
        !rIsDragging;

    rIsDragging = Mouse.isButtonDown(1) && rIsDragging || rStartDragging;

    lStartDragging = Mouse.isButtonDown(0) && KeyHandler.keyDown(Key.CTRL) && 
        !lIsDragging;

    lIsDragging = Mouse.isButtonDown(0) && lIsDragging || lStartDragging;


    //Makes sure that up and down / left and right can't be both true
    up =   button(Control.MAP_UP)    && !button(Control.MAP_DOWN);

    down = button(Control.MAP_DOWN)  && 
        !(button(Control.MAP_UP) || KeyHandler.keyDown(Key.CTRL));


    left = button(Control.MAP_LEFT)  && !button(Control.MAP_RIGHT);

    right =button(Control.MAP_RIGHT) && !button(Control.MAP_LEFT);

    isReset = button(Control.REVERT_MAP);

    isSave = button(Control.SAVE);

    isNewSave = button(Control.NEW_SAVE);

    //    System.out.println(isSave + ", " + isNewSave);

    isGrid = button(Control.GRID);
  }


  /**
   * Used for the editor to help with performance
   */
  public void update(){
    //Sets mouse tile to the one from the palette
    if(lButtonIsDown && checkBounds() && !isSelection){
      int mx = getMouseX();
      int my = getMouseY();

      if(!isMoveSet){
        tiles[my][mx] = pal.getSelected();
      }else if(!mouseSame && isMoveSet){
        collision[my][mx] = true;
      }
      saved = false;
      if(Display.getTitle().charAt(Display.getTitle().length()-1) != '*'){
        Display.setTitle(Display.getTitle()+"*");
      }
    }

    //Sets palette's selected to mouse tile
    if(rButtonIsDown && checkBounds()){
      if(!isMoveSet && !rButtonWasDown){
        pal.setLoc(tiles[getMouseY()][getMouseX()]);
        clearSelection();
      }else if(!mouseSame && isMoveSet){
        collision[my][mx] = false;
      }
    }

    //Flood Fills tiles
    if(mButtonIsDown && !mButtonWasDown && checkBounds()){
      int mx = getMouseX();
      int my = getMouseY();

      if(!isMoveSet){
        flood(mx, my, tiles[my][mx]);
      }else{
        flood(mx, my, !collision[my][mx]);
      }
      saved = false;
      if(Display.getTitle().charAt(Display.getTitle().length()-1) != '*'){
        Display.setTitle(Display.getTitle()+"*");
      }
    }

    //Creates a selection
    if(rStartDragging || lStartDragging){
      initX = getMouseX();
      initY = getMouseY();
    }

    if(rIsDragging && checkBounds()){
      if(!isMoveSet){
        createSelection();
      }else{
        createSelection(true);
      }
    }

    if(lIsDragging && checkBounds() && isMoveSet){
      createSelection(false);
    }

    if(isSelection && lButtonIsDown && checkBounds() && !lButtonWasDown && !isMoveSet){
      for(int i = 0; i < selection.length; i++){
        for (int j = 0; j < selection[0].length; j++) {
          if(checkTileBounds(getMouseY()+i, getMouseX()+j)){
            tiles[getMouseY()+i][getMouseX()+j] = 
                selection[i][j];
          }
        }
      }
      saved = false;
      if(Display.getTitle().charAt(Display.getTitle().length()-1) != '*'){
        Display.setTitle(Display.getTitle()+"*");
      }
    }

    //Resets tiles to default position
    if(isSave){
      if(savePath == null){
        newSave();
      }else{
        save(savePath, saveName);
      }
    }

    if(isNewSave){
      newSave();
    }


    if(isGrid){
      changeGrid();
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

    if(isReset){
      x = 0;
      y = 0;
    }

    if(button(Control.MOVE_SET)){
      isMoveSet = !isMoveSet;
    }



    x+=dx;
    y+=dy;

    dx = 0;
    dy = 0;


  }


  /* (non-Javadoc)
   * @see komorebi.clyde.engine.Renderable#render()
   */
  @Override
  public void render() {
    for (int i = 0; i < tiles.length; i++) {
      for (int j = 0; j < tiles[0].length; j++) {
        if(checkTileInBounds(x+j*SIZE, y+i*SIZE)){
          Draw.rect(x+j*SIZE, y+i*SIZE, SIZE, SIZE, tiles[i][j].getX(), 
              tiles[i][j].getY(), 1);
          if(grid){
            Draw.rect(x+j*SIZE, y+i*SIZE, SIZE, SIZE, 0, 16, SIZE, 16+SIZE, 2);
          }

        }
      }
    }

    if(selection != null){
      for (int i = 0; i < selection.length; i++) {
        for (int j = 0; j < selection[0].length; j++) {
          Draw.rect(x+tiles[0].length*SIZE+j*SIZE, y+i*SIZE, SIZE, SIZE, 
              selection[i][j].getX(), selection[i][j].getY(), 1);
          if(grid){
            Draw.rect(x+selection[0].length*SIZE+j*SIZE, y+i*SIZE, SIZE, SIZE, 
                0, 16, SIZE, 16+SIZE, 2);
          }
        }
      }
    }

    for (NPC[] npcR: npcs) {
      for (NPC npc: npcR) {
        if (npc != null) 
        {
          npc.render();
        }
      }
    }

    if(isMoveSet){
      for (int i = 0; i < tiles.length; i++) {
        for (int j = 0; j < tiles[0].length; j++) {
          if(collision[i][j]){
            Draw.rect(x+j*SIZE, y+i*SIZE, SIZE, SIZE, 16, 16, 16, 16, 2);
          }else{
            Draw.rect(x+j*SIZE, y+i*SIZE, SIZE, SIZE, 17, 16, 17, 16, 2);
          }
        }
      }
    }
  }


  /**
   * Moves the entire map and all entities contained by it by the specified amount
   * 
   * @param dx pixels to move left/right
   * @param dy pixels to move up/down
   */
  public void move(float dx, float dy) {

    x+=dx;
    y+=dy;
    for (int i = 0; i < tiles.length; i++) {
      for (int j = 0; j < tiles[0].length; j++) {
        if (npcs[i][j] != null) 
        {
          npcs[i][j].setPixLocation((int) x+j*16+npcs[i][j].getXTravelled(), 
              (int) y+i*16+npcs[i][j].getYTravelled());
          npcs[i][j].update();

        }

        if (scripts[i][j] != null)
        {
          scripts[i][j].setAbsoluteLocation(x+j*16,y+i*16);
        }

      }
    }

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

      //The map itself
      for (TileList[] tile : tiles) {
        for (TileList t : tile) {
          writer.print(t.getID() + " ");
        }
        writer.println();
      }

      //The collision
      for (boolean[] tile : collision) {
        for (boolean t : tile) {
          writer.print((t?1:0) + " ");
        }
        writer.println();
      }

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

    KeyHandler.reloadKeyboard();

    if(returnee == JFileChooser.APPROVE_OPTION){

      savePath = chooser.getSelectedFile().getAbsolutePath();
      saveName = chooser.getSelectedFile().getName();

      return save(savePath, saveName);
    }

    return false;
  }



  /**
   * Creates a new selection
   */
  private void createSelection() {
    selection = new TileList[Math.abs(getMouseY()-initY)+1]
        [Math.abs(getMouseX()-initX)+1];
    int firstX, lastX;
    int firstY, lastY;

    firstX = Math.min(initX, getMouseX());
    firstY = Math.min(initY, getMouseY());

    lastX = Math.max(initX, getMouseX());
    lastY = Math.max(initY, getMouseY());


    for(int i = 0; i <= lastY - firstY; i++){
      for(int j = 0; j <= lastX - firstX; j++){
        selection[i][j] =  tiles[firstY+i][firstX+j];
      }
    }

    isSelection = true;
  }
  /**
   * Creates a new selection
   */
  private void createSelection(boolean type) {
    int firstX, lastX;
    int firstY, lastY;

    firstX = Math.min(initX, getMouseX());
    firstY = Math.min(initY, getMouseY());

    lastX = Math.max(initX, getMouseX());
    lastY = Math.max(initY, getMouseY());


    for(int i = firstY; i <= lastY; i++){
      for(int j = firstX; j <= lastX; j++){
        collision[i][j] = type;
      }
    }
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
    if(tiles[mouseY][mouseX] != type || tiles[mouseY][mouseX] == pal.getSelected()){
      return;
    }

    tiles[mouseY][mouseX] = pal.getSelected();
    flood(mouseX-1, mouseY,   type);
    flood(mouseX+1, mouseY,   type);
    flood(mouseX,   mouseY+1, type);
    flood(mouseX,   mouseY-1, type);
  }

  /**
   * Recursive method that flood fills collisions
   * 
   * @param mouseX starting tile x
   * @param mouseY starting tile y
   * @param type tile to search and destroy
   */
  private void flood(int mouseX, int mouseY, boolean type) {
    if (mouseX < 0 || mouseX >= tiles[0].length ||
        mouseY < 0 || mouseY >= tiles.length){
      return;
    }
    if(collision[mouseY][mouseX] == type){
      return;
    }

    collision[mouseY][mouseX] = type;
    flood(mouseX-1, mouseY,   type);
    flood(mouseX+1, mouseY,   type);
    flood(mouseX,   mouseY+1, type);
    flood(mouseX,   mouseY-1, type); 
  }


  /**
   * 
   * @param s
   * @return
   */
  public NPC findNPC(String s)
  {
    for (NPC[] npcR: npcs) {
      for (NPC npc: npcR) {
        if (npc!=null)
          if (npc.getName().equals(s)) return npc;
      }
    }

    return null;
  }

  public AreaScript getScript(String s)
  {
    for (AreaScript[] scriptR: scripts)
    {
      for (AreaScript scr: scriptR)
      {
        if (scr!=null)
        {
          System.out.println(scr.getName());
          if (scr.getName().equals(s)) return scr;
        }

      }
    }

    return null;
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

  /**
   * @return Whether the tile is on the map
   */
  private boolean checkTileInBounds(float x, float y) {
    return x+32 > 0 && x < WIDTH && y+32 > 0 && y < HEIGHT;
  }

  /**
   * Swtiches the state of the grid of every tile
   */
  private static void changeGrid(){
    grid = !grid;
  }  

  public TileList[][] getSelection(){
    return selection;
  }

  public void setSelection(TileList[][] sel){
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

