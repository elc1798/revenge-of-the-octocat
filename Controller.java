import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;


@SuppressWarnings("serial")
public class Controller extends JFrame {
	public final int MAX_X = 950;
	public final int MAX_Y = 600;
	public final int MIN_X = 0;
	public final int MIN_Y = 0;
	
	private Octocat player;
	private BackGroundLoader bgl;
	private GfxRenderer gfx;
	
	private class inputAdapter extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			player.keyReleased(e);
		}
		
		public void keyPressed(KeyEvent e) {
			player.keyPressed(e);
		}
	}
	
	public Controller() {
		bgl = new BackGroundLoader("resources/BKGRND_ENTRY.jpg");
		player = new Octocat(this);
		gfx = new GfxRenderer(player , bgl);
		
		setPreferredSize(new Dimension(950 , 600));
		
		add(gfx);
		addKeyListener(new inputAdapter());
		pack();
		
		setTitle("Revenge of the Octocat!");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

}
