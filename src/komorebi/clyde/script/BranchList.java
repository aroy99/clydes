/**
 * BranchList.java		Jun 18, 2016, 3:12:01 PM
 */
package komorebi.clyde.script;

import java.util.ArrayList;

/**
 * 
 * @author Aaron Roy
 * @version 
 */
public class BranchList extends ArrayList<InstructionList> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BranchList()
	{
		super();
	}
	
	public InstructionList getBranch(String s)
	{
		for (InstructionList i: this)
		{
			if (s.equalsIgnoreCase(i.getBranchName()))
			{
				return i;
			}
		}
		
		return null;
	}
	
	
	
}
