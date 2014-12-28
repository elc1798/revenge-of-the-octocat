import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Entity extends JPanel implements Runnable , ActionListener {

	private int lives;
	private int damage;
	private int speed;
	private String type;
	private Image sprite;
	private ImageIcon loader = null;
	
	public int[] spriteBounds;
	public int[] spriteLocation; // [xcoor , ycoor]
	
	private final int DELAY = 24; //24 ms
	private Thread animus; //Animation driver
	
	//Data retrievers
	public int getLives() {
		return lives;
	}
	public int getDamage() {
		return damage;
	}
	public int getSpeed() {
		return speed;
	}
	public String getType() {
		return type;
	}
	public Image getSprite() {
		return sprite;
	}
	//Data setters
	public void setLives(int n) {
		lives = n;
	}
	public void setDamage(int n) {
		damage = n;
	}
	public void setSpeed(int n) {
		speed = n;
	}
	public void setType(String _type) {
		type = _type;
	}
	public void setSprite(String PIC) {
		if (new File(PIC).exists()) {
			loader = new ImageIcon(PIC);
			sprite = loader.getImage();
			loader = null; //Reset for next setSprite
		} else {
			System.out.println("Error: File " + PIC + " not found. Exitting...");
			System.exit(0);
		}
	}
	
	public void drawObj(Graphics g) {
		//Use keyword 'this' to clarify variables of class Entity, not of subclasses
		g.drawImage(this.sprite , this.spriteLocation[0] , this.spriteLocation[1] , this.spriteBounds[0] , this.spriteBounds[1] , null);
		Toolkit.getDefaultToolkit().sync();
	}
	
	private void move() {//Movement alg
		//TO BE DEFINED IN SUBCLASSES
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		//TO BE DEFINED IN SUBCLASSES
		//Incorporates move
	}
}
