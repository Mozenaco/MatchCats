package cats.match.android.matchcats;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.MutableLiveData;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.mockito.Mockito.*;

import cats.match.android.data.entities.Game;
import cats.match.android.data.sharedpreferences.PreferenceHelper;
import cats.match.android.viewmodel.GameViewModel;

public class GameViewModelTest {

    GameViewModel gameViewModel;
    PreferenceHelper preferenceHelper = mock(PreferenceHelper.class);

    @Rule public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp(){
        gameViewModel = new GameViewModel(preferenceHelper);
        DummyEntities.dummyGame();
    }

    @Test
    public void shouldSaveHighScores() {

        gameViewModel.saveHighScores();

        MutableLiveData<Boolean> savedHighScore = gameViewModel.getObservableSavedHighScore();

        Assert.assertTrue(savedHighScore.getValue());

    }

    @Test
    public void shouldSetTurnToPlayer() {

        gameViewModel.setTurnToPlayer(0);

        Assert.assertTrue(Game.getInstance().currentPlayerTurn == 0);

        gameViewModel.setTurnToPlayer(1);

        Assert.assertTrue(Game.getInstance().currentPlayerTurn == 1);
    }

    @Test
    public void shouldAddScoreBecauseMatch() {

        int previousPlayerScore = Game.getInstance().currentPlayerOneScore;

        gameViewModel.addScoreBecauseMatch();

        Assert.assertTrue(Game.getInstance().currentPlayerOneScore == previousPlayerScore + 100);
    }

    @Test
    public void shouldCheckEndGameLevel() {

        Game.getInstance().currentImagesMatched = 0;

        gameViewModel.checkEndGameLevel();

        MutableLiveData<Boolean> showNextLevelButton = gameViewModel.getObservableShowNextLevelButton();

        Assert.assertNull(showNextLevelButton.getValue());



        Game.getInstance().currentImagesMatched = 6;

        gameViewModel.checkEndGameLevel();

        showNextLevelButton = gameViewModel.getObservableShowNextLevelButton();

        Assert.assertTrue(showNextLevelButton.getValue());

    }

    @Test
    public void shouldForceEndGame() {

        gameViewModel.forceEndGame();

        MutableLiveData<Boolean> endGame = gameViewModel.getObservableEndGameLevel();

        Assert.assertTrue(endGame.getValue());

    }
}
