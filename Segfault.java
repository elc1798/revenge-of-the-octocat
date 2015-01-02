import java.awt.Graphics;
import java.awt.Image;


public class Segfault extends Entity {
	private int deltaX = 0;
	private int deltaY = 0;
	
	public int id = 0;
	
	private Controller instance = null;
	private Octocat host = null;
	
	//Constructor!
	
	public Segfault(Controller ctrl , Octocat player , int dir, int _id) {
		instance = ctrl;
		host = player;
		setLives(Integer.MAX_VALUE);//Indestructible?
		setDamage(Integer.MAX_VALUE);//Indestructible?
		setSpeed(3);
		id = _id;
		setType("SEGFAULT");
		super.spriteBounds = new int[]{25 , 25};
		super.spriteLocation = new int[]{host.spriteLocation[0] , host.spriteLocation[1]};
		switch (dir) {
		case 0:
			deltaX = 1;
			deltaY = 0;
			//System.out.println("Spawning segfault w/ DIR = RIGHT");
			this.setSprite("resources/SEGFAULT_RIGHT.png");
			break;
		case 1:
			deltaX = -1;
			deltaY = 0;
			//System.out.println("Spawning segfault w/ DIR = LEFT");
			this.setSprite("resources/SEGFAULT_LEFT.png");
			break;
		case 2:
			deltaX = 0;
			deltaY = 1;
			//System.out.println("Spawning segfault w/ DIR = DOWN");
			this.setSprite("resources/SEGFAULT_DOWN.png");
			break;
		case 3:
			deltaX = 0;
			deltaY = -1;
			//System.out.println("Spawning segfault w/ DIR = UP");
			this.setSprite("resources/SEGFAULT_UP.png");
			break;
		//Add diagonal cases???? idek yo
		case 4:
			deltaX = 1;
			deltaY = 1;
			//System.out.println("Spawning segfault w/ DIR = DOWN - RIGHT");
			this.setSprite("resources/SEGFAULT_DOWN-RIGHT.png");
			break;
		case 5:
			deltaX = -1;
			deltaY = 1;
			//System.out.println("Spawning segfault w/ DIR = DOWN - LEFT");
			this.setSprite("resources/SEGFAULT_DOWN-LEFT.png");
			break;
		case 6:
			deltaX = 1;
			deltaY = -1;
			//System.out.println("Spawning segfault w/ DIR = UP - RIGHT");
			this.setSprite("resources/SEGFAULT_UP-RIGHT.png");
			break;
		case 7:
			deltaX = -1;
			deltaY = -1;
			//System.out.println("Spawning segfault w/ DIR = UP - LEFT");
			this.setSprite("resources/SEGFAULT_UP_LEFT.png");
			break;
		default:
			System.out.println("Invalid case: Valid queries are ints from 0 - 7 inclusive");
			System.out.println("Exitting...");
			System.exit(0);
		}
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
	public int getID() {
		return id;
	}
	//Data setters
	public void setLives(int n) {
		super.setLives(n);
	}
	public void setDamage(int n) {
		super.setDamage(n);
	}
	public void setSpeed(int n) {
		super.setSpeed(n);
	}
	public void setType(String _type) {
		super.setType(_type);
	}
	public void setSprite(String PIC) {
		super.setSprite(PIC);
	}
	
	public void setID(int n) {
		id = n;
	}
	
	public void drawObj(Graphics g) {
		super.drawObj(g);
	}
	
	public void move() {//Movement alg
		
		super.spriteLocation[0] += deltaX * getSpeed();
		super.spriteLocation[1] += deltaY * getSpeed();
		
		//Location Correction: Do not let out of bounds of JFrame 'Controller -> instance'
		if (super.spriteLocation[0] > instance.MAX_X - super.spriteBounds[0]) {
			instance.rmAmmo(id);
		}
		if (super.spriteLocation[0] < instance.MIN_X) {
			instance.rmAmmo(id);
		}
		if (super.spriteLocation[1] > instance.MAX_Y - super.spriteBounds[1]) {
			instance.rmAmmo(id);
		}
		if (super.spriteLocation[1] < instance.MIN_Y) {
			instance.rmAmmo(id);
		}
	}
	
	public void paintComponent(Graphics g) {
		drawObj(g);
	}
	
}
