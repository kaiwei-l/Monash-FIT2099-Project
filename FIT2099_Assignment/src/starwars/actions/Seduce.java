package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.Team;
import starwars.entities.actors.DarthVader;
import starwars.entities.actors.Player;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class is used to drag Luke into dark side
 *
 */
public class Seduce extends SWAffordance implements SWActionInterface{
	
	/**
	 * constructor of Seduce
	 * 
	 * @param theTarget: target actor to be draged into dark side
	 * @param m: MessageRenderer
	 */
	public Seduce(SWEntityInterface theTarget, MessageRenderer m)
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
		return "is trying to seduce " + this.target.getShortDescription() + "into the dark side of the Force";
	}
	
	/**
	 * Only DathVader can drag other SWActor into dark side
	 * 
	 * @param a: SWActor who performs this action
	 */
	@Override
	public boolean canDo(SWActor a)
	{
		if(a instanceof DarthVader){return true;}
		return false;
	}
	
	/**
	 * if player is not trained, then we sets his team into Evil since he cannot resist.
	 * if player is trained, then there is a 25% chance that this player will be 
	 * turned into dark side of the force.
	 * 
	 * @param a: SWActor who will perform this action
	 */
	@Override
	public void act(SWActor a)
	{
		if(canDo(a) == false) {return;}
		
		Player targetActor = null;
		
		if(this.getTarget() instanceof Player)
		{
			targetActor = (Player) this.target;
		}
		
		if(targetActor.hasTrained() == false)
		{
			// end the game
			targetActor.setTeam(Team.EVIL);
			a.say(targetActor.getLongDescription() + " has been turned into Dark side");
		}
		else
		{
			int chanceOfSeduce = ThreadLocalRandom.current().nextInt(1, 100 + 1);
			
			// seduce him
			if(chanceOfSeduce >= 75)
			{
				// end game
				targetActor.setTeam(Team.EVIL);
				a.say(targetActor.getLongDescription() + "has been turned into Dark side");
			}
			return;
		}
	}
}
