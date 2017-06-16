package starwars;

/**
 * All SWDroid and SWLegendDroid in the starwars client package should implement this interface.
 * 
 * @author Kaiwei Luo
 *
 */
public interface SWDroidInterface extends SWEntityInterface{
    /**
     * Returns the DroidStatus of this SWDroid, which is useful in deciding if this SWDroid
     * can Move or be Attacked etc.
     * 
     * @return DroidStatus of this SWDroid
     */
	public abstract DroidStatus getStatus();
	
	/**
	 * Set owner of this SWDroid, which is useful becasue during the game, 
	 * it is common to change a SWDroid's owner and be default, no SWDroid has
	 * owner at the start of this game
	 * 
	 * @param owner  owner of this SWDroid and a SWDroid can only has one owner
	 */
	public abstract void setOwner(SWActor owner);
	
	/**
	 * Set the DroidStatus of this SWDroid, which is useful becasue when
	 * a SWDroid is being disassembled or fixed, we want to change its status
	 * 
	 * 
	 * @param status  the new SWDroid's status
	 */
	public abstract void setStatus(DroidStatus status);
	
	/**
	 * Initialize the SWDroid's action list becasue this class extends SWActor and by default,
	 * SWActor will be added Attack Affordance, which is not needed when SWDroid's status is
	 * PART or IMMOBILE because a part of a SWDroid or an immobile SWDroid is unattackable
	 * 
	 * FUNCRIONAL SWDroid: Can be owned by SWActor
	 * 
	 * IMMOBILE SWDRoid: Can be fixed or disassembled by SWActor. Cannot be attacked.
	 * 
	 * 
	 * @param status  New DroidStatus which we want this SWDroid to have
	 */
	public abstract void initializeStatusAndAffordance(DroidStatus status);
	
	/**
	 * Restore SWDoird's hitpoints to defaultHitpoint, which is useful when being repaired by DroidPart.
	 */
	public abstract void resetHitpoints();
	
	/**
	 * 
	 * @return true if SWDroid is IMMOBILE and false otherwise
	 */
	public abstract boolean isImmobile();
	
	/**
	 * 
	 * @return true if SWDroid is FUNCTIONAL and false otherwise
	 */
	public abstract boolean isFunctional();
	
	/**
	 * 
	 * @return true if SWDroid has owner and false otherwise
	 */
	public abstract boolean hasOwner();
	
	/**
	 * 
	 * @return SWDroid's type
	 */
	public abstract String getType();
	
	/**
	 * A method provided for initializing SWDroid's ownerPath, which is used to get direction of SWDroid's owner and 
	 * SWDroid uses this direction to schedule a Move to follow its owner 
	 */
	public abstract void setPath();
	
	/**
	 * A wrapper class for scheduling a Move which follows SWDroid's owner
	 */
	public abstract void followOwner();
	
	/**
	 * 
	 * @return true if SWDroid is in badland and false otherwise
	 */
	public abstract boolean inBadLand();
	
	/**
	 * Reduces SWDroid's health if SWDroid is in badland
	 */
	public abstract void reduceHealthIfInBadLand();
	
	/**
	 * 
	 * @return true if SWDroid has an owner and false otherwise
	 */
	public abstract boolean canFollowOwner();
}
