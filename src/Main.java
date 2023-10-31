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
            Board b = new Board(6);

            while(!gameOver){
                Board.printBoard(b.board);
                System.out.println("Which square would you like to examine?");
                String position = sc.nextLine();

              b.checkSquare(position);

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
                // When restarting the game, reset the booleans/status
                gameOver = false;
                victory = false;
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