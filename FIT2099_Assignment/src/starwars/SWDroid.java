package starwars;

import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.actions.Attack;
import starwars.actions.Disassemble;
import starwars.actions.Fix;
import starwars.actions.Move;
import starwars.actions.Own;
import starwars.actions.Take;
import starwars.entities.actors.behaviors.Follow;

/**
 * Class that represents a Droid that can perform actions in the starwars world.
 * @author Kaiwei Luo
 *
 */
public abstract class SWDroid extends SWActor{

	/**the type of this droid*/
	protected String type;
	
	/**A field which containing droid's owner and null if this droid does not have one*/
	private SWActor owner = null;
	
	/**SWDroid's status */
	private DroidStatus status;
	
	/**Used to get relative direction between a SWDroid and its owner if it has one*/
	protected Follow ownerPath;
	
	private ForceAbility droidForce = ForceAbility.LEVEL0;
	
	/**
	 * Constructor for this SWDroid. Will initialize this SWDroid's
	 * <code>messageRenderer</code> and set of capabilities.
	 * @param hitpoints
	 * @param type
	 * @param m messageRenderer
	 * @param world
	 */
	public SWDroid(int hitpoints, String type, MessageRenderer m, SWWorld world, SWActor owner)
	{
		super(Team.NEUTRAL, hitpoints, m, world);
		this.type = type;
		this.owner = owner;
		this.initializeStatusAndAffordance(this.status);
		
		if(this.owner != null)
		{
			this.ownerPath = new Follow(this.owner, this, this.world);
		}
	}
	

	public DroidStatus getStatus()
	{
		return status;
	}
	
	public void setOwner(SWActor owner)
	{
		this.owner = owner;
	}
	
	public void setStatus(DroidStatus status)
	{
		this.status = status;
	}
	
	public void initializeStatusAndAffordance(DroidStatus status)
	{
		this.setStatus(status);
		if(this.status == DroidStatus.FUNCTIONAL)
		{
			this.addAffordance(new Own(this, messageRenderer));  // A FUNCTIONAL SWDroid can be owned by SWActor
			this.addAffordance(new Attack(this, messageRenderer));
		}
		else  // this.status == DroidStatus.IMMOBILE
		{
			this.addAffordance(new Fix(this, messageRenderer));  // An IMMOBILE SWDriod can be fixed by SWActor
			this.addAffordance(new Disassemble(this, messageRenderer));  // An IMMOBILE SWDroid can be disassembled by SWActor
			this.removeAffordance(new Attack(this, messageRenderer));  // An IMMOBILE SWDroid cannot be attacked by SWActor
		}
	}
	
	public void resetHitpoints()
	{
		this.setHitpoints(this.getDefaultHitPoints());
	}
	
	public boolean isImmobile()
	{
		if(this.status == DroidStatus.IMMOBILE || this.getHitpoints() <= 0)
		{
			return true;
		}
		return false;
	}
	
	public boolean isFunctional()
	{
		if(this.status == DroidStatus.FUNCTIONAL || this.getHitpoints() > 0)
		{
			return true;
		}
		return false;
	}
	
	public boolean hasOwner()
	{
		if(this.owner != null)
		{
			return true;
		}
		return false;
	}
	
	public String getType()
	{
		return this.type;
	}
	
	public void setPath()
	{
		if(this.hasOwner())
		{
			this.ownerPath = new Follow(this.owner, this, this.world);
		}
		
	}
	
	public void followOwner()
	{
		Direction nextDirection = ownerPath.getDirection();
		Move nextMove = new Move(nextDirection, messageRenderer, world);
		
		scheduler.schedule(nextMove, this, 1);
	}
	
	/**
	 * This method find this SWDroid's locationa and see if this location has symbol: "b", which is for badland
	 * 
	 * @return true if SWDroid is in badland and false otherwise
	 */
	public boolean inBadLand()
	{
		SWLocation droidLoc = (SWLocation) world.find(this);
		
		if(droidLoc.getSymbol() == 'b')
		{
			return true;
		}
		
		return false;
	}
	
	public void reduceHealthIfInBadLand()
	{
		if(this.inBadLand())
		{
			this.takeDamage(1);  // staying in badland will be reduced health
			if(this.getHitpoints() == 0)
			{
				this.initializeStatusAndAffordance(DroidStatus.IMMOBILE);  // if health is 0, then this droid is immobile
			}
		}
	}
	
	public boolean canFollowOwner()
	{
		if(this.hasOwner() == true && this.isFunctional())
		{
			return true;
		}
		return false;
	}
	
	@Override
	public void act()
	{
		this.reduceHealthIfInBadLand();
		
		if(this.canFollowOwner())  // follow owner
		{
			this.followOwner();
		}
	}
	
	@Override
	public boolean isDead()
	{
		if(this.isImmobile())
		{
			return true;
		}
		return false;
	}
	
	public boolean hasOilReservoir()
	{return false;}
}
