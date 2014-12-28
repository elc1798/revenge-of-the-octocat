import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class BackGroundLoader {
	
	private Image bkgrnd;
	private ImageIcon loader = null;
	
	public BackGroundLoader(String PIC) {
		loadImage(PIC);
	}
	
	private void loadImage(String PIC) {
		loader = new ImageIcon(PIC);
		System.out.println("Found " + PIC + "? " + new File(PIC).exists() + "");
		bkgrnd = loader.getImage();
	}
	
	public void paintComponent(Graphics g) {//Background paint
		g.drawImage(bkgrnd , 0 , 0 , 950 , 600 , null);
	}
	
}
