package presentation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import automata.CoordinateState;

/**
 * @author VuMinhQuan
 *
 */
public class Vertex {
	private Point2D position;
	private CoordinateState info;
	private VertexType typeVertex; // init, end, normal,...

	public Point2D getPostion() {
		return position;
	}

	public void setPostion(Point2D postion) {
		this.position = postion;
	}

	public CoordinateState getInfo() {
		this.info.setCoordinate(this.position);
		return info;
	}

	public void setInfo(CoordinateState info) {
		this.info = info;
	}

	private CoordinateState setInfo(VertexType tVertex) {
		CoordinateState t = new CoordinateState("", false, false); // mac dinh
																	// la normal
		// t.setCoordinate(coordinate)
		if (tVertex == VertexType.INIT) {
			t.setInit(true);
		} else {
			if (tVertex == VertexType.END) {
				t.setEnd(true);
			} else {
				if (tVertex == VertexType.INIT_AND_END) {
					t.setInit(true);
					t.setEnd(true);
				}
			}
		}
		return t;
	}

	public VertexType getTypeVertex() {
		return typeVertex;
	}

	public void setTypeVertex(VertexType typeVertex) {
		this.typeVertex = typeVertex;
	}

	public Vertex(Point2D position) {
		super();
		this.position = position;
		this.typeVertex = VertexType.NORMAL;
		this.info = setInfo(typeVertex);
	}

	public Vertex(Point2D position, VertexType typev) {
		super();
		this.position = position;
		this.typeVertex = typev;
		this.info = setInfo(typeVertex);
	}

	public Vertex(CoordinateState infoState) {
		this.position = infoState.getCoordinate();
		this.typeVertex = VertexType.NORMAL;
		if (infoState.isEnd() && infoState.isInit())
			this.typeVertex = VertexType.INIT_AND_END;
		else {
			if (infoState.isInit())
				this.typeVertex = VertexType.INIT;
			else {
				if (infoState.isEnd())
					this.typeVertex = VertexType.END;
			}
		}
		this.info = infoState;
	}

	public void drawVertex(Graphics g, Color color) {
		Draw.drawVertex((Graphics2D) g, position, typeVertex, info.getLabel(), color);

	}

	public boolean checkInVertex(Point2D p) {
		if (position.getX() - Draw.SIZE / 2 <= p.getX() && p.getX() <= position.getX() + Draw.SIZE / 2
				&& position.getY() - Draw.SIZE / 2 <= p.getY() && p.getY() <= position.getY() + Draw.SIZE / 2)
			return true;
		return false;
	}

	@Override
	public String toString() {
		System.out.println("Position: " + position.toString());
		info.toString();
		return "";
	}

}
