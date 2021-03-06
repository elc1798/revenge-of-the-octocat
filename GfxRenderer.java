import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GfxRenderer extends JPanel implements Runnable , ActionListener {

    private Octocat OC;
    private BackGroundLoader bgl;
    private Bug[] enemies;
    private Boss[] bosses;
    private Segfault[] projectiles;
    private Controller instance;
    private Overlay overlay;

    public ArrayList<Powerup> powerups = new ArrayList<Powerup>(); //Made public so Controller can have access
    public ArrayList<Bug> bossesMinions = new ArrayList<Bug>();

    private final int DELAY = 24;
    private Thread animus; //Animation driver
    private boolean alive = true;
    private boolean requireNextLevel = false;
    private boolean startGame = false;

    public boolean requireOverlayReset = false;
    public long overlayChanged = System.currentTimeMillis();

    private long bossSpawnedPowerup = System.currentTimeMillis();
    private long bossSpawnedBugs = System.currentTimeMillis();
    private int minionsSpawned = 0;
    private Random r = new Random();
    private boolean toggledSplash = false;

    public GfxRenderer(Octocat session , BackGroundLoader b , Bug[] bugs , Segfault[] sfs , Controller _instance , Boss[] _bosses) {
        OC = session;
        bgl = b;
        enemies = bugs;
        projectiles = sfs;
        instance = _instance;
        overlay = new Overlay(instance);
        bosses = _bosses;
    }

    public void splashScreen() {
        bgl.loadImage("resources/SPLASHSCREEN.png");
        startGame = false;
    }

    public boolean hasStarted() {
        return startGame;
    }

    public void startSession() {
        if (!toggledSplash) {
            bgl.loadImage("resources/INSTRUCTIONS.png");
            toggledSplash = true;
        } else {
            bgl.loadImage("resources/BKGRND_ENTRY.jpg");
            startGame = true;
            instance.resizeToGamePrefs();
        }
    }

    public void resetPointer(Bug[] bugPointer) {
        enemies = bugPointer;
    }

    public void cleanupPowerups() {
        powerups = null;
        powerups = new ArrayList<Powerup>();
    }

    public void cleanupBossMinions() {
        bossesMinions = null;
        bossesMinions = new ArrayList<Bug>();
    }

    public void spawnBoss() {
        /*
        for (int i = 0; i < instance.getLevel() / 10; i++) {
            bosses[i] = new Boss(instance , OC , i);
        }
         */
        minionsSpawned = 0;
        switch (instance.getLevel()) {
        case 31:
            bosses[2] = new Boss(instance , OC , 2);
        case 21:
            bosses[1] = new Boss(instance , OC , 1);
        case 11:
            bosses[0] = new Boss(instance , OC , 0);
        }
    }

    public void checkVictory() {
        if (!instance.isBossLevel) {
            if (instance.numBugs() <= 0) {
                overlay.victoryScreen();
                if (!requireNextLevel) {
                    requireOverlayReset = true;
                    overlayChanged = System.currentTimeMillis();
                    requireNextLevel = true;
                }
            }
        } else {
            for (Boss bau5 : bosses) {
                if (bau5 != null && bau5.getLives() <= 0) {
                    bau5 = null;
                }
            }
            boolean allBossesDead = true;
            for (Boss bau5 : bosses) {
                if (bau5 != null) {
                    allBossesDead = false;
                }
            }
            if (allBossesDead) {
                overlay.victoryScreen();
                if (!requireNextLevel) {
                    requireOverlayReset = true;
                    overlayChanged = System.currentTimeMillis();
                    requireNextLevel = true;
                }
            }
        }
    }

    public void checkOverlays() {
        if (requireOverlayReset && System.currentTimeMillis() - overlayChanged > 3000) {
            overlay.resetOverlay();
            requireOverlayReset = false;
            if (requireNextLevel) {
                for (Segfault s : projectiles) {
                    if (s != null) {
                        instance.rmAmmo(s.id);
                    }
                }
                instance.nextLevel();
                requireNextLevel = false;
            }
        }
    }

    public void updateBosses() {
        for (int i = 0; i < 3; i++) {
            if (bosses[i] != null && bosses[i].getLives() <= 0) {
                bosses[i] = null;
            }
        }
        for (Boss bau5 : bosses) {
            if (bau5 != null) {
                bau5.move();
                if (System.currentTimeMillis() - bossSpawnedPowerup > 3000) {
                    //Spawn a powerup every 3 seconds
                    bossSpawnedPowerup = System.currentTimeMillis();

                    powerups.add(new Powerup(bau5.spriteLocation.clone()));
                }
                if (System.currentTimeMillis() - bossSpawnedBugs > 3500) {
                    //Spawn a bunch of bugs every 3.5 seconds
                    bossSpawnedBugs = System.currentTimeMillis();
                    int toSpawn = r.nextInt(2) + 1; //Between 1 - 3 bugs
                    //System.out.println(toSpawn);
                    for (int i = 0; i < toSpawn; i++) {
                        bossesMinions.add(new Bug(instance , OC , minionsSpawned));
                        bossesMinions.get(minionsSpawned).spriteLocation[0] = bau5.spriteLocation[0] + r.nextInt(50) - 25;
                        bossesMinions.get(minionsSpawned).spriteLocation[1] = bau5.spriteLocation[1] + r.nextInt(50) - 25;
                        minionsSpawned++;
                    }
                }
            }
        }
    }

    public void updateMinions(Rectangle currOCBounds) {
        for (Bug b : bossesMinions) {
            if (b != null && !b.getType().equals("BUG_GHOST")) {
                b.move();
                if (b.boundaries().intersects(currOCBounds)) {
                    OC.setLives(OC.getLives() - b.getDamage());
                    bossesMinions.set(b.id , null);
                    overlay.rmLifeScreen();
                    requireOverlayReset = true;
                    overlayChanged = System.currentTimeMillis();
                }
            } else if (b != null && b.getType().equals("BUG_GHOST")) {
                b.setLives(-9001);
            }
        }
    }

    public void updateBugs(Rectangle currOCBounds) {
        for (Bug b : enemies) {
            if (b != null && !b.getType().equals("BUG_GHOST")) {
                b.move();
                if (b.boundaries().intersects(currOCBounds)) {
                    OC.setLives(OC.getLives() - b.getDamage());
                    instance.rmBug(b.id);
                    instance.decrementEnemiesLeft();
                    overlay.rmLifeScreen();
                    requireOverlayReset = true;
                    overlayChanged = System.currentTimeMillis();
                }
            } else if (b != null && b.getType().equals("BUG_GHOST")) {
                b.setLives(-9001);
            }
        }
    }

    public void updateSegfaults() {
        for (Segfault s : projectiles) {
            if (s != null) {
                s.move();
                Rectangle currSFBounds = s.boundaries();    //Note: Entity.boundaries() creates a new Rectangle EVERY TIME it is called. To save runtime, create a variable instead of calling it over and over again
                //Prioritize hitting the boss over hitting the bug:
                for (Boss bau5 : bosses) {
                    if (bau5 != null && bau5.getLives() > 0) {
                        if (bau5.boundaries().intersects(currSFBounds)) {
                            bau5.setLives(bau5.getLives() - 1);//Mwahahahaha only 1 damage XD
                            instance.rmAmmo(s.id);
                            break;
                        }
                    }
                }
            }
        }
        for (Segfault s : projectiles) {
            if (s != null) {
                Rectangle currSFBounds = s.boundaries();
                for (Bug b : enemies) {
                    if (b != null && !b.getType().equals("BUG_GHOST")) {
                        if (b.boundaries().intersects(currSFBounds)) {
                            b.setLives(b.getLives() - s.getDamage());
                            instance.rmAmmo(s.id);
                            break;
                        }
                    }
                }
            }
        }
        for (Segfault s : projectiles) {
            if (s != null) {
                Rectangle currSFBounds = s.boundaries();
                for (Bug b : bossesMinions) {
                    if (b != null && !b.getType().equals("BUG_GHOST")) {
                        if (b.boundaries().intersects(currSFBounds)) {
                            bossesMinions.set(b.id , null);
                            overlay.rmLifeScreen();
                            instance.rmAmmo(s.id);
                            requireOverlayReset = true;
                            overlayChanged = System.currentTimeMillis();
                            break;
                        }
                    }
                }
            }
        }
    }

    public void updatePowerups(Rectangle currOCBounds) {
        for (int i = 0; i < powerups.size(); i++) {//I need the index, so use basic for loop instead of for(Powerup p : powerups)
            if (powerups.get(i) != null) {
                if (powerups.get(i).boundaries().intersects(currOCBounds)) {
                    powerups.get(i).givePowerup(OC , instance);
                    if (powerups.get(i).getType().equals("POWERUP_LIFE")) {
                        overlay.addLifeScreen(OC.spriteLocation[0] , OC.spriteLocation[1]);
                        requireOverlayReset = true;
                        overlayChanged = System.currentTimeMillis();
                    }
                    powerups.set(i , null); //Use this rather than calling remove to save runtime. Cleanup later!
                }
            }
        }
    }

    @Override
    public void addNotify() {
        super.addNotify();

        animus = new Thread(this);
        animus.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!startGame) {
            bgl.paintComponent(g);
        } else {
            if (OC != null && OC.getLives() <= 0) {
                String msg = "You have died... Score: " + instance.score;
                Font f = new Font("Helvetica", Font.BOLD, 28);
                FontMetrics metr = this.getFontMetrics(f);
                setBackground(Color.BLACK);
                bgl.loadImage("resources/DEATH_SCREEN.jpg");
                bgl.paintGameOver(g);
                g.setColor(Color.white);
                g.setFont(f);
                g.drawString(msg, (instance.MAX_X - metr.stringWidth(msg)) / 2, instance.MAX_Y + 25);
                alive = false;
                instance.stopKeyListener();
            } else {
                setBackground(Color.BLACK);
                bgl.paintComponent(g);
                if (OC != null) {OC.paintComponent(g);}
                if (instance.getLevel() < 32) {
                    for (Segfault s : projectiles) { if (s != null) { s.paintComponent(g); } }
                    for (Boss bau5 : bosses) { if (bau5 != null) { bau5.paintComponent(g); } }
                    for (Bug b : bossesMinions) { if (b != null) { b.paintComponent(g); } }
                    for (Bug b : enemies) { if (b != null) { b.paintComponent(g); } }
                    for (Powerup p : powerups) { if (p != null) { p.paintComponent(g); } }
                    overlay.paintComponent(g);
                }
                if (OC != null) {
                    Font f = new Font("Helvetica", Font.BOLD, 28);
                    g.setColor(Color.white);
                    g.setFont(f);
                    String score_display = "Score: " + instance.score;
                    String life_display = "Lives: " + OC.getLives();
                    String ammo_display = "Ammo: " + instance.getStock() + "/5";
                    String stat_display = "Speed/Damage: " + OC.getSpeed() + "/" + OC.getDamage();
                    String stage_num = "Stage " + instance.getLevel();
                    g.drawString(score_display , 0 , 620);
                    g.drawString(life_display , instance.MAX_X / 2 , 620);
                    g.drawString(ammo_display , 0 , 660);
                    g.drawString(stat_display , instance.MAX_X / 2 , 660);
                    g.drawString(stage_num , 10 , 40);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        // Do Nothing
    }

    @Override
    public void run() {
        long prevTime , timeDiff , sleepTime;

        prevTime = System.currentTimeMillis();

        while (alive && instance.getLevel() < 32) {

            if (!startGame) {

            } else {
                checkOverlays();
                checkVictory();
                OC.move();
                //Note: Collision detectors would otherwise be in Controller.java, but it would take more loops, so I put it here to save runtime
                Rectangle currOCBounds = OC.boundaries();
                updateBosses();
                updateMinions(currOCBounds);
                updateBugs(currOCBounds);
                updateSegfaults();
                updatePowerups(currOCBounds);
            }

            repaint();

            timeDiff = System.currentTimeMillis() - prevTime;
            sleepTime = DELAY - timeDiff;

            if (sleepTime < 0) {
                sleepTime = 2; // Do not allow negative sleepTime
            }

            try {
                Thread.sleep(sleepTime);
            } catch(InterruptedException ie) {
                System.out.println("Interruption during Thread.sleep, GfxRenderer.java Line 378");
                System.out.println("Sending SIGTERM to process...");
                System.exit(0);
            }

            prevTime = System.currentTimeMillis();

        }

        if (instance.getLevel() >= 32) {
            bgl.loadImage("resources/BKGRND_VICTORY.png");
            OC = null;
            instance.resizeToStatic();
            repaint();
        }

    }

}
