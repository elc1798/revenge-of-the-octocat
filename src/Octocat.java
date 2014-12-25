import java.awt.image.BufferedImage;

public class Octocat extends Entity{

	public Octocat() {
		//Base Stats:
		super.setLives(3);
		super.setSpeed(2);
		super.setDamage(1);
		super.setType(0);
	}
	
	public int getLives() {return super.getLives();}
	public int getSpeed() {return super.getSpeed();}
	public int getDamage() {return super.getDamage();}
	public int getType() {return super.getType();}
	public BufferedImage getSprite() {return super.getSprite();}
	
	public void setLives(int n) {super.setLives(n);}
	public void setSpeed(int n) {super.setSpeed(n);}
	public void setDamage(int n) {super.setDamage(n);}
	//No set type! You cannot change thy Octocat
	public void setSprite(String s) {super.setSprite(s);}
	
}
