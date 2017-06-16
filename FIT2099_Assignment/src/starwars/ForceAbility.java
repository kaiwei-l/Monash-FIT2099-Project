package starwars;
/**
 * Force ability that various SWActors may have. 

 *  
 * @author 	Rashfa Moosa
 * 
 */

public enum ForceAbility {
	LEVEL0,//SWActors with no force ability
	LEVEL1,//SWActors with low force ability
	LEVEL2,//SWActors with  middle force ability
	LEVEL3;//SWActors with high force ability
	
    public Enum<?> increaseForceAbility(ForceAbility fb)
	{
		if(fb == ForceAbility.LEVEL0)
		{return ForceAbility.LEVEL1;}
		else if(fb == ForceAbility.LEVEL1)
		{return ForceAbility.LEVEL2;}
		else if(fb == ForceAbility.LEVEL2)
		{return ForceAbility.LEVEL3;}
		else
		{return ForceAbility.LEVEL3;}
	}

}
