/**
 * TextHandler.java  Jun 12, 2016, 1:48:14 PM
 */
package komorebi.clyde.script;

import java.util.ArrayList;

import komorebi.clyde.engine.Draw;

/**
 * 
 * @author Aaron Roy
 * @version 
 */
public class TextHandler {

  public static final int SCALE = 16;

  private boolean spoken;
  private boolean hasChoice;
  private int pickerIndex;

  public ArrayList<char[]> words;
  public ArrayList<Integer> xLocations;
  public ArrayList<Integer> yLocations;
  public ArrayList<Integer> ptSize;


  /**
   * Creates a text handler object which will render words in a speaker-bubble style
   */
  public TextHandler()
  {
    spoken=true;
    words = new ArrayList<char[]>();
    xLocations = new ArrayList<Integer>();
    yLocations = new ArrayList<Integer>();
    ptSize = new ArrayList<Integer>();
  }
  
  /**
   * Creates a text handler object
   * @param b If true, text will render in a speaker-bubble style
   *          If false, text will render without surrounding bubble
   */
  public TextHandler(boolean b)
  {
    spoken=b;
    words = new ArrayList<char[]>();
    xLocations = new ArrayList<Integer>();
    yLocations = new ArrayList<Integer>();
    ptSize = new ArrayList<Integer>();
  }

  private static int getTexX(char c)
  {
    switch (c)
    {
      case' ':case',':case'8':case'D':case'P':case'h':case't':
        return 0;
      case'!':case'-':case'9':case'E':case'Q':case'i':case'u':
        return SCALE;
      case'"':case'.':case':':case'F':case'R':case'j':case'v':
        return SCALE*2;
      case'/':case';':case'G':case'S':case'k':case'w':
        return SCALE*3;
      case'$':case'0':case'H':case'T':case'l':case'x':
        return SCALE*4;
      case'%':case'1':case'=':case'I':case'U':case'a':case'm':case'y':
        return SCALE*5;
      case'2':case'J':case'V':case'b':case'n':case'z':
        return SCALE*6;
      case'\'':case'3':case'?':case'K':case'W':case'c':case'o':case'[':
        return SCALE*7;
      case'(':case'4':case'L':case'X':case'd':case'p':
        return SCALE*8;
      case')':case'5':case'A':case'M':case'Y':case'e':case'q':case']':
        return SCALE*9;
      case'*':case'6':case'B':case'N':case'Z':case'f':case'r':case'~':
        return SCALE*10;
      case'+':case'7':case'C':case'O':case'g':case's':
        return SCALE*11;
      default:
        break;
    }

    return 0;
  }

  private static int getTexY(char c)
  {
    switch(c)
    {
      case' ':case'!':case'"':case'$':case'%':case'\'':case'(':case')':case'*':
      case'+':
        return 0;
      case',':case'-':case'.':case'/':case'0':case'1':case'2':case'3':case'4':
      case'5':case'6':case'7':
        return SCALE;
      case'8':case'9':case':':case';':case'=':case'?':case'A':case'B':case'C':
        return SCALE*2;
      case'D':case'E':case'F':case'G':case'H':case'I':case'J':case'K':case'L':
      case'M':case'N':case'O':
        return SCALE*3;
      case'P':case'Q':case'R':case'S':case'T':case'U':case'V':case'W':case'X':
      case'Y':case'Z':
        return SCALE*4;
      case'a':case'b':case'c':case'd':case'e':case'f':case'g':
        return SCALE*5;
      case'h':case'i':case'j':case'k':case'l':case'm':case'n':case'o':case'p':
      case'q':case'r':case's':
        return SCALE*6;
      case'[':case']':case'~':case't':case'u':case'v':case'w':case'x':case'y':
      case'z':
        return SCALE*7;
      default:
        break;
    }

    return 0;
  }

  public static int getTexSx(char c)
  {
    switch (c)
    {
      case' ':case',':case'8':case'D':case'P':case'h':case't':
        return 0;
      case'!':case'-':case'9':case'E':case'Q':case'i':case'u':
        return SCALE;
      case'"':case'.':case':':case'F':case'R':case'j':case'v':
        return SCALE*2;
      case'/':case';':case'G':case'S':case'k':case'w':
        return SCALE*3;
      case'$':case'0':case'H':case'T':case'l':case'x':
        return SCALE*4;
      case'%':case'1':case'=':case'I':case'U':case'a':case'm':case'y':
        return SCALE*5;
      case'2':case'J':case'V':case'b':case'n':case'z':
        return SCALE*6;
      case'\'':case'3':case'?':case'K':case'W':case'c':case'o':case'[':
        return SCALE*7;
      case'(':case'4':case'L':case'X':case'd':case'p':
        return SCALE*8;
      case')':case'5':case'A':case'M':case'Y':case'e':case'q':case']':
        return SCALE*9;
      case'*':case'6':case'B':case'N':case'Z':case'f':case'r':case'~':
        return SCALE*10;
      case'+':case'7':case'C':case'O':case'g':case's':
        return SCALE*11;
      default:
        break;
    }

    return 0;
  }

  public static int getTexSy(char c)
  {
    switch(c)
    {
      case' ':case'!':case'"':case'$':case'%':case'\'':case'(':case')':case'*':case'+':
        return 0;
      case',':case'-':case'.':case'/':case'0':case'1':case'2':case'3':case'4':case'5':
        case'6':case'7':
        return SCALE;
      case'8':case'9':case':':case';':case'=':case'?':case'A':case'B':case'C':
        return SCALE*2;
      case'D':case'E':case'F':case'G':case'H':case'I':case'J':case'K':case'L':case'M':
        case'N':case'O':
        return SCALE*3;
      case'P':case'Q':case'R':case'S':case'T':case'U':case'V':case'W':case'X':case'Y':case'Z':
        return SCALE*4;
      case'a':case'b':case'c':case'd':case'e':case'f':case'g':
        return SCALE*5;
      case'h':case'i':case'j':case'k':case'l':case'm':case'n':case'o':case'p':case'q':
        case'r':case's':
        return SCALE*6;
      case'[':case']':case'~':case't':case'u':case'v':case'w':case'x':case'y':case'z':
        return SCALE*7;
      default:
        break;
    }

    return 0;
  }

  /**
   * 
   * @param s
   * @param x
   * @param y
   * @param fontPt
   */
  public void write(String s, int x, int y, int fontPt)
  {
    words.add(s.toCharArray());
    xLocations.add(x);
    yLocations.add(y);
    ptSize.add(fontPt);
  }

  /**
   * Renders the text in a style specified by the text handler's attributess
   */
  public void render()
  {
    if (!words.isEmpty() && spoken) {
      Draw.rect(15, 15, 220, 59, 0, 0, 220, 59, 6);
    }
    
    for (char[] letters: words)
    {
      int horiz = xLocations.get(words.indexOf(letters));
      int vert = yLocations.get(words.indexOf(letters));
      int size = ptSize.get(words.indexOf(letters));

      for (int i=0; i < letters.length; i++)
      {

        int under = 0, texUnder = 0;

        if (letters[i]=='g' || letters[i] == 'j' || letters[i] == 'p' ||
            letters[i] == 'q' || letters[i] == 'y')
        {   
          under = size;
          texUnder = 8;
        }


        Draw.rect(horiz, vert-under, size, size+under, 
            getTexX(letters[i]), getTexY(letters[i]), 
            getTexX(letters[i])+8, getTexY(letters[i]) + 8+texUnder, 5);
        horiz+=size;
      }
    }

    if (hasChoice)
    {
      int x = 0, y= 0;
      switch (pickerIndex)
      {
        case 1: x = 20; y = 40; break;
        case 2: x = 90; y = 40; break;
        case 3: x = 20; y = 22; break;
        case 4: x = 90; y = 22; break;
        default: break;
      }

      Draw.rect(x, y, 8, 8, 0, 0, 8, 8, 7);
    }
  }

  /**
   * Clears the text handler's memory
   */
  public void clear()
  {
    words.clear();
    xLocations.clear();
    yLocations.clear();
    ptSize.clear();
    hasChoice = false;
  }





  /**
   * Draws the arrow pointing to one of two text-based choices
   * @param option Which option the arrow should correspond to, where 1 represents the left option and 2 represents the right option
   */
  public void drawPicker(int option)
  {
    pickerIndex = option;
    hasChoice = true;
  }

  public int getPickerIndex()
  {
    return pickerIndex;
  }

  public void setPickerIndex(int option)
  {
    pickerIndex = option;
  }
}
