/**
 * Tile.java		May 18, 2016, 8:42:07 PM
 *
 * -
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
    int texx, texy;
    switch(type){
      case BLANK     :texx=  0;texy=  0;break;
      case GRASS     :texx= 16;texy=  0;break;
      case FLOWER    :texx= 32;texy=  0;break;
      case G_DECOR1  :texx= 48;texy=  0;break;
      case G_DECOR2  :texx=  0;texy= 16;break;
      case HEDGE_L   :texx= 16;texy= 16;break;
      case HEDGE_C   :texx= 32;texy= 16;break;
      case HEDGE_R   :texx= 48;texy= 16;break;
      case DOOR1     :texx=  0;texy= 32;break;
      case DOOR2     :texx= 16;texy= 32;break;
      case DOOR3_L   :texx= 32;texy= 32;break;
      case DOOR3_R   :texx= 48;texy= 32;break;
      case DOOR4     :texx=  0;texy= 48;break;
      case DOOR5     :texx= 16;texy= 48;break;
      case ROOF_UL   :texx= 32;texy= 48;break;
      case ROOF_U    :texx= 48;texy= 48;break;
      case ROOF_UR   :texx=  0;texy= 64;break;
      case ROOF_L    :texx= 16;texy= 64;break;
      case ROOF_R    :texx= 32;texy= 64;break;
      case ROOF_C    :texx= 48;texy= 64;break;
      case ROOF_BL   :texx=  0;texy= 80;break;
      case ROOF_B    :texx= 16;texy= 80;break;
      case ROOF_BR   :texx= 32;texy= 80;break;
      case WIN1      :texx= 48;texy= 80;break;
      case WIN2      :texx=  0;texy= 96;break;
      case JAIL_WIN  :texx= 16;texy= 96;break;
      case REST_WIN  :texx= 32;texy= 96;break;
      case CHURCH_WIN:texx= 48;texy= 96;break;
      case D_ARC1    :texx=  0;texy=112;break;
      case D_ARC2    :texx= 16;texy=112;break;
      case D_ARC3_L  :texx= 32;texy=112;break;
      case D_ARC3_R  :texx= 48;texy=112;break;
      case D_ARC4    :texx=  0;texy=128;break;
      case D_ARC5    :texx= 16;texy=128;break;
      case xBLANK    :texx= 32;texy=128;break;
      case xGRASS    :texx= 48;texy=128;break;
      case xFLOWER   :texx=  0;texy=144;break;
      case xG_DECOR1 :texx= 16;texy=144;break;
      case xG_DECOR2 :texx= 32;texy=144;break;
      case xHEDGE_L  :texx= 48;texy=144;break;
      case xHEDGE_C  :texx=  0;texy=160;break;
      case xHEDGE_R  :texx= 16;texy=160;break;
      case xDOOR1    :texx= 32;texy=160;break;
      case xDOOR2    :texx= 48;texy=160;break;
      case xDOOR3_L  :texx=  0;texy=176;break;
      case xDOOR3_R  :texx= 16;texy=176;break;
      case xDOOR4    :texx= 32;texy=176;break;
      case xDOOR5    :texx= 48;texy=176;break;
      case xROOF_UL  :texx=  0;texy=192;break;
      case xROOF_U   :texx= 16;texy=192;break;
      case xROOF_UR  :texx= 32;texy=192;break;
      case xROOF_L   :texx= 48;texy=192;break;
      case xROOF_R   :texx=  0;texy=208;break;
      case xROOF_C   :texx= 16;texy=208;break;
      case xROOF_BL  :texx= 32;texy=208;break;
      case xROOF_B   :texx= 48;texy=208;break;
      case xROOF_BR  :texx=  0;texy=224;break;
      case xWIN1     :texx= 16;texy=224;break;
      case xWIN2     :texx= 32;texy=224;break;
      case xJAIL_WIN :texx= 48;texy=224;break;
      case xREST_WIN :texx=  0;texy=240;break;
      case xCHURCH_WI:texx= 16;texy=240;break;
      case xD_ARC1   :texx= 32;texy=240;break;
      case xD_ARC2   :texx= 48;texy=240;break;

      case yBLANK    :texx= 64;texy=0  ;break;
      case yGRASS    :texx= 80;texy=0  ;break;
      case yFLOWER   :texx= 96;texy=0  ;break;
      case yG_DECOR1 :texx=112;texy=0  ;break;
      case yG_DECOR2 :texx= 64;texy=16 ;break;
      case yHEDGE_L  :texx= 80;texy=16 ;break;
      case yHEDGE_C  :texx= 96;texy=16 ;break;
      case yHEDGE_R  :texx=112;texy=16 ;break;
      case yDOOR1    :texx= 64;texy=32 ;break;
      case yDOOR2    :texx= 80;texy=32 ;break;
      case yDOOR3_L  :texx= 96;texy=32 ;break;
      case yDOOR3_R  :texx=112;texy=32 ;break;
      case yDOOR4    :texx= 64;texy=48 ;break;
      case yDOOR5    :texx= 80;texy=48 ;break;
      case yROOF_UL  :texx= 96;texy=48 ;break;
      case yROOF_U   :texx=112;texy=48 ;break;
      case yROOF_UR  :texx= 64;texy=64 ;break;
      case yROOF_L   :texx= 80;texy=64 ;break;
      case yROOF_R   :texx= 96;texy=64 ;break;
      case yROOF_C   :texx=112;texy=64 ;break;
      case yROOF_BL  :texx= 64;texy=80 ;break;
      case yROOF_B   :texx= 80;texy=80 ;break;
      case yROOF_BR  :texx= 96;texy=80 ;break;
      case yWIN1     :texx=112;texy=80 ;break;
      case yWIN2     :texx= 64;texy=96 ;break;
      case yJAIL_WIN :texx= 80;texy=96 ;break;
      case yREST_WIN :texx= 96;texy=96 ;break;
      case yCHURCH_WI:texx=112;texy=96 ;break;
      case yD_ARC1   :texx= 64;texy=112;break;
      case yD_ARC2   :texx= 80;texy=112;break;
      case yD_ARC3_L :texx= 96;texy=112;break;
      case yD_ARC3_R :texx=112;texy=112;break;
      case yD_ARC4   :texx= 64;texy=128;break;
      case yD_ARC5   :texx= 80;texy=128;break;
      case zBLANK    :texx= 96;texy=128;break;
      case zGRASS    :texx=112;texy=128;break;
      case zFLOWER   :texx= 64;texy=144;break;
      case zG_DECOR1 :texx= 80;texy=144;break;
      case zG_DECOR2 :texx= 96;texy=144;break;
      case zHEDGE_L  :texx=112;texy=144;break;
      case zHEDGE_C  :texx= 64;texy=160;break;
      case zHEDGE_R  :texx= 80;texy=160;break;
      case zDOOR1    :texx= 96;texy=160;break;
      case zDOOR2    :texx=112;texy=160;break;
      case zDOOR3_L  :texx= 64;texy=176;break;
      case zDOOR3_R  :texx= 80;texy=176;break;
      case zDOOR4    :texx= 96;texy=176;break;
      case zDOOR5    :texx=112;texy=176;break;
      case zROOF_UL  :texx= 64;texy=192;break;
      case zROOF_U   :texx= 80;texy=192;break;
      case zROOF_UR  :texx= 96;texy=192;break;
      case zROOF_L   :texx=112;texy=192;break;
      case zROOF_R   :texx= 64;texy=208;break;
      case zROOF_C   :texx= 80;texy=208;break;
      case zROOF_BL  :texx= 96;texy=208;break;
      case zROOF_B   :texx=112;texy=208;break;
      case zROOF_BR  :texx= 64;texy=224;break;
      case zWIN1     :texx= 80;texy=224;break;
      case zWIN2     :texx= 96;texy=224;break;
      case zJAIL_WIN :texx=112;texy=224;break;
      case zREST_WIN :texx= 64;texy=240;break;
      case zCHURCH_WI:texx= 80;texy=240;break;
      case zD_ARC1   :texx= 96;texy=240;break;
      case zD_ARC2   :texx=112;texy=240;break;

      case aBLANK    :texx=128;texy=0  ;break;
      case aGRASS    :texx=144;texy=0  ;break;
      case aFLOWER   :texx=160;texy=0  ;break;
      case aG_DECOR1 :texx=176;texy=0  ;break;
      case aG_DECOR2 :texx=128;texy=16 ;break;
      case aHEDGE_L  :texx=144;texy=16 ;break;
      case aHEDGE_C  :texx=160;texy=16 ;break;
      case aHEDGE_R  :texx=176;texy=16 ;break;
      case aDOOR1    :texx=128;texy=32 ;break;
      case aDOOR2    :texx=144;texy=32 ;break;
      case aDOOR3_L  :texx=160;texy=32 ;break;
      case aDOOR3_R  :texx=176;texy=32 ;break;
      case aDOOR4    :texx=128;texy=48 ;break;
      case aDOOR5    :texx=144;texy=48 ;break;
      case aROOF_UL  :texx=160;texy=48 ;break;
      case aROOF_U   :texx=176;texy=48 ;break;
      case aROOF_UR  :texx=128;texy=64 ;break;
      case aROOF_L   :texx=144;texy=64 ;break;
      case aROOF_R   :texx=160;texy=64 ;break;
      case aROOF_C   :texx=176;texy=64 ;break;
      case aROOF_BL  :texx=128;texy=80 ;break;
      case aROOF_B   :texx=144;texy=80 ;break;
      case aROOF_BR  :texx=160;texy=80 ;break;
      case aWIN1     :texx=176;texy=80 ;break;
      case aWIN2     :texx=128;texy=96 ;break;
      case aJAIL_WIN :texx=144;texy=96 ;break;
      case aREST_WIN :texx=160;texy=96 ;break;
      case aCHURCH_WI:texx=176;texy=96 ;break;
      case aD_ARC1   :texx=128;texy=112;break;
      case aD_ARC2   :texx=144;texy=112;break;
      case aD_ARC3_L :texx=160;texy=112;break;
      case aD_ARC3_R :texx=176;texy=112;break;
      case aD_ARC4   :texx=128;texy=128;break;
      case aD_ARC5   :texx=144;texy=128;break;
      case bBLANK    :texx=160;texy=128;break;
      case bGRASS    :texx=176;texy=128;break;
      case bFLOWER   :texx=128;texy=144;break;
      case bG_DECOR1 :texx=144;texy=144;break;
      case bG_DECOR2 :texx=160;texy=144;break;
      case bHEDGE_L  :texx=176;texy=144;break;
      case bHEDGE_C  :texx=128;texy=160;break;
      case bHEDGE_R  :texx=144;texy=160;break;
      case bDOOR1    :texx=160;texy=160;break;
      case bDOOR2    :texx=176;texy=160;break;
      case bDOOR3_L  :texx=128;texy=176;break;
      case bDOOR3_R  :texx=144;texy=176;break;
      case bDOOR4    :texx=160;texy=176;break;
      case bDOOR5    :texx=176;texy=176;break;
      case bROOF_UL  :texx=128;texy=192;break;
      case bROOF_U   :texx=144;texy=192;break;
      case bROOF_UR  :texx=160;texy=192;break;
      case bROOF_L   :texx=176;texy=192;break;
      case bROOF_R   :texx=128;texy=208;break;
      case bROOF_C   :texx=144;texy=208;break;
      case bROOF_BL  :texx=160;texy=208;break;
      case bROOF_B   :texx=176;texy=208;break;
      case bROOF_BR  :texx=128;texy=224;break;
      case bWIN1     :texx=144;texy=224;break;
      case bWIN2     :texx=160;texy=224;break;
      case bJAIL_WIN :texx=176;texy=224;break;
      case bREST_WIN :texx=128;texy=240;break;
      case bCHURCH_WI:texx=144;texy=240;break;
      case bD_ARC1   :texx=160;texy=240;break;
      case bD_ARC2   :texx=176;texy=240;break;

      case cBLANK    :texx=192;texy=0  ;break;
      case cGRASS    :texx=208;texy=0  ;break;
      case cFLOWER   :texx=224;texy=0  ;break;
      case cG_DECOR1 :texx=240;texy=0  ;break;
      case cG_DECOR2 :texx=192;texy=16 ;break;
      case cHEDGE_L  :texx=208;texy=16 ;break;
      case cHEDGE_C  :texx=224;texy=16 ;break;
      case cHEDGE_R  :texx=240;texy=16 ;break;
      case cDOOR1    :texx=192;texy=32 ;break;
      case cDOOR2    :texx=208;texy=32 ;break;
      case cDOOR3_L  :texx=224;texy=32 ;break;
      case cDOOR3_R  :texx=240;texy=32 ;break;
      case cDOOR4    :texx=192;texy=48 ;break;
      case cDOOR5    :texx=208;texy=48 ;break;
      case cROOF_UL  :texx=224;texy=48 ;break;
      case cROOF_U   :texx=240;texy=48 ;break;
      case cROOF_UR  :texx=192;texy=64 ;break;
      case cROOF_L   :texx=208;texy=64 ;break;
      case cROOF_R   :texx=224;texy=64 ;break;
      case cROOF_C   :texx=240;texy=64 ;break;
      case cROOF_BL  :texx=192;texy=80 ;break;
      case cROOF_B   :texx=208;texy=80 ;break;
      case cROOF_BR  :texx=224;texy=80 ;break;
      case cWIN1     :texx=240;texy=80 ;break;
      case cWIN2     :texx=192;texy=96 ;break;
      case cJAIL_WIN :texx=208;texy=96 ;break;
      case cREST_WIN :texx=224;texy=96 ;break;
      case cCHURCH_WI:texx=240;texy=96 ;break;
      case cD_ARC1   :texx=192;texy=112;break;
      case cD_ARC2   :texx=208;texy=112;break;
      case cD_ARC3_L :texx=224;texy=112;break;
      case cD_ARC3_R :texx=240;texy=112;break;
      case cD_ARC4   :texx=192;texy=128;break;
      case cD_ARC5   :texx=208;texy=128;break;
      case dBLANK    :texx=224;texy=128;break;
      case dGRASS    :texx=240;texy=128;break;
      case dFLOWER   :texx=192;texy=144;break;
      case dG_DECOR1 :texx=208;texy=144;break;
      case dG_DECOR2 :texx=224;texy=144;break;
      case dHEDGE_L  :texx=240;texy=144;break;
      case dHEDGE_C  :texx=192;texy=160;break;
      case dHEDGE_R  :texx=208;texy=160;break;
      case dDOOR1    :texx=224;texy=160;break;
      case dDOOR2    :texx=240;texy=160;break;
      case dDOOR3_L  :texx=192;texy=176;break;
      case dDOOR3_R  :texx=208;texy=176;break;
      case dDOOR4    :texx=224;texy=176;break;
      case dDOOR5    :texx=240;texy=176;break;
      case dROOF_UL  :texx=192;texy=192;break;
      case dROOF_U   :texx=208;texy=192;break;
      case dROOF_UR  :texx=224;texy=192;break;
      case dROOF_L   :texx=240;texy=192;break;
      case dROOF_R   :texx=192;texy=208;break;
      case dROOF_C   :texx=208;texy=208;break;
      case dROOF_BL  :texx=224;texy=208;break;
      case dROOF_B   :texx=240;texy=208;break;
      case dROOF_BR  :texx=192;texy=224;break;
      case dWIN1     :texx=208;texy=224;break;
      case dWIN2     :texx=224;texy=224;break;
      case dJAIL_WIN :texx=240;texy=224;break;
      case dREST_WIN :texx=192;texy=240;break;
      case dCHURCH_WI:texx=208;texy=240;break;
      case dD_ARC1   :texx=224;texy=240;break;
      case dD_ARC2   :texx=240;texy=240;break;
      default        :texx=  0;texy=  0;break;
    }                    

    Draw.rect(x,y, SIZE, SIZE, texx, texy, texx+SIZE, texy+SIZE, 1);
    if(grid)Draw.rect(x, y, SIZE, SIZE, 0, 16, SIZE, 16+SIZE, 2);
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
