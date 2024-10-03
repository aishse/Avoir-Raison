import java.io.File;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
// this class is not shown in the game as a panel. It's purpose is to store all the player data and question information for the game. The information for the leaderboard as well as the
// question information are gathered from the text file in their respective methods. Also, variables that are needed in other classes and panels have their own getter and setter menus that
// are easily accessable.
public class GameData
{
  private String verb, form, question, correctAnswer, name; // Strings for various player and question information
	private String [] answerSet; // array of answers that are retrieved from the text file
	private String questionNum; // the number of the question
	private int points, correctAnswerIndex, lives, previousPoints; // integers for player, question, and leaderboard information.

  public GameData ()
  {
    verb = form = question = correctAnswer = name = "";
    answerSet = new String[4];
    points = 0;
    previousPoints = 0;
    lives = 5;
    questionNum = " ";


  }
  // returns the question string
  public String getQuestion ()
  {
    return question;
  }
  // grabs question from file using file IO.
  public void grabQuestionFromFile ()
  {
    int temp  = (int)(Math.random()* 101); // creates a random integer from 1 to 100 and makes it equal to a question number
    questionNum = "" + temp;


  //  System.out.println(questionNum);
    Scanner inFile = null;
		String fileName = "questions.txt"; // grabs questions from file using file io
		File inputFile = new File(fileName);

		try
		{
			inFile = new Scanner(inputFile);
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e);
			System.exit(1);
		}
    int counter = 0;
		while (inFile.hasNext()) // traverses through text file and saves certain parts to certain variables
		{
      String temp2 = inFile.next(); // grabs question num

			 if (temp2.equals(questionNum)) // if it's equal to the question number variable, then the remaining information should be collected
       {
        // System.out.println(temp);
         form = inFile.next(); // saves the next two strings picked up into their specified variables
         verb = inFile.next();
        // System.out.println("VERB: " + verb + " FORM: " + form);
         inFile.nextLine();
         for (int i = 0; i < answerSet.length; i++) // traverses through answerSet array and saves the next four strings into it
         {
           answerSet[i] = inFile.nextLine();
           if (answerSet[i].charAt(0) == '*') // if the first character of the string has a '*' at the beginning, then it will be saved as the correct answer
           {
             correctAnswer = answerSet[i];
             correctAnswerIndex = i;

           }
         }

       }
		}

    question = "What is the " + form + " form of " + verb + "?"; // creates the question once the previous variables are initialized

  // System.out.println("\n\nANSWERS");
  //  System.out.println("CORRECT ANSWER: " + correctAnswer);
		inFile.close();
	}

  public String getAnswer (int index) // returns the answer at the specified index (called in the question panel class)
  {
    return answerSet[index];
  }

  public String getCorrectAnswer () // returns the correct answer
  {
    return correctAnswer;
  }

  public int getCorrectIndex () // returns the index of the correct answer
  {
    return correctAnswerIndex;
  }

  public void addToPoints (int increment) // adds the specified increment to the points int
  {
    points += increment;
  }

  public int getPoints() // returns the points
  {
    return points;

  }

  public int getLives() // returns lives
  {
    return lives;
  }

  public void loseLife() // decreases the lives if called
  {
    lives--;
  }

  public String getQuestionNum() // returns the question number
  {
    return questionNum;

  }

  public void savePoints (int temp) // saves points from before
  {
    previousPoints = temp;
  }

  public int getPrevious () // returns previous points
  {
    return previousPoints;
  }

  public void reset() // resets all the player information
  {
    points = 0;
    lives = 5;
    verb = "";
    form = "";
    question = "";
    correctAnswer = "";
    answerSet = new String[4];
    int temp  = (int)(Math.random()* 101);
    questionNum = "" + temp;

  }

  public String getInstructions ( ) // grabs the instructions from the text file.
	{
		String result = "";
		String fileName = "instructions.txt";
		Scanner inFile = null;
		File inputFile = new File(fileName);
		try
		{
			inFile = new Scanner(inputFile);
		}
		catch(FileNotFoundException e)
		{
			System.err.printf("ERROR: Cannot open %s\n", fileName);
			System.out.println(e);
			System.exit(1);
		}
		while(inFile.hasNext())
		{
			String line = inFile.nextLine();
			result += line + "\n";
		}
		return result;
	}

  public void randomize () // randomizes question num
  {
    int temp  = (int)(Math.random()* 101);
    questionNum = "" + temp;

  }

  public void setName (String temp) // sets the name of the player
  {
    name = temp;
  }

  public String getName () // returns the name of the player
  {
    return name;
  }

  public String getHighScores () // grabs the highscores from the text file
  {

    String result = "";
		String fileName = "highScores.txt";
		Scanner inFile = null;
		File inputFile = new File(fileName);
		try
		{
			inFile = new Scanner(inputFile);
		}
		catch(FileNotFoundException e)
		{
			System.err.printf("ERROR: Cannot open %s\n", fileName);
			System.out.println(e);
			System.exit(1);
		}
		while(inFile.hasNext())
		{
			String line = inFile.nextLine();
			result += line + "\n";
		}
		return result;
  }

  public void saveToHighScores () // opens up the high scores textfile and writes to it with more information.
  {
    if (points >= 0)
    {
      String result = "";
  		String fileName = "highScores.txt";
      boolean hasBeenAdded = false;
  		Scanner inFile = null;
  		File inputFile = new File(fileName);
  		try
  		{
  			inFile = new Scanner(inputFile);
  		}
  		catch(FileNotFoundException e)
  		{
  			System.err.printf("ERROR: Cannot open %s\n", fileName);
  			System.out.println(e);
  			System.exit(1);
  		}
  		while(inFile.hasNext())
  		{
  			String line = inFile.nextLine();
        if (!hasBeenAdded && Integer.parseInt("" + line.substring(line.indexOf("-") + 2)) <= points) // looks to see if the scores are less than or greater than the points int
        {
          if (inFile.hasNext())
          {
            result += name + " " + "-" + " " + points + "\n";
            hasBeenAdded = true;
          }
          else
          {
            result += name + " " + "-" + " " + points + "\n";
            hasBeenAdded = true;
          }
        }
  			result += line + "\n";
  		}
      if (!hasBeenAdded)
      {
        result += name + " " + "-" + " " + points + "\n";

      }
      inFile.close();

      PrintWriter outFile = null; // The printwriter is in charge of writing the new player information to the textfile
  		File toFile = new File(fileName);

  		try
  		{
  			outFile = new PrintWriter(toFile);
  		}
  		catch(IOException e)
  		{
  			System.out.println(e);
        e.printStackTrace();
  			System.exit(2);
  		}

      outFile.println(result); // prints out the result string to the file, which includes the new information
  		outFile.close();

    }
  }
}
