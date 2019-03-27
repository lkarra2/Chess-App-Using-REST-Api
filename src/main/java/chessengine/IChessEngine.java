package chessengine;

import chessgame.Move;

public interface IChessEngine {
    Response newGame(String playerName, boolean firstMove, int iaLevel) throws Exception;
    Response move(Move m, Session session) throws Exception;
    Response quit(Session session) throws Exception;
    void show(Session s);

    Response getGameStatus(Session s);

    Response delete(Session s);
}
