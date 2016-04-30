package presentation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;

import automata.CoordinateTransition;

/**
 * @author VuMinhQuan
 *
 */
@SuppressWarnings("serial")
public class GraphComponent extends JComponent implements Observer {

	ListVertex listVertex;
	ListEdge listEdge;
	MouseListen mListen;
	VertexType typeVertex;

	Edge recognizeEdge;
	Edge currentEdge;
	Vertex currentVertex;
	Point2D currentPoint;

	public GraphComponent() {
		listVertex = new ListVertex();
		listEdge = new ListEdge();
		currentEdge = new Edge(null, null, "");
		currentVertex = null;
		currentPoint = null;
		typeVertex = null;

		mListen = new MouseListen(this);

		addMouseListener(mListen);
		addMouseMotionListener(mListen);
	}

	@Override
	public void paint(Graphics arg0) {
		super.paint(arg0);

		arg0.setColor(Color.BLUE);
		if (currentPoint != null && currentVertex != null) {// ve duong thang
															// tam tu mot hinh
			Draw.drawArrow(arg0, (int) currentVertex.getPostion().getX(), (int) currentVertex.getPostion().getY(),
					(int) currentPoint.getX(), (int) currentPoint.getY());
		}

		arg0.setColor(Color.BLUE);
		if (listEdge != null)
			listEdge.drawListEdge(arg0);

		if (listVertex != null)
			listVertex.drawListVertex(arg0);
		if (recognizeEdge != null) {
			arg0.setColor(Color.RED);
			recognizeEdge.drawEdge(arg0, Color.RED, Color.RED);
		}
	}

	public void addJointPoint() {
		if (currentPoint != null && currentEdge != null) {
			Vertex ver = currentVertex;
			if (currentEdge.checkJoinPoint(currentPoint) == null
					&& currentEdge.getListJoinPoint().size() < Edge.MAX_JOIN_POINT) {
				ver = new Vertex(currentPoint);
				currentEdge.addJoinPoint(currentPoint);
			}
			currentVertex = ver;
			repaint();
		}
	}

	public void deleteVertex() {
		Vertex shape = listVertex.checkPointInList(currentPoint);
		if (shape != null) {
			if (listEdge.findEdge(shape) != null) {
				listEdge.removeAll(listEdge.findEdge(shape));
			}
			listVertex.removeVertex(shape);
		} else {// check joinpoint
			if (listEdge.findEdge(currentPoint) != null) {
				listEdge.removeEdge(listEdge.findEdge(currentPoint));
			}
		}
		currentPoint = null;
		repaint();
	}

	public void addLabelVertex(int label) {
		if (currentVertex != null) {
			currentVertex.getInfo().setLabel(String.valueOf(label));
			repaint();
		}
	}

	public void addLabelEdge(String keyText) {
		if (currentEdge != null) {
			currentEdge.setLabel(keyText);
			repaint();
		}

	}

	@SuppressWarnings("rawtypes")
	@Override
	public void update(Observable o, Object arg) {
		CoordinateTransition transition = (CoordinateTransition) arg;
		recognizeEdge = currentEdge.getEdge(transition);

		repaint();
	}

}
