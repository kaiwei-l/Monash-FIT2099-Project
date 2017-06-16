package starwars.entities.actors;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWDroid;
import starwars.SWWorld;
import starwars.SWActor;

public class GeneralDroid extends SWDroid{
	
	public GeneralDroid(int hitpoints, MessageRenderer m, SWWorld world, SWActor owner)
	{
		super(hitpoints, "General-Type", m, world, owner);
	}
	
	@Override
	public String getShortDescription()
	{
		return this.getType() + ": a Droid";
	}
	
	@Override
	public String getLongDescription()
	{
		return this.getShortDescription();
	}
	
	@Override
	public void act()
	{
		if(this.isFunctional() == false)
		{
			return;
		}
		
		// reduce health if in badland
		this.reduceHealthIfInBadLand();
		
		// follow owner
		if(this.canFollowOwner())
		{
			this.followOwner();
		}
	}

}
