package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWEntity;
import starwars.actions.Drink;
import starwars.actions.Fill;
import starwars.actions.Heal;
import starwars.actions.Take;

/**
 * A canteen that can be used to contain water.
 * 
 * It can be filled at a Reservoir, or any other Entity
 * that has a Dip affordance.
 * 
 * Please note that drinking from the canteen is currently 
 * unimplemented
 * 
 * 
 * @author Robert Merkel
 * 
 */
public class Canteen extends SWEntity implements Fillable {

	private int capacity;
	private int level;
	
	public Canteen(MessageRenderer m, int capacity, int initialLevel)  {
		super(m);
		assert(capacity < initialLevel): "initialLevel should be less than or equal to capability";
		
		this.shortDescription = "a canteen";
		this.longDescription = "a slightly battered aluminium canteen";
		
		this.capacity = capacity;
		this.level= initialLevel;
		this.capabilities.add(Capability.FILLABLE);
		this.capabilities.add(Capability.DRINKABLE);
		this.capabilities.add(Capability.HEAL);
		this.addAffordance(new Fill(this, m));
		this.addAffordance(new Drink(this, m));

	}

	public void fill() {
	
		level = capacity;
	}
	
	public boolean CanUseCanteen(){
		boolean check;
	
		if(capacity > 0){
			check = true;
		}
		else{
			check = false;
		}
		return check;
	}
	
	public int getCapacity(){
		return capacity;
	}
	
	//to change the capacity during heal
	public void setCapacity(int i){
		capacity = i;
	}
	
	@Override 
	public String getShortDescription() {
		return shortDescription + " [" + level + "/" + capacity + "]";
	}
	
	@Override
	public String getLongDescription () {
		return longDescription + " [" + level + "/" + capacity + "]";
	}
	
	public int getLevel()
	{
		return this.level;
	}
	
	public void setLevel(int newLevel)
	{
		if(newLevel < 0)
		{return;}
		
		this.level = newLevel;
	}
	
	public void reduceCurrentLevel()
	{
		this.setLevel(this.getLevel() - 1);
	}
	
	public boolean isEmpty()
	{
		return (this.level == 0);
	}
	
	public boolean isFull()
	{
		return (this.level == this.capacity);
	}
	
	public void use()
	{
		this.reduceCurrentLevel();
	}
}
