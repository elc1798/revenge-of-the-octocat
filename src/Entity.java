public class Entity {

	private int lives;
	private int speed;
	private int damage;
	private String type;
	
	public int getLives() {
		return this.lives;
	}
	
	public int getSpeed() {
		return this.speed;
	}
	
	public int getDamage() {
		return this.damage;
	}
	
	public String getType() {
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
	
	public void setType(String s) {
		this.type = s;
	}
	
}
