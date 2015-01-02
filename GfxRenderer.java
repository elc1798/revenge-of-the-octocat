import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class GfxRenderer extends JPanel implements Runnable , ActionListener {
	
	private Octocat OC;
	private BackGroundLoader bgl;
	private Bug[] enemies;
	private Segfault[] projectiles;
	private Controller instance;
	private Overlay overlay;
	
	public ArrayList<Powerup> powerups = new ArrayList<Powerup>(); //Made public so Controller can have access
	
	private final int DELAY = 24;
	private Thread animus; //Animation driver
	private boolean alive = true;
	public boolean requireOverlayReset = false;
	public long overlayChanged = System.currentTimeMillis();
	private boolean requireNextLevel = false;
	
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
	
	public void cleanupPowerups() {
		powerups = null;
		powerups = new ArrayList<Powerup>();
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
			for (Powerup p : powerups) {
				if (p != null) {
					p.paintComponent(g);
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
			
			if (requireOverlayReset && System.currentTimeMillis() - overlayChanged > 3000) {
					overlay.resetOverlay();
					requireOverlayReset = false;
					if (requireNextLevel) {
						instance.nextLevel();
						requireNextLevel = false;
					}
			}
			
			if (instance.numBugs() <= 0) {
				overlay.victoryScreen();
				if (!requireNextLevel) {
					requireOverlayReset = true;
					overlayChanged = System.currentTimeMillis();
					requireNextLevel = true;
				}
			}			
			
			OC.move();
			
			//Note: Collision detectors would otherwise be in Controller.java, but it would take more loops, so I put it here to save runtime
			
			Rectangle currOCBounds = OC.boundaries();
			
			for (Bug b : enemies) {
				if (b != null && !b.getType().equals("BUG_GHOST")) {
					b.move();
					if (b.boundaries().intersects(currOCBounds)) {
						OC.setLives(OC.getLives() - b.getDamage());
						instance.rmBug(b.id); //It's either this or we can decrement b's lives by 1?
						overlay.rmLifeScreen();
						requireOverlayReset = true;
						overlayChanged = System.currentTimeMillis();
					}
				} else if (b != null && b.getType().equals("BUG_GHOST")) {
					b.setLives(-9001);
				}
			}
			for (Segfault s : projectiles) {
				if (s != null) {
					s.move();
					Rectangle currSFBounds = s.boundaries();	//Note: Entity.boundaries() creates a new Rectangle EVERY TIME it is called. To save runtime, create a variable instead of calling it over and over again
					for (Bug b : enemies) {
						if (b != null && !b.getType().equals("BUG_GHOST")) {
							if (b.boundaries().intersects(currSFBounds)) {
								b.setLives(b.getLives() - s.getDamage());
								instance.rmAmmo(s.id);
								break;
							}
						}
					}
				}
			}
			for (int i = 0; i < powerups.size(); i++) {//I need the index, so use basic for loop instead of for(Powerup p : powerups)
				if (powerups.get(i) != null) {
					if (powerups.get(i).boundaries().intersects(currOCBounds)) {
						powerups.get(i).givePowerup(OC , instance);
						if (powerups.get(i).getType().equals("POWERUP_LIFE")) {
							overlay.addLifeScreen(OC.spriteLocation[0] , OC.spriteLocation[1]);
							requireOverlayReset = true;
							overlayChanged = System.currentTimeMillis();
						}
						powerups.set(i , null); //Use this rather than calling remove to save runtime. Cleanup later!
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
				System.out.println("Interruption during Thread.sleep, GfxRenderer.java Line 183");
				System.out.println("Sending SIGTERM to process...");
				System.exit(0);
			}
			
			prevTime = System.currentTimeMillis();
			
		}
		
	}

}
