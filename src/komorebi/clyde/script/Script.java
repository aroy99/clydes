/**
 * Script.java  Jul 8, 2016, 12:11:04 PM
 */
package komorebi.clyde.script;

/**
 * 
 * @author Aaron Roy
 * @version 
 */
public abstract class Script {

  public int abortionIndex;
  
  public String script;
  public Execution execution;
  
  public boolean isRunning;
  public boolean isInterrupted;
 
  
  public abstract void run();
  public abstract void abort();
  
  public void setExecution(Execution newEx)
  {
    execution = newEx;
  }
 
  public String getScript()
  {
    return script;
  }
  
  public boolean isRunning()
  {
    return isRunning;
  }
  
  public boolean isInterrupted()
  {
    return isInterrupted;
  }
  
  public void setIsRunning(boolean b)
  {
    isRunning = b;
  }
  
  public void setIsInterrupted(boolean b)
  {
    isInterrupted = b;
  }
  
  public void setAbortionIndex(int i)
  {
    abortionIndex = i;
  }
  
}
