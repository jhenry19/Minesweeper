/**
 * Julian Henry
 * CS110
 * Grid class for Minesweeper
 */
public class Grid {
    enum Status {OK, WIN, MINE, QUIT} // enum for use determining status of game
    private Square[][] grid; // the grid that is the board
    private int width;
    private int height;
    private int numMines;
    private int numSquaresUncovered = 0;

    /**
     * Default constructor for Grid class
     * @param width width of the board
     * @param height height of the board
     * @param numMines number of mines on the board
     */
    public Grid(int width, int height, int numMines)
    {
        this.width    = width;
        this.height   = height;
        this.numMines = numMines;

        //Make the board
        grid = new Square[height][width];

        // create the mines before placing numberSquares so that they can be given the number of neighbor mines they have
        createMines();

        //Populate the board with numberSquares wherever there is not a mine
        for (int x = 0; x < height; x++)
        {
            for (int y = 0; y < width; y++)
            {
                if(grid[x][y] == null) grid[x][y] = new NumberSquare(getNeighbors(x,y), x, y);
            }
        }
    }

    /**
     * Fills grid with a specified number of mines. I decided it was easier for this method to be in this class because
     * it made accessing the grid easier than doing so in the MineSweeper class.
     */
    public void createMines(){
        int row;
        int col;
        boolean placingMine;

        // Run once for each mine the board should have
        for (int i = 0; i < numMines; i++){
            placingMine = true;
            while(placingMine) {
                MineSquare mine = new MineSquare(); // new mine created

                // a row and column is randomly chosen
                row = (int)(Math.random() * height);
                col = (int)(Math.random() * width);

                if (grid[row][col] == null) //mine is only placed if no mine is there before
                {
                    grid[row][col] = mine;
                    placingMine = false;
                }
            }
        }
    }

    /**
     * Checks for mine neighbors through a nested for loop to go through either 8 squares (the ones around it) or
     * fewer if the square being checked is on a border.
     *
     * @param row the x coordinate of the square being checked
     * @param col the y coordinate of the square being checked
     * @return the number of mines surrounding the square
     */
    public int getNeighbors(int row, int col)
    {
        Square temp;
        int mineCounter = 0;

        // default value for these is 0 and changing them decides how far to the top left corner the algorithm
        // will search
        int xStart = 0;
        int yStart = 0;

        // default value is 2 and changing them decides how far to the bottom right corner the algorithm
        // will search
        int xEnd = 2;
        int yEnd = 2;

        //Adjusts for top left corner
        if (row == 0) xStart = 1;
        if (col == 0) yStart = 1;

        //Adjusts for bottom right corner
        if (row == height - 1) xEnd = 1;
        if (col == width - 1) yEnd = 1;

        //initiated outside loop for efficiency
        int rowToCheck;
        int colToCheck;

        //Both loops will run 3 times (if square is not on edge), 3 and 2 times (if square on edge), or both 2 times
        //(if square is in corner). Does not check self to see if it's a mine
        for (int x = xStart; x <= xEnd; x++)
        {
            for (int y = yStart; y <= yEnd; y++)
            {
                rowToCheck = row - 1 + x;
                colToCheck = col - 1 + y;

                if (!(rowToCheck == row && colToCheck == col)) { //will not check self for mine
                    temp = grid[rowToCheck][colToCheck];
                    //null pointer exception caught because this will be run when grid is filling up with number
                    //squares so some squares may be null when checked but those will not be mines
                    try{
                        if (temp.isMine()) mineCounter++;
                    }
                    catch(NullPointerException n){}
                }
            }
        }
        return mineCounter;
    }

    /**
     * Called when the user uncovers a square. Asses how much of board to show, whether the user uncovered a mine,
     * or whether they won the game.
     * @param row row of square to uncover
     * @param col col of square to uncover
     * @return the status of the game (win, mine, or ok)
     */
    public Status uncoverSquare(int row, int col) throws UncoveringFlaggedSquareException
    {
        Square square = grid[row][col];
        //Exception is thrown if user attempts to uncover a flagged square
        if (square.isFlagged())
        {
            throw new UncoveringFlaggedSquareException("You must remove the flag before uncovering this square.");
        }
        //If a mine is uncovered, the status of the game is changed to mine
        if (square.isMine())
        {
            square.uncover();
            return Status.MINE;
        }
        //Determines how many squares to uncover depending on how many neighbor mines it has
        else {
            int neighbors = ((NumberSquare)square).getNeighborMines(); //converts to numbers square to access neighborMines
            if (neighbors == 0) { // exposes a 5x5 neighborhood without exposing mines or going off grid
                Square temp;
                // default value for these is 0 and changing them decides how far to the top left corner it goes
                int xStart = 0;
                int yStart = 0;
                // default value is 2 and changing them decides how far to the bottom right corner it goes
                int xEnd = 4;
                int yEnd = 4;

                //Adjusts for top left corner
                if (row == 1) xStart = 1;
                else if (row == 0) xStart = 2;
                if (col == 1) yStart = 1;
                else if (col == 0) yStart = 2;
                //Adjusts for bottom right corner
                if (row == height - 1) xEnd = 2;
                else if (row == height - 2) xEnd = 3;
                if (col == width - 1) yEnd = 2;
                else if (col == width - 2) yEnd = 3;

                //initiated outside loop for efficiency
                int rowToCheck;
                int colToCheck;

                for (int x = xStart; x <= xEnd; x++)
                {
                    for (int y = yStart; y <= yEnd; y++)
                    {
                        rowToCheck = row - 2 + x;
                        colToCheck = col - 2 + y;

                        temp = grid[rowToCheck][colToCheck];
                        if (!temp.isMine() && !temp.isUncovered()){ //exposes non-mine squares that are uncovered
                            temp.uncover();
                            numSquaresUncovered++;
                        }
                    }
                }
            }
            else if (neighbors == 1)   //expose 3x3 neighborhood. Do not expose mines. Do not go off the end of the grid
            {
                Square temp;
                // Top left corner
                int xStart = 0;
                int yStart = 0;
                // bottom right corner
                int xEnd = 2;
                int yEnd = 2;

                //Adjusts for top left corner
                if (row == 0) xStart = 1;
                if (col == 0) yStart = 1;
                //Adjusts for bottom right corner
                if (row == height - 1) xEnd = 1;
                if (col == width - 1) yEnd = 1;

                //initiated outside loop for efficiency
                int rowToCheck;
                int colToCheck;

                //Runs loops the correct number of times
                for (int x = xStart; x <= xEnd; x++)
                {
                    for (int y = yStart; y <= yEnd; y++)
                    {
                        rowToCheck = row - 1 + x;
                        colToCheck = col - 1 + y;

                        temp = grid[rowToCheck][colToCheck];
                        if (!temp.isMine() && !temp.isUncovered()){ //exposes non-mine squares that are uncovered
                            temp.uncover();
                            numSquaresUncovered++;
                        }
                    }
                }
            }
            //If there is more than 1 neighbor than only that square is uncovered
            else
            {
                square.uncover();
                numSquaresUncovered++;
            }
            //Check for win
            if (numSquaresUncovered == (height * width) - numMines) return Status.WIN;
            else return Status.OK;
        }
    }

    /**
     * Uncovers all mines on the board
     */
    public void exposeMines()
    {
        for (int x = 0; x < height; x++)
        {
            for (int y = 0; y < width; y++)
            {
                if (grid[x][y].isMine()) grid[x][y].uncover();
            }
        }
    }

    /**
     * Flags the correct square
     * @param row the row of the square to be flagged
     * @param col the column of the square to be flagged
     */
    public void flagSquare(int row, int col)
    {
        grid[row][col].flagSquare();
    }

    /**
     * @return A string representation of the game board used to display the game to the user
     */
    public String toString()
    {
        String toDisplay = "   "; // creates top left corner space

        // created row of column headers
        for (int i = 0; i < width; i++)
        {
            if (i <= 9) toDisplay += (i + "  "); // extra space is given to numbers with only one digit
            else toDisplay += (i + " ");
        }
        toDisplay += "\n"; //creates new line for first row of game board

        for (int x = 0; x < grid.length; x++)
        {
            if (x <= 9) toDisplay += (x + "  "); // displays row headers
            else toDisplay += (x + " ");
            for (int y = 0; y < grid[0].length; y++)
            {
                toDisplay += grid[x][y].toString() + "  "; //shows elements
            }
            toDisplay += "\n"; //new line after each row is complete
        }
        return toDisplay;
    }
}
