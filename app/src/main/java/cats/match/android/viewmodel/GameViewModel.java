package cats.match.android.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import cats.match.android.data.entities.HighScore;
import cats.match.android.data.sharedpreferences.PreferenceHelper;

public class GameViewModel extends ViewModel {

    PreferenceHelper preferenceHelper;
    private MutableLiveData<Boolean> savedHighScore;

    public GameViewModel(PreferenceHelper preferenceHelper){
        this.preferenceHelper = preferenceHelper;
    }

    public void saveHighScores(HighScore highScore){

        preferenceHelper.setHighScores(highScore);
        savedHighScore.postValue(true);
    }
}
