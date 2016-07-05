/**
 * NPC.java    Jun 9, 2016, 3:09:11 PM
 */
package komorebi.clyde.entities;

import komorebi.clyde.engine.Animation;
import komorebi.clyde.engine.Main;
import komorebi.clyde.script.Execution;
import komorebi.clyde.script.TextHandler;

import java.util.ArrayList;

/**
 * 
 * @author Andrew Faulkenberry
 */
public class NPC extends Entity {

  private static ArrayList<NPC> npcs = new ArrayList<NPC>();

  private String name;

  public Face direction = Face.DOWN;
  private boolean isVisible, isMoving, isRunning, isWaiting;
  private boolean hasInstructions;

  private String[] options;

  private Execution instructor;

  private NPCType type;

  private TextHandler text;

  private int dy, dx;
  private int framesToGo;

  Animation rightAni, leftAni, downAni, upAni;

  /**
   * Creates a new NPC
   * 
   * @param name The name of the NPC
   * @param tx The tile x location of the bottom left corner of the NPC
   * @param ty The tile y location of the bottom left corner of the NPC
   * @param type The sprite of the NPC
   */
  public NPC(String name, float tx, float ty, NPCType type) {
    super(tx*16, ty*16, 16, 24);

    this.name = name;
    ent=Entities.NPC;

    setAttributes(type);

    isMoving=false;
    hasInstructions=false;
    isVisible = true;

    text = new TextHandler();


  }

  /**
   * Creates a new NPC at 0,0
   * 
   * @param name The name of the NPC
   */
  public NPC(String name){
    super(0,0,16,24);
    this.name = name;
    ent=Entities.NPC;

    isVisible = false;

    isMoving=false;
    hasInstructions=false;

    text = new TextHandler();

  }


  /**
   * @see komorebi.clyde.engine.Renderable#update()
   */
  @Override
  public void update() {

    if (framesToGo <= 0 && hasInstructions){
      isMoving=false;
      isRunning=false;
      isWaiting=false;
      dx=0;
      dy=0;

      hasInstructions=false;
      if (instructor != null){
        instructor.getLock().resumeThread();
      }

    }

    if (isMoving){
      if (!isRunning){
        downAni.setSpeed(8);
        leftAni.setSpeed(8);
        rightAni.setSpeed(8);
        upAni.setSpeed(8);
      }else{
        downAni.setSpeed(4);
        leftAni.setSpeed(4);
        rightAni.setSpeed(4);
        upAni.setSpeed(4);
      }
      switch (direction){
        case DOWN:
          downAni.resume();
          break;
        case LEFT:
          leftAni.resume();
          break;
        case RIGHT:
          rightAni.resume();
          break;
        case UP:
          upAni.resume();
          break;
        default:
          break;

      }

    }else{
      downAni.hStop();
      upAni.hStop();
      leftAni.hStop();
      rightAni.hStop();
    }

    x+=dx;
    y+=dy;

    if (dx != 0){
      framesToGo-=Math.abs(dx);
    }else if (dy != 0){
      framesToGo-=Math.abs(dy);
    }else if (isWaiting){
      framesToGo--;
    }

  }

  /**
   * @see komorebi.clyde.engine.Renderable#render()
   */
  @Override
  public void render() {

    if (isVisible) {
      switch (direction){
        case DOWN:
          downAni.play(x,y);
          break;
        case LEFT:
          leftAni.play(x,y);
          break;
        case RIGHT:
          rightAni.play(x,y);
          break;
        case UP:
          upAni.play(x,y);
          break;
        default:
          break;

      }

      text.render();
    }


  }

  /**
   * Moves the NPC indefinitely in a given direction
   * 
   * @param dir The direction in which the NPC should move
   */
  public void walk(Face dir){

    isMoving=true;
    isRunning=false;
    this.direction = dir;

    switch (dir){
      case DOWN:
        dx=0;
        dy=-1;
        break;
      case LEFT:
        dx=-1;
        dy=0;
        break;
      case RIGHT:
        dx=1;
        dy=0;
        break;
      case UP:
        dx=0;
        dy=1;
        break;
      default:
        break;

    }

  }

  /**
   * Moves the NPC a given number of tiles in a specified direction
   * 
   * @param dir The direction in which the NPC should move
   * @param tiles The number of tiles the NPC should move, where one tile is 
   *         equal to 16 pixels 
   */
  private void walk(Face dir, int tiles){

    hasInstructions=true;
    framesToGo = tiles*16;
    isMoving=true;
    isRunning=false;
    this.direction = dir;

    switch (dir){
      case DOWN:
        dx=0;
        dy=-1;
        break;
      case LEFT:
        dx=-1;
        dy=0;
        break;
      case RIGHT:
        dx=1;
        dy=0;
        break;
      case UP:
        dx=0;
        dy=1;
        break;
      default:
        break;

    }

  }

  /**
   * Moves the NPC a given number of tiles in a specified direction, pausing the
   * thread
   * 
   * @param dir The direction in which the NPC should move
   * @param tiles The number of tiles the NPC should move, where one tile is 
   *         equal to 16 pixels 
   * @param instructor The new thread to run the command The new thread to run the command
   */
  public void walk(Face dir, int tiles, Execution instructor){
    this.instructor = instructor;

    instructor.getLock().pauseThread();
    walk(dir,tiles);
  }

  /**
   * Moves the NPC a given number of tiles in a specified direction at a brisk 
   * pace
   * 
   * @param dir The direction in which the NPC should move
   * @param tiles The number of tiles the NPC should move, where one tile is 
   *         equal to 16 pixels 
   */
  private void jog(Face dir, int tiles){
    hasInstructions=true;
    framesToGo = tiles*16;
    isMoving=true;
    isRunning=true;
    this.direction = dir;

    switch (dir){
      case DOWN:
        dx=0;
        dy=-2;
        break;
      case LEFT:
        dx=-2;
        dy=0;
        break;
      case RIGHT:
        dx=2;
        dy=0;
        break;
      case UP:
        dx=0;
        dy=2;
        break;
      default:
        break;

    }
  }

  /**
   * Moves the NPC a given number of tiles in a specified direction at a brisk 
   * pace, pausing the thread
   * 
   * @param dir The direction in which the NPC should move
   * @param tiles The number of tiles the NPC should move, where one tile is
   *         equal to 16 pixels 
   * @param instructor The new thread to run the command The new thread to run the command
   */
  public void jog(Face dir, int tiles, Execution instructor){

    this.instructor = instructor;
    jog(dir,tiles);
    this.instructor.getLock().pauseThread();

  }

  /**
   * Turns the NPC to face a different direction
   * 
   * @param dir The direction for the NPC to face
   */
  private void turn(Face dir){

    direction=dir;
  }

  /**
   * Turns the NPC to face a different direction, pausing the thread
   * 
   * @param dir The direction for the NPC to face
   * @param instructor The new thread to run the command The new thread to run the command
   */
  public void turn(Face dir, Execution instructor){
    this.instructor = instructor;
    turn(dir);

  }


  public void setNPCType(NPCType type){
    setAttributes(type);
  }

  /**
   * Creates all of the required objects for the specified NPC type
   * 
   * @param type The NPC type to set the attributes
   */
  public void setAttributes(NPCType type){
    this.type = type;
    switch (type){
      case POKEMON:
        leftAni = new Animation(3,8,16,24,3);
        rightAni = new Animation(3,8,16,24,3);
        upAni = new Animation(3,8,16,24,3);
        downAni = new Animation(3,8,16,24,3);

        downAni.add(1, 0);
        downAni.add(18, 0);
        downAni.add(35, 0);

        leftAni.add(51, 0);
        leftAni.add(67, 0);
        leftAni.add(83, 0);

        rightAni.add(51, 0, true);
        rightAni.add(67, 0, true);
        rightAni.add(83, 0, true);

        upAni.add(100, 0);
        upAni.add(117, 0);
        upAni.add(134, 0);
        break;
      case NESS:
        leftAni = new Animation(2,8,16,24,4);
        rightAni = new Animation(2,8,16,24,4);
        upAni = new Animation(2,8,16,24,4);
        downAni = new Animation(2,8,16,24,4);

        downAni.add(0, 0);
        downAni.add(17, 0);

        upAni.add(34, 0);
        upAni.add(34, 0, true);

        leftAni.add(51, 0);
        leftAni.add(68, 0);

        rightAni.add(51, 0, true);
        rightAni.add(68, 0, true);
        break;
      default:
        leftAni = new Animation(3,8,16,24,3);
        rightAni = new Animation(3,8,16,24,3);
        upAni = new Animation(3,8,16,24,3);
        downAni = new Animation(3,8,16,24,3);

        downAni.add(1, 0);
        downAni.add(18, 0);
        downAni.add(35, 0);

        leftAni.add(51, 0);
        leftAni.add(67, 0);
        leftAni.add(83, 0);

        rightAni.add(51, 0, true);
        rightAni.add(67, 0, true);
        rightAni.add(83, 0, true);

        upAni.add(100, 0);
        upAni.add(117, 0);
        upAni.add(134, 0);
        break;

    }
  }

  /**
   * Pauses the game thread for the specified amount of frames
   * 
   * @param frames amount of time to pause, measured in game frames
   * @param instructor The new thread to run the command
   */
  public void pause(int frames, Execution instructor){
    hasInstructions=true;
    this.instructor=instructor;

    isWaiting=true;
    framesToGo=frames;

    this.instructor.getLock().pauseThread();
  }

  /**
   * Moves the NPC to a new tile
   * 
   * @param tx The tile x location of the bottom left corner of the NPC
   * @param ty The tile y location of the bottom left corner of the NPC
   */
  public void setLocation(int tx, int ty){
    this.x=tx*16;
    this.y=ty*16;
  }

  /**
   * Moves the NPC to a new tile, pausing the thread
   * 
   * @param tx The tile x location of the bottom left corner of the NPC
   * @param ty The tile y location of the bottom left corner of the NPC
   * @param instructor The new thread to run the command
   */
  public void setLocation(int tx, int ty, Execution instructor){
    this.instructor = instructor;
    setLocation(tx,ty);
  }

  /**
   * Says a string, creating a message box, and pausing movement
   * 
   * @param s The string to say
   * @param instructor The new thread to run the command
   */
  public void say(String s, Execution instructor){
    text.write(s, 20, 58, 8);
    this.instructor = instructor;

    Main.getGame().setSpeaker(this);
    instructor.getLock().pauseThread();
  }


  /**
   * Asks a question, creating a message box and pausing the thread
   * 
   * @param args The options to write
   * @param instructor The new thread to run the command
   */
  public void ask(String[] args, Execution instructor){
    text.write(args[0], 20, 58, 8);
    if (args.length > 1){
      text.write(args[1], 30, 40, 8);
    }
    if (args.length > 2){
      text.write(args[2], 100, 40, 8);
    }
    if (args.length > 3){
      text.write(args[3], 30, 22,8);
    }
    if (args.length > 4){
      text.write(args[4], 100, 22, 8);
    }

    this.instructor = instructor;

    options = args;
    text.drawPicker(1);
    Main.getGame().setMaxOptions(args.length-1);
    Main.getGame().setAsker(this);
    instructor.getLock().pauseThread();

  }

  public int getTileX(){
    return ((int) x)/16;
  }

  public int getTileY(){
    return ((int) y)/16;
  }
  
  public void clearText(){
    text.clear();
    instructor.getLock().resumeThread();
  }

  public void resumeThread(){
    this.instructor.getLock().resumeThread();
  }


  public void setPickerIndex(int i){
    text.setPickerIndex(i);
  }

  public void branch(int i){
    instructor.setCurrentBranch(options[i]);
  }

  public NPCType getType(){
    return type;
  }

  public String getName(){
    return name;
  }

  public static void add(NPC person){
    npcs.add(person);
  }

  /**
   * Returns the NPC that matches the name provided
   * 
   * @param s The name input
   * @return The NPC, if none found, null
   */
  public static NPC get(String s){
    for (NPC n: npcs){
      if (n.getName().equals(s)){
        return n;
      }
    }

    return null;
  }

  public static ArrayList<NPC> getNPCs(){
    return npcs;
  }

  public void setVisible(boolean b){
    isVisible = b;
  }
}
