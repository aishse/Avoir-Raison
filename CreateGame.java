import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.CardLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

// This panel draws the entire game portion of the application. A timer is used along with an ActoinListener to allow a ball to move back and forth across the bottom of the panel, where a prompt is
// located telling the player to press their enter key to shoot at one or more of the randomly located boxes. This is done using a KeyListener. Another use of this listener is if the user wishes
// to quit the game. They can press the delete/backspace key to go back to the main menu. A confirmation page will show up, letting the user know their game won't be saved. Various booleans are used
// to check if the player has fired the ball, whether it has hit a box, or if the location of the box is high enough for the player to score additional points. If the boolean for hitting a box isn't
// triggered, as well as the location of the ball being less than 0, the panel switches to the question panel, and a life is reduced. The panel also switches to the questions if the number of points is
// greater than 10 and divisible by 2. If the player hits a box, the points method in the GameData class is incremented by 2 and the change is shown in the game panel.  If the box is high enough,
// the number of points scored is randomized to be a number between 3 and 5, and is incremented in the method as well. A counter at the top for lives and points is also shown via a getPoints() and
// a getLives() method that is updated. The number of times the ball goes back and forth is also counted by a counter variable, and if that counter reaches a value greater than 8, a life is lost
// the panel switches to the questions panel.
public class CreateGame extends JPanel implements ActionListener, KeyListener, FocusListener
{
  private int delay, x, y, radius, xpos, width, height, lives, backAndForth; // various fields for counting and setting positions
  private double dx, dy; // how fast the ball will be moving
  private int [] randArray; // an array of random numbers for the boxes displayed
  private Timer timer; // timer for the ball to move back and forth
  private Font myFont; // font for the strings
  private boolean fire, hit, morePoints; // booleans for checking whether the ball fired, hit a box, or if the box hit was high enough to score more points
  private CardLayout listOfCards; // cardlayout instance
  private CycleGamesCard primaryPanel; // class for cycling between panels
  private GameData data; // gamedata class for various purposes

  public CreateGame (GameData d, CardLayout c, CycleGamesCard p)
  {
    myFont = new Font("Andale Mono", Font.PLAIN, 30);
    listOfCards = c;
    primaryPanel = p;
    data = d;
    setBackground(new Color(0, 36, 61));
    setLayout(new BorderLayout(10, 10));

    randArray = new int [10]; // sets length of array (some of the values aren't used for the boxes due to overcrowding)

    for (int i = 0; i < randArray.length; i++)
    {
      randArray[i] = (int)(Math.random() * 300 + 100); // sets each of the ints in the array to a random number between 100 and 300
    }
    backAndForth = 0; // counter for how many times the ball goes back and forth
    width = height = 40; // width and height of the boxes, to make easy changes
    delay = 10; // delay of the timer
    radius = 15; // how wide the ball is
    xpos = 0; // x position of the ball as it moves (changes)
    x = 0; // the xposition of the ball that is incremented by the dx variable (changes)
    y = 560; // the yposition of the ball as it moves (doesn't change)
    dx = 6.8; // velocity of the ball as it moves horizontally
    dy = -6.8; // velocity of the ball as it moves vertically (shooting)
    fire = hit = morePoints = false; // booleans for checking the status of the ball
    timer = new Timer(delay, this); // creates the timer
    addFocusListener(this); // adds focus and key listeners to the instance
		addKeyListener(this);
    timer.start(); // starts the timer
  }

  public void paintComponent (Graphics g)
  {
    this.requestFocus(); // requests focus
    super.paintComponent(g);
    g.setColor(new Color(0, 24, 41));
    g.fillRect(0, 520, 800, 150);
    drawRandomRects(g); // draws the various boxes

    g.setColor(new Color(255, 255, 255, 60));
    g.drawRect(2, 520, 775, 87);
    g.setFont(myFont);

    g.setColor(Color.WHITE);
    // draws the counter strings for points and lives using the GameData instance
    g.drawString("POINTS: " + data.getPoints(), 10, 30);
    g.drawString("LIVES: " + data.getLives(), 230, 30);

    // draws some rectangles for cosmetic purposes
    g.setFont(new Font("Andale Mono", Font.PLAIN, 20));
    g.setColor(new Color(255, 255, 255, 60));
    g.drawRect(260, 480, 270, 25);
    g.setColor(new Color(255, 255, 255, 240));

    // draws the string prompting the user to shoot
    g.drawString("PRESS ENTER TO SHOOT", 275, 500);
    g.setColor(Color.WHITE);

    // if the fire variable is not true, then the ball goes back and forth
    if (fire == false)
    {
      if (x < radius)
      {
        dx = Math.abs(dx); // positive velocity (left to right)
        backAndForth++; // incremented every time the ball reaches the edge of the panel
      }

      if (x > getWidth() - radius)
      {
        dx = -Math.abs(dx); // negative velocity (right to left)
        backAndForth++; // incremented every time the ball reaches the edge of the panel
      }
      x+= dx; // adds velocity to the x position
      xpos = x-radius;
      g.fillOval(x-radius, y, 10, 10); // draws the circle at the location
    }
    if (backAndForth > 8) // checks if the backAndForth variable is greater than 8, if so moves to the question panel and user loses a life
    {
      data.loseLife();
      backAndForth = 0;
      listOfCards.show(primaryPanel, "5");
    }

    // if fire is true, the direction flips and the ypos is changed instead of the xpos.
    if (fire == true)
    {
      if(y > getHeight() - radius)
  		{
  			dy = -Math.abs(dy);
  		}
      	y += dy;

      g.fillOval(xpos, y-radius, 10, 10);
    }
    // this entire section checks to see if the location of the ball is within the limits of the boxes. If so, depending on their location, the morePoints boolean is changed or not changed,
    // as well as the hit variable being changed. After each if-statement, a method is called for resetting xposition and y position of the ball, adding the points, or switiching to a question
    // panel if the number points is divisible by 2. The reason the number 15 is added to each of the x criterias is due to some of the balls not triggering the hit boolean even though they
    // passed by the box, which would make it unfair if not implemented.
    if (x >= randArray[1] && x <= randArray[1] + width + 15 && y >= 60 && y <= 60 + height)
    {
      //System.out.println("MADE IT!");
      morePoints = true;
      hit = true;

    }

    checkIfHit();

    if (x >= randArray[2] && x <= randArray[2] + width + 15 && y >= 180 && y <= height + 180)
    {
      morePoints = true;
      //System.out.println("MADE IT2!");
      hit = true;


    }
    checkIfHit();

    if (x >= randArray[3] && x <= randArray[3] + width + 15 && y >= 385 && y <= 385 + height)
    {

      //System.out.println("MADE IT3!");
      hit = true;

    }
    checkIfHit();

    if (x >= randArray[4] + 70 && x <= randArray[4] + 85 + width && y >= randArray[4]-20 && y <= randArray[4] + height)
    {
      if (randArray[4] -20< 200)
      {
        morePoints = true;
      }
      //System.out.println("MADE IT4!");
      hit = true;

    }
    checkIfHit();

    if (x >= randArray[5] + 80 && x <= randArray[5] + 95 + width && y >= randArray[5] - 40 && y <= (randArray[5] - 40) + height)
    {
      if (randArray[5] - 40 < 200)
      {
        morePoints = true;
      }
      //System.out.println("MADE IT5!");
      hit = true;

    }
    checkIfHit();

    if (x >= 650 && x <= 665 + width && y >= randArray[6] - 20 && y <= (randArray[6] - 20) + height)
    {
      if (randArray[6] < 200)
      {
        morePoints = true;
      }
      //System.out.println("MADE IT6!");
      hit = true;

    }
    checkIfHit();

    if (x >= 530 && x <= 545 + width && y >= randArray[7] - 40 && y <= randArray[7] + height)
    {
      if (randArray[7] - 40 < 200)
      {
        morePoints = true;
      }

      //System.out.println("MADE IT7!");
      hit = true;

    }
    checkIfHit();
    if (x >= 200 && x <= 215 + width && y >= randArray[8]- 20 && y <= randArray[8] + height)
    {
      if (randArray[8]- 20 < 200)
      {
        morePoints = true;
      }

      //System.out.println("MADE IT8!");
      hit = true;

    }
    checkIfHit();
    if (x >= randArray[9] && x <= randArray[9] + width + 15 && y >= 200 && y <= 200 + height)
    {

      morePoints = true;
      //System.out.println("MADE IT9!");
      hit = true;

    }
    checkIfHit();

    // this is the catch-all if hit is not triggered and the ypos is less than 0 (meaning the player missed). Everything is reset in terms of booleans and xpositions, but the lives are decreased
    // and the random boxes are changed. The panel changes to the question panel.
    if (y < 0 && hit == false)
    {
      hit = false;
      backAndForth = 0;
      fire = false;
      morePoints = false;
      data.loseLife();
      //System.out.println("LIVES: " + data.getLives());
      y = 560;
      x = 0;
      for (int i = 0; i < randArray.length; i++)
      {
        randArray[i] = (int)(Math.random() * 400 + 100);
      }
      listOfCards.show(primaryPanel, "5");
    }
    // if the number points is divisible by 2 and greater than 10, the question num is randomized and the question is shown. If the lives has stayed the same, points are added.
    if (data.getPoints() % 2 == 0 && data.getPoints() > 10)
    {
      data.randomize();
      int temp = data.getLives();
      listOfCards.show(primaryPanel, "5");
      if (temp == data.getLives())
        data.addToPoints(5);
    }

    if (data.getLives() < 0)
    {
      data.savePoints(data.getPoints());
      listOfCards.show(primaryPanel, "6");
    }


}
  // this method only works if the hit is true. If the more points boolean is true then points are added, otherwise 2 points are added. Everything resets, including booleans, positions of the ball,
  // the random ints for the boxes are changed, and the panel is repainted.
  public void checkIfHit ()
  {
    if (hit == true)
    {
      if (morePoints)
      {
        int temp = (int)(Math.random()* 5 + 3); // creates a random int to add.
        data.addToPoints(temp);
        morePoints = false;
      }
      else
      {
        data.addToPoints(2);
      }
      //System.out.println(data.getPoints());
      // everything is reset
      hit = false;
      fire = false;
      backAndForth = 0;
      y = 560;
      x = 0;
      for (int i = 0; i < randArray.length; i++)
      {
        randArray[i] = (int)(Math.random() * 300 + 100); // creates new random ints.
      }
      this.repaint(); // repainted to show the change
    }

  }

  public void actionPerformed(ActionEvent e)
	{
		this.repaint(); // as the ball moves, the panel repaints
	}

  public void keyTyped(KeyEvent e)	{}
  public void focusGained(FocusEvent evt)
	{
		this.repaint();
	}

	public void focusLost(FocusEvent evt)
	{
		this.repaint();
	}

	public void keyPressed(KeyEvent e)
	{
    int keyValue = e.getKeyCode();
    if (keyValue == KeyEvent.VK_ENTER) // if key is enter, then the fire boolean is true and the ball's ypos starts changing
    {
      fire = true;
      //System.out.println("FIRE");
    }
    if (keyValue == KeyEvent.VK_BACK_SPACE)
    {
      x = 0; 
      listOfCards.show(primaryPanel, "8"); // the main menu confirmatino panel pops up
    }
    this.repaint();
	}

	public void keyReleased(KeyEvent e)        {}

  // This entire method draws random rectangles at random positions, which are changed with various ints added to them. They are all multicolor for cosmetic as well as debugging purposes, and
  // change as the array changes. Some of the indexes in the array are not used due to overcrowding.
  public void drawRandomRects (Graphics g)
  {
    // a checker to see if any of the arrays are close to the area where the ball is moving back and forth.
    for (int i = 0; i < randArray.length; i++)
    {
      if (randArray[i] >= 500)
      {
        randArray[i] -= 200;
      }
    }
    // all of these are the boxes, which have some additions or subtractions in order to add as much randomization and spacing as possible. Some of these colors are from the default library,
    // and some are custom colors via a color picker. The width and height are all field variables to create a streamlines way of changing box size if needed.
    g.setColor(Color.WHITE);
    g.drawRect(randArray[1], 60, width, height);

    g.setColor(Color.BLUE);
    g.drawRect(randArray[2], 180, width, height);

    g.setColor(Color.CYAN);
    g.drawRect(randArray[3], 385, width, height);

    g.setColor(Color.GREEN);
    g.drawRect(randArray[4] + 70, randArray[4]-20, width, height);

    g.setColor(Color.RED);
    g.drawRect(randArray[5] + 80, randArray[5] - 40, width, height);

    g.setColor(new Color(112, 64, 179));
    g.drawRect(650, randArray[6]-20, width, height);

    g.setColor(new Color(227, 157, 225));
    g.drawRect(530, randArray[7] - 40, width, height);

    g.setColor(new Color(227, 41, 222));
    g.drawRect(200, randArray[8] - 20, width, height);

    g.setColor(new Color(182, 188, 250));
    g.drawRect(randArray[9], 200, width, height);
  }
}
