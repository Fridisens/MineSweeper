import java.util.ArrayList;
import java.util.Random;

public class Board {

    int squaresInEachRow;
    public char[][] board;

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

    public double Randomize(){
        Random random = new Random();
        //Get percentage
        double percentage = this.mineLow + (this.mineHigh - this.mineLow) * random.nextDouble();
        double count = (board.length * board[0].length);
        //Get mines to add to board by percentage
        double value = (percentage / 100) * count;
        value = Math.round(value);
        int replaced = 0;
        ArrayList<String> used = new ArrayList<>();
        while(replaced < value) {
            int row = random.nextInt(board.length);
            int col = random.nextInt(board[0].length);
            String conCat = row + "" + col;
            //replace if not already did
            if(!used.contains((conCat))) {
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
            }
            else {
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
            }
            else {
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

    public boolean placeMarker (String position) {

         if (position.equals("") || position.length() > 2)  {
            System.out.println("Invalid input. Please enter a valid posistion.");
             return false;
         }

        int rowIndex = getRowIndex(position);
        int colIndex = getColumnIndex(position);

        if (rowIndex >= 0 && rowIndex < squaresInEachRow && colIndex >= 0 && colIndex < squaresInEachRow) {
            if (board[rowIndex][colIndex] == 'X' && shadowBoard[rowIndex][colIndex] != '*') {
                board[rowIndex][colIndex] = ' ';
                return true;
            } else if (board[rowIndex][colIndex] == 'X' && shadowBoard[rowIndex][colIndex] == '*') {
                gameOver();
                return true;
            } else if (board[rowIndex][colIndex] == ' ') {
                System.out.println("That square is already cleared, choose another square.");
            }
        }else {
            System.out.println("Invalid input. Please enter a valid posistion.");
        } return false;
    }


        public boolean gameOver () {
            for (int i = 0; i < squaresInEachRow; i++) {
                for (int j = 0; j < squaresInEachRow; j++) {
                    if (shadowBoard[i][j] == '*') {
                        System.out.println("GameOver");
                        return true;
                    }
                }
            }
            return false;
        }

        public boolean checkVictory(){
        //Variabler for att räkna antalet öppnade celler (utan bomber) och totala antalet celler på brädet
        int uncoveredCells = 0;
        int totalCells = (squaresInEachRow * squaresInEachRow) - totalMineCount;
        //Loopa igenom varje cell på spelplanen
        for (int i = 0; i <squaresInEachRow; i++){
            for (int j = 0; j <squaresInEachRow; j++){

                //Om cellen är öppen (innehåller mellanslag och inte en bomb, ökar räknaren för öppna celler
                if (board[i][j] == ' ' && shadowBoard [i][j] != '*'){
                    uncoveredCells++;
                }
            }
        }
        // Om antalet öppna celler är lika med totala antalet celler, har spelaren vunnit
        if (uncoveredCells == totalCells){
            winner = true;
            return true;
        }
        return false;

        }


        public void resetBoard () {
            for (int i = 0; i < squaresInEachRow; i++) {
                for (int j = 0; j < squaresInEachRow; j++) {
                    board[i][j] = 'X';
                }
            }
            winner = false;
        }

    }