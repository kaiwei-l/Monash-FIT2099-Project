package starwars;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.actions.Take;

public class DroidPart extends SWEntity{
	public DroidPart(MessageRenderer m)
	{
		super(m);
		
		this.shortDescription = "a droid part";
		this.longDescription = "A droid part which can be used to repair immobile droid";
		
		this.addAffordance(new Take(this, m));
	}
	
	public String getSymbol()
	{
		return "DP";
	}
	
    @Override
    public void takeDamage(int damage)
    {
    	return;
    }

}
