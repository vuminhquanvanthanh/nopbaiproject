package automata;
import java.io.Serializable;

/**
 * @author VuMinhQuan
 *
 * 
 */
public class Transition<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7204570390080620572L;
	private CoordinateState sourceState;
	private CoordinateState targetState;
	private T label;

	public Transition(CoordinateState sourceState, CoordinateState targetState, T label) {
		this.sourceState = sourceState;
		this.targetState = targetState;
		this.label = label;
	}

	public CoordinateState getSourceState() {
		return sourceState;
	}

	public void setSourceState(CoordinateState sourceState) {
		this.sourceState = sourceState;
	}

	public CoordinateState getTargetState() {
		return targetState;
	}

	public void setTargetState(CoordinateState targetState) {
		this.targetState = targetState;
	}

	public T getLabel() {
		return label;
	}

	public void setLabel(T label) {
		this.label = label;
	}

	@Override
	public String toString() {
		System.out.println(sourceState.getLabel() + " - " + label + " - " + targetState.getLabel());
		return "";
	}
}
