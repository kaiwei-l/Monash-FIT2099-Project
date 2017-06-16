package starwars.entities.actors;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.entities.actors.behaviors.AttackInformation;

public class MonMothma extends SWActor {
	private String name;

	public MonMothma(String name, MessageRenderer m, SWWorld world) {
		super(Team.GOOD, 50, m, world);
		this.name = name;
	}

	@Override
	public void act() {  //give affordance
		// if Luke not accompanied by both Lean and R2-D2 need to add this condition
		SWLocation location = world.getEntityManager().whereIs(this);
		EntityManager<SWEntityInterface, SWLocation> em = world.getEntityManager();
		List<SWEntityInterface> entities = em.contents(location);
		
		boolean checkPlayer = false;
		boolean checkR2D2 = false;
		boolean checkLea = false;
		for (SWEntityInterface e : entities) {
			
			checkPlayer = isPlayerPresent(e);
			checkR2D2 = isR2D2Present(e);
            checkLea = isPrincessPresent(e);
				
		 }
		if(checkPlayer){
		    if((checkR2D2) && checkLea){
		    }else{
		    	say(getShortDescription() + " is saying, 'What are you doing here farmboy? "
						+ "Bring us General Organa and the plans!'");
		    }
		}
		return;
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
	
	public boolean isPlayerPresent(SWEntityInterface a){
		boolean check = false;
		if(a instanceof Player){
			check =  true;
		}
		return check;
	}
	public boolean isR2D2Present(SWEntityInterface a){
		boolean check = false;
		if(a instanceof R2D2){
			check =  true;
		}
		return check;
	}
	
	public boolean isPrincessPresent(SWEntityInterface a){
		boolean check = false;
		if(a instanceof LeiaOrgana){
			check =  true;
		}
		return check;
	}

}
