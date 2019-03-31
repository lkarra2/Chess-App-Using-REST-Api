package chessengine;

import chessgame.ChessGame;
import chessgame.Move;

import java.util.HashMap;

public class ChessEngine implements IChessEngine {

    // Implementation of the IChessEngine Interface

    HashMap<Session, ChessGame> map = new HashMap<>();

    @Override
    public Response newGame(String playerName, boolean firstMove, int aiLevel) throws Exception {
        ChessGame chessGame = new ChessGame(playerName, firstMove);
        Session s = new Session();
        map.put(s, chessGame);
        Response res = new Response();
        res.setStatus(0);
        res.setSession(s);
        if(!firstMove) {
            res.setNextMove(chessGame.getLastMove());
        }
        return res;
    }

    @Override
    public Response move(Move m, Session session) throws Exception {
        ChessGame chessGame = map.get(session);
        chessGame.move(m);
        Response res = new Response();
        res.setStatus(0);
        if(chessGame.isEndOFGame()) {
            res.setGameStatus(chessGame.getGameStatus());
            res.setWinner(chessGame.getWinner());
        }
        res.setSession(session);
        Move lastMove = chessGame.getLastMove();
        res.setNextMove(lastMove);
        return res;
    }

    @Override
    public Response quit(Session session) throws Exception {
        ChessGame chessGame = map.get(session);
        chessGame.endGame();
        Response res = new Response();
        res.setWinner(chessGame.getWinner());
        res.setGameStatus(chessGame.getGameStatus());
        return res;
    }

    @Override
    public void show(Session s) {
        ChessGame chessGame = map.get(s);
        chessGame.show();
    }

    @Override
    public Response getGameStatus(Session s) {
        ChessGame chessGame = map.get(s);
        Response res = new Response();
        res.setWinner(chessGame.getWinner());
        res.setGameStatus(chessGame.getGameStatus());
        return res;
    }

    @Override
    public Response delete(Session s) {
        ChessGame chessGame = map.get(s);
        chessGame.endGame();
        map.remove(s);
        Response res = new Response();
        res.setStatus(0);
        return res;
    }
}
