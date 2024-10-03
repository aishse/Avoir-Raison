import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

import java.io.IOException;
import javax.imageio.ImageIO;

//  This panel is shown if the user decides to leave the game and go to the main menu. It uses an action listener for the buttons toggling between the main menu and the game.

public class ConfirmMainMenu extends JPanel implements ActionListener
{
  // private field variables
  private CardLayout listOfCards; // CardLayout instance
  private CycleGamesCard primaryPanel; // instance of class that cycles between the various cards
  private GameData data; // instance of game data class in order to reset previous game's data
  private Font myFont; // font used in the class
  private Image image;  // background image

  public ConfirmMainMenu (GameData d, CardLayout c, CycleGamesCard p)
  {
    listOfCards = c;
    data = d;
    primaryPanel = p;
    myFont = new Font("Andale Mono", Font.BOLD, 40);

    // grabs the panel's background image
    image = Toolkit.getDefaultToolkit().getImage("bkg1.png");

    setBackground(new Color(2, 58, 87));
    setLayout(new BorderLayout(10, 40));

    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(new Color(0, 105, 110, 1)); // transparent JPanel to allow the image to be visible
    buttonPanel.setLayout(new GridLayout(2, 1, 2, 2));
    add(buttonPanel, BorderLayout.SOUTH);

    JPanel leaderboardPanel = new JPanel();
    leaderboardPanel.setBackground(new Color(0, 105, 110, 1));
    leaderboardPanel.setLayout(new FlowLayout());
    buttonPanel.add(leaderboardPanel);

    JButton leaderboard = new JButton("BACK TO GAME"); // JButton for toggling back to the game
    leaderboard.setFont(myFont);
    leaderboard.addActionListener(this);
    leaderboardPanel.add(leaderboard);

    JPanel mainMenuPanel = new JPanel();
    mainMenuPanel.setBackground(new Color(0, 105, 110, 1));
    mainMenuPanel.setLayout(new FlowLayout());
    buttonPanel.add(mainMenuPanel);

    JButton mainMenu = new JButton("BACK TO MAIN MENU"); // JButton for continuing to the main menu
    mainMenu.setFont(myFont);
    mainMenu.addActionListener(this);
    mainMenuPanel.add(mainMenu);

    waitForImage(this, image); // method for finding image in directory
  }
  // tries to find the image for the background
  public void waitForImage(JPanel component, Image image)
	{
		MediaTracker tracker = new MediaTracker(component);
		try
		{
			tracker.addImage(image, 0);
			tracker.waitForID(0);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}

  // Draws the image and a confirmation message
  public void paintComponent (Graphics g)
  {
      g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
      g.setFont(new Font("Din Condensed", Font.BOLD, 200));
      g.setColor(Color.BLACK);
      g.drawString("STOP!", 220, 300);
      g.setColor(Color.WHITE);
      g.drawString("STOP!", 210, 300);
      g.setFont(new Font("Din Condensed", Font.BOLD, 40));
      g.setColor(Color.WHITE);
      g.drawString("Are you sure you want to quit? Your game WON'T be saved.", 40, 400);

  }

  public void actionPerformed (ActionEvent evt)
  {
    String command = evt.getActionCommand();
    if (command.equals("BACK TO GAME"))
    {
      listOfCards.show(primaryPanel, "2"); // goes back to the game
    }
    if (command.equals("BACK TO MAIN MENU"))
    {
      data.reset(); // resets data and goes back to main menu
      listOfCards.show(primaryPanel, "1");
    }
  }
}
