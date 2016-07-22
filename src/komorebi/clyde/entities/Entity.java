/**
 * Entity.java    May 15, 2016, 11:47:59 PM
 */
package komorebi.clyde.entities;

import komorebi.clyde.engine.Renderable;

/**
 * Represents something that moves
 * 
 * @author Aaron Roy
 */
public abstract class Entity implements Renderable{
  protected float x;
  protected float y;
  protected int sx;
  protected int sy;

  protected Entities ent;

  /**
   * Represents the different varieties of entities that exist within the game
   * 
   * @author Aaron Roy
   * @version
   */
  public enum Entities{
    CLYDE, FILLER, NPC;
  }

  /**
   * Creates a new entity
   * 
   * @param x The x position
   * @param y The y position
   * @param sx The width
   * @param sy The height
   */
  public Entity(float x,float y,int sx,int sy){
    this.x = x;
    this.y = y;
    this.sx = sx;
    this.sy = sy;
  }

  public float getX()
  {
    return x;
  }

  public float getY()
  {
    return y;
  }

}
