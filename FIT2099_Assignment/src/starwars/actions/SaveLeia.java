package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.entities.actors.LeiaOrgana;
import starwars.entities.actors.Player;

/**
 * An SWAffordance which can let LeiaOrgana follow Player
 * 
 * @author Rashfa Moosa
 *
 */
public class SaveLeia extends SWAffordance implements SWActionInterface{
	
	/**
	 * Creates an object of this affordance
	 * @param theTarget belongs to <Code>SWEntitiyInterface<Code>
	 * @param m message renderer for this <code>SWEntitiyInterface</code> to display messages
	 * @param 	world the <code>World</code> to which <code>SWEntitiyInterface</code> belongs to
	 */
	public SaveLeia(SWEntityInterface theTarget, MessageRenderer m)
	{
		super(theTarget, m); //super class constructor
		priority = 1;
	}
	
	/**
	 * Get the duration
	 * @return an interger representing duration
	 */
	@Override
	public int getDuration()
	{
		return 1;
	}
	
	/**
	 * Created a description suitable to be output to console
	 * @return a string with a message to be displayed
	 */
	@Override
	public String getDescription()
	{
		return "save " + this.target.getShortDescription();
	}
	
	/**
	 * Checks the conditions and return a boolean accordingly
	 * @return true if a in an instance of <Code>Player<Code>
	 *         false in every other case
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
	
	/**
	 * Allows the affordance to be removed from this <Code>SWActor<Code> once the affordance is used.
	 * 
	 */
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
