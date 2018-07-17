package cats.match.android.data.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cats.match.android.data.entities.HighScore;

/**
 * PreferenceHelper class is a singleton class that is responsible for store some player data on Game
 * and store information about highscores.
 *
 * @author Mateus Andrade
 * @since 06/07/2018
 */
public class PreferenceHelper {

    private static PreferenceHelper instance;

    private SharedPreferences sharedPreferences;

    private static final String PREFS_NAME = "default_preferences";
    private static final String NUM_OF_PLAYERS = "num_of_players";
    private static final String NAME_PLAYER_ONE = "name_player_one";
    private static final String NAME_PLAYER_TWO = "name_player_two";
    private static final String HIGHSCORES = "highscores";

    public synchronized static PreferenceHelper getInstance(Context context) {
        if (instance == null) {
            instance = new PreferenceHelper(context);
        }
        return instance;
    }

    private PreferenceHelper(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public int getNumOfPlayers() {
        return sharedPreferences.getInt(NUM_OF_PLAYERS, 1);
    }

    public void setNumOfPlayers(int value) {
        sharedPreferences.edit().putInt(NUM_OF_PLAYERS, value).apply();
    }

    public String getNamePlayerOne() {
        return sharedPreferences.getString(NAME_PLAYER_ONE, "");
    }

    public void setNamePlayerOne(String value) {
        sharedPreferences.edit().putString(NAME_PLAYER_ONE, value).apply();
    }

    public String getNamePlayerTwo() {
        return sharedPreferences.getString(NAME_PLAYER_TWO, "");
    }

    public void setNamePlayerTwo(String value) {
        sharedPreferences.edit().putString(NAME_PLAYER_TWO, value).apply();
    }

    public List<HighScore> getHighScores() {

        String connectionsJSONString = sharedPreferences.getString(HIGHSCORES, null);
        Type type = new TypeToken<List<HighScore>>() {}.getType();
        List<HighScore> highScores = new Gson().fromJson(connectionsJSONString, type);

        if (highScores == null) {
            highScores = new ArrayList<>();
        }
        Collections.sort(highScores);//Sort the highscores
        return highScores;
    }

    public void setHighScores(List<HighScore> highScores) {

        List<HighScore> list = getHighScores();
        list.addAll(highScores);

        Gson gson = new Gson();
        String json = gson.toJson(list);
        sharedPreferences.edit().putString(HIGHSCORES, json).apply();
    }

    public void setHighScores(HighScore highScore) {

        List<HighScore> list = getHighScores();
        list.add(highScore);

        Gson gson = new Gson();
        String json = gson.toJson(list);
        sharedPreferences.edit().putString(HIGHSCORES, json).apply();
    }
}