import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import java.io.IOException;
import javax.imageio.ImageIO;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

// This panel is the first panel displayed in the game. It's function is to draw the title screen (background image, options menu) and take the user to the game portion. A MouseListner and a
// MouseMotionListener are used to allow the user to click on the panel and have it switch to the game, and an ActionListner is used to allow the user to toggle between various panels and quit
// game from the menu bar at the top left corner of the panel. The two panels that can be accessed from the menu are the leaderboard and the instructions page. The third option is to quit the
// game, which prompts a confirmation panel to show up, before exiting out of the game itself. CardLayouts are used to toggle between each of the panels.

public class DrawTitle extends JPanel implements MouseListener, ActionListener, MouseMotionListener
{
  // private field variables
  private Image image; // background image
  private GameData data; // instance of game data class in order to reset previous game's data
  private CardLayout listOfCards; // CardLayout instance
  private CycleGamesCard primaryPanel; // instance of class that cycles between the various cards

  public DrawTitle (GameData d, CardLayout c, CycleGamesCard p)
  {
    listOfCards = c;
    primaryPanel = p;
    data = d;
    // tries to get the image in the directory
    image = Toolkit.getDefaultToolkit().getImage("background.png");
    setBackground(Color.BLACK);
    setLayout(new BorderLayout(5,5));

    JPanel settings = new JPanel();
    settings.setBackground(new Color(213, 134, 145, 1));
    settings.setLayout(new BorderLayout(5,5));
    add(settings, BorderLayout.NORTH);

    // settings menu for toggling between leaderboard and instructions, and quitting the game
    JMenuBar settingsBar = new JMenuBar();
    settingsBar.setBackground(new Color(242, 183, 179));
    settings.add(settingsBar, BorderLayout.WEST);

    JMenu settingsMenu = new JMenu("OPTIONS");
    settingsMenu.setOpaque(true);
    settingsMenu.setBackground(new Color(242, 183, 179));
    settingsMenu.setFont(new Font("Andale Mono", Font.PLAIN, 30));

    // the various menu items that are shown in the menu bar
    JMenuItem leaderboard = new JMenuItem("LEADERBOARD");
    leaderboard.setFont(new Font("Andale Mono", Font.PLAIN, 20));

    JMenuItem instructions = new JMenuItem("HOW TO PLAY");
    instructions.setFont(new Font("Andale Mono", Font.PLAIN, 20));

    JMenuItem endGame = new JMenuItem("QUIT GAME");
    endGame.setFont(new Font("Andale Mono", Font.PLAIN, 20));

    leaderboard.addActionListener(this);
    instructions.addActionListener(this);
    endGame.addActionListener(this);

    settingsMenu.add(leaderboard);
    settingsMenu.add(instructions);
    settingsMenu.add(endGame);

    settingsBar.add(settingsMenu);

    // Draws the title on a transparent JPanel so the image is shown
    JPanel title = new JPanel();
    title.setPreferredSize(new Dimension (800, 750));
    title.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 550));
    title.setOpaque(false);
    add(title, BorderLayout.SOUTH);

    JLabel avoirRaison = new JLabel("Avoir Raison", JLabel.CENTER);
    avoirRaison.setForeground(Color.WHITE);
    avoirRaison.setFont(new Font("Din Condensed", Font.BOLD, 120));
    title.add(avoirRaison);

    JLabel click = new JLabel("  Click to Begin  ", JLabel.CENTER);
    click.setForeground(Color.WHITE);
    click.setFont(new Font("Din Condensed", Font.PLAIN, 60));
    title.add(click);

    waitForImage(this, image); // method for grabbing image is called.
    addMouseListener(this);
    addMouseMotionListener(this);
  }
  // Method for writing image from file (called in constructor)
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

  // draws the image
  public void paintComponent (Graphics g)
  {
      g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
  }

  // if the mouse is pressed anywhere on the frame below the menu bar, the previous gamedata resets and the game panel shows
	public void mousePressed (MouseEvent evt)
	{
    int xPos = evt.getX();
    int yPos = evt.getY();

    if (xPos > 0 && xPos < 800 && yPos > 10 && yPos < 750)
    {
      //System.out.println("NEXT");
      data.reset();
     listOfCards.next(primaryPanel); // toggles to the next panel (the game)
     //listOfCards.show(primaryPanel, "6");
    }
	}

  public void mouseExited (MouseEvent evt) {}
  public void mouseEntered(MouseEvent evt) {}
	public void mouseClicked(MouseEvent evt) {}
	public void mouseMoved(MouseEvent evt) {}
	public void mouseDragged(MouseEvent evt)	{}
	public void mouseReleased (MouseEvent evt)	{}

  public void actionPerformed (ActionEvent evt)
  {
    String command = evt.getActionCommand();
    if (command.equals("LEADERBOARD"))
    {
      listOfCards.show(primaryPanel, "3"); // shows the leaderboard panel
    }

    if (command.equals("HOW TO PLAY"))
    {
      listOfCards.show(primaryPanel, "4"); // shows the instructions panel
    }
    if (command.equals("QUIT GAME"))
    {
      listOfCards.show(primaryPanel, "7"); // shows the confirmation panel for quitting the game
    }
  }
}
