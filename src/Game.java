import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;


@SuppressWarnings("serial")
public class Game extends Canvas {

	protected JFrame rotocGAME;
	protected boolean leftPressed , rightPressed , upPressed , downPressed;
	Octocat player;
	ArrayList<Bug> hostiles = new ArrayList<Bug>();
	Segfault[] ammo = new Segfault[5];
	private KeyListener kl = new KeyListener() {
		@Override
		public void keyPressed(KeyEvent e) {
			int id = e.getID();
			switch (id) {
			case KeyEvent.VK_UP:
				upPressed = true;
				break;
			case KeyEvent.VK_DOWN:
				downPressed = true;
				break;
			case KeyEvent.VK_LEFT:
				leftPressed = true;
				break;
			case KeyEvent.VK_RIGHT:
				rightPressed = true;
				break;
			}
			
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			int id = e.getID();
			switch (id) {
			case KeyEvent.VK_UP:
				upPressed = false;
				break;
			case KeyEvent.VK_DOWN:
				downPressed = false;
				break;
			case KeyEvent.VK_LEFT:
				leftPressed = false;
				break;
			case KeyEvent.VK_RIGHT:
				rightPressed = false;
				break;
			}
			
		}
		
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	};
	
	public void updateFrame() {
		
	}
	
	public void startGame() {
		rotocGAME = new JFrame("Revenge of the Octocat!");
		rotocGAME.setResizable(false);
		rotocGAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		player = new Octocat();
		Arrays.fill(ammo , new Segfault());
		
	}
	
}
