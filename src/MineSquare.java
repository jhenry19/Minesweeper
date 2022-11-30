/**
 * Julian Henry
 * CS110
 * MineSquare class for Minesweeper
 */
public class MineSquare extends Square{
    /**
     * Uncovers the mine and sets the element to an *
     * @return whether the square was uncovered
     */
    public boolean uncover(){
        if (!isFlagged()) {
            setUncovered(true);
            setElement("*");
            return true;
        }
        return false;
    }

    /**
     * @return returns that the mineSquare is a mine
     */
    public boolean isMine(){
        return true;
    }
}
