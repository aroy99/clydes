/**
 * ScriptHandler.java		Jun 11, 2016, 2:26:01 PM
 */
package komorebi.clyde.script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import komorebi.clyde.entities.NPC;
import komorebi.clyde.entities.NPCType;

/**
 * 
 * @author Aaron Roy
 * @version 
 */
public class ScriptHandler {

	private static BufferedReader read;
	
	private static InstructionList currentBranch;
	private static BranchList branches = new BranchList();
	
	/**
	 * Interprets a given script and has a given NPC execute them
	 * @param script The script to be executed
	 * @param npc The NPC to whom the script refers
	 */
	public static void read(String script, NPC npc)
	{
		InstructionList ex = new InstructionList("Main");
		setCurrentBranch(ex);
		branches.add(ex);
		
		
		try {
			read = new BufferedReader(
			        new FileReader(new File("res/"+script+".txt")));
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
					for (int i=0; i<dir.length; i++)
					{
						if (dir[i].equals("left"))
						{
							currentBranch.add(Instructions.WALK_LEFT);
						} 
						else if (dir[i].equals("right"))
						{
							currentBranch.add(Instructions.WALK_RIGHT);
						}
						else if (dir[i].equals("down"))
						{
							currentBranch.add(Instructions.WALK_DOWN);
						}
						else if (dir[i].equals("up"))
						{
							currentBranch.add(Instructions.WALK_UP);
						}
					}
				}
				else if (s.startsWith("jog"))
				{
					s=s.replace("jog ", "");
					
					String[] dir = s.split(" ");
					for (int i=0; i<dir.length; i++)
					{
						if (dir[i].equals("left"))
						{
							currentBranch.add(Instructions.JOG_LEFT);
						} 
						else if (dir[i].equals("right"))
						{
							currentBranch.add(Instructions.JOG_RIGHT);
						}
						else if (dir[i].equals("down"))
						{
							currentBranch.add(Instructions.JOG_DOWN);
						}
						else if (dir[i].equals("up"))
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
					
					for (int i=0; i<newWords.length; i++)
					{
						newWords[i]=words[(i*2)+1];
					}
					
					currentBranch.add(Instructions.ASK, newWords);
					currentBranch.add(Instructions.BRANCH, newWords);
					
					for (int i=1; i<newWords.length; i++)
					{
						branches.add(new InstructionList(newWords[i]));
					}
					
					
				} else if (s.startsWith("branch"))
				{
					s = s.replace("branch ", "");
					currentBranch = branches.getBranch(s);
				} else if (s.startsWith("fadeout"))
				{
					currentBranch.add(Instructions.FADE_OUT);
				} else if (s.startsWith("fadein"))
				{
					currentBranch.add(Instructions.FADE_IN);
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		(new Thread(new Execution(npc, branches))).start();
	}
	
	private static void setCurrentBranch(InstructionList list)
	{
		currentBranch = list;
	}
}