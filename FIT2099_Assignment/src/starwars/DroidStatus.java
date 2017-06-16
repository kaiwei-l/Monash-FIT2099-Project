package starwars;
/**
 * Status that various droids may have. This is useful in canDo() method
 * of several actions class
 * 
 * @author Kaiwei Luo
 */
public enum DroidStatus {
	FUNCTIONAL,  // Functional Droid can do whatever an SWActor do
	IMMOBILE,  // Immobile Droid cannot move or follow and can be fixed or 
	          // disassembled
}
