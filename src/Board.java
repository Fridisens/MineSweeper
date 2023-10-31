import java.util.ArrayList;
import java.util.Random;

public class Board {

    int squaresInEachRow;
    public char[][] board;
    boolean gameover = false;
    static char[] alphabet = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};


    char[][] shadowBoard;
    double mineLow = 16;
    double mineHigh = 40;

    public int totalMineCount;

    boolean winner = false;


    public Board(int squaresInEachRow) {
        this.squaresInEachRow = squaresInEachRow;
        board = new char[squaresInEachRow][squaresInEachRow];
        // Initialize the game board with empty squares
        for (int i = 0; i < squaresInEachRow; i++) {
            for (int j = 0; j < squaresInEachRow; j++) {
                board[i][j] = '-';

            }
        }
        shadowBoard = new char[squaresInEachRow][squaresInEachRow];
        // Initialize the shadow board with empty squares
        for (int i = 0; i < squaresInEachRow; i++) {
            for (int j = 0; j < squaresInEachRow; j++) {
                shadowBoard[i][j] = '-';
            }
        }
        // Randomly place mines on the board
        this.totalMineCount = (int) Randomize();

        System.out.println("Amount of mines to find: " + totalMineCount);
    }

    public double Randomize() {
        Random random = new Random();
        // Get a random percentage within the specified range
        double percentage = this.mineLow + (this.mineHigh - this.mineLow) * random.nextDouble();
        double count = (board.length * board[0].length);
        // Calculate the number of mines to add to the board based on the percentage
        double value = (percentage / 100) * count;
        value = Math.round(value);
        int replaced = 0;
        ArrayList<String> used = new ArrayList<>();
        while (replaced < value) {
            int row = random.nextInt(board.length);
            int col = random.nextInt(board[0].length);
            String conCat = row + "" + col;
            // Replace if the position is not already used
            if (!used.contains((conCat))) {
                used.add(conCat);
                shadowBoard[row][col] = '*';
                replaced++;
            }
        }

        return value;
    }

    public static void printBoard(char[][] board) {
        int squaresInEachRow = board.length;

        //Display row numbers
        System.out.print("    ");
        for (int i = 1; i <= squaresInEachRow; i++) {
            if (i < 10) {
                System.out.print("   " + i + "  ");
            } else {
                System.out.print("  " + i + "  ");
            }
        }
        System.out.println();
        //Display the upper edge of the board
        System.out.print("    ");
        System.out.print(
                "┌─────");
        for (int i = 0; i < squaresInEachRow - 2; i++) {
            System.out.print("┬─────");
        }
        System.out.println("┬─────┐");

        for (int j = 0; j < squaresInEachRow; j++) {
            // Display squares and their contents
            System.out.print(" " + alphabet[j] + "  ");
            System.out.print("│  ");
            for (int i = 0; i < squaresInEachRow; i++) {
                System.out.print(board[j][i]);
                System.out.print("  │  ");
            }
            if (j != squaresInEachRow - 1) {
                System.out.println();
                // Display the middle line of the board
                System.out.print("    ");
                System.out.print(
                        "├─────");
                for (int i = 0; i < squaresInEachRow - 2; i++) {
                    System.out.print("┼─────");
                }
                System.out.println("┼─────┤");
            } else {
                // Display the lower edge of the board
                System.out.println();
                System.out.print(
                        "    └─────");
                for (int i = 0; i < squaresInEachRow - 2; i++) {
                    System.out.print("┴─────");
                }
                System.out.println("┴─────┘");
            }
        }
    }

    public int getRowIndex(String position) {
        /* takes a letter-number combination, e.g. b3, and translates it to
        correct row index in a 2d array (e.g. b3 = row index 1)
         */
        char row = position.toLowerCase().charAt(0);
        return new String(alphabet).indexOf(row);
    }

    public int getColumnIndex(String position) {
        /* takes a letter-number combination, e.g. b3, and translates it to
        correct column index in a 2d array (eg b3 = column index 2)
         */
        char column = position.charAt(1);
        return Character.getNumericValue(column) - 1;
    }

     public boolean checkSquare (String position) {
         //catches if the player types too few or too many characters, as well as if the user presses Enter and doesn't type anything.
         if (!isValidPosition(position)) {
            System.out.println("Invalid input. Please enter a valid position.");
             return false;

         }

        int rowIndex = getRowIndex(position);
        int colIndex = getColumnIndex(position);


        //checks if the box is cleared and does NOT contain a bomb
        if (rowIndex >= 0 && rowIndex < squaresInEachRow && colIndex >= 0 && colIndex < squaresInEachRow) {
            if (board[rowIndex][colIndex] == '-' && shadowBoard[rowIndex][colIndex] != '*') {
                shadowBoard[rowIndex][colIndex] = ' ';
                board[rowIndex][colIndex] = GetAmount(rowIndex, colIndex);
                return true;

                //checks if the box is cleared and contain a bomb
            } else if (board[rowIndex][colIndex] == '-' && shadowBoard[rowIndex][colIndex] == '*') {
                shadowBoard[rowIndex][colIndex] = '¤';
                gameOver();
                return true;
                //checks if the box is already cleared
            } else if (board[rowIndex][colIndex] == ' ') {
                System.out.println("That square is already cleared, choose another square.");
            }
        }
        return false;
    }


    public boolean gameOver() {
        // Check if the game is over (e.g., a mine was triggered)
        for (int i = 0; i < squaresInEachRow; i++) {
            for (int j = 0; j < squaresInEachRow; j++) {
                if (shadowBoard[i][j] == '*') {
                    System.out.println("GameOver");
                    printBoard(shadowBoard);
                    gameover = true;
                    return true;
                }
            }
        }
        gameover = false;
        return false;
    }

    public boolean checkVictory() {
        // Variables for counting the number of opened cells (without bombs) and the total number
        // of cells on the board
        int uncoveredCells = 0;
        int totalSafeCells = (squaresInEachRow * squaresInEachRow) - totalMineCount;

        // Loop through each cell on the game board
        for (int i = 0; i < squaresInEachRow; i++) {
            for (int j = 0; j < squaresInEachRow; j++) {
                //If the cell is open (contains dashes and not a bomb), the open cells counter is incremented
                if (board[i][j] != '-' && shadowBoard[i][j] != '*') {
                    uncoveredCells++;
                }
            }
        }
        // If the number of open cells equals the total number of cells, the player has won
        winner = (uncoveredCells == totalSafeCells);
            return winner;
    }


    public boolean isValidPosition(String position) {
        /* takes a string as input and checks that it is in the format of a letter and
        a number (for example "b3"), and then checks that the letter and the number
        correspond to indexes that are within the size of the char[][] board.
         */
        int num;
        // check that the input contains 2 or 3 characters, otherwise return false
        if (position.length() != 2 && position.length() != 3) {
            System.out.println("Input contains too few, or too many characters.");
            return false;
        }
        // convert the first character in input to a char in lower case
        char firstChar = position.toLowerCase().charAt(0);
        // check if char is in alphabet array, otherwise return false
        boolean isValidChar = false;
        int index = 0;
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] == firstChar) {
                isValidChar = true;
                index = i;
                break;
            }
        }
        if (!isValidChar) {
            System.out.println("The first character in the input is not a letter a-z.");
            return false;
        }
        // checks if chars index i alphabet-arrayen <= squaresInEachRow - 1, otherwise return false
        if (index >= squaresInEachRow) {
            System.out.println("The letter " + firstChar + " indicates a square outside " +
                    "the scope of the board.");
            return false;
        }

        // check that the second character is a number and if so assign that number to the variable num
        StringBuilder sb = new StringBuilder();
        char numChar1 = position.charAt(1);
        if (!Character.isDigit(numChar1)) {
            System.out.println("The second character " + numChar1 + " is not a number!");
            return false;
        }
        sb.append(numChar1);
        // if number of characters is 3, check that the second and third characters are numeric,
        // and if necessary assign that number to the variable num
        if (position.length() == 3) {
            char numChar2 = position.charAt(2);
            if (!Character.isDigit(numChar2)) {
                System.out.println("The third character " + numChar2 + " is not a number!");
                return false;
            } else {
                sb.append(numChar2);
            }
        }
        String numString = sb.toString();
        num = Integer.parseInt(numString);
        System.out.println("Player entered letter " + firstChar + " and number " + num);
        // check that num < squaresInEachRow, otherwise false
        return num <= squaresInEachRow;



    }

    public char GetAmount(int rowIndex, int colIndex) {
        // Initialize a counter for counting nearby mines
        int value = 0;

        for (int j = -1; j < 2; j++) {
            try {
                if (shadowBoard[rowIndex - 1][colIndex + j] == '*') {
                    value++;
                }
            } catch (Exception ex) {}
            try {
                if (shadowBoard[rowIndex][colIndex + j] == '*') {
                    value++;
                }
            } catch (Exception ex) {}
            try {
                if (shadowBoard[rowIndex + 1][colIndex + j] == '*') {
                    value++;
                }
            } catch (Exception ex) {}
        }

        return (char) (value + '0');
    }
}
