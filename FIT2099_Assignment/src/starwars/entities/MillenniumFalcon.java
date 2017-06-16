package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWEntity;

/**
 * Millennium Falcon space ship, which can have multiple Transport affordance 
 * during initializing SWWorld
 */
public class MillenniumFalcon extends SWEntity{
	
	public MillenniumFalcon(MessageRenderer m)
	{
		super(m);
		
		this.shortDescription = "Millennium Falcon";
		this.longDescription = "Millennium Falcon. A space ship";
		this.hitpoints = 200000;
	}
	
	/**
	 * no one can harm Millennium Falcon
	 */
	@Override 
	public void takeDamage(int damage)
	{
		return;
	}
	
	@Override
	public String getSymbol()
	{
		return "M-F";
	}
	
	

}
