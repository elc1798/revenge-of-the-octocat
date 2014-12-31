import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class GfxRenderer extends JPanel implements Runnable , ActionListener {
	
	private Octocat OC;
	private BackGroundLoader bgl;
	private Bug[] enemies;
	private Segfault[] projectiles;
	private Controller instance;
	private Overlay overlay;
	
	private final int DELAY = 24;
	private Thread animus; //Animation driver
	private boolean alive = true;
	
	public GfxRenderer(Octocat session , BackGroundLoader b , Bug[] bugs , Segfault[] sfs , Controller _instance) {
		OC = session;
		bgl = b;
		enemies = bugs;
		projectiles = sfs;
		instance = _instance;
		overlay = new Overlay(instance);
	}
	
	public void resetPointer(Bug[] bugPointer) {
		enemies = bugPointer;
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
		if (OC.getLives() <= 0) {
			String msg = "Game over! Score: " + instance.score;
            Font small = new Font("Helvetica", Font.BOLD, 28);
            FontMetrics metr = this.getFontMetrics(small);
            setBackground(Color.DARK_GRAY);
            g.setColor(Color.white);
            g.setFont(small);
            g.drawString(msg, (instance.MAX_X - metr.stringWidth(msg)) / 2, instance.MAX_Y / 2);
            alive = false;
            instance.stopKeyListener();
		} else {
			bgl.paintComponent(g);
			OC.paintComponent(g);
			for (Segfault s : projectiles) {
				if (s != null) {
					s.paintComponent(g);
				}
			}
			for (Bug b : enemies) {
				if (b != null) {
					b.paintComponent(g);
				}
			}
			
			overlay.paintComponent(g);
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
		
		while (alive) {
			
			if (instance.numBugs() <= 0) {
				overlay.victoryScreen();
			}
			
			OC.move();
			
			//Note: Collision detectors would otherwise be in Controller.java, but it would take more loops, so I put it here to save runtime
			
			Rectangle currOCBounds = OC.boundaries();
			
			for (Bug b : enemies) {
				if (b != null) {
					b.move();
					if (b.boundaries().intersects(currOCBounds)) {
						OC.setLives(OC.getLives() - b.getDamage());
						instance.rmBug(b.id); //It's either this or we can decrement b's lives by 1?
					}
				}
			}
			for (Segfault s : projectiles) {
				if (s != null) {
					s.move();
					Rectangle currSFBounds = s.boundaries();	//Note: Entity.boundaries() creates a new Rectangle EVERY TIME it is called. To save runtime, create a variable instead of calling it over and over again
					for (Bug b : enemies) {
						if (b != null) {
							if (b.boundaries().intersects(currSFBounds)) {
								b.setLives(b.getLives() - s.getDamage());
								instance.rmAmmo(s.id);
								break;
							}
						}
					}
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
				System.out.println("Interruption during Thread.sleep, GfxRenderer.java Line 136");
				System.out.println("Sending SIGTERM to process...");
				System.exit(0);
			}
			
			prevTime = System.currentTimeMillis();
			
		}
		
	}

}
