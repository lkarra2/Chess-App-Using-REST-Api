package chessengine;

import chessgame.Move;
import chessengine.ChessEngine;
import chessengine.Response;
import chessengine.Session;
import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.*;


public class ChessEngineTest {

    @Test
    public void testNewGame() throws Exception {
        ChessEngine chessEngine = new ChessEngine();
        Response response = chessEngine.newGame("", true, 1);
        assertNotNull(response);
        assertEquals( 0, response.getStatus());
    }

    @Test
    public void testMove() throws Exception {
        ChessEngine chessEngine = new ChessEngine();
        Response res = chessEngine.newGame("", true, 1);
        Session s = res.getSession();
        Move move = new Move("b7", "b6");
        Response response = chessEngine.move(move, s);
        assertEquals(0, response.getStatus());
    }

    @Test
    public void testTwoGameMoves() throws Exception {
        ChessEngine chessEngine = new ChessEngine();
        Response r = chessEngine.newGame("player1", true, 1);
        Session s1 = r.getSession();
        r = chessEngine.newGame("player2", false, 2);
        Session s2 = r.getSession();
        Move move = r.getNextMove();
        //System.out.println(move.getBegin() + " " + move.getEnd());
        while(chessEngine.getGameStatus(s1).getGameStatus() == 0 && chessEngine.getGameStatus(s2).getGameStatus() == 0) {
            r = chessEngine.move(move, s1);
            if(r.getStatus() != 0)
                continue;
            move = r.getNextMove();
            r = chessEngine.move(move, s2);
            move = r.getNextMove();
        }
        //System.out.println("Game status " + r.getGameStatus() + "Winner" + r.getWinner());
        //chessEngine.show(s1);
        //Thread.sleep(5000);
    }

    @Test
    public void testQuit() throws Exception {
        ChessEngine chessEngine = new ChessEngine();
        Response r = chessEngine.newGame("player1", true, 1);
        Session s = r.getSession();
        r = chessEngine.quit(s);
        assertEquals("Computer", r.getWinner());
    }

    @Test
    public void testGame() throws Exception {
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
