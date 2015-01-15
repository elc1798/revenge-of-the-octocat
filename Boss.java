import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

public class Boss extends Entity{

	private int deltaX = 0;
	private int deltaY = 0;

	private Controller instance = null;
	private Octocat target = null;

	public int id = 0;//This shouldn't really change
	public int facing = 0;

	private Random r = new Random();
	private boolean dead = false;

	public Boss(Controller ctrl , Octocat prey , int _id) {
		instance = ctrl;
		target = prey;
		id = _id;
		setLives(100);
		setDamage(1);
		this.setSpeed(prey.getSpeed() - 1);
		setType("BUG_BOSS");
		setSprite("resources/BUG_BOSS_LEFT.png");
		super.spriteBounds = new int[]{95 , 95};
		super.spriteLocation = new int[]{350 , 300}; //Octocat will spawn at {600 , 300} at boss level
	}

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
	public void setLives(int n) {
		super.setLives(n);
		if (this.getLives() <= 0) {
			//Do death animation here
		}
	}
	public void setSpeed(int n) {
		super.setSpeed(n);
		if (super.getSpeed() <= 0) {
			super.setSpeed(2);
		}
	}
	public void setType(String _type) {
		super.setType(_type);
	}
}
