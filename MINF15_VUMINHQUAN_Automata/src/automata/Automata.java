package automata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Observer;

/**
 * @author VuMinhQuan
 *
 */
public class Automata implements Serializable {

	private static final long serialVersionUID = -3931561791317085523L;

	HandleObserverable observerable;
	private CoordinateState initialState = null;

	@SuppressWarnings("rawtypes")
	private final Map<CoordinateState, Map<Object, CoordinateTransition>> transitions;

	@SuppressWarnings("rawtypes")
	public Automata(CoordinateTransition<?>... transitions)
			throws NotDeterministTransitionException, UnknownInitialStateException, NotDeterministInitalStateException {
		observerable = new HandleObserverable();
		this.transitions = new HashMap<CoordinateState, Map<Object, CoordinateTransition>>();
		for (CoordinateTransition<?> t : transitions) {

			if (transition(t.getSourceState(), t.getLabel()) != null)
				throw new NotDeterministTransitionException(t, transition(t.getSourceState(), t.getLabel()));

			addState(t.getSourceState());// state source of transition
			addState(t.getTargetState());// state end of transition
			Map<Object, CoordinateTransition> map = this.transitions.get(t.getSourceState());
			map.put(t.getLabel(), t);// set label for transition
		}
		if (initialState == null)
			throw new UnknownInitialStateException();
	}

	public CoordinateState initialState() {
		return this.initialState;
	}

	public void setInitialState(CoordinateState st) {
		this.initialState = st;
	}

	public Transition<?> transition(State s, Object label) {
		try {
			if (!transitions.containsKey(s))
				throw new NoSuchElementException();
			return transitions.get(s).get(label);
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public Map<Object, CoordinateTransition> transitionByState(CoordinateState s) {
		try {
			if (!transitions.containsKey(s))
				throw new NoSuchElementException();
			return transitions.get(s);
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	protected final void addState(CoordinateState s)
			throws UnknownInitialStateException, NotDeterministInitalStateException {
		if (!transitions.containsKey(s)) {
			transitions.put(s, new HashMap<Object, CoordinateTransition>());
			if (s.isInit()) {
				if (initialState == null) {
					initialState = s;
				}
			}
		}
	}

	public boolean recognize(Object[] word) throws NotDeterministTransitionException {
		try {
			Iterator<Object> it = Arrays.asList(word).iterator();
			return recognize(it);
		} catch (Exception e) {

		}
		return false;
	}

	public boolean recognize(Iterator<Object> word) throws NotDeterministTransitionException {
		try {
			State source = initialState();
			Transition<?> t = null;
			while (word.hasNext()) {
				t = transition(source, word.next());
				source = t.getTargetState();
				handleRecognize(t);
			}

			if (source.isEnd())
				return true;
		} catch (Exception e) {

		}
		return false;

	}

	@Override
	public String toString() {
		transitions.toString();
		return "";
	}

	@SuppressWarnings("rawtypes")
	public List<CoordinateTransition> getAllTransitions() {
		CoordinateState source = initialState;
		List<CoordinateTransition> list = new ArrayList<CoordinateTransition>();
		list.addAll(transitionByState(source).values());

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getSourceState() != source)
				source = list.get(i).getSourceState();
			if (list.get(i).getTargetState() != source)
				source = list.get(i).getTargetState();
			if (transitionByState(source).values().size() > 0) {
				for (CoordinateTransition t1 : transitionByState(source).values()) {
					if (!list.contains(t1))
						list.add(t1);
				}
			}
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	public static List<CoordinateState> getAllStates(List<CoordinateTransition> listTrans) {
		List<CoordinateState> list = new ArrayList<CoordinateState>();
		for (CoordinateTransition cTransition : listTrans) {
			if (!list.contains(cTransition.getSourceState()))
				list.add(cTransition.getSourceState());
			if (!list.contains(cTransition.getTargetState()))
				list.add(cTransition.getTargetState());
		}
		return list;
	}

	public void addObserver(Observer ob) {
		observerable.addObserver(ob);
	}

	private void handleRecognize(Transition<?> t) {
		observerable.notifyObservers(t);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
