package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.entities.Canteen;
import starwars.entities.OilCan;
import starwars.SWDroid;

public class Drink extends SWAffordance{
	public Drink(SWEntityInterface theTarget, MessageRenderer m)
	{
		super(theTarget, m);
		priority = 1;
	}
	
	public boolean canDo(SWActor a)
	{
		if(a instanceof SWDroid)
		{
			if(a instanceof SWDroid)
			{
				if(a.getItemCarried() instanceof OilCan || ((SWDroid)a).hasOilReservoir())
				{return true;}
			}
		}
		
		// a is not a droid
		if(a.getItemCarried() instanceof Canteen && ((Canteen)a.getItemCarried()).isEmpty() == false)
		{return true;}
		return false;
	}
	
	/**
	 * A String describing what this <code>Heal</code> action will do, suitable for display on a user interface
	 * 
	 * @return String comprising "heal " and the short description of the target of this <code>Affordance</code>
	 */
	@Override
	public String getDescription() {
		return "drink " + target.getShortDescription();
	}
	
	@Override 
	public void act(SWActor a)
	{
		if(canDo(a) == false) {return;}
		a.increaseHitPoints();
		if (target instanceof Canteen)
		{((Canteen)target).use();}
	}


}
