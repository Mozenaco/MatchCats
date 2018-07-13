package cats.match.android.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.view.View;

import com.wajahatkarim3.easyflipview.EasyFlipView;

import cats.match.android.data.entities.ActionAfterFlip;
import cats.match.android.data.entities.Game;
import cats.match.android.data.entities.HighScore;
import cats.match.android.data.sharedpreferences.PreferenceHelper;

public class GameViewModel extends ViewModel {

    private PreferenceHelper preferenceHelper;
    private MutableLiveData<Boolean> savedHighScore = new MutableLiveData<>();
    private int openedCards = 0;
    private View firstOpenedView;
    private int firstOpenedValue;

    private MutableLiveData<ActionAfterFlip> actionAfterFlip = new MutableLiveData<ActionAfterFlip>();
    private MutableLiveData<Integer> playerOneScore = new MutableLiveData<Integer>();
    private MutableLiveData<Integer> playerTwoScore = new MutableLiveData<Integer>();
    private MutableLiveData<Boolean> levelEnded = new MutableLiveData<Boolean>();

    public GameViewModel(PreferenceHelper preferenceHelper){
        this.preferenceHelper = preferenceHelper;
    }

    public void saveHighScores(){

        HighScore highScore = new HighScore(Game.getInstance().currentPlayerOneName, Game.getInstance().currentPlayerOneScore);
        preferenceHelper.setHighScores(highScore);

        if(Game.getInstance().currentNumOfPlayers == 2){

            highScore = new HighScore(Game.getInstance().currentPlayerTwoName, Game.getInstance().currentPlayerTwoScore);
            preferenceHelper.setHighScores(highScore);
        }
        savedHighScore.postValue(true);
    }

    public void open(int i, EasyFlipView easyFlipView) {
        if(easyFlipView.isBackSide() && easyFlipView != firstOpenedView) {
            openedCards++;
        }
        if(openedCards == 1) {
            firstOpenedView = easyFlipView;
            firstOpenedValue = i;
            return;
        }
        if(openedCards == 2) {

            Boolean isMatch = Game.getInstance().checkMatch(firstOpenedValue, i);

            ActionAfterFlip action = new ActionAfterFlip(firstOpenedView, easyFlipView, isMatch);
            actionAfterFlip.postValue(action);
        }
    }

    public void addScoreBecauseMatch(){

        if(Game.getInstance().currentPlayerTurn == 1) {
            Game.getInstance().currentPlayerOneScore += 100;
            playerOneScore.postValue(Game.getInstance().currentPlayerOneScore);
        }

        if(Game.getInstance().currentPlayerTurn == 2) {
            Game.getInstance().currentPlayerTwoScore += 100;
            playerTwoScore.postValue(Game.getInstance().currentPlayerTwoScore);
        }
    }

    public void resetOpenedCardsValue(){
        openedCards = 0;
        firstOpenedValue = -1;
        firstOpenedView = null;
    }

    public void checkEndGameLevel(){
        if(Game.getInstance().currentImagesMatched == Game.getInstance().numImages){
            levelEnded.postValue(true);
        }
    }

    public void forceEndGame(){
        levelEnded.postValue(true);
    }

    public int getCurrentPlayerOneScore(){

        return Game.getInstance().currentPlayerOneScore;
    }

    public int getCurrentPlayerTwoScore(){

        return Game.getInstance().currentPlayerTwoScore;
    }

    //Gets Observables Methods
    public MutableLiveData<ActionAfterFlip> getObservableActionAfterFlip() {
        return actionAfterFlip;
    }

    public MutableLiveData<Integer> getObservablePlayerOneScore() {
        return playerOneScore;
    }

    public MutableLiveData<Integer> getObservablePlayerTwoScore() {
        return playerTwoScore;
    }

    public MutableLiveData<Boolean> getObservableEndGameLevel() {
        return levelEnded;
    }

    public void setTurnToPlayer(int i) {
        Game.getInstance().currentPlayerTurn = i;
    }
}
