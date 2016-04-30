package automata;

import java.io.Serializable;

/**
 * @author VuMinhQuan
 *
 */
public class State implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9088452342858980988L;
	private String label;
	private boolean init;
	private boolean end;

	/**
	 * Constructor with State
	 * 
	 * @param label
	 *            label of State
	 * @param init
	 *            State is initial state
	 * @param end
	 *            State is terminal state
	 */
	public State(String label, boolean init, boolean end) {
		this.label = label;
		this.init = init;
		this.end = end;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isInit() {
		return init;
	}

	public void setInit(boolean init) {
		this.init = init;
	}

	public boolean isEnd() {
		return end;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}

	@Override
	public String toString() {
		System.out.println("State: " + label + ", " + init + ", " + end);
		return "";
	}

}
