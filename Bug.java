import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;


public class Bug extends Entity {
	private int deltaX = 0;
	private int deltaY = 0;
	
	private Controller instance = null;
	private Octocat target = null;
	
	public int id = 0;
	
	private Random locationSetter = new Random();
	private long deathTime;
	private boolean dead = false;
	
	//Constructor!
	
	public Bug(Controller ctrl , Octocat prey , int _id) {
		instance = ctrl;
		target = prey;
		id = _id;
		setLives(1 + (int)(instance.getLevel() / 2));
		setDamage(1);
		setSpeed(1);
		setType("BUG_HEALTHY");
		setSprite("resources/BUG_RIGHT.png");
		super.spriteBounds = new int[]{70 , 70};
		int init_X = target.spriteLocation[0];
		int init_Y = target.spriteLocation[1];
		while (Math.abs(init_X - target.spriteLocation[0]) < 50) { // Do not allow spawn within 50 units of player! (Cheap way!)
			init_X = locationSetter.nextInt(instance.MAX_X); //Note we do not need to add instance.MIN_X because it's 0!
		}
		while (Math.abs(init_Y - target.spriteLocation[1]) < 50) {
			init_Y = locationSetter.nextInt(instance.MAX_Y);
		}
		//TODO: Revise the init_LOC setters to be more efficient. Loops are definitely NOT required here.
		
		super.spriteLocation = new int[]{init_X , init_Y};
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
			if (!dead && instance.numBugs() != 1) {
				dead = true;
				deathTime = System.currentTimeMillis();
				this.setType("BUG_GHOST");
				this.setSprite("resources/BUG_GHOST.png");
				super.spriteBounds[0] = 107;
				super.spriteBounds[1] = 20;
				instance.spawnPowerup(this.id);
			} else if (!dead && instance.numBugs() == 1) { // Do not create ghost if last bug
				instance.spawnPowerup(this.id);
				instance.rmBug(this.id);
			} else if (System.currentTimeMillis() - deathTime > 2500){
				instance.rmBug(this.id);
			}
		}
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
	
	public void drawObj(Graphics g) {
		super.drawObj(g);
	}
	
	public void move() {//Movement alg
		
		//Chase after the octocat!
		
		if (super.spriteLocation[0] > target.spriteLocation[0]) {
			deltaX = -1;
			this.setSprite("resources/BUG_LEFT.png");
		} else {
			deltaX = 1;
			if (super.spriteLocation[0] != target.spriteLocation[0]) {
				this.setSprite("resources/BUG_RIGHT.png");
			}
		}
		if (super.spriteLocation[1] > target.spriteLocation[1]) {
			deltaY = -1;
		} else {
			deltaY = 1;
		}
		
		super.spriteLocation[0] += deltaX * getSpeed();
		super.spriteLocation[1] += deltaY * getSpeed();
		
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
	
	public void paintComponent(Graphics g) {
		drawObj(g);
	}
	
}
