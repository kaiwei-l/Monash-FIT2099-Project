package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWDroid;
import starwars.SWEntityInterface;

/**
 * An SWAffordance which can let SWDroid or SWLegendDroid be owned by an SWActor
 * 
 * @author Kaiwei Luo
 *
 */
public class Own extends SWAffordance implements SWActionInterface{
	
	public Own(SWEntityInterface theTarget, MessageRenderer m)
	{
		super(theTarget, m);
		priority = 1;
	}
	
	@Override
	public int getDuration()
	{
		return 1;
	}
	
	@Override
	public String getDescription()
	{
		return "own " + this.target.getShortDescription();
	}
	
	/**
	 * Only actor who does not carry droid can own a droid
	 */
	@Override
	public boolean canDo(SWActor a)
	{
		if (a.ownDroid() == false)  // actor does not have a droid
		{
			return true;
		}
		return false;
	}
	
	@Override
	public void act(SWActor a)
	{
		if(target instanceof SWDroid)
		{
			SWDroid targetDroid = (SWDroid) this.getTarget();
			targetDroid.removeAffordance(this);
			targetDroid.addAffordance(new NotOwn(this.getTarget(), messageRenderer));
			targetDroid.setOwner(a);
			targetDroid.setPath();  // This droid has owner and we can set its ownerPath for it
			                        // to follow its owner
			targetDroid.setTeam(a.getTeam());
		}
		a.setDroidCarried(this.getTarget());
		a.say(a.getShortDescription() + " says: I am the master of " + this.getTarget().getShortDescription());
	}
}
