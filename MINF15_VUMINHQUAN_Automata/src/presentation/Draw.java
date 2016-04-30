package presentation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

/**
 * @author VuMinhQuan
 *
 */
public class Draw {

	public static final int SIZE = 30;

	public static RectangularShape drawVertex(Graphics2D g, Point2D location, VertexType type, String label,
			Color color) {
		Ellipse2D ellip = (Ellipse2D) drawVertex(g, location, type);

		g.setColor(color);
		g.setFont(new Font(Font.SERIF, Font.BOLD, 15));
		g.drawString(label, (int) (location.getX() - 3), (int) (location.getY() + 5));
		return ellip;
	}

	public static RectangularShape drawVertex(Graphics2D g, Point2D location, VertexType type) {
		Ellipse2D ellip = new Ellipse2D.Double(location.getX(), location.getY(), SIZE, SIZE);
		g.setColor(Color.RED);
		if (type == VertexType.INIT) {
			drawArrow(g, (int) location.getX() - 30, (int) location.getY() - 30, (int) location.getX(),
					(int) location.getY());
		}
		if (type == VertexType.END) {
			drawArrow(g, (int) location.getX() + 10, (int) location.getY() + 10, (int) location.getX() + 30,
					(int) location.getY() + 30);
		}
		if (type == VertexType.INIT_AND_END) {
			drawArrow(g, (int) location.getX() - 30, (int) location.getY() - 30, (int) location.getX(),
					(int) location.getY());
			drawArrow(g, (int) location.getX() + 10, (int) location.getY() + 10, (int) location.getX() + 30,
					(int) location.getY() + 30);
		}
		// ve tai tam cua hinh tron
		g.drawOval((int) (location.getX() - ellip.getWidth() / 2), (int) (location.getY() - ellip.getWidth() / 2),
				(int) ellip.getWidth(), (int) ellip.getHeight());
		g.setColor(Color.YELLOW);
		g.fillOval((int) (location.getX() - ellip.getWidth() / 2), (int) (location.getY() - ellip.getWidth() / 2),
				(int) ellip.getWidth(), (int) ellip.getHeight());

		return ellip;
	}

	public static void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
		Graphics2D g = (Graphics2D) g1.create();

		double dx = x2 - x1, dy = y2 - y1;
		double angle = Math.atan2(dy, dx);
		int len = ((int) Math.sqrt(dx * dx + dy * dy));

		AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
		at.concatenate(AffineTransform.getRotateInstance(angle));
		g.transform(at);

		// Draw horizontal arrow starting in (0, 0)
		g.drawLine(0, 0, len, 0);
		g.fillPolygon(new int[] { len / 2, (len / 2) - 8, (len / 2) - 8, len / 2 }, new int[] { 0, -8, 8, 0 }, 4);
	}

	public static void drawArrow(Graphics g1, int x1, int y1, int x2, int y2, String label) {
		drawArrow(g1, x1, y1, x2, y2);

		((Graphics2D) g1).setFont(new Font(Font.SERIF, Font.BOLD, 15));
		((Graphics2D) g1).drawString(label, ((x2 + x1) / 2) + 5, ((y2 + y1) / 2) - 5);
	}
}
