import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Entity {

	private int lives;
	private int speed;
	private int damage;
	private int type;
	private BufferedImage sprite = null;
	
	public int getLives() {
		return this.lives;
	}
	
	public int getSpeed() {
		return this.speed;
	}
	
	public int getDamage() {
		return this.damage;
	}
	
	public BufferedImage getSprite() {
		return this.sprite;
	}
	
	public int getType() {
		return this.type;
	}
	
	public void setLives(int n) {
		this.lives = n;
	}
	
	public void setSpeed(int n) {
		this.speed = n;
	}
	
	public void setDamage(int n) {
		this.damage = n;
	}
	
	public void setType(int n) {
		/*
		 * Valid Types:
		 * 	0	OCTOCAT_HEALTHY
		 * 	1	OCTOCAT_HURT
		 * 	2	OCTOCAT_DEATH
		 * 	3	OCTOCAT_VICTORY
		 * 	4	BUG_HEALTHY
		 * 	5	BUG_HURT
		 * 	6	BUG_DEATH
		 * 	7	SEGFAULT_SHOOT
		 * 	8	SEGFAULT_IMPACT
		 * 	9	SEGFAULT_EXPLOSION
		 */
		if (n < 10 && n >= 0) {
			this.type = n;
		} else {
			System.out.println("Cannot set type to: " + n);
			System.exit(0);
		}
	}
	
	public void setSprite(String s) {
		try {
			sprite = ImageIO.read(new File(s));
		} catch (IOException e) {
			System.out.println("Could not find image: " + s);
			e.printStackTrace();
			System.exit(0);
		}
	}
	
}
