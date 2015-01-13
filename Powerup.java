import java.awt.Graphics;
import java.util.Random;


public class Powerup extends Entity {

	//Powerups don't move !
	//Powerups are invincible and cannot be hit by anything!
	
	int choice = -1;
	
	public Powerup(int[] spriteLoc) {
		Random r = new Random();
		/*
		 * 0 - 2	: Speed
		 * 3 - 7	: +1 Life
		 * 8 - 10	: +1 Damage
		 * 11 - 13	: +1 Ammo
		 */
		choice = r.nextInt(14);
		super.spriteLocation = spriteLoc;
		super.spriteBounds = new int[]{30 , 30};
		super.setLives(Integer.MAX_VALUE);
		super.setDamage(Integer.MAX_VALUE);
		super.setType("POWERUP");
		super.setSpeed(0);
		if (0 <= choice && choice <= 2) {
			super.setSprite("resources/POWERUP_SPEED.png");
		} else if (3 <= choice && choice <= 7) {
			super.setSprite("resources/POWERUP_LIFE.png");
			super.setType("POWERUP_LIFE");
		} else if (8 <= choice && choice <= 10) {
			super.setSprite("resources/POWERUP_DAMAGE.png");
		} else if (11 <= choice && choice <= 13) {
			super.setSprite("resources/POWERUP_AMMO.png");
		} else {
			System.out.println("Error: Invalid ID for powerup. Exitting...");
			System.exit(0);
		}
		
	}
	
	public void givePowerup(Octocat player , Controller instance) {
		if (0 <= choice && choice <= 2) {
			player.setSpeed(player.getSpeed() + 1);
		} else if (3 <= choice && choice <= 7) {
			player.setLives(player.getLives() + 1);
		} else if (8 <= choice && choice <= 10) {
			player.setDamage(player.getDamage() + 1);
		} else if (11 <= choice && choice <= 13) {
			instance.addAmmo();
		} else {
			System.out.println("Error: Invalid ID for powerup. Exitting...");
			System.exit(0);
		}
	}
	
	public void paintComponent(Graphics g){
		super.drawObj(g);
	}
	
}
