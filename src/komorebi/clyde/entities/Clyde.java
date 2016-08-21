/**
 * Clyde.java       May 15, 2016, 11:58:06 PM
 */
package komorebi.clyde.entities;

import java.awt.Rectangle;

import org.lwjgl.input.Keyboard;

import komorebi.clyde.engine.Animation;
import komorebi.clyde.engine.Playable;
import komorebi.clyde.script.Execution;
import komorebi.clyde.script.Lock;
import komorebi.clyde.states.Game;

/**
 * @author Aaron Roy
 * @author Andrew Faulkenberry
 */
public class Clyde extends Entity implements Playable{

  private boolean up;
  private boolean down;
  private boolean left;
  private boolean right;
  private boolean run;
  private boolean pause;
  
  private boolean canMove = true;

  private float dx;
  private float dy;
  
  private int framesToGo;
  private boolean hasInstructions;

  private Animation upAni;
  private Animation downAni;
  private Animation leftAni;
  private Animation rightAni;

  private Face dir = Face.DOWN;    
  private Execution ex;
  
  private Lock lock;


  /**
   * @param x x pos, from left
   * @param y y pos from bottom
   */
  public Clyde(float x, float y) {
    super(x, y, 16, 24);
    ent = Entities.CLYDE;
    
    area = new Rectangle((int) x, (int) y, 16, 24);
    rArea = new Rectangle((int) rx, (int) ry, 16, 24);

    upAni =    new Animation(4, 8, 16, 24, 0);
    downAni =  new Animation(4, 8, 16, 24, 0);
    leftAni =  new Animation(4, 8, 16, 24, 0);
    rightAni = new Animation(4, 8, 16, 24, 0);

    downAni.add(16,24);
    downAni.add( 0,24);
    downAni.add(16,24);
    downAni.add(32,24);

    upAni.add(16, 0);
    upAni.add( 0, 0);
    upAni.add(16, 0);
    upAni.add(32, 0);

    leftAni.add(48, 0);
    leftAni.add(48,24);
    leftAni.add(48, 0);
    leftAni.add(0, 48, 3);

    rightAni.add(48, 0, true);
    rightAni.add(48,24, true);
    rightAni.add(48, 0, true);
    rightAni.add(0, 48, 3, true);

  }

  /**
   * @see komorebi.clyde.engine.Playable#update()
   */
  public void getInput(){

    if (canMove)
    {
      up =    Keyboard.isKeyDown(Keyboard.KEY_UP) && 
          !Keyboard.isKeyDown(Keyboard.KEY_DOWN);
      down =  Keyboard.isKeyDown(Keyboard.KEY_DOWN) && 
          !Keyboard.isKeyDown(Keyboard.KEY_UP);
      left =  Keyboard.isKeyDown(Keyboard.KEY_LEFT) && 
          !Keyboard.isKeyDown(Keyboard.KEY_RIGHT);
      right = Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && 
          !Keyboard.isKeyDown(Keyboard.KEY_LEFT);

      run = Keyboard.isKeyDown(Keyboard.KEY_X);
    }

  }

  /**
   * @see komorebi.clyde.engine.Renderable#update()
   */
  @Override
  public void update() {
    
    int speed = 8;

    if(left){
      dx = -1;
      dir = Face.LEFT;
      leftAni.resume();
    } 
    if(right){
      dx = 1;
      dir = Face.RIGHT;
      rightAni.resume();
    } 

    if(!(left || right)){
      dx = 0;
      leftAni.hStop();
      rightAni.hStop();
    }

    if(down){
      dy = -1;
      dir = Face.DOWN;
      downAni.resume();
    }
    if(up){
      dy = 1;
      dir = Face.UP;
      upAni.resume();
    }

    if(!(up || down)){
      dy = 0;
      downAni.hStop();
      upAni.hStop();
    }

    if(run){
      dx *=2;
      dy *=2;
      speed /=2;
    }


    /*
      if((up && (left || right)) || (down && (left || right))){
        dx *= Math.sqrt(2)/2;
        dy *= Math.sqrt(2)/2;
        speed = (int)Math.round(speed / (Math.sqrt(2)/2));
      }
     */

    upAni.setSpeed(speed);
    downAni.setSpeed(speed);
    leftAni.setSpeed(speed);
    rightAni.setSpeed(speed);

    Game.getMap().move(-dx, -dy);
    
    rx += dx;
    ry += dy;
    
    rArea.x+=dx;
    rArea.y+=dy;

    if (hasInstructions)
    {
      if (dx!=0) 
      {
        framesToGo-=Math.abs(dx);
      } else if (dy != 0)
      {
        framesToGo-=Math.abs(dy);
      } else if (pause)
      {
        framesToGo--;
      }
    }

    if (hasInstructions&&framesToGo<=0)
    {
      hasInstructions=false;
      left = false;
      right = false;
      down = false;
      up = false;
      lock.resumeThread();
    }
  }

  /**
   * @see komorebi.clyde.engine.Renderable#render()
   */
  @Override
  public void render() {
    switch (dir) {
      case DOWN:
        downAni.play(x,y);
        break;
      case UP:
        upAni.play(x,y);
        break;
      case LEFT:
        leftAni.play(x,y);
        break;
      case RIGHT:
        rightAni.play(x,y);
        break;
      default:
        break;
    }
  }
  
  public void pause(int frames, Lock lock)
  {
    framesToGo = frames;
    
    pause = true;
    hasInstructions = true;
    
    this.lock = lock;
    this.lock.pauseThread();
  }

  public void walk(Face dir, int tiles)
  {

    hasInstructions=true;
    framesToGo = tiles*16;
    //isMoving=true;
    //isRunning=false;
    this.dir = dir;

    switch (dir)
    {
      case DOWN:
        down = true;
        break;
      case LEFT:
        left = true;
        break;
      case RIGHT:
        right = true;
        break;
      case UP:
        up = true;
        break;
      default:
        break;
    }

  }
  
  public void walk(Face dir, int tiles, Execution ex)
  {
    walk(dir, tiles);
    this.ex = ex;
    this.ex.getLock().pauseThread();    
  }
  
  public void walk(Face dir, int tiles, Lock lock)
  {
    this.lock = lock;
    walk(dir,tiles);
    this.lock.pauseThread();
  }
  
  public void align(Face dir, Lock lock)
  {
    this.lock = lock;
    hasInstructions=true;
    
    this.dir = dir;
    
    switch (dir)
    {
      case DOWN:
        framesToGo = (int) this.ry - 16*getTileY();
        down = true;
        break;
      case LEFT:
        framesToGo = (int) this.rx - 16*getTileX();
        left = true;
        break;
      case RIGHT:
        framesToGo = (int) (16*getTileX() + 16 - this.rx);
        right = true;
        break;
      case UP:
        framesToGo = (int) (16*getTileY() + 16 - this.ry);
        up = true;
        break;
      default:
        break;
    }
    
    this.lock.pauseThread();
  }
  
  public void align(NPC npc, Lock lock)
  {
    Rectangle r = npc.intersectedHitbox(area);
    goToPixX(r.x, lock);
    goToPixY(r.y, lock);
    dir = npc.faceMe(area);
  }
  
  public void goToPixX(int goTo, Lock lock)
  {
    int distance = goTo - (int) x;
    
    framesToGo = Math.abs(distance);
    hasInstructions = true;
    
    if (distance<0)
    {
      left = true;
      dir = Face.LEFT;
    } else if (distance>0)
    {
      right = true;
      dir = Face.RIGHT;
    }
    
    this.lock = lock;
    lock.pauseThread();
  }
  
  public void goToPixY(int goTo, Lock lock)
  {
    
    int distance = goTo - (int) y;
    framesToGo = Math.abs(distance);
    hasInstructions = true;
    
    if (distance<0)
    {
      down = true;
      dir = Face.DOWN;
    } else if (distance>0)
    {
      up = true;
      dir = Face.UP;
    }
    
    this.lock = lock;
    lock.pauseThread();
  }
  
  public void turn(Face dir)
  {
    this.dir = dir;
  }

  public void lock(){
    canMove=false;
  }

  public void unlock(){
    canMove=true;
  }

  public int getTileX(){
    return  (int)rx/16;
  }

  public int getTileY(){
    return  (int)ry/16;
  }

  public Face getDirection(){
    return dir;
  }
  
  public void goTo(boolean horizontal, int tx, Lock lock)
  {
    if (horizontal)
    {
      if (rx>tx*16)
      {
        align(Face.LEFT, lock);
        walk(Face.LEFT, getTileX()-tx);
      } else if (rx<tx*16)
      {
        align(Face.RIGHT, lock);
        walk(Face.RIGHT, tx-getTileX(), lock);
      }
    } else
    {
      if (ry>tx*16)
      {
        align(Face.DOWN, lock);
        walk(Face.DOWN, getTileY()-tx, lock);
      } else if (ry<tx*16)
      {
        align(Face.UP, lock);
        walk(Face.UP, tx-getTileY(), lock);
      }
    }
    
  }
  
  public void stop()
  {
    dx=0;
    dy=0;
  }
  
  public Rectangle getRelativeArea()
  {
    return rArea;
  }
  
  public Rectangle getAbsoluteArea()
  {
    return area;
  }
  


}