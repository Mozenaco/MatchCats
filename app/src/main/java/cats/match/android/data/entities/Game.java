package cats.match.android.data.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Singleton;

import cats.match.android.data.entities.enums.GameMode;
import cats.match.android.data.sharedpreferences.PreferenceHelper;

/*
 *
 * @author Mateus Andrade
 * @since 06/07/2018
 *
 */
@Singleton
public class Game {

    public int currentPlayerScore = 0;
    public int currentTime;
    public List<Photo> photos;
    public GameMode gameMode = GameMode.EASY;
    public List<View> gameImageViews;
    public List<Integer> listItens;
    private int comparationLimit;
    public int numImages;
    public int currentImagesMatched = 0;

    private static Game instance;

    private Game() {
    }

    public synchronized static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
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

    private void initEasyGame(){

        numImages = 3;
        comparationLimit = 4;

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