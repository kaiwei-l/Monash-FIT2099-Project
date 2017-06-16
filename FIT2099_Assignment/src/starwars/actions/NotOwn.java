package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWDroid;
import starwars.SWEntityInterface;
import starwars.Team;

/**
 * An SWAffordance which means if one entity has this affordance, then is can be not owned by its owner
 * 
 * @author Kaiwei Luo
 *
 */
public class NotOwn extends SWAffordance implements SWActionInterface{
	
	public NotOwn(SWEntityInterface theTarget, MessageRenderer m)
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
		return "doesn't own " + this.target.getShortDescription();
	}
	
	/**
	 * An entity can be not owned only if it is an SWDroid or SWLegendDroid and 
	 * has an owner 
	 */
	@Override
	public boolean canDo(SWActor a)
	{
		if(target instanceof SWDroid)
		{
			if(((SWDroid)target).hasOwner() && a.ownDroid() == true)
			{
				return true;
			}
			return false;
		}
		return false;
	}
	
	@Override
	public void act(SWActor a)
	{
		if(target instanceof SWDroid)
		{
			SWDroid targetDroid = (SWDroid) this.getTarget();
			targetDroid.setOwner(null);
			targetDroid.setTeam(Team.NEUTRAL);
			targetDroid.removeAffordance(this);
			targetDroid.addAffordance(new Own(this.getTarget(), messageRenderer));
		}
		a.setDroidCarried(null);
	}
	
	
}
