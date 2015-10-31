import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.ImageIcon;

public class Entity {

    private int lives;
    private int damage;
    private int speed;
    private String type;
    private Image sprite;
    private ImageIcon loader = null;

    public int[] spriteBounds;
    public int[] spriteLocation; // [xcoor , ycoor]
    private Rectangle spriteRect;

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
        //System.out.println(type + ": " + lives);
        if (lives <= 0) {
            //System.out.println(type + " died");
        }
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
        PIC = PIC.replace("/" , Controller.DIRMARKER);
        if (new File(PIC).exists()) {
            loader = new ImageIcon(PIC);
            sprite = loader.getImage();
            loader = null; //Reset for next setSprite
        } else {
            System.out.println("Error: File " + PIC + " not found. Exitting...");
            System.exit(0);
        }
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

}
