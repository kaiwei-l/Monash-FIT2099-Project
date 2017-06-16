package starwars.entities.actors;

import java.util.ArrayList;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.DroidStatus;
import starwars.SWActor;
import starwars.SWDroid;
import starwars.SWLocation;
import starwars.SWWorld;

public class C3PO extends SWDroid{
	private ArrayList<String> c3poQuotes = new ArrayList<>();
	
	public C3PO(int hitpoints, MessageRenderer m, SWWorld world, SWActor owner)
	{
		super(hitpoints, "C-3PO", m, world, owner);

		// add some quotes to C3PO quote-set
		this.c3poQuotes.add("Don't blame me. I'm an interpreter. "
				+ "I'm not supposed to know a power "
				+ "socket from a computer terminal. ");
		this.c3poQuotes.add("I have no idea!");
		this.c3poQuotes.add("Exciting is hardly the word I would choose.");
		this.c3poQuotes.add("Oh. Well, yes.");
		this.c3poQuotes.add("If I fall, I'm programmed to smash, not fly.");
		this.c3poQuotes.add("I feel so helpless.");
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
	
	private String describeLocation() {
		SWLocation location = this.world.getEntityManager().whereIs(this);
		return this.getShortDescription() + " [" + this.getHitpoints() + "] is at " + 
				location.getShortDescription();
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
		
		// describe location about this droid
		say(describeLocation());
		
		// say some quotes
		if(Math.random() > 0.9)
		{
			int indx = (int) (Math.random() * ((5 - 0) + 1) + 0);
			say(this.getShortDescription()+ " says: " + c3poQuotes.get(indx));
		}
		
		// follow owner
		if(this.canFollowOwner())
		{
			this.followOwner();
		}
	}	

}
