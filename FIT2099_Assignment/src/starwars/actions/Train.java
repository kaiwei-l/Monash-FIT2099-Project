package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;

import starwars.ForceAbility;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.entities.actors.BenKenobi;
import starwars.entities.actors.Player;

/**
 * Command to train entities.
 * 
 * This affordance is attacked to trainable entities
 * 
 * @author Rashfa Moosa
 */

public class Train extends SWAffordance {

	
	/**
	 * Constructor for the <code>Train</code> class. Will initialize the <code>messageRenderer</code> and
	 * give <code>Train</code> a priority of 1 (lowest priority is 0).
	 * 
	 * @param theTarget the target being trained
	 * @param m message renderer to display messages
	 */
	public Train(SWActor theTarget, MessageRenderer m) {
		super(theTarget, m);	
		priority = 1;
	}



	
	/**
	 * A String describing what this <code>Train</code> action will do, suitable for display on a user interface
	 * 
	 * @return String comprising "train " and the short description of the target of this <code>Affordance</code>
	 */
	@Override
	public String getDescription() {
		return this.target.getShortDescription() + " offers training";
	}


	/**
	 * Determine whether a particular <code>SWActor a</code> can trian the target.
	 * 
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true any <code>SWActor</code> can always try an train, it just won't do much 
	 * 			good unless this <code>SWActor a</code> is BenKenobi.
	 */
	@Override
	public boolean canDo(SWActor a) {
		if(a instanceof Player && target instanceof BenKenobi){
			return true;
		} 
		return false;
	}

	
	/**
	 * Perform the <code>Train</code> command on an entity.
	 * TODO : check to see if target is Luke and trianer is BenKenobi
	 */
	@Override
	public void act(SWActor a) {
 		Player trainee = (Player) a;
 		trainee.setTrainStatus(true);
		//increase forceAbility of Player
	    if (trainee.getForceAbility() == ForceAbility.LEVEL3){
			trainee.say(trainee.getShortDescription() + "is already trained to maximum force ability");
		}else if (trainee.getForceAbility() == ForceAbility.LEVEL0){
			trainee.setForceAbility(ForceAbility.LEVEL1); 
			trainee.say(trainee.getShortDescription() + "is trained. Force Ability increased to " + trainee.getForceAbility());
		}else if(trainee.getForceAbility() == ForceAbility.LEVEL1){
			trainee.setForceAbility(ForceAbility.LEVEL2); 
			trainee.say(trainee.getShortDescription() + "is trained. Force Ability increased to " + trainee.getForceAbility());
			//WEAPON added to capabilities when ForceAbility increased to LEVEL2, check <Code>Capability<Code> for details
			trainee.giveLightSabres();
			trainee.say(trainee.getShortDescription() + "can use a weapon (LightSabres) now");
		}else if(trainee.getForceAbility() == ForceAbility.LEVEL2){
			trainee.setForceAbility(ForceAbility.LEVEL3); 
			trainee.say(trainee.getShortDescription() + "is trained. Force Ability increased to " + trainee.getForceAbility());
		}
			
	}
}
