package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.ForceAbility;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.entities.actors.Humanoid;
import starwars.swinterfaces.SWGridController;



/*
 * This class let's SWActors with force ability to control those with none or low force ability
 * @author Rashfa Moosa
 */

public class MindControl extends SWAffordance  {
	/**
	 * Constructor for the <code>MindControl</code> class. Will initialize the <code>messageRenderer</code> and
	 * give <code>MindControl</code> a priority of 1 (lowest priority is 0).
	 * 
	 * @param theTarget the target being controlled
	 * @param m message renderer to display messages
	 */
	
	public MindControl(SWActor theTarget, MessageRenderer m) {
		super(theTarget, m);	
		priority = 1;
	}
	


	/**
	 * Determine whether a particular <code>SWActor a</code> can mind control the target.
	 * 
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true, any <code>SWActor</code> can mind control the target, 
	 * but the mindControl will only work if they have higher forceAbility
	 */
	@Override
	public boolean canDo(SWActor a) {  
		if(a instanceof SWActor && target instanceof Humanoid){
		    return true;
		}else{
			return false;
		}
	}
	
	
	
	@Override
	public void act(SWActor a) {
		if(canDo(a) == false) {return;}
		
		SWActor aActor = (SWActor) a;
		Humanoid targetActor = (Humanoid) target;

		if(aActor.getForceAbility()== ForceAbility.LEVEL3 &&  targetActor.getForceAbility()==ForceAbility.LEVEL0){
		a.say("oi");
			//can control mind
		    //some code to make the target   move in whatever direction
	        targetActor.getScheduler().schedule(SWGridController.getUserDecision(targetActor), targetActor, 1);
		    aActor.say("\t" + aActor.getShortDescription() + " says: Yeap, I can control you now, " + targetActor.getShortDescription() + ". I see the force is with you");
		}else
			aActor.say("Mind control failed");
	}
			 
	
	/**
	 * A String describing what this <code>MindControl</code> action will do, 
	 * suitable for display on a user interface
	 * 
	 * @return String comprising "mind control " and the short description
	 *         of the target of this <code>Affordance</code>
	 */
	@Override
	public String getDescription() {
		return "mind control " + this.target.getShortDescription();
	}
}
