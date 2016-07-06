/**
 * Tile.java    May 18, 2016, 8:42:07 PM
 */
package komorebi.clyde.map;

import komorebi.clyde.engine.Draw;
import komorebi.clyde.engine.Renderable;

/**
 * @author Aaron Roy
 */
public class Tile implements Renderable{

  private static boolean grid;        //Whether the grid is on or not

  private float x, y;                 //Location of the tile
  private float dx,dy;                //Amount tile will move each frame
  public static final int SIZE = 16;  //Width and height of a tile
  private TileList type;              //This tile's type

  /**
   * Creates a tile at the current coordinates
   * 
   * @param tx x will become this*16
   * @param ty y will become this*16
   * @param t the type of this tile
   */
  public Tile(int tx,int ty, TileList t){
    x = tx*SIZE;
    y = ty*SIZE;
    type = t;
  }

  /**
   * Creates a tile at the current coordinates
   * 
   * @param x x will become this
   * @param y y will become this
   * @param t the type of this tile
   * @param dummy Dummy variable to differentiate this from the other
   */
  public Tile(float x,float y, TileList t, boolean dummy){
    this.x = x;
    this.y = y;
    type = t;
  }


  /**
   * Currently has a lot of test tiles (all tests start with x)
   */
  public void render(){
    int texx = type.getX(), texy = type.getY();
    
    Draw.rect(x,y, SIZE, SIZE, texx, texy, texx+SIZE, texy+SIZE, 1);
    if(grid){
      Draw.rect(x, y, SIZE, SIZE, 0, 16, SIZE, 16+SIZE, 2);
    }
  }

  public TileList getType(){
    return type;
  }


  public void setType(TileList newT){
    type = newT;
  }

  public void move(float dx, float dy){
    this.dx = dx;
    this.dy = dy;
  }

  public float getX(){
    return x;
  }

  public float getY(){
    return y;
  }

  public String toString(){
    return type.toString();
  }

  /* (non-Javadoc)
   * @see komorebi.clyde.engine.Renderable#update()
   */
  @Override
  public void update() {
    x += dx;
    y += dy;
  }

  /**
   * Sets the location to the specified x and y
   * 
   * @param x the x, duh
   * @param y the y, duh
   */
  public void setLoc(float x, float y){
    this.x = x;
    this.y = y;
  }

  /**
   * Swtiches the state of the grid of every tile
   */
  public static void changeGrid(){
    grid = !grid;
  }
  
  /**
   * @return The X coordinate of this texture
   */
  private int getTexX(){
    return 0;
  }
  
  /**
   * @return The Y coordinate of this texture
   */
  private int getTexY(){
    
    switch (type) {
      case BLANK     :  case yBLANK    :  case aBLANK    :  case cBLANK    :
      case GRASS     :  case yGRASS    :  case aGRASS    :  case cGRASS    :
      case FLOWER    :  case yFLOWER   :  case aFLOWER   :  case cFLOWER   :
      case G_DECOR1  :  case yG_DECOR1 :  case aG_DECOR1 :  case cG_DECOR1 :
        return 0;                                                          
                                                                           
      case G_DECOR2  :  case yG_DECOR2 :  case aG_DECOR2 :  case cG_DECOR2 :
      case HEDGE_L   :  case yHEDGE_L  :  case aHEDGE_L  :  case cHEDGE_L  :
      case HEDGE_C   :  case yHEDGE_C  :  case aHEDGE_C  :  case cHEDGE_C  :
      case HEDGE_R   :  case yHEDGE_R  :  case aHEDGE_R  :  case cHEDGE_R  :
        return 16;
        
      case DOOR1     :  case yDOOR1    :  case aDOOR1    :  case cDOOR1    :
      case DOOR2     :  case yDOOR2    :  case aDOOR2    :  case cDOOR2    :
      case DOOR3_L   :  case yDOOR3_L  :  case aDOOR3_L  :  case cDOOR3_L  :
      case DOOR3_R   :  case yDOOR3_R  :  case aDOOR3_R  :  case cDOOR3_R  :
        return 32;  
        
      case DOOR4     :  case yDOOR4    :  case aDOOR4    :  case cDOOR4    :
      case DOOR5     :  case yDOOR5    :  case aDOOR5    :  case cDOOR5    :
      case ROOF_UL   :  case yROOF_UL  :  case aROOF_UL  :  case cROOF_UL  :
      case ROOF_U    :  case yROOF_U   :  case aROOF_U   :  case cROOF_U   :
        return 48;
        
      case ROOF_UR   :  case yROOF_UR  :  case aROOF_UR  :  case cROOF_UR  :
      case ROOF_L    :  case yROOF_L   :  case aROOF_L   :  case cROOF_L   :
      case ROOF_R    :  case yROOF_R   :  case aROOF_R   :  case cROOF_R   :
      case ROOF_C    :  case yROOF_C   :  case aROOF_C   :  case cROOF_C   :
        return 64;
        
      case ROOF_BL   :  case yROOF_BL  :  case aROOF_BL  :  case cROOF_BL  :
      case ROOF_B    :  case yROOF_B   :  case aROOF_B   :  case cROOF_B   :
      case ROOF_BR   :  case yROOF_BR  :  case aROOF_BR  :  case cROOF_BR  :
      case WIN1      :  case yWIN1     :  case aWIN1     :  case cWIN1     :
        return 80;
        
      case WIN2      :  case yWIN2     :  case aWIN2     :  case cWIN2     :
      case JAIL_WIN  :  case yJAIL_WIN :  case aJAIL_WIN :  case cJAIL_WIN :
      case REST_WIN  :  case yREST_WIN :  case aREST_WIN :  case cREST_WIN :
      case CHURCH_WIN:  case yCHURCH_WI:  case aCHURCH_WI:  case cCHURCH_WI:
        return 96;
        
      case D_ARC1    :  case yD_ARC1   :  case aD_ARC1   :  case cD_ARC1   :
      case D_ARC2    :  case yD_ARC2   :  case aD_ARC2   :  case cD_ARC2   :
      case D_ARC3_L  :  case yD_ARC3_L :  case aD_ARC3_L :  case cD_ARC3_L :
      case D_ARC3_R  :  case yD_ARC3_R :  case aD_ARC3_R :  case cD_ARC3_R :
        return 112;
        
      case D_ARC4    :  case yD_ARC4   :  case aD_ARC4   :  case cD_ARC4   :
      case D_ARC5    :  case yD_ARC5   :  case aD_ARC5   :  case cD_ARC5   :
      case xBLANK    :  case zBLANK    :  case bBLANK    :  case dBLANK    :
      case xGRASS    :  case zGRASS    :  case bGRASS    :  case dGRASS    :
        return 128;

      case xFLOWER   :  case zFLOWER   :  case bFLOWER   :  case dFLOWER   :
      case xG_DECOR1 :  case zG_DECOR1 :  case bG_DECOR1 :  case dG_DECOR1 :
      case xG_DECOR2 :  case zG_DECOR2 :  case bG_DECOR2 :  case dG_DECOR2 :
      case xHEDGE_L  :  case zHEDGE_L  :  case bHEDGE_L  :  case dHEDGE_L  :
        return 144;

      case xHEDGE_C  :  case zHEDGE_C  :  case bHEDGE_C  :  case dHEDGE_C  :
      case xHEDGE_R  :  case zHEDGE_R  :  case bHEDGE_R  :  case dHEDGE_R  :
      case xDOOR1    :  case zDOOR1    :  case bDOOR1    :  case dDOOR1    :
      case xDOOR2    :  case zDOOR2    :  case bDOOR2    :  case dDOOR2    :
        return 160;

      case xDOOR3_L  :  case zDOOR3_L  :  case bDOOR3_L  :  case dDOOR3_L  :
      case xDOOR3_R  :  case zDOOR3_R  :  case bDOOR3_R  :  case dDOOR3_R  :
      case xDOOR4    :  case zDOOR4    :  case bDOOR4    :  case dDOOR4    :
      case xDOOR5    :  case zDOOR5    :  case bDOOR5    :  case dDOOR5    :
        return 176;

      case xROOF_UL  :  case zROOF_UL  :  case bROOF_UL  :  case dROOF_UL  :
      case xROOF_U   :  case zROOF_U   :  case bROOF_U   :  case dROOF_U   :
      case xROOF_UR  :  case zROOF_UR  :  case bROOF_UR  :  case dROOF_UR  :
      case xROOF_L   :  case zROOF_L   :  case bROOF_L   :  case dROOF_L   :
        return 192;

      case xROOF_R   :  case zROOF_R   :  case bROOF_R   :  case dROOF_R   :
      case xROOF_C   :  case zROOF_C   :  case bROOF_C   :  case dROOF_C   :
      case xROOF_BL  :  case zROOF_BL  :  case bROOF_BL  :  case dROOF_BL  :
      case xROOF_B   :  case zROOF_B   :  case bROOF_B   :  case dROOF_B   :
        return 208;

      case xROOF_BR  :  case zROOF_BR  :  case bROOF_BR  :  case dROOF_BR  :
      case xWIN1     :  case zWIN1     :  case bWIN1     :  case dWIN1     :
      case xWIN2     :  case zWIN2     :  case bWIN2     :  case dWIN2     :
      case xJAIL_WIN :  case zJAIL_WIN :  case bJAIL_WIN :  case dJAIL_WIN :
        return 224;

      case xREST_WIN :  case zREST_WIN :  case bREST_WIN :  case dREST_WIN :
      case xCHURCH_WI:  case zCHURCH_WI:  case bCHURCH_WI:  case dCHURCH_WI:
      case xD_ARC1   :  case zD_ARC1   :  case bD_ARC1   :  case dD_ARC1   :
      case xD_ARC2   :  case zD_ARC2   :  case bD_ARC2   :  case dD_ARC2   :
        return 240;

      default:
        return 0;
    }

  }

}
