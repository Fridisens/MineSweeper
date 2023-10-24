import java.util.ArrayList;
import java.util.Random;

public class Board {

    int squaresInEachRow;
    char[][] board;
    char[][] shadowBoard;
    double mineLow = 16;
    double mineHigh = 40;



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
        double amountMine = Randomize();

        //Debugprint, remove before release
        System.out.println("Antal minor: " + amountMine);
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
        char[] alphabet;
        alphabet = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
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


}
