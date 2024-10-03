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

// Similar to ConfirmMainMenu, this panel also shows a confirmation message, but only if the user decides to exit the game from the game over panel. This is because when the user
// leaves the game to go to the main menu, their score will not be saved due to them not completing the game, but when they leave from the end panel, their points have to be saved as they have
// completed the game. The layout is the same of both panels, but the message says the game will be saved, unlike the ConfirmMainMenu panel, which says the game will not. This is clearly
// highlighted both panels' messages. ActionListeners are used for the buttons to either exit the game or go back to the main menu, and an image is drawn for the background.
public class ConfirmGameOver extends JPanel implements ActionListener
{
  private CardLayout listOfCards; // cardlayout instance
  private CycleGamesCard primaryPanel; // instance of class that cycles between the classes/panels
  private Font myFont; // font
  private Image image; // background image

  public ConfirmGameOver (CardLayout c, CycleGamesCard p)
  {
    listOfCards = c;
    primaryPanel = p;
    myFont = new Font("Andale Mono", Font.BOLD, 40);
    image = Toolkit.getDefaultToolkit().getImage("bkg1.png"); // gets the image

    setBackground(new Color(2, 58, 87));
    setLayout(new BorderLayout(10, 40));


    JPanel buttonPanel = new JPanel(); // panel for the two buttons (end game and main menu)
    buttonPanel.setBackground(new Color(0, 105, 110, 1));
    buttonPanel.setLayout(new GridLayout(2, 1, 2, 2));
    add(buttonPanel, BorderLayout.SOUTH);

    JPanel mainMenuPanel = new JPanel();
    mainMenuPanel.setBackground(new Color(0, 105, 110, 1));
    mainMenuPanel.setLayout(new FlowLayout());
    buttonPanel.add(mainMenuPanel);


    JButton mainMenu = new JButton("BACK TO MAIN MENU");
    mainMenu.setFont(myFont);
    mainMenu.addActionListener(this);
    mainMenuPanel.add(mainMenu);

    JPanel endTheGame = new JPanel();
    endTheGame.setBackground(new Color(0, 105, 110, 1));
    endTheGame.setLayout(new FlowLayout());
    buttonPanel.add(endTheGame);

    JButton endGame = new JButton("I'M SURE");
    endGame.setFont(myFont);
    endGame.addActionListener(this);
    endTheGame.add(endGame);

    waitForImage(this, image);


  }
  // gets background image
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

  public void paintComponent (Graphics g)
  {
      g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
      g.setFont(new Font("Din Condensed", Font.BOLD, 200));
      g.setColor(Color.BLACK);
      g.drawString("STOP!", 220, 300); // this string is positioned slightly to the side of the other string to add a three-dimensional effect to the message
      g.setColor(Color.WHITE);
      g.drawString("STOP!", 210, 300);
      g.setFont(new Font("Din Condensed", Font.BOLD, 40));
      g.setColor(Color.WHITE);
      g.drawString("Are you sure you want to quit? Your game WILL be saved.", 40, 400); // emphasizes the "will" part in order to differentiate.

  }

  public void actionPerformed (ActionEvent evt)
  {
    String command = evt.getActionCommand();
    if (command.equals("BACK TO MAIN MENU"))
    {
      listOfCards.show(primaryPanel, "1"); // goes back to main menu
    }
    if (command.equals("I'M SURE"))
    {
      System.exit(2); // exits the game
    }
  }
}
