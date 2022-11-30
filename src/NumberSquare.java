/**
 * Julian Henry
 * CS110
 * NumberSquare class for Minesweeper
 */
public class NumberSquare extends Square {
    private int neighborMines;
    private int myRow;
    private int myCol;

    /**
     * Default constructor for a NumberSquare
     * @param n number of neighbor mines a square has
     * @param r row positions of square
     * @param c column positions of square
     */
    public NumberSquare(int n, int r, int c)
    {
        neighborMines = n;
        myRow         = r;
        myCol         = c;
    }

    /**
     * Uncovers a square if it is not flagged and sets the display to a number or a - if no neighborMines
     * @return true if the square is uncovered
     */
    public boolean uncover(){
        if (!isFlagged()) {
            setUncovered(true);
            if (neighborMines == 0) setElement("_");
            else setElement(Integer.toString(neighborMines));
            return true;
        }
        return false;
    }

    /**
     * @return an int that represents the NeighborMines variable
     */
    public int getNeighborMines() {
        return neighborMines;
    }

    /**
     * @return returns that the numberSquare is not a mine
     */
    public boolean isMine(){
        return false;
    }
}
