package chessengine;

import chessgame.Move;

import java.util.Scanner;


public class ChessEngineMain {


    public static void main(String[] args) throws Exception {
        //TODO Print Chessboard as ASCII with capital letters for white and small for black

        ChessEngine chessEngine = new ChessEngine();
        Response r = chessEngine.newGame("player1", true, 1);
        Session s = r.getSession();
        chessEngine.show(s);

        Scanner sc = new Scanner(System.in);
        String line;
        while((line = sc.nextLine()) != null) {
            if(line.equals("quit"))
                break;
            else {
                String[] arr = line.split("-");
                Move move = new Move(arr[0], arr[1]);
                r = chessEngine.move(move, s);
                System.out.println("Response: " + r);
            }
        }
    }
}
