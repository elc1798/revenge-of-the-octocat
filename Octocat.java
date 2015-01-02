import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.ImageIcon;

class MeleeAtk {
	private Image sprite;
	private ImageIcon loader = null;
	
	public int[] spriteBounds;
	public int[] spriteLocation; // [xcoor , ycoor]
	private Rectangle spriteRect;
	
	public void setSprite(String PIC) {
		if (new File(PIC).exists()) {
			loader = new ImageIcon(PIC);
			sprite = loader.getImage();
			loader = null; //Reset for next setSprite
		} else {
			System.out.println("Error: File " + PIC + " not found. Exitting...");
			System.exit(0);
		}
	}
	
	public Rectangle boundaries() {
		spriteRect = new Rectangle(spriteLocation[0] , spriteLocation[1] , spriteBounds[0] , spriteBounds[1]);
		return spriteRect;
	}
	
	public void drawObj(Graphics g) {
		g.drawImage(this.sprite , this.spriteLocation[0] , this.spriteLocation[1] , this.spriteBounds[0] , this.spriteBounds[1] , null);
		Toolkit.getDefaultToolkit().sync();
	}
	
	public MeleeAtk(Octocat parent) {
		spriteLocation = new int[]{parent.spriteLocation[0] - 25 , parent.spriteLocation[1] - 25};
		spriteBounds = new int[]{parent.spriteBounds[0] + 50 , parent.spriteBounds[1] + 50};
		this.setSprite("resources/MELEEATK_OFF.png");
	}
	
	public void update(Octocat parent) {
		spriteLocation = new int[]{parent.spriteLocation[0] - 25 , parent.spriteLocation[1] - 25};
		spriteBounds = new int[]{parent.spriteBounds[0] + 50 , parent.spriteBounds[1] + 50};
	}
}

public class Octocat extends Entity {
	private int deltaX = 0;
	private int deltaY = 0;
	private final int STOP = 0;
	private int facing = 0;
	
	private Controller instance = null;
	private MeleeAtk attackRect = null;
	private boolean attackRectSpriteNeedsReset = false;
	private long attackRectSpriteChangeTime;
	
	//Constructor!
	
	public Octocat(Controller ctrl) {
		instance = ctrl;
		setLives(3);
		setDamage(1);
		setSpeed(2);
		setType("OCTOCAT_HEALTHY");
		setSprite("resources/OCTOCAT_HEALTHY.png");
		
		super.spriteBounds = new int[]{50 , 50};
		super.spriteLocation = new int[]{ctrl.MAX_X / 2 , ctrl.MAX_Y / 2};
		facing = 0;
		
		attackRect = new MeleeAtk(this);
	}

	//Data retrievers
	public int getLives() {
		return super.getLives();
	}
	public int getDamage() {
		return super.getDamage();
	}
	public int getSpeed() {
		return super.getSpeed();
	}
	public String getType() {
		return super.getType();
	}
	public Image getSprite() {
		return super.getSprite();
	}
	//Data setters
	public void setLives(int n) {
		super.setLives(n);
		if (this.getLives() <= 0) {
			//Game over is handled by GfxRenderer because the method currently used requires the Graphics and draw modules
		}
	}
	public void setDamage(int n) {
		super.setDamage(n);
	}
	public void setSpeed(int n) {
		super.setSpeed(n);
		if (this.getSpeed() > 5) {
			this.setSpeed(5);
		}
	}
	public void setType(String _type) {
		super.setType(_type);
	}
	public void setSprite(String PIC) {
		super.setSprite(PIC);
	}
	
	public void drawObj(Graphics g) {
		super.drawObj(g);
	}
	
	public void move() {//Movement alg
		super.spriteLocation[0] += deltaX * getSpeed();
		super.spriteLocation[1] += deltaY * getSpeed();
		attackRect.update(this);
		if (attackRectSpriteNeedsReset && System.currentTimeMillis() - attackRectSpriteChangeTime > 125) {
			attackRect.setSprite("resources/MELEEATK_OFF.png");
			attackRectSpriteNeedsReset = false;
		}
		
		//Location Correction: Do not let out of bounds of JFrame 'Controller -> instance'
		if (super.spriteLocation[0] > instance.MAX_X - super.spriteBounds[0]) {
			super.spriteLocation[0] = instance.MAX_X - super.spriteBounds[0];
		}
		if (super.spriteLocation[0] < instance.MIN_X) {
			super.spriteLocation[0] = instance.MIN_X;
		}
		if (super.spriteLocation[1] > instance.MAX_Y - super.spriteBounds[1]) {
			super.spriteLocation[1] = instance.MAX_Y - super.spriteBounds[1];
		}
		if (super.spriteLocation[1] < instance.MIN_Y) {
			super.spriteLocation[1] = instance.MIN_Y;
		}
	}
	
	public void keyPressed(KeyEvent e) {
		int id = e.getKeyCode();
		
		switch (id) {
		
		//Note deltaX and deltaY are hard coded to be -1 , 0 , 1 only
		//Values for facing are taken from Segfault.java
		
		case KeyEvent.VK_LEFT:
			deltaX = -1;
			//Uncomment when picture is made:
			//this.setSprite("resources/OCTOCAT_LEFT.png");
			switch (deltaY) {
			case -1:
				facing = 7;
				//System.out.println("Octocat: Update position: Should be facing LEFT UP");
				break;
			case 0:
				facing = 1;
				//System.out.println("Octocat: Update position: Should be facing LEFT");
				break;
			case 1:
				facing = 5;
				//System.out.println("Octocat: Update position: Should be facing LEFT DOWN");
				break;
			default:
				System.out.println("Invalid values for delta values detected. Exitting to prevent glitches/hacks...");
				System.out.println("Sending SIGTERM to process 'this'");
				System.exit(0);
				break;
			}
			break;
		case KeyEvent.VK_RIGHT:
			//Uncomment when picture is made:
			//this.setSprite("resources/OCTOCAT_RIGHT.png");
			deltaX = 1;
			switch (deltaY) {
			case -1:
				facing = 6;
				//System.out.println("Octocat: Update position: Should be facing RIGHT UP");
				break;
			case 0:
				facing = 0;
				//System.out.println("Octocat: Update position: Should be facing RIGHT");
				break;
			case 1:
				facing = 4;
				//System.out.println("Octocat: Update position: Should be facing RIGHT DOWN");
				break;
			default:
				System.out.println("Invalid values for delta values detected. Exitting to prevent glitches/hacks...");
				System.out.println("Sending SIGTERM to process 'this'");
				System.exit(0);
				break;
			}
			break;
		case KeyEvent.VK_UP:
			//Uncomment when picture is made:
			//this.setSprite("resources/OCTOCAT_UP.png");
			deltaY = -1;
			switch (deltaX) {
			case -1:
				facing = 7;
				//System.out.println("Octocat: Update position: Should be facing LEFT UP");
				break;
			case 0:
				facing = 3;
				//System.out.println("Octocat: Update position: Should be facing UP");
				break;
			case 1:
				facing = 6;
				//System.out.println("Octocat: Update position: Should be facing RIGHT UP");
				break;
			default:
				System.out.println("Invalid values for delta values detected. Exitting to prevent glitches/hacks...");
				System.out.println("Sending SIGTERM to process 'this'");
				System.exit(0);
				break;
			}
			break;
		case KeyEvent.VK_DOWN:
			//Uncomment when picture is made:
			//this.setSprite("resources/OCTOCAT_DOWN.png");
			deltaY = 1;
			switch (deltaX) {
			case -1:
				facing = 5;
				//System.out.println("Octocat: Update position: Should be facing LEFT DOWN");
				break;
			case 0:
				facing = 2;
				//System.out.println("Octocat: Update position: Should be facing DOWN");
				break;
			case 1:
				facing = 4;
				//System.out.println("Octocat: Update position: Should be facing RIGHT DOWN");
				break;
			default:
				System.out.println("Invalid values for delta values detected. Exitting to prevent glitches/hacks...");
				System.out.println("Sending SIGTERM to process 'this'");
				System.exit(0);
				break;
			}
			break;
		case KeyEvent.VK_Z:
			instance.shoot(facing);
			break;
		case KeyEvent.VK_X:
			instance.meleeAtk();
			attackRect.setSprite("resources/MELEEATK_ON.png");
			attackRectSpriteNeedsReset = true;
			attackRectSpriteChangeTime = System.currentTimeMillis();
			break;
		}
		
	}
	
	public void keyReleased(KeyEvent e) {
		int id = e.getKeyCode();
		
		switch (id) {
		
		case KeyEvent.VK_LEFT:
			deltaX = STOP;
			switch (deltaY) {
			case -1:
				facing = 3;
				break;
			case 0:
				//Do not change. Octocat is stationary, do not change face direction
				break;
			case 1:
				facing = 2;
				break;
			default:
				System.out.println("Invalid values for delta values detected. Exitting to prevent glitches/hacks...");
				System.out.println("Sending SIGTERM to process 'this'");
				System.exit(0);
				break;
			}
			break;
		case KeyEvent.VK_RIGHT:
			deltaX = STOP;
			switch (deltaY) {
			case -1:
				facing = 3;
				break;
			case 0:
				//Do not change. Octocat is stationary, do not change face direction
				break;
			case 1:
				facing = 2;
				break;
			default:
				System.out.println("Invalid values for delta values detected. Exitting to prevent glitches/hacks...");
				System.out.println("Sending SIGTERM to process 'this'");
				System.exit(0);
				break;
			}
			break;
		case KeyEvent.VK_UP:
			deltaY = STOP;
			switch (deltaX) {
			case -1:
				facing = 1;
				break;
			case 0:
				//Do not change. Octocat is stationary, do not change face direction
				break;
			case 1:
				facing = 0;
				break;
			default:
				System.out.println("Invalid values for delta values detected. Exitting to prevent glitches/hacks...");
				System.out.println("Sending SIGTERM to process 'this'");
				System.exit(0);
				break;
			}
			break;
		case KeyEvent.VK_DOWN:
			deltaY = STOP;
			switch (deltaX) {
			case -1:
				facing = 1;
				break;
			case 0:
				//Do not change. Octocat is stationary, do not change face direction
				break;
			case 1:
				facing = 0;
				break;
			default:
				System.out.println("Invalid values for delta values detected. Exitting to prevent glitches/hacks...");
				System.out.println("Sending SIGTERM to process 'this'");
				System.exit(0);
				break;
			}
			break;
		}
	}
	
	public void paintComponent(Graphics g) {
		attackRect.drawObj(g);
		drawObj(g);
	}
		
}
