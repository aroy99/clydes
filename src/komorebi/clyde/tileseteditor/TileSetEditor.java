/**
 * TileSetEditor.java		Jul 28, 2016, 2:35:58 PM
 */
package komorebi.clyde.tileseteditor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import komorebi.clyde.engine.Animation;
import komorebi.clyde.engine.Draw;
import komorebi.clyde.engine.Key;
import komorebi.clyde.engine.KeyHandler;
import komorebi.clyde.engine.Playable;
import komorebi.clyde.entities.NPC;
import komorebi.clyde.entities.NPCType;
import komorebi.clyde.map.EditorMap;
import komorebi.clyde.map.TileList;
import komorebi.clyde.script.AreaScript;
import komorebi.clyde.script.TalkingScript;
import komorebi.clyde.script.WalkingScript;
import komorebi.clyde.script.WarpScript;
import komorebi.clyde.states.Editor;

/**
 * 
 * @author Aaron Roy
 * @version 
 */
public class TileSetEditor implements Playable {

  private int max, min;

  private TileList[][] left;
  private TileList[][] right;

  private static final int HEIGHT = 256;
  private static final int OFFX = 8;

  private Animation selection;
  private int selX, selY;
  private TileList selTiles[][];

  private boolean mouseIsDown, mouseWasDown, mouseClick;
  int prevMx, prevMy;

  int anchorX, anchorY;

  private boolean save;
  private boolean newSave;
  private String savePath, saveName;

  public TileSetEditor()
  {

    save = true;
    newSave = false;

    min = 0;
    max = 16;

    left = new TileList[32][8];
    right = new TileList[16][8];

    for (int i=0, k=0; i<left.length; i++)
    {
      for (int j=0; j<left[0].length; j++, k++)
      {
        left[i][j] = TileList.getTile(k);
      }
    }

    for (int i=0; i<right.length; i++)
    {
      for (int j=0; j<right[0].length; j++)
      {
        right[i][j] = TileList.BLANK;
      }
    }

    selection = new Animation(8, 8, 16, 16, 2);
    for(int i=3; i >= 0; i--){
      selection.add(0 , 0 , i);
      selection.add(16, 0 , i);
    }

    selX = 0;
    selY = 0;

    selTiles = new TileList[1][1];
    selTiles[0][0]=TileList.BLANK;
  }

  /* (non-Javadoc)
   * @see komorebi.clyde.engine.Renderable#update()
   */
  @Override
  public void update() {
    int dWheel = Mouse.getDWheel();

    mouseWasDown = mouseIsDown;
    mouseIsDown = Mouse.isButtonDown(0);

    mouseClick = mouseIsDown && !mouseWasDown;

    if (left() && mouseClick)
    {
      selTiles = new TileList[1][1];

      selX = getMouseTx();
      selY = getMouseTy();

      anchorX = getMouseTx();
      anchorY = getMouseTy();

      selTiles[0][0] = left[31-(getMouseTy()+(16-min))][getMouseTx()];
    } else if (right() && mouseIsDown)
    {
      if (save)
      {
        Display.setTitle(Display.getTitle() + "*");
        save = false;
      }

      int horiz, vert;

      if (15-getMouseTy()+selTiles[0].length>15)
      {
        vert = getMouseTy()+1;
      } else {
        vert = selTiles[0].length;
      }

      if (getMouseTx()-9+selTiles.length>7)
      {
        horiz = 16-getMouseTx()+1;
      } else {
        horiz = selTiles.length;
      }

      for (int i=0; i<vert; i++)
      {
        for (int j=0; j<horiz; j++)
        {
          right[15-getMouseTy()+i][getMouseTx()-9+j] = selTiles[j][i];
        }
      }

    } else if (left() && mouseIsDown && (prevMx!=getMouseTx() ||
        prevMy!=getMouseTy()))
    {
      int lowX = Math.min(getMouseTx(), anchorX);
      int hiX = Math.max(getMouseTx(), anchorX);

      int lowY = Math.min(getMouseTy(), anchorY);
      int hiY = Math.max(getMouseTy(), anchorY);


      selX = lowX;
      selY = hiY;
      selTiles = new TileList[hiX-lowX+1][hiY-lowY+1];

      for (int i=0; i<selTiles.length; i++)
      {
        for (int j=0; j<selTiles[0].length; j++)
        {
          selTiles[i][j] = left[left.length-1-(selY-j+(16-min))][selX+i];
        }
      }

    }

    if (saveButton() && mouseClick)
    {
      if (!newSave)
      {
        newSave = newSave();
      } else
      {
        save(savePath, saveName);
      }
    }

    if (openButton() && mouseClick)
    {
      load();
    }

    if ((KeyHandler.keyDown(Key.DOWN) ||(left() && dWheel<0))&& max<left.length && 
        !mouseIsDown)
    {
      max++;
      min++;
      selY++;
    }

    if ((KeyHandler.keyDown(Key.UP) || (left() && dWheel>0))&& min>0 && 
        !mouseIsDown)
    {
      max--;
      min--;
      selY--;
    }

    prevMx = getMouseTx();
    prevMy = getMouseTy();
  }

  /* (non-Javadoc)
   * @see komorebi.clyde.engine.Renderable#render()
   */
  @Override
  public void render() {
    for (int i=min; i<max; i++)
    {
      for (int j=0; j<left[0].length; j++)
      {
        Draw.rect(j*16, HEIGHT-16-(i*16)+(min*16), 16, 16, left[i][j].getX(), 
            left[i][j].getY(), left[i][j].getX()+16, left[i][j].getY()+16, 1);
      }
    }

    for (int i=0; i<right.length; i++)
    {
      for (int j=0; j<right[0].length; j++)
      {
        if (right[i][j]!=null)
        {
          Draw.rect(OFFX*16+j*16+16, HEIGHT-16-(i*16), 16, 16, right[i][j].getX(), 
              right[i][j].getY(), right[i][j].getX()+16, right[i][j].getY()+16,
              1);
        }
      }
    }


    for (int i=0; i<selTiles.length; i++)
    {
      for (int j=0; j<selTiles[0].length; j++)
      {
        Draw.rect(selX*16+i*16, selY*16-j*16, 16, 16, 220, 0, 221, 1, 6);
      }
    }

    Draw.rect(128, 0, 16, 16, 221, 0, 237, 16, 6);
    Draw.rect(128, 16, 16, 16, 237, 0, 253, 16, 6);
  }

  /* (non-Javadoc)
   * @see komorebi.clyde.engine.Playable#getInput()
   */
  @Override
  public void getInput() {
    // TODO Auto-generated method stub

  }

  public boolean left()
  {
    return (Mouse.getX()<128*MainT.scale);
  }

  public boolean right()
  {
    return (Mouse.getX()>144*MainT.scale);
  }

  public boolean saveButton()
  {
    return getMouseTx()==8 && getMouseTy()==0;
  }

  public boolean openButton()
  {
    return getMouseTx()==8 && getMouseTy()==1;
  }

  public int getMouseTx()
  {
    return (Mouse.getX()/MainT.scale) / 16;
  }

  public int getMouseTy()
  {
    return (Mouse.getY()/MainT.scale) / 16;
  }

  public boolean newSave() {

    JFileChooser chooser = new JFileChooser("res/tilesets/"){
      /**
       * I don't know what this does, but it does something...
       */
      private static final long serialVersionUID = 3881189161552826430L;

      @Override
      public void approveSelection(){
        File f = getSelectedFile();
        if(f.exists() && getDialogType() == SAVE_DIALOG){
          int result = JOptionPane.showConfirmDialog(this,
              "The file exists, overwrite?","Existing file",JOptionPane.YES_NO_CANCEL_OPTION);
          switch(result){
            case JOptionPane.YES_OPTION:
              super.approveSelection();
              return;
            case JOptionPane.NO_OPTION:
              return;
            case JOptionPane.CLOSED_OPTION:
              return;
            case JOptionPane.CANCEL_OPTION:
              cancelSelection();
              return;
            default:
              return;
          }
        }
        super.approveSelection();
      }
    };

    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "TileSet Files", "tset");
    chooser.setFileFilter(filter);
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    chooser.setDialogTitle("Enter the name of the tileset to save");
    int returnee = chooser.showSaveDialog(null);

    Editor.reloadKeyboard();

    if(returnee == JFileChooser.APPROVE_OPTION){

      savePath = chooser.getSelectedFile().getAbsolutePath();
      saveName = chooser.getSelectedFile().getName();

      return save(savePath, saveName);
    }

    return false;
  }

  public boolean save(String path,String name) {
    PrintWriter writer;

    try {
      if(path.substring(path.length()-5).equals(".tset")){
        writer = new PrintWriter(path, "UTF-8");
      }else{
        writer = new PrintWriter(path+".tset", "UTF-8");
      }
      writer.println(right.length);
      writer.println(right[0].length);

      for (TileList[] tile : right) {
        for (TileList t : tile) {
          writer.print(t.getID() + " ");
        }
        writer.println();
      }
      System.out.println("Save complete");
      save = true;
      writer.close();
      if(name.substring(name.length()-5).equals(".tset")){
        Display.setTitle("Clyde\'s Tile Editor - " + name);
      }else{
        Display.setTitle("Clyde\'s Tile Editor - " + name + ".tset");
      }


      return true;
    } catch (FileNotFoundException | UnsupportedEncodingException e) {
      e.printStackTrace();
      return false;
    }

  }

  public void load(){

    JFileChooser chooser = new JFileChooser("res/tilesets/");
    /*FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "Map Files", "map");*/
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "TileSet Files", "tset");
    chooser.setFileFilter(filter);
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    chooser.setDialogTitle("Enter the name of the tile set to load");
    int returnee = chooser.showOpenDialog(null);



    if(returnee == JFileChooser.APPROVE_OPTION){
      /*map = new EditorMap(chooser.getSelectedFile().getAbsolutePath(), 
          chooser.getSelectedFile().getName());
       */
      savePath = chooser.getSelectedFile().getAbsolutePath();
      saveName = chooser.getSelectedFile().getName();

      try {
        BufferedReader reader = new BufferedReader(new FileReader(
            new File(savePath)));
        int rows = Integer.parseInt(reader.readLine());
        int cols = Integer.parseInt(reader.readLine());

        right = new TileList[rows][cols];

        for (int i = 0; i < rows; i++) {
          String[] str = reader.readLine().split(" ");
          int index = 0;
          for (int j = 0; j < cols; j++, index++) {
            if(str[index].equals("")){
              index++;  //pass this token, it's blank
            }
            right[i][j] = TileList.getTile(Integer.parseInt(str[index]));
          }
        }

        Display.setTitle("Clyde\'s Editor - "+saveName);

        save = true;
        newSave = true;
        reader.close();
      } catch (IOException | NumberFormatException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, 
            "The file was not found / was corrupt.");

       
      }         
    }
  }
}
