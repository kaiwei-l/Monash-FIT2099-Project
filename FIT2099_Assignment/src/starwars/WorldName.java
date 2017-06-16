package starwars;

/**
 * Enumerator class for different names of grids, in which SWActor lives in
 *
 */
public enum WorldName {
	INITIAL, // initial world
	DEATHSTAR,
	YAVINIV;
	
	/**
	 * 
	 * @param aName: one of the WorldName
	 * @return string representation of WorldName
	 */
	public String toString(WorldName aName)
	{
		if(aName == WorldName.INITIAL)
		{
			return "Original World";
		}
		else if(aName == WorldName.DEATHSTAR)
		{
			return "Dead Star";
		}
		else
		{
			return "Yavin IV";
		}

	}

}
