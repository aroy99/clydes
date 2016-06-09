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
 * @version 
 */
public class Tile implements Renderable{
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
    
    public void render(){
        int texx, texy;
        switch(type){
        case BLANK     :texx=  0;texy=  0;break;
        case GRASS     :texx= 16;texy=  0;break;
        case FLOWER    :texx= 32;texy=  0;break;
        case G_DECOR1  :texx= 48;texy=  0;break;
        case G_DECOR2  :texx= 64;texy=  0;break;
        case HEDGE_L   :texx= 80;texy=  0;break;
        case HEDGE_C   :texx= 96;texy=  0;break;
        case HEDGE_R   :texx=112;texy=  0;break;
        case DOOR1     :texx=128;texy=  0;break;
        case DOOR2     :texx=144;texy=  0;break;
        case DOOR3_L   :texx=160;texy=  0;break;
        case DOOR3_R   :texx=176;texy=  0;break;
        case DOOR4     :texx=192;texy=  0;break;
        case DOOR5     :texx=208;texy=  0;break;
        case ROOF_UL   :texx=224;texy=  0;break;
        case ROOF_U    :texx=240;texy=  0;break;
        case ROOF_UR   :texx=  0;texy= 16;break;
        case ROOF_L    :texx= 16;texy= 16;break;
        case ROOF_R    :texx= 32;texy= 16;break;
        case ROOF_C    :texx= 48;texy= 16;break;
        case ROOF_BL   :texx= 64;texy= 16;break;
        case ROOF_B    :texx= 80;texy= 16;break;
        case ROOF_BR   :texx= 96;texy= 16;break;
        case WIN1      :texx=112;texy= 16;break;
        case WIN2      :texx=128;texy= 16;break;
        case JAIL_WIN  :texx=144;texy= 16;break;
        case REST_WIN  :texx=160;texy= 16;break;
        case CHURCH_WIN:texx=176;texy= 16;break;
        case D_ARC1    :texx=192;texy= 16;break;
        case D_ARC2    :texx=208;texy= 16;break;
        case D_ARC3_L  :texx=224;texy= 16;break;
        case D_ARC3_R  :texx=240;texy= 16;break;
        case D_ARC4    :texx=  0;texy= 32;break;
        case D_ARC5    :texx= 16;texy= 32;break;
        default        :texx= 32;texy= 32;break;
        }
        
        Draw.rect(x,y, SIZE, SIZE, texx, texy, texx+SIZE, texy+SIZE, 1);
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
}
