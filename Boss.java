import java.awt.Graphics;
import java.awt.Image;

public class Boss extends Entity{

    private int deltaX = -1;
    private int deltaY = -1;

    private Controller instance = null;
    private Octocat target = null;

    public int id = 0;//This shouldn't really change
    public int facing = 0;
    private int[] actualBounds = new int[]{0,0};

    public Boss(Controller ctrl , Octocat prey , int _id) {
        instance = ctrl;
        target = prey;
        id = _id;
        super.setLives(50);
        //super.setLives(1);
        super.setDamage(1);
        this.setSpeed(prey.getSpeed() - 1);
        super.setType("BUG_BOSS");
        super.setSprite("resources/BUG_BOSS_LEFT.png");
        super.spriteBounds = new int[]{95 , 95};
        super.spriteLocation = new int[]{350 , 300};
        this.actualBounds = new int[]{spriteLocation[0] + 47 , spriteLocation[1] + 47 };
        //Octocat will spawn at {600 , 300} at boss level
        target.spriteLocation = new int[]{600 , 300};
    }

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
    public void setLives(int n) {
        System.out.println(n);
        super.setLives(n);
        if (this.getLives() <= 0) {
            //Do death animation here
        }
    }
    public void setSpeed(int n) {
        super.setSpeed(n);
        if (super.getSpeed() <= 0) {
            super.setSpeed(2);
        }
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
    public void facing(){
        if (deltaX == -1) {
            if (deltaY == -1) {
                facing = 7;
            }
            else if (deltaY == 0) {
                facing = 1;
            }
            else if (deltaY == 1) {
                facing = 5;
            }
        }
        else if (deltaX == 0) {
            if (deltaY == -1){
                facing = 3;
            }
            else if (deltaY == 0) {
                //MEANINGLESS LINES OF CODE!~
                //Stationary Bugs
            }
            else if (deltaY == 1) {
                facing = 2;
            }
        }
        else if (deltaX == 1){
            if (deltaY == -1) {
                facing = 6;
            }
            else if (deltaY == 0) {
                facing = 0;
            }
            else if (deltaY == 1) {
                facing  = 4;
            }
        }
    }

    public boolean closeToOctocat() {
        return  distanceFromOctocat() < 90;
    }

    public boolean octocatPersonalBubble() {
        return distanceFromOctocat() < 200;
    }
    public int distanceFromEntity(Entity other){
        return (int)(Math.sqrt(Math.pow(this.spriteLocation[0]-other.spriteLocation[0],2)+Math.pow(this.spriteLocation[1]-other.spriteLocation[1],2)));
    }
    public int distanceFromOctocat(){
        return (int)(Math.sqrt(Math.pow(this.actualBounds[0]-target.actualBounds[0],2)+Math.pow(this.actualBounds[1]-target.actualBounds[1],2)));
    }

    public void move() {

        //Prevents octocat from zooming past it

        this.setSpeed(target.getSpeed() - 1);

        //Avoid getting killed by the octocat

        if (this.spriteLocation[0] > target.spriteLocation[0]) {
            if (Math.abs(distanceFromOctocat() - 200) < this.getSpeed()){
                deltaX=0;
            } else if (!octocatPersonalBubble()) {
                deltaX = -1;
                this.setSprite("resources/BUG_BOSS_LEFT.png");
            } else {
                deltaX = 1;
                //this.setSprite("resources/BUG_BOSS_RIGHT.png");
            }
        } else {
            if (Math.abs(distanceFromOctocat() - 200) < this.getSpeed()){
                deltaX=0;
            } else if (!octocatPersonalBubble()) {
                deltaX = 1;
                //this.setSprite("resources/BUG_BOSS_RIGHT.png");
            } else {
                deltaX = -1;
                this.setSprite("resources/BUG_BOSS_LEFT.png");
            }
        }
        if (this.spriteLocation[1] < target.spriteLocation[1]) {
            if (Math.abs(distanceFromOctocat() - 200) < this.getSpeed()){
                deltaY=0;
            } else if (!octocatPersonalBubble()) {
                deltaY = 1;
            } else {
                deltaY = -1;
            }
        } else {
            if (Math.abs(distanceFromOctocat() - 200) < this.getSpeed()){
                deltaY=0;
            } else if (!octocatPersonalBubble()) {
                deltaY = -1;
            } else {
                deltaY = 1;
            }
        }

        //Move:


        super.spriteLocation[0] += deltaX * getSpeed();
        super.spriteLocation[1] += deltaY * getSpeed();

        this.actualBounds = new int[]{spriteLocation[0] + 47 , spriteLocation[1] + 47 };

        //Teleport
        if (closeToOctocat()) { //If the Mega-Bug is too close, it will hop over the Octocat and run :)
            if (super.spriteLocation[0] > target.spriteLocation[0] && super.spriteLocation[1] < target.spriteLocation[1]) {
                //Boss is upper right from octocat
                super.spriteLocation[0] = target.spriteLocation[0] - 100;
                super.spriteLocation[1] = target.spriteLocation[1] + 100;
            } else if (super.spriteLocation[0] > target.spriteLocation[0] && super.spriteLocation[1] > target.spriteLocation[1]) {
                //Lower right
                super.spriteLocation[0] = target.spriteLocation[0] - 100;
                super.spriteLocation[1] = target.spriteLocation[1] - 100;
            } else if (super.spriteLocation[0] < target.spriteLocation[0] && super.spriteLocation[1] > target.spriteLocation[1]) {
                //Lower left
                super.spriteLocation[0] = target.spriteLocation[0] + 100;
                super.spriteLocation[1] = target.spriteLocation[1] - 100;
            } else if (super.spriteLocation[0] < target.spriteLocation[0] && super.spriteLocation[1] < target.spriteLocation[1]) {
                //Upper left
                super.spriteLocation[0] = target.spriteLocation[0] + 100;
                super.spriteLocation[1] = target.spriteLocation[1] + 100;
            }
            this.actualBounds = new int[]{spriteLocation[0] + 47 , spriteLocation[1] + 47 };

            //Update speeds
            if (this.spriteLocation[0] < target.spriteLocation[0]) {
                deltaX = 1;
            } else {
                deltaX = -1;
            }
            if (this.spriteLocation[1] < target.spriteLocation[1]) {
                deltaY = 1;
            } else {
                deltaY = -1;
            }
        }

        //Avoid Overlapping other Bosses
        for (int i = 1; i < instance.getLevel(); i += 10){
            Boss other = instance.getBoss((int)(i/10));
            if (other != null && other != this){
                int diffX = this.spriteLocation[0] - other.spriteLocation[0];
                int diffY = this.spriteLocation[1] - other.spriteLocation[1];
                if (distanceFromEntity(other) > 30) {
                } else if (diffX == 0 && diffY == 0){
                    this.spriteLocation[0] -= 100;
                    this.spriteLocation[1] -= 100;
                } else if (diffX == 0){
                    this.spriteLocation[1] += Math.abs(diffY)/diffY * this.getSpeed();
                } else if (diffY == 0){
                    this.spriteLocation[0] += Math.abs(diffX)/diffX * this.getSpeed();
                } else  {
                    this.spriteLocation[0] += Math.abs(diffX)/diffX * this.getSpeed();
                    this.spriteLocation[1] += Math.abs(diffY)/diffY * this.getSpeed();
                }
                this.actualBounds = new int[]{spriteLocation[0] + 47 , spriteLocation[1] + 47};
            }
        }



        //Position correction:
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


        //Update direction
        this.facing();

    }

    public void paintComponent(Graphics g) {
        drawObj(g);
    }
}
