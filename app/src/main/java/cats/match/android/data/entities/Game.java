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
        currentImagesMatched = 0;
        currentTime = 0;
        gameMode = GameMode.EASY;
    }

    public void initGame(){

        switch (gameMode){

            case EASY:
                initEasyGame();
                break;

            case MEDIUM:
                initMediumGame();
                break;
        }
    }

    //Init an easy game mode with 3 images. (3 images multiplied by 2 is 6 squares to play with)
    private void initEasyGame(){

        numImages = 3;
        comparationLimit = (numImages*2)-2;

        //Game Ramdom Logic
        listItens = new ArrayList<>(Arrays.asList(0,1,2,3,4,5));
        Collections.shuffle(listItens);

        //Loading Images
        int j = 0;
        for(int i = 0; i < numImages; i++){

            Picasso.get().load(photos.get(i).getUrlString()).into((ImageView)gameImageViews.get(listItens.get(j)));
            j++;
            Picasso.get().load(photos.get(i).getUrlString()).into((ImageView)gameImageViews.get(listItens.get(j)));
            j++;
        }
    }

    private void initMediumGame(){

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

        //Select a different gamemodelevel
        initGame();
    }
}