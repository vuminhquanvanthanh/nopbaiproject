package automata;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.List;

/**
 * @author VuMinhQuan
 *
 * @param <T>
 */
public class CoordinateTransition<T> extends Transition<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4136950566396679791L;
	List<Point2D> listPoint;

	public List<Point2D> getListPoint() {
		return listPoint;
	}

	public void setListPoint(List<Point2D> listPoint) {
		this.listPoint = listPoint;
	}

	public CoordinateTransition(CoordinateState sourceState, CoordinateState targetState, T label) {
		super(sourceState, targetState, label);

	}

	public CoordinateTransition(CoordinateState sourceState, CoordinateState targetState, T label,
			List<Point2D> listPoint) {
		super(sourceState, targetState, label);
		this.listPoint = listPoint;
	}
}
