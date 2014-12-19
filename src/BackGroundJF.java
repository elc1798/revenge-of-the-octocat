import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;


public class BackGroundJF extends JFrame implements KeyListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BackGroundJF() {
		JFrame k = new JFrame();
		
		this.setVisible(false); //Leave in background
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if ((arg0.getKeyCode() == KeyEvent.VK_C) && ((arg0.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
            System.out.println("woot!");
        }
		int id = arg0.getID();
		switch (id) {
		case KeyEvent.VK_TAB:
			System.out.println("Tab key caught");
			break;
		case KeyEvent.VK_UP:
			System.out.println("Up key caught");
			break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
