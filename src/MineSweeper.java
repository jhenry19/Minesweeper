/**
 * Julian Henry
 * CS110
 * Minesweeper class for Minesweeper
 */
public class MineSweeper {
    private int width;
    private int height;
    private Grid grid;
    private int numMines;

    /**
     * Default constructor for minesweeper
     * @param width width of board
     * @param height height of board
     * @param numMines number of mines on board
     */
    public MineSweeper(int width, int height, int numMines)
    {
        this.width    = width;
        this.height   = height;
        this.numMines = numMines;
        grid = new Grid(width,height,numMines);
    }

    /**
     * Called when the user flags a square. Uses the Grid class's functions to handle the flagging.
     * @param row the row of the square being flagged
     * @param col the column of the square being flagged
     */
    public void userFlag(int row, int col)
    {
        grid.flagSquare(row, col);
    }

    /**
     * Called when the user uncovers a square.
     * @param row the row of the square being uncovered
     * @param col the column of the square being uncovered
     * @return the status of the game as determined by the Grid class
     * @throws UncoveringFlaggedSquareException if the user tries to uncover a square that is flagged
     */
    public Grid.Status userUncover(int row, int col) throws UncoveringFlaggedSquareException
    {
        return grid.uncoverSquare(row, col);
    }

    /**
     * Shows the user the grid
     */
    public void displayGrid()
    {
        System.out.print(grid);
    }

    /**
     * Shows the user the grid with all the mines uncovered. Called when the user losses and uncovers a mine.
     */
    public void displayMineGrid()
    {
        grid.exposeMines();
        System.out.print(grid);
    }
}
