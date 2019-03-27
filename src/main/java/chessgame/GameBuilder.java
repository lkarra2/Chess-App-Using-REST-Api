package chessgame;

import pl.art.lach.mateusz.javaopenchess.core.Colors;
import pl.art.lach.mateusz.javaopenchess.core.Game;
import pl.art.lach.mateusz.javaopenchess.core.players.PlayerFactory;
import pl.art.lach.mateusz.javaopenchess.core.players.PlayerType;
import pl.art.lach.mateusz.javaopenchess.utils.GameModes;
import pl.art.lach.mateusz.javaopenchess.utils.GameTypes;
import pl.art.lach.mateusz.javaopenchess.utils.Settings;

public class GameBuilder {

    private Settings settings = new Settings(PlayerFactory.getInstance("", Colors.WHITE, PlayerType.COMPUTER), PlayerFactory.getInstance("", Colors.BLACK, PlayerType.COMPUTER));

    private boolean setPiecesForNewGame = true;

    private boolean isChatEnabled = false;

    public GameBuilder setWhitePlayerName(String whitePlayerName) {
        settings.getPlayerWhite().setName(whitePlayerName);
        return this;
    }

    public GameBuilder setBlackPlayerName(String blackPlayerName) {
        settings.getPlayerBlack().setName(blackPlayerName);
        return this;
    }

    public GameBuilder setBlackPlayerType(PlayerType playerType) {
        settings.getPlayerBlack().setType(playerType);
        return this;
    }

    public GameBuilder setWhitePlayerType(PlayerType playerType) {
        settings.getPlayerWhite().setType(playerType);
        return this;
    }

    public GameBuilder setGameMode(GameModes mode) {
        settings.setGameMode(mode);
        return this;
    }

    public GameBuilder setGameType(GameTypes type) {
        settings.setGameType(type);
        return this;
    }

    public GameBuilder setPiecesForNewGame(boolean setPiecesForNewGame) {
        this.setPiecesForNewGame = setPiecesForNewGame;
        return this;
    }

    public GameBuilder setChatEnabled(boolean isChatEnabled) {
        this.isChatEnabled = isChatEnabled;
        return this;
    }

    public Game build() {
        Game game = new Game();
        if (setPiecesForNewGame) {
            game.getChessboard().setPieces4NewGame(settings.getPlayerWhite(), settings.getPlayerBlack());
        }
        game.getChat().setEnabled(isChatEnabled);
        game.setSettings(settings);
        game.setActivePlayer(settings.getPlayerWhite());
        return game;
    }
}