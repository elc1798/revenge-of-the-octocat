import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;


public class Bug extends Entity {
	private int deltaX = 0;
	private int deltaY = 0;

	private Controller instance = null;
	private Octocat target = null;

	public int id = 0;
	public int facing = 0;

	private Random locationSetter = new Random();
	private long deathTime;
	private boolean dead = false;
	private Rectangle moveRect = null;

	//Constructor!

	public Bug(Controller ctrl , Octocat prey , int _id) {
		instance = ctrl;
		target = prey;
		id = _id;
		setLives(1 + (int)(instance.getLevel() / 2));
		setDamage(1);
		if (instance.getLevel() <= 3) {
			setSpeed(1);
		} else {
			setSpeed(instance.getLevel() / 2);
		}
		setType("BUG_HEALTHY");
		setSprite("resources/BUG_RIGHT.png");
		super.spriteBounds = new int[]{70 , 70};
		int init_X = target.spriteLocation[0];
		int init_Y = target.spriteLocation[1];
		for (int i = 0; i < 2 * instance.getLevel() - 1; i++){
			if (instance.getBug(i) != null){
				int tries = 0;
				while (tries < 5 && Math.abs(init_X - instance.getBug(i).spriteLocation[0]) < 70) { // Do not allow spawn within 50 units of player! (Cheap way!)
					init_X = locationSetter.nextInt(instance.MAX_X); //Note we do not need to add instance.MIN_X because it's 0!
					tries++;
				}
				tries = 0;
				while (tries < 5 && Math.abs(init_X - instance.getBug(i).spriteLocation[0]) < 70) {
					init_Y = locationSetter.nextInt(instance.MAX_Y);
					tries++;
				}
				//TODO: Revise the init_LOC setters to be more efficient. Loops are definitely NOT required here.
			}
		}
		while (Math.abs(init_X - target.spriteLocation[0]) < 50) { // Do not allow spawn within 50 units of player! (Cheap way!)
			init_X = locationSetter.nextInt(instance.MAX_X); //Note we do not need to add instance.MIN_X because it's 0!
		}
		while (Math.abs(init_Y - target.spriteLocation[1]) < 50 ) {
			init_Y = locationSetter.nextInt(instance.MAX_Y);	
		}
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
			if (!dead && instance.numBugs() > 1) {
				dead = true;
				deathTime = System.currentTimeMillis();
				this.setType("BUG_GHOST");
				this.setSprite("resources/BUG_GHOST.png");
				super.spriteBounds[0] = 107;
				super.spriteBounds[1] = 20;
				instance.decrementEnemiesLeft();
				instance.spawnPowerup(this.id);
			} else if (!dead && instance.numBugs() <= 1) { // Do not create ghost if last bug
				this.setType("BUG_GHOST");
				instance.decrementEnemiesLeft();
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
		if (n < 6) {
			super.setSpeed(n);
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
	public void facing(){
		if (deltaX == -1) {
			if (deltaY == -1) {
				facing = 7;
			}
			else if (deltaY == 0) {
				facing = 1;
			}
			else if (deltaY == 1) {
				facing = 5;
			}
		}
		else if (deltaX == 0) {
			if (deltaY == -1){
				facing = 3;
			}
			else if (deltaY == 0) {
				//MEANINGLESS LINES OF CODE!~
				//Stationary Bugs
			}
			else if (deltaY == 1) {
				facing = 2;
			}
		}
		else if (deltaX == 1){
			if (deltaY == -1) {
				facing = 6;
			}
			else if (deltaY == 0) {
				facing = 0;
			} 
			else if (deltaY == 1) {
				facing  = 4;
			} 
		}
	}

	public void move() {//Movement alg
		//System.out.println("Bug moved");
		//Chase after the octocat!
		boolean impact = false;
		Bug other = null;
		for (int i = 0; i < 2 * instance.getLevel();i++){
			if (instance.getBug(i) != null && instance.getBug(i).id != this.id && !instance.getBug(i).getType().equals("BUG_GHOST")){
				if (this.moveBoundaries().intersects(instance.getBug(i).moveBoundaries())){
					impact = true;
					other = instance.getBug(i);
				}
			}
		}
		if (super.spriteLocation[0] > target.spriteLocation[0]) {
			this.setSprite("resources/BUG_LEFT.png");
			if (!impact){
				deltaX = -1;
				facing();
			} else {
				switch (other.facing){ 

				case 0:
					if (super.spriteLocation[0] < other.spriteLocation[0]){
						deltaX = -1;
					} else {
						deltaX = 1;
					} 
					break;
				case 1:
					deltaX = -1;
					break;
				case 2:
					if (super.spriteLocation[0] < other.spriteLocation[0]){
						deltaX = -1;
					} else {
						deltaX = 1;
					}
					break;
				case 3:
					if (super.spriteLocation[0] < other.spriteLocation[0]){
						deltaX = -1;
					} else {
						deltaX = 1;
					}
					break;
				case 4:
					if (super.spriteLocation[0] < other.spriteLocation[0]){
						deltaX = -1;
					} else {
						deltaX = 1;
					}
					break;
				case 5:
					deltaX = -1;
					break;
				case 6:
					if (super.spriteLocation[0] < other.spriteLocation[0]){
						deltaX = -1;
					} else {
						deltaX = 1;
					}
					break;		    
				case 7:
					deltaX = -1;
					break;		    
				}
			}
		} else {
			if (super.spriteLocation[0] != target.spriteLocation[0]) {
				this.setSprite("resources/BUG_RIGHT.png");
			}
			if (!impact){
				deltaX = 1;
				facing();
			} else {

				switch (other.facing){ 

				case 0:
					deltaX = 1;
					break;
				case 1:
					if (super.spriteLocation[0] > other.spriteLocation[0]){
						deltaX = 1;
					} else {
						deltaX = -1;
					} 
					break;
				case 2:
					if (super.spriteLocation[0] > other.spriteLocation[0]){
						deltaX = 1;
					} else {
						deltaX = -1;
					}
					break;
				case 3:
					if (super.spriteLocation[0] > other.spriteLocation[0]){
						deltaX = 1;
					} else {
						deltaX = -1;
					}
					break;
				case 4:
					deltaX = 1;
				case 5:
					if (super.spriteLocation[0] > other.spriteLocation[0]){
						deltaX = 1;
					} else {
						deltaX = -1;
					}
					break;		    
				case 6:
					deltaX = 1;		    
					break;
				case 7:
					if (super.spriteLocation[0] > other.spriteLocation[0]){
						deltaX = 1;
					} else {
						deltaX = -1;
					}
					break;
				}
			}
		}

		if (super.spriteLocation[1] > target.spriteLocation[1]) {
			if (!impact){
				deltaY = -1;
				facing();
			} else {
				switch (other.facing){

				case 0:
					if (super.spriteLocation[1] < other.spriteLocation[1]){
						deltaY = -1;
					} else {
						deltaY = 1;
					} 
					break;
				case 1:
					if (super.spriteLocation[1] < other.spriteLocation[1]){
						deltaY = -1;
					} else {
						deltaY = 1;
					} 
					break;
				case 2:
					if (super.spriteLocation[1] < other.spriteLocation[1]){
						deltaY = -1;
					} else {
						deltaY = 1;
					}
					break;
				case 3:
					deltaY = -1;
					break;
				case 4:
					if (super.spriteLocation[1] < other.spriteLocation[1]){
						deltaY = -1;
					} else {
						deltaY = 1;
					}
					break;
				case 5:
					if (super.spriteLocation[1] < other.spriteLocation[1]){
						deltaY = -1;
					} else {
						deltaY = 1;
					}
					break;		    
				case 6:
					deltaY = -1;		    
					break;
				case 7:
					deltaY = -1;
					break;
				}
			}
		} else {
			if (!impact){
				deltaY = 1;
				facing();
			} else {
				switch (other.facing){

				case 0:
					if (super.spriteLocation[1] > other.spriteLocation[1]){
						deltaY = 1;
					} else {
						deltaY = -1;
					} 
					break;
				case 1:
					if (super.spriteLocation[1] > other.spriteLocation[1]){
						deltaY = 1;
					} else {
						deltaY = -1;
					} 
					break;
				case 2:
					deltaY = 1;
					break;
				case 3:
					if (super.spriteLocation[1] > other.spriteLocation[1]){
						deltaY = 1;
					} else {
						deltaY = -1;
					}
					break;
				case 4:
					deltaY = 1;
					break;
				case 5:
					deltaY = 1;
					break;		    
				case 6:
					if (super.spriteLocation[1] > other.spriteLocation[1]){
						deltaY = 1;
					} else {
						deltaY = -1;
					}		    
					break;
				case 7:
					if (super.spriteLocation[1] > other.spriteLocation[1]){
						deltaY = 1;
					} else {
						deltaY = -1;
					}
					break;
				}
			}
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
		//	System.out.println("done"); 	
	}

	/*	public void bounceBack(Bug other){
	if (this.spriteLocation[0] < other.spriteLocation[0]) {
	switch (deltaX) {
	case -1:
	this.move();
	break;
	case 0:
	deltaX = -1;
	this.move();
	deltaX = 0;
	break;
	case 1:
	deltaX = -1;
	this.move();
	deltaX = 1;
	break;
	default:
	System.out.println("Invalid values detected! Exitting...");
	System.exit(0);
	}
	} else if (this.spriteLocation[0] > other.spriteLocation[0]) {
	switch (deltaX) {
	case -1:
	deltaX=1;
	this.move();
	deltaX=-1;
	break;
	case 0:
	deltaX=1;
	this.move();
	deltaX=0;
	break;
	case 1:
	this.move();
	break;
	default:
	System.out.println("Invalid values detected! Exitting...");
	System.exit(0);
	}
	}
	if (this.spriteLocation[1] < other.spriteLocation[1]) {
	switch (deltaY) {
	case -1:
	this.move();
	break;
	case 0:
	deltaY = -1;
	this.move();
	deltaY = 0;
	break;
	case 1:
	deltaY = -1;
	this.move();
	deltaY = 1;
	break;
	default:
	System.out.println("Invalid values detected! Exitting...");
	System.exit(0);
	}
	} else if (this.spriteLocation[1] > other.spriteLocation[1]) {
	switch (deltaY) {
	case -1:
	deltaY=1;
	this.move();
	deltaY=-1;
	break;
	case 0:
	deltaY=1;
	this.move();
	deltaY=0;
	break;
	case 1:
	this.move();
	break;
	default:
	System.out.println("Invalid values detected! Exitting...");
	System.exit(0);
	}
	}
	}
	 */	
	public void paintComponent(Graphics g) {
		drawObj(g);
	}

	public Rectangle moveBoundaries() {
		moveRect = new Rectangle(super.spriteLocation[0] - 5, super.spriteLocation[1] - 5, super.spriteBounds[0] + 10, spriteBounds[1] + 10);
		return moveRect;
	}

}
