import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean cont = true;
        boolean gameOver = false;
        boolean victory = false;
        int gameCount = 0;
        int score = 0;

        Scanner sc = new Scanner(System.in);

        while(cont){
            //Skapa nytt bräde
            Board b = new Board(4);

            while(!gameOver){
                Board.printBoard(b.board);
                //Spela spelet
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

                //Board.printBoard(b.board);

                //kontrollera om spelaren vunnit
                if (b.checkVictory()){
                    victory = true;
                    gameOver = true;
                    
                }
                else {
                    gameOver = b.gameover;
                }
            }

            //Vinstmeddelande beroende på värdet av victory
            if (victory){
                System.out.println("Congratulations! You won!");
                score++;
            } else {
                System.out.println("Better luck next time!");
            }
            //Räknar hur många gånger man har spelat
            gameCount++;

            // Fråga om man vill spela igen, eller avsluta spelet
            System.out.println("Would you like to play again? (Y/N)");
            String answer = sc.nextLine();
            if(answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("yes")) {
                gameOver = false;
                cont = true;
            }
            else {
                cont = false;
                System.out.println("Games played: " + gameCount);
                System.out.println("Victories:    " + score);
            }
        }
    }
}