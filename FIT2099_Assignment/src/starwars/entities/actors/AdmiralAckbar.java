package starwars.entities.actors;

import java.util.Random;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;

public class AdmiralAckbar extends SWActor {
	private String name;

	public AdmiralAckbar(String name, MessageRenderer m, SWWorld world) {
		super(Team.GOOD, 50, m, world);
		this.name = name;
	}

	@Override
	public void act() {
		//this event occurs 10% of the time
		Random r = new Random();
		float chance = r.nextFloat();

		  if (chance <= 0.10f){
			  say(getShortDescription() + " is saying, ' It's a trap!'");
		  }
	}
	
	@Override
	public String getShortDescription() {
		return name ;
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
