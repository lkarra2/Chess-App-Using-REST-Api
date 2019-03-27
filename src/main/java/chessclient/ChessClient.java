package chessclient;

import chessgame.Move;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class ChessClient {
    private final ChessSession chessSession;
    private String baseUrl;
    private String playerName = "Local user";
    private boolean firstMove = true;
    private int aiLevel = 1;

    @Override
    public String toString() {
        return "ChessClient{" +
                "baseUrl='" + baseUrl + '\'' +
                ", playerName='" + playerName + '\'' +
                ", firstMove=" + firstMove +
                ", aiLevel=" + aiLevel +
                '}';
    }

    public ChessClient(String[] args) {
        processArguments(args);
        chessSession = new ChessSession(baseUrl, playerName, firstMove, aiLevel);

    }

    private void processArguments(String[] args) {
        if(args.length < 1)
            help("Invalid Arguments");
        baseUrl = args[args.length - 1];

        try {
            URI uri = new URI(baseUrl);
        } catch (URISyntaxException e) {
            help("Invalid Server URL: " + baseUrl);
        }
        for(int i = 0; i < args.length - 1; i++) {
            switch (args[i]) {
                case "-player":
                    checkArgs(i, args.length - 1);
                    this.playerName = args[++i];
                    break;
                case "-white":
                    this.firstMove = true;
                    break;
                case "-black":
                    this.firstMove = false;
                    break;
                case "-aiLevel":
                    checkArgs(i, args.length - 1);
                    try {
                        this.aiLevel = Integer.parseInt(args[++i]);
                    } catch (NumberFormatException n) {
                        help("Invalid ai Level");
                    }
                    break;
                default:
                    help("Invalid Arguments");
            }
        }
    }

    private void checkArgs(int i, int count) {
        if(++i < count)
            return;
        else
            help("Invalid Arguments");
    }

    private void startGame() throws IOException {
        Move move = chessSession.startGame();
        if(move != null) {
            System.out.println("[ " + move.getBegin() + ", " + move.getEnd() + " ]");
        }
        Scanner scanner = new Scanner(System.in);
        String line = null;
        while ((line = scanner.nextLine())!= null) {
            if(line.equals("help"))
                helpCommands();
            else if(line.equals("quit")) {
                break;
            }
            else if(line.equals("status")) {
                String status = chessSession.status();
                System.out.println("Status: " + status);
            } else {
                move = processMove(line);
                if(move != null) {
                    System.out.println("[ " + move.getBegin() + ", " + move.getEnd() + " ]");
                }
            }
        }
        chessSession.quit();
        String status = chessSession.status();
        System.out.println("Status: " + status);
        chessSession.delete();
    }

    private Move processMove(String move) throws IOException {
        if(!move.matches("[a-h][1-8]-[a-h][1-8]")) {
            System.out.println("Invalid Move");
            return null;
        } else {
            String[] arr = move.split("-");
            return chessSession.move(new Move(arr[0], arr[1]));
        }
    }

    private void helpCommands() {
        System.out.println("Available Commands: help, quit, status, move[eg: e2-e3]");
    }

    private void help(String message) {
        System.err.println(message);
        System.err.println("Usage: ChessClient [-player playerName] [-white|-black] [-aiLevel 1|2] ServerURL");
        System.out.println(this);
        System.exit(1);
    }

    public static void main(String[] args) throws IOException {
        ChessClient chessClient = new ChessClient(args);
        chessClient.startGame();
        System.out.println(chessClient);
    }
}
