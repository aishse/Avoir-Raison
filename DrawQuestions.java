import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.BorderFactory;

// This panel shows up if the user misses a box or if they scored a certain amount of points. Using the information from the GameData class,
// an array of answers is created and displayed using JRadioButtons and a GridLayout. The question as well as the question number are also
// retrived from the GameData class, and displayed using a JTextArea. Two buttons at the bottom are placed for checking the answer and moving on if the answer has been checked.
// If the answer is correct, a JTextArea that is underneath the question and previously set as an empty string will be set to a good job message or a wrong answer message in French
// depending on if the answer was correct or not. This message along with the answers array resets every time the user leaves the question panel.
class DrawQuestions extends JPanel implements ActionListener
{
  private GameData data;
  private Font myFont;
	private CardLayout listOfCards;
	private CycleGamesCard primaryPanel;
  private ButtonGroup group;
  private JTextArea questionArea;
  private JRadioButton [] answer; // RadioButton array of answers
  private JButton enter, next; // buttons for toggling
  private JTextField message; // the message field after the answer is checked
  private boolean correct, incorrect; // booleans for the answer message


  public DrawQuestions (GameData d, CardLayout c, CycleGamesCard p)
  {
    data = d;
    listOfCards = c;
    primaryPanel = p;
    incorrect = correct = false;

    myFont = new Font("Andale Mono", Font.PLAIN, 22); // font for the answers
    setBackground(new Color(7, 29, 51));
    setLayout(new BorderLayout(10, 10));

    answer = new JRadioButton[4]; // the array's length is set to the number of answers displayed

		JPanel question= new JPanel(); // panel where the question is displayed
		question.setBackground(new Color(33, 58, 82));
		question.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		question.setLayout(new BorderLayout());
		add(question, BorderLayout.NORTH);

    questionArea = new JTextArea(data.getQuestion(), 4, 30); // The question area's content is retrieved from the GameData class
		questionArea.setFont(new Font("Andale Mono", Font.PLAIN, 35));
		questionArea.setLineWrap(true);
		questionArea.setWrapStyleWord(true);
		questionArea.setOpaque(false); // So it can be transparent
    questionArea.setForeground(Color.WHITE);
		questionArea.setEditable(false); // can't be edited

    question.add(questionArea, BorderLayout.CENTER);

    message = new JTextField(""); // the message that is displayed if the answer is wrong or right is initially set to an empty string
    message.setFont(new Font("Andale Mono", Font.BOLD, 40));
    message.setBackground(new Color(33, 58, 82, 1));
    message.setForeground(Color.WHITE);
		message.setEditable(false);
    question.add(message, BorderLayout.SOUTH);


    JPanel answers = new JPanel(); // answers panel
    answers.setBackground(new Color(25, 50, 74));
    answers.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    answers.setLayout(new GridLayout(2, 2, 20, 20));
    add(answers, BorderLayout.CENTER);
    group = new ButtonGroup();

    // this loop goes through the answers array and sets the answers using the separate array in the gamedata class.
    for (int i = 0; i < answer.length; i++)
    {
      // gets the answer using a GameData method passing in the index of the answers array. The method will grab the specified answer from the array of answers in the GameData class that are
      // at the specified index.
      answer[i] = new JRadioButton("" + (char)(65 + i) + ". " + data.getAnswer(i));

      if (data.getAnswer(i) == data.getCorrectAnswer()) // if the answer at the index is the same as the correct answer already saved to another String, then this statment executes
      {
        String tempString = data.getAnswer(i).substring(1); // the beginning of the answer has a '*' at the beginning, which is removed to avoid cheating
        answer[i] = new JRadioButton("" + (char)(65 + i) + ". " + tempString);
      }
      group.add(answer[i]);
      answer[i].setBackground(new Color(230, 230, 230));
      answer[i].setOpaque(true);
      answer[i].setFont(myFont);
      answer[i].addActionListener(this);
      answers.add(answer[i]);
    }

    JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 30));
		add(buttonPanel, BorderLayout.SOUTH);

		enter = new JButton("CHECK"); // for checking the answer
		enter.setFont(myFont);
		enter.addActionListener(this);
		enter.setEnabled(false); // initally not enabled because an answer is not selected
		buttonPanel.add(enter);

    next = new JButton("CONTINUE"); // for moving on to the panel
		next.setFont(myFont);
		next.addActionListener(this);
		next.setEnabled(false); // not enabled until the answer is checked
		buttonPanel.add(next);
  }

  public void actionPerformed (ActionEvent e)
  {
    String command = e.getActionCommand();

    if (group.getSelection() != null) // if there is a selectino, then the check button is enabled
    {
      enter.setEnabled(true);
    }

    if (command.equals("CHECK"))
    {
      answer[data.getCorrectIndex()].setBackground(Color.GREEN); // the correct answer is highlighted with green
      for (int i = 0; i < answer.length; i++)
      {
        // goes through the answers array checking if any are selected, and if it is selected and it's incorrect, a life is lost and a boolean for the message text area is set to true.
        if (answer[i].isSelected())
        {
          if (i != data.getCorrectIndex())
          {
            data.loseLife();
            incorrect = true;
            //System.out.println("That is incorrect!");
            next.setEnabled(true); // button for switching panels is enabled
          }
          else // if the answer is equal to the correct answer, then the correct answer boolean for the message text file is set to true and the
          {
            //resetQuestion();
            correct = true;
            //System.out.println("That is correct!");
            next.setEnabled(true); // button for changing panels is enabled
          }
        }

      }

    }
    // if either boolean is true, the message is changed to show if their answer was correct or not.
    if (incorrect)
    {
      message.setForeground(Color.RED);
      message.setText("           Désolée!");
    }

    if (correct)
    {
      message.setForeground(Color.GREEN);
      message.setText("          Bien Fait!");

    }

    if (command.equals("CONTINUE")) // stops user from changing their answers, resets the booleans, and calls the reset question method
    {

      if (incorrect)
      {
        listOfCards.show(primaryPanel, "2");

      }
      else if (correct)
      {
        listOfCards.show(primaryPanel, "2");

      }

      group.clearSelection();
      for(int i = 0; i < answer.length; i++)
      {
        answer[i].setEnabled(false);
      }
      enter.setEnabled(false);
      next.setEnabled(false);

      incorrect = false;
      correct = false;
      resetQuestion();

    }


  }

  public void resetQuestion ( ) // overwrites the answers array with new answers and a new question
	{
    data.grabQuestionFromFile();
    questionArea.setText(data.getQuestion());
    for (int i = 0; i < answer.length; i++)
    {
      answer[i].setText("" + (char)(65 + i) + ". " + data.getAnswer(i));
      if (data.getAnswer(i) == data.getCorrectAnswer())
      {
        String tempString = data.getAnswer(i).substring(1);
        answer[i].setText("" + (char)(65 + i) + ". " + tempString);
      }
    }
		for(int i = 0; i < answer.length; i++)
		{
			answer[i].setEnabled(true);
			answer[i].setBackground(new Color(230, 230, 230));
		}
    message.setText("");


	}

}
