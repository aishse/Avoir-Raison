import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.BorderFactory;

// This class is the class that adds the panels to the CardLayout and an instance of the GameData class. An instance of this class as
// well as the cardlayout instance are passed as parameters into each of the classes. Depending on whether the panel requires anything
// from the GameData class, an instance of it is passed as well.
public class CycleGamesCard extends JPanel
{
  private CardLayout listOfCards;
  private GameData data;
  public CycleGamesCard ()
  {
    data = new GameData(); // gamedata instance
    data.grabQuestionFromFile(); // grabs the question from file initially so the variables aren't null

    setBackground(Color.BLACK);
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    listOfCards = new CardLayout();
    setLayout(listOfCards);

    // All of the panels in the game that are visible.
    DrawTitle first = new DrawTitle(data, listOfCards, this);
    add(first, "1");

    CreateGame second = new CreateGame(data, listOfCards, this);
    add(second, "2");

    LeaderboardPanel third = new LeaderboardPanel(data, listOfCards, this);
    add(third, "3");

    InstructionPanel fourth = new InstructionPanel(data, listOfCards, this);
    add(fourth, "4");

    DrawQuestions fifth = new DrawQuestions(data, listOfCards, this);
    add(fifth, "5");

    GameOver sixth = new GameOver(data, listOfCards, this);
    add(sixth, "6");

    ConfirmGameOver seventh = new ConfirmGameOver(listOfCards, this);
    add(seventh, "7");

    ConfirmMainMenu eighth = new ConfirmMainMenu(data, listOfCards, this);
    add(eighth, "8");


  }
}
