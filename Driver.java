import java.awt.EventQueue;


public class Driver {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                Controller gameFrame = new Controller();
                gameFrame.setVisible(true);
            }
            
        });
    }
    
}
