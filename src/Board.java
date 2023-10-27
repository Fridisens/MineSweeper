import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;

public class Board {

    int squaresInEachRow;
    public char[][] board;
    boolean gameover = false;
    static char[] alphabet = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};


    char[][] shadowBoard;
    double mineLow = 16;
    double mineHigh = 40;

    private int totalMineCount;

    boolean winner = false;


    public Board(int squaresInEachRow) {
        this.squaresInEachRow = squaresInEachRow;
        board = new char[squaresInEachRow][squaresInEachRow];
        for (int i = 0; i < squaresInEachRow; i++) {
            for (int j = 0; j < squaresInEachRow; j++) {
                board[i][j] = 'X';

            }
        }
        shadowBoard = new char[squaresInEachRow][squaresInEachRow];
        for (int i = 0; i < squaresInEachRow; i++) {
            for (int j = 0; j < squaresInEachRow; j++) {
                shadowBoard[i][j] = 'X';
            }
        }
        this.totalMineCount = (int) Randomize();


        //Debugprint, remove before release
        System.out.println("Antal minor: " + totalMineCount);
    }

    public double Randomize() {
        Random random = new Random();
        //Get percentage
        double percentage = this.mineLow + (this.mineHigh - this.mineLow) * random.nextDouble();
        double count = (board.length * board[0].length);
        //Get mines to add to board by percentage
        double value = (percentage / 100) * count;
        value = Math.round(value);
        int replaced = 0;
        ArrayList<String> used = new ArrayList<>();
        while (replaced < value) {
            int row = random.nextInt(board.length);
            int col = random.nextInt(board[0].length);
            String conCat = row + "" + col;
            //replace if not already did
            if (!used.contains((conCat))) {
                used.add(conCat);
                shadowBoard[row][col] = '*';
                replaced++;
            }
        }

        //Debugprint, remove before release
        printBoard(shadowBoard);

        return value;
    }

    public static void printBoard(char[][] board) {
        int squaresInEachRow = board.length;

        //rad med siffror
        System.out.print("    ");
        for (int i = 1; i <= squaresInEachRow; i++) {
            if (i < 10) {
                System.out.print("   " + i + "  ");
            } else {
                System.out.print("  " + i + "  ");
            }
        }
        System.out.println();
        //övre kanten:
        System.out.print("    ");
        System.out.print(
                "┌─────");
        for (int i = 0; i < squaresInEachRow - 2; i++) {
            System.out.print("┬─────");
        }
        System.out.println("┬─────┐");

        for (int j = 0; j < squaresInEachRow; j++) {
            //rad med rutor
            System.out.print(" " + alphabet[j] + "  ");
            System.out.print("│  ");
            for (int i = 0; i < squaresInEachRow; i++) {
                System.out.print(board[j][i]);
                System.out.print("  │  ");
            }
            if (j != squaresInEachRow - 1) {
                System.out.println();
                //mellanlinje
                System.out.print("    ");
                System.out.print(
                        "├─────");
                for (int i = 0; i < squaresInEachRow - 2; i++) {
                    System.out.print("┼─────");
                }
                System.out.println("┼─────┤");
            } else {
                //undre kanten:
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
        /* tar en bokstavs-siffer-kombination, t.ex. b3, och översätter den till
        korrekt rad-index i en 2d-array (t.ex. b3 = radindex 1)
         */
        char row = position.toLowerCase().charAt(0);
        return new String(alphabet).indexOf(row);
    }

    public int getColumnIndex(String position) {
        /* tar en bokstavs-siffer-kombination, t.ex. b3, och översätter det till
        korrekt kolumn-index i en 2d-array (t.ex. b3 = kolumnindex 2)
         */
        char column = position.charAt(1);
        return Character.getNumericValue(column) - 1;
    }

    public boolean checkSquare (String position) {
        //fångar upp om spelaren skriver in för få eller för många tecken, samt om anv trycker Enter och inte skriver ngt.
         //if (!isValidPosition(position)) {
        if (position.equals("") || position.length() > 2) {
            System.out.println("Invalid input. Please enter a valid posistion.");
             return false;
         }

        int rowIndex = getRowIndex(position);
        int colIndex = getColumnIndex(position);
        //kollar om rutan är röjd och INTE består av en bomb
        if (rowIndex >= 0 && rowIndex < squaresInEachRow && colIndex >= 0 && colIndex < squaresInEachRow) {
            if (board[rowIndex][colIndex] == 'X' && shadowBoard[rowIndex][colIndex] != '*') {
                shadowBoard[rowIndex][colIndex] = ' ';
                board[rowIndex][colIndex] = GetAmount(rowIndex, colIndex);
                return true;
                //kollar om rutan är röjd och består av en bomb
            } else if (board[rowIndex][colIndex] == 'X' && shadowBoard[rowIndex][colIndex] == '*') {
                gameOver();
                return true;
                //kollar om rutan redan är röjd
            } else if (board[rowIndex][colIndex] == ' ') {
                System.out.println("That square is already cleared, choose another square.");
            }
        } else {
            System.out.println("Invalid input. Please enter a valid position.");
        }
        return false;
    }


    public boolean gameOver() {
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
        //Variabler for att räkna antalet öppnade celler (utan bomber) och totala antalet celler på brädet
        int uncoveredCells = 0;
        int totalCells = (squaresInEachRow * squaresInEachRow) - totalMineCount;
        //Loopa igenom varje cell på spelplanen
        for (int i = 0; i < squaresInEachRow; i++) {
            for (int j = 0; j < squaresInEachRow; j++) {

                //Om cellen är öppen (innehåller mellanslag och inte en bomb, ökar räknaren för öppna celler
                if (board[i][j] == ' ' && shadowBoard[i][j] != '*') {
                    uncoveredCells++;
                }
            }
        }
        // Om antalet öppna celler är lika med totala antalet celler, har spelaren vunnit
        if (uncoveredCells == totalCells) {
            winner = true;
            return true;
        }
        return false;

    }


    public boolean isValidPosition(String position) {
        /* takes a string as input and checks that it is in the format of a letter and
        a number (for example "b3"), and then checks that the letter and the number
        correspond to indexes that are within the size of the char[][] board.
         */
        int num;
        // kolla att input innehåller 2 eller 3 tecken, annars return false)
        if (position.length() != 2 && position.length() != 3) {
            // debugprint TODO remove before release
            System.out.println("Input contains too few, or too many characters.");
            return false;
        }
        // gör om första tecknet i input till en char i lower case
        char firstChar = position.toLowerCase().charAt(0);
        // kolla om char finns i alphabet-array, annars return false
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
            // debugprint TODO remove before release
            System.out.println("The first character in the input is not a letter a-z.");
            return false;
        }
        // kolla om chars index i alphabet-arrayen <= squaresInEachRow - 1, annars false
        if (index >= squaresInEachRow) {
            // debugprint TODO remove before release
            System.out.println("The letter " + firstChar + " indicates a square outside " +
                    "the scope of the board.");
            return false;
        }

        // kolla att andra tecknet är ett nummer och tilldela i så fall
        // det numret till variabeln num
        StringBuilder sb = new StringBuilder();
        char numChar1 = position.charAt(1);
        if (!Character.isDigit(numChar1)) {
            // debugprint TODO remove before release
            System.out.println("The second character " + numChar1 + " is not a number!");
            return false;
        }
        sb.append(numChar1);
        // om antal tecken är 3, kolla att andra och tredje tecknen är numeriska,
        // och tilldela isf det numret till variabeln num
        if (position.length() == 3) {
            char numChar2 = position.charAt(2);
            if (!Character.isDigit(numChar2)) {
                // debugprint TODO remove before release
                System.out.println("The third character " + numChar2 + " is not a number!");
                return false;
            } else {
                sb.append(numChar2);
            }
        }
        String numString = sb.toString();
        num = Integer.parseInt(numString);
        // debugprint TODO remove before release
        System.out.println("Player entered letter " + firstChar + " and number " + num);
        // kolla att num < squaresInEachRow, annars false
        return num < squaresInEachRow;
        //return true;


    }


    public void resetBoard() {
        for (int i = 0; i < squaresInEachRow; i++) {
            for (int j = 0; j < squaresInEachRow; j++) {
                board[i][j] = 'X';
            }
        }
        winner = false;
    }
    public char GetAmount(int rowIndex, int colIndex) {
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
