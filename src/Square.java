/**
 * Julian Henry
 * CS110
 * Abstract Square class for Minesweeper
 */
public abstract class Square {
    //Variables with default values
    private String element    = "x";
    private boolean flagged   = false;
    private boolean uncovered = false;

    /**
     * Default constructor for Square class
     */
    public Square(){}

    /**
     * A constructor for the Square class that takes 3 arguments
     * @param e the element representing the square
     * @param f whether or not the square is flagged
     * @param u whether or not the square is uncovered
     */
    public Square(String e, boolean f, boolean u)
    {
        element   = e;
        flagged   = f;
        uncovered = u;
    }

    /**
     * @return a boolean that represents the flagged variable
     */
    public boolean isFlagged() {
        return flagged;
    }

    /**
     * @return a boolean that represents the uncovered variable
     */
    public boolean isUncovered() {
        return uncovered;
    }

    /**
     * Flags a square and "unflags" if it is flagged
     */
    public void flagSquare()
    {
        if (flagged) {
            setElement("x");
            flagged = false;
        }
        else {
            setElement("f");
            flagged = true;
        }
    }

    /**
     * Uncovers a square
     */
    public void setUncovered(boolean u)
    {
        uncovered = u;
    }

    /**
     * Sets the element that represents the mine
     * @param e the element to represent the mine
     */
    public void setElement(String e){
        element = e;
    }

    /**
     * @return a string representation of the square
     */
    public String toString()
    {
        return String.format("%s", element);
    }

    /**
     *  @return whether the square is uncovered or not
     */
    public abstract boolean uncover();

    /**
     * @return whether or not the square is a mine
     */
    public abstract boolean isMine();
}
