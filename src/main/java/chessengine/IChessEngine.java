package chessengine;

import chessgame.Move;

public interface IChessEngine {

    // Interface defining the functions available to the Client

    Response newGame(String playerName, boolean firstMove, int iaLevel) throws Exception;
    Response move(Move m, Session session) throws Exception;
    Response quit(Session session) throws Exception;
    void show(Session s);

    Response getGameStatus(Session s);

    Response delete(Session s);
}
