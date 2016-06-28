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

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import komorebi.clyde.editor.Palette;
import komorebi.clyde.engine.MainE;
import komorebi.clyde.engine.Playable;
import komorebi.clyde.states.Editor;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

/**
 * Represents a map of tiles
 * 
 * @author Aaron Roy
 * @version 
 */
public class Map implements Playable{

    //Mouse buttons
    private boolean lButtonIsDown, lButtonWasDown;//Left Button Clicked
    private boolean rButtonIsDown, rButtonWasDown;//Right Button Clicked
    private boolean mButtonIsDown, mButtonWasDown;//Middle Button Pressed

    //Arrow keys
    private boolean up, down, left, right;        //Directions for movement

    //Special commands
    private boolean isReset, wasReset;            //Reset the map
    private boolean isSave, wasSave;              //Save the map
    private boolean isResetTile, wasResetTile;    //Resets tiles
    private boolean isGrid, wasGrid;              //Turn on/off Grid
    private boolean startDragging;                //Starting a group selection
    private boolean isDragging;                   //Is making a group selection
    private boolean isSelection;                  //A selection is active
    private boolean isClearSel, wasClearSel;      //Clears the selection

    private Palette pal;
    private Tile[][] tiles;                       //The Map itself
    private Tile[][] selection;
    
    

    private float x, y;         //Current location
    private int initX, initY; //Location at the beginning of a drag
    private float dx, dy;
    private float speed = 10;

    private int fileNum = 0;


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
        pal = Editor.getPalette();
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
            String s;
            
            while ((s=reader.readLine())!=null)
            {
            	
            }
            

            reader.close();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "The file was not found / was corrupt, therefore, the default settings were used");
            tiles = new Tile[10][10];
            pal = Editor.getPalette();

            for (int i = tiles.length-1; i >= 0; i--) {
                for (int j = 0; j < tiles[0].length; j++) {
                    tiles[i][j] = new Tile(j, i, TileList.BLANK);
                }
            }
        }         
        Keyboard.destroy();
        try {
            Keyboard.create();
        } catch (LWJGLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    /* (non-Javadoc)
     * @see komorebi.clyde.engine.Playable#getInput()
     */
    @Override
    public void getInput() {
        lButtonWasDown = lButtonIsDown;
        lButtonIsDown = Mouse.isButtonDown(0);

        rButtonWasDown = rButtonIsDown;
        rButtonIsDown = Mouse.isButtonDown(1) && !controlPressed();

        mButtonWasDown = mButtonIsDown;
        mButtonIsDown = Mouse.isButtonDown(2);
        
        startDragging = Mouse.isButtonDown(1) && controlPressed() && 
                !isDragging;

        isDragging = Mouse.isButtonDown(1) && isDragging || startDragging;
        
        //Makes sure that up and down / left and right can't be both true
        up =   upPressed()   && !downPressed();

        down = downPressed() && !(upPressed() || controlPressed());

        left = leftPressed() && !rightPressed();

        right =rightPressed()&& !leftPressed();

        wasReset = isReset;
        isReset=Keyboard.isKeyDown(Keyboard.KEY_R) && !controlPressed();

        wasResetTile = isResetTile;
        isResetTile = Keyboard.isKeyDown(Keyboard.KEY_R) && controlPressed();

        wasSave = isSave;
        isSave = Keyboard.isKeyDown(Keyboard.KEY_S) && controlPressed();

        wasGrid = isGrid;
        isGrid = Keyboard.isKeyDown(Keyboard.KEY_G) && controlPressed();
        
        wasClearSel = isClearSel;
        isClearSel = Keyboard.isKeyDown(Keyboard.KEY_C) && controlPressed();

    }
    
    /* (non-Javadoc)
     * @see komorebi.clyde.engine.Renderable#update()
     */
    @Override
    public void update() {
        //Sets mouse tile to the one from the palette
        if(lButtonIsDown && checkBounds() && !isSelection){
            tiles[getMouseY()][getMouseX()].setType(pal.getSelected().getType());
        }

        //Sets palette's selected to mouse tile
        if(rButtonIsDown && !rButtonWasDown && checkBounds()){
            pal.setLoc(tiles[getMouseY()][getMouseX()].getType());
            clearSelection();
        }

        //Flood Fills tiles
        if(mButtonIsDown && !mButtonWasDown && checkBounds()){
            flood(getMouseX(), getMouseY(), 
                    tiles[getMouseY()][getMouseX()].getType());
        }
        
        if(startDragging){
            initX = getMouseX();
            initY = getMouseY();
        }
        if(isDragging && checkBounds()){
            createSelection();
        }
        
        if(isSelection && lButtonIsDown && checkBounds() && !lButtonWasDown){
            for(int i = 0; i < selection.length; i++){
                for (int j = 0; j < selection[0].length; j++) {
                    if(checkTileBounds(getMouseY()+i, getMouseX()+j)){
                        tiles[getMouseY()+i][getMouseX()+j].setType(
                                selection[i][j].getType());
                    }
                }
            }
        }
        
        if(isClearSel && isSelection && !wasClearSel){
            clearSelection();
        }

        //Resets tiles to default
        if(isResetTile && !wasResetTile){
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles[0].length; j++) {
                    tiles[i][j].setType(TileList.BLANK);
                }
            }

        }

        if(isSave && !wasSave){
            save();
        }

        if(isGrid && !wasGrid){
            Tile.changeGrid();
        }


        if(up)   dy =  speed;
        if(down) dy = -speed;
        if(right)dx =  speed;
        if(left) dx = -speed;

        move(dx, dy);

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
    
    /* (non-Javadoc)
     * @see komorebi.clyde.engine.Renderable#render()
     */
    @Override
    public void render() {
        for (Tile[] tileL : tiles) {
            for (Tile t : tileL) {
                t.render();
            }
        }
        if(selection!=null){
            for (Tile[] tileL : selection) {
                for (Tile t : tileL) {
                    t.render();
                }
            }

        }
    }


    /**
     * Moves the entire map by the specified amount
     * 
     * @param dx pixels to move left/right
     * @param dy pixels to move up/down
     */
    public void move(float dx, float dy) {
        x+=dx;
        y+=dy;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j].setLoc(x+j*16, y+i*16);
                tiles[i][j].update();
            }
        }
        
        if(selection != null){
            for (int i = 0; i < selection.length; i++) {
                for (int j = 0; j < selection[0].length; j++) {
                    selection[i][j].move(dx, dy);
                    selection[i][j].update();
                }
            }
        }
    }
    
    /**
     * @return x
     */
    public float getX() {
        return x;
    }
    
    /**
     * @return y
     */
    public float getY() {
        return y;
    }

    /**
     * @return if the up key was pressed
     */
    private boolean upPressed() {
        return (Keyboard.isKeyDown(Keyboard.KEY_UP)||
                Keyboard.isKeyDown(Keyboard.KEY_W));
    }

    /**
     * @return if the down key was pressed
     */
    private boolean downPressed() {
        return (Keyboard.isKeyDown(Keyboard.KEY_DOWN)||
                Keyboard.isKeyDown(Keyboard.KEY_S));
    }

    /**
     * @return if the left key was pressed
     */
    private boolean leftPressed() {
        return (Keyboard.isKeyDown(Keyboard.KEY_LEFT)||
                Keyboard.isKeyDown(Keyboard.KEY_A));
    }

    /**
     * @return if the right key was pressed
     */
    private boolean rightPressed() {
        return (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)||
                Keyboard.isKeyDown(Keyboard.KEY_D));
    }

    /**
     * @return if the control key was pressed
     */
    private boolean controlPressed() {
        return (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)||
                Keyboard.isKeyDown(Keyboard.KEY_RCONTROL));
    }

    
    /**
     * Saves the Map file
     */
    private void save() {

        JFileChooser chooser = new JFileChooser("res/maps/");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Map Files", "map");
        chooser.setFileFilter(filter);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setDialogTitle("Enter the name of the map to save");
        int returnee = chooser.showSaveDialog(null);

        if(returnee == JFileChooser.APPROVE_OPTION){

            String path = chooser.getSelectedFile().getAbsolutePath();
            
            PrintWriter writer;

            try {
                if(path.substring(path.length()-4).equals(".map")){
                    writer = new PrintWriter(path, "UTF-8");
                }else{
                    writer = new PrintWriter(path+".map", "UTF-8");
                }
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
                e.printStackTrace();
            }

        }
    }

    /**
     * Creates a new selection
     */
    private void createSelection() {
        selection = new Tile[Math.abs(getMouseY()-initY)+1]
                [Math.abs(getMouseX()-initX)+1];
        int firstX, lastX;
        int firstY, lastY;

        firstX = Math.min(initX, getMouseX());
        firstY = Math.min(initY, getMouseY());

        lastX = Math.max(initX, getMouseX());
        lastY = Math.max(initY, getMouseY());


        for(int i = 0; i<=lastY - firstY; i++){
            for(int j = 0; j<=lastX - firstX; j++){
                selection[i][j] = new Tile(x + tiles[0].length*16 + j*16, 
                        y + i*16, tiles[firstY+i][firstX+j].getType(), true);
            }
        }
        
        isSelection = true;
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
              (getMouseY() >=0 &&
               getMouseY() <tiles.length &&
               getMouseX() >=0 &&
               getMouseX() <tiles[0].length);
    }
    
    /**
     * Checks if the tile is valid
     * 
     * @param tx the X index of the tile
     * @param ty the Y index of the tile
     * @return true if the tile is in bounds
     */
    private boolean checkTileBounds(int tx, int ty) {
        return ty>=0 &&           
               ty<tiles.length && 
               tx>=0 &&           
               tx<tiles[0].length;
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
    
    public Tile[][] getSelection(){
        return selection;
    }
    
    public void setSelection(Tile[][] sel){
        selection = sel;
    }
    
    public int getWidth(){
        return tiles[0].length;
    }
    
    public int getHeight(){
        return tiles.length;
    }

    /**
     * Sets isSelection to s
     * 
     * @param s
     */
    public void setSelection() {
        isSelection = true;        
    }
    
    public void clearSelection(){
        selection = null;
        isSelection = false;
    }

}

