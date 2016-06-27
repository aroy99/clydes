/**
 * NPCType.java		Jun 10, 2016, 9:33:03 PM
 */
package komorebi.clyde.entities;

import java.util.ArrayList;

/**
 * 
 * @author Andrew Faulkenberry
 * @version 
 */
public enum NPCType {
	POKEMON,
	NESS;
	
	public static ArrayList<String> allStrings()
	{
		ArrayList<String> a = new ArrayList<String>();
		a.add("POKEMON");
		a.add("NESS");
		
		return a;
	}
	
	public static NPCType toEnum(String s)
	{
		switch (s)
		{
		case "POKEMON":
			return NPCType.POKEMON;
			
		case "NESS":
			return NPCType.NESS;
			
		default:
			return null;
		}
	}
}
