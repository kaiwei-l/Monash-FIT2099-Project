package starwars.actions;

import edu.monash.fit2099.simulator.matter.ActionInterface;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.DroidPart;
import starwars.DroidStatus;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWDroid;
import starwars.SWEntityInterface;

/**
 * A SWAffordance which means if an entity has this Affordance, then it is fixable
 * 
 * @author Kaiwei Luo
 *
 */
public class Fix extends SWAffordance implements ActionInterface{
	
	public Fix(SWEntityInterface theTarget, MessageRenderer m)
	{
		super(theTarget, m);
		priority = 1;
	}
	
	/**
	 * Only SWActor with capability: repair can repair a droid and this SWActor needs to carry a droid part
	 */
	@Override
	public boolean canDo(SWActor a)
	{
		if(a.hasCapability(Capability.REPAIR))
		{
			SWEntityInterface item = a.getItemCarried();
			if(item instanceof DroidPart)
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method has two logic blocks. One for normal droid 
	 * and the other for legend droid, which perform similar functionalities
	 * 
	 */
	@Override
	public void act(SWActor a)
	{
		if(canDo(a) == false) {return;}
		
		SWEntityInterface target = this.getTarget();
		SWDroid targetDroid = null;
		if (target instanceof SWDroid)
		{
			targetDroid = (SWDroid) target;
			targetDroid.resetHitpoints();
			targetDroid.setStatus(DroidStatus.FUNCTIONAL);
			targetDroid.removeAffordance(this);
			targetDroid.removeAffordance(new Disassemble(target, messageRenderer));  // A FUNCTIONAL droid cannot be disassembled
			targetDroid.addAffordance(new Attack(target, messageRenderer));  // A FUNCTIONAL droid should be attackable
		}
		a.setItemCarried(null);
		a.say(a.getShortDescription() + " is fixing " + targetDroid.getShortDescription());		
	}
	
	@Override
	public String getDescription()
	{
		return "fixs " + target.getShortDescription();
	}

  }
