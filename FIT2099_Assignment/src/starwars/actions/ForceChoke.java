package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.DroidStatus;
import starwars.SWActor;
import starwars.SWDroid;
import starwars.SWEntityInterface;
import starwars.entities.actors.DarthVader;

/**
 * ForceChoke is inherited from Attack class, and it causes 50 points of damages to any SWActor
 * Because it can only be performed by Darth Vader, there is no need to add this affordance to all SWActor
 *
 */
public class ForceChoke extends Attack{
	
	/**
	 * Constructor for ForceChoke
	 * 
	 * @param theTarget target actor who will be attacked
	 * @param m MessageRenderer
	 */
	public ForceChoke(SWEntityInterface theTarget, MessageRenderer m)
	{
		super(theTarget, m);
		priority = 1;
	}
	
	@Override
	public String getDescription()
	{
		return "force choke " + this.target.getShortDescription();
	}
	
	/**
	 * Only darth vader schedule force choke
	 * For any other actors who is not darth vader, this method will return false
	 * 
	 * @param a: SWActor who performs this action
	 */
	@Override
	public boolean canDo(SWActor a)
	{
		if(a instanceof DarthVader)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * This method causes 50 points of damage to SWActor
	 */
	@Override
	public void act(SWActor a)
	{
		if(canDo(a) == false) {return;}
		
		SWEntityInterface target = this.getTarget();
		SWActor targetActor = null;
		
		
		targetActor = (SWActor) target;
		
		targetActor.takeDamage(50);
		a.say(a.getShortDescription() + "is force choking " + target.getShortDescription() + "!");
		
		//After choke
		if (this.getTarget().getHitpoints() <= 0) {  // can't use isDead(), as we don't know that the target is an actor
			SWDroid targetDroid = null;
			if(this.getTarget() instanceof SWDroid)
			{
				targetDroid = (SWDroid) target;
				targetDroid = (SWDroid) this.getTarget();
				targetDroid.setStatus(DroidStatus.IMMOBILE);
				targetDroid.addAffordance(new Fix(target, messageRenderer));
				targetDroid.addAffordance(new Disassemble(target, messageRenderer));
			}
			
			target.setLongDescription(target.getLongDescription() + ", that was killed in a fight");

			//remove the attack affordance of the dead actor so it can no longer be attacked
			targetActor.removeAffordance(new Attack(target, messageRenderer));
	}
   }
}
