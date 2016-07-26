/**
 * Map.java    May 30, 2016, 11:32:19 AM
 */

package komorebi.clyde.map;

import komorebi.clyde.engine.Draw;
import komorebi.clyde.engine.Key;
import komorebi.clyde.engine.KeyHandler;
import komorebi.clyde.engine.Main;
import komorebi.clyde.engine.Playable;
import komorebi.clyde.entities.Clyde;
import komorebi.clyde.entities.Face;
import komorebi.clyde.entities.NPC;
import komorebi.clyde.entities.NPCType;
import komorebi.clyde.script.AreaScript;
import komorebi.clyde.script.TalkingScript;
import komorebi.clyde.script.WalkingScript;
import komorebi.clyde.script.WarpScript;
import komorebi.clyde.states.Game;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Represents a map of tiles
 * 
 * @author Aaron Roy
 * @version 
 */
public class Map implements Playable{

  private TileList[][] tiles;                //The Map itself
  private boolean[][] collision;

  public static final int SIZE = 16;  //Width and height of a tile

  private NPC[][] npcs;
  private AreaScript[][] scripts;
  private static Clyde play;

  private float x, y;       //Current location

  private float clydeX, clydeY;
  private Face clydeDirection;

  private static final int WIDTH = Display.getWidth();
  private static final int HEIGHT = Display.getHeight();
  
  //Debug
  private boolean isHitBox;


  /**
   * Creates a new Map of the dimensions col x row <br>
   * Really shouldn't be used anymore
   * @param col number of columns (x)
   * @param row number of rows (y)
   */
  @Deprecated
  public Map(int col, int row){

    tiles = new TileList[row][col];
    npcs = new NPC[row][col];
    scripts = new AreaScript[row][col];

    for (int i = tiles.length-1; i >= 0; i--) {
      for (int j = 0; j < tiles[0].length; j++) {
        tiles[i][j] = TileList.BLANK;
      }
    }

  }


  /**
   * Creates a map from a map file, used for the game
   * 
   * @param key The location of the map
   */
  public Map(String key){
    play = new Clyde(120,100);
    
    try {
      BufferedReader reader = new BufferedReader(new FileReader(
          new File(key)));
      int rows = Integer.parseInt(reader.readLine());
      int cols = Integer.parseInt(reader.readLine());

      tiles = new TileList[rows][cols];
      collision = new boolean[rows][cols];
      npcs = new NPC[rows][cols];
      scripts = new AreaScript[rows][cols];

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
          collision[i][j] = true;
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
          collision[i][j]=str[index].equals("0")?true:false;
        }
      }
      
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
    }


  }

  /* (non-Javadoc)
   * @see komorebi.clyde.engine.Playable#getInput()
   */
  @Override
  public void getInput() {
    play.getInput();
    
    // TODO Debug
    if(KeyHandler.keyClick(Key.H)){
      isHitBox = !isHitBox;
    }
  }
  
  
  /* (non-Javadoc)
   * @see komorebi.clyde.engine.Renderable#update()
   */
  @Override
  public void update() {
    
    play.update();
    setClydeLocation(play.getX(), play.getY(), play.getDirection());
    
    for (NPC[] npcR: npcs) {
      for (NPC npc: npcR) {
        if (npc != null) 
        {
          npc.update();

          if (npc.isTalking() && KeyHandler.keyDown(Key.CTRL) && KeyHandler.keyClick(Key.A))
          {
            npc.abortTalkingScript();
          }

          if (!npc.isTalking() && !npc.getWalkingScript().isRunning())
          {
            npc.getWalkingScript().run();
          }



        }
      }
    }

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
    
    
    //TODO Debug
    if (isHitBox) {
      for (int i = 0; i < collision.length; i++) {
        for (int j = 0; j < collision[0].length; j++) {
          if(checkTileInBounds(x+j*SIZE, y+i*SIZE) && !collision[i][j]){
            Draw.rect(x+j*SIZE, y+i*SIZE, SIZE, SIZE, 16, 16, 16, 16, 2);
          }        
        }
      }
    }
    
    play.render();
    
    //TODO Debug
    if(isHitBox){
      Draw.rect(clydeX, clydeY, 16, 16, 18, 16, 18, 16, 2);
    }
  }


  /**
   * Moves the entire map and all entities contained by it by the specified amount
   * 
   * @param dx pixels to move left/right
   * @param dy pixels to move up/down
   */
  public void move(float cx, float cy, float dx, float dy) {

    boolean[] col = checkCollisions(cx,cy,dx,dy);

    if(!col[0] || !col[2]){
      dy=0;
      dx*=2/3f;
    }
    if(!col[1] || !col[3]){
      dx=0;
      dy*=2/3f;
    }
    
    x+=dx;
    y+=dy;
    for (int i = 0; i < tiles.length; i++) {
      for (int j = 0; j < tiles[0].length; j++) {
        if (npcs[i][j] != null) 
        {
          npcs[i][j].setPixLocation((int) x+j*16+npcs[i][j].getXTravelled(), 
              (int) y+i*16+npcs[i][j].getYTravelled());
          npcs[i][j].update();

          if (npcs[i][j].isApproached(clydeX, clydeY, clydeDirection) && 
              KeyHandler.keyClick(Key.SPACE))
          {
            npcs[i][j].turn(clydeDirection.opposite());
            npcs[i][j].approach();
          } 


        }

        if (scripts[i][j] != null)
        {
          scripts[i][j].setAbsoluteLocation(x+j*16,y+i*16);
          if (scripts[i][j].isLocationIntersected(play) && 
              !scripts[i][j].hasRun()) {

            if (scripts[i][j] instanceof WarpScript)
            {
              WarpScript scr = (WarpScript) scripts[i][j];
              Main.getGame().warp(scr.getMap());
            } else {
              scripts[i][j].run();
            }

          }

        }

      }
    }

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
   * @return Whether the tile is on the map
   */
  private boolean checkTileInBounds(float x, float y) {
    return x+32 > 0 && x < WIDTH && y+32 > 0 && y < HEIGHT;
  }
  
  /**
   * Checks the collisions between all four points of the character
   * 
   * @param x Current x of the map
   * @param y Current y of the map
   * @param dx Delta x of the map
   * @param dy Delta y of the map
   * @return {Never, Eat, Slimy, Worms}
   */
  private boolean[] checkCollisions(float x, float y, float dx, float dy){
    //Changed
    int x1 = (int)((-this.x-16+x-dx)/16)+1; //Left
    int y1 = (int)((-this.y-16+y-dy)/16)+1; //Bottom
    
    int x2 = (int)((-this.x-1+x-dx)/16)+1;  //Right
    int y2 = (int)((-this.y-1+y-dy)/16)+1;  //Top
    
    //Unchanged
    int x3 = (int)((-this.x-16+x)/16)+1; //Left
    int y3 = (int)((-this.y-16+y)/16)+1; //Bottom
    
    int x4 = (int)((-this.x-1+x)/16)+1;  //Right
    int y4 = (int)((-this.y-1+y)/16)+1;  //Top

 
    boolean[] ret = new boolean[4];
    
    ret[1] = x2<collision[0].length;
    ret[3] = x1-1>=0;
    ret[0] = y2<collision.length;
    ret[2] = y1-1>=0;
    
    for(boolean r:ret){
      if(!r){
        return ret;
      }
    }
    
    ret[0] = collision[y2][x3] && collision[y2][x4];
    ret[2] = collision[y1][x3] && collision[y1][x4];
                                   
    ret[1] = collision[y3][x2] && collision[y4][x2];
    ret[3] = collision[y3][x1] && collision[y4][x1];
    
    if(KeyHandler.keyClick(Key.P)){
      System.out.println(x1 + ", " + x2 + ", " + y1 + ", " + y2);
      System.out.println("dx: " + dx + ", dy: " + dy + "\n" + 
          collision[y2][x1]+ ", " +collision[y2][x2]+ ", \n" +
          collision[y1][x1]+ ", " +collision[y1][x2]);
      System.out.println("Never: " + ret[0] + ", Eat: " + ret[1] + 
          ", Slimy: " + ret[2] + ", Worms: " + ret[3]);
    }
        
    return ret;
  }

  public int getWidth(){
    return tiles[0].length;
  }

  public int getHeight(){
    return tiles.length;
  }

  public void setClydeLocation(float x, float y, Face direction)
  {
    this.clydeX = x;
    this.clydeY = y;
    this.clydeDirection = direction;
  }


  public float getX() {
    return x;
  }

  public float getY() {
    return y;
  }
  
  public static Clyde getClyde()
  {
    return play;
  }

}

