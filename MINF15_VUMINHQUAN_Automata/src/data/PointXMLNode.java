/**
 * 
 */
package data;

import java.awt.geom.Point2D;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


/**
 * @author VuMinhQuan
 *
 */
public class PointXMLNode {
	public static final String POSITON = "point";
	private static final String POSITION_X="x";
	private static final String POSITION_Y="y";
	
	public static Node setPositionNode(Document doc,Point2D point){
		Element ele = doc.createElement(POSITON);
		ele.setAttribute(POSITION_X, String.valueOf(point.getX()));
		ele.setAttribute(POSITION_Y, String.valueOf(point.getY()));
		return ele;
	}
	
	public static Point2D getPositionNode(Node node){
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) node;
			return new Point2D.Double(
					Double.parseDouble(eElement.getAttribute(POSITION_X)),
					Double.parseDouble(eElement.getAttribute(POSITION_Y)));	
		}
		return null;
	}
}
