package starwars.actions;
/*
 * Command to Heal entities
 * @author: Rashfa Moosa
 */
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWDroid;
import starwars.SWEntityInterface;
import starwars.entities.Canteen;
import starwars.entities.OilCan;


public class Heal extends SWAffordance{
	/**
	 * Constructor for the <code>Heal</code> class. Will initialize the <code>messageRenderer</code> and
	 * give <code>Heal</code> a priority of 1 (lowest priority is 0).
	 * 
	 * @param theTarget the target being Healed
	 * @param m message renderer to display messages
	 */
	
	public Heal(SWEntityInterface target,MessageRenderer m) {
		super(target, m);	
		priority = 1;
		
	}
	

	/**
	 * Determine whether a particular <code>SWActor a</code> can heal the target.
	 * 
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if a is <code>SWActor</code> and if <code>SWActor a</code> has a suitable item.
	 */
	@Override
	public boolean canDo(SWActor a){
		if(target instanceof SWDroid && a instanceof SWDroid)
		{
			if(((SWDroid)a).hasOilReservoir())
			{return true;}
			return false;
		}
		else if(target instanceof SWDroid && (a.getItemCarried() instanceof OilCan))
		{
			return true;
		}
		else if(target instanceof SWDroid == false && target instanceof SWActor && a.getItemCarried() instanceof Canteen && (((Canteen)a.getItemCarried()).isEmpty() == false))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Perform the <code>Heal</code> command on an entity.
	 * <p>
	 * This method heals only,
	 * <ul>
	 * 	<li>The target of the <code>heal</code> is a Droid and the item carried belongs to <code>OilCan<code></li>
	 * 	<li>TThe target of the <code>heal</code> is any SWActor but not Droid and the item carried belongs to <code>Canteen<code></li>
	 * </ul>
	 * <p>
	 *
	 * TODO : check if the item carried is DRINKABLE and SWActor is HEALABLE
	 * 
	 * @param 	a the <code>SWActor</code> who is Using Heal on some target
	 * @pre 	this method should only be called if the <code>SWActor a</code> is alive
	 * @pre		an <code>Attack</code> must not be performed on a dead <code>SWActor</code>
	 * @post	if a <code>SWActor</code>dies in an <code>Attack</code> their <code>Attack</code> affordance would be removed
	 * @see		starwars.SWActor#isDead()
	 * @see 	starwars.Team
	 */
   
	@Override
	public void act(SWActor a){
		if(canDo(a) == false) {return;}
		
		SWEntityInterface target = this.getTarget();
		SWActor targetActor;
		if(target instanceof SWActor)
		{
			targetActor = (SWActor) target;
			targetActor.increaseHitPoints();
		}
		
		if(a.getItemCarried() instanceof Canteen)
		{
			Canteen item = (Canteen) a.getItemCarried();
			item.use();
		}
	}

	/**
	 * A String describing what this <code>Heal</code> action will do, suitable for display on a user interface
	 * 
	 * @return String comprising "heal " and the short description of the target of this <code>Affordance</code>
	 */
	@Override
	public String getDescription() {
		return "heal " + target.getShortDescription();
	}
}
