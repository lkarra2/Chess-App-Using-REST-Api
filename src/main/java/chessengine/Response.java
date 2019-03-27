package chessengine;

import chessgame.Move;

public class Response {
    int status, gameStatus;
    String message, winner;
    Session session;
    Move nextMove;

    @Override
    public String toString() {
        return "Response{" +
                "status=" + status +
                ", gameStatus=" + gameStatus +
                ", message='" + message + '\'' +
                ", winner='" + winner + '\'' +
                ", session=" + session +
                ", nextMove=" + nextMove +
                '}';
    }

    public void setWinner(String winner) { this.winner = winner; }

    public String getWinner() { return winner; }

    public void setGameStatus(int gameStatus) { this.gameStatus = gameStatus; }

    public int getGameStatus() { return gameStatus; }

    public void setNextMove(Move nextMove) { this.nextMove = nextMove; }

    public void setStatus(int status) {
        this.status = status;
    }

    public Move getNextMove() {
        return nextMove;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }
}
