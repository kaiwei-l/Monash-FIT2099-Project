package starwars.actions;



import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.DroidPart;
import starwars.SWAction;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWDroid;

/**
 * An SWAffordance which means if one entity has this affordance, then this entity is disassembleable
 * 
 * @author Kaiwei Luo
 *
 */
public class Disassemble extends SWAffordance implements SWActionInterface{
	
	public Disassemble(SWEntityInterface theTarget, MessageRenderer m)
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
		return "disassemble " + this.target.getShortDescription();
		
	}

	@Override
	/**
	 * any SWActor can disassemble other droid
	 * 
	 * @return true if target is disassemble and false otherwise
	 */
	public boolean canDo(SWActor a)
	{
		if(target instanceof SWDroid)
		{
			if(((SWDroid)target).isImmobile())
			{return true;}
			return false;
		}
		return false;
	}
	
	@Override
	public void act(SWActor a)
	{
		if(canDo(a) == false) {return;}
		
		SWEntityInterface target = this.getTarget();
		
		SWDroid targetDroid = (SWDroid) target;
        DroidPart dp = new DroidPart(messageRenderer);
		
        // put a droid part in this location
		EntityManager<SWEntityInterface, SWLocation> entityManager = SWAction.getEntitymanager();
		entityManager.setLocation((SWEntityInterface)dp, entityManager.whereIs(a));
		
		//remove the target from the entity manager since it's now held by the SWActor
		SWAction.getEntitymanager().remove(target);

		a.say(a.getShortDescription() + " is disassembling " + targetDroid.getShortDescription());
	}
}
