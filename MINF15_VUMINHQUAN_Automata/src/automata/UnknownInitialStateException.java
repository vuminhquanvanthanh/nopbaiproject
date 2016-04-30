/**
 * 
 */
package automata;

/**
 * @author VuMinhQuan
 *
 */
public class UnknownInitialStateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2223511483504094331L;

	public UnknownInitialStateException() {
		super("Automata Unknown inital state ==");
	}

	public UnknownInitialStateException(Automata dt) {
		super("Automata unknown inital state ==");
	}
}
