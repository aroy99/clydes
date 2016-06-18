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
     * Currently has a lot of test tiles (all tests start with x)
     */
    public void render(){
        int texx, texy;
        switch(type){
        case BLANK     :texx= 0;texy=0  ;break;
        case GRASS     :texx=16;texy=0  ;break;
        case FLOWER    :texx=32;texy=0  ;break;
        case G_DECOR1  :texx=48;texy=0  ;break;
        case G_DECOR2  :texx= 0;texy=16 ;break;
        case HEDGE_L   :texx=16;texy=16 ;break;
        case HEDGE_C   :texx=32;texy=16 ;break;
        case HEDGE_R   :texx=48;texy=16 ;break;
        case DOOR1     :texx= 0;texy=32 ;break;
        case DOOR2     :texx=16;texy=32 ;break;
        case DOOR3_L   :texx=32;texy=32 ;break;
        case DOOR3_R   :texx=48;texy=32 ;break;
        case DOOR4     :texx= 0;texy=48 ;break;
        case DOOR5     :texx=16;texy=48 ;break;
        case ROOF_UL   :texx=32;texy=48 ;break;
        case ROOF_U    :texx=48;texy=48 ;break;
        case ROOF_UR   :texx= 0;texy=64 ;break;
        case ROOF_L    :texx=16;texy=64 ;break;
        case ROOF_R    :texx=32;texy=64 ;break;
        case ROOF_C    :texx=48;texy=64 ;break;
        case ROOF_BL   :texx= 0;texy=80 ;break;
        case ROOF_B    :texx=16;texy=80 ;break;
        case ROOF_BR   :texx=32;texy=80 ;break;
        case WIN1      :texx=48;texy=80 ;break;
        case WIN2      :texx= 0;texy=96 ;break;
        case JAIL_WIN  :texx=16;texy=96 ;break;
        case REST_WIN  :texx=32;texy=96 ;break;
        case CHURCH_WIN:texx=48;texy=96 ;break;
        case D_ARC1    :texx= 0;texy=112;break;
        case D_ARC2    :texx=16;texy=112;break;
        case D_ARC3_L  :texx=32;texy=112;break;
        case D_ARC3_R  :texx=48;texy=112;break;
        case D_ARC4    :texx= 0;texy=128;break;
        case D_ARC5    :texx=16;texy=128;break;
        case xBLANK    :texx=32;texy=128;break;
        case xGRASS    :texx=48;texy=128;break;
        case xFLOWER   :texx= 0;texy=144;break;
        case xG_DECOR1 :texx=16;texy=144;break;
        case xG_DECOR2 :texx=32;texy=144;break;
        case xHEDGE_L  :texx=48;texy=144;break;
        case xHEDGE_C  :texx= 0;texy=160;break;
        case xHEDGE_R  :texx=16;texy=160;break;
        case xDOOR1    :texx=32;texy=160;break;
        case xDOOR2    :texx=48;texy=160;break;
        case xDOOR3_L  :texx= 0;texy=176;break;
        case xDOOR3_R  :texx=16;texy=176;break;
        case xDOOR4    :texx=32;texy=176;break;
        case xDOOR5    :texx=48;texy=176;break;
        case xROOF_UL  :texx= 0;texy=192;break;
        case xROOF_U   :texx=16;texy=192;break;
        case xROOF_UR  :texx=32;texy=192;break;
        case xROOF_L   :texx=48;texy=192;break;
        case xROOF_R   :texx= 0;texy=208;break;
        case xROOF_C   :texx=16;texy=208;break;
        case xROOF_BL  :texx=32;texy=208;break;
        case xROOF_B   :texx=48;texy=208;break;
        case xROOF_BR  :texx= 0;texy=224;break;
        case xWIN1     :texx=16;texy=224;break;
        case xWIN2     :texx=32;texy=224;break;
        case xJAIL_WIN :texx=48;texy=224;break;
        case xREST_WIN :texx= 0;texy=240;break;
        case xCHURCH_WI:texx=16;texy=240;break;
        case xD_ARC1   :texx=32;texy=240;break;
        case xD_ARC2   :texx=48;texy=240;break;
        default        :texx=32;texy= 32;break; 
        }                    
        
        Draw.rect(x,y, SIZE, SIZE, texx, texy, 1);
        if(grid)Draw.rect(x, y, SIZE, SIZE, 0, 16, 2);
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
