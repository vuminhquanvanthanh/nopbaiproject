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
public class Edge {

	public static final int MAX_JOIN_POINT = 2;
	private Vertex source;
	private Vertex target;
	private List<Point2D> listJoinPoint;
	private String label;

	public Edge(List<Point2D> listPoint, Vertex source, Vertex target, String lbl) {
		super();
		this.listJoinPoint = listPoint;
		this.source = source;
		this.target = target;
		this.label = lbl;
	}

	public Edge(Vertex source, Vertex target, String lbl) {
		super();
		this.listJoinPoint = new ArrayList<Point2D>();
		this.source = source;
		this.target = target;
		this.label = lbl;
	}

	public Vertex getSource() {
		return source;
	}

	public void setSource(Vertex source) {
		this.source = source;
	}

	public Vertex getTarget() {
		return target;
	}

	public void setTarget(Vertex target) {
		this.target = target;
	}

	public List<Point2D> getListJoinPoint() {
		return listJoinPoint;
	}

	public void setListJoinPoint(List<Point2D> listJoinPoint) {
		this.listJoinPoint = listJoinPoint;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void addJoinPoint(Point2D jpoint) {
		listJoinPoint.add(jpoint);
	}

	@Override
	public String toString() {
		System.out.println("=============================");
		System.out.println(source.toString());
		System.out.println(target.toString());
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Edge getEdge(CoordinateTransition transition) {
		Edge ed = new Edge(null, null, "");

		if (transition != null) {
			ed.setSource(new Vertex(transition.getSourceState()));
			ed.setTarget(new Vertex(transition.getTargetState()));
			ed.setLabel((String) transition.getLabel());
			ed.setListJoinPoint(transition.getListPoint());
		}
		return ed;
	}

	public boolean checkPointInEdge(Point2D p) {
		if (source.checkInVertex(p) || target.checkInVertex(p))
			return true;
		for (Point2D point : listJoinPoint)
			if (point.equals(p))
				return true;
		return false;
	}

	public Point2D checkJoinPoint(Point2D point) {
		for (Point2D p : listJoinPoint)
			if (p.getX() - 5 <= point.getX() && point.getX() <= p.getX() + 5 && p.getY() - 5 <= point.getY()
					&& point.getY() <= p.getY() + 5)
				return p;
		return null;

	}

	public void drawEdge(Graphics g, Color color1, Color color2) {
		if (source != null && listJoinPoint.size() > 0) {

			Point2D p1 = source.getPostion();
			Point2D p2 = new Point2D.Double(listJoinPoint.get(0).getX(), listJoinPoint.get(0).getY());

			g.setColor(color1);
			source.drawVertex(g, color1);

			g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());

			g.setColor(color2);
			g.fillOval((int) p2.getX() - 5, (int) p2.getY() - 5, 10, 10);
			for (int i = 1; i < listJoinPoint.size(); i++) {

				p1 = p2;
				p2 = new Point2D.Double(listJoinPoint.get(i).getX(), listJoinPoint.get(i).getY());
				g.setColor(color1);
				Draw.drawArrow(g, (int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY(), label);

				g.setColor(color2);
				g.fillOval((int) p2.getX() - 5, (int) p2.getY() - 5, 10, 10);
			}
			if (target != null) {
				p1 = p2;
				g.setColor(color2);
				g.fillOval((int) p1.getX() - 5, (int) p1.getY() - 5, 10, 10);
				p2 = target.getPostion();
				g.setColor(color1);
				g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
				target.drawVertex(g, color1);

			}
		}
	}

}
