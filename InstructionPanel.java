import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

// Grabs instruction information from the instructions text file, which is displayed using a JTextArea. The user can toggle between the leaderboard and the main menu if they choose to do so
// using JButtons.

public class InstructionPanel extends JPanel implements ActionListener
{
  private CardLayout listOfCards;
  private CycleGamesCard primaryPanel;
  private GameData data; // for grabbing information from the getInstructions() method
  private Font myFont;

  public InstructionPanel (GameData d, CardLayout c, CycleGamesCard p)
  {
    data = d;
    listOfCards = c;
    primaryPanel = p;

    myFont = new Font("Andale Mono", Font.PLAIN, 25);

    setBackground(new Color(203, 238, 242));
    setLayout(new BorderLayout(10, 10));

    JPanel textPanel = new JPanel(); // panel for holding the instructions information
    textPanel.setLayout(new BorderLayout(10, 10));
    textPanel.setBackground(new Color(203, 238, 242));
    add(textPanel, BorderLayout.CENTER);

    JTextArea instructionsArea = new JTextArea("" + data.getInstructions(), 10, 25); // grabs the information from the GameData instance of the getInstructions() method and sets the text as it
    instructionsArea.setOpaque(false);
    instructionsArea.setEditable(false);

    instructionsArea.setBackground(new Color(203, 238, 242));
    instructionsArea.setFont(new Font("Andale Mono", Font.BOLD, 30));
    instructionsArea.setLineWrap(true);
    instructionsArea.setWrapStyleWord(true);

    JScrollPane scroller = new JScrollPane(instructionsArea);
    scroller.getViewport().setOpaque(false);
    scroller.setOpaque(false);
    textPanel.add(scroller);

    JPanel buttonPanel = new JPanel(); // panel for holding the buttons
    buttonPanel.setLayout(new GridLayout(2, 1, 0, 0));
    buttonPanel.setBackground(new Color(200, 123, 123, 1));
    textPanel.add(buttonPanel, BorderLayout.SOUTH);

    JPanel leaderboardPanel = new JPanel(); // holds leaderboard button to avoid filling the entire space
    leaderboardPanel.setBackground(new Color(0, 105, 110, 1));
    leaderboardPanel.setLayout(new FlowLayout());
    buttonPanel.add(leaderboardPanel);

    JButton leaderboard = new JButton("VIEW LEADERBOARD");
		leaderboard.setFont(myFont);
		leaderboard.addActionListener(this);
		leaderboardPanel.add(leaderboard);

    JPanel mainMenuPanel = new JPanel(); // holds main menu button for filling the entire space
    mainMenuPanel.setBackground(new Color(0, 105, 110, 1));
    mainMenuPanel.setLayout(new FlowLayout());
    buttonPanel.add(mainMenuPanel);


    JButton mainMenu = new JButton("BACK TO MAIN MENU");
		mainMenu.setFont(myFont);
		mainMenu.addActionListener(this);
		mainMenuPanel.add(mainMenu);

  }


  public void actionPerformed (ActionEvent evt)
  {
    String command = evt.getActionCommand();
    if (command.equals("VIEW LEADERBOARD")) // switches to the leaderboard card
    {
      listOfCards.show(primaryPanel, "3");
    }
    if (command.equals("BACK TO MAIN MENU")) // switches to the main menu card
    {
      listOfCards.show(primaryPanel, "1");
    }

  }
}
