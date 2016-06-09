/**
 * Entity.java		May 15, 2016, 11:47:59 PM
 *
 * -
 */
package komorebi.clyde.entities;

import komorebi.clyde.engine.Renderable;

/**
 * Represents something that moves
 * 
 * @author Aaron Roy
 * @version 
 */
public abstract class Entity implements Renderable{
    protected float x;
    protected float y;
    protected int sx;
    protected int sy;
    
    protected Entities ent;
    
    public enum Entities{
        CLYDE, FILLER;
    }
    
    public Entity(float x,float y,int sx,int sy){
        this.x = x;
        this.y = y;
        this.sx = sx;
        this.sy = sy;
    }
    
}
