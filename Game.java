import java.awt.BorderLayout;
import javax.swing.JFrame;

// Anishka Chauhan, 1727 lines of code.
// Game class, creates instance of cardlayout class and sets the JFrame parameters.
public class Game
{
  public static void main (String [] args)
  {
    JFrame frame = new JFrame("Avoir Raison");
    frame.setLayout(new BorderLayout());
		CycleGamesCard panel = new CycleGamesCard(); // Instance of class meant to toggle between panels
    frame.add(panel, BorderLayout.CENTER);
		frame.add(panel);
		frame.setSize(800, 650);
		frame.setLocation(150, 200);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
