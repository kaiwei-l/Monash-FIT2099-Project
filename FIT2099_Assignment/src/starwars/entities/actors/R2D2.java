package starwars.entities.actors;

import java.util.List;

import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.DroidPart;
import starwars.SWActor;
import starwars.SWDroid;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Disassemble;
import starwars.actions.Fix;
import starwars.actions.Heal;
import starwars.actions.Move;
import starwars.actions.Take;
import starwars.entities.OilCan;
import starwars.entities.actors.behaviors.Patrol;

public class R2D2 extends SWDroid{
	
	/**path is used for patrol during the game*/
	private Patrol path;
	
	/**OilReservoir always return a OilCan which can be used to oil droids*/
	private OilCan[] oilReservoir = new OilCan[1];
	
	public R2D2(int hitpoints, MessageRenderer m, SWWorld world, SWActor owner, Direction [] moves)
	{
		super(hitpoints, "R2-D2", m, world, owner);
		this.addCapability(Capability.REPAIR);
		this.path = new Patrol(moves);
		OilCan anOilCan = new OilCan(m);
		this.oilReservoir[0] = anOilCan;
	}
	
	@Override
	public String getShortDescription()
	{
		return this.getType() + ": a Droid";
	}
	
	@Override
	public String getLongDescription()
	{
		return this.getShortDescription();
	}
	
	private String describeLocation()
	{
		SWLocation location = this.world.getEntityManager().whereIs(this);
		return this.getShortDescription() + " [" + this.getHitpoints() + "] is at " + 
				location.getShortDescription();
	}
    
	/**
	 * This method organises different actions in a successive manner
	 * 
	 * Note that following is the priority of actions
	 * 1. Follow owner
	 * 2. Check entities in the same location and see if R2-D2 can do something, e.g. take droid part or fix immobile droid
	 * 3. Follow its patrol pattern and schedule a Move
	 */
	@Override
	public void act()
	{
		if(this.isFunctional() == false)
		{
			return;
		}
		else
		{
			this.say(this.describeLocation());
			
			this.reduceHealthIfInBadLand();
			
			// this.healitself if necessary		
			
			// follow owner
			if(this.canFollowOwner())
			{
				this.followOwner();
				return;
			}
			else
			{	
				if(this.getHitpoints() < this.getDefaultHitPoints())
				{
					Heal healMyself = new Heal(this, messageRenderer);
					scheduler.schedule(healMyself, this, 1);
				}
				
				
				// Find if there is any disabled driod or a droid part
				List<SWEntityInterface> entities = world.getEntityManager().contents(world.getEntityManager().whereIs(this));
				
				// for each entity in entities, after having done a SWAction on one entity, we should return and wait for next tick()
				// This is becasue SWAction does not happen immediately, so I would like R2-D2 can pick up a droid part and fix a droid
				// rather than pick up a droid part and disassemble a droid, which will happen if we don't jump out of the for loop below.
				for(SWEntityInterface e : entities)
				{
					boolean hasDoneSth = false;
					if(e instanceof DroidPart || e instanceof OilCan)
					{
						Take takeItem = new Take(e, messageRenderer);
						scheduler.schedule(takeItem, this, 1);
						return;
					}
					
					if(e instanceof SWDroid && (((SWDroid)e).getTeam() == this.getTeam()) && e != this)
					{
						SWDroid swdroid = (SWDroid) e;
						
						// Droid which is functional but needs oil
						if(swdroid.getHitpoints() < swdroid.getDefaultHitPoints())
						{
							Heal healIt = new Heal(e, messageRenderer);
							scheduler.schedule(healIt, this, 1);
							hasDoneSth = true;
						}
						
						// encounter immobile droid
						else if(swdroid.isImmobile())
						{
							hasDoneSth = true;
							// R2-D2 carries a droid part
							if(this.getItemCarried() instanceof DroidPart)
							{
								Fix fixDroid = new Fix(e, messageRenderer);	
								scheduler.schedule(fixDroid, this, 1);
							}
							else  // R2-D2 does not have droid part, so disassemble it
							{
								Disassemble disassembleDroid = new Disassemble(e, messageRenderer);
								scheduler.schedule(disassembleDroid, this, 1);
							}
						}
						
						if(hasDoneSth == true)
						{
							return;
						}
					}
				}
			}
			
			// patrol
			if(this.hasOwner() == false)
			{
				Direction newdirection = path.getNext();
				Move myMove = new Move(newdirection, messageRenderer, world);
				
				scheduler.schedule(myMove, this, 1);
			}
		}
	}
	
	public OilCan getOilCan()
	{
		return this.oilReservoir[0];
	}
	
	@Override
	public boolean hasOilReservoir()
	{
		return true;
	}

}
