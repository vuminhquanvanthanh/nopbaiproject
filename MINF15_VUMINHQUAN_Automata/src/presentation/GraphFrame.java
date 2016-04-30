package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
//import javax.swing.JTextPane;
//import javax.swing.WindowConstants;
import javax.xml.parsers.ParserConfigurationException;

import automata.Automata;

import middle.LayoutGraphAutomata;
import data.XMLLibrary;
//import graph.editor.GraphFrame;

/**
 * @author VuMinhQuan
 *
 */
@SuppressWarnings("serial")
public class GraphFrame extends JFrame implements ActionListener {

	JMenuBar menubar;
	JFileChooser fc;
	JPopupMenu popup;
	GraphComponent component;
	JMenuItem[] rMenuItem;

	LayoutGraphAutomata middle;

	public GraphFrame() {
		init();
		this.addKeyListener(new KeyListen(component));
	}

	void init() {
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setPreferredSize(new Dimension(1200, 700));
		this.setTitle("VU MINH QUAN MINF15");
		this.setVisible(true);

		component = new GraphComponent();
		component.setPreferredSize(new Dimension(1000, 800));
		middle = new LayoutGraphAutomata(null, component.listEdge);

		fc = new JFileChooser(); // define file chooser
		createScrollBar(); // created and add scrollbar

		// created and add menu
		createMenuBar();
		setJMenuBar(menubar);

		// add popup menu
		createPopuMenu();
		component.add(popup);
		this.pack();
	}



	private void createScrollBar() {
		JScrollPane srb = new JScrollPane(component);
		this.add(srb, BorderLayout.CENTER);

	}

	private void createMenuBar() {
		menubar = new JMenuBar();
		menubar.setFocusable(false);

		JMenu menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menubar.add(menu);

		menu.addSeparator();

		JMenuItem item1 = new JMenuItem("Exit", KeyEvent.VK_E);
		item1.setName("Exit");
		item1.setMnemonic(KeyEvent.VK_E);
		item1.setActionCommand("Exit");
		item1.addActionListener(this);
		menu.add(item1);
		// ------------------------------------------------------
		menu = new JMenu("Automata");
		menu.setMnemonic(KeyEvent.VK_A);
		menubar.add(menu);

		item1 = new JMenuItem("Save to Automata");
		item1.setActionCommand("defineAT");
		item1.addActionListener(this);
		menu.add(item1);

		item1 = new JMenuItem("Load from Automata");
		item1.setActionCommand("defineGraph");
		item1.addActionListener(this);
		menu.add(item1);

		menu.addSeparator();

		item1 = new JMenuItem("Save to XML File");
		item1.setActionCommand("saveXML");
		item1.addActionListener(this);
		menu.add(item1);

		item1 = new JMenuItem("Load from XML file");
		item1.setActionCommand("loadXML");
		item1.addActionListener(this);
		menu.add(item1);

		menu.addSeparator();

		item1 = new JMenuItem("Recognize Automata");
		item1.setActionCommand("recognizeAT");
		item1.addActionListener(this);
		menu.add(item1);

		// ------------------------------------------------------
		menu = new JMenu("Vertex Type");
		menu.setMnemonic(KeyEvent.VK_V);
		menubar.add(menu);

		rMenuItem = new JMenuItem[VertexType.values().length];

		for (int i = 0; i < VertexType.values().length; i++) {
			rMenuItem[i] = new JRadioButtonMenuItem(((VertexType.values())[i].toString()).toLowerCase());

			boolean selected = (i == 0);
			rMenuItem[i].setActionCommand((VertexType.values())[i].toString());
			rMenuItem[i].addActionListener(this);
			rMenuItem[i].setSelected(selected);
			menu.add(rMenuItem[i]);
		}
		
	}

	Point2D rcPoint = null;

	private void createPopuMenu() {
		popup = new JPopupMenu();
		JMenuItem item = new JMenuItem("Delete");
		item.setActionCommand("Delete");
		item.addActionListener(this);
		popup.add(item);

		component.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				showPopup(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				showPopup(e);
				rcPoint = e.getPoint();
			}

			private void showPopup(MouseEvent e) {
				if (e.isPopupTrigger()) {
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String source = (arg0.getActionCommand());
		XMLLibrary xmlLib = null;
		try {
			xmlLib = new XMLLibrary();
		} catch (ParserConfigurationException e1) {

			e1.printStackTrace();
		}
		switch (source) {
		case "Exit":
			exitMenuClick();
			break;
		case "defineAT":// define automata from graph
			middle.setListEdge(component.listEdge);
			saveAutomata();
			break;
		case "recognizeAT":
			if (middle.getAtmata() == null) {
				if (middle.getListEdge().getSize() > 0) {
					saveAutomata();
				}
			}
			if (middle.getAtmata() != null)
				middle.getAtmata().addObserver(component);
			Thread t = new Thread() {
				@Override
				public void run() {
					middle.recognizeAutomata(inputStringRecognize());

				};
			};
			t.start();
			break;
		case "defineGraph":
			component.listEdge = loadAutomata();
			component.listVertex = component.listVertex.getListVertexFromListEdge(component.listEdge);
			repaint();
			break;
		case "saveXML":
			middle.defineAutomata();
			saveToXMLFile(xmlLib, middle.getAtmata());
			break;
		case "loadXML":
			middle.setAtmata(loadFromXMLFile(xmlLib));
			component.listEdge = middle.defineGraph(middle.getAtmata());
			component.listVertex = component.listVertex.getListVertexFromListEdge(component.listEdge);
			repaint();
			break;
		case "Help":

			break;
		case "Delete":
			component.currentPoint = rcPoint;
			component.deleteVertex();
			break;
		default:
			component.typeVertex = (VertexType.valueOf(source) == null) ? VertexType.NORMAL
					: VertexType.valueOf(source);
			if (VertexType.valueOf(source) != null) {
				component.typeVertex = VertexType.valueOf(source);
				changeVertexType(VertexType.valueOf(source));
				return;
			}
			break;
		}
	}

	private void changeVertexType(VertexType arg0) {
		for (int i = 0; i < VertexType.values().length; i++) {
			if (VertexType.values()[i].equals(arg0))
				rMenuItem[i].setSelected(true);
			else
				rMenuItem[i].setSelected(false);
		}
	}

	private void exitMenuClick() {
		int dialogButton = JOptionPane.YES_NO_OPTION;
		int dialogResult = JOptionPane.showConfirmDialog(this, "Do you want close application ?", "Confirm",
				dialogButton);
		if (dialogResult == 0)
			this.dispose();
	}

	private String[] inputStringRecognize() {
		if (middle.getAtmata() != null) {
			String inputString = JOptionPane.showInputDialog(this, "Type word to recognize", "Recognize",
					JOptionPane.OK_OPTION);
			if (inputString != null) {
				String[] inputRecognize = new String[inputString.length()];
				for (int i = 0; i < inputString.length(); i++) {
					inputRecognize[i] = String.valueOf(inputString.charAt(i));
				}
				return inputRecognize;
			}
			return null;
		} else {
			JOptionPane.showMessageDialog(this, "Automaton is not exist", "Error", JOptionPane.WARNING_MESSAGE);
			return null;
		}
	}

	private String getPathFile() {
		String folderPath = "";
		fc.setCurrentDirectory(new File("."));
		int value = fc.showOpenDialog(this);
		if (value == JFileChooser.APPROVE_OPTION) {
			folderPath = fc.getSelectedFile().getAbsolutePath();
		}
		return folderPath;
	}

	private void saveAutomata() {
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		String path = getPathFile();

		if (path != "") {
			boolean result = middle.serialization(path);
			if (result)
				JOptionPane.showMessageDialog(this, "Serialization successfully...");
			else
				JOptionPane.showMessageDialog(this, "Serialization unsuccessfully...");
		}
	}

	private ListEdge loadAutomata() {
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		String path = getPathFile();

		ListEdge listEdge = null;
		if (path != "") {
			listEdge = middle.deserilization(path);
		}
		return listEdge;
	}

	private void saveToXMLFile(XMLLibrary library, Automata automata) {
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		String path = getPathFile();

		if (path != "") {
			boolean result = library.writeXML(automata.getAllTransitions(), path);
			if (result)
				JOptionPane.showMessageDialog(null, "XML Automata created successfully..");
			else
				JOptionPane.showMessageDialog(null, "XML Automata created unsuccessfully..");
		}
	}

	private Automata loadFromXMLFile(XMLLibrary library) {
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		String path = getPathFile();

		if (path != "") {
			Automata result = library.readXML(path);
			return result;
		}
		return null;
	}
}
