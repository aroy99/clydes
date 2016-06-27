/**
 * Clyde.java		May 15, 2016, 11:58:06 PM
 *
 * -
 */
package komorebi.clyde.entities;

import komorebi.clyde.engine.Animation;
import komorebi.clyde.engine.Playable;

import org.lwjgl.input.Keyboard;

/**
 * @author Aaron Roy
 * @version 0.0.2.0
 */
public class Clyde extends Entity implements Playable{

    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    private boolean run;
    private boolean canMove = true;
    
    private float dx;
    private float dy;
    
    private Animation upAni;
    private Animation downAni;
    private Animation leftAni;
    private Animation rightAni;
    
    private Face dir = Face.DOWN;    
    
    /**
     * @param x x pos, from left
     * @param y y pos from bottom
     * @param sx width
     * @param sy height
     */
    public Clyde(float x, float y) {
        super(x, y, 16, 24);
        ent = Entities.CLYDE;
        
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

    /* (non-Javadoc)
     * @see komorebi.clyde.engine.Playable#update()
     */
    public void getInput(){
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
    
    /* (non-Javadoc)
     * @see komorebi.clyde.engine.Renderable#update()
     */
    @Override
    public void update() {
        if (canMove)
        {
        	if(left){
        		dx = -1;
        		dir = Face.LEFT;
        		leftAni.resume();
        	}if(right){
        		dx = 1;
        		dir = Face.RIGHT;
        		rightAni.resume();
        	}if(!(left || right)){
        		dx = 0;
        		leftAni.hStop();
        		rightAni.hStop();
        	}
        
        	if(up){
        		dy = 1;
        		dir = Face.UP;
        		upAni.resume();
        	}if(down){
        		dy = -1;
        		dir = Face.DOWN;
        		downAni.resume();
        	}if(!(up || down)){
        		dy = 0;
        		downAni.hStop();
        		upAni.hStop();
        	}

        	if(run){
        		dx *=2;
        		dy *=2;
        		upAni.setSpeed(4);
        		downAni.setSpeed(4);
        		leftAni.setSpeed(4);
        		rightAni.setSpeed(4);
        	}else{
        		upAni.setSpeed(8);
        		downAni.setSpeed(8);
        		leftAni.setSpeed(8);
        		rightAni.setSpeed(8);
        	}
        
        	x += dx;
        	y += dy;
        } else
        {
        	upAni.hStop();
        	downAni.hStop();
        	leftAni.hStop();
        	rightAni.hStop();
        }
    }

    /* (non-Javadoc)
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
    
    public void lock()
    {
    	canMove=false;
    }
    
    public void unlock()
    {
    	canMove=true;
    }

    public int getTileX()
    {
    	return ((int) x)/16;
    }
    
    public int getTileY()
    {
    	return ((int) y)/16;
    }
}
