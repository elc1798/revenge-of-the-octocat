import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class SplashScreen extends JPanel implements Runnable , MouseListener {

	private BackGroundLoader b;
	private final int DELAY = 24;
	private Thread animus; //Animation driver

	/*
	 * On keypress, Controller.java will start the game and do splashscreen.startGame = true;
	 */
	public boolean startGame = false;

	public SplashScreen() {
		startGame = false; //Just to clarify
		b = new BackGroundLoader("resources/SPLASHSCREEN.png");

	}

	@Override
	public void addNotify() {
		super.addNotify();

		animus = new Thread(this);
		animus.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		b.paintComponent(g);
	}

	@Override
	public void run() {
		long prevTime , timeDiff , sleepTime;

		prevTime = System.currentTimeMillis();

		while (!startGame) {
			repaint();

			timeDiff = System.currentTimeMillis() - prevTime;
			sleepTime = DELAY - timeDiff;

			if (sleepTime < 0) {
				sleepTime = 2; // Do not allow negative sleepTime
			}

			try {
				Thread.sleep(sleepTime);
			} catch(InterruptedException ie) {
				System.out.println("Interruption during Thread.sleep, SplashScreen.java Line 74");
				System.out.println("Sending SIGTERM to process...");
				System.exit(0);
			}

			prevTime = System.currentTimeMillis();

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		int clickX = e.getX();
		int clickY = e.getY();
		if (clickX < 500 && clickY > 400) {
			startGame = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}