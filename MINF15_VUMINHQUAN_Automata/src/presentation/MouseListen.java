package presentation;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

/**
 * @author VuMinhQuan
 *
 */
public class MouseListen implements MouseInputListener {

	GraphComponent graph;
	Point2D currentPoint;
	Edge currentEdge;

	public MouseListen(GraphComponent graph) {
		super();
		this.graph = graph;
		currentPoint = null;
		currentEdge = null;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if (SwingUtilities.isLeftMouseButton(arg0)) {
			currentPoint = arg0.getPoint();

			Vertex v = graph.listVertex.checkPointInList(currentPoint);
			currentEdge = graph.listEdge.checkJoinPoint(currentPoint);
			if (v == null && currentEdge == null) {
				v = new Vertex(arg0.getPoint(), graph.typeVertex);
				graph.listVertex.addVertex(v);
				return;
			} else {
				if (currentEdge != null) {
					graph.currentEdge = currentEdge;
				}
			}

			graph.currentVertex = v;
			if (arg0.isAltDown()) {// nhan alt
				graph.currentEdge = new Edge(new ArrayList<Point2D>(), null, null, "");
				graph.currentEdge.setSource(v);
				if (graph.currentEdge.getSource() != null)
					graph.listEdge.addEdge(graph.currentEdge);
			}
			graph.repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		graph.currentPoint = arg0.getPoint();
		Vertex v = graph.listVertex.checkPointInList(arg0.getPoint());
		if (arg0.isAltDown()) {
			if (v != null) {
				if (graph.currentEdge.getSource() != null && graph.currentEdge.getListJoinPoint().size() > 0
						&& graph.currentEdge.getTarget() == null) {
					graph.currentEdge.setTarget(v);
				}
			} else {// khong hinh thanh edge
				graph.listEdge.removeEdge(graph.currentEdge);
			}

		}
		graph.currentVertex = v;
		graph.currentPoint = null;
		graph.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		double dx = arg0.getPoint().getX() - currentPoint.getX();
		double dy = arg0.getPoint().getY() - currentPoint.getY();
		if (!arg0.isAltDown()) {
			Point2D point = null;
			if (currentEdge != null)
				point = currentEdge.checkJoinPoint(currentPoint);
			currentPoint = arg0.getPoint(); // gan lai de cap nhat vi tri
			if (graph.currentVertex != null) {// re currentVertex
				graph.currentVertex.getPostion().setLocation(graph.currentVertex.getPostion().getX() + dx,
						graph.currentVertex.getPostion().getY() + dy);
			}
			if (currentEdge != null) {
				point.setLocation(point.getX() + dx, point.getY() + dy);
			}
		} else {
			graph.currentPoint = arg0.getPoint();
		}
		graph.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
