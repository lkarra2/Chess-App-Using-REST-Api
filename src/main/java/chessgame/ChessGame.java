package chessgame;

import pl.art.lach.mateusz.javaopenchess.core.Chessboard;
import pl.art.lach.mateusz.javaopenchess.core.Colors;
import pl.art.lach.mateusz.javaopenchess.core.Game;
import pl.art.lach.mateusz.javaopenchess.core.Square;
import pl.art.lach.mateusz.javaopenchess.core.ai.AI;
import pl.art.lach.mateusz.javaopenchess.core.ai.AIFactory;
import pl.art.lach.mateusz.javaopenchess.core.moves.MovesHistory;
import pl.art.lach.mateusz.javaopenchess.core.pieces.implementation.King;
import pl.art.lach.mateusz.javaopenchess.core.players.Player;
import pl.art.lach.mateusz.javaopenchess.core.players.PlayerType;
import pl.art.lach.mateusz.javaopenchess.utils.GameModes;
import pl.art.lach.mateusz.javaopenchess.utils.GameTypes;
import javax.swing.*;
import java.util.ArrayList;

public class ChessGame {

    private final String playerName;
    private final boolean firstMove;
    private final int matchMoves = 1000;
    private Game game;
    private final Chessboard chessboard;
    MovesHistory moves;
    Player winner;
    int countMoves = 0;

    public String getWinner() {
        if(winner == null)
            return "";
        if(firstMove && winner.getColor() == Colors.WHITE)
            return playerName;
        if(!firstMove && winner.getColor() == Colors.BLACK)
            return playerName;
        return "Computer";
    }

    public int getGameStatus() {
        return gameStatus;
    }

    int gameStatus;


    public ChessGame(String playerName, boolean firstMove) {
        this(playerName, firstMove, 1);
    }

    public ChessGame(String playerName, boolean firstMove, int aiLevel) {
        this.playerName = playerName;
        this.firstMove = firstMove;
        if(isFirstMove()) {
            game = new GameBuilder()
                    .setWhitePlayerName(playerName)
                    .setBlackPlayerName("COMPUTER")
                    .setWhitePlayerType(PlayerType.COMPUTER)
                    .setBlackPlayerType(PlayerType.COMPUTER)
                    .setGameMode(GameModes.NEW_GAME)
                    .setGameType(GameTypes.LOCAL)
                    .setPiecesForNewGame(true)
                    .build();
        } else {
            game = new GameBuilder()
                    .setBlackPlayerName(playerName)
                    .setWhitePlayerName("COMPUTER")
                    .setWhitePlayerType(PlayerType.COMPUTER)
                    .setBlackPlayerType(PlayerType.COMPUTER)
                    .setGameMode(GameModes.NEW_GAME)
                    .setGameType(GameTypes.LOCAL)
                    .setPiecesForNewGame(true)
                    .build();
        }
        chessboard = game.getChessboard();
        moves = game.getMoves();
        AI ai = AIFactory.getAI(aiLevel);
        game.setAi(ai);
        if(!isFirstMove()) {
            game.doComputerMove();
            //game.nextMove();
        }
    }

    public boolean isFirstMove() {
        return firstMove;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Game getGame() {
        return game;
    }


    public Move getLastMove() {
        pl.art.lach.mateusz.javaopenchess.core.moves.Move lastMove = moves.getLastMoveFromHistory();
        Move myMove = new Move(lastMove.getFrom().getAlgebraicNotation(), lastMove.getTo().getAlgebraicNotation());
        return myMove;
    }

    public ArrayList<Move> getAllMoves() {
        ArrayList<Move> listOfMoves = new ArrayList<>();

        for(String str: moves.getMoves()) {
            String[] arr = str.split("-");
            if(arr.length == 2) {
                Move m = new Move(arr[0], arr[1]);
                listOfMoves.add(m);
            }
        }
        return listOfMoves;
    }

    public void move(Move move) {
        if(countMoves++ > matchMoves) {
            winner = null;
            game.setIsEndOfGame(true);
            game.setBlockedChessboard(true);
        }
        if(game.isIsEndOfGame())
            return;
        Square beginSquare = getSquare(move.getBegin());
        Square endSquare = getSquare(move.getEnd());
        if(beginSquare.getPiece() == null)
            throw new RuntimeException("Begin square " + move + " Doesn't have a piece");
        chessboard.move(beginSquare, endSquare);
        game.nextMove();
        endGameIfNeeded();
        if(game.isIsEndOfGame())
            return;
        game.doComputerMove();
        //game.nextMove();
        endGameIfNeeded();
    }

    public void endGame() {
        if(firstMove) {
            winner = game.getSettings().getPlayerBlack();
            game.setIsEndOfGame(true);
            game.setBlockedChessboard(true);
            gameStatus = 1;
        }
        else {
            winner = game.getSettings().getPlayerWhite();
            game.setIsEndOfGame(true);
            game.setBlockedChessboard(true);
            gameStatus = 1;
        }
    }

    private Square getSquare(String sMove) {
        int row = 0;
        int col = 1;
        if(sMove.length() > 2) {
            row++;
            col++;
        }
        //System.out.println("MOVE#####: " + sMove);
        int begin_i = sMove.charAt(row) - 'a';
        int begin_j = '8' - sMove.charAt(col);
        return chessboard.getSquare(begin_i, begin_j);
    }

    public void endGameIfNeeded() {
        checkKingStatus(chessboard.getKingWhite(), Colors.WHITE);
        checkKingStatus(chessboard.getKingBlack(), Colors.BLACK);
        int nBlack = chessboard.getAllPieces(Colors.BLACK).size();
        int nWhite = chessboard.getAllPieces(Colors.WHITE).size();
        if(nBlack == 1 && nWhite == 1) {
            winner = null;
            game.setIsEndOfGame(true);
            game.setBlockedChessboard(true);
        }
    }

    private void checkKingStatus(King king, Colors color) {
        gameStatus = king.isCheckmatedOrStalemated();
        switch(gameStatus) {
            case 1:
                if(king.getPlayer().getColor() == color) {
                    winner = game.getSettings().getPlayerBlack();
                } else
                    winner = game.getSettings().getPlayerWhite();
            // Fall through
            case 2:
                game.setIsEndOfGame(true);
                game.setBlockedChessboard(true);
                break;
        }
    }

    public void show() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Game");
            frame.add(game);
            frame.setSize(800, 600);
            frame.setVisible(true);
        });
    }


    public boolean isEndOFGame() {
        return game.isIsEndOfGame();
    }
}
