import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.ImageIcon;

public class Overlay { //Overlays are simply 'sprites' that appear on top of the JFrame to display messages as a picture (fancy text)
	private Image sprite;
	private ImageIcon loader = null;
	
	public int[] spriteBounds;
	public int[] spriteLocation; // [xcoor , ycoor]
	private Rectangle spriteRect;
	private Controller instance;

	public Overlay(Controller _instance) {
		instance = _instance;
		setSprite("resources/OVERLAY_BLANK.png");
		spriteBounds = new int[]{instance.MAX_X , instance.MAX_Y};
		spriteLocation = new int[]{instance.MIN_X , instance.MIN_Y};
	}
	
	public Image getSprite() {
		return sprite;
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
	
	public void victoryScreen() {
		setSprite("resources/OVERLAY_VICTORY");
		instance.nextLevel();
	}
	
	public Rectangle boundaries() {
		spriteRect = new Rectangle(spriteLocation[0] , spriteLocation[1] , spriteBounds[0] , spriteBounds[1]);
		return spriteRect;
	}
			
	public void drawObj(Graphics g) {
		//Use keyword 'this' to clarify variables of class Entity, not of subclasses
		g.drawImage(this.sprite , this.spriteLocation[0] , this.spriteLocation[1] , this.spriteBounds[0] , this.spriteBounds[1] , null);
		Toolkit.getDefaultToolkit().sync();
	}
	
	public void paintComponent(Graphics g) {
		drawObj(g);
	}
	
}
