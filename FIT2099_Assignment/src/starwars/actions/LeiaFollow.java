package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.entities.actors.LeiaOrgana;
import starwars.entities.actors.Player;

/**
 * An SWAffordance which can let SWDroid or SWLegendDroid be owned by an SWActor
 * 
 * @author Rashfa Moosa
 *
 */
public class LeiaFollow extends SWAffordance implements SWActionInterface{
	
	public LeiaFollow(SWEntityInterface theTarget, MessageRenderer m)
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
		return "make " + this.target.getShortDescription() + " follow Player";
	}
	
	/**
	 * Only actor who does not carry droid can own a droid
	 */
	@Override
	public boolean canDo(SWActor a)
	{
		if (a instanceof Player)  // actor is a player
		{
			return true;
		}
		return false;
	}
	
	@Override
	public void act(SWActor a)
	{
		if(target instanceof LeiaOrgana)
		{
			LeiaOrgana targetLeia = (LeiaOrgana) this.getTarget();
			targetLeia.removeAffordance(this);
			targetLeia.setFollower(a);
			targetLeia.setPath();  // Leia follows luke
			
		}
		
		a.setDroidCarried(this.getTarget());
		a.say(a.getShortDescription() + " says: Good, follow me " + this.getTarget().getShortDescription());
	}
}
