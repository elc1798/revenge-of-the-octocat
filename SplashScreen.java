import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import java.util.ArrayList;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class SplashScreen extends JPanel implements Runnable , ActionListener {

	private JButton startButton;
	private BackGroundLoader b;
	private final int DELAY = 24;
	private Thread animus; //Animation driver

	/*
	 * On keypress, Controller.java will start the game and do splashscreen.startGame = true;
	 */
	public boolean startGame = false;

	public SplashScreen() {
		startGame = false; //Just to clarify
		b = new BackGroundLoader("SPLASHSCREEN.png");
		startButton.addActionListener(this);
		
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
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == startButton) {
			startGame = true;
		}
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
				System.out.println("Interruption during Thread.sleep, SplashScreen.java Line 58");
				System.out.println("Sending SIGTERM to process...");
				System.exit(0);
			}

			prevTime = System.currentTimeMillis();

		}
	}

}