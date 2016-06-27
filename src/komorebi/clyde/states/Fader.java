/**
 * Fader.java		Jun 26, 2016, 3:54:54 PM
 */
package komorebi.clyde.states;

import komorebi.clyde.engine.Draw;
import komorebi.clyde.script.Execution;


/**
 * 
 * @author Aaron Roy
 * @version 
 */
public class Fader {
	
	private static int faderIndex;
	private static boolean isFadingOut, isFadingIn;
	
	private static Execution instructor;
	
	public static void fadeOut(Execution ex)
	{
		isFadingOut = true;
		instructor = ex;
		instructor.getLock().pauseThread();
		
	}
	
	public static void fadeIn(Execution ex)
	{
		
		isFadingIn=true;
		
		instructor = ex;
		instructor.getLock().pauseThread();

	}
	
	public static void update()
	{
		/*
		if (isFading)
		{
			inc++;
			
			if (inc>=2)
			{
				inc=0;
				faderIndex++;
			}
			
			if (faderIndex>=16) 
			{
				inc=0;
				isFading = false;
			}
		}
		*/
		
		if (isFadingOut) 
		{
			faderIndex++;
			if (faderIndex>16) 
			{
				isFadingOut=false;
				instructor.getLock().resumeThread();
			}
		}
		
		
		if (isFadingIn) 
		{
			faderIndex--;
			if (faderIndex<=0) 
			{
				isFadingIn=false;
				instructor.getLock().resumeThread();
			}
		}
		
		
	}
	
	public static void render()
	{
		
		Draw.rect(0, 0, 284, 224, faderIndex, 0, faderIndex+1, 1, 8);
	}
}
