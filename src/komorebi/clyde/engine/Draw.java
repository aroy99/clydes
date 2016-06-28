
/*
 * Draw.java    June 2, 2016, 7:42:38 PM
 */

package komorebi.clyde.engine;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.io.File;
import java.io.FileInputStream;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 * Draws stuff. :D
 *
 * @author Aaron Roy
 * @version 
 */
public class Draw {

  /** Determines whether textures are loaded.*/
  private static boolean texLoaded;
  
  /** Holds all of the textures for this class.*/
  private static Texture[] tex = new Texture[9];
  
  /** To ensure rotations can only happen in multiples of 90 degrees.*/
  private static final int RIGHT_ANGLE = 90;
  
  public static void rect(float x, float y, float sx, float sy, int texx, 
      int texy, int texsx, int texsy, int texID) {
    rect(x, y, sx, sy, texx, texy, texsx, texsy, 0, texID);
  }

  /**
   * Draws a sprite on the screen from the specified image, with rotation.
   * 
   * @param x the X position on the screen, starting from the left           
   * @param y the Y position on the screen, starting from the <i>bottom</i>  
   * @param sx the width                                                     
   * @param sy the height                                                    
   * @param texx X position on the picture, starting from the left           
   * @param texy Y position on the picture, starting from the <i>top</i>     
   * @param texsx end X position on the picture, starting from the left      
   * @param texsy end Y position on the picture, starting from the <i>top</i>
   * @param angle the rotation of the tile / 90 degrees
   * @param texID Use 0 for Pacman, 1 for ghosts
   */
  public static void rect(float x, float y, float sx, float sy, int texx, 
      int texy, int texsx, int texsy, int angle, int texID) {
    glPushMatrix();
    {
      if (!texLoaded) {
        loadTextures();
        texLoaded = true;
      }

      int imgX = tex[texID].getImageWidth();
      int imgY = tex[texID].getImageHeight();

      glTranslatef((int) x, (int) y, 0);
      glRotatef(angle * RIGHT_ANGLE, 0.0f, 0.0f, 1.0f);
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
      tex[texID].bind();

      glBegin(GL_QUADS);
     
        glTexCoord2f((float) texx / imgX, (float) texsy / imgY);
          glVertex2f(0, 0);
        glTexCoord2f((float) texx / imgX, (float) texy / imgY);
          glVertex2f(0, (int) sy);
        glTexCoord2f((float) texsx / imgX, (float) texy / imgY);
          glVertex2f((int) sx, (int) sy);
        glTexCoord2f((float) texsx / imgX, (float) texsy / imgY);
          glVertex2f((int) sx, 0);
      
      glEnd();
    }

    glPopMatrix();

  }
  

  

  /**
   * Loads textures.<p>
   * 
   * Current:<br>
   *     0: Terra<br>
   *     1: Tiles<br>
   *     2: Selector<br>
   */
  public static void loadTextures() {
    try {
      tex[0] = TextureLoader.getTexture("PNG", new FileInputStream(
          new File("res/Terra.png")));
      
      tex[1] = TextureLoader.getTexture("PNG", new FileInputStream(
          new File("res/PokeTiles.png")));
      
      tex[2] = TextureLoader.getTexture("PNG", new FileInputStream(
          new File("res/EditorSheet.png")));
      
      tex[3] = TextureLoader.getTexture("PNG", new FileInputStream(
              new File("res/NPCFiller.png")));
      
      tex[4] = TextureLoader.getTexture("PNG", new FileInputStream(
              new File("res/NPCFiller2.png")));
      
      tex[5] = TextureLoader.getTexture("PNG", new FileInputStream(
              new File("res/FillerFont.png")));
      
      tex[6] = TextureLoader.getTexture("PNG", new FileInputStream(
              new File("res/Textfield2.png")));
      
      tex[7] = TextureLoader.getTexture("PNG", new FileInputStream(
              new File("res/Picker.png")));
      tex[8] = TextureLoader.getTexture("PNG", new FileInputStream(
              new File("res/Fader.png")));
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
