package chessserver;

import chessengine.ChessEngine;
import chessengine.Response;
import chessengine.Session;
import chessgame.Move;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    // Defines all the Postmapping functions ["/new-game", "/move", "/status", "/quit", "/delete"]

    private ChessEngine chessEngine = new ChessEngine();

    public static class NewGameRequest {
        private String playerName;
        private boolean firstMove;
        private int aiLevel;

        public String getPlayerName() {
            return playerName;
        }

        public void setPlayerName(String playerName) {
            this.playerName = playerName;
        }

        public boolean isFirstMove() { return firstMove; }

        public void setFirstMove(boolean firstMove) {
            this.firstMove = firstMove;
        }

        public int getAiLevel() {
            return aiLevel;
        }

        public void setAiLevel(int aiLevel) {
            this.aiLevel = aiLevel;
        }
    }

    public static class NewMoveRequest {
        private Move move;
        private Session session;

        public Move getMove() {
            return move;
        }

        public void setMove(Move move) {
            this.move = move;
        }

        public Session getSession() {
            return session;
        }

        public void setSession(Session session) {
            this.session = session;
        }
    }

    @PostMapping("/new-game")
    public Response newGame(@RequestBody NewGameRequest request) {
        try {
            return chessEngine.newGame(request.playerName, request.firstMove, request.aiLevel);
        } catch (Exception e) {
            e.printStackTrace();
            return throwError(e.getMessage());
        }

    }

    @PostMapping("/move")
    public Response move(@RequestBody NewMoveRequest request) {
        try {
            return chessEngine.move(request.move, request.session);
        } catch (Exception e) {
            e.printStackTrace();
            return throwError(e.getMessage());
        }
    }

    @PostMapping("/quit")
    public Response quit(@RequestBody Session session) {
        try {
            return chessEngine.quit(session);
        } catch (Exception e) {
            e.printStackTrace();
            return throwError(e.getMessage());
        }
    }

    @PostMapping("/delete")
    public Response delete(@RequestBody Session session) {
        try {
            return chessEngine.delete(session);
        } catch (Exception e) {
            e.printStackTrace();
            return throwError(e.getMessage());
        }
    }

    @PostMapping("/status")
    public Response getGameStatus(@RequestBody Session session) {
        try {
            return chessEngine.getGameStatus(session);
        } catch (Exception e) {
            e.printStackTrace();
            return throwError(e.getMessage());
        }
    }

    private Response throwError(String message) {
        Response response = new Response();
        response.setStatus(1);
        response.setMessage("Exception: " + message);
        return response;
    }

}