package cats.match.android.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.view.View;

import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.List;

import cats.match.android.data.entities.ActionAfterFlip;
import cats.match.android.data.entities.Game;
import cats.match.android.data.entities.HighScore;
import cats.match.android.data.entities.Photo;
import cats.match.android.data.sharedpreferences.PreferenceHelper;

public class GameViewModel extends ViewModel {

    PreferenceHelper preferenceHelper;
    private MutableLiveData<Boolean> savedHighScore;
    private int openedCards = 0;
    private View firstOpenedView;
    private int firstOpenedValue;

    private MutableLiveData<ActionAfterFlip> actionAfterFlip = new MutableLiveData<ActionAfterFlip>();
    private MutableLiveData<Integer> playerOneScore = new MutableLiveData<Integer>();
    private MutableLiveData<Boolean> levelEnded = new MutableLiveData<Boolean>();

    public GameViewModel(PreferenceHelper preferenceHelper){
        this.preferenceHelper = preferenceHelper;
    }

    public void saveHighScores(HighScore highScore){

        preferenceHelper.setHighScores(highScore);
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

        Game.getInstance().currentPlayerScore += 10;
        playerOneScore.postValue(Game.getInstance().currentPlayerScore);
    }

    public void resetOpenedCardsValue(){
        openedCards = 0;
        firstOpenedValue = -1;
        firstOpenedView = null;
    }

    public void checkEndGameLevel(){
        if(Game.getInstance().currentImagesMatched == Game.getInstance().numImages){
            levelEnded.postValue(true);
            Game.getInstance().nextLevel();
        }
    }

    public MutableLiveData<ActionAfterFlip> getObservableActionAfterFlip() {
        return actionAfterFlip;
    }

    public MutableLiveData<Integer> getObservablePlayerOneScore() {
        return playerOneScore;
    }

    public MutableLiveData<Boolean> getObservableEndGameLevel() {
        return levelEnded;
    }

}
