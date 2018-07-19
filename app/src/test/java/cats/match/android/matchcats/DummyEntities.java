package cats.match.android.matchcats;

import cats.match.android.data.entities.Game;
import cats.match.android.data.entities.enums.GameMode;

public class DummyEntities {

    public static Game dummyGame(){

        Game game = Game.getInstance();

        game.currentPlayerOneName = "Mike";
        game.currentPlayerOneScore = 500;
        game.currentPlayerTwoName = "Johnny";
        game.currentPlayerTwoScore = 100;
        game.currentNumOfPlayers = 2;
        game.currentImagesMatched = 6;
        game.gameMode = GameMode.MEDIUM;
        game.numImages = 6;

        return game;
    }
}
