import java.awt.image.BufferedImage;


public class Segfault extends Entity { //Segfaults are Octocat's ranged projectiles

	public Segfault() {
		//Base Stats:
		super.setLives(9999999);
		super.setSpeed(10);
		super.setDamage(1);
		super.setType(7);
	}
	
	public int getLives() {return super.getLives();}
	public int getSpeed() {return super.getSpeed();}
	public int getDamage() {return super.getDamage();}
	public int getType() {return super.getType();}
	public BufferedImage getSprite() {return super.getSprite();}
	
	public void setLives(int n) {super.setLives(n);}
	public void setSpeed(int n) {super.setSpeed(n);}
	public void setDamage(int n) {super.setDamage(n);}
	public void setType(int n) {super.setType(n);}
	public void setSprite(String s) {super.setSprite(s);}
	
}
