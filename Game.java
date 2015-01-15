import java.awt.EventQueue;


public class Game {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				Controller gameFrame = new Controller();
				gameFrame.setVisible(true);
				
			}
			
		});
	}
	
}
