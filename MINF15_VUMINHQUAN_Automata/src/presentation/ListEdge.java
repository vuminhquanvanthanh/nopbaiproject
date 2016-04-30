package presentation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import automata.CoordinateTransition;

/**
 * @author VuMinhQuan
 *
 */
public class ListEdge {
	List<Edge> listEdge;

	public ListEdge() {
		listEdge = new ArrayList<Edge>();
	}

	public void addEdge(Edge ed) {
		listEdge.add(ed);
	}

	public List<Edge> getListEdge() {
		return listEdge;
	}

	public void setListEdge(List<Edge> listEdge) {
		this.listEdge = listEdge;
	}

	public void removeEdge(Edge ed) {
		listEdge.remove(ed);
	}

	public void removeAll() {
		listEdge.clear();
	}

	public void removeAll(List<Edge> lEdge) {
		listEdge.removeAll(lEdge);
	}

	public Edge checkVertex(Point2D point) {
		for (Edge ed : listEdge)
			if (ed.checkPointInEdge(point))
				return ed;
		return null;
	}

	public Edge checkJoinPoint(Point2D point) {
		for (Edge ed : listEdge)
			if (ed.checkJoinPoint(point) != null)
				return ed;
		return null;
	}

	public void drawListEdge(Graphics g) {
		for (Edge ed : listEdge)
			ed.drawEdge(g, Color.BLUE, Color.RED);
	}

	public int getSize() {
		return listEdge.size();
	}

	public Edge getIndex(int index) {
		if (listEdge != null)
			return listEdge.get(index);
		return null;
	}

	public List<Edge> findEdge(Vertex shape) {
		List<Edge> listKQ = new ArrayList<>();
		for (Edge ed : listEdge)
			if (ed.getSource().equals(shape) || ed.getTarget().equals(shape))
				listKQ.add(ed);
		return listKQ;
	}

	public Edge findEdge(Vertex source, Vertex target) {
		for (Edge ed : listEdge)
			if (ed.getSource().equals(source) && ed.getTarget().equals(target))
				return ed;
		return null;
	}

	/**
	 * Find edge by join point
	 * 
	 * @param point
	 * @return edge
	 */
	public Edge findEdge(Point2D point) {
		for (Edge ed : listEdge) {
			if (ed.checkJoinPoint(point) != null)
				return ed;
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ListEdge generalListEdge(List<CoordinateTransition> listTransitions) {
		ListEdge list = new ListEdge();
		ListVertex listV = new ListVertex();

		for (CoordinateTransition t : listTransitions) {
			Vertex vSource = listV.getVertex(t.getSourceState());
			Vertex vTarget = listV.getVertex(t.getTargetState());
			if (list.findEdge(vSource, vTarget) == null) {
				Edge ed = new Edge(vSource, vTarget, t.getLabel().toString());
				if (t.getListPoint() != null) {
					ed.setListJoinPoint(t.getListPoint());
				}
				list.addEdge(ed);
			}
		}
		return list;

	}
}
