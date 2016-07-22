/**
 * Map.java    May 30, 2016, 11:32:19 AM
 */

package komorebi.clyde.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.lwjgl.opengl.Display;

import komorebi.clyde.engine.Draw;
import komorebi.clyde.engine.Key;
import komorebi.clyde.engine.KeyHandler;
import komorebi.clyde.engine.Main;
import komorebi.clyde.engine.Playable;
import komorebi.clyde.entities.Face;
import komorebi.clyde.entities.NPC;
import komorebi.clyde.entities.NPCType;
import komorebi.clyde.script.AreaScript;
import komorebi.clyde.script.TalkingScript;
import komorebi.clyde.script.WalkingScript;
import komorebi.clyde.script.WarpScript;

/**
 * Represents a map of tiles
 * 
 * @author Aaron Roy
 * @version 
 */
public class Map implements Playable{

  private TileList[][] tiles;                //The Map itself

  public static final int SIZE = 16;  //Width and height of a tile

  private NPC[][] npcs;
  private AreaScript[][] scripts;

  private float x, y;       //Current location
  private float dx, dy;
  
  private float clydeX, clydeY;
  private Face clydeDirection;

  private static final int WIDTH = Display.getWidth();
  private static final int HEIGHT = Display.getHeight();




  /**
   * Creates a new Map of the dimensions col x row
   * @param col number of columns (x)
   * @param row number of rows (y)
   */
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
    try {
      BufferedReader reader = new BufferedReader(new FileReader(
          new File(key)));
      int rows = Integer.parseInt(reader.readLine());
      int cols = Integer.parseInt(reader.readLine());

      tiles = new TileList[rows][cols];
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
        }
      }
      
      String s;

      while ((s=reader.readLine()) != null)
      {
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
      }


      reader.close();
    } catch (IOException | NumberFormatException e) {
      e.printStackTrace();
    }


  }
  
  public Map(String key, boolean b)
  {
    try {
      FileInputStream fileIn = new FileInputStream(key);
      ObjectInputStream in = new ObjectInputStream(fileIn);
      
      EditorMap edit = (EditorMap) in.readObject();
      tiles = edit.getTiles();
      
      in.close();
      fileIn.close();
      
      
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    
  }

  /* (non-Javadoc)
   * @see komorebi.clyde.engine.Playable#getInput()
   */
  @Override
  public void getInput() {
    
  }
  
  
  /* (non-Javadoc)
   * @see komorebi.clyde.engine.Renderable#update()
   */
  @Override
  public void update() {

    move(dx, dy);

    dx = 0;
    dy = 0;
    
    for (NPC[] npcR: npcs) {
      for (NPC npc: npcR) {
        if (npc != null) 
        {
          npc.update();
          
          if (!npc.isTalking() && !npc.isWalking())
          {
            npc.runWalkingScript();
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
          //npcs[i][j].update();

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
          if (scripts[i][j].isLocationIntersected(Main.getGame().getClyde()) && 
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
  
  public void setTile(TileList tile, int x, int y)
  {
    tiles[x][y] = tile;
  }
  
  public void setDirection(float dx, float dy)
  {
    this.dx = dx;
    this.dy = dy;
  }
}

