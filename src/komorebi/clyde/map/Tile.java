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
     * @param tx x will become this*16
     * @param ty y will become this*16
     * @param t the type of this tile
     * @param fill to differentiate this from the other
     */
    public Tile(float x,float y, TileList t, boolean fill){
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

}
