import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
// The leaderboard panel takes information from a method in the GameData class which retrieves leadboard information via fileIO. A JTextArea holds the leaderboard information, and changes every
// time something new is added to the leaderboard with the use of the paint() method. This panel is accessable from the main menu as well as the game over panel.

public class LeaderboardPanel extends JPanel implements ActionListener
{
  private CardLayout listOfCards;
  private CycleGamesCard primaryPanel;
  private GameData data;
  private Font myFont; // font for this panel
  private JTextArea area; // the area where the leaderboard info is written to

  public LeaderboardPanel (GameData d, CardLayout c, CycleGamesCard p)
  {
    data = d;
    listOfCards = c;
    primaryPanel = p;

    myFont = new Font("Andale Mono", Font.PLAIN, 30);

    setBackground(new Color(242, 231, 194));
    setLayout(new BorderLayout(10, 10));

    JPanel textPanel = new JPanel();
    textPanel.setLayout(new BorderLayout(10, 10));
    textPanel.setBackground(new Color(245, 175, 174));
    add(textPanel, BorderLayout.CENTER);

    area = new JTextArea("" + data.getHighScores(), 10, 20); // the context of this text area is replaced with the highscores information, which is gathered from the GameData class
    area.setFont(myFont);
    area.setLineWrap(true);
    area.setWrapStyleWord(true);
    area.setOpaque(false);
    area.setEditable(false);
    area.setMargin(new Insets(10, 10, 10, 10));

    JScrollPane scroller = new JScrollPane(area);
    scroller.getViewport().setOpaque(false);
    scroller.setOpaque(false);
    textPanel.add(scroller, BorderLayout.CENTER);

    JPanel mainMenuPanel = new JPanel();
    mainMenuPanel.setBackground(new Color(0, 105, 110, 1));
    mainMenuPanel.setLayout(new FlowLayout());
    textPanel.add(mainMenuPanel, BorderLayout.SOUTH);

    JButton mainMenu = new JButton("BACK TO MAIN MENU");
		mainMenu.setFont(myFont);
		mainMenu.addActionListener(this);
		mainMenuPanel.add(mainMenu, BorderLayout.SOUTH);



  }

  public void actionPerformed (ActionEvent evt)
  {
    String command = evt.getActionCommand();
    if (command.equals("BACK TO MAIN MENU"))
    {
      listOfCards.show(primaryPanel, "1");
    }

  }

  public void paint (Graphics g) // every time the panel is shown and the paint method is called, the area for showing the leaderboard is updated.
  {
      super.paint(g);
      //System.out.println("LEADERBOARD PRINTED");
      area.setText("" + data.getHighScores());
      area.setCaretPosition(0);
      //System.out.println(data.getHighScores());
  }

}
