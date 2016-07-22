/**
 * ScriptHandler.java Jun 11, 2016, 2:26:01 PM
 */
package komorebi.clyde.script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import komorebi.clyde.entities.NPC;
import komorebi.clyde.entities.NPCType;
import komorebi.clyde.states.Game;

/**
 * 
 * @author Aaron Roy
 * @version 
 */
public class ScriptHandler {

  private static BufferedReader read;

  private static InstructionList currentBranch;
  private static BranchList branches;
  private static NPC npc;


  /**
   * Interprets a given script and has a given NPC execute them
   * @param script The script to be executed
   */
  public static void read(Script script, int abortIndex)
  {
    
    InstructionList ex = new InstructionList("Main");
    branches = new BranchList();
    branches.add(ex);

    setCurrentBranch(ex);


    try {
      read = new BufferedReader(
          new FileReader(new File("res/scripts/"+script.getScript()+".txt")));
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    }
    String s;

    try {
      while ((s = read.readLine()) != null) {
        s=s.trim();
        if (s.startsWith("walk"))
        {
          s=s.replace("walk ", "");

          String[] dir = s.split(" ");
          for (int i=0; i < dir.length; i++)
          {
            if (dir[i].equals("left"))
            {
              currentBranch.add(Instructions.WALK_LEFT);
            } else if (dir[i].equals("right"))
            {
              currentBranch.add(Instructions.WALK_RIGHT);
            } else if (dir[i].equals("down"))
            {
              currentBranch.add(Instructions.WALK_DOWN);
            } else if (dir[i].equals("up"))
            {
              currentBranch.add(Instructions.WALK_UP);
            }
          }
        } else if (s.startsWith("jog"))
        {
          //System.out.println(s);
          
          s=s.replace("jog ", "");

          String[] dir = s.split(" ");
          for (int i=0; i < dir.length; i++)
          {
            if (dir[i].equals("left"))
            {
              currentBranch.add(Instructions.JOG_LEFT);
            } else if (dir[i].equals("right"))
            {
              currentBranch.add(Instructions.JOG_RIGHT);
            } else if (dir[i].equals("down"))
            {
              currentBranch.add(Instructions.JOG_DOWN);
            } else if (dir[i].equals("up"))
            {
              currentBranch.add(Instructions.JOG_UP);
            }
          }
        } else if (s.startsWith("change"))
        {
          s =s.replace("change ", "");


          for (String type: NPCType.allStrings())
          {
            if (s.equalsIgnoreCase(type))
            {
              currentBranch.add(Instructions.CHANGE_SPRITE, NPCType.toEnum(s));
              break;
            }
          }
        } else if (s.startsWith("pause"))
        {
          s=s.replace("pause ", "");
          try
          {
            int frames = Integer.parseInt(s);
            currentBranch.add(Instructions.WAIT, frames);
          } catch (NumberFormatException e)
          {
            System.out.println("Error");
          }

        } else if (s.startsWith("lock"))
        {
          currentBranch.add(Instructions.LOCK);
        } else if (s.startsWith("unlock"))
        {
          currentBranch.add(Instructions.UNLOCK);
        } else if (s.startsWith("turn"))
        {
          s = s.replace("turn ", "");
          if (s.equalsIgnoreCase("left"))
          {
            currentBranch.add(Instructions.TURN_LEFT);
          } else if (s.equalsIgnoreCase("right"))
          {
            currentBranch.add(Instructions.TURN_RIGHT);
          } else if (s.equalsIgnoreCase("up"))
          {
            currentBranch.add(Instructions.TURN_UP);
          } else if (s.equalsIgnoreCase("down"))
          {
            currentBranch.add(Instructions.TURN_DOWN);
          }
        } else if (s.startsWith("say"))
        {
          s = s.replace("say ", "");
          currentBranch.add(Instructions.SAY, s);
        } else if (s.startsWith("ask"))
        {
          s = s.replace("ask ", "");
          String[] words = s.split("\"");

          String[] newWords = new String[words.length/2];

          for (int i=0; i < newWords.length; i++)
          {
            newWords[i]=words[(i*2)+1];
          }

          currentBranch.add(Instructions.ASK, newWords);
          currentBranch.add(Instructions.RUN_BRANCH_ASK);

        } else if (s.startsWith("branch"))
        {
          
          s = s.replace("branch ", "");
          branches.add(new InstructionList(s));
          currentBranch = branches.getBranch(s);
        } else if (s.startsWith("fadeout"))
        {
          currentBranch.add(Instructions.FADE_OUT);
        } else if (s.startsWith("fadein"))
        {
          currentBranch.add(Instructions.FADE_IN);
        } else if (s.startsWith("run "))
        {
          s = s.replace("run ","");
          currentBranch.add(Instructions.RUN_SCRIPT, s);
        } else if (s.startsWith("npc"))
        {
          s = s.replace("npc ", "");

          npc = Game.getMap().findNPC(s);
        } else if (s.startsWith("sprite"))
        {
          s = s.replace("sprite ", "");
          npc.setAttributes(NPCType.toEnum(s));

        } else if (s.startsWith("at"))
        {
          s = s.replace("at ","");
          String[] args = s.split(",");


          npc.setTileLocation(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
          npc.setVisible(true);
        } else if (s.startsWith("load"))
        {
          s = s.replace("load ", "");
          currentBranch.add(Instructions.LOAD_MAP, s);
        } else if (s.startsWith("retile"))
        {
          s = s.replace("retile ", "");
          String[] split = s.split(" ");
          currentBranch.add(Instructions.RETILE, Integer.parseInt(split[0]), 
              Integer.parseInt(split[2]), Integer.parseInt(split[1]));
        } else if (s.startsWith("clydewalk"))
        {
          s = s.replace("clydewalk ", "");
          String[] dir = s.split(" ");
          for (int i=0; i < dir.length; i++)
          {
            if (dir[i].equals("left"))
            {
              currentBranch.add(Instructions.CLYDE_WALK_LEFT);
            } else if (dir[i].equals("right"))
            {
              currentBranch.add(Instructions.CLYDE_WALK_RIGHT);
            }else if (dir[i].equals("down"))
            {
              currentBranch.add(Instructions.CLYDE_WALK_DOWN);
            } else if (dir[i].equals("up"))
            {
              currentBranch.add(Instructions.CLYDE_WALK_UP);
            }
          }
        } else if (s.startsWith("runbranch"))
        {
          s = s.replace("runbranch ", "");
          currentBranch.add(Instructions.RUN_BRANCH, s);
        } else if (s.startsWith("end"))
        {
          currentBranch.add(Instructions.END);
        } else if (s.startsWith("simulrun branch"))
        {
          s = s.replace("simulrun branch ", "");
          currentBranch.add(Instructions.SIMUL_RUN_BRANCH, s);
        } else if (s.startsWith("clydepause"))
        {
          s = s.replace("clydepause ", "");
          currentBranch.add(Instructions.CLYDE_PAUSE, Integer.parseInt(s));
        } else if (s.startsWith("clydeturn"))
        {
          s = s.replace("clydeturn ", "");
          if (s.equals("left"))
          {
            currentBranch.add(Instructions.CLYDE_TURN_LEFT);
          } else if (s.equals("right")) {
            currentBranch.add(Instructions.CLYDE_TURN_RIGHT);
          } else if (s.equals("up"))
          {
            currentBranch.add(Instructions.CLYDE_TURN_UP);
          } else if (s.equals("down"))
          {
            currentBranch.add(Instructions.CLYDE_TURN_DOWN);
          }
        }

      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    Execution execution = new Execution(npc, branches, abortIndex);
    
    script.setExecution(execution);
    execution.setSender(script);
    
    (new Thread(execution)).start();
  }

  public static void read(Script script, NPC person)
  {
    npc = person;
    read(script, 0);
  }
  
  public static void read(Script script)
  {
    read(script, 0);
  }
  
  public static void read(Script script, NPC person, int abortIndex)
  {
    npc = person;
    read(script, abortIndex);
  }
  

  private static void setCurrentBranch(InstructionList list)
  {
    currentBranch = list;
  }
  
  public static InstructionList getCurrentBranch()
  {
    return currentBranch;
  }
}
