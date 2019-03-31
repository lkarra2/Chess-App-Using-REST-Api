package chessclient;

import chessgame.Move;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ChessChallenger {

    // Runs a chess challenge between two running chess servers and plays till there is a winner

    private final ChessSession chessSession1;
    private final ChessSession chessSession2;
    private String baseUrl1;
    private String playerName1 = "Local user";
    private boolean firstMove1 = true;
    private int aiLevel1 = 1;
    private String baseUrl2;
    private String playerName2 = "Local user";
    private boolean firstMove2 = false;
    private int aiLevel2 = 2;

    @Override
    public String toString() {
        return "ChessChallenger{" +
                "chessSession1=" + chessSession1 +
                ", chessSession2=" + chessSession2 +
                ", baseUrl1='" + baseUrl1 + '\'' +
                ", playerName1='" + playerName1 + '\'' +
                ", firstMove1=" + firstMove1 +
                ", aiLevel1=" + aiLevel1 +
                ", baseUrl2='" + baseUrl2 + '\'' +
                ", playerName2='" + playerName2 + '\'' +
                ", firstMove2=" + firstMove2 +
                ", aiLevel2=" + aiLevel2 +
                '}';
    }

    public ChessChallenger(String[] args) {
        processArguments(args);
        chessSession1 = new ChessSession(baseUrl1, playerName1, firstMove1, aiLevel1);
        chessSession2 = new ChessSession(baseUrl2, playerName2, firstMove2, aiLevel2);
    }

    private void checkArgs(int i, int count) {
        if(++i < count)
            return;
        else
            help("Invalid Arguments1");
    }

    private void processArguments(String[] args) {
        if(args.length < 1)
            help("Invalid Arguments2");
        for(int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-player1":
                    checkArgs(i, args.length - 1);
                    this.playerName1 = args[++i];
                    break;
                case "-1white":
                    this.firstMove1 = true;
                    break;
                case "-1black":
                    this.firstMove1 = false;
                    break;
                case "-aiLevel1":
                    checkArgs(i, args.length - 1);
                    try {
                        this.aiLevel1 = Integer.parseInt(args[++i]);
                    } catch (NumberFormatException n) {
                        help("Invalid ai Level");
                    }
                    break;
                case "-URL1":
                    try {
                        this.baseUrl1 = args[++i];
                        URI uri = new URI(baseUrl1);
                    } catch (URISyntaxException e) {
                        help("Invalid Server URL: " + baseUrl1);
                    }
                    break;
                case "-player2":
                    checkArgs(i, args.length - 1);
                    this.playerName2 = args[++i];
                    break;
                case "-2white":
                    this.firstMove2 = true;
                    break;
                case "-2black":
                    this.firstMove2 = false;
                    break;
                case "-aiLevel2":
                    checkArgs(i, args.length - 1);
                    try {
                        this.aiLevel2 = Integer.parseInt(args[++i]);
                    } catch (NumberFormatException n) {
                        help("Invalid ai Level");
                    }
                    break;
                case "-URL2":
                    try {
                        this.baseUrl2 = args[++i];
                        URI uri = new URI(baseUrl2);
                    } catch (URISyntaxException e) {
                        help("Invalid Server URL: " + baseUrl2);
                    }
                    break;
                default:
                    help("Invalid Arguments3");
            }
        }
    }

    private void help(String message) {
        System.err.println(message);
        System.err.println("Usage: ChessClient [-player1 playerName] [-1white|-1black] [-aiLevel1 1|2] -URL1 ServerURL1 " +
                "[-player2 playerName] [-2white|-2black] [-aiLevel2 1|2] -URL2 ServerURL2");
        System.out.println(this);
        System.exit(1);
    }

    private void startChallenge() throws IOException {
        Move move1 = chessSession1.startGame();
        Move move2 = chessSession2.startGame();
        while (move2 != null && !gameHasEnded()) {
            move1 = chessSession1.move(move2);
            System.out.println("MOVE: [ " + move1.getBegin() + ", " + move1.getEnd() + " ]");
            if(!gameHasEnded())
                move2 = chessSession2.move(move1);
            System.out.println("MOVE: [ " + move2.getBegin() + ", " + move2.getEnd() + " ]");
        }
        chessSession1.quit();
        chessSession2.quit();
//        String status = chessSession1.status();
//        System.out.println("########Status: " + status);
        chessSession1.delete();
        chessSession2.delete();
    }

    private boolean gameHasEnded() throws IOException {
        //TODO CHeck for all initial input args
        chessSession1.status();
        chessSession2.status();
        String winner = "";
        if(chessSession1.getGameStatus() == 1) {
            winner = chessSession1.getPlayerName();
            System.out.println("Game Status: 1 & " + "WINNER: " + winner);
            return true;
        } else if(chessSession2.getGameStatus() == 1) {
            winner = chessSession2.getPlayerName();
            System.out.println("Game Status: 1 & " + "WINNER: " + winner);
            return true;
        } else if(chessSession1.getGameStatus() == 2 || chessSession2.getGameStatus() == 2) {
            System.out.println("Game Status: 2 & Draw Match");
            return true;
        }
        return false;
    }

    private static void printPlayerDetails(ChessChallenger chessChallenger) {
        System.out.println("   PLAYER 1:  " + chessChallenger.playerName1);
        System.out.println("   White:     " + chessChallenger.firstMove1);
        System.out.println("   Black:     " + !chessChallenger.firstMove1);
        System.out.println("   aiLevel:   " + chessChallenger.aiLevel1);
        System.out.println("   ServerURL: " + chessChallenger.baseUrl1);
        System.out.println();
        System.out.println();
        System.out.println("   PLAYER 2:  " + chessChallenger.playerName2);
        System.out.println("   White:     " + chessChallenger.firstMove2);
        System.out.println("   Black:     " + !chessChallenger.firstMove2);
        System.out.println("   aiLevel:   " + chessChallenger.aiLevel2);
        System.out.println("   ServerURL: " + chessChallenger.baseUrl2);
    }

    public static void main(String[] args) throws IOException {
        System.out.println("**********************************************");
        System.out.println("*                                            *");
        System.out.println("*       WELCOME TO THE CHESS CHALLENGE       *");
        System.out.println("*                                            *");
        System.out.println("**********************************************");
        ChessChallenger chessChallenger = new ChessChallenger(args);
        printPlayerDetails(chessChallenger);
        chessChallenger.startChallenge();
        System.out.println(chessChallenger);
    }
}
