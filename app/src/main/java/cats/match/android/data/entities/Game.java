package cats.match.android.data.entities;

import android.view.View;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.inject.Singleton;
import cats.match.android.data.entities.enums.GameMode;
import cats.match.android.matchcats.R;

/**
 * This class orchestrate the current game variables, states and links for resources
 *
 * @author Mateus Andrade
 * @since 06/07/2018
 */
@Singleton
public class Game {

    public int currentPlayerOneScore = 0;
    public String currentPlayerOneName = "";
    public int currentPlayerTwoScore = 0;
    public String currentPlayerTwoName = "";
    public int currentNumOfPlayers = 1;
    public int currentImagesMatched = 0;
    public int currentPlayerTurn = 1;
    public int currentTime;
    public List<Photo> photos;
    public GameMode gameMode = GameMode.EASY;
    public List<View> gameImageViews;
    public static List<Integer> listItens;
    private int comparationLimit;
    public int numImages;

    private static Game instance;

    private Game() {
    }

    public synchronized static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void resetGame(){
        currentPlayerOneScore = 0;
        currentPlayerOneName = "";
        currentPlayerTwoScore = 0;
        currentPlayerTwoName = "";
        currentTime = 0;
        gameMode = GameMode.EASY;
    }

    public void resetCurrentImagesMatched(){
        currentImagesMatched = 0;
    }

    public void initGame(){

        numImages = gameMode.toIntValue()/2;
        comparationLimit = (numImages*2)-2;

        switch (gameMode){

            //Init an easy game mode with 3 images. (3 images multiplied by 2 is 6 squares to play with)
            case EASY:
                initEasyGameConfigs();
                break;

            case MEDIUM:
                initMediumGameConfigs();
                break;

            case HARD:
                initHardGameConfigs();
                break;
        }

        loadGameImages();
    }

    public List<Integer> getViewIds(){

        switch (gameMode) {

            case EASY:
                return Arrays.asList(R.id.viewA1, R.id.viewA2, R.id.viewB1, R.id.viewB2,
                        R.id.viewC1, R.id.viewC2);
            case MEDIUM:
                return Arrays.asList(R.id.viewA1, R.id.viewA2, R.id.viewA3, R.id.viewB1,
                        R.id.viewB2, R.id.viewB3, R.id.viewC1, R.id.viewC2, R.id.viewC3,
                        R.id.viewD1, R.id.viewD2, R.id.viewD3);
            case HARD:
                return Arrays.asList(R.id.viewA1, R.id.viewA2, R.id.viewA3, R.id.viewA4,
                        R.id.viewB1, R.id.viewB2, R.id.viewB3, R.id.viewB4, R.id.viewC1,
                        R.id.viewC2, R.id.viewC3, R.id.viewC4, R.id.viewD1, R.id.viewD2,
                        R.id.viewD3, R.id.viewD4);
            default:
                return null;
        }
    }

    public Integer getIdLayoutBase(){

        switch (gameMode) {

            case EASY:
                return R.layout.game_mode_1;
            case MEDIUM:
                return R.layout.game_mode_2;
            case HARD:
                return R.layout.game_mode_3;
            default:
                return null;
        }
    }

    public Integer getFullTimeValue(){

        switch (gameMode) {

            case EASY:
                return 21000;
            case MEDIUM:
                return 51000;
            case HARD:
                return 71000;
            default:
                return null;
        }
    }

    private void initEasyGameConfigs(){

        //Game Ramdom Logic
        listItens = new ArrayList<>(Arrays.asList(0,1,2,3,4,5));
        Collections.shuffle(listItens);

    }

    private void initMediumGameConfigs(){

        //Game Ramdom Logic
        listItens = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11));
        Collections.shuffle(listItens);

    }

    private void initHardGameConfigs(){

        //Game Ramdom Logic
        listItens = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15));
        Collections.shuffle(listItens);

    }

    private void loadGameImages() {

        //Loading Images
        int j = 0;
        for(int i = 0; i < numImages; i++){

            Picasso.get().load(photos.get(i).getUrlString()).into((ImageView)gameImageViews.get(listItens.get(j)));
            j++;
            Picasso.get().load(photos.get(i).getUrlString()).into((ImageView)gameImageViews.get(listItens.get(j)));
            j++;
        }
    }

    public Boolean checkMatch(int firstOpenedValue, int secondOpenedValue) {

        Boolean isMatch = false;

        for(int i = 0; i <= comparationLimit; i+=2){

            if(listItens.get(i) == firstOpenedValue && listItens.get(i+1) == secondOpenedValue ||
                    listItens.get(i+1) == firstOpenedValue && listItens.get(i) == secondOpenedValue)
            isMatch = true;
        }

        if(isMatch)
            currentImagesMatched++;

        return isMatch;
    }

    public void nextLevel(){

        //Go to a different gamemodelevel

        switch (gameMode){
            case EASY:
                gameMode = GameMode.MEDIUM;
                break;

            case MEDIUM:
                gameMode = GameMode.HARD;
                break;
        }
    }
}