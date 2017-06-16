package starwars.entities.actors;

import java.util.List;

import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.ForceAbility;
import starwars.SWEntityInterface;
import starwars.SWLegend;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Drink;
import starwars.actions.Leave;
import starwars.actions.Move;
import starwars.actions.Take;
import starwars.actions.Train;
import starwars.entities.Canteen;
import starwars.entities.LightSaber;
import starwars.entities.actors.behaviors.AttackInformation;
import starwars.entities.actors.behaviors.AttackNeighbours;
import starwars.entities.actors.behaviors.Patrol;

/**
 * Ben (aka Obe-Wan) Kenobi.  
 * 
 * At this stage, he's an extremely strong critter with a <code>Lightsaber</code>
 * who wanders around in a fixed pattern and neatly slices any Actor not on his
 * team with his lightsaber.
 * 
 * Note that you can only create ONE Ben, like all SWLegends.
 * @author rober_000
 *
 */
public class BenKenobi extends SWLegend {

	private static BenKenobi ben = null; // yes, it is OK to return the static instance!
	private Patrol path;
	
	/**A pointer which indicates Ben's previous item*/
	private SWEntityInterface previousItem = null;
	
	/**Ben can only drink until one canteen is empty or his hitpoints is 100%. He cannot drink another canteen beacasue this would
	 * overwrite his previous item from lightsaber to canteen*/
	private boolean hasDoneOneDrink = false;
	
	private BenKenobi(MessageRenderer m, SWWorld world, Direction [] moves) {
		super(Team.GOOD, 1000, m, world);
		path = new Patrol(moves);
		this.setShortDescription("Ben Kenobi");
		this.setLongDescription("Ben Kenobi, an old man who has perhaps seen too much");
		this.addAffordance(new Train(this, m));
		forceAbility = ForceAbility.LEVEL2;
		LightSaber bensweapon = new LightSaber(m);
		setItemCarried(bensweapon);
	}

	public static BenKenobi getBenKenobi(MessageRenderer m, SWWorld world, Direction [] moves) {
		ben = new BenKenobi(m, world, moves);
		ben.activate();
		return ben;
	}
	
	@Override
	protected void legendAct() {

		if(isDead()) {
			return;
		}
		
		AttackInformation attack;
		attack = AttackNeighbours.attackLocals(ben,  ben.world, true, true);
		
		// if hitpoints is not 100% then we need to search Canteen
		if(this.getHitpoints() < this.getDefaultHitPoints())
		{
			// if we have one non-empty Canteen, then we need to drink it
			if(this.getItemCarried() instanceof Canteen && ((Canteen)this.getItemCarried()).isEmpty() == false)
			{   
				Drink aDrink = new Drink(this.getItemCarried(), messageRenderer);
				scheduler.schedule(aDrink, this, 1);
				this.hasDoneOneDrink = true;
				return;
			}
			
			if(hasDoneOneDrink == false)
			{
				// Find if there is any disabled driod or a droid part
				List<SWEntityInterface> entities = world.getEntityManager().contents(world.getEntityManager().whereIs(this));
							
				// for each entity in entities, after having done a SWAction on one entity, we should return and wait for next tick()
				// This is becasue SWAction does not happen immediately, so I would like R2-D2 can pick up a droid part and fix a droid
				// rather than pick up a droid part and disassemble a droid, which will happen if we don't jump out of the for loop below.
				for(SWEntityInterface e : entities)
				{   
					// if entity is non-empty canteen
					if(e instanceof Canteen && ((Canteen) e).isEmpty() == false)
					{   
						// if ben is carrying an item, we need to drop that item
						if(this.getItemCarried() != null)
						{
							Leave leaveItem = new Leave(this.getItemCarried(), messageRenderer);
							scheduler.schedule(leaveItem, this, 1);
							// set previous holding item as ben's previous item
							previousItem = this.getItemCarried();
							return;
						}
						// if ben is empty-hand, he only need to pick up that canteen
						Take takeItem = new Take(e, messageRenderer);
						scheduler.schedule(takeItem, this, 1);
						return;
						}
			       }
			}
		}
		
		// if ben's health is 100% or canteen is empty
		if(this.getItemCarried() instanceof Canteen)
		{
			this.hasDoneOneDrink = false;
			Leave leaveItem = new Leave(this.getItemCarried(), messageRenderer);
			scheduler.schedule(leaveItem, this, 1);
			
			// restore previous item
			if(this.previousItem != null)
			{
				Take takeItem = new Take(previousItem, messageRenderer);
				scheduler.schedule(takeItem, this, 1);
				previousItem = null;
			}
			return;
		}
		  // attack other entities
		if (attack != null) {
			say(getShortDescription() + " suddenly looks sprightly and attacks " +
		attack.entity.getShortDescription());
			scheduler.schedule(attack.affordance, ben, 1);
		}
		// keep patroling
		else
		{
			Direction newdirection = path.getNext();
			say(getShortDescription() + " moves " + newdirection);
			Move myMove = new Move(newdirection, messageRenderer, world);

			scheduler.schedule(myMove, this, 1);
		}
	}

}
