/*
 * MainE.java        May 22, 2016, 2:15:58 PM
 */

package komorebi.clyde.engine;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;

import komorebi.clyde.editor.Palette;
import komorebi.clyde.states.Editor;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.openal.SoundStore;

/**
 * Use this if you want to run the editor
 * 
 * @author Aaron Roy
 * @version 0.0.1.0
 */
public class MainE {

  public static Editor edit;
  public static int scale = 1;
  private static BufferedReader read;

  private static JDialog frame;

  /**
   * Starts the program, reading an int from settings and using it for the scale.
   * @param args not used
   */
  public static void main(String[] args) {
	  
    try {
      read = new BufferedReader(
          new FileReader(new File("res/settings")));
      String str;

      while ((str = read.readLine()) != null) {
        if (str.charAt(0) == '#') {
          continue;
        }
        if (scale == 0) {
          scale = Integer.parseInt(str);
        }
      }

    } catch (IOException ex) {
      ex.printStackTrace();
      scale = 1;
    } catch (NumberFormatException ex) {
      ex.printStackTrace();
      scale = 1;
    }

    initDisplay();
    initGl();

    initGame();
    gameLoop();
    cleanUp();
  }


  /**
   *  Initializes the Display using the Display Class, properly Scaling it.
   */
  public static void initDisplay() {
    //create display
    try {
      Display.setDisplayMode(new DisplayMode(800 * scale, 608 * scale));
      Display.setTitle("Clyde\'s");
//    Display.setResizable(true);
      Display.create();
      Display.setVSyncEnabled(true);

    } catch (LWJGLException ex) {
      ex.printStackTrace();
    }
  }

  /**
   *  Creates a new game and initializes the audio.
   *  @see GameHandler
   */
  private static void initGame() {
    edit = new Editor();
    AudioHandler.init();
  }


  private static void getInput() {
    edit.getInput();
  }

  private static void update() {
    edit.update();
  }


  private static void render() {
    glClear(GL_COLOR_BUFFER_BIT);   //clears the matrix with black
    glLoadIdentity();

    edit.render();

    Display.update();   //updates the display with the changes
    Display.sync(60);   //makes up for lost time

  }


  /**
   *  Goes through the game loop, starting the music once.
   */
  private static void gameLoop() {

    while (!Display.isCloseRequested()) {
      getInput();
      update();
      render();
      SoundStore.get().poll(0);

      if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
        break;
      }

    }
  }

  /**
   *  Currently Enabled:<br>
   *  -Textures<br>
   *  -Transparency
   *  
   *  <p>Size: 256 x 224.
   */
  private static void initGl() {
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();              //resets the Matrix
    glOrtho(0,800,0,608,-1,1);     //creates a 3D space
    glMatrixMode(GL_MODELVIEW);
    glEnable(GL_TEXTURE_2D);       //enables Textures
    glEnable(GL_BLEND);

    //Enables transparency
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

    glClearColor(246f / 255,246f / 255,246f / 255,1);//sets the clearing color to black

    glDisable(GL_DEPTH_TEST);      //kills off the third dimension
  }
  
  /**
   *  Destroys the display and keyboard, closing the window.
   */
  private static void cleanUp() {
    Display.destroy();
    AL.destroy();
  }

  public static int getScale() {
    return scale;
  }

}
