package middle;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.swing.JOptionPane;

import presentation.Edge;
import presentation.ListEdge;

import automata.Automata;
import automata.CoordinateState;
import automata.CoordinateTransition;
import automata.NotDeterministInitalStateException;
import automata.NotDeterministTransitionException;
import automata.UnknownInitialStateException;

/**
 * @author VuMinhQuan
 *
 */
public class LayoutGraphAutomata {

	private Automata atmata;
	private ListEdge listEdge;

	public Automata getAtmata() {
		return atmata;
	}

	public void setAtmata(Automata atmata) {
		this.atmata = atmata;
	}

	public ListEdge getListEdge() {
		return listEdge;
	}

	public void setListEdge(ListEdge listEdge) {
		this.listEdge = listEdge;
	}

	public LayoutGraphAutomata(Automata at, ListEdge listEd) {
		this.atmata = at;
		this.listEdge = listEd;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void defineAutomata() {
		if (atmata != null && listEdge.getSize() == 0)
			listEdge = defineGraph(atmata);
		if (listEdge == null)
			listEdge = new ListEdge();
		CoordinateTransition[] transitions = new CoordinateTransition[listEdge.getSize()];
		CoordinateState st = null;
		for (int i = 0; i < listEdge.getSize(); i++) {
			Edge ed = listEdge.getIndex(i);

			if (ed.getSource().getInfo().isInit())
				st = ed.getSource().getInfo();
			if (ed.getTarget().getInfo().isInit())
				st = ed.getTarget().getInfo();

			transitions[i] = new CoordinateTransition(ed.getSource().getInfo(), ed.getTarget().getInfo(),
					ed.getLabel());
			transitions[i].setListPoint(ed.getListJoinPoint());
		}
		Automata automata;
		try {
			automata = new Automata(transitions);
			automata.setInitialState(st);
			setAtmata(automata);

		} catch (NotDeterministTransitionException e) {
			JOptionPane.showInputDialog("Error", "NotDeterministTransitionException");
		} catch (UnknownInitialStateException e) {
			JOptionPane.showInputDialog("Error", "UnknownInitialStateException");
		} catch (NotDeterministInitalStateException e) {
			JOptionPane.showInputDialog("Error", "NotDeterministInitalStateException");
		}
	}

	public void recognizeAutomata(String[] word) {
		if (atmata != null) {
			try {
				boolean result = atmata.recognize(word);
				if (result)
					JOptionPane.showMessageDialog(null, "The word is accepted.", "Warning",
							JOptionPane.WARNING_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "The word is not accepted.", "Warning",
							JOptionPane.WARNING_MESSAGE);
			} catch (NotDeterministTransitionException e) {
				JOptionPane.showInputDialog("Error", "NotDeterministTransitionException");
			}
		}

	}

	@SuppressWarnings("rawtypes")
	public ListEdge defineGraph(Automata autmata) {
		List<CoordinateTransition> list = autmata.getAllTransitions();
		return listEdge.generalListEdge(list);
	}

	public boolean serialization(String fileName) {
		try {
			FileOutputStream fileOut = new FileOutputStream(fileName + "/automata.bin");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			defineAutomata();
			out.writeObject(atmata);
			out.close();
			fileOut.close();

			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public ListEdge deserilization(String fileName) {
		ListEdge e = null;
		try {
			FileInputStream fileIn = new FileInputStream(fileName);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			Automata at = (Automata) in.readObject();
			if (at != null) {
				setAtmata(at);
				e = defineGraph(at);
			}
			in.close();
			fileIn.close();
			return e;
		} catch (IOException i) {
			i.printStackTrace();
			return null;
		} catch (ClassNotFoundException c) {
			System.out.println("Error");
			c.printStackTrace();
			return null;
		}
	}
}
