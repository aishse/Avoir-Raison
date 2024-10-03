import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.CardLayout;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

// This class displays the game over panel after the player loses all their lives. A JLabel as well as some strings display the number points and a game mover message and tell the player to
// enter their name for the leaderboard. If the player doesn't enter their name, they aren't able to leave the end panel until they do. The player can either view the leaderboard, go back to
// the main menu, or exit the game entirely, which prompts a confirmation panel to show up before they can actually exit.
public class GameOver extends JPanel implements ActionListener
{
  private CardLayout listOfCards; // cardlayout instance
  private CycleGamesCard primaryPanel; // instance of class that cycles between various cards
  private GameData data; // game data class, for showing the number of points and resetting the game
  private JTextField name; // text field for entering the player's name
  private Font myFont; // font for various jlabels and strings
  private JButton leaderboard, mainMenu, end; // various buttons for toggling between the leaderboard, main menu, and end panel
  private Image image; // image for good job message

  public GameOver (GameData d, CardLayout c, CycleGamesCard p)
  {

    listOfCards = c;
    data = d;
    primaryPanel = p;
    // Image for good job message
    image = Toolkit.getDefaultToolkit().getImage("goodjob.png");

    myFont = new Font("Andale Mono", Font.PLAIN, 20);
    setBackground(new Color(51, 78, 105));
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    setLayout(new BorderLayout(10, 10));

    JPanel endPanel = new JPanel(); // JPanel for showing the message JLabel and name field for the leaderboard.
    endPanel.setBackground(new Color(51, 78, 105, 1));
    endPanel.setLayout(new BorderLayout(0, 0));
    add(endPanel, BorderLayout.CENTER);

    JPanel namePanel = new JPanel(); // Panel that holds the name field
    namePanel.setLayout(new GridLayout(3, 1, 10, 120));
    namePanel.setBackground(new Color(0, 0, 0, 1));
    endPanel.add(namePanel, BorderLayout.NORTH);

    JLabel overMessage = new JLabel("PARTIE TERMINÉE", JLabel.CENTER);
    overMessage.setFont(new Font("Andale Mono", Font.BOLD, 50));
    overMessage.setForeground(Color.WHITE);
    namePanel.add(overMessage);

    name = new JTextField(""); // name field for username
		name.setFont(new Font("Andale Mono", Font.PLAIN, 40));
		name.setEditable(true);
    name.setColumns(15);
		namePanel.add(name);

    JPanel buttonPanel = new JPanel(); // panel for the various buttons
    buttonPanel.setLayout(new GridLayout(3, 1, 0, 0));
    buttonPanel.setBackground(new Color(0, 36, 61));
    endPanel.add(buttonPanel, BorderLayout.SOUTH);

    JPanel leaderboardPanel = new JPanel(); // for holding leaderboard button (so it doesn't fill the screen)
    leaderboardPanel.setBackground(new Color(0, 36, 61));
    leaderboardPanel.setLayout(new FlowLayout());
    buttonPanel.add(leaderboardPanel);


    leaderboard = new JButton("LEADERBOARD");
		leaderboard.setFont(myFont);
		leaderboard.addActionListener(this);
		leaderboardPanel.add(leaderboard);

    JPanel mainMenuPanel = new JPanel(); // for holding main menu button (so it doesn't fill the screen)
    mainMenuPanel.setBackground(new Color(0, 36, 61));
    mainMenuPanel.setLayout(new FlowLayout());
    buttonPanel.add(mainMenuPanel);

    mainMenu = new JButton("BACK TO MAIN MENU");
		mainMenu.setFont(myFont);
		mainMenu.addActionListener(this);
		mainMenuPanel.add(mainMenu);

    JPanel endGamePanel = new JPanel(); // for holding exit game button (so it doesn't fill the screen)
    endGamePanel.setBackground(new Color(0, 36, 61));
    endGamePanel.setLayout(new FlowLayout());
    buttonPanel.add(endGamePanel);

    end = new JButton("END GAME");
		end.setFont(myFont);
		end.addActionListener(this);
		endGamePanel.add(end);

    waitForImage(this, image); // grabs good job image

  }
  // method for getting good job image
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
    g.setColor(new Color(0, 36, 61));
    g.fillRect(0, 0, this.getWidth(), this.getHeight()); // the background kept disappearing due to the image, so this rect acts as the background
    g.setColor(Color.WHITE);
    g.setFont(new Font("Andale Mono", Font.PLAIN, 30));
    g.drawString("Great job! You scored " + data.getPoints() + " points!", 100, 100); // shows number of points
    g.setFont(new Font("Andale Mono", Font.PLAIN, 20));
    g.drawString("Please enter your name for the leaderboard.", 110, 150); // prompts user to enter their username
    g.drawImage(image, 290, 250, 210, 210, null); // draws image at specified location

  }
  // If any of the buttons are pressed and the name field is not empty, then the panel switches depending on the button pressed
  public void actionPerformed (ActionEvent evt)
  {
    String command = evt.getActionCommand();

    if (command.equals("BACK TO MAIN MENU") && !name.getText().equals(""))
    {
        data.setName(name.getText());
        data.saveToHighScores();
        name.setText("");
        listOfCards.show(primaryPanel, "1"); // main menu panel
    }
    if (command.equals("LEADERBOARD") && !name.getText().equals(""))
    {
        data.setName(name.getText());
        data.saveToHighScores();
        name.setText("");
        listOfCards.show(primaryPanel, "3"); // leaderboard panel

    }
    if (command.equals("END GAME") && !name.getText().equals(""))
    {
        data.setName(name.getText());
        data.saveToHighScores();
        name.setText("");
        listOfCards.show(primaryPanel, "7"); // confirmation panel for ending the game

    }

  }

}
