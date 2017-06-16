package edu.monash.fit2099.win.lose;

import java.util.ArrayList;

import edu.monash.fit2099.simulator.matter.Actor;
import starwars.SWActor;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.entities.actors.DarthVader;
import starwars.entities.actors.LeiaOrgana;
import starwars.entities.actors.MonMothma;
import starwars.entities.actors.Player;
import starwars.entities.actors.R2D2;


/**
 * This Class handles the games win or loose mechanism by checking the winning and loosing 
 * conditions by using boolean methods and return the appropriate boolean accordingly.
 * This class contains static methods so they can be called from anywhere within the application.
 * 
 * @author Rashfa Moosa
 */
public class WinOrLose {
	
		   
	 /**
	  * This method checks the winning condition and return a boolean accordingly
	  * 
      * @return true if either of the conditions are met, that is if <Code>Player<Code> takes
      *         <Code>LeiaOrgana<Code> and <Code>R2D2Legend<Code> to Yavin IV or if the 
	  *         <Code>DarthVader<Code> is dead.
	  *         false if not
      */
   public static boolean win(){
	  
	   //local variables
	   boolean check = false;
	   SWWorld lukeWorld = null;
	   SWWorld leiaWorld = null;
	   SWWorld r2d2World =  null;
	   SWLocation playerLocation = null;
	   SWLocation leiaLocation = null;
	   SWLocation r2d2Location = null;
	   SWLocation monMothmaLocation = null;
	   
	   //to get all the actors in the SWWorld
	   ArrayList<Actor> allActors = SWWorld.getActors();
	   for (Actor a : allActors) {
		   SWActor actor = (SWActor) a;
		   
		   
		   if(actor instanceof Player){
			   lukeWorld = actor.getSWWorld();
			   playerLocation = lukeWorld.getEntityManager().whereIs(actor);
				 
		   }else if(actor instanceof LeiaOrgana){
				 leiaWorld = actor.getSWWorld();
				 leiaLocation = leiaWorld.getEntityManager().whereIs(actor);
		   }else if(actor instanceof R2D2){
				 r2d2World = actor.getSWWorld();
				 r2d2Location = r2d2World.getEntityManager().whereIs(actor);
		   }else if (actor instanceof MonMothma){
			   monMothmaLocation = ((MonMothma)actor).getSWWorld().getEntityManager().whereIs(actor);
		   }
		   else if(actor instanceof DarthVader)
		   {
			   if(actor.isDead())
			   {
				   check = true;
			   }
		   }
	   }

	   if((playerLocation == monMothmaLocation) &&  ( leiaLocation == monMothmaLocation)) {
			 check = true;
		 }

	   return check;
   }
   
	/**
	 *  This method checks the loosing condition and return a boolean accordingly
     *  
     * @return true if either of the conditions are met, that is if <Code>Player<Code> is dead or
	 *         if <Code>LeiaOrgana<Code> is dead or if <Code>R2D2Legend<Code> is disassembled or if the 
     *         <Code>DarthVader<Code> turns <Code>Player<Code> dark.
	 *         false if not
	 */
   public static boolean loose(){ 
	   //class level variables
	   boolean check = false;
	   
	   boolean R2D2IsDead = true;
	   
	   //to get all the SWActors created in the SWWorld
	   ArrayList<Actor> allActors = SWWorld.getActors();
	   for (Actor a : allActors) {
	   SWActor actor = (SWActor) a;
	   
	   //check if Player is dead
	   if(actor instanceof Player){
			if(actor.isDead() || ((Player)actor).getTeam() == Team.EVIL){
				check = true;
			}
		}
	   
	   //check if LeiaOrgana is dead
	   if(actor instanceof LeiaOrgana){
			if(actor.isDead()){
				check = true;
			}
		}
	   
	   //check if R2D2 is disassembled
	   if(actor instanceof R2D2){
			R2D2IsDead = false;
			}
		}
	   return check;
	}
	   
   

   
   /**
    * Displays a String saying "YOU WON" to console to let the player know they won
    */
   public static void displayWin(){
	   System.out.println("YOU WON");
   }
   
   
   /**
    * Displays a String saying "GAMEOVER! YOU LOST" to console to let the player know they lost
    */
   public static void displayLoose(){
	   
	   System.out.println("GAMEOVER! YOU LOST");;
   }

	
    

}
