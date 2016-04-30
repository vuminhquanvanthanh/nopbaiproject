/**
 * 
 */
package data;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import automata.CoordinateState;

/**
 * @author VuMinhQuan
 *
 */
public class StateXMLNode {

	private static final String NODE_NAME = "state";
	private static final String LABEL = "label";
	private static final String INIT = "init";
	private static final String END = "end";

	public static Node setStateNode(Document doc, CoordinateState state) {
		Element ele = doc.createElement(NODE_NAME);
		ele.setAttribute(INIT, String.valueOf(state.isInit()));
		ele.setAttribute(END, String.valueOf(state.isEnd()));
		ele.setAttribute(LABEL, state.getLabel());
		ele.appendChild(PointXMLNode.setPositionNode(doc, state.getCoordinate()));
		return ele;
	}

	public static CoordinateState getStateNode(Node xmlNode) {
		CoordinateState state = new CoordinateState("", false, false, null);
		if (xmlNode.getNodeType() == Node.ELEMENT_NODE) {

			Element eElement = (Element) xmlNode;
			state.setLabel(eElement.getAttribute(LABEL));
			state.setInit(Boolean.parseBoolean(eElement.getAttribute(INIT)));
			state.setEnd(Boolean.parseBoolean(eElement.getAttribute(END)));
			state.setCoordinate(
					PointXMLNode.getPositionNode(eElement.getElementsByTagName(PointXMLNode.POSITON).item(0)));
		}
		return state;
	}

	public static Node getNodeByLabel(Document doc, String label) {
		Element ele = doc.getDocumentElement();

		for (int i = 0; i < ele.getElementsByTagName(NODE_NAME).getLength(); i++) {
			Element node = (Element) ele.getElementsByTagName(NODE_NAME).item(i);
			if (node.getAttribute(LABEL).equals(label))
				return node;
		}
		return null;
	}
}
