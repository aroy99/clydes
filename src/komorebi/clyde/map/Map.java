/**
 * Map.java		May 30, 2016, 11:32:19 AM
 *
 * -
 */
package komorebi.clyde.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Savepoint;

import javax.swing.JFrame;

import komorebi.clyde.editor.Palette;
import komorebi.clyde.engine.MainE;
import komorebi.clyde.engine.Playable;
import komorebi.clyde.states.Editor;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

/**
 * Represents a map of tiles
 * 
 * @author Aaron Roy
 * @version 
 */
public class Map implements Playable{

    private boolean lButtonIsDown;                //Left Button Clicked
    private boolean rButtonIsDown, rButtonWasDown;//Right Button Clicked
    private boolean mButtonWasDown, mButtonIsDown;//Middle Button Pressed
    private boolean up, down, left, right;        //Directions for movement
    private boolean isReset, wasReset;            //Reset the map
    private boolean isSave, wasSave;              //Save the map
    
    private Palette pal;
    private Tile[][] tiles;                       //The Map itself
    
    private float x, y;
    private float dx, dy;
    private float speed = 10;
    private boolean isResetTile, wasResetTile;
    
    private int fileNum = 1;
    
    
    /**
     * Creates a new Map of the dimensions col x row
     * @param col number of columns (x)
     * @param row number of rows (y)
     */
    public Map(int col, int row){
        tiles = new Tile[row][col];
        pal = Editor.getPalette();
        
        for (int i = tiles.length-1; i >= 0; i--) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j] = new Tile(j, i, TileList.BLANK);
            }
        }
    }
    
    /**
     * Creates a map from a map file, used for the game
     * 
     * @param key The location of the map
     */
    public Map(String key){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(
                    new File(key)));
            int rows = Integer.parseInt(reader.readLine());
            int cols = Integer.parseInt(reader.readLine());

            tiles = new Tile[rows][cols];


            for (int i = 0; i < tiles.length; i++) {
                String[] str = reader.readLine().split(" ");
                int index = 0;
                for (int j = 0; j < cols; j++, index++) {
                    if(str[index].equals("")){
                        index++;
                    }
                    tiles[i][j] =new Tile(j, i, 
                            TileList.getTile(Integer.parseInt(str[index])));
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    @Override
    public void getInput() {        
        lButtonIsDown = Mouse.isButtonDown(0);
        
        rButtonWasDown = rButtonIsDown;
        rButtonIsDown = Mouse.isButtonDown(1);
        
        mButtonWasDown = mButtonIsDown;
        mButtonIsDown = Mouse.isButtonDown(2);        
        
        //Makes sure that up and down / left and right can't be both true
        up =    (Keyboard.isKeyDown(Keyboard.KEY_UP)     ||
                 Keyboard.isKeyDown(Keyboard.KEY_W))     &&
                    !(Keyboard.isKeyDown(Keyboard.KEY_DOWN)  ||
                      Keyboard.isKeyDown(Keyboard.KEY_S));
        
        down = (Keyboard.isKeyDown(Keyboard.KEY_DOWN)    ||
                Keyboard.isKeyDown(Keyboard.KEY_S))      &&
                   !(Keyboard.isKeyDown(Keyboard.KEY_UP)     ||
                     Keyboard.isKeyDown(Keyboard.KEY_W) ||
                    (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)||
                     Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)));
        
        left = (Keyboard.isKeyDown(Keyboard.KEY_LEFT)    ||
                Keyboard.isKeyDown(Keyboard.KEY_A))      &&
                   !(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)  ||
                     Keyboard.isKeyDown(Keyboard.KEY_D));
        
        right =(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)   ||
                Keyboard.isKeyDown(Keyboard.KEY_D))      &&
                   !(Keyboard.isKeyDown(Keyboard.KEY_LEFT)   ||
                     Keyboard.isKeyDown(Keyboard.KEY_A));

        wasReset = isReset;
        isReset=Keyboard.isKeyDown(Keyboard.KEY_R) &&
               !(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)||
                 Keyboard.isKeyDown(Keyboard.KEY_RCONTROL));
        
        wasResetTile = isResetTile;
        isResetTile = Keyboard.isKeyDown(Keyboard.KEY_R) &&
                   (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)||
                    Keyboard.isKeyDown(Keyboard.KEY_RCONTROL));
        
        if(isSave && !wasSave){
            save();
        }
        wasSave = isSave;
        isSave = Keyboard.isKeyDown(Keyboard.KEY_S) &&
                (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)||
                        Keyboard.isKeyDown(Keyboard.KEY_RCONTROL));
    }

    /**
     * Saves the Map file
     */
    private void save() {
        // TODO Auto-generated method stub
        
        
        try {
            File file;
            do{
                file = new File("res/maps/map"+fileNum);
                if(file.exists())fileNum++;
            }while(file.exists());
            PrintWriter writer = new PrintWriter("res/maps/map"+fileNum, "UTF-8");
            
            writer.println(tiles.length);
            writer.println(tiles[0].length);
            
            for (Tile[] tile : tiles) {
                for (Tile t : tile) {
                    writer.print(t.getType().getID() + " ");
                }
                writer.println();
            }
            System.out.println("Save complete");
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void render() {
        for (Tile[] tileL : tiles) {
            for (Tile t : tileL) {
                t.render();
            }
        }
    }

    @Override
    public void update() {
        //Sets mouse tile to the one from the palette
        if(lButtonIsDown && checkBounds()){
            tiles[getMouseY()][getMouseX()].setType(pal.getSelected().getType());
        }
        
        //Sets palette's selected to mouse tile
        if(rButtonIsDown && !rButtonWasDown && checkBounds()){
            pal.setLoc(tiles[getMouseY()][getMouseX()].getType());
        }

        //Flood Fills tiles
        if(mButtonIsDown && !mButtonWasDown && checkBounds()){
            flood(getMouseX(), getMouseY(), 
                    tiles[getMouseY()][getMouseX()].getType());
        }
        
        //Resets tiles to default
        if(isResetTile && !wasResetTile){
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles[0].length; j++) {
                    tiles[i][j].setType(TileList.BLANK);;
                }
            }

        }
        
        
        if(up)   dy =  speed;
        if(down) dy = -speed;
        if(right)dx =  speed;
        if(left) dx = -speed;
        
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j].move(dx, dy);
                tiles[i][j].update();
            }
        }
        
        x+=dx;
        y+=dy;
        
        if(isReset && !wasReset){
            x = 0;
            y = 0;
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles[0].length; j++) {
                    tiles[i][j].setLoc(j*16, i*16);
                }
            }

        }
        
        dx = 0;
        dy = 0;
    }
    
    /**
     * Recursive method that flood fills tiles
     * 
     * @param mouseX starting tile x
     * @param mouseY starting tile y
     * @param type tile to search and destroy
     */
  private void flood(int mouseX, int mouseY, TileList type) {
    if (mouseX < 0 || mouseX >= tiles[0].length ||
        mouseY < 0 || mouseY >= tiles.length){
      return;
    }
    if(tiles[mouseY][mouseX].getType() != type ||
        tiles[mouseY][mouseX].getType() == pal.getSelected().getType())
      return;
    tiles[mouseY][mouseX].setType(pal.getSelected().getType());
    flood(mouseX-1, mouseY,   type);
    flood(mouseX+1, mouseY,   type);
    flood(mouseX,   mouseY+1, type);
    flood(mouseX,   mouseY-1, type);

  }

    /**
     * Checks if the Mouse is in bounds of the map
     * @return Mouse is in map
     */
    private boolean checkBounds() {
        return (Mouse.getX()/MainE.getScale()<Palette.xOffset*16 ||
                Mouse.getY()/MainE.getScale()<Palette.yOffset*16) &&
              ((Mouse.getY()-(int)y)/(16*MainE.getScale()) >=0 &&
               (Mouse.getY()-(int)y)/(16*MainE.getScale()) <tiles.length &&
               (Mouse.getX()-(int)x)/(16*MainE.getScale()) >=0 &&
               (Mouse.getX()-(int)x)/(16*MainE.getScale()) <tiles[0].length);
    }
    
    /**
     * Converts Mouse X into a tile index, adjusting for map position
     * @return adjusted mouse x
     */
    private int getMouseX(){
        return ((Mouse.getX()/MainE.getScale())-(int)x)/(16);
    }
    
    /**
     * Converts Mouse Y into a tile index, adjusting for map position
     * @return adjusted mouse y
     */
    private int getMouseY() {
        return ((Mouse.getY()/MainE.getScale())-(int)y)/(16);
    }

    public void move(float dx, float dy) {
        x+=dx;
        x+=dy;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j].move(dx, dy);
                tiles[i][j].update();
            }
        }    
    }

}
