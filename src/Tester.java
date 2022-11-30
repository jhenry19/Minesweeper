public class Tester {

    public static void main(String[] args) {
        System.out.print(new Grid(10,12, 10));
    }

    public static String theString()
    {
        MineSquare m = new MineSquare();
        int width = 26;
        int height = 18;

        Square[][] grid = new Square[height][width];

        // make grid
        for (int x = 0; x < height; x++)
        {
            for (int y = 0; y < width; y++)
            {
                grid[x][y] = new NumberSquare(0, x, y);
            }
        }


        String toDisplay = "   ";
        for (int i = 0; i < width; i++)
        {
            if (i <= 9) toDisplay += (i + "  ");
            else toDisplay += (i + " ");
        }
        toDisplay += "\n";

        for (int x = 0; x < grid.length; x++)
        {
            if (x <= 9) toDisplay += (x + "  ");
            else toDisplay += (x + " ");
            for (int y = 0; y < grid[0].length; y++)
            {
                toDisplay += grid[x][y].toString() + "  ";
            }
            toDisplay += "\n";
        }
        return toDisplay;
    }
}
