import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

public class Octocat extends Entity {
	private int deltaX = 0;
	private int deltaY = 0;
	private final int STOP = 0;
	
	private Controller instance = null;
	
	//Constructor!
	
	public Octocat(Controller ctrl) {
		instance = ctrl;
		setLives(3);
		setDamage(1);
		setSpeed(1);
		setType("OCTOCAT_HEALTHY");
		setSprite("resources/OCTOCAT_HEALTHY.jpg");
		super.spriteBounds = new int[]{50 , 50};
		super.spriteLocation = new int[]{ctrl.MAX_X / 2 , ctrl.MAX_Y / 2};
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
	
	public void keyPressed(KeyEvent e) {
		int id = e.getKeyCode();
		
		switch (id) {
		
		case KeyEvent.VK_LEFT:
			deltaX = -1;
			break;
		case KeyEvent.VK_RIGHT:
			deltaX = 1;
			break;
		case KeyEvent.VK_UP:
			deltaY = -1;
			break;
		case KeyEvent.VK_DOWN:
			deltaY = 1;
			break;
		}
		
	}
	
	public void keyReleased(KeyEvent e) {
		int id = e.getKeyCode();
		
		switch (id) {
		
		case KeyEvent.VK_LEFT:
			deltaX = STOP;
			break;
		case KeyEvent.VK_RIGHT:
			deltaX = STOP;
			break;
		case KeyEvent.VK_UP:
			deltaY = STOP;
			break;
		case KeyEvent.VK_DOWN:
			deltaY = STOP;
			break;
		}
	}
	
	public void paintComponent(Graphics g) {
		drawObj(g);
	}
	
	
}
