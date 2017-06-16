package starwars.entities.actors;

//import java.util.ArrayList;


//import edu.monash.fit2099.gridworld.Grid;
//import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
//import starwars.Capability;
import starwars.ForceAbility;
import starwars.SWActor;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.MindControl;

//import starwars.actions.Move;
//import starwars.entities.LightSaber;
//import starwars.entities.actors.behaviors.AttackInformation;
//import starwars.entities.actors.behaviors.AttackNeighbours;
//import java.util.HashSet;
/**
* 
* @author Rashfa Moosa
*/

public class Humanoid extends SWActor {
	
	private String name;

	/**
	 * Create Humanoid
	 * 
	 * @param hitpoints
	 *            the number of hit points for this Humanoid
	 * @param name
	 *            Human's name. Used in displaying descriptions.
	 * @param m
	 *            <code>MessageRenderer</code> to display messages.
	 * @param world
	 *            the <code>SWWorld</code> world to which 
	 *            <code>Humanoid</code> belongs to
	 * 
	 */
	public Humanoid(int hitpoints, String name, MessageRenderer m, SWWorld world) {
		super(Team.GOOD, 50, m, world ); 
		this.forceAbility = ForceAbility.LEVEL0;
		this.name = name;
		
		//more affordance
		this.addAffordance(new MindControl(this,m)); 
		
	}
	
	
	@Override
	public void act() {
		if (isDead()) {
			return;
		}
		say(describeLocation());
		
		//can be mind controlled, cannot move on their own 
	}

	@Override
	public String getShortDescription() {
		return name + " the Humaoid";
	}

	@Override
	public String getLongDescription() {
		return this.getShortDescription();
	}

	private String describeLocation() {
		SWLocation location = this.world.getEntityManager().whereIs(this);
		return this.getShortDescription() + " [" + this.getHitpoints() + "] is at " + location.getShortDescription();

	}


}
