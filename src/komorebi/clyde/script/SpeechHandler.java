/**
 * SpeechHandler.java		Jul 26, 2016, 10:54:08 AM
 */
package komorebi.clyde.script;

import komorebi.clyde.engine.Draw;

/**
 * 
 * @author Aaron Roy
 * @version 
 */
public class SpeechHandler extends TextHandler {

  private boolean hasChoice;
  private int pickerIndex;
  private boolean alreadyAsked;
  private boolean paraWait;
  private int paraStop;
  private static boolean scrolling;

  private int scrollIndex;
  private int buffer;
  private static int speed;
  
  private int dotCount;

  public SpeechHandler(boolean b)
  {
    super();
    scrolling = b;
    speed = 1;
  }
  
  public static void setSpeed(int speed)
  {
    scrolling = true;
    SpeechHandler.speed = speed;
  }
  
  public static void setScrolling(boolean b)
  {
    scrolling = b;
  }

  @Override
  public void render()
  { 
    if (!words.isEmpty())
    {
      Draw.rect(15, 15, 220, 59, 0, 0, 220, 59, 6);
    }
    
    if (hasChoice)
    {
      if (!alreadyAsked)
      {
        if (scrolling)
        {
          scrollingRender(words.get(0), scrollIndex);
        } else
        {
          super.render(words.get(0));
          skipScroll();
        }
      } else {
        for (char[] letters: words)
        {
          super.render(letters);
        }
        
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
      
      
    } else if (!words.isEmpty())
    {
      if (scrolling)
      {
        scrollingRender(words.get(0), scrollIndex);
      } else {
        render(words.get(0));
      }
    }
    
    if (paraWait)
    {
      
      if (dotCount>=10 && dotCount<50)
      {
        Draw.rect(210, 25, 1, 1, 1, 0, 2, 1, 6);
      }
      
      if (dotCount>=20 && dotCount<60)
      {
        Draw.rect(215, 25, 1, 1, 1, 0, 2, 1, 6);
      }
      
      if (dotCount>=30 && dotCount<70)
      {
        Draw.rect(220, 25, 1, 1, 1, 0, 2, 1, 6);
      }
     
      dotCount++;
      if (dotCount>=80)
      {
        dotCount=0;
      }
    }

  }
  
  private void scrollingRender(char[] letters, int scroll)
  {
    int horiz = xLocations.get(words.indexOf(letters));
    int vert = yLocations.get(words.indexOf(letters));
    int size = ptSize.get(words.indexOf(letters));
      
    int ohor = horiz;
    
    for (int i=0; i < scroll; i++)
    {
      
      if (letters[i]=='\\')
      {
        if (letters[i+1]=='n')
        {
          vert-=size*2;
          horiz = ohor;
          i++;
          continue; 
        } else if (letters[i+1]=='p')
        {
          paraWait = true;
          paraStop = i+2;
          i++;
          continue;
        }
      }
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
      horiz+=(getLength(letters[i])+1);
    }
    
    if (!paraWait)
    {
      if (scrollIndex<letters.length)
      {
        buffer++;
        if (buffer>=speed)
        {
          scrollIndex++;
          buffer=0;
        }
      } else if (hasChoice){
        scrollIndex = 0;
        alreadyAsked = true;
      }
    }
    
    
  }
  
  @Override
  public void render(char[] letters)
  {
    int horiz = xLocations.get(words.indexOf(letters));
    int vert = yLocations.get(words.indexOf(letters));
    int size = ptSize.get(words.indexOf(letters));

    int ohor = horiz;
    
    if (!paraWait)
    {
      for (int i=0; i < letters.length; i++)
      {
        if (letters[i]=='\\')
        {
          if (letters[i+1]=='n')
          {
            vert-=size*2;
            horiz = ohor;
            i++;
            continue;
          } else if (letters[i+1]=='p')
          {
            paraWait = true;
            paraStop = i+2;
            i++;
            break;
          }
          
        } 

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
        horiz+=(getLength(letters[i])+1);
      }
    } else 
    {
      for (int i=0; i < paraStop-2; i++)
      {
        if (letters[i]=='\\')
        {
          if (letters[i+1]=='n')
          {
            vert-=size*2;
            horiz = ohor;
            i++;
            continue;
          } else if (letters[i+1]=='p')
          {
            paraWait = true;
            paraStop = i+2;
            
          }
          
        } 

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
        horiz+=(getLength(letters[i])+1);
      }
    }
    
    
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

  @Override
  public void clear()
  {
    super.clear();
    scrollIndex = 0;
    hasChoice = false;
    alreadyAsked = false;
  }

  @Override
  public void write(String s, int x, int y, int fontPt)
  {
    super.write(s,x,y,fontPt);
  }
  
  public void skipScroll()
  {
    alreadyAsked = true;
    scrollIndex = 0;
  }
  
  public boolean alreadyAsked()
  {
    return alreadyAsked;
  }
  
  public boolean isWaitingOnParagraph()
  {
    return paraWait;
  }
  
  public void nextParagraph()
  {
    char[] newWord = new char[words.get(0).length-paraStop+1];
    for (int i=paraStop; i<words.get(0).length; i++)
    {
      newWord[i-paraStop] = words.get(0)[i];
    }
    
    words.set(0, newWord);
    paraWait = false;
    if (scrolling) 
    {
      scrollIndex =0;
    }
    
    dotCount = 0;
  }

}
