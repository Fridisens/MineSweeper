import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean cont = true;
        boolean gameOver = false;
        boolean victory = false;

        Scanner sc = new Scanner(System.in);

        while(cont){
            //Skapa nytt bräde
            Board b = new Board(2);
            Board.printBoard(b.board);
            while(!gameOver){

                //Spela spelet
                //b[Board.getRowIndex("b1")][Board.getColumnIndex("b1")]
                System.out.println("Vilken ruta vill du undersöka?");
                String position = sc.nextLine();

              b.checkSquare(position);


                /* denna kod kan nu tas bort, låter den bara vara tillfälligt

                if(!position.equals(""))
                    if(position.length() > 1)
                       b.placeMarker(position);
                //tillfällig testkod, ersätt med lämpligt spelflöde
               /* int i = b.getRowIndex(position);
                int j = b.getColumnIndex(position);
                if (b.board[i][j] == 'X') {
                    b.board[i][j] = ' ';
                }

                */

                Board.printBoard(b.board);

                //kontrollera om spelaren vunnit
                if (b.checkVictory()){
                    victory = true;
                    gameOver = true;
                    
                }

                gameOver = b.gameover;
            }

            //Vinstmeddelande beroende på värdet av victory
            if (victory){
                System.out.println("Congratulations! You won!");
            } else {
                System.out.println("Better luck next time!");

            }

            // Fråga om man vill spela igen, eller avsluta spelet
            System.out.println("Vill du spela igen? (J/N)");
            String answer = sc.nextLine();
            if(answer.equalsIgnoreCase("J")) {
                gameOver = false;
                cont = true;
            }
            else
                cont = false;




        /* Hur man använder metoderna getRowIndex och getColumnIndex:
        Om spelaren t.ex. anger att den vill undersöka ruta c6,
        så kan man skriva i = Board.getRowIndex("c6") för att få fram rätt radindex (2)
        och j = Board.getColumnIndex("c6") för att få fram rätt kolumnindex (5).
        Sedan hittar man rätt ruta i arrayen board genom board[i][j].
         */


        }
    }
}