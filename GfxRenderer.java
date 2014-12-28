import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class GfxRenderer extends JPanel implements Runnable , ActionListener {
	
	private Octocat OC;
	private BackGroundLoader bgl;
	private ArrayList<Bug> enemies;
	private Segfault[] projectiles;
	
	private final int DELAY = 24;
	private Thread animus; //Animation driver
	
	public GfxRenderer(Octocat session , BackGroundLoader b , ArrayList<Bug> bugs , Segfault[] sfs) {
		OC = session;
		bgl = b;
		enemies = bugs;
		projectiles = sfs;
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
		bgl.paintComponent(g);
		OC.paintComponent(g);
		for (Segfault s : projectiles) {
			if (s != null) {
				s.paintComponent(g);
			}
		}
		for (Bug b : enemies) {
			b.paintComponent(g);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		// Do Nothing
	}

	@Override
	public void run() {
		long prevTime , timeDiff , sleepTime;
		
		prevTime = System.currentTimeMillis();
		
		while (true) {
			
			OC.move();
			for (Bug b : enemies) {
				b.move();
			}
			for (Segfault s : projectiles) {
				if (s != null) {
					s.move();
				}
			}
			repaint();
			
			timeDiff = System.currentTimeMillis() - prevTime;
			sleepTime = DELAY - timeDiff;
			
			if (sleepTime < 0) {
				sleepTime = 2; // Do not allow negative sleepTime
			}
			
			try {
				Thread.sleep(sleepTime);
			} catch(InterruptedException ie) {
				System.out.println("Interruption during Thread.sleep, GfxRenderer.java Line 62");
				System.out.println("Sending SIGTERM to process...");
				System.exit(0);
			}
			
			prevTime = System.currentTimeMillis();
			
		}
		
	}

}
