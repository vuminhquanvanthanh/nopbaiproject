package automata;

import java.io.Serializable;
import java.util.Observable;

/**
 * @author VuMinhQuan
 *
 */
public class HandleObserverable extends Observable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6072422691959069435L;

	@Override
	public void notifyObservers(Object arg) {
		setChanged();
		super.notifyObservers(arg);
	}

}
