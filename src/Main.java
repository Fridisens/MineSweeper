import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean cont = true;
        boolean gameOver = false;
        Scanner sc = new Scanner(System.in);



        while(cont){
            while(!gameOver){
                //Skapa nytt bräde
                Board b = new Board(16);
                Board.printBoard(b.board);

                //Spela spelet

                gameOver = b.gameOver();
            }

            // Fråga om man vill spela igen, eller avsluta spelet
            System.out.println("Vill du spela igen? (J/N)");
            String answer = sc.nextLine();
            if(answer.equals("J")) {
                gameOver = false;
                cont = true;
            }
            else
                cont = false;



        }
        /* Hur man använder metoderna getRowIndex och getColumnIndex:
        Om spelaren t.ex. anger att den vill undersöka ruta c6,
        så kan man skriva i = Board.getRowIndex("c6") för att få fram rätt radindex (2)
        och j = Board.getColumnIndex("c6") för att få fram rätt kolumnindex (5).
        Sedan hittar man rätt ruta i arrayen board genom board[i][j].
         */

        

    }
}