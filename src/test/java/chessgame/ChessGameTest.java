package chessgame;

import chessgame.ChessGame;
import chessgame.Move;
import org.junit.Test;
import pl.art.lach.mateusz.javaopenchess.core.players.PlayerType;

import java.util.Scanner;

import static org.junit.Assert.*;

public class ChessGameTest {
    private ChessGame chessGame;

    @Test
    public void testCreate() {
        chessGame = new ChessGame("Mana", false);
        assertEquals("Mana", chessGame.getPlayerName());
        assertEquals(false, chessGame.isFirstMove());
    }

    @Test
    public void testGetLastMove() {
        chessGame = new ChessGame("Mana", false);
        assertNotNull(chessGame.getLastMove());
    }

    @Test
    public void testMove() {
        chessGame = new ChessGame("Mana", false);
        Move move = new Move("g7", "g6");
        chessGame.move(move);
        //System.out.println(chessGame.getAllMoves());
        assertEquals("g6", chessGame.getAllMoves().get(1).getEnd());
    }

    @Test
    public void testTwoGames() throws InterruptedException {
        ChessGame wGame = new ChessGame("Mana", true, 1); //White Player
        ChessGame bGame = new ChessGame("Bhepu", false, 2); //Black Player
        while (!wGame.getGame().isIsEndOfGame() && !bGame.getGame().isIsEndOfGame()) {
            Move move1 = bGame.getLastMove();
            wGame.move(move1);
            Move move2 = wGame.getLastMove();
            bGame.move(move2);
        }
        //wGame.show();
        //Thread.sleep(5000);
        //System.out.println(wGame.getAllMoves());
    }

    @Test
    public void testEndGame() {
        ChessGame game = new ChessGame("Mana", true, 1);
        game.endGame();
        assertEquals(true, game.isEndOFGame());
        assertEquals("Computer", game.getWinner());
    }

}
