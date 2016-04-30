/**
 * 
 */
package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import automata.Automata;
import automata.CoordinateState;
import automata.CoordinateTransition;

/**
 * @author VuMinhQuan
 *
 */
public class XMLLibrary {
	private final String AUTOMATA = "automata";
	private final String TRANSITIONS = "transitions";
	private final String STATES = "states";
	DocumentBuilderFactory icFactory;
	DocumentBuilder icBuilder;
	Document doc;

	public XMLLibrary() throws ParserConfigurationException {
		icFactory = DocumentBuilderFactory.newInstance();
		icBuilder = icFactory.newDocumentBuilder();
	}

	private void transformToFile(Document doc, String fileName) {
		Transformer transformer = null;
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
		} catch (TransformerConfigurationException e1) {
			e1.printStackTrace();
		} catch (TransformerFactoryConfigurationError e1) {
			e1.printStackTrace();
		}

		DOMSource source = new DOMSource(doc);
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(new File(fileName + "/export.xml"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		StreamResult console = new StreamResult(fileOutputStream);
		try {
			transformer.transform(source, console);

		} catch (TransformerException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("rawtypes")
	public boolean writeXML(List<CoordinateTransition> list, String fileName) {
		try {
			doc = icBuilder.newDocument();
			Element rootElement = doc.createElement(AUTOMATA);
			doc.appendChild(rootElement);
			Element ele = doc.createElement(STATES);
			List<CoordinateState> listStates = Automata.getAllStates(list);
			for (CoordinateState cState : listStates) {
				ele.appendChild(StateXMLNode.setStateNode(doc, cState));
			}
			rootElement.appendChild(ele);

			ele = doc.createElement(TRANSITIONS);
			for (CoordinateTransition cTransition : list) {
				ele.appendChild(TransitionXMLNode.setTransitionNode(doc, cTransition));
			}
			rootElement.appendChild(ele);

			transformToFile(doc, fileName);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public Automata readXML(String fileName) {
		File xmlFile = new File(fileName);
		try {
			doc = icBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();

			NodeList nodeList = doc.getElementsByTagName(TRANSITIONS).item(0).getChildNodes();
			CoordinateTransition<?>[] listTransitions = new CoordinateTransition[nodeList.getLength()];

			for (int i = 0; i < nodeList.getLength(); i++) {
				listTransitions[i] = TransitionXMLNode.getTransitionNode(doc, nodeList.item(i));
			}
			return new Automata(listTransitions);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
