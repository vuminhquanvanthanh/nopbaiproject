package presentation;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author VuMinhQuan
 *
 */
public class KeyListen implements KeyListener {

	GraphComponent graph;

	public KeyListen(GraphComponent gr) {
		this.graph = gr;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
			graph.addJointPoint();
		}
		if (arg0.getKeyCode() >= KeyEvent.VK_0 && arg0.getKeyCode() <= KeyEvent.VK_9)
			graph.addLabelVertex(arg0.getKeyCode() - 48);
		if (arg0.getKeyCode() >= KeyEvent.VK_A && arg0.getKeyCode() <= KeyEvent.VK_Z)
			graph.addLabelEdge(KeyEvent.getKeyText(arg0.getKeyCode()).toLowerCase());
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

}
