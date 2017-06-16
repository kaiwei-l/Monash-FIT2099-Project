package starwars.entities.actors;

import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.ForceAbility;
import starwars.SWActor;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.SaveLeia;
import starwars.actions.Move;
import starwars.entities.actors.behaviors.Follow;


/**
 * Create Princess Leia Organa.  Leia Organa will stay in the inital position without
 * any movement until Player reaches the same location and use <Code>SaveLeia<Code> affordance
 * on her. Then Leia Organa will follow the Player.
 * 
 * 
 * @param hitpoints
 *            the number of hit points of LeiaOrgana. If this
 *            decreases to below zero, the LeiaOrgana will die.
 * @param name
 *            LeiaOrgana's name. Used in displaying descriptions.
 * @param m
 *            <code>MessageRenderer</code> to display messages.
 * @param world
 *            the <code>SWWorld</code> world to which this
 *            <code>LeiaOrgana</code> belongs to
 *            
 *@author Rashfa Moosa
 * 
 */
public class LeiaOrgana extends SWActor{
	private SWWorld world;
	private SWActor follower;
	protected Follow followerPath; 
	private String name;
	
	//constructor for this class
	public LeiaOrgana( int hitpoints, MessageRenderer m, SWWorld world) {
		super(Team.GOOD , hitpoints, m, world);
		this.name = "Princess Leia Organa";
		this.forceAbility= ForceAbility.LEVEL2;
		this.follower = null;
		this.world = world;
		this.setSymbol("PP");
		this.addAffordance(new SaveLeia(this, messageRenderer));
		
	}
	
	/**
	 * Allows an <Code>SWActor<Code> to be set as follower
	 * @param follower an <Code>SWActor<Code>
	 */
	public void setFollower(SWActor follower)
	{
		this.follower = follower;
		this.say("setFollower Succeeded");
	}
	
	/**
	 * Checks if <Code>LeiaOrgana<Code> has a follower
	 * @return true if <Code>LeiaOrgana<Code> has a follower
	 *         false in every other case
	 */
	public boolean hasFollower()
	{
		if(this.follower != null)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Allows <Code>Follow<Code> affordance to be used if <Code>LeiaOrgana<Code> has a path
	 */
	public void setPath()
	{
		if(this.hasFollower())
		{
			this.followerPath = new Follow(this.follower, this, this.world);
		}
		
	}
	
	/**
	 * Checks if <Code>LeiaOrgana<Code> has a follower and if <Code>LeiaOrgana<Code> is not dead
	 * and return a boolean accordingly
	 * @return true if if <Code>LeiaOrgana<Code> has a follower and if <Code>LeiaOrgana<Code> is not dead
	 *         false otherwise
	 */
	public boolean canFollowFollower()
	{
		if(this.hasFollower() == true && this.isDead() != true)
		{
			return true;
		}
		return false;
	}
	
	
	/**
	 * Creates Direction and use the schedular so that if <Code>LeiaOrgana<Code> can follow the follower
	 */
	public void followFollower()
	{
		this.say("check");
		this.setPath();
		Direction nextDirection = followerPath.getDirection();
		Move nextMove = new Move(nextDirection, messageRenderer, world);
		
		scheduler.schedule(nextMove, this, 1);
	}
	
	
	/**
	 * Allows the <code>LeiaOrgana<Code> to perform an action if not dead, in this case allows the player to follow the follower 
	 */
	@Override
	public void act() {
		if (isDead()) {
			return;
		}
		say(describeLocation());
		
		if(this.canFollowFollower())
		{
			this.followFollower();
			return;
		}
	}
	
	/**
	 * A short description about <code>LeiaOrgana<Code> consisting name suitable to be displayed in console
	 */
	@Override
	public String getShortDescription() {
		return name;
	}
    
	/**
	 * A detailed description about <code>LeiaOrgana<Code> consisting name suitable to be displayed in console
	 */
	@Override
	public String getLongDescription() {
		return this.getShortDescription();
	}
    
	/**
	 * A description about the status of <code>LeiaOrgana<Code> including location and Hitpoints
	 * @return a string containing a description about f <code>LeiaOrgana<Code> including location and Hitpoints
	 */
	private String describeLocation() {
		SWLocation location = this.world.getEntityManager().whereIs(this);
		return this.getShortDescription() + " [" + this.getHitpoints() + "] is at " + location.getShortDescription();

	}
}
