public class Board {

    int squaresInEachRow;
    char[][] board;



    public Board(int squaresInEachRow) {
        this.squaresInEachRow = squaresInEachRow;
        board = new char[squaresInEachRow][squaresInEachRow];
        for (int i = 0; i < squaresInEachRow; i++) {
            for (int j = 0; j < squaresInEachRow; j++) {
                board[i][j] = 'X';
            }
        }
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
                System.out.print(board[0][i]);
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
