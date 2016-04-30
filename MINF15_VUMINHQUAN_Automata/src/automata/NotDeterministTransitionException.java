/**
 * 
 */
package automata;

/**
 * @author VuMinhQuan
 *
 */
public class NotDeterministTransitionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1691759035382470814L;

	public NotDeterministTransitionException() {
		super("Not determinist transition.");
	}

	public NotDeterministTransitionException(Transition<?> t1, Transition<?> t2) {
		super("Not determinist transition.");
	}

}
