/**
 * 
 */
package data;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import automata.CoordinateState;
import automata.CoordinateTransition;

/**
 * @author VuMinhQuan
 *
 */
public class TransitionXMLNode {
	private static final String NODE_NAME = "transition";
	private static final String SOURCE = "source";
	private static final String TARGET = "target";
	private static final String LABEL = "label";
	private static final String LIST_POINT = "joinpoint";
	static List<CoordinateState> listState = new ArrayList<CoordinateState>();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Node setTransitionNode(Document doc, CoordinateTransition transition) {
		Element ele = doc.createElement(NODE_NAME);
		ele.setAttribute(SOURCE, transition.getSourceState().getLabel());
		ele.setAttribute(TARGET, transition.getTargetState().getLabel());
		ele.setAttribute(LABEL, transition.getLabel().toString());

		Element node = doc.createElement(LIST_POINT);
		List<Point2D> listPoint = transition.getListPoint();
		for (Point2D point : listPoint) {
			node.appendChild(PointXMLNode.setPositionNode(doc, point));
		}
		ele.appendChild(node);
		return ele;
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public static CoordinateTransition getTransitionNode(Document doc, Node transitionNode) {
		CoordinateTransition<String> coorTransition = new CoordinateTransition<String>(null, null, "");
		StateXMLNode stateXML = new StateXMLNode();

		if (transitionNode.getNodeType() == Node.ELEMENT_NODE) {

			Element eElement = (Element) transitionNode;
			coorTransition.setLabel(eElement.getAttribute(LABEL));
			CoordinateState stateSource = getState(listState, eElement.getAttribute(SOURCE));
			CoordinateState stateTarget = getState(listState, eElement.getAttribute(TARGET));
			if (stateSource == null) {
				stateSource = StateXMLNode.getStateNode(StateXMLNode.getNodeByLabel(doc, eElement.getAttribute(SOURCE)));
				listState.add(stateSource);
			}
			coorTransition.setSourceState(stateSource);
			if (stateTarget == null) {
				stateTarget = StateXMLNode.getStateNode(StateXMLNode.getNodeByLabel(doc, eElement.getAttribute(TARGET)));
				listState.add(stateTarget);
			}
			coorTransition.setTargetState(stateTarget);

			List<Point2D> listPoint = new ArrayList<Point2D>();
			NodeList nodeList = eElement.getElementsByTagName(LIST_POINT).item(0).getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				listPoint.add(PointXMLNode.getPositionNode(nodeList.item(i)));
			}
			coorTransition.setListPoint(listPoint);
		}
		return coorTransition;
	}

	public static CoordinateState getState(List<CoordinateState> list, String label) {
		for (CoordinateState cState : list)
			if (cState.getLabel().equals(label))
				return cState;
		return null;
	}
}
