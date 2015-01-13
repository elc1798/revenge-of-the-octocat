import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

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
	private inputAdapter userIn = new inputAdapter();
	
	private int level = 1;
	private int stock = 0;
	private int enemiesLeft = 0;
	private Rectangle currHitZone = null;
	private Random r = new Random();
	private String bkgrndPrefix = "resources/BKGRND_";
	private String[] bkgrndOrder = new String[]{"ENTRY.jpg" , "ROOTS.jpg" , "MOBO.jpg" , "HUB.jpg" , "NET.jpg"};
	
	public int score = 0;
	
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
		enemies = new Bug[3];
		for (int i = 0; i < enemies.length; i++) {
			enemies[i] = new Bug(this , player , i);
		}
		enemiesLeft = enemies.length;
		gfx = new GfxRenderer(player , bgl , enemies , projectiles , this);
		
		setPreferredSize(new Dimension(950 , 600));
		
		add(gfx);
		addKeyListener(userIn);
		pack();
		
		setTitle("Revenge of the Octocat!");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
	}
	
	public int getStock() {
		return stock;
	}
	
	public int getLevel() {
		return level;
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
	
	public void meleeAtk() {
		/*
		 * Octocat's melee attack!
		 * Octocat lashes out in a 'circle' (Actually a rectangle) , dealing to *a single* enemy in the zone of attack
		 */
		
		currHitZone = new Rectangle(player.spriteLocation[0] - 25 , player.spriteLocation[1] - 25 , player.spriteBounds[0] + 50 , player.spriteBounds[1] + 50);
		
		for (Bug b : enemies) {
			if (b != null) {
				if (b.boundaries().intersects(currHitZone)) {
					b.setLives(b.getLives() - player.getDamage());
					//System.out.println(b.getLives());
					currHitZone = null;
					break;
				}
			}
		}
		
	}
	
	public void rmAmmo(int id) {
		//id represents the index!!!!!
		projectiles[id] = null;
	}

	public void rmBug(int id) {
		enemies[id] = null;
		score += 100;
		//Used to call decrementEnemiesLeft() but removed so dynamic sprites for Bugs could work with Overlays and BUG_GHOSTs
	}
	
	public void decrementEnemiesLeft() {
		enemiesLeft--;
	}
	
	public void spawnPowerup(int id) {
		if (r.nextInt(100) < 25) {
			gfx.powerups.add(new Powerup(enemies[id].spriteLocation));
		}
	}
	
	public int numBugs() {
		//System.out.println(enemiesLeft);
		return enemiesLeft;
	}
	
	public void gameOver() {
		//Graphics drawing handled in GfxRenderer
		System.out.println("You lose!");
		System.exit(0);
	}
	
	public void nextLevel() {
		bgl.loadImage(bkgrndPrefix + bkgrndOrder[level % bkgrndOrder.length]);
		level++;
		enemies = null;
		enemies = new Bug[3 + level * 2];
		enemiesLeft = enemies.length;
		for (int i = 0; i < enemies.length; i++) {
			enemies[i] = new Bug(this , player , i);
		}
		gfx.resetPointer(enemies);
		//Be nice! Give ammo! You'll need it ;)
		addAmmo();
		addAmmo();
		addAmmo();
	}
	
        public void stopKeyListener() {
		removeKeyListener(userIn);
	}
    
        //Accesses Bug[] enemies with parameter index and returns object
        public Bug getBug(int index) {
	    if (index<enemies.length){
		return enemies[index];
	    } else {
		return null;
	    }
	}
}
