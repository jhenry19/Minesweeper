import java.util.Scanner;

/**
 * Julian Henry
 * CS110
 * Runner class for Minesweeper
 *
 * EXTRA CREDIT CONSIDERATION
 * I have added the following functionality, I would like evaluated for extra credit:
 *  ** Throw an exception when the user attempts to uncover a flagged square
 *  ** Allow the user to select a level when they start
 *  ** Allow the user to restart the game
 */
public class Runner {
    public static void main(String[] args) {
        //loaded with default values for testing
        int width  = 10;
        int height = 10;
        int mines  = 2;

        boolean playing = true;
        Scanner keyboard = new Scanner(System.in); // gathers user input

        //Authentication for picking a level at the beginning of the game
        boolean pickingLevel = true;
        while (pickingLevel)
        {
            System.out.print("Welcome to Minesweeper! What level would you like to play?\n" +
                    "(B)eginner – 8 x 8 grid with 8 mines\n(I)ntermediate - 10 x 12 grid with 10 mines\n" +
                    "(E)xpert -  16 x 20 grid with 50 mines\nChoose a level (B, I, or E): ");
            String level = keyboard.nextLine();

            // changes initialization variables based on chosen level
            if (level.toUpperCase().equals("B"))
            {
                width  = 8;
                height = 8;
                mines  = 8;
                pickingLevel = false;
            }
            else if (level.toUpperCase().equals("I"))
            {
                width  = 10;
                height = 12;
                mines  = 10;
                pickingLevel = false;
            }
            else if (level.toUpperCase().equals("E"))
            {
                width  = 16;
                height = 20;
                mines  = 50;
                pickingLevel = false;
            }
            else
            {
                System.out.println("Please enter a valid input for picking a level.");
            }
        }
            //Outer while loop allows user to restart game
            while (playing) {
                //Run at the beginning of each game
                MineSweeper mineSweeper = new MineSweeper(width, height, mines);
                Grid.Status gameStatus = Grid.Status.OK;

                System.out.println("Happy minesweeping!\n");
                //inner while loop runs each turn until change in game state
                while (gameStatus == Grid.Status.OK) {
                    mineSweeper.displayGrid(); //displays the board the user
                    System.out.println("\nWhat next? \nOptions: (U)ncover r c, (F)lag r c, (Q)uit");

                    //Collects user input
                    String input = keyboard.nextLine();
                    String[] pieces = input.split("\\s+");
                    if (pieces[0].toUpperCase().equals("U")) { // if user wants to uncover square
                        try {
                            gameStatus = mineSweeper.userUncover(Integer.parseInt(pieces[1]), Integer.parseInt(pieces[2]));
                        }
                        //catches exception if flagged square is uncovered
                        catch (UncoveringFlaggedSquareException e)
                        {
                            System.out.println(e.getMessage());
                        }
                    }
                    else if (pieces[0].toUpperCase().equals("F")) // if user wants to flag a square
                    {
                        mineSweeper.userFlag(Integer.parseInt(pieces[1]), Integer.parseInt(pieces[2]));
                    }
                    else if(pieces[0].toUpperCase().equals("Q")) // if user wants to quit
                    {
                        gameStatus = Grid.Status.QUIT;
                    }
                    else { // all other inputs are invalid so the user is alerted if their input doesn't work
                        System.out.println("Invalid input. Try again.");
                    }
                }
                //If the player hits a mine the grid is displayed with all mines uncovered
                if (gameStatus == Grid.Status.MINE) {
                    System.out.println("BOOOOM!");
                    mineSweeper.displayMineGrid();
                    System.out.println("Oh no! You blew up a mine!");
                }
                //If the player wins they are congratulated and asked if they want to play again
                else if (gameStatus == Grid.Status.WIN)
                {
                    mineSweeper.displayGrid();
                    System.out.println("YOU WIN! CONGRATULATION!");
                }
                // Quits the game
                else if (gameStatus == Grid.Status.QUIT)
                {
                    System.out.println("Thanks for playing!");
                    playing = false;
                }
                //Asks the user if they want to play again only if they did not quit
                if (!(gameStatus == Grid.Status.QUIT))
                {
                    System.out.println("Would you like to play again? (Y or N)");
                    String playAgain = keyboard.nextLine();
                    //prints a new line to separate games if the user wants to play again
                    if (playAgain.toUpperCase().equals("Y")){
                        System.out.print("\n");
                    }
                    else
                    {
                        System.out.println("Thanks for playing!");
                        playing = false;
                    }
                }
            }
    }
}
