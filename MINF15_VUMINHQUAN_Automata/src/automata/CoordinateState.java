package automata;

import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 * @author VuMinhQuan
 *
 */
public class CoordinateState extends State implements Serializable {

	/**
	 * Class is used to defined vertice in automaton with coordinates
	 */
	private static final long serialVersionUID = 2080963032424636791L;
	Point2D coordinate;

	public Point2D getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Point2D coordinate) {
		this.coordinate = coordinate;
	}

	public CoordinateState(String label, boolean init, boolean end) {
		super(label, init, end);
		this.coordinate = null;
	}

	public CoordinateState(String label, boolean init, boolean end, Point2D coordinate) {
		super(label, init, end);
		this.coordinate = coordinate;
	}
}
