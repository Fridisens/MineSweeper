public class Board {

    int rowLength;
    char[][] board;



    public Board(int rowLength) {
        this.rowLength = rowLength;
        board = new char[rowLength][rowLength];
        for (int i = 0; i < rowLength; i++) {
            for (int j = 0; j < rowLength; j++) {
                board[i][j] = 'X';
            }
        }
    }
    //{
    //            {' ', ' ', ' ', ' ', ' ', ' '},
    //            {' ', ' ', ' ', ' ', ' ', ' '},
    //            {' ', ' ', ' ', ' ', ' ', ' '},
    //            {' ', ' ', ' ', ' ', ' ', ' '},
    //            {' ', ' ', ' ', ' ', ' ', ' '},
    //            {' ', ' ', ' ', ' ', ' ', ' '}};
    //}


    public static void printBoard(char[][] board, int rowLength) {
        char[] alphabet;
        alphabet = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        //rad med siffror
        System.out.print("    ");
        for (int i = 1; i <= rowLength; i++) {
            System.out.print("   " + i + "  ");
        }
        System.out.println();
        //övre kanten:
        System.out.print("    ");
        System.out.print(
                "┌─────");
        for (int i = 0; i < rowLength - 2; i++) {
            System.out.print("┬─────");
        }
        System.out.println("┬─────┐");

        for (int j = 0; j < rowLength; j++) {
            //rad med rutor
            System.out.print(" " + alphabet[j] + "  ");
            System.out.print("│  ");
            for (int i = 0; i < rowLength; i++) {
                System.out.print(board[0][i]);
                System.out.print("  │  ");
            }
            if (j != rowLength - 1) {
                System.out.println();
                //mellanlinje
                System.out.print("    ");
                System.out.print(
                        "├─────");
                for (int i = 0; i < rowLength - 2; i++) {
                    System.out.print("┼─────");
                }
                System.out.println("┼─────┤");
            }
            else {
                //undre kanten:
                System.out.println();
                System.out.print(
                        "    └─────");
                for (int i = 0; i < rowLength - 2; i++) {
                    System.out.print("┴─────");
                }
                System.out.println("┴─────┘");
            }
        }
    }


}
