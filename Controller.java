import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;


@SuppressWarnings("serial")
public class Controller extends JFrame {
	public final int MAX_X = 950;
	public final int MAX_Y = 600;
	public final int MIN_X = 0;
	public final int MIN_Y = 0;
	
	private Octocat player;
	private BackGroundLoader bgl;
	private GfxRenderer gfx;
	private Bug[] enemies;
	private Segfault[] projectiles = new Segfault[5];
	
	private int level = 1;
	private int stock = 0;
	
	private class inputAdapter extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			player.keyReleased(e);
		}
		
		public void keyPressed(KeyEvent e) {
			player.keyPressed(e);
		}
	}
	
	public Controller() {
		
		stock = 3;
		
		bgl = new BackGroundLoader("resources/BKGRND_ENTRY.jpg");
		player = new Octocat(this);
		enemies = new Bug[level * 3];
		for (int i = 0; i < enemies.length; i++) {
			enemies[i] = new Bug(this , player , i);
		}
		gfx = new GfxRenderer(player , bgl , enemies , projectiles , this);
		
		setPreferredSize(new Dimension(950 , 600));
		
		add(gfx);
		addKeyListener(new inputAdapter());
		pack();
		
		setTitle("Revenge of the Octocat!");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	public int getStock() {
		return stock;
	}
	
	public void addAmmo() {
		if (stock < 5) {
			stock++;
		}
		// Fail safe correction:

		if (stock > 5) {
			stock = 5;
		}
		
		System.out.println("Ammo stock: " + stock + " / 5");
	}
	
	public void shoot(int dir) {
		
		// Fail safe correction:
		
		if (stock > 5) {
			stock = 5;
		}
		if (stock > 0) {
			projectiles[stock - 1] = new Segfault(this , player , dir , stock - 1);
			stock--;
		}
		System.out.println("Ammo stock: " + stock + " / 5");
	}
	
	public void rmAmmo(int id) {
		//id represents the index!!!!!
		projectiles[id] = null;
	}

	public void rmBug(int id) {
		enemies[id] = null;
	}
	
	public void gameOver() {
		System.out.println("You lose! You suck :D");
		System.exit(0);
	}
	
	public void victoryScreen() {
		//Planned
	}
	
	public void nextLevel() {
		//Reinvoke constructor?
		level++;
		//Stuff
	}
	
}
