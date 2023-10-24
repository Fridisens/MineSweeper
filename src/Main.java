public class Main {
    public static void main(String[] args) {

        //Skapa nytt bräde

        Board b = new Board(6);
        Board.printBoard(b.board);

        /* Hur man använder metoderna getRowIndex och getColumnIndex:
        Om spelaren t.ex. anger att den vill undersöka ruta c6,
        så kan man skriva i = Board.getRowIndex("c6") för att få fram rätt radindex (2)
        och j = Board.getColumnIndex("c6") för att få fram rätt kolumnindex (5).
        Sedan hittar man rätt ruta i arrayen board genom board[i][j].
         */

        

    }
}