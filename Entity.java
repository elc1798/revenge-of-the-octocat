import java.awt.Image;
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
	
	public int[] spriteBounds = new int[2];
	public int[] spriteLocation = new int[2]; // [xcoor , ycoor]
	
	private final int DELAY = 24; //24 ms
	
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
			System.out.println("Error: File " + PIC + "not found. Exitting...");
			System.exit(0);
		}
	}
	
	private void move() {//Movement alg
		//TO BE DEFINED IN SUBCLASSES
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		long prevTime , timeDiff , sleepTime;
		
		prevTime = System.currentTimeMillis();
		
		while (true) {
			
			move();
			repaint();
			
			timeDiff = System.currentTimeMillis() - prevTime;
			sleepTime = DELAY - timeDiff;
			
			if (sleepTime < 0) {
				sleepTime = 2; // Do not allow negative sleepTime
			}
			
			try {
				Thread.sleep(sleepTime);
			} catch(InterruptedException ie) {
				System.out.println("Interruption during Thread.sleep, Animation.java line 89");
				System.out.println("Sending SIGTERM to process...");
				System.exit(0);
			}
			
			prevTime = System.currentTimeMillis();
			
		}
	}

}
