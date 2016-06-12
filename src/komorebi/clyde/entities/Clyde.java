/**
 * Clyde.java		May 15, 2016, 11:58:06 PM
 *
 * -
 */
package komorebi.clyde.entities;

import komorebi.clyde.engine.Animation;
import komorebi.clyde.engine.Playable;
import komorebi.clyde.states.Game;

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
        int speed = 8;
        
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
            speed = 4;
        }else{
            speed = 8;
        }
        
       
        if((up && (left || right)) || (down && (left || right))){
            dx *= Math.sqrt(2)/2;
            dy *= Math.sqrt(2)/2;
            speed = (int) Math.round(speed / (Math.sqrt(2)/2));
        }
       
        
        upAni.setSpeed(speed);
        downAni.setSpeed(speed);
        leftAni.setSpeed(speed);
        rightAni.setSpeed(speed);
        
        Game.getMap().move(-dx, -dy);
        
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

}
