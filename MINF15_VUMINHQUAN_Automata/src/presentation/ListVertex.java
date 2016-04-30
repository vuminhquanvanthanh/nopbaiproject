package presentation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import automata.CoordinateState;
import automata.State;

/**
 * @author VuMinhQuan
 *
 */
public class ListVertex {
	List<Vertex> listVertex;

	public ListVertex(List<Vertex> listVertex) {
		this.listVertex = listVertex;
	}

	public ListVertex() {
		this.listVertex = new ArrayList<Vertex>();
	}

	public void addVertex(Vertex v) {
		listVertex.add(v);
	}

	public void removeVertex(Vertex v) {
		listVertex.remove(v);
	}

	public void removeAll() {
		listVertex.clear();
	}

	/**
	 * Get Vertex by State
	 * 
	 * @param state
	 * @return
	 */
	public Vertex getVertex(State state) {
		for (Vertex v : listVertex)
			if (v.getInfo().equals(state))
				return v;
		return new Vertex((CoordinateState) state);
	}

	/**
	 * Get vertex by position
	 * 
	 * @param point
	 * @return
	 */
	public Vertex getVertex(Point2D point) {
		for (Vertex v : listVertex)
			if (v.getPostion().equals(point))
				return v;
		return null;
	}

	public void drawListVertex(Graphics g) {
		for (Vertex v : listVertex)
			v.drawVertex(g, Color.BLUE);
	}

	public Vertex checkInList(Vertex v1) {
		for (Vertex v : listVertex)
			if (v.equals(v1))
				return v;
		return null;
	}

	public Vertex checkPointInList(Point2D p) {
		for (Vertex v : listVertex)
			if (v.checkInVertex(p))
				return v;
		return null;
	}

	public ListVertex getListVertexFromListEdge(ListEdge list) {
		ListVertex listV = new ListVertex();
		if (list != null) {
			for (Edge ed : list.getListEdge()) {
				if (listV.checkInList(ed.getSource()) == null)
					listV.addVertex(ed.getSource());
				if (listV.checkInList(ed.getTarget()) == null)
					listV.addVertex(ed.getTarget());
			}
		}
		return listV;
	}

}
