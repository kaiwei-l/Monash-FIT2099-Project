package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWEntity;
import starwars.actions.Drink;
import starwars.actions.Take;


/**
 * An OilCan that can contain Oil.
 * OilCan can be used on Droids for Healing
 * @author Rashfa Moosa
 */

public class OilCan extends SWEntity {
	
	public OilCan(MessageRenderer m)  {
		super(m);
		this.shortDescription = "oil can ";
		this.longDescription = " oil which can be used infinite times is ";
		this.addAffordance(new Take(this, m));
		this.addAffordance(new Drink(this, m));
		
		this.capabilities.add(Capability.DRINKABLE); 
		this.capabilities.add(Capability.HEAL);

	}

	
	
	
	
	@Override 
	public String getShortDescription() {
		return shortDescription;
	}
	
	@Override
	public String getLongDescription () {
		return longDescription;
	}

	public int getCapacity() {
		// TODO Auto-generated method stub
		return 0;
	}
}
